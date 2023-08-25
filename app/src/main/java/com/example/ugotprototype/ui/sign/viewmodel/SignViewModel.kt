package com.example.ugotprototype.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.SignService
import com.example.ugotprototype.data.sign.SignData
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.TOKEN_DATA
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
        private val apiService: ApiService,
        private val signService: SignService
) : ViewModel() {
    private val _currentFragmentIndex = MutableLiveData<Int>()
    val currentFragmentIndex: LiveData<Int> = _currentFragmentIndex

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> = _nickName

    private val _major = MutableLiveData<String>()
    val major: LiveData<String> = _major

    private val _nowGrade = MutableLiveData<Int>()
    val nowGrade: LiveData<Int> = _nowGrade

    private val _nowClass = MutableLiveData<String>()
    val nowClass: LiveData<String> = _nowClass

    private val _selectedChipTexts = MutableLiveData<List<String>>()
    val selectedChipTexts: LiveData<List<String>> = _selectedChipTexts

    private val _buttonStateText = MutableLiveData<String>()
    val buttonStateText: LiveData<String> = _buttonStateText

    private val _blogLink = MutableLiveData<String>()
    val blogLink: LiveData<String> = _blogLink

    private val _gitHubLink = MutableLiveData<String>()
    val gitHubLink: LiveData<String> = _gitHubLink

    private val _realName = MutableLiveData<String>()
    val realName: LiveData<String> = _realName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    init {
        _currentFragmentIndex.value = 0
    }

    companion object {
        private const val NICKNAME_REGEX_PATTERN = "^[A-Za-z0-9]{3,10}$"
        private const val GRADE_REGEX_PATTERN = "^[1234]$"
        private val CLASS_REGEX_PATTERN = "^(YA|YB|YC|YD|YJ|YK)$".toRegex(RegexOption.IGNORE_CASE)
        private const val KOREAN_NAME_PATTERN = "^[가-힣]+$"
        private const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
    }

    fun setCurrentFragmentIndex(index: Int) {
        _currentFragmentIndex.value = index
    }

    fun setNickName(nickName: String) {
        _nickName.value = nickName
    }

    fun setMajor(major: String) {
        _major.value = major
    }

    fun setNowGrade(grade: Int) {
        _nowGrade.value = grade
    }

    fun setNowClass(nowClass: String) {
        _nowClass.value = nowClass
    }

    fun updateSelectedChips(chip: Chip) {
        val updatedChipTexts = _selectedChipTexts.value?.toMutableList() ?: mutableListOf()

        if (chip.isChecked) {
            if (updatedChipTexts.size < 3) {
                updatedChipTexts.add(chip.text.toString())
            } else {
                chip.isChecked = false
                updatedChipTexts.remove(chip.text.toString())
            }
        } else {
            updatedChipTexts.remove(chip.text.toString())
        }

        _selectedChipTexts.value = updatedChipTexts
    }

    fun setButtonStateText(text: String) {
        _buttonStateText.value = text
    }

    fun setBlogLink(blog: String) {
        _blogLink.value = blog
    }

    fun setGitHubLink(gitHub: String) {
        _gitHubLink.value = gitHub
    }

    fun setRealname(realName: String) {
        _realName.value = realName
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun checkGitHubAccount(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                gitHubLink.value?.let { apiService.getUser(it, "Bearer $TOKEN_DATA") }
            }.onSuccess { callback(true) }
                .onFailure { callback(false) }
        }
    }

    fun isMajorValid(): Boolean {
        return (major.value?.isNotEmpty() == true && major.value.toString() != "null")
    }

    fun isNickNameValid(): Boolean {
        return nickName.value?.matches(NICKNAME_REGEX_PATTERN.toRegex()) == true
    }

    fun isNowGradeValid(): Boolean {
        return nowGrade.value?.toString()?.matches(GRADE_REGEX_PATTERN.toRegex()) == true
    }

    fun isNowClassValid(): Boolean {
        return nowClass.value?.matches(CLASS_REGEX_PATTERN) == true
    }

    fun isSelectedChipTextsValid(): Boolean {
        return selectedChipTexts.value?.isNotEmpty() == true
    }

    fun isBlogValid(): Boolean {
        return blogLink.value!!.startsWith("https://") && blogLink.value!!.contains(".com")
    }

    fun isRealNameValid(): Boolean {
        Log.d("realName", realName.value.toString())
        Log.d(
            "realName",
            (realName.value?.matches(KOREAN_NAME_PATTERN.toRegex()) == true).toString()
        )
        return realName.value!!.matches(KOREAN_NAME_PATTERN.toRegex())
    }

    fun isEmailValid(): Boolean {
        Log.d("email", email.value.toString())
        return email.value!!.matches(EMAIL_PATTERN.toRegex())
    }

    fun signUp() {
        viewModelScope.launch {
            kotlin.runCatching {
                signService.createAccount(
                    SignData(
                        name = realName.value.toString(),
                        nickname = _nickName.value.toString(),
                        email = _email.value.toString(),
                        password = "test1234",
                        major = _major.value.toString(),
                        grade = _nowGrade.value!!,
                        _class = _nowClass.value.toString(),
                        skill = _selectedChipTexts.value!!,
                        gitHubLink = "github.com/" + _gitHubLink.value,
                        personalBlogLink = _blogLink.value.toString()
                    )
                )
            }.onSuccess { Log.d("성공", "성공") }
                .onFailure { Log.d("실패", it.toString()) }
        }
    }
}
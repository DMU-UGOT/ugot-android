package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.CLASS_REGEX_PATTERN
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.EMAIL_PATTERN
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.GRADE_REGEX_PATTERN
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.KOREAN_NAME_PATTERN
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.NICKNAME_REGEX_PATTERN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolActivityViewModel @Inject constructor(
    private val profileService: ProfileService,
    private val sharedPreference: SharedPreference,
    private val apiService: ApiService
) : ViewModel() {

    private val _memberData = MutableLiveData<ProfileMemberData>()
    val memberData: MutableLiveData<ProfileMemberData> = _memberData

    private val _isMemberDataPatch = MutableLiveData<Boolean>()
    val isMemberDataPatch: MutableLiveData<Boolean> = _isMemberDataPatch

    private val _userInfoValid = MutableLiveData<Boolean>()
    val userInfoValid: MutableLiveData<Boolean> = _userInfoValid

    fun initData() {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.getUserInfo(sharedPreference.getMemberId().toString())
            }.onSuccess {
                memberData.value = it
            }
        }
    }

    fun patchMemberData(memberData: ProfileMemberData) {
        viewModelScope.launch {
            kotlin.runCatching {
                memberData.gitHubLink = "https://github.com/" + memberData.gitHubLink
                        profileService.setUserInfo(
                    sharedPreference.getMemberId().toString(), memberData
                )
            }.onSuccess {
                isMemberDataPatch.value = true
            }
        }
    }

    fun isValidateCheck(memberData: ProfileMemberData) {
        if (isEmailValid(memberData.email) &&
            isRealNameValid(memberData.name) &&
            isBlogValid(memberData.personalBlogLink) &&
            isNowClassValid(memberData._class) &&
            isNowGradeValid(memberData.grade) &&
            isNickNameValid(memberData.nickname)
        ) {
            checkGitHubAccount(memberData.gitHubLink) { isGitHubValid ->
                _userInfoValid.value = isGitHubValid
            }
        } else{
            _userInfoValid.value = false
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.matches(EMAIL_PATTERN.toRegex())
    }

    fun isRealNameValid(realName: String): Boolean {
        return realName.matches(KOREAN_NAME_PATTERN.toRegex())
    }

    fun isBlogValid(blogLink: String): Boolean {
        if(blogLink.isBlank()) {
            return true
        }
        return blogLink.contains(".com") ?: true
    }

    fun isNowClassValid(nowClass: String): Boolean {
        return nowClass.matches(CLASS_REGEX_PATTERN)
    }

    fun isNickNameValid(nickName: String): Boolean {
        return nickName.matches(NICKNAME_REGEX_PATTERN.toRegex())
    }

    fun isNowGradeValid(nowGrade: Int): Boolean {
        return nowGrade.toString().matches(GRADE_REGEX_PATTERN.toRegex())
    }

    fun checkGitHubAccount(gitHubLink: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                gitHubLink.let { apiService.getUser(it) }
            }.onSuccess { callback(true) }.onFailure { callback(false) }
        }
    }
}
package com.example.ugotprototype.ui.profile.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySetPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyStudyPostPatchViewModel @Inject constructor(
    private val apiService: ApiService,
    private val profileService: ProfileService
) : ViewModel() {

    companion object {
        const val BASE_URL_PATTERN = "open\\.kakao\\.com/[A-Za-z0-9]+"
    }

    private val _isStudyPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isStudyPostRegisterBtnEnabled: LiveData<Boolean> = _isStudyPostRegisterBtnEnabled

    private val _studyCreateData = MutableLiveData<StudySetPost>()
    val studyCreateData: LiveData<StudySetPost> = _studyCreateData

    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _seekBar = MutableLiveData<Int>()
    val seekBar: LiveData<Int> = _seekBar

    private val _selectSpinner = MutableLiveData<Int>()
    val selectSpinner: LiveData<Int> = _selectSpinner

    private val _isTeamExists = MutableLiveData<Boolean>()
    val isTeamExists: LiveData<Boolean> = _isTeamExists

    private val _createFinish = MutableLiveData<Boolean>()
    val createFinish: LiveData<Boolean> = _createFinish

    private val _studyItemList = MutableLiveData<StudyGetPost>()
    val studyItemList: LiveData<StudyGetPost> = _studyItemList


    fun isStudyPostRegisterButtonState(enabled: Boolean) {
        _isStudyPostRegisterBtnEnabled.value = enabled
    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _etText.value = s.toString()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _selectSpinner.value = position
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            _seekBar.value = progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    fun isKakaoOpenChatBaseURL(input: String, callback: (String) -> Unit) {
        if (input.matches(BASE_URL_PATTERN.toRegex())) {
            callback("success")
        } else {
            callback("falied")
        }
    }

    fun initData(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _studyItemList.value = profileService.getStudy(teamId)
            }
        }
    }

    fun isStudyExists(gitHubLink: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getOrganization(gitHubLink)
            }.onSuccess { _isTeamExists.value = true }.onFailure { _isTeamExists.value = false }
        }
    }

    fun patchPost(teamId: Int, studyPost: StudySetPost) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.patchStudy(teamId, studyPost)
            }.onSuccess {
                _createFinish.value = true
            }.onFailure {
                _createFinish.value = false
            }
        }
    }
}
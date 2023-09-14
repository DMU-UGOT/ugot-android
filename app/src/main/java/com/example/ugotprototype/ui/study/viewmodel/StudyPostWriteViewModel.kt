package com.example.ugotprototype.ui.study.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.StudyService
import com.example.ugotprototype.data.study.StudySetPost
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.ui.team.viewmodel.TeamPostWriteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyPostWriteViewModel @Inject constructor(private val apiService: ApiService, private val studyService: StudyService) : ViewModel() {
    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _seekBar = MutableLiveData<Int>()
    val seekBar: LiveData<Int> = _seekBar

    private val _selectSpinner = MutableLiveData<Int>()
    val selectSpinner: LiveData<Int> = _selectSpinner

    private val _isStudyPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isStudyPostRegisterBtnEnabled: LiveData<Boolean> = _isStudyPostRegisterBtnEnabled

    private val _isTeamExists = MutableLiveData<Boolean>()
    val isTeamExists: LiveData<Boolean> = _isTeamExists

    private val _studyCreateData = MutableLiveData<StudySetPost>()
    val studyCreateData: LiveData<StudySetPost> = _studyCreateData

    private val _createFinish = MutableLiveData<Boolean>()
    val createFinish: LiveData<Boolean> = _createFinish

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

    fun isStudyPostRegisterButtonState(enabled: Boolean) {
        _isStudyPostRegisterBtnEnabled.value = enabled
    }

    fun isTeamExists(gitHubLink: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getOrganization(gitHubLink)
            }.onSuccess { _isTeamExists.value = true }.onFailure { _isTeamExists.value = false }
        }
    }

    fun setStudyPostData(studyPostData: StudySetPost) {
        _studyCreateData.value = studyPostData
    }

    fun sendStudyData(studyPostData: StudySetPost) {
        _createFinish.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                studyService.setStudies(studyPostData)
            }.onSuccess { _createFinish.value = true }
        }
    }

    fun isKakaoOpenChatBaseURL(input: String, callback: (String) -> Unit) {
        if (input.matches(TeamPostWriteViewModel.BASE_URL_PATTERN.toRegex())) {
            callback("success")
        } else{
            callback("falied")
        }
    }
}
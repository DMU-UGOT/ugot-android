package com.example.ugotprototype.ui.profile.viewmodel

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
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.team.TeamPostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyTeamPostPatch @Inject constructor(
    private val apiService: ApiService,
    private val profileService: ProfileService,
    private val groupService: GroupService
) : ViewModel() {

    companion object {
        val BASE_URL_PATTERN = """(https://)?open\.kakao\.com/o/[a-zA-Z]+""".toRegex()
    }

    private val _isTeamPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isTeamPostRegisterBtnEnabled: LiveData<Boolean> = _isTeamPostRegisterBtnEnabled

    private val _teamCreateData = MutableLiveData<TeamPostData>()
    val teamCreateData: LiveData<TeamPostData> = _teamCreateData

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

    private val _teamItemList = MutableLiveData<Team>()
    val teamItemList: LiveData<Team> = _teamItemList

    fun isTeamPostRegisterButtonState(enabled: Boolean) {
        _isTeamPostRegisterBtnEnabled.value = enabled
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
        if (input.matches(BASE_URL_PATTERN)) {
            callback("success")
        } else {
            callback("falied")
        }
    }

    fun initData(teamId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _teamItemList.value = profileService.getTeam(teamId)
            }
        }
    }

    fun isTeamExists(gitHubLink: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getOrganization(gitHubLink)
            }.onSuccess { _isTeamExists.value = true }.onFailure { _isTeamExists.value = false }
        }
    }

    fun patchPost(teamId: Int, teamPost: TeamPostData) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.patchTeam(teamId, teamPost)
            }.onSuccess {
                _createFinish.value = true
            }.onFailure {
                _createFinish.value = false
            }
        }
    }
}
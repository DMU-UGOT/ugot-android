package com.example.ugotprototype.ui.team.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.data.group.GroupPostWriteData
import com.example.ugotprototype.data.team.TeamPostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPostWriteViewModel @Inject constructor(
    private val teamBuildingService: TeamBuildingService,
    private val apiService: ApiService,
    private val groupService: GroupService
) : ViewModel() {

    companion object {
        const val BASE_URL_PATTERN = "(https?://)?open\\.kakao\\.com/[A-Za-z0-9]+"
    }

    private val _isTeamPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isTeamPostRegisterBtnEnabled: LiveData<Boolean> = _isTeamPostRegisterBtnEnabled

    private val _teamCreateData = MutableLiveData<TeamPostData>()
    val teamCreateData: LiveData<TeamPostData> = _teamCreateData

    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _selectSpinner = MutableLiveData<Int>()
    val selectSpinner: LiveData<Int> = _selectSpinner

    private val _isTeamExists = MutableLiveData<Boolean>()
    val isTeamExists: LiveData<Boolean> = _isTeamExists

    private val _createFinish = MutableLiveData<Boolean>()
    val createFinish: LiveData<Boolean> = _createFinish

    private val _postTitles = MutableLiveData<List<String>>()
    val postTitles: LiveData<List<String>> = _postTitles

    private val _postData = MutableLiveData<Map<Int, String>>()
    val postData: LiveData<Map<Int, String>> = _postData

    private val _isGroupData = MutableLiveData<List<GroupPostWriteData>>()
    val isGroupData: LiveData<List<GroupPostWriteData>> = _isGroupData

    private val _gitHubUrl = MutableLiveData<String>()
    val gitHubUrl: LiveData<String> = _gitHubUrl


    fun isTeamPostRegisterButtonState(enabled: Boolean) {
        _isTeamPostRegisterBtnEnabled.value = enabled
    }

    fun setTeamPostData(teamPostData: TeamPostData) {
        _teamCreateData.value = teamPostData
    }

    fun isTeamExists(gitHubLink: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getOrganization(gitHubLink)
            }.onSuccess { _isTeamExists.value = true }.onFailure { _isTeamExists.value = false }
        }
    }

    fun sendTeamData(teamPostData: TeamPostData) {
        _createFinish.value = false
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.createTeam(teamPostData)
            }.onSuccess {
                _createFinish.value = true
                Log.d("test", teamPostData.toString())
            }
        }
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

    fun isKakaoOpenChatBaseURL(input: String, callback: (String) -> Unit) {
        if (input.matches(BASE_URL_PATTERN.toRegex())) {
            callback("success")
        } else {
            callback("falied")
        }
    }

    fun getGroupSpinnerData() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupService.getTeamPostWriteGroupData()
            }.onSuccess {
                val list = mutableListOf<String>()
                val data = mutableMapOf<Int, String>()
                it.forEach {
                    list.add(it.groupName)
                    data.put(it.groupId, it.groupName)
                }

                _postData.value = data
                _postTitles.value = list
            }
        }
    }

    fun getGroupDetailData(position: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val foundKey =
                    _postData.value!!.entries.find { it.value == _postTitles.value!![position] }?.key
                _isGroupData.value = groupService.getTeamPostWriteGroupData()

                _isGroupData.value!!.forEach {
                    if (it.groupId == foundKey) {
                        _gitHubUrl.value = it.githubUrl
                    }
                }
            }
        }
    }
}
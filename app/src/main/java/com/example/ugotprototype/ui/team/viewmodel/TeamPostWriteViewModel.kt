package com.example.ugotprototype.ui.team.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.di.api.TeamBuildingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPostWriteViewModel @Inject constructor(private val teamBuildingService: TeamBuildingService) :
    ViewModel() {

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


    fun isTeamPostRegisterButtonState(enabled: Boolean) {
        _isTeamPostRegisterBtnEnabled.value = enabled
    }

    fun setTeamPostData(teamPostData: TeamPostData) {
        _teamCreateData.value = teamPostData
    }

    fun sendTeamData(teamPostData: TeamPostData) {
        viewModelScope.launch {
            kotlin.runCatching {
                teamBuildingService.createTeam(teamPostData)
            }
        }
    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            _etText.value = s.toString()
        }

        override fun afterTextChanged(s: Editable?) { }
    }

    val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            _selectSpinner.value = position
        }

        override fun onNothingSelected(parent: AdapterView<*>?) { }
    }

    val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            _seekBar.value = progress
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) { }

        override fun onStopTrackingTouch(seekBar: SeekBar?) { }
    }
}
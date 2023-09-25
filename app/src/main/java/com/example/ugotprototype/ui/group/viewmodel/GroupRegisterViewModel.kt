package com.example.ugotprototype.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.GroupService
import com.example.ugotprototype.data.group.GroupRegisterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupRegisterViewModel @Inject constructor(
    private val groupService: GroupService,
    private val apiService: ApiService
) : ViewModel() {
    private val _groupRegisterData = MutableLiveData<GroupRegisterData>()
    var groupRegisterData: LiveData<GroupRegisterData> = _groupRegisterData

    private val _groupMaxPersonnel = MutableLiveData<Int>()
    var groupMaxPersonnel: LiveData<Int> = _groupMaxPersonnel

    private val _isCreateGroup = MutableLiveData<Boolean>()
    var isCreateGroup: LiveData<Boolean> = _isCreateGroup

    fun groupMaxPersonnel(maxPersonnel: Int) {
        _groupMaxPersonnel.value = maxPersonnel
    }

    fun registerGroup(groupRegisterData: GroupRegisterData) {
        _groupRegisterData.value = groupRegisterData
    }

    fun sendRegisterGroupData() {
        viewModelScope.launch {
            kotlin.runCatching {
                _groupRegisterData.value?.let { groupService.registerGroup(it) }
            }.onSuccess {
                _isCreateGroup.value = true
            }.onFailure {
                _isCreateGroup.value = false
            }
        }
    }

    fun isValidGithub(input: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                apiService.getOrganization(input)
            }.onSuccess {
                callback(true)
            }.onFailure {
                callback(false)
            }
        }
    }
}
package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.change.CommunityChangeNewPostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeNewGroupViewModel @Inject constructor(
    private val changeService: ChangeService
) : ViewModel() {
    private val _isCommunityChangePostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isCommunityChangePostRegisterBtnEnabled: LiveData<Boolean> = _isCommunityChangePostRegisterBtnEnabled

    private val _communityCreateData = MutableLiveData<CommunityChangeNewPostData>()
    val communityCreateData: LiveData<CommunityChangeNewPostData> = _communityCreateData

    private val _createFinish = MutableLiveData<Boolean>()
    val createFinish: LiveData<Boolean> = _createFinish

    private val _spGrade = MutableLiveData<String>()
    val spGrade: LiveData<String> = _spGrade

    private val _spNowClass = MutableLiveData<String>()
    val spNowClass: LiveData<String> = _spNowClass

    private val _spChangeClass = MutableLiveData<String>()
    val spChangeClass: LiveData<String> = _spChangeClass

    fun isCommunityChangePostRegisterButtonState(enabled: Boolean) {
        _isCommunityChangePostRegisterBtnEnabled.value = enabled
    }

    fun sendCommunityChangeData(communityChangeNewPostData: CommunityChangeNewPostData) {
        viewModelScope.launch {
            kotlin.runCatching {
                changeService.createChange(communityChangeNewPostData)
            }.onSuccess { _createFinish.value = true }
                .onFailure { _createFinish.value = false }
        }
    }
}
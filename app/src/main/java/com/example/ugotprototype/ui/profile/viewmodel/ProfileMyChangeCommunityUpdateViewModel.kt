package com.example.ugotprototype.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeUpdateViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyChangeCommunityUpdateViewModel @Inject constructor(
    private val profileService: ProfileService
) : ViewModel() {
    private val _isProfileChangePostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isProfileChangePostRegisterBtnEnabled: LiveData<Boolean> =
        _isProfileChangePostRegisterBtnEnabled

    private val _profileChangeData = MutableLiveData<CommunityChangePostViewData>()
    val profileChangeData: LiveData<CommunityChangePostViewData> = _profileChangeData

    private val _spUpdateGrade = MutableLiveData<String>()
    val spUpdateGrade: LiveData<String> = _spUpdateGrade

    private val _spUpdateNowClass = MutableLiveData<String>()
    val spUpdateNowClass: LiveData<String> = _spUpdateNowClass

    private val _spUpdateChangeClass = MutableLiveData<String>()
    val spUpdateChangeClass: LiveData<String> = _spUpdateChangeClass

    private val _dataUpdate = MutableLiveData<Boolean>()
    val dataUpdate: LiveData<Boolean> = _dataUpdate

    fun isProfileChangePostRegisterButtonState(enabled: Boolean) {
        _isProfileChangePostRegisterBtnEnabled.value = enabled
    }

    fun sendUpdatedData(classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                val data = profileService.getChangeDetail(classChangeId)
                _profileChangeData.value = data
            }
        }
    }

    fun modifyCommunityChangeUpdateData(communityChangeUpdateViewData: CommunityChangeUpdateViewData, classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                profileService.updateChangeCommunity(classChangeId, communityChangeUpdateViewData)
            }.onSuccess { result ->
                _dataUpdate.value = true
            }.onFailure { exception ->
                _dataUpdate.value = false
            }
        }
    }
}
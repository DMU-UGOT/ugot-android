package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeUpdateViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeUpdateViewModel  @Inject constructor(
    private val changeService: ChangeService
) : ViewModel() {
    private val _isCommunityChangePostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isCommunityChangePostRegisterBtnEnabled: LiveData<Boolean> =
        _isCommunityChangePostRegisterBtnEnabled

    private val _communityChangeUpdateData = MutableLiveData<CommunityChangePostViewData>()
    val communityChangeUpdateData: LiveData<CommunityChangePostViewData> = _communityChangeUpdateData

    private val _spUpdateGrade = MutableLiveData<String>()
    val spUpdateGrade: LiveData<String> = _spUpdateGrade

    private val _spUpdateNowClass = MutableLiveData<String>()
    val spUpdateNowClass: LiveData<String> = _spUpdateNowClass

    private val _spUpdateChangeClass = MutableLiveData<String>()
    val spUpdateChangeClass: LiveData<String> = _spUpdateChangeClass

    private val _dataUpdate = MutableLiveData<Boolean>()
    val dataUpdate: LiveData<Boolean> = _dataUpdate

    private val _isCommunityGeneralExists = MutableLiveData<Boolean>()
    val isCommunityGeneralExists: LiveData<Boolean> = _isCommunityGeneralExists

    fun isCommunityChangePostRegisterButtonState(enabled: Boolean) {
        _isCommunityChangePostRegisterBtnEnabled.value = enabled
    }

    fun getChangeCommunityUpdateList(classChangeId : Int){
        viewModelScope.launch {
            kotlin.runCatching {
                var data = changeService.getChangeDetail(classChangeId)
                _communityChangeUpdateData.value = data
            }
        }
    }

    fun modifyCommunityChangeUpdateData(communityChangeUpdateViewData: CommunityChangeUpdateViewData, classChangeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                var data = changeService.updateChangeCommunity(classChangeId, communityChangeUpdateViewData)
            }.onSuccess { result ->
                _dataUpdate.value = true}
                .onFailure { exception ->
                    _dataUpdate.value = false
                }
        }
    }
}
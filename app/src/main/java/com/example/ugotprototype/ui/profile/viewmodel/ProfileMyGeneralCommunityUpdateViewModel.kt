package com.example.ugotprototype.ui.profile.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralUpdateViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileMyGeneralCommunityUpdateViewModel @Inject constructor(
    private val profileService: ProfileService
) : ViewModel() {
    private val _isProfileGeneralPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isProfileGeneralPostRegisterBtnEnabled: LiveData<Boolean> =
        _isProfileGeneralPostRegisterBtnEnabled

    private val _communityUpdateData = MutableLiveData<CommunityGeneralPostViewData>()
    val communityUpdateData: LiveData<CommunityGeneralPostViewData> = _communityUpdateData

    private val _etTitle = MutableLiveData<String>()
    val etTitle: LiveData<String> = _etTitle

    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _dataUpdate = MutableLiveData<Boolean>()
    val dataUpdate: LiveData<Boolean> = _dataUpdate

    fun isProfileGeneralPostRegisterButtonState(enabled: Boolean) {
        _isProfileGeneralPostRegisterBtnEnabled.value = enabled
    }

    fun getCommunityUpdateList(postId : Int){
        viewModelScope.launch {
            kotlin.runCatching {
                var data = profileService.getProfileGeneralDetail(postId)
                _communityUpdateData.value = data
            }
        }
    }

    fun modifyCommunityGeneralUpdateData(communityGeneralUpdateViewData: CommunityGeneralUpdateViewData, postID: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                var data = profileService.updateProfileGeneralDetail(postID, communityGeneralUpdateViewData)
            }.onSuccess { result ->
                _dataUpdate.value = true
            }.onFailure { exception ->
                _dataUpdate.value = false
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
}

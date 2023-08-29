package com.example.ugotprototype.ui.community.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralNewGroupViewModel @Inject constructor(
    private val communityService: CommunityService
) : ViewModel() {
    private val _isCommunityGeneralPostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isCommunityGeneralPostRegisterBtnEnabled: LiveData<Boolean> = _isCommunityGeneralPostRegisterBtnEnabled

    private val _communityCreateData = MutableLiveData<CommunityGeneralPostViewData>()
    val communityCreateData: LiveData<CommunityGeneralPostViewData> = _communityCreateData

    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _isCommunityGeneralExists = MutableLiveData<Boolean>()
    val isCommunityGeneralExists: LiveData<Boolean> = _isCommunityGeneralExists

    fun isCommunityGeneralPostRegisterButtonState(enabled: Boolean) {
        _isCommunityGeneralPostRegisterBtnEnabled.value = enabled
    }

    fun setCommunityPostData(communityGeneralPostViewData: CommunityGeneralPostViewData) {
        _communityCreateData.value = communityGeneralPostViewData
    }

    fun sendCommunityGeneralData(communityGeneralPostViewData: CommunityGeneralPostViewData) {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.createCommunity(communityGeneralPostViewData)
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
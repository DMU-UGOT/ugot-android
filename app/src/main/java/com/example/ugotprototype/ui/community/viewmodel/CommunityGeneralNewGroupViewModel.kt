package com.example.ugotprototype.ui.community.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.community.CommunityGeneralNewPostData
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

    private val _communityCreateData = MutableLiveData<CommunityGeneralNewPostData>()
    val communityCreateData: LiveData<CommunityGeneralNewPostData> = _communityCreateData

    private val _etTitle = MutableLiveData<String>()
    val etTitle: LiveData<String> = _etTitle

    private val _etText = MutableLiveData<String>()
    val etText: LiveData<String> = _etText

    private val _createFinish = MutableLiveData<Boolean>()
    val createFinish: LiveData<Boolean> = _createFinish

    fun isCommunityGeneralPostRegisterButtonState(enabled: Boolean) {
        _isCommunityGeneralPostRegisterBtnEnabled.value = enabled
    }

    fun sendCommunityGeneralData(communityGeneralNewPostData: CommunityGeneralNewPostData) {
        viewModelScope.launch {
            kotlin.runCatching {
                communityService.createCommunity(communityGeneralNewPostData)
            }.onSuccess { Log.d("success1", it.toString()) }
                .onFailure { Log.d("success2", it.toString()) }
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

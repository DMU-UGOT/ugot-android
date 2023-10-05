package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ugotprototype.data.api.ChangeService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityChangeUpdateViewModel  @Inject constructor(
    private val changeService: ChangeService
) : ViewModel() {
    private val _isCommunityChangePostRegisterBtnEnabled = MutableLiveData<Boolean>()
    var isCommunityChangePostRegisterBtnEnabled: LiveData<Boolean> =
        _isCommunityChangePostRegisterBtnEnabled
}
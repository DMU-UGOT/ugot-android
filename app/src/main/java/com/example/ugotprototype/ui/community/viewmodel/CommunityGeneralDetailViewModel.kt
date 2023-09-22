package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralDetailViewModel @Inject constructor(
    private val communityService: CommunityService
) : ViewModel() {
    private val _communityDetailData = MutableLiveData<CommunityGeneralPostViewData>()
    val communityDetailData: LiveData<CommunityGeneralPostViewData> = _communityDetailData

    fun getCommunityDetailList(postId:Int){
        viewModelScope.launch {
            kotlin.runCatching {
                val data = communityService.getCommunityDetailGeneral(postId)
                _communityDetailData.value = data
            }
        }
    }
}
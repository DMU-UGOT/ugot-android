package com.example.ugotprototype.ui.community.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.response.CommunityGeneralDataResponse
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.di.api.CommunityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityGeneralViewModel @Inject constructor(
    private val communityService: CommunityService)
: ViewModel() {
    private val _communityGeneralItemList = MutableLiveData<List<CommunityGeneralPostResponse>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityGeneralItemList: LiveData<List<CommunityGeneralPostResponse>> = _communityGeneralItemList

    private val _totalPage = MutableLiveData<Int>()
    var totalPage: LiveData<Int> = _totalPage

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    fun setCurrentPage(data: Int) {
        _currentPage.value = data
    }

    fun setTotalPage(data: Int) {
        _totalPage.value = data
    }

    fun getCommunityList(){
        viewModelScope.launch {
            kotlin.runCatching {
                val pageResponse = communityService.getCommunityGeneral(_currentPage.value!!, 5)
                val communityGeneralData = CommunityGeneralDataResponse(pageResponse)

                _communityGeneralItemList.value = communityGeneralData.data.toList()
            }.onSuccess { Log.d("성공","성공") }
                .onFailure { Log.d("실패2", it.toString()) }
        }
    }
}
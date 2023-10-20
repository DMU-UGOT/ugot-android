package com.example.ugotprototype.ui.community.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.response.CommunityChangePostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeViewModel @Inject constructor(
    private val changeService: ChangeService
) : ViewModel() {
    private val _communityChangeItemList = MutableLiveData<List<CommunityChangePostResponse>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityChangeItemList: LiveData<List<CommunityChangePostResponse>> = _communityChangeItemList

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _totalPage = MutableLiveData<Int>()
    var totalPage: LiveData<Int> = _totalPage

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean> = _isLoadingPage

    val onCmuChangePrevButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! > 1) {
            _currentPage.value = _currentPage.value!! - 1
        }
    }

    val onCmuChangeNextButtonClickListener = View.OnClickListener {
        if (_currentPage.value!! < _totalPage.value!!) {
            _currentPage.value = _currentPage.value!! + 1
        }
    }

    fun setCurrentPage(data: Int) {
        _currentPage.value = data
    }

    fun setTotalPage(data: Int) {
        _totalPage.value = data
    }

    fun getChangeList(){
        viewModelScope.launch {
            _isLoadingPage.value = false

            kotlin.runCatching {
                val pageResponse = changeService.getChange(_currentPage.value!!, 10)
                val communityChangeResponse = pageResponse.data

                _communityChangeItemList.value = communityChangeResponse
                _totalPage.value = pageResponse.pageInfo.totalPages
            }
            _isLoadingPage.value = true
        }
    }
}
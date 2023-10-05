package com.example.ugotprototype.ui.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.change.CommunityMessageData
import com.example.ugotprototype.data.response.CommunityChangePostResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityChangeViewModel @Inject constructor(
    private val changeService: ChangeService,
    private val messageService: MessageService
) : ViewModel() {
    private val _communityChangeItemList = MutableLiveData<List<CommunityChangePostResponse>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityChangeItemList: LiveData<List<CommunityChangePostResponse>> = _communityChangeItemList

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int> = _currentPage

    private val _totalPage = MutableLiveData<Int>()
    var totalPage: LiveData<Int> = _totalPage

    fun setCurrentPage(data: Int) {
        _currentPage.value = data
    }

    fun setTotalPage(data: Int) {
        _totalPage.value = data
    }

    fun getChangeList(){
        viewModelScope.launch {
            kotlin.runCatching {
                val pageResponse = changeService.getChange(_currentPage.value!!, 5)
                val communityChangeResponse = pageResponse.data

                _communityChangeItemList.value = communityChangeResponse
                _totalPage.value = pageResponse.pageInfo.totalPages
            }
        }
    }

    fun sendMessage(content: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                messageService.sendMessage(
                    CommunityMessageData(
                        content = content,
                        receiverName = "미래김"
                    )
                )
            }
        }
    }
}
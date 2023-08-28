package com.example.ugotprototype.ui.community.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.data.community.CommunityMessageData
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.MY_TOKEN_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CommunityChangeViewModel @Inject constructor(private val messageService: MessageService) : ViewModel() {
    private val _communityChangeItemList = MutableLiveData<ArrayList<CommunityChangeViewData>>() // 뷰 모델에서 데이터 처리를 하는 변수
    val communityChangeItemList: LiveData<ArrayList<CommunityChangeViewData>> = _communityChangeItemList

    // 글쓴이 여부를 나타내는 LiveData 변수 추가
    private val _isAuthor = MutableLiveData<Boolean>()
    val isAuthor: LiveData<Boolean> get() = _isAuthor

    // 가시성을 제어할 LiveData 변수 추가
    private val _visibility = MutableLiveData<Int>()

    init {
        _visibility.value = View.GONE // 초기에는 가시성을 GONE으로 설정
    }

    // 글쓴이 여부를 업데이트하는 메소드
    fun setIsAuthor(author: Boolean) {
        _isAuthor.value = author
        _visibility.value = if (author) View.VISIBLE else View.GONE
    }

    fun setCommunityChangeData(communityChangeViewData: ArrayList<CommunityChangeViewData>) {
        processDate(communityChangeViewData)
        _communityChangeItemList.value = communityChangeViewData
    }

    private fun processDate(communityChangeViewData: ArrayList<CommunityChangeViewData>) {
        val currentDate = Calendar.getInstance().time
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val fullDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

        for (item in communityChangeViewData) {
            val isToday = isSameDay(currentDate, item.ComChangeDate)
            if (isToday) {
                // 오늘 날짜인 경우 시간만 표시
                item.formattedDate = timeFormat.format(item.ComChangeDate)
            } else {
                // 오늘이 아닌 경우 년, 월, 일을 전부 표시
                item.formattedDate = fullDateFormat.format(item.ComChangeDate)
            }
        }
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    fun sendMessage(title:String, content:String) {
        viewModelScope.launch{
            kotlin.runCatching {
                messageService.sendMessage(CommunityMessageData(
                    title = title,
                    content = content,
                    receiverName = "미래김"
                ), "Bearer $MY_TOKEN_DATA")
            }
        }
    }
}
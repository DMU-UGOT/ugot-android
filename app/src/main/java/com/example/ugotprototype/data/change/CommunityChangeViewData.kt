package com.example.ugotprototype.data.change

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class CommunityChangeViewData (
    val ComChangeGrade:String,
    val ComChangeNickName:String,
    val ComChangeNowClass:String,
    val ComChangeClass:String,
    var ComChangeExchange:String,
    var ComChangeDate: Date
) {
    // 포맷된 날짜를 표시할 formattedDate 속성을 추가합니다.
    var formattedDate: String = ""
        get() {
            val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
            val dateFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())

            var date = LocalDateTime.now()
            var now = dateFormat.parse(date.format(DateTimeFormatter.ofPattern("yy/MM/dd")))

            if (dateFormat.parse(dateFormat.format(ComChangeDate)).before(now)){
                return dateFormat.format(ComChangeDate)
            }
            return timeFormat.format(ComChangeDate)
        }
}
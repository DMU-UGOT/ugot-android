package com.example.ugotprototype.ui.group.view

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.ugotprototype.databinding.GroupCalendarDayBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth

class DayViewContainer(
    view: View,
) : ViewContainer(view) {
    private val bind = GroupCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay

    fun bind(
        day: CalendarDay, currentMonth: YearMonth, tvMiddle: TextView
    ) {
        this.day = day

        bind.calendarDayText.text = day.date.dayOfMonth.toString()

        if (day.date.month == currentMonth.month) {
            if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
                bind.calendarDayText.setTextColor(Color.parseColor("#ff0000"))
            } else {
                bind.calendarDayText.setTextColor(Color.parseColor("#90636363"))
            }
        } else if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
            bind.calendarDayText.setTextColor(Color.parseColor("#40ff0000"))
        } else {
            bind.calendarDayText.setTextColor(Color.parseColor("#40636363"))
        }

        tvMiddle.text = "${currentMonth.year}년 ${currentMonth.monthValue}월"
    }
}


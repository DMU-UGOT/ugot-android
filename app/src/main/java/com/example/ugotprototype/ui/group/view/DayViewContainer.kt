package com.example.ugotprototype.ui.group.view

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.ugotprototype.databinding.GroupCalendarDayBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class DayViewContainer(
    view: View, private val calendarView: CalendarView, private var vm: GroupViewModel
) : ViewContainer(view) {
    private val bind = GroupCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay
    private lateinit var tvMiddleText: TextView
    private lateinit var currentMonth: YearMonth

    companion object {
        fun clearSelection(calendarView: CalendarView) {
            selectedDate = LocalDate.now()
            calendarView.notifyCalendarChanged()
        }

        private var selectedDate: LocalDate? = null
    }

    init {/*view.setOnClickListener {
            if (day.position == DayPosition.MonthDate && day.date == today || day.date.isAfter(today)) {
                selection = getSelection(clickedDate = day.date, dateSelection = selection)
                calendarView.notifyCalendarChanged()
            }
        }*/
        view.setOnClickListener {
            if (day.position == DayPosition.MonthDate) {
                selectDate(calendarView)
            }
        }

        calendarView.monthScrollListener = {
            updateTitle(it)
        }
    }

    fun bind(
        day: CalendarDay,
        currentMonth: YearMonth,
        tvMiddle: TextView,
        tvYearMonthDay: TextView,
        noticeTitle: Map<LocalDate, String>
    ) {
        this.day = day
        this.tvMiddleText = tvMiddle
        this.currentMonth = currentMonth

        bind.calendarDayText.text = day.date.dayOfMonth.toString()

        if (day.position == DayPosition.MonthDate) {
            if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
                bind.calendarDayText.setTextColor(Color.parseColor("#ff0000"))
            } else {
                bind.calendarDayText.setTextColor(Color.parseColor("#90000000"))
            }
        } else if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
            bind.calendarDayText.setTextColor(Color.parseColor("#40ff0000"))
        } else {
            bind.calendarDayText.setTextColor(Color.parseColor("#40636363"))
        }

        //날짜 중복클릭 처리
        dayOnlyOneClick(tvYearMonthDay)
        setDayTitleNotice(noticeTitle)
    }

    private fun selectDate(calendarView: CalendarView) {
        if (selectedDate != day.date) {
            val oldDate = selectedDate
            selectedDate = day.date
            oldDate?.let { calendarView.notifyDateChanged(it) }
            calendarView.notifyDateChanged(day.date)

            selectedDate?.let { vm.onDateClicked(it) }
        }
    }

    private fun updateTitle(calendarMonth: CalendarMonth) {
        tvMiddleText.text = "${calendarMonth.yearMonth}"
    }

    private fun dayOnlyOneClick(tvYearMonthDay: TextView) {
        if (day.position == DayPosition.MonthDate) {
            when (day.date) {
                selectedDate -> {
                    bind.calendarDayView.setBackgroundColor(Color.parseColor("#202f80ed"))
                    tvYearMonthDay.text = selectedDate.toString()
                }

                else -> {
                    bind.calendarDayView.background = null
                }
            }
        }
    }

    private fun setDayTitleNotice(noticeTitle: Map<LocalDate, String>) {
        if (noticeTitle.containsKey(day.date)) {
            bind.tvTitleCalendar.text = noticeTitle[day.date]
            bind.calendarDayView.setBackgroundColor(Color.parseColor("#202f80ed"))
        }

    }
}
/*@SuppressLint("ResourceAsColor")
private fun configureBinders() {

    val textView = bind.calendarDayText
    val roundBgView = bind.calendarDayRoundBackgroundView
    val continuousBgView = bind.calendarDayView

    textView.text = null
    roundBgView.makeInVisible()
    continuousBgView.makeInVisible()

    val (startDate, endDate) = selection

    val clipLevelHalf = 5000
    val ctx = view.context
    val rangeStartBackground = ctx.getDrawableCompat(R.drawable.ic_group_calendar_start).also {
        it.level = clipLevelHalf
    }
    val rangeEndBackground = ctx.getDrawableCompat(R.drawable.ic_group_calendar_end).also {
        it.level = clipLevelHalf
    }
    val rangeMiddleBackground = ctx.getDrawableCompat(R.drawable.ic_group_calendar_middle)
    val singleBackground = ctx.getDrawableCompat(R.drawable.ic_group_calendar_single)

    when (day.position) {
        DayPosition.MonthDate -> {
            textView.text = day.date.dayOfMonth.toString()
            if (day.date.isBefore(today)) {
                textView.setTextColorRes(R.color.color_50636363)
                if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
                    textView.setTextColorRes(R.color.color_50ff0000)
                }
            } else {
                when {
                    startDate == day.date && endDate == null -> {
                        Log.d("test", "$startDate")
                        Log.d("test", "$endDate")
                        textView.setTextColorRes(R.color.white)
                        roundBgView.applyBackground(singleBackground)
                    }

                    day.date == startDate -> {
                        Log.d("test", "$startDate")
                        Log.d("test", "$endDate")
                        textView.setTextColorRes(R.color.white)
                        continuousBgView.applyBackground(rangeStartBackground)
                        roundBgView.applyBackground(singleBackground)
                    }

                    startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                        Log.d("test", "$startDate")
                        Log.d("test", "$endDate")
                        textView.setTextColorRes(R.color.black)
                        if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
                            textView.setTextColorRes(R.color.color_ff0000)
                        }
                        continuousBgView.applyBackground(rangeMiddleBackground)
                    }

                    day.date == endDate -> {
                        textView.setTextColorRes(R.color.white)
                        continuousBgView.applyBackground(rangeEndBackground)
                        roundBgView.applyBackground(singleBackground)
                    }

                    day.date.dayOfWeek == DayOfWeek.SUNDAY -> {
                        textView.setTextColorRes(R.color.color_ff0000)
                    }

                    else -> textView.setTextColorRes(R.color.black)
                }
            }
        }

        DayPosition.InDate -> {
            if ((startDate != null) && (endDate != null) && isInDateBetweenSelection(
                    day.date, startDate, endDate
                )
            ) {
                continuousBgView.applyBackground(rangeMiddleBackground)
            }
        }

        DayPosition.OutDate -> {
            if (startDate != null && endDate != null && isOutDateBetweenSelection(
                    day.date, startDate, endDate
                )
            ) {
                continuousBgView.applyBackground(rangeMiddleBackground)
            }
        }
    }
}
 */
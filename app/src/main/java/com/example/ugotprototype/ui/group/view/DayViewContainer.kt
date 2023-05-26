package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.GroupCalendarDayBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.sample.shared.ContinuousSelectionHelper.getSelection
import com.kizitonwose.calendar.sample.shared.ContinuousSelectionHelper.isInDateBetweenSelection
import com.kizitonwose.calendar.sample.shared.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.kizitonwose.calendar.sample.shared.DateSelection
import com.kizitonwose.calendar.sample.view.getDrawableCompat
import com.kizitonwose.calendar.sample.view.makeInVisible
import com.kizitonwose.calendar.sample.view.makeVisible
import com.kizitonwose.calendar.sample.view.setTextColorRes
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date

class DayViewContainer(
    view: View, calendarView: CalendarView
) : ViewContainer(view) {
    private val bind = GroupCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay
    private val today = LocalDate.now()

    companion object {
        private var selection = DateSelection()
    }

    init {
        view.setOnClickListener {
            if (day.position == DayPosition.MonthDate && day.date == today || day.date.isAfter(today)) {
                selection = getSelection(clickedDate = day.date, dateSelection = selection)
                calendarView.notifyCalendarChanged()
            }
        }
        calendarView.monthScrollListener?.let {
            Log.d("test", "test")
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(
        day: CalendarDay, currentMonth: YearMonth, tvMiddle: TextView
    ) {
        this.day = day
        bind.calendarDayText.text = day.date.dayOfMonth.toString()
        tvMiddle.text = "${currentMonth.year}년 ${currentMonth.monthValue}월"
        configureBinders()
    }

    @SuppressLint("ResourceAsColor")
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

    private fun View.applyBackground(drawable: Drawable) {
        makeVisible()
        background = drawable
    }
}


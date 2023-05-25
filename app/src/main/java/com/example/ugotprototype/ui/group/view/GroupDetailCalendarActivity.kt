package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailCalendarBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.YearMonth

class GroupDetailCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailCalendarBinding
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail_calendar)

        binding.ibCalendarPrev.setOnClickListener {
            finish()
        }

        spawnCalendarView()
    }

    private fun spawnCalendarView() {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val daysOfWeek = daysOfWeek()

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        updateCalendar(currentMonth)

        goToPreviousMonth()
        goToNextMonth()

        observeUpdate()
    }

    private fun goToPreviousMonth() {
        binding.ivGroupDetailPrevMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.yearMonth?.let { currentMonth ->
                groupViewModel.setCurrentMonth(currentMonth.minusMonths(1))
            }
        }
    }

    private fun goToNextMonth() {
        binding.ivGroupDetailNextMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.yearMonth?.let { currentMonth ->
                groupViewModel.setCurrentMonth(currentMonth.plusMonths(1))
            }
        }
    }

    private fun updateCalendar(currentMonth: YearMonth) {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) =
                container.bind(data, currentMonth, binding.tvMiddleTitle)

            override fun create(view: View): DayViewContainer = DayViewContainer(view)
        }
    }

    private fun observeUpdate() {
        groupViewModel.currentMonth.observe(this) { currentMonth ->
            currentMonth?.let {
                observeDataSet(it)
            }
        }
    }

    private fun observeDataSet(currentMonth: YearMonth) {
        updateCalendar(currentMonth)
        binding.calendarView.scrollToMonth(currentMonth)
    }
}
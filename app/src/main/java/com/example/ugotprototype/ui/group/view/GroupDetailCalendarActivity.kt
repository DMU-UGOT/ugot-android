package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailCalendarBinding
import com.example.ugotprototype.ui.group.adapter.GroupCalendarRecyclerViewAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupCalendarViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class GroupDetailCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailCalendarBinding
    private val viewModel: GroupCalendarViewModel by viewModels()
    private var now = LocalDate.now()
    private var groupCalendarAdapter: GroupCalendarRecyclerViewAdapter =
        GroupCalendarRecyclerViewAdapter()
    private var noticeTitle: Map<LocalDate, String> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail_calendar)
        binding.rvNoticeDetail.adapter = groupCalendarAdapter

        viewModel.getNoticeData(intent.getIntExtra(GROUP_ID, 0))

        viewModel.groupNoticeDetail.observe(this) {
            viewModel.updateNoticeTitle(it)
            viewModel.onDateClicked(now)
        }

        viewModel.noticeTitle.observe(this) {
            updateCalendar(YearMonth.now(), it)
        }

        viewModel.oneDayNoticeData.observe(this) {
            groupCalendarAdapter.setFilterData(it)
        }

        viewModel.selectedDate.observe(this) {
            viewModel.getClickDateData(it.toString())
        }

        binding.ibCalendarPrev.setOnClickListener {
            finish()
        }

        spawnCalendarView()
        DayViewContainer.clearSelection(binding.calendarView)
    }

    @SuppressLint("SetTextI18n")
    private fun spawnCalendarView() {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val daysOfWeek = daysOfWeek()

        binding.tvYearMonthDay.text = "${now.year}-${String.format("%02d", now.monthValue)}-${
            String.format(
                "%02d", now.dayOfMonth
            )
        }"

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)


        goToPreviousMonth()
        goToNextMonth()
        updateCalendar(currentMonth, noticeTitle)
        observeUpdate()

    }

    private fun goToPreviousMonth() {
        binding.ivGroupDetailPrevMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.yearMonth?.let { currentMonth ->
                viewModel.setCurrentMonth(currentMonth.minusMonths(1))
            }
        }
    }

    private fun goToNextMonth() {
        binding.ivGroupDetailNextMonth.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.yearMonth?.let { currentMonth ->
                viewModel.setCurrentMonth(currentMonth.plusMonths(1))
            }
        }
    }

    private fun updateCalendar(currentMonth: YearMonth, noticeTitle: Map<LocalDate, String>) {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) = container.bind(
                data, currentMonth, binding.tvMiddleTitle, binding.tvYearMonthDay, noticeTitle
            )

            override fun create(view: View): DayViewContainer =
                DayViewContainer(view, binding.calendarView, viewModel)
        }
    }

    private fun observeUpdate() {
        viewModel.currentMonth.observe(this) { currentMonth ->
            currentMonth?.let {
                observeDataSet(it)
            }
        }
    }

    private fun observeDataSet(currentMonth: YearMonth) {
        binding.calendarView.scrollToMonth(currentMonth)
    }


    override fun onDestroy() {
        super.onDestroy()
        DayViewContainer.clearSelection(binding.calendarView)
        viewModel.getClickDateData(now.toString())
    }
}
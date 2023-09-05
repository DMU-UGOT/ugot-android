package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.databinding.ActivityGroupDetailCalendarBinding
import com.example.ugotprototype.ui.group.adapter.GroupCalendarRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class GroupDetailCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailCalendarBinding
    private val groupViewModel: GroupViewModel by viewModels()
    private var now = LocalDate.now()
    private var groupCalendarAdapter: GroupCalendarRecyclerViewAdapter =
        GroupCalendarRecyclerViewAdapter()
    private var groupCalendarItems = ArrayList<GroupCalendarData>()
    private var noticeTitle: Map<LocalDate, String> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail_calendar)
        binding.rvNoticeDetail.adapter = groupCalendarAdapter

        binding.ibCalendarPrev.setOnClickListener {
            finish()
        }

        spawnCalendarView()

        DayViewContainer.clearSelection(binding.calendarView)
        adapterDataAdd(now)
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

        setCalendarView()

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
            override fun bind(container: DayViewContainer, data: CalendarDay) = container.bind(
                data, currentMonth, binding.tvMiddleTitle, binding.tvYearMonthDay, noticeTitle
            )

            override fun create(view: View): DayViewContainer =
                DayViewContainer(view, binding.calendarView, groupViewModel)
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

    private fun setCalendarView() {

        //데이터 추가
        groupCalendarItems = arrayListOf(
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "두 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "세 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "네 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "다섯 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "여섯 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "일곱 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "여덟 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-27", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-21", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-09", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-05", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
            GroupCalendarData(
                LocalDate.parse(
                    "2023-05-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ), "첫 번째 공지사항"
            ),
        )
        groupViewModel.setCalendarDataForDate(groupCalendarItems)

        // 클릭한 날짜가 변경되면 아래로직실행
        groupViewModel.selectedDate.observe(this) {
            adapterDataAdd(it)
        }

        // 달력 날짜 숫자 밑에 표기되는 공지사항
        noticeTitle = groupViewModel.groupNoticeDetail.value?.groupBy { it.date }
            ?.mapValues { entry -> entry.value.last().groupNoticeDetail }!!
    }

    private fun adapterDataAdd(local: LocalDate) {
        groupViewModel.groupNoticeDetail.value?.let {
            groupCalendarAdapter.setFilterData(
                it, local
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DayViewContainer.clearSelection(binding.calendarView)
        adapterDataAdd(now)
    }
}
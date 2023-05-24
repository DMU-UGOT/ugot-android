package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailCalendarBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

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

        setTitleDay(daysOfWeek)
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

    private fun setTitleDay(daysOfWeek: List<DayOfWeek>) {
        val titleContainer: ViewGroup = binding.titlesContainer as ViewGroup
        titleContainer.children.map { it as TextView }.forEachIndexed { index, textView ->
            val dayOfWeek = daysOfWeek[index]
            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA)
            textView.text = title
            textView.setTextColor(if (index == 0) Color.RED else Color.BLACK)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCalendar(currentMonth: YearMonth) {
        val dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.dayTextView.text = data.date.dayOfMonth.toString()
                if (data.date.month == currentMonth.month) {
                    container.dayTextView.setTextColor(Color.parseColor("#90636363"))
                } else {
                    container.dayTextView.setTextColor(Color.parseColor("#40636363"))
                }
            }
        }
        binding.calendarView.dayBinder = dayBinder
        binding.calendarView.scrollToMonth(currentMonth)
        binding.tvMiddleTitle.text = "${currentMonth.year}년 ${currentMonth.monthValue}월"
    }

    private fun observeUpdate() {
        groupViewModel.currentMonth.observe(this) { currentMonth ->
            currentMonth?.let { updateCalendar(it) }
        }
    }
}
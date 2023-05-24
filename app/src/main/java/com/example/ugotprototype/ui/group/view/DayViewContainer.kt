package com.example.ugotprototype.ui.group.view

import android.view.View
import android.widget.TextView
import com.example.ugotprototype.R
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val dayTextView: TextView = view.findViewById(R.id.calendarDayText)
}
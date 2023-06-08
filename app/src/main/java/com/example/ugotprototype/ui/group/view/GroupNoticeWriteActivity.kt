package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupNoticeWriteBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import java.util.Calendar

class GroupNoticeWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupNoticeWriteBinding
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_notice_write)
        binding.ivCheck.isEnabled = false

        binding.tvSelectedDay.setOnClickListener{
            showDatePicker()
        }

        postWriteFinish()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // 선택된 날짜 처리
                val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.tvSelectedDay.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun postWriteFinish() {

        val totalListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldsAndUpdateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}

        }

        binding.tvSelectedDay.addTextChangedListener(totalListener)
        binding.tvNoticeTitle.addTextChangedListener(totalListener)

        groupViewModel.isNoticeWriteButtonState.observe(this) {
            binding.ivCheck.isEnabled = it
        }

        binding.ivCheck.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("resultText", binding.tvNoticeTitle.text.toString())
                putExtra("yearMonthDay", binding.tvSelectedDay.text.toString())
            })
            finish()
        }
    }

    private fun checkFieldsAndUpdateButtonState() {
        if(binding.tvNoticeTitle.length() != 0 && binding.tvSelectedDay.length() != 0) {
            groupViewModel.isNoticeWriteStateButton(true)
        } else {
            groupViewModel.isNoticeWriteStateButton(false)
        }
    }
}
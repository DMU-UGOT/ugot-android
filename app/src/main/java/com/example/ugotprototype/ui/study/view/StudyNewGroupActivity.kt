package com.example.ugotprototype.ui.study.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudyNewGroupBinding
import com.example.ugotprototype.ui.study.viewmodel.StudyViewModel

class StudyNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyNewGroupBinding
    private val studyViewModel: StudyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_new_group)

        studyViewModel.studyMaxPersonnel.observe(this) { maxPersonnel ->
            binding.tvStNewMaxNumber.text = maxPersonnel.toString() + "명"
        }

        spinnerMeetChoice()
        studyMaxPersonnel()
        backStudyNewToMainActivity()
    }

    private fun spinnerMeetChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.study_new_group_meet, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spStMeet.adapter = adapter
    }

    private fun studyMaxPersonnel() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                studyViewModel.studyMaxPersonnel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun backStudyNewToMainActivity() {

        binding.btStNewPostRegister.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btStNewBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
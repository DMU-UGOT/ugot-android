package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding

class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_school)

        spinnerGradePostSet()
        spinnerClassPostSet()
        spinnerGenderPostSet()
        backProfileSchoolNewToMainActivity()
    }


    private fun spinnerGradePostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_grade_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGrade.adapter = adapter
    }

    private fun spinnerClassPostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_class_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spClass.adapter = adapter
    }

    private fun spinnerGenderPostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_gender_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGender.adapter = adapter
    }

    private fun backProfileSchoolNewToMainActivity() {
        binding.btSchoolNewPostRegister.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btSchoolNewBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
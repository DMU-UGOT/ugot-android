package com.example.ugotprototype.ui.profile.view

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

        saveSchool()
        spinnerGradePostSet()
        spinnerClassPostSet()
        spinnerSexPostSet()
    }

    private fun saveSchool(){
        binding.btSchoolSave.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
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

    private fun spinnerSexPostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_sex_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spSex.adapter = adapter
    }
}
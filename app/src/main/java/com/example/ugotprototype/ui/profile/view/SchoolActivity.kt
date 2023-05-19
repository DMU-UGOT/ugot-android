package com.example.ugotprototype.ui.profile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding

class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_school)

    }

    private fun saveSchool(){
        binding.btSchoolSave.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
    }
}
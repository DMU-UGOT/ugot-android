package com.example.ugotprototype.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding

class Activity_profile_school : AppCompatActivity() {
    private lateinit var binding : ActivityProfileSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_school)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_school)




// 학교 정보 저장
        binding.profileSchoolSaveBtn.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
    }
}
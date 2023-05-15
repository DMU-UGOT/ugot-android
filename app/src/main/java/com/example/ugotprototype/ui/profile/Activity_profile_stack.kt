package com.example.ugotprototype.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileStackBinding

class Activity_profile_stack : AppCompatActivity(){
    private lateinit var binding : ActivityProfileStackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_stack)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_stack)


// 스택 저장
        binding.profileStackSaveBtn.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }

    }
}
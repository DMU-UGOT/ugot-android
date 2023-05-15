package com.example.ugotprototype.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyBinding

class Activity_profile_my : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_my)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my)


// 개인 프로필 저장
        binding.profileMySaveBtn.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
    }
}
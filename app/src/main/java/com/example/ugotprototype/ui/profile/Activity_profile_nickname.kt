package com.example.ugotprototype.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileNicknameBinding

class Activity_profile_nickname : AppCompatActivity() {
    private lateinit var binding: ActivityProfileNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_nickname)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_nickname)


// 닉네임 저장
        binding.profileNicknameSaveBtn.setOnClickListener {
            startActivity(Intent(this, ProfileFragment::class.java))
            finish()
        }
    }
}
package com.example.ugotprototype.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentProfileBinding

class ProfileFragment : AppCompatActivity() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.fragment_profile)


// 학교 정보 확인
        binding.profileSchoolBtn.setOnClickListener {
            startActivity(Intent(this, SchoolActivity::class.java))
            finish()
        }


// 사용 기술
        binding.profileStackBtn.setOnClickListener {
            startActivity(Intent(this, StackActivity::class.java))
            finish()
        }

// 회원 탈퇴
        binding.profileLogoutBtn.setOnClickListener {


        }

// 로그 아웃
        binding.profileLogoutBtn.setOnClickListener {


        }

// 알림 설정
        binding.profileAlarmOptionBtn.setOnClickListener {

        }

// 채팅 설정
        binding.profileChatOptionBtn.setOnClickListener {

        }

    }
}
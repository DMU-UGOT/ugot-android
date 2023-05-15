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
            startActivity(Intent(this, Activity_profile_school::class.java))
            finish()
        }

// 닉네임 변경
        binding.profileNicknameBtn.setOnClickListener {
            startActivity(Intent(this, Activity_profile_nickname::class.java))
            finish()
        }

// 개인정보 변경
        binding.profileModifyBtn.setOnClickListener {
            startActivity(Intent(this, Activity_profile_my::class.java))
            finish()
        }

// 사용 기술
        binding.profileStackBtn.setOnClickListener {
            startActivity(Intent(this, Activity_profile_stack::class.java))
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
            startActivity(Intent(this, Activity_profile_alarm::class.java))
            finish()
        }

// 채팅 설정
        binding.profileChatOptionBtn.setOnClickListener {
            startActivity(Intent(this, Activity_profile_chatting::class.java))
            finish()
        }

    }
}
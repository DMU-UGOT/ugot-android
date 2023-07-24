package com.example.ugotprototype.ui.profile.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    // 초기화
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    // 화면 전환
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToSchool()
        goToStack()
        goToMessage()
    }

    // 개인 정보 페이지 이동
    private fun goToSchool() {
        binding.layoutProfileSchool.setOnClickListener {
            startActivity(Intent(requireActivity(), SchoolActivity::class.java))
        }
    }

    // 스택 페이지 이동
    private fun goToStack() {
        binding.layoutProfileStack.setOnClickListener {
            startActivity(Intent(requireActivity(), StackActivity::class.java))
        }
    }

    // 쪽지 관리 페이지 이동
    private fun goToMessage() {
        binding.layoutProfileMessage.setOnClickListener {
            startActivity(Intent(requireActivity(), MessageActivity::class.java))
        }
    }
}
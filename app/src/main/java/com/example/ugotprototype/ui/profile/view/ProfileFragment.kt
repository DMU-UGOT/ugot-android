package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.R
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.databinding.FragmentProfileBinding
import com.example.ugotprototype.ui.login.view.LoginActivity
import com.example.ugotprototype.ui.login.view.LoginActivity.Companion.USER_LOGIN_TYPE
import com.example.ugotprototype.ui.profile.viewmodel.ProfileFragmentViewModel
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var sharedPreference: SharedPreference
    private val goToSearchResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.profileGetUserInfo()
            }
        }

    // 초기화
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPreference = SharedPreference(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    // 화면 전환
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileGetUserInfo()

        viewModel.isDeleteAccount.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        viewModel.profileMemberData.observe(viewLifecycleOwner) {
            profileViewSetting(it)
        }

        goToSchool()
        goToStack()
        goToMessage()
        goToMyPost()
        goToMyBookMark()
        deleteUser()
        oAuthLogOut()
    }

    // 개인 정보 페이지 이동
    private fun goToSchool() {
        binding.layoutProfileSchool.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(
                    requireContext(), SchoolActivity::class.java
                )
            )
        }
    }

    // 스택 페이지 이동
    private fun goToStack() {
        binding.layoutProfileStack.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(
                    requireContext(), StackActivity::class.java
                )
            )
        }
    }

    // 쪽지 관리 페이지 이동
    private fun goToMessage() {
        binding.layoutProfileMessage.setOnClickListener {
            startActivity(Intent(requireActivity(), MessageActivity::class.java))
        }
    }

    private fun deleteUser() {
        binding.layoutProfileCheckout.setOnClickListener {
            showDeleteUserConfirmationDialog()
        }
    }

    private fun goToMyPost() {
        binding.layoutMypost.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileMyPostActivity::class.java))
        }
    }

    private fun goToMyBookMark() {
        binding.layoutMybookmark.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileMyBookmarkActivity::class.java))
        }
    }

    private fun profileViewSetting(data: ProfileMemberData) {
        with(binding) {
            tvFgName.text = data.nickname
            tvFgEmail.text = data.email
            tvFgProfileStackInput.text = data.skill.toString()
        }
    }

    private fun oAuthLogOut() {
        setUpNaverLogin()

        binding.layoutProfileLogout.setOnClickListener {
            showLogOutConfirmationDialog()
        }
    }

    private fun setUpNaverLogin() {
        NaverIdLoginSDK.initialize(
            context = requireContext(),
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
            clientName = BuildConfig.NAVER_CLIENT_ID
        )
    }


    private fun showDeleteUserConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("정말로 회원 탈퇴하시겠습니까? \n회원 탈퇴 후에는 모든 데이터가 삭제되며 회원 복구가 불가능합니다.")
            .setPositiveButton("확인") { _, _ ->
                viewModel.deleteUser()
            }.setNegativeButton("취소", null).create()

        alertDialog.show()
    }

    private fun showLogOutConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                logout()
            }.setNegativeButton("취소", null).create()

        alertDialog.show()
    }

    private fun logout() {
        if (USER_LOGIN_TYPE == "네이버") {
            NaverIdLoginSDK.logout()
        } else if (USER_LOGIN_TYPE == "카카오") {
            UserApiClient.instance.logout {}
        }

        sharedPreference.saveMemberId(0)
        sharedPreference.saveToken("")
        sharedPreference.saveAutoLogin(false)
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}
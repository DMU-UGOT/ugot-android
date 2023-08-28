package com.example.ugotprototype.ui.sign.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivitySignNoEmailBinding
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignNoEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignNoEmailBinding
    private val signViewModel: SignViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_no_email)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostSign) as? NavHostFragment
        navController = navHostFragment?.navController ?: return

        binding.ivSignPrev.setOnClickListener {
            if (signViewModel.currentFragmentIndex.value!! > 0) {
                signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! - 1)
                navController.popBackStack()
                binding.mbNext.text = "다음"
            }
        }

        binding.mbNext.setOnClickListener {
            if (signViewModel.currentFragmentIndex.value!! < 9) {
                navigateToNextFragment(signViewModel.currentFragmentIndex.value!!)
            }
        }

        signViewModel.onSignUpCompleted.observe(this) {
            if(it) { signViewModel.attemptLogin() }
        }

        signViewModel.onSignInCompleted.observe(this) {
            if(it) { startActivity(Intent(this, MainActivity::class.java)) }
        }

        signViewModel.currentFragmentIndex.observe(this) {
            binding.pbNowpage.progress = it
        }
    }

    private fun navigateToNextFragment(currentFragmentIndex: Int) {
        when (currentFragmentIndex) {
            0 -> {
                if (signViewModel.isNickNameValid()) {
                    navController.navigate(R.id.action_sign_input_name_to_sign_input_real_name)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            1 -> {
                if (signViewModel.isRealNameValid()) {
                    navController.navigate(R.id.action_sign_input_real_name_to_sign_input_email)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 이름의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            2 -> {
                if (signViewModel.isEmailValid()) {
                    navController.navigate(R.id.action_sign_input_email_to_sign_input_major)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 이메일의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            3 -> {
                if (signViewModel.isMajorValid()) {
                    navController.navigate(R.id.action_sign_input_major_to_sign_input_grade)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "전공이 선택되지 않았습니다", Toast.LENGTH_SHORT).show()
                }
            }

            4 -> {
                if (signViewModel.isNowGradeValid()) {
                    navController.navigate(R.id.action_sign_input_grade_to_sign_input_class)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            5 -> {
                if (signViewModel.isNowClassValid()) {
                    navController.navigate(R.id.action_sign_input_class_to_sign_input_skill)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            6 -> {
                if (signViewModel.isSelectedChipTextsValid()) {
                    navController.navigate(R.id.action_sign_input_skill_to_sign_input_github)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "1개 이상의 기술 스택을 선택해야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }

            7 -> {
                signViewModel.checkGitHubAccount { success ->
                    if (success) {
                        navController.navigate(R.id.action_sign_input_github_to_sign_input_blog)
                        signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                        binding.mbNext.text = "회원가입"
                    } else {
                        Toast.makeText(this, "해당 깃허브 계정은 존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            8 -> {
                if(signViewModel.isBlogValid()) {
                    signUpCheck()
                } else {
                    Toast.makeText(this, "블로그 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signUpCheck() {
        signViewModel.signUp()
    }
}
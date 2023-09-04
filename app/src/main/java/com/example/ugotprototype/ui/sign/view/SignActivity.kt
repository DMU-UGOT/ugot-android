package com.example.ugotprototype.ui.sign.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivitySignBinding
import com.example.ugotprototype.ui.login.view.LoginActivity
import com.example.ugotprototype.ui.login.view.LoginActivity.Companion.LOGIN_EMAIL
import com.example.ugotprototype.ui.login.view.LoginActivity.Companion.LOGIN_REAL_NAME
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel
import com.example.ugotprototype.ui.sign.viewmodel.SignViewModel.Companion.LOGIN_TYPE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private val signViewModel: SignViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var loginType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign)

        loginType = intent.getStringExtra(LOGIN_TYPE).toString()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostSign) as? NavHostFragment
        navController = navHostFragment?.navController ?: return

        signViewModel.setEmail(intent.getStringExtra(LOGIN_EMAIL).toString())
        signViewModel.setRealname(intent.getStringExtra(LOGIN_REAL_NAME).toString())

        binding.ivSignPrev.setOnClickListener {
            if (signViewModel.currentFragmentIndex.value!! > 0) {
                signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! - 1)
                navController.popBackStack()
                binding.mbNext.text = "다음"
            }
        }

        binding.mbNext.setOnClickListener {
            if (signViewModel.currentFragmentIndex.value!! < 7) {
                navigateToNextFragment(signViewModel.currentFragmentIndex.value!!)
            }
        }

        signViewModel.onSignUpCompleted.observe(this) {
            if (it) {
                if (loginType == "카카오") {
                    signViewModel.attemptLogin()
                } else if (loginType == "네이버") {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }

        signViewModel.onSignInCompleted.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        signViewModel.currentFragmentIndex.observe(this) {
            binding.pbNowpage.progress = it
        }
    }

    private fun navigateToNextFragment(currentFragmentIndex: Int) {
        when (currentFragmentIndex) {
            0 -> {
                if (signViewModel.isNickNameValid()) {
                    navController.navigate(R.id.action_sign_input_name_to_sign_input_major)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            1 -> {
                if (signViewModel.isMajorValid()) {
                    navController.navigate(R.id.action_sign_input_major_to_sign_input_grade)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "전공이 선택되지 않았습니다", Toast.LENGTH_SHORT).show()
                }
            }

            2 -> {
                if (signViewModel.isNowGradeValid()) {
                    navController.navigate(R.id.action_sign_input_grade_to_sign_input_class)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            3 -> {
                if (signViewModel.isNowClassValid()) {
                    navController.navigate(R.id.action_sign_input_class_to_sign_input_skill)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "입력된 데이터의 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            4 -> {
                if (signViewModel.isSelectedChipTextsValid()) {
                    navController.navigate(R.id.action_sign_input_skill_to_sign_input_github)
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
                } else {
                    Toast.makeText(this, "2개의 기술 스택을 체크해야합니다.", Toast.LENGTH_SHORT).show()
                }
            }

            5 -> {
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

            6 -> {
                if (signViewModel.isBlogValid()) {
                    signUpCheck()
                    signViewModel.setCurrentFragmentIndex(signViewModel.currentFragmentIndex.value!! + 1)
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
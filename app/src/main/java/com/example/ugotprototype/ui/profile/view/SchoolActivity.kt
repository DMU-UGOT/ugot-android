package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding

class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_school)

        spinnerGradePostSet()
        spinnerClassPostSet()
        spinnerGenderPostSet()
        backProfileSchoolNewToMainActivity()
    }


    private fun spinnerGradePostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_grade_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGrade.adapter = adapter
    }

    private fun spinnerClassPostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_class_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spClass.adapter = adapter
    }

    private fun spinnerGenderPostSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.school_gender_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGender.adapter = adapter
    }

    private fun backProfileSchoolNewToMainActivity() {
        binding.btSchoolNewPostRegister.setOnClickListener {
            val checkSchoolGrade = binding.spGrade.selectedItem.toString()
            val checkSchoolClass = binding.spClass.selectedItem.toString()
            val checkNickName = binding.etNicknameInput.text.toString()
            val checkGender = binding.spGender.selectedItem.toString()
            val checkEmail = binding.etEmailInput.text.toString()
            val checkGit = binding.etGitInput.text.toString()

            if (checkSchoolGrade.isEmpty() || checkSchoolGrade == "미선택") {
                Toast.makeText(applicationContext, "학년을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkSchoolClass.isEmpty() || checkSchoolClass == "미선택") {
                Toast.makeText(applicationContext, "분반을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkNickName.isEmpty()) {
                Toast.makeText(applicationContext, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkGender.isEmpty() || checkGender == "미선택") {
                Toast.makeText(applicationContext, "성별을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkEmail.isEmpty()) {
                Toast.makeText(applicationContext, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkGit.isEmpty()) {
                Toast.makeText(applicationContext, "깃주소를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
                Intent().putExtra("resultText", "text")
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btSchoolNewBackToMain.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        if (isFinishing) {
            return
        }

        val dialogBinding = ActivityDialogMessageBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btDialogYes.setOnClickListener {
            // 예 버튼 클릭 시 동작
            setResult(Activity.RESULT_OK, Intent())
            finish()
            alertDialog.dismiss()
        }

        dialogBinding.btDialogNo.setOnClickListener {
            // 아니오 버튼 클릭 시 동작
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}
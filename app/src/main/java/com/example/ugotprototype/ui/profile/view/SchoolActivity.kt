package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding
import com.example.ugotprototype.ui.profile.viewmodel.SchoolActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSchoolBinding
    private val schoolActivityViewModel: SchoolActivityViewModel by viewModels()
    private lateinit var gradeSpinnerItems: List<String>
    private lateinit var classSpinnerItems: List<String>
    private lateinit var memberData: ProfileMemberData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_school)

        spinnerGradePostSet()
        spinnerClassPostSet()

        schoolActivityViewModel.initData()

        schoolActivityViewModel.memberData.observe(this) {
            initData(it)
        }

        schoolActivityViewModel.userInfoValid.observe(this) {
            if (it) {
                schoolActivityViewModel.patchMemberData(memberData)
            } else {
                Toast.makeText(
                    applicationContext,
                    "입력된 데이터의 형식이 올바르지 않거나 깃허브 계정이 존재하지 않습니다ㅁㄴ.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        schoolActivityViewModel.isMemberDataPatch.observe(this) {
            if (it) {
                finish()
            }
        }

        binding.btSchoolNewPostRegister.setOnClickListener {
            setMemberData()
        }

        binding.btSchoolNewBackToMain.setOnClickListener {
            finish()
        }
    }

    private fun spinnerGradePostSet() {
        gradeSpinnerItems = resources.getStringArray(R.array.school_grade_item).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gradeSpinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGrade.adapter = adapter
    }

    private fun spinnerClassPostSet() {
        classSpinnerItems = resources.getStringArray(R.array.school_class_item).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classSpinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spClass.adapter = adapter
    }

    private fun initData(profileMemberData: ProfileMemberData) {
        with(binding) {
            spGrade.setSelection(gradeSpinnerItems.indexOf(profileMemberData.grade.toString()))
            spClass.setSelectionIgnoreCase(classSpinnerItems, profileMemberData._class)
            etEmailInput.setText(profileMemberData.email)
            etGitInput.setText(profileMemberData.gitHubLink.split("/").lastOrNull())
            etNicknameInput.setText(profileMemberData.nickname)
            etNameInput.setText(profileMemberData.name)
        }
    }

    private fun Spinner.setSelectionIgnoreCase(items: List<String>, target: String) {
        val position = items.indexOfFirst { it.equals(target, ignoreCase = true) }
        if (position >= 0) {
            setSelection(position)
        }
    }

    private fun setMemberData() {
        memberData = ProfileMemberData(
            name = binding.etNameInput.text.toString(),
            nickname = binding.etNicknameInput.text.toString(),
            email = binding.etEmailInput.text.toString(),
            grade = binding.spGrade.selectedItem.toString().toInt(),
            _class = binding.spClass.selectedItem.toString(),
            gitHubLink = binding.etGitInput.text.toString(),
            personalBlogLink = schoolActivityViewModel.memberData.value!!.personalBlogLink,
            skill = schoolActivityViewModel.memberData.value!!.skill,
            major = schoolActivityViewModel.memberData.value!!.major
        )

        schoolActivityViewModel.isValidateCheck(memberData)
    }
}
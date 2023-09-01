package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.profile.ProfileMemberData
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.databinding.ActivityProfileSchoolBinding
import com.example.ugotprototype.ui.profile.viewmodel.SchoolActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileSchoolBinding
    private val schoolActivityViewModel: SchoolActivityViewModel by viewModels()
    private lateinit var gradeSpinnerItems: List<String>
    private lateinit var classSpinnerItems: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_school)

        spinnerGradePostSet()
        spinnerClassPostSet()

        schoolActivityViewModel.initData()

        schoolActivityViewModel.memberData.observe(this) {
            initData(it)
        }

        schoolActivityViewModel.isMemberDataPatch.observe(this) {
            if(it) {
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
        schoolActivityViewModel.patchMemberData(
            ProfileMemberData(
                name = binding.etNameInput.text.toString(),
                nickname = binding.etNicknameInput.text.toString(),
                email = binding.etEmailInput.text.toString(),
                grade = binding.spGrade.selectedItem.toString().toInt(),
                _class = binding.spClass.selectedItem.toString(),
                gitHubLink = "https://github.com/" + binding.etGitInput.text.toString(),
                personalBlogLink = schoolActivityViewModel.memberData.value!!.personalBlogLink,
                skill = schoolActivityViewModel.memberData.value!!.skill,
                major = schoolActivityViewModel.memberData.value!!.major
            )
        )
    }
}
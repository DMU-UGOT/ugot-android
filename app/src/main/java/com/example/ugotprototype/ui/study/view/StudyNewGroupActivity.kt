package com.example.ugotprototype.ui.study.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.study.StudySetPost
import com.example.ugotprototype.databinding.ActivityStudyNewGroupBinding
import com.example.ugotprototype.ui.study.viewmodel.StudyPostWriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyNewGroupBinding
    private val studyViewModel: StudyPostWriteViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_new_group)
        binding.lifecycleOwner = this
        binding.vm = studyViewModel

        studyViewModel.isStudyPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btStNewPostRegister.isEnabled = enabled
        }

        studyViewModel.etText.observe(this) {
            checkAllFields()
        }

        studyViewModel.seekBar.observe(this) {
            checkAllFields()
            binding.tvStNewMaxNumber.text = it.toString() + "명"
        }

        studyViewModel.selectSpinner.observe(this) {
            checkAllFields()
        }

        studyViewModel.isTeamExists.observe(this) {
            checkOrganizationExistence(it)
        }

        studyViewModel.studyCreateData.observe(this) {
            studyViewModel.sendStudyData(it)
        }

        studyViewModel.createFinish.observe(this) {
            if (it) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }


        spinnerMeetChoice()
        backStudyNewToMainActivity()
    }

    private fun spinnerMeetChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.study_new_group_meet, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spStMeet.adapter = adapter
    }

    private fun backStudyNewToMainActivity() {
        binding.btStNewPostRegister.setOnClickListener {
            studyViewModel.isTeamExists(binding.etStNewGitLink.text.toString())
        }

        binding.btStNewBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun checkAllFields() {
        if (binding.etStNewTitleName.length() != 0 && binding.etTitleDetail.length() != 0 && binding.tvStNewGithubTitle.length() != 0) {
            studyViewModel.isStudyPostRegisterButtonState(true)
        } else {
            studyViewModel.isStudyPostRegisterButtonState(false)
        }
    }

    private fun checkOrganizationExistence(isOrgCheck: Boolean) {
        if (isOrgCheck) {
            val studyData = StudySetPost(
                title = binding.etStNewTitleName.text.toString(),
                content = binding.etTitleDetail.text.toString(),
                isContact = binding.spStMeet.selectedItem.toString(),
                allPersonnel = binding.seekBar.progress,
                gitHubLink = binding.etStNewGitLink.text.toString(),
            )
            studyViewModel.setStudyPostData(studyData)
        } else {
            Toast.makeText(this, "해당 깃허브 조직은 존재하지 않습니다", Toast.LENGTH_SHORT).show()
        }
    }
}
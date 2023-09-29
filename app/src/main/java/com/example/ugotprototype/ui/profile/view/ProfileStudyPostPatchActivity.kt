package com.example.ugotprototype.ui.profile.view

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
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.data.study.StudySetPost
import com.example.ugotprototype.databinding.ActivityProfileStudyPostPatchBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyStudyPostPatchViewModel
import com.example.ugotprototype.ui.team.view.TeamFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileStudyPostPatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileStudyPostPatchBinding
    private val viewModel: ProfileMyStudyPostPatchViewModel by viewModels()
    private lateinit var adapter: ArrayAdapter<CharSequence>
    private var teamId = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_study_post_patch)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        teamId = intent.getIntExtra(TeamFragment.TEAM_ID, 0)

        viewModel.initData(teamId)

        viewModel.studyItemList.observe(this) {
            viewSetting(it)
        }

        viewModel.isStudyPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btStNewPostRegister.isEnabled = enabled
        }

        viewModel.etText.observe(this) {
            checkAllFields()
        }

        viewModel.seekBar.observe(this) {
            checkAllFields()
            binding.tvStNewMaxNumber.text = it.toString() + "명"
        }

        viewModel.selectSpinner.observe(this) {
            checkAllFields()
        }

        viewModel.isTeamExists.observe(this) {
            checkOrganizationExistence(it)
        }

        viewModel.createFinish.observe(this) {
            if (it) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        spinnerMeetChoice()
        backStudyNewToMainActivity()
    }

    private fun spinnerMeetChoice() {
        adapter = ArrayAdapter.createFromResource(
            this, R.array.study_new_group_meet, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spStMeet.adapter = adapter
    }

    private fun backStudyNewToMainActivity() {
        binding.btStNewPostRegister.setOnClickListener {
            viewModel.isKakaoOpenChatBaseURL(binding.etStNewKakaoLink.text.toString()) {
                if (it == "success") {
                    viewModel.isStudyExists(binding.etStNewGitLink.text.toString())
                } else {
                    Toast.makeText(this, "해당 카카오 오픈링크는 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btStNewBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun checkAllFields() {
        if (binding.etStNewTitleName.length() != 0 && binding.etTitleDetail.length() != 0 && binding.tvStNewGithubTitle.length() != 0 && binding.etSubject.length() != 0 && binding.etField.length() != 0) {
            viewModel.isStudyPostRegisterButtonState(true)
        } else {
            viewModel.isStudyPostRegisterButtonState(false)
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
                kakaoOpenLink = binding.etStNewKakaoLink.text.toString(),
                subject = binding.etSubject.text.toString(),
                field = binding.etField.text.toString(),
                groupId = 0
            )
            viewModel.patchPost(teamId, studyData)
        } else {
            Toast.makeText(this, "해당 깃허브 조직은 존재하지 않습니다", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewSetting(study: StudyGetPost) {
        with(binding) {
            etStNewTitleName.setText(study.title)
            etTitleDetail.setText(study.content)
            spStMeet.setSelection(adapter.getPosition(study.isContact))
            tvStNewMinNumber.text = study.nowPersonnel.toString() + "명"
            tvStNewMaxNumber.text = study.allPersonnel.toString() + "명"
            seekBar.progress = study.allPersonnel
            etSubject.setText(study.subject)
            etField.setText(study.field)
            etStNewGitLink.setText(study.gitHubLink)
            etStNewKakaoLink.setText(study.kakaoOpenLink)
        }
    }
}
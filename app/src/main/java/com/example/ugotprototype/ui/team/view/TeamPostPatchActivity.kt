package com.example.ugotprototype.ui.team.view

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
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.databinding.ActivityProfileTeamPostPatchBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyTeamPostPatch
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GITHUB_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_PERSON_CNT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamPostPatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileTeamPostPatchBinding
    private val viewModel: ProfileMyTeamPostPatch by viewModels()
    private var teamId = 0
    private lateinit var fieldAdapter: ArrayAdapter<CharSequence>
    private lateinit var classAdapter: ArrayAdapter<CharSequence>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_team_post_patch)
        binding.vm = viewModel

        teamId = intent.getIntExtra(TEAM_ID, 0)

        viewModel.initData(teamId)

        viewModel.isTeamPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btTeamPostRegister.isEnabled = enabled
        }

        viewModel.etText.observe(this) {
            checkAllFields()
        }

        viewModel.selectSpinner.observe(this) {
            checkAllFields()
        }

        viewModel.createFinish.observe(this) {
            if (it) {
                finish()
            }
        }

        viewModel.teamItemList.observe(this) {
            viewSetting(it)
        }

        spinnerSetting()
        backToMainActivity()
    }

    private fun spinnerSetting() {

        fieldAdapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_field_item, android.R.layout.simple_spinner_item
        )
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fieldSpinner.adapter = fieldAdapter

        classAdapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_class_item, android.R.layout.simple_spinner_item
        )
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.classSpinner.adapter = classAdapter
    }

    private fun backToMainActivity() {
        binding.btTeamPostRegister.setOnClickListener {
            viewModel.isKakaoOpenChatBaseURL(binding.etInputKakaoOpenLink.text.toString()) {
                if (it == "success") {
                    checkOrganizationExistence()
                } else {
                    Toast.makeText(this, "해당 카카오 오픈링크는 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun checkAllFields() {
        if (binding.etLanguage.length() != 0 && binding.etTarget.length() != 0 && binding.classSpinner.selectedItemPosition != 0 && binding.fieldSpinner.selectedItemPosition != 0 && binding.etTitleName.length() != 0 && binding.etTitleDetail.length() != 0 && binding.etInputKakaoOpenLink.length() != 0) {
            viewModel.isTeamPostRegisterButtonState(true)
        } else {
            viewModel.isTeamPostRegisterButtonState(false)
        }
    }

    private fun checkOrganizationExistence() {

        val teamData = TeamPostData(
            title = binding.etTitleName.text.toString(),
            content = binding.etTitleDetail.text.toString(),
            field = binding.fieldSpinner.selectedItem.toString(),
            _class = binding.classSpinner.selectedItem.toString(),
            allPersonnel = intent.getIntExtra(TEAM_PERSON_CNT, 0),
            gitHubLink = intent.getStringExtra(TEAM_GITHUB_LINK).toString(),
            kakaoOpenLink = binding.etInputKakaoOpenLink.text.toString(),
            goal = binding.etTarget.text.toString(),
            language = binding.etLanguage.text.toString(),
            groupId = 0
        )
        viewModel.patchPost(teamId, teamData)
    }

    @SuppressLint("SetTextI18n")
    private fun viewSetting(team: Team) {
        with(binding) {
            etTitleName.setText(team.title)
            etTitleDetail.setText(team.content)
            fieldSpinner.setSelection(fieldAdapter.getPosition(team.field))
            classSpinner.setSelection(classAdapter.getPosition(team._class))
            etTarget.setText(team.goal)
            etLanguage.setText(team.language)
            etInputKakaoOpenLink.setText(team.kakaoOpenLink)
        }
    }
}

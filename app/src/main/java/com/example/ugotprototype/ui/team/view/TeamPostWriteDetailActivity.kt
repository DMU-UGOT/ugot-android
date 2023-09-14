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
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.databinding.ActivityTeamPostWriteDetailBinding
import com.example.ugotprototype.ui.team.viewmodel.TeamPostWriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamPostWriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPostWriteDetailBinding
    private val teamPostWriteViewModel: TeamPostWriteViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_post_write_detail)
        binding.vm = teamPostWriteViewModel

        teamPostWriteViewModel.isTeamPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btTeamPostRegister.isEnabled = enabled
        }

        teamPostWriteViewModel.teamCreateData.observe(this) {
            teamPostWriteViewModel.sendTeamData(it)
        }

        teamPostWriteViewModel.etText.observe(this) {
            checkAllFields()
        }

        teamPostWriteViewModel.seekBar.observe(this) {
            checkAllFields()
            binding.tvMaxNumber.text = it.toString() + "명"
        }

        teamPostWriteViewModel.selectSpinner.observe(this) {
            checkAllFields()
        }

        teamPostWriteViewModel.isTeamExists.observe(this) {
            checkOrganizationExistence(it)
        }

        teamPostWriteViewModel.createFinish.observe(this) {
            if(it) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        spinnerSetting()
        backToMainActivity()
    }

    private fun spinnerSetting() {

        val fieldAdapter = ArrayAdapter.createFromResource(
                this, R.array.team_post_field_item, android.R.layout.simple_spinner_item
        )
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fieldSpinner.adapter = fieldAdapter

        val classAdapter = ArrayAdapter.createFromResource(
                this, R.array.team_post_class_item, android.R.layout.simple_spinner_item
        )
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.classSpinner.adapter = classAdapter
    }

    private fun backToMainActivity() {
        binding.btTeamPostRegister.setOnClickListener {
            teamPostWriteViewModel.isKakaoOpenChatBaseURL(binding.etInputKakaoOpenLink.text.toString()) {
                if(it == "success") {
                    teamPostWriteViewModel.isTeamExists(binding.etInputGithubLink.text.toString())
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
        if (binding.classSpinner.selectedItemPosition != 0 && binding.fieldSpinner.selectedItemPosition != 0 && binding.etTitleName.length() != 0 && binding.etTitleDetail.length() != 0 && binding.etInputGithubLink.length() != 0 && binding.etInputKakaoOpenLink.length() != 0) {
            teamPostWriteViewModel.isTeamPostRegisterButtonState(true)
        } else {
            teamPostWriteViewModel.isTeamPostRegisterButtonState(false)
        }
    }

    private fun checkOrganizationExistence(isOrgCheck: Boolean) {
        if (isOrgCheck) {
            val teamData = TeamPostData(
                    title = binding.etTitleName.text.toString(),
                    content = binding.etTitleDetail.text.toString(),
                    field = binding.fieldSpinner.selectedItem.toString(),
                    _class = binding.classSpinner.selectedItem.toString(),
                    allPersonnel = binding.seekBar.progress,
                    gitHubLink = binding.etInputGithubLink.text.toString(),
                    kakaoOpenLink = binding.etInputKakaoOpenLink.text.toString()
            )
            teamPostWriteViewModel.setTeamPostData(teamData)
        } else {
            Toast.makeText(this, "해당 깃허브 조직은 존재하지 않습니다", Toast.LENGTH_SHORT).show()
        }
    }
}

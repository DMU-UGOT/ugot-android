package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.R
import com.example.ugotprototype.data.team.TeamPostData
import com.example.ugotprototype.databinding.ActivityTeamPostWriteDetailBinding
import com.example.ugotprototype.data.api.BackEndService
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeamPostWriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPostWriteDetailBinding
    private val teamViewModel: TeamViewModel by viewModels()

    @Inject
    lateinit var backEndService: BackEndService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_post_write_detail)
        binding.lifecycleOwner = this
        binding.vm = teamViewModel

        teamViewModel.isTeamPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btTeamPostRegister.isEnabled = enabled
        }

        teamViewModel.teamMaxPersonnel.observe(this) { maxPersonnel ->
            binding.tvMaxNumber.text = maxPersonnel.toString() + "명"
        }

        teamViewModel.teamCreateData.observe(this) {
            createSubmitTeam(it)
        }

        postFieldSet()
        postClassSet()
        checkPostRegister()
        backToMainActivity()
        teamMaxPersonnel()
    }

    private fun checkPostRegister() {

        val totalListener = object : AdapterView.OnItemSelectedListener, TextWatcher {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                checkFieldsAndUpdateButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldsAndUpdateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.classSpinner.onItemSelectedListener = totalListener
        binding.fieldSpinner.onItemSelectedListener = totalListener
        binding.etTitleName.addTextChangedListener(totalListener)
        binding.etTitleDetail.addTextChangedListener(totalListener)
        binding.etInputGithubLink.addTextChangedListener(totalListener)
        binding.etInputKakaoOpenLink.addTextChangedListener(totalListener)
    }

    private fun checkFieldsAndUpdateButtonState() {
        if (binding.classSpinner.selectedItemPosition != 0 && binding.fieldSpinner.selectedItemPosition != 0 && binding.etTitleName.length() != 0 && binding.etTitleDetail.length() != 0 && binding.etInputGithubLink.length() != 0 && binding.etInputKakaoOpenLink.length() != 0) {
            teamViewModel.isTeamPostRegisterButtonState(true)
        } else {
            teamViewModel.isTeamPostRegisterButtonState(false)
        }
    }

    private fun postFieldSet() {

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_field_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fieldSpinner.adapter = adapter
    }

    private fun postClassSet() {

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_class_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.classSpinner.adapter = adapter
    }

    private fun backToMainActivity() {

        binding.btTeamPostRegister.setOnClickListener {
            var teamData = TeamPostData(
                title = binding.etTitleName.text.toString(),
                content = binding.etTitleDetail.text.toString(),
                field = binding.fieldSpinner.selectedItem.toString(),
                _class = binding.classSpinner.selectedItem.toString(),
                allPersonnel = binding.seekBar.progress,
                gitHubLink = binding.etInputGithubLink.text.toString(),
                kakaoOpenLink = binding.etInputKakaoOpenLink.text.toString()
            )
            teamViewModel.setTeamPostData(teamData)
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun teamMaxPersonnel() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                teamViewModel.teamMaxPersonnel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun createSubmitTeam(data: TeamPostData) {
        lifecycleScope.launch{
            try {
                val response = backEndService.createTeam(data)
            }catch(e: Exception) {
                Log.d("e", "$e")
            }
        }
    }
}

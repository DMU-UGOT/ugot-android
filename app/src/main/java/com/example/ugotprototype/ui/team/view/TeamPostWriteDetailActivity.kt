package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamPostWriteDetailBinding
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel

class TeamPostWriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPostWriteDetailBinding
    private val teamViewModel: TeamViewModel by viewModels()


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

        postFieldSet()
        postClassSet()
        checkPostRegister()
        backToMainActivity()
        teamMaxPersonnel()
    }

    private fun checkPostRegister() {

        val totalListener =
            object : AdapterView.OnItemSelectedListener, TextWatcher {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    checkFieldsAndUpdateButtonState()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
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
        if (binding.classSpinner.selectedItemPosition != 0
            && binding.fieldSpinner.selectedItemPosition != 0
            && binding.etTitleName.length() != 0
            && binding.etTitleDetail.length() != 0
            && binding.etInputGithubLink.length() != 0
            && binding.etInputKakaoOpenLink.length() != 0
        ) {
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
        val resultIntent = Intent()

        binding.btTeamPostRegister.setOnClickListener {
            resultIntent.putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.btBackToMain.setOnClickListener {
            resultIntent.putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, resultIntent)
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
}
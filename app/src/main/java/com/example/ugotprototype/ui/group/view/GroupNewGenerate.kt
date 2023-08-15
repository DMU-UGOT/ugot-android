package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.databinding.ActivityGroupNewGenerateBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel

class GroupNewGenerate : AppCompatActivity() {
    private lateinit var binding: ActivityGroupNewGenerateBinding
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_new_generate)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_new_generate)

        groupViewModel.groupMaxPersonnel.observe(this) { maxPersonnel ->
            binding.tvGroupNewMaxNumber.text = maxPersonnel.toString() + "명"
        }

        groupMaxPersonnel()
        groupToMainActivity()
    }

    private fun groupToMainActivity() {
        binding.btGroupNewPostRegister.setOnClickListener {
            val inputTitle = binding.etGroupNewTitleName.text.toString()
            val inputDetail = binding.etGroupTextDetail.text.toString()
            val inputGithubUrl = binding.etGroupInputGithubLink.text.toString()
            val resultIntent = Intent()

            if (inputTitle.isEmpty() || inputDetail.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("제목, 내용을 모두 입력해주세요.")
                    .setPositiveButton("확인", null)
                    .create()
                alertDialog.show()
            } else if (!isValidGitHubUrl(inputGithubUrl)) {
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("유효한 깃헙 URL을 입력해주세요.")
                    .setPositiveButton("확인", null)
                    .create()
                alertDialog.show()
            } else {
                // 모든 필드가 채워져 있는 경우 데이터 설정 및 액티비티 종료
                resultIntent.putExtra("githubUrl", inputGithubUrl)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        binding.btGroupNewBackToMain.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun isValidGitHubUrl(url: String): Boolean {
        val githubUrlPattern = "https?://(www\\.)?github\\.com/[A-Za-z0-9_-]+/?"
        return url.matches(Regex(githubUrlPattern))
    }

    private fun groupMaxPersonnel() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                groupViewModel.groupMaxPersonnel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
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
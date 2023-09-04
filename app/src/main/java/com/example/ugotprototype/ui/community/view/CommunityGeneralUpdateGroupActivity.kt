package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.databinding.ActivityCommunityGeneralUpdateGroupBinding
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityGeneralUpdateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGeneralUpdateGroupBinding
    private val communityGeneralUpdateViewModel: CommunityGeneralUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_general_update_group)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_general_update_group)
        binding.vm = communityGeneralUpdateViewModel

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        binding.etGeneralNewTitleName2.setText(title)
        binding.etGeneralTextDetail2.setText(content)

        communityGeneralUpdateViewModel.isCommunityGeneralPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btGeneralNewPostRegister2.isEnabled = enabled
        }

        communityGeneralUpdateViewModel.etText.observe(this) {
            checkAllFields()
        }

        communityGeneralUpdateViewModel.communityUpdateData.observe(this) {
            communityGeneralUpdateViewModel.sendCommunityGeneralUpdateData(it)
        }

        communityGeneralUpdateViewModel.isCommunityGeneralExists.observe(this) {
            checkGeneralOrganizationExistence(it)
        }

        backNewToMainActivity()
    }

    private fun backNewToMainActivity() {
        binding.btGeneralNewPostRegister2.setOnClickListener {
            val generalNewTitleName2 = binding.etGeneralNewTitleName2.text.toString()
            val generalNewTextDetail2 = binding.etGeneralTextDetail2.text.toString()

            if (generalNewTitleName2.isEmpty()) {
                Toast.makeText(applicationContext, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (generalNewTextDetail2.isEmpty()) {
                Toast.makeText(applicationContext, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (!generalNewTextDetail2.isEmpty() && !generalNewTitleName2.isEmpty()) {
                Toast.makeText(applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
                Intent().putExtra("resultText", "text")
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btGeneralNewBackToMain2.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun checkGeneralOrganizationExistence(isOrgCheck: Boolean) {
        if (isOrgCheck) {
            val communityGeneralViewData = CommunityGeneralPostViewData(
                id = "",
                title = binding.etGeneralNewTitleName2.text.toString(),
                content = binding.etGeneralTextDetail2.text.toString(),
                viewCount = 0,
                voteCount = 0,
                created_at = "yyyy-MM-dd'T'HH:mm:ss",
                member_id = ""
            )
            communityGeneralUpdateViewModel.setCommunityPostData(communityGeneralViewData)
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun checkAllFields() {
        if (binding.etGeneralNewTitleName2.length() != 0 && binding.etGeneralTextDetail2.length() != 0) {
            communityGeneralUpdateViewModel.isCommunityGeneralPostRegisterButtonState(true)
        } else {
            communityGeneralUpdateViewModel.isCommunityGeneralPostRegisterButtonState(false)
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
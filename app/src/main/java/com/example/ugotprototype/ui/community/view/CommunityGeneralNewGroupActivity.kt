package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralNewPostData
import com.example.ugotprototype.databinding.ActivityCommunityGeneralNewGroupBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralNewGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityGeneralNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGeneralNewGroupBinding
    private val communityGeneralNewGroupViewModel: CommunityGeneralNewGroupViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_community_general_new_group)
        binding.vm = communityGeneralNewGroupViewModel

        communityGeneralNewGroupViewModel.isCommunityGeneralPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btGeneralNewPostRegister.isEnabled = enabled
        }

        communityGeneralNewGroupViewModel.communityCreateData.observe(this) {
            communityGeneralNewGroupViewModel.sendCommunityGeneralData(it)
        }

        communityGeneralNewGroupViewModel.etTitle.observe(this) {
            checkAllFields()
        }

        communityGeneralNewGroupViewModel.etText.observe(this) {
            checkAllFields()
        }

        communityGeneralNewGroupViewModel.createFinish.observe(this) {
            if (it) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        backNewToMainActivity()
    }

    private fun backNewToMainActivity() {
        binding.btGeneralNewPostRegister.setOnClickListener {
            checkGeneralOrganizationExistence()
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btGeneralNewBackToMain.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun checkAllFields() {
        if (binding.etGeneralNewTitleName.length() != 0 && binding.etGeneralTextDetail.length() != 0) {
            communityGeneralNewGroupViewModel.isCommunityGeneralPostRegisterButtonState(true)
        } else {
            communityGeneralNewGroupViewModel.isCommunityGeneralPostRegisterButtonState(false)
        }
    }

    private fun checkGeneralOrganizationExistence() {
        communityGeneralNewGroupViewModel.sendCommunityGeneralData(
            CommunityGeneralNewPostData(
                title = binding.etGeneralNewTitleName.text.toString(),
                content = binding.etGeneralTextDetail.text.toString()
            )
        )
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("나가시겠습니까?")
        builder.setMessage("\n변경사항이 저장되지 않을 수 있습니다")

        builder.setPositiveButton("예") { dialog, which ->
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        builder.setNegativeButton("아니오") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}

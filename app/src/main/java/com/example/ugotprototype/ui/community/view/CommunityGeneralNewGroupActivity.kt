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
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralNewGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class
CommunityGeneralNewGroupActivity : AppCompatActivity() {
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
        if (isFinishing) {
            return
        }

        val dialogBinding = ActivityDialogMessageBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btDialogYes.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
            alertDialog.dismiss()
        }

        dialogBinding.btDialogNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}

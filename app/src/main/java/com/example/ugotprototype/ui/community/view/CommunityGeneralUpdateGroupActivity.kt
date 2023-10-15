package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralUpdateViewData
import com.example.ugotprototype.databinding.ActivityCommunityGeneralUpdateGroupBinding
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.di.api.CommunityService
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityGeneralUpdateGroupActivity : AppCompatActivity() {
    @Inject
    lateinit var communityService: CommunityService

    private lateinit var binding: ActivityCommunityGeneralUpdateGroupBinding
    private val communityGeneralUpdateViewModel: CommunityGeneralUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_general_update_group)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_general_update_group)
        binding.vm = communityGeneralUpdateViewModel

        communityGeneralUpdateViewModel.isCommunityGeneralPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btGeneralNewPostRegister2.isEnabled = enabled
        }

        communityGeneralUpdateViewModel.getCommunityUpdateList(intent.getIntExtra(GENERAL_ID, 0))

        communityGeneralUpdateViewModel.communityUpdateData.observe(this){
            binding.etGeneralNewTitleName2.setText(it.title)
            binding.etGeneralTextDetail2.setText(it.content)
        }

        communityGeneralUpdateViewModel.etTitle.observe(this) {
            checkAllFields()
        }

        communityGeneralUpdateViewModel.etText.observe(this) {
            checkAllFields()
        }

        backNewToDetailActivity()
    }

    private fun backNewToDetailActivity() {
        binding.btGeneralNewPostRegister2.setOnClickListener {
            checkGeneralOrganizationExistence()
            communityGeneralUpdateViewModel.dataUpdate.observe(this) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btGeneralNewBackToMain2.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun checkGeneralOrganizationExistence() {
        val communityGeneralUpdateViewData = CommunityGeneralUpdateViewData(
            title = binding.etGeneralNewTitleName2.text.toString(),
            content = binding.etGeneralTextDetail2.text.toString()
        )
        communityGeneralUpdateViewModel.modifyCommunityGeneralUpdateData(communityGeneralUpdateViewData, intent.getIntExtra(GENERAL_ID,0))
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
            alertDialog.dismiss()
            onBackPressed()
        }

        dialogBinding.btDialogNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}

package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralUpdateViewData
import com.example.ugotprototype.databinding.ActivityProfileGeneralUpdateBinding
import com.example.ugotprototype.di.api.CommunityService
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralCommunityUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileMyGeneralCommunityUpdateActivity : AppCompatActivity() {
    @Inject
    lateinit var communityService: CommunityService

    private lateinit var binding: ActivityProfileGeneralUpdateBinding
    private val profileMyGeneralCommunityUpdateViewModel: ProfileMyGeneralCommunityUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_general_update)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_general_update)
        binding.vm = profileMyGeneralCommunityUpdateViewModel

        profileMyGeneralCommunityUpdateViewModel.isProfileGeneralPostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btGeneralNewPostRegister2.isEnabled = enabled
        }

        profileMyGeneralCommunityUpdateViewModel.getCommunityUpdateList(intent.getIntExtra(
            CommunityGeneralFragment.GENERAL_ID, 0))

        profileMyGeneralCommunityUpdateViewModel.communityUpdateData.observe(this){
            binding.etGeneralNewTitleName2.setText(it.title)
            binding.etGeneralTextDetail2.setText(it.content)
        }

        profileMyGeneralCommunityUpdateViewModel.etTitle.observe(this) {
            checkAllFields()
        }

        profileMyGeneralCommunityUpdateViewModel.etText.observe(this) {
            checkAllFields()
        }

        backNewToDetailActivity()
    }

    private fun backNewToDetailActivity() {
        binding.btGeneralNewPostRegister2.setOnClickListener {
            if(binding.etGeneralNewTitleName2.text.toString().isBlank()){
                showErrorMessage()
            }
            if(binding.etGeneralTextDetail2.text.toString().isBlank()){
                showErrorMessage2()
            }
            else {
                checkGeneralOrganizationExistence()
                profileMyGeneralCommunityUpdateViewModel.dataUpdate.observe(this) {
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
            }
        }

        binding.btGeneralNewBackToMain2.setOnClickListener {
            showOutCheckDialog()
        }
    }

    private fun checkGeneralOrganizationExistence() {
        val communityGeneralUpdateViewData = CommunityGeneralUpdateViewData(
            title = binding.etGeneralNewTitleName2.text.toString(),
            content = binding.etGeneralTextDetail2.text.toString()
        )
        profileMyGeneralCommunityUpdateViewModel.modifyCommunityGeneralUpdateData(communityGeneralUpdateViewData, intent.getIntExtra(
            CommunityGeneralFragment.GENERAL_ID,0))
    }

    private fun checkAllFields() {
        if (binding.etGeneralNewTitleName2.length() != 0 && binding.etGeneralTextDetail2.length() != 0) {
            profileMyGeneralCommunityUpdateViewModel.isProfileGeneralPostRegisterButtonState(true)
        } else {
            profileMyGeneralCommunityUpdateViewModel.isProfileGeneralPostRegisterButtonState(false)
        }
    }

    private fun showOutCheckDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("나가시겠습니까?")
        builder.setMessage("\n변경사항이 저장되지 않을 수 있습니다")

        builder.setPositiveButton("예") { dialog, which ->
            dialog.dismiss()
            onBackPressed()
        }

        builder.setNegativeButton("아니오") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun showErrorMessage() {
        val builder = AlertDialog.Builder(binding.root.context)

        builder.setTitle("제목이 비었습니다")
        builder.setMessage("\n제목을 작성해주시기 바랍니다")

        builder.setPositiveButton("확인") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun showErrorMessage2() {
        val builder = AlertDialog.Builder(binding.root.context)

        builder.setTitle("내용이 비었습니다")
        builder.setMessage("\n내용을 작성해주시기 바랍니다")

        builder.setPositiveButton("확인") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}

package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.change.CommunityChangeNewPostData
import com.example.ugotprototype.databinding.ActivityCommunityChangeNewGroupBinding
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeNewGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityChangeNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeNewGroupBinding
    private val communityChangeNewGroupViewModel: CommunityChangeNewGroupViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_change_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_new_group)

        communityChangeNewGroupViewModel.isCommunityChangePostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btChangeNewPostRegister.isEnabled = enabled
        }

        communityChangeNewGroupViewModel.communityCreateData.observe(this) {
            communityChangeNewGroupViewModel.sendCommunityChangeData(it)
        }

        communityChangeNewGroupViewModel.spGrade.observe(this){
            checkAllFields()
        }

        communityChangeNewGroupViewModel.spNowClass.observe(this) {
            checkAllFields()
        }

        communityChangeNewGroupViewModel.spChangeClass.observe(this) {
            checkAllFields()
        }


        communityChangeNewGroupViewModel.createFinish.observe(this) {
            if (it) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        spinnerChangeNewChoice()
        spinnerChangeNowClassNewChoice()
        spinnerChangeClassGroupNewChoice()
        backCommunityChangeNewToMainActivity()
    }

    private fun spinnerChangeNewChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_new_grade, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeNewGrade.adapter = adapter
    }

    private fun spinnerChangeNowClassNewChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_new_now_class, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeNewNowClass.adapter = adapter
    }

    private fun spinnerChangeClassGroupNewChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_new_now_change_class, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeNewChangeClass.adapter = adapter
    }

    private fun backCommunityChangeNewToMainActivity() {
        binding.btChangeNewPostRegister.setOnClickListener {
            checkChangeOrganizationExistence()
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btChangeNewBackToMain.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun checkAllFields() {
        if (binding.spChangeNewGrade.selectedItem == "미선택" &&
            binding.spChangeNewChangeClass.selectedItem == "미선택" &&
            binding.spChangeNewNowClass.selectedItem == "미선택") {
            communityChangeNewGroupViewModel.isCommunityChangePostRegisterButtonState(false)
        } else {
            communityChangeNewGroupViewModel.isCommunityChangePostRegisterButtonState(true)
        }
    }

    private fun checkChangeOrganizationExistence() {
        communityChangeNewGroupViewModel.sendCommunityChangeData(
            CommunityChangeNewPostData(
                grade = binding.spChangeNewGrade.selectedItem.toString(),
                currentClass = binding.spChangeNewNowClass.selectedItem.toString(),
                changeClass = binding.spChangeNewChangeClass.selectedItem.toString()
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
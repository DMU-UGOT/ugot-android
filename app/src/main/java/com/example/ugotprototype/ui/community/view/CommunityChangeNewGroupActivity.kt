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
            val selectedGrade = binding.spChangeNewGrade.selectedItem.toString()
            val nowClass = binding.spChangeNewNowClass.selectedItem.toString()
            val changeClass = binding.spChangeNewChangeClass.selectedItem.toString()

            val isFourthGrade = selectedGrade == "4학년"
            val isInvalidGradeChange = nowClass !in listOf("YJ", "YK") || changeClass !in listOf("YJ", "YK")
            val isInvalidOtherGradeChange = nowClass !in listOf("YA", "YB", "YC", "YD") || changeClass !in listOf("YA", "YB", "YC", "YD")

            if ((selectedGrade == "미선택" || nowClass == "미선택" || changeClass == "미선택") ||
                (nowClass == changeClass)) {
                showErrorMessage()
            } else if ((isFourthGrade && isInvalidGradeChange) || (!isFourthGrade && isInvalidOtherGradeChange)) {
                showErrorMessage()
            } else {
                checkChangeOrganizationExistence()
            }
        }

        binding.btChangeNewBackToMain.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun checkAllFields() {
        if (binding.spChangeNewGrade.selectedItem == "미선택" ||
            binding.spChangeNewChangeClass.selectedItem == "미선택" ||
            binding.spChangeNewNowClass.selectedItem == "미선택") {
            communityChangeNewGroupViewModel.isCommunityChangePostRegisterButtonState(false)
        }
        else {
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

    private fun showErrorMessage() {
        val builder = AlertDialog.Builder(binding.root.context)

        builder.setTitle("올바르지 않은 항목이 있습니다")
        builder.setMessage("\n제대로 된 항목을 선택해주시기 바랍니다")

        builder.setPositiveButton("확인") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
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
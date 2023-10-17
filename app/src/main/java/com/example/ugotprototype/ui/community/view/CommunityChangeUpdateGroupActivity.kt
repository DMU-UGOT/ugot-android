package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.api.ChangeService
import com.example.ugotprototype.data.change.CommunityChangeUpdateViewData
import com.example.ugotprototype.databinding.ActivityCommunityChangeUpdateGroupBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeFragment.Companion.CHANGE_CLASS_CHANGE_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityChangeUpdateGroupActivity : AppCompatActivity() {
    @Inject
    lateinit var changeService: ChangeService

    private lateinit var binding: ActivityCommunityChangeUpdateGroupBinding
    private val communityChangeUpdateViewModel: CommunityChangeUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_change_update_group)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_community_change_update_group)
        binding.vm = communityChangeUpdateViewModel

        communityChangeUpdateViewModel.isCommunityChangePostRegisterBtnEnabled.observe(this) { enabled ->
            binding.btChangeUpdatePostRegister.isEnabled = enabled
        }

        communityChangeUpdateViewModel.spUpdateGrade.observe(this) {
            checkAllFields()
        }

        communityChangeUpdateViewModel.spUpdateNowClass.observe(this) {
            checkAllFields()
        }

        communityChangeUpdateViewModel.spUpdateChangeClass.observe(this) {
            checkAllFields()
        }

        spinnerChangeClassGroupUpdateChoice()
        spinnerChangeNowClassUpdateChoice()
        spinnerChangeUpdateChoice()
        spinnerChangeStatusUpdateChoice()
        backNewToChangeDetailActivity()
    }

    private fun backNewToChangeDetailActivity() {
        binding.btChangeUpdatePostRegister.setOnClickListener {
            checkChangeUpdateOrganizationExistence()

            communityChangeUpdateViewModel.dataUpdate.observe(this) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btChangeUpdateBackToMain.setOnClickListener {
            showOutCheckDialog()
        }
    }

    private fun spinnerChangeUpdateChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_update_grade, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeUpdateGrade.adapter = adapter
    }

    private fun spinnerChangeNowClassUpdateChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_update_now_class, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeUpdateNowClass.adapter = adapter
    }

    private fun spinnerChangeClassGroupUpdateChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_update_now_change_class, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeUpdateChangeClass.adapter = adapter
    }

    private fun spinnerChangeStatusUpdateChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_update_now_change_status, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeUpdateChangeStatus.adapter = adapter
    }

    private fun checkAllFields() {
        if (binding.spChangeUpdateGrade.selectedItem == "미선택" &&
            binding.spChangeUpdateChangeClass.selectedItem == "미선택" &&
            binding.spChangeUpdateNowClass.selectedItem == "미선택"
        ) {
            communityChangeUpdateViewModel.isCommunityChangePostRegisterButtonState(false)
        } else {
            communityChangeUpdateViewModel.isCommunityChangePostRegisterButtonState(true)
        }
    }

    private fun checkChangeUpdateOrganizationExistence() {
        val communityChangeUpdateViewData = CommunityChangeUpdateViewData(
            grade = binding.spChangeUpdateGrade.selectedItem.toString(),
            currentClass = binding.spChangeUpdateNowClass.selectedItem.toString(),
            changeClass = binding.spChangeUpdateChangeClass.selectedItem.toString(),
            status = binding.spChangeUpdateChangeStatus.selectedItem.toString()
        )

        if((binding.spChangeUpdateNowClass.selectedItem.toString() == binding.spChangeUpdateChangeClass.selectedItem.toString())
            ||
            (binding.spChangeUpdateGrade.selectedItem.toString() != "4학년" &&
                    (binding.spChangeUpdateNowClass.selectedItem.toString() == "YJ" || binding.spChangeUpdateNowClass.selectedItem.toString() == "YK") ||
                    (binding.spChangeUpdateChangeClass.selectedItem.toString() == "YJ" || binding.spChangeUpdateChangeClass.selectedItem.toString() == "YK")))
        {
            showConfirmationErrorDialog()
        } else {
            communityChangeUpdateViewModel.modifyCommunityChangeUpdateData(
                communityChangeUpdateViewData,
                intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0)
            )
        }
    }

    private fun showConfirmationErrorDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("현재 반과 변경 반이 올바르지 않습니다")
        builder.setMessage("\n반을 다시 선택해주시기 바랍니다")

        builder.setNegativeButton("확인") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
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
}
package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityChangeNewGroupBinding
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding

class CommunityChangeNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeNewGroupBinding
    private lateinit var dialogBinding: ActivityDialogMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_change_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_new_group)

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
            val gradeClass = binding.spChangeNewGrade.selectedItem.toString()
            val currentClass = binding.spChangeNewNowClass.selectedItem.toString()
            val changeClass = binding.spChangeNewChangeClass.selectedItem.toString()

            if (currentClass == "미선택" || changeClass == "미선택" || gradeClass == "미선택") {
                Toast.makeText(applicationContext, "선택되지 않은 항목이 있습니다", Toast.LENGTH_SHORT).show()
            } else if (currentClass == changeClass) {
                Toast.makeText(applicationContext, "현재 반과 변경할 반이 동일합니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
                Intent().putExtra("resultText", "text")
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btChangeNewBackToMain.setOnClickListener {
            binding.spChangeNewNowClass.setSelection(0) // 현재 반 선택을 초기화합니다.
            binding.spChangeNewChangeClass.setSelection(0) // 변경할 반 선택을 초기화합니다.
            showConfirmationDialog()
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
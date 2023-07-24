package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityGeneralNewGroupBinding
import com.example.ugotprototype.databinding.ActivityDialogMessageBinding

class CommunityGeneralNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGeneralNewGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_general_new_group)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_community_general_new_group)

        backCommunityGeneralNewToMainActivity()
    }

    private fun backCommunityGeneralNewToMainActivity() {
        binding.btGeneralNewPostRegister.setOnClickListener {
            val generalNewTitleName = binding.etGeneralNewTitleName.text.toString()
            val generalNewTextDetail = binding.etGeneralTextDetail.text.toString()

            if (generalNewTitleName.isEmpty()) {
                Toast.makeText(applicationContext, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (generalNewTextDetail.isEmpty()) {
                Toast.makeText(applicationContext, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (!generalNewTextDetail.isEmpty() && !generalNewTitleName.isEmpty()) {
                Toast.makeText(applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
                Intent().putExtra("resultText", "text")
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        binding.btGeneralNewBackToMain.setOnClickListener {
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
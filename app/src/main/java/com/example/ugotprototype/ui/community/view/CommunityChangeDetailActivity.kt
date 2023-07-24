package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding
import com.example.ugotprototype.databinding.ActivityCommunityChangeSendMessageBinding

class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)

        dataChangeSet()
        backCommunityChangeToMainActivity()
        clickSendMessage()
    }

    private fun backCommunityChangeToMainActivity() {
        binding.ivCommunityChangeBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun dataChangeSet() {
        binding.tvCommunityChangeDetailGrade.text = intent.getStringExtra("comChangeGrade")
        binding.tvCommunityChangeDetailNickName.text = intent.getStringExtra("comChangeNickName")
        binding.tvCommunityChangeDetailTime.text = intent.getStringExtra("comChangeDate")
        binding.tvCommunityChangeDetailNowInput.text = intent.getStringExtra("comChangeNowClass")
        binding.tvCommunityChangeDetailChangeInput.text = intent.getStringExtra("comChangeClass")
        binding.tvCommunityChangeDetailExchange.text = intent.getStringExtra("comChangeExchange")
    }

    private fun clickSendMessage(){
        binding.btChangeNewMessage.setOnClickListener {
            sendMessageDialog()
        }
    }

    private fun sendMessageDialog() {
        if (isFinishing) {
            return
        }

        val dialogBinding = ActivityCommunityChangeSendMessageBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btChangeSendMessage.setOnClickListener {
            val title = dialogBinding.etChangeSendTitle.toString()
            val text = dialogBinding.etChangeSendText.text.toString()

            // 입력된 이름과 내용을 사용하여 보내기 동작 수행
            send(title, text)
            alertDialog.dismiss()
        }

        dialogBinding.btChangeSendReturn.setOnClickListener {
            // 취소
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun send(title: String, text: String) {
        // 여기에 실제로 보내기 동작을 구현
        // 예를 들어, 서버 API 호출이나 데이터 처리 등 구현
        // 예시로 Log 출력
        Log.d("Send", "제목: $title, 내용: $text")
    }
}
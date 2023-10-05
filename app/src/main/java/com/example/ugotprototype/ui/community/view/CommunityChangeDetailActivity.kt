package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding
import com.example.ugotprototype.databinding.ActivityCommunityChangeSendMessageBinding
import com.example.ugotprototype.databinding.ActivityDialogDeleteMessageBinding
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding
    private val communityChangeDetailViewModel: CommunityChangeDetailViewModel by viewModels()
    private val communityChangeViewModel: CommunityChangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)


        binding.tvChangeDelete.setOnClickListener {
            showDeleteCheckDialog()
        }

        dataChangeSet()

        if(communityChangeDetailViewModel.getLoggedInUserId().toString() == binding.tvCommunityChangeMemberId.text.toString()){
            binding.tvChangeDelete.visibility = View.VISIBLE
            binding.tvChangeNewUpdate.visibility = View.VISIBLE
            binding.ivCmuChangeResetTime.visibility = View.VISIBLE
        } else {
            binding.tvChangeDelete.visibility = View.GONE
            binding.tvChangeNewUpdate.visibility = View.GONE
            binding.ivCmuChangeResetTime.visibility = View.GONE
        }

        resetTime(intent.getIntExtra(CommunityChangeFragment.CHANGE_CLASS_CHANGE_ID, 0))
        backCommunityChangeToMainActivity()




        clickSendMessage()


        // 아이템 정보 받기
//        var comChangeExchange = intent.getStringExtra("comChangeExchange")

        // 거래 상태 표시
//        binding.tvCommunityChangeDetailExchange.text = comChangeExchange

        // 수정 버튼 클릭 이벤트 처리
//        binding.btChangeNewUpdate.setOnClickListener {
//            // 거래 상태를 "완료"와 "가능" 사이에서 토글합니다.
//            comChangeExchange = if (comChangeExchange == "완료") "가능" else "완료"
//            binding.tvCommunityChangeDetailExchange.text = comChangeExchange
//        }
    }

    private fun backCommunityChangeToMainActivity() {
        binding.ivCommunityChangeBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dataChangeSet() {
        with(binding) {
            tvCommunityChangeDetailGrade.text = intent.getStringExtra(CommunityChangeFragment.CHANGE_GRADE)
            tvCommunityChangeDetailNickName.text = intent.getStringExtra(CommunityChangeFragment.CHANGE_NICK_NAME)
            tvCommunityChangeDetailTime.text =
                LocalDateTime.parse(intent.getStringExtra((CommunityChangeFragment.CHANGE_CREATE_AT)))
                ?.format(
                    DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                ) ?: ""
            tvCommunityChangeDetailNowInput.text = intent.getStringExtra(CommunityChangeFragment.CHANGE_CURRENT_CLASS)
            tvCommunityChangeDetailChangeInput.text = intent.getStringExtra(CommunityChangeFragment.CHANGE_CHANGE_CLASS)
            tvCommunityChangeDetailExchange.text = intent.getStringExtra(CommunityChangeFragment.CHANGE_STATUS)
            tvCommunityChangeMemberId.text = intent.getIntExtra(CommunityChangeFragment.CHANGE_MEMBER_ID, 0).toString()
        }
    }



    private fun clickSendMessage() {
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
            alertDialog.dismiss()
        }

        dialogBinding.btChangeSendReturn.setOnClickListener {
            // 취소
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    // 삭제
    private fun showDeleteCheckDialog() {
        val dialogBinding = ActivityDialogDeleteMessageBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btDialogDeleteYes.setOnClickListener {
            alertDialog.dismiss()
            deleteCommunity()
        }

        dialogBinding.btDialogDeleteNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun deleteCommunity() {
        communityChangeDetailViewModel.deleteChangeDetailText(intent.getIntExtra(CommunityChangeFragment.CHANGE_CLASS_CHANGE_ID,0))
    }

    private fun refreshGeneralOrganizationExistence(classChangeId : Int) {
        val communityChangeRefreshData = CommunityChangeRefreshData(
            createdAt = binding.tvCommunityChangeDetailTime.text.toString()
        )
        communityChangeDetailViewModel.refreshData(classChangeId, communityChangeRefreshData)
    }

    private fun resetTime(classChangeId : Int) {
        binding.ivCmuChangeResetTime.setOnClickListener {
            refreshGeneralOrganizationExistence(classChangeId)
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
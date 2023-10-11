package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding
import com.example.ugotprototype.databinding.ActivityCommunityChangeSendMessageBinding
import com.example.ugotprototype.databinding.ActivityDialogDeleteMessageBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeFragment.Companion.CHANGE_CLASS_CHANGE_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding
    private val communityChangeDetailViewModel: CommunityChangeDetailViewModel by viewModels()
    private val communityChangeUpdateViewModel: CommunityChangeUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)

        dataChangeSet()

        if (communityChangeDetailViewModel.getLoggedInUserId()
                .toString() == binding.tvCommunityChangeMemberId.text.toString()
        ) {
            binding.ivChangeMenu.visibility = View.VISIBLE
        } else {
            binding.ivChangeMenu.visibility = View.GONE
        }

        communityChangeUpdateViewModel.dataUpdate.observe(this) { isDataUpdate ->
            if (isDataUpdate) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        goToUpdateChangeResult()
        onClickChangeHamburgerButton()
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

    @SuppressLint("SetTextI18n")
    private fun dataChangeSet() {
        with(binding) {
            tvCommunityChangeDetailGrade.text =
                intent.getStringExtra(CommunityChangeFragment.CHANGE_GRADE)
            tvCommunityChangeDetailNickName.text =
                intent.getStringExtra(CommunityChangeFragment.CHANGE_NICK_NAME)
            tvCommunityChangeDetailTime.text =
                LocalDateTime.parse(intent.getStringExtra((CommunityChangeFragment.CHANGE_CREATE_AT)))
                    ?.format(
                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    ) ?: ""
            tvCommunityChangeDetailNowInput.text =
                intent.getStringExtra(CommunityChangeFragment.CHANGE_CURRENT_CLASS)
            tvCommunityChangeDetailChangeInput.text =
                intent.getStringExtra(CommunityChangeFragment.CHANGE_CHANGE_CLASS)
            tvCommunityChangeDetailExchange.text =
                intent.getStringExtra(CommunityChangeFragment.CHANGE_STATUS)
            tvCommunityChangeMemberId.text =
                intent.getIntExtra(CommunityChangeFragment.CHANGE_MEMBER_ID, 0).toString()
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

    private fun goToUpdateChangeResult() {
        val goToUpdateChangeResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    communityChangeDetailViewModel.getCommunityChangeDetailList(intent.getIntExtra(
                        CHANGE_CLASS_CHANGE_ID, 0))
                }
            }

        binding.tvCmuChangeUpdate.setOnClickListener {
            goToUpdateChangeResultLauncher.launch(
                Intent(
                    applicationContext,
                    CommunityChangeUpdateGroupActivity::class.java
                ).putExtra(
                    CHANGE_CLASS_CHANGE_ID, intent.getIntExtra(
                        CHANGE_CLASS_CHANGE_ID, 0))
            )
        }
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
        communityChangeDetailViewModel.deleteChangeDetailText(
            intent.getIntExtra(
                CommunityChangeFragment.CHANGE_CLASS_CHANGE_ID,
                0
            )
        )
    }

    private fun refreshGeneralOrganizationExistence(classChangeId: Int) {
        val communityChangeRefreshData = CommunityChangeRefreshData(
            createdAt = binding.tvCommunityChangeDetailTime.text.toString()
        )
        communityChangeDetailViewModel.refreshData(classChangeId, communityChangeRefreshData)
    }

    private fun resetTime(classChangeId: Int) {
        refreshGeneralOrganizationExistence(classChangeId)
        setResult(Activity.RESULT_OK, Intent())
        finish()
    }

    private fun onClickChangeHamburgerButton() {
        binding.ivChangeMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.change_hamburger_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_change_item1 -> {
                        // 수정
                        Intent(
                            this@CommunityChangeDetailActivity,
                            CommunityChangeUpdateGroupActivity::class.java
                        ).apply {
                            putExtra(CHANGE_CLASS_CHANGE_ID, 0)
                            startActivity(this)
                        }
                        true
                    }
                    // 갱신
                    R.id.menu_change_item2 -> {
                        resetTime(intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0))
                        true
                    }
                    // 삭제
                    R.id.menu_change_item3 -> {
                        showDeleteCheckDialog()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }
}
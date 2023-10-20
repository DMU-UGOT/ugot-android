package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.change.CommunityChangePostViewData
import com.example.ugotprototype.data.change.CommunityChangeRefreshData
import com.example.ugotprototype.data.profile.ProfileMessageCommentNewPostData
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding
import com.example.ugotprototype.databinding.ActivityCommunityChangeSendMessageBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeFragment.Companion.CHANGE_CLASS_CHANGE_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding
    private val communityChangeDetailViewModel: CommunityChangeDetailViewModel by viewModels()
    private val communityChangeUpdateViewModel: CommunityChangeUpdateViewModel by viewModels()
    private var classChangeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)

        communityChangeDetailViewModel.communityChangeDetailData.observe(this) {
            viewSetting(it)
        }

        onClickChangeHamburgerButton()
        backCommunityChangeToMainActivity()
        clickSendMessage()
    }

    private fun viewSetting(data: CommunityChangePostViewData) {
        classChangeId = data.classChangeId

        with(binding) {
            tvCommunityChangeDetailGrade.text = data.grade
            tvCommunityChangeDetailTime.text = formatDate(data.createdAt)
            tvCommunityChangeDetailNickName.text = data.nickname
            tvCommunityChangeDetailNowInput.text = data.currentClass
            tvCommunityChangeDetailChangeInput.text = data.changeClass
            tvCommunityChangeDetailExchange.text = data.status
            tvCommunityChangeMemberId.text = data.memberId.toString()
        }

        if (communityChangeDetailViewModel.getLoggedInUserId()
                .toString() == data.memberId.toString()
        ) {
            binding.btChangeNewMessage.visibility = View.GONE
            binding.ivChangeMenu.visibility = View.VISIBLE
        } else {
            binding.btChangeNewMessage.visibility = View.VISIBLE
            binding.ivChangeMenu.visibility = View.GONE
        }
    }

    private fun backCommunityChangeToMainActivity() {
        binding.ivCommunityChangeBack.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        val date = inputFormat.parse(dateString)
        val cal = Calendar.getInstance().apply { time = date }
        val dateFormat: SimpleDateFormat

        if (currentDate.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
        ) {
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        } else {
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        }

        return dateFormat.format(date)
    }

    private fun deleteCommunity() {
        communityChangeDetailViewModel.deleteChangeDetailText(
            intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0)
        )

        communityChangeDetailViewModel.isDeleteChangeGroup.observe(this) {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun refreshGeneralOrganizationExistence(classChangeId: Int) {
        val communityChangeRefreshData = CommunityChangeRefreshData(
            createdAt = binding.tvCommunityChangeDetailTime.text.toString()
        )
        communityChangeDetailViewModel.refreshData(classChangeId, communityChangeRefreshData)
    }

    private fun resetTime(classChangeId: Int) {
        refreshGeneralOrganizationExistence(classChangeId)

        communityChangeDetailViewModel.dataRefresh.observe(this) {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private val goToUpdateToDetailResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                communityChangeUpdateViewModel.sendUpdatedData(
                    intent.getIntExtra(
                        CHANGE_CLASS_CHANGE_ID, 0
                    )
                )
            }
        }

    private fun onClickChangeHamburgerButton() {
        binding.ivChangeMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.change_hamburger_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_change_item1 -> {
                        goToUpdateToDetailResultLauncher.launch(
                            Intent(
                                applicationContext,
                                CommunityChangeUpdateGroupActivity::class.java
                            ).putExtra(
                                CHANGE_CLASS_CHANGE_ID,
                                intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0)
                            )
                        )
                        true
                    }

                    R.id.menu_change_item2 -> {
                        resetTime(intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0))
                        true
                    }

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
            communityChangeDetailViewModel.sendMessage(
                intent.getIntExtra(CommunityGeneralFragment.GENERAL_ID, 0), ProfileMessageCommentNewPostData(
                    content = dialogBinding.etChangeSendText.text.toString()
                )
            )
            alertDialog.dismiss()
            Toast.makeText(this,"메시지를 전송하였습니다", Toast.LENGTH_SHORT).show()
        }

        dialogBinding.btChangeSendReturn.setOnClickListener {
            alertDialog.dismiss()
            Toast.makeText(this,"메시지를 전송을 취소했습니다", Toast.LENGTH_SHORT).show()
        }
        alertDialog.show()
    }

    private fun showDeleteCheckDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("삭제하시겠습니까?")
        builder.setMessage("정말로 이 게시글을 삭제하시겠습니까?")

        builder.setPositiveButton("예") { dialog, which ->
            deleteCommunity()
        }

        builder.setNegativeButton("아니오") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        communityChangeDetailViewModel.getChangeDetailData(
            intent.getIntExtra(CHANGE_CLASS_CHANGE_ID, 0)
        )
    }
}
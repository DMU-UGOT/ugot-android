package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.databinding.ActivityDialogDeleteMessageBinding
import com.example.ugotprototype.databinding.FragmentCommunityGeneralDetailBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralChatRecyclerViewAdapter
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralChatViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CommunityGeneralDetailActivity : AppCompatActivity() {
    private lateinit var binding: FragmentCommunityGeneralDetailBinding
    private val communityGeneralChatViewModel: CommunityGeneralChatViewModel by viewModels()
    private val communityGeneralDetailViewModel: CommunityGeneralDetailViewModel by viewModels()
    private val communityGeneralUpdateViewModel: CommunityGeneralUpdateViewModel by viewModels()
    private lateinit var communityGeneralChatRecyclerViewAdapter: CommunityGeneralChatRecyclerViewAdapter
    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_community_general_detail)
        binding.vm = communityGeneralChatViewModel

        communityGeneralDetailViewModel.communityDetailData.observe(this) {
            viewSetting(it)
        }

        // 댓글 관련
        communityGeneralChatRecyclerViewAdapter =
            CommunityGeneralChatRecyclerViewAdapter(communityGeneralChatViewModel, intent.getIntExtra(GENERAL_ID, 0))
        binding.rvCommunityGeneralChat.adapter = communityGeneralChatRecyclerViewAdapter

        communityGeneralChatViewModel.communityGeneralChatItemList.observe(this) {
            communityGeneralChatRecyclerViewAdapter.setData(it)
        }

        //댓글 추가
        communityGeneralChatViewModel.communityGeneralChatCreateItemList.observe(this) {
            communityGeneralChatViewModel.newCommunityCommentData(intent.getIntExtra(GENERAL_ID, 0), it)
        }

        communityGeneralChatViewModel.isDeleteComment.observe(this){
            setResult(Activity.RESULT_OK, Intent())
        }

        onClickGeneralHamburgerButton()
        chatInputBtn()
        changeMyGeneralChatCount()
        goBackCommunityGeneralUpdate()
    }

    private fun viewSetting(data: CommunityGeneralPostViewData) {
        postId = data.id

        with(binding) {
            tvCommunityGeneralName.text = data.title
            tvCommunityGeneralNickname.text = data.nickname
            tvCommunityGeneralTime.text = formatDate(data.created_at)
            tvCommunityGeneralText.text = data.content
            tvCommunityInquireInput.text = formatViewCount(data.viewCount)
            tvCommunityGeneralCnt.text = formatVoteCount(data.voteCount)
            tvCommunityGeneralMemberId.text = data.memberId.toString()
        }

        if (communityGeneralDetailViewModel.getLoggedInUserId()
                .toString() == data.memberId.toString()
        ) {
            binding.ivGeneralMenu.visibility = View.VISIBLE
        } else {
            binding.ivGeneralMenu.visibility = View.GONE
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
            dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        } else {
            dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        }

        return dateFormat.format(date)
    }

    private fun formatViewCount(viewCount: Int?): String {
        return viewCount?.toString() ?: "0"
    }

    private fun formatVoteCount(voteCount: Int?): String {
        return voteCount?.toString() ?: "0"
    }

    private fun goBackCommunityGeneralUpdate() {
        binding.ivCommunityGeneralBack.setOnClickListener {
            finish()
        }
    }

    private fun deleteCommunity() {
        communityGeneralDetailViewModel.deleteDetailText(intent.getIntExtra(GENERAL_ID, 0))

        communityGeneralDetailViewModel.isDeleteGeneralGroup.observe(this) {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun changeMyGeneralChatCount() {
        communityGeneralChatViewModel.itemCount.observe(this) { count ->
            binding.tvCommunityGeneralCnt.text = count.toString()
        }
    }

    private fun chatInputBtn() {
        binding.btGeneralDetailChatInput.setOnClickListener {
            checkGeneralOrganizationExistence(intent.getIntExtra(GENERAL_ID, 0))
            binding.generalChatInput.text.clear()
            setResult(Activity.RESULT_OK, Intent())
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.generalChatInput.windowToken, 0)
    }

    private fun checkGeneralOrganizationExistence(postId: Int) {
        communityGeneralChatViewModel.newCommunityCommentData(
            postId,
            CommunityGeneralCommentNewPostData(
                content = binding.generalChatInput.text.toString()
            )
        )
    }

    private fun refreshGeneralOrganizationExistence(postId: Int) {
        val communityGeneralRefreshData = CommunityGeneralRefreshData(
            created_at = binding.tvCommunityGeneralTime.text.toString()
        )
        communityGeneralDetailViewModel.refreshData(postId, communityGeneralRefreshData)
    }

    private fun resetTime(postId: Int) {
        refreshGeneralOrganizationExistence(postId)

        communityGeneralDetailViewModel.dataRefresh.observe(this){
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private val goToUpdateToGeneralDetailResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                communityGeneralUpdateViewModel.getCommunityUpdateList(intent.getIntExtra(GENERAL_ID, 0)
                )
            }
        }

    private fun onClickGeneralHamburgerButton() {
        binding.ivGeneralMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.general_hamburger_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_general_item1 -> {
                        goToUpdateToGeneralDetailResultLauncher.launch(
                            Intent(
                                applicationContext,
                                CommunityGeneralUpdateGroupActivity::class.java
                            ).putExtra(GENERAL_ID, intent.getIntExtra(GENERAL_ID, 0))
                        )
                        true
                    }

                    R.id.menu_general_item2 -> {
                        resetTime(intent.getIntExtra(GENERAL_ID, 0))
                        true
                    }

                    R.id.menu_general_item3 -> {
                        showDeleteCheckDialog()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
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
        communityGeneralDetailViewModel.getCommunityDetailList(
            intent.getIntExtra(GENERAL_ID, 0)
        )

        communityGeneralChatViewModel.getCommunityDetailList(
            intent.getIntExtra(GENERAL_ID, 0)
        )
    }
}

package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralPostViewData
import com.example.ugotprototype.data.community.CommunityGeneralRefreshData
import com.example.ugotprototype.databinding.ActivityProfileGeneralCommunityDetailBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import com.example.ugotprototype.ui.community.view.CommunityGeneralUpdateGroupActivity
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralUpdateViewModel
import com.example.ugotprototype.ui.profile.adapter.ProfilePostGeneralChatRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralChatViewModel
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralCommunityDetailViewModel
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralCommunityUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileMyGeneralCommunityDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileGeneralCommunityDetailBinding
    private val profileMyGeneralChatViewModel: ProfileMyGeneralChatViewModel by viewModels()
    private val profileMyGeneralCommunityDetailViewModel : ProfileMyGeneralCommunityDetailViewModel by viewModels()
    private lateinit var profilePostGeneralChatRecyclerViewAdapter: ProfilePostGeneralChatRecyclerViewAdapter
    private val profileMyGeneralCommunityUpdateViewModel: ProfileMyGeneralCommunityUpdateViewModel by viewModels()
    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_general_community_detail)

        profileMyGeneralCommunityDetailViewModel.communityDetailData.observe(this) {
            viewSetting(it)
        }

        // 댓글 관련
        profilePostGeneralChatRecyclerViewAdapter =
            ProfilePostGeneralChatRecyclerViewAdapter(profileMyGeneralChatViewModel, intent.getIntExtra(GENERAL_ID, 0))
        binding.rvCommunityGeneralChat.adapter = profilePostGeneralChatRecyclerViewAdapter

        profileMyGeneralChatViewModel.communityGeneralChatItemList.observe(this) {
            profilePostGeneralChatRecyclerViewAdapter.setData(it)
        }

        //댓글 추가
        profileMyGeneralChatViewModel.communityGeneralChatCreateItemList.observe(this) {
            profileMyGeneralChatViewModel.newCommunityCommentData(
                intent.getIntExtra(
                    CommunityGeneralFragment.GENERAL_ID, 0
                ), it
            )
        }

        profileMyGeneralChatViewModel.isDeleteComment.observe(this){
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

        if (profileMyGeneralCommunityDetailViewModel.getLoggedInUserId()
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
        profileMyGeneralCommunityDetailViewModel.deleteDetailText(intent.getIntExtra(CommunityGeneralFragment.GENERAL_ID, 0))

        profileMyGeneralCommunityDetailViewModel.isDeleteGeneralGroup.observe(this) {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun changeMyGeneralChatCount() {
        profileMyGeneralChatViewModel.itemCount.observe(this) { count ->
            binding.tvCommunityGeneralCnt.text = count.toString()
        }
    }

    private fun chatInputBtn() {
        binding.btGeneralDetailChatInput.setOnClickListener {
            if(binding.generalChatInput.text.toString().isBlank()){
                showErrorMessage()
            }
            else {
                checkGeneralOrganizationExistence(intent.getIntExtra(CommunityGeneralFragment.GENERAL_ID, 0))
                binding.generalChatInput.text.clear()
                setResult(Activity.RESULT_OK, Intent())
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.generalChatInput.windowToken, 0)
    }

    private fun checkGeneralOrganizationExistence(postId: Int) {
        profileMyGeneralChatViewModel.newCommunityCommentData(
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
       profileMyGeneralCommunityDetailViewModel.refreshData(postId, communityGeneralRefreshData)
    }

    private fun resetTime(postId: Int) {
        refreshGeneralOrganizationExistence(postId)

        profileMyGeneralCommunityDetailViewModel.dataRefresh.observe(this){
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private val goToUpdateToGeneralDetailResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                profileMyGeneralCommunityUpdateViewModel.getCommunityUpdateList(
                    intent.getIntExtra(GENERAL_ID, 0)
                )
            }
        }

    private fun onClickGeneralHamburgerButton() {
        binding.ivGeneralMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.profile_general_hamburger_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_profile_general_item1 -> {
                        goToUpdateToGeneralDetailResultLauncher.launch(
                            Intent(
                                applicationContext,
                                ProfileMyGeneralCommunityUpdateActivity::class.java
                            ).putExtra(GENERAL_ID, intent.getIntExtra(GENERAL_ID, 0))
                        )
                        true
                    }

                    R.id.menu_profile_general_item2 -> {
                        resetTime(intent.getIntExtra(GENERAL_ID, 0))
                        true
                    }

                    R.id.menu_profile_general_item3 -> {
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

    private fun showErrorMessage() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("빈칸은 입력이 안됩니다")
        builder.setMessage("댓글 텍스트를 입력해주세요")

        builder.setNegativeButton("확인") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        profileMyGeneralCommunityDetailViewModel.getCommunityDetailList(
            intent.getIntExtra(CommunityGeneralFragment.GENERAL_ID, 0)
        )

        profileMyGeneralChatViewModel.getCommunityDetailList(
            intent.getIntExtra(CommunityGeneralFragment.GENERAL_ID, 0)
        )
    }
}

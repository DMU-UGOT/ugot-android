package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.animation.content.Content
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.data.community.CommunityGeneralCommentNewPostData
import com.example.ugotprototype.data.community.CommunityGeneralNewPostData
import com.example.ugotprototype.databinding.ActivityDialogDeleteMessageBinding
import com.example.ugotprototype.databinding.FragmentCommunityGeneralDetailBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralChatRecyclerViewAdapter
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralChatViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralUpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CommunityGeneralDetailActivity : AppCompatActivity() {
    private lateinit var binding: FragmentCommunityGeneralDetailBinding
    private val communityGeneralChatViewModel: CommunityGeneralChatViewModel by viewModels()
    private val communityGeneralDetailViewModel: CommunityGeneralDetailViewModel by viewModels()
    private val communityGeneralUpdateViewModel: CommunityGeneralUpdateViewModel by viewModels()
    private lateinit var communityGeneralChatRecyclerViewAdapter: CommunityGeneralChatRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_community_general_detail)
        binding.vm = communityGeneralChatViewModel

        communityGeneralChatRecyclerViewAdapter = CommunityGeneralChatRecyclerViewAdapter()
        binding.rvCommunityGeneralChat.adapter = communityGeneralChatRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvCommunityGeneralChat.layoutManager = layoutManager

        communityGeneralChatViewModel.communityGeneralChatItemList.observe(this) {
            communityGeneralChatRecyclerViewAdapter.setData(it)
        }

        communityGeneralDetailViewModel.communityDetailData.observe(this) {
            binding.tvCommunityGeneralName.text = it.title
            binding.tvCommunityGeneralText.text = it.content
        }

        binding.tvCmuGeneralDelete.setOnClickListener {
            showDeleteCheckDialog()
        }

        communityGeneralUpdateViewModel.dataUpdate.observe(this) { isDataUpdate ->
            if (isDataUpdate) {
                setResult(Activity.RESULT_OK, Intent())
                finish()
            }
        }

        //댓글 추가
        communityGeneralChatViewModel.communityGeneralChatCreateItemList.observe(this) {
            communityGeneralChatViewModel.newCommunityCommentData(intent.getIntExtra(GENERAL_ID,0), it)
        }

        communityGeneralChatViewModel.getCommunityDetailList(intent.getIntExtra(GENERAL_ID,0))

        goToUpdateResult()
        dataGeneralSet()
        chatInputBtn()
        changeMyGeneralChatCount()
        goBackCommunityGeneralUpdate()
    }

    private fun goBackCommunityGeneralUpdate() {
        binding.ivCommunityGeneralBack.setOnClickListener {
            finish()
        }
    }

    private fun goToUpdateResult() {
        val goToUpdateResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    communityGeneralDetailViewModel.getCommunityDetailList(intent.getIntExtra(GENERAL_ID, 0))
                }
            }

        binding.tvCmuGeneUpdate.setOnClickListener {
            goToUpdateResultLauncher.launch(
                Intent(
                    applicationContext,
                    CommunityGeneralUpdateGroupActivity::class.java
                ).putExtra(GENERAL_ID, intent.getIntExtra(GENERAL_ID, 0))
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dataGeneralSet() {
        with(binding) {
            tvCommunityGeneralName.text =
                intent.getStringExtra(CommunityGeneralFragment.GENERAL_TITLE)
            tvCommunityGeneralNickname.text =
                intent.getStringExtra(CommunityGeneralFragment.GENERAL_NICKNAME)
            tvCommunityGeneralText.text =
                intent.getStringExtra(CommunityGeneralFragment.GENERAL_CONTENT)
            tvCommunityGeneralTime.text =
                LocalDateTime.parse(intent.getStringExtra((CommunityGeneralFragment.GENERAL_CREATE_AT)))
                    ?.format(
                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    ) ?: ""
            tvCommunityGeneralCnt.text =
                intent.getIntExtra(CommunityGeneralFragment.GENERAL_VOTE_COUNT, 0).toString()
            tvCommunityInquireInput.text =
                intent.getIntExtra(CommunityGeneralFragment.GENERAL_VIEW_COUNT, 0).toString()
        }
    }

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
        communityGeneralDetailViewModel.deleteDetailText(intent.getIntExtra(GENERAL_ID,0))
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

    private fun checkGeneralOrganizationExistence(postId : Int) {
        communityGeneralChatViewModel.newCommunityCommentData(postId,
            CommunityGeneralCommentNewPostData(
                content = binding.generalChatInput.text.toString(),
                status = binding.tvStatus.text.toString(),
            )
        )
    }
}

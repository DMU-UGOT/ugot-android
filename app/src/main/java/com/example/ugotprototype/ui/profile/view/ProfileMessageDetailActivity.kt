package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.profile.ProfileMessageCommentNewPostData
import com.example.ugotprototype.databinding.ActivityProfileMessageDetailBinding
import com.example.ugotprototype.ui.profile.adapter.ProfileMessageDetailRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.view.MessageActivity.Companion.ROOM_ID
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMessageDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileMessageDetailBinding
    private val profileMessageDetailViewModel: ProfileMessageDetailViewModel by viewModels()
    private lateinit var profileMessageDetailRecyclerViewAdapter: ProfileMessageDetailRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_message_detail)

        binding.btMessageDetailBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        profileMessageDetailRecyclerViewAdapter = ProfileMessageDetailRecyclerViewAdapter(profileMessageDetailViewModel)
        binding.rvProfileMessageDetail.adapter = profileMessageDetailRecyclerViewAdapter

        profileMessageDetailViewModel.getMessageChatData.observe(this) {
            profileMessageDetailRecyclerViewAdapter.setData(it)
        }

        profileMessageDetailViewModel.getMessageChatData.observe(this) { updatedData ->
            profileMessageDetailRecyclerViewAdapter.setData(updatedData)
        }

        profileMessageDetailViewModel.isDeleteVisible.observe(this) {
            profileMessageDetailRecyclerViewAdapter.updateAllItemsVisibility(it)
        }

        binding.tvMessageChatDelete.setOnClickListener {
            profileMessageDetailViewModel.toggleDeleteVisibility()
        }

        profileMessageDetailViewModel.isDelete.observe(this) {
            if (it) {
                onStart()
            }
        }

        chatInputBtn()
    }

    private fun chatInputBtn() {
        binding.btMessageDetailChatInput.setOnClickListener {
            if(binding.etMessageDetailChatInput.text.toString().isBlank()){
                showErrorMessage()
            }
            else {
                checkGeneralOrganizationExistence(intent.getIntExtra(ROOM_ID, 0))
                binding.etMessageDetailChatInput.text.clear()
                setResult(Activity.RESULT_OK, Intent())
                hideKeyboard()
            }
        }
    }

    private fun checkGeneralOrganizationExistence(roomId: Int) {
        profileMessageDetailViewModel.newMessageChatList(
            roomId,
            ProfileMessageCommentNewPostData(
                content = binding.etMessageDetailChatInput.text.toString()
            )
        )
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

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etMessageDetailChatInput.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        profileMessageDetailViewModel.getMessageChatList(intent.getIntExtra(ROOM_ID, 0))
        profileMessageDetailViewModel.setDeleteVisibility()
    }
}
package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupCommunityBinding
import com.example.ugotprototype.ui.group.adapter.GroupCommunityRecyclerViewAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupCmuChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupCommunityBinding
    private val groupCmuChatViewModel: GroupCmuChatViewModel by viewModels()

    private lateinit var groupCommunityRecyclerViewAdapter: GroupCommunityRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_community)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        binding.rvGroupCmuChat.layoutManager = layoutManager
        binding.rvGroupCmuChat.isNestedScrollingEnabled = false

        groupCommunityRecyclerViewAdapter = GroupCommunityRecyclerViewAdapter(groupCmuChatViewModel)
        binding.rvGroupCmuChat.adapter = groupCommunityRecyclerViewAdapter


        groupCmuChatViewModel.messageList.observe(this) {
            groupCommunityRecyclerViewAdapter.setData(it)
            binding.nsvGroupCmuDetail.scrollTo(0, binding.nsvGroupCmuDetail.getChildAt(0).height)
        }

        groupCmuChatViewModel.messageCreated.observe(this) {
            if (it) {
                groupCmuChatViewModel.getMessageList(intent.getIntExtra(GROUP_ID, 0))
            }
        }

        binding.tvGroupChatDelete.setOnClickListener {
            groupCmuChatViewModel.getNickname(intent.getIntExtra(GROUP_ID, 0))
        }

        groupCmuChatViewModel.nickname.observe(this) {
            groupCmuChatViewModel.iconView(intent.getIntExtra(GROUP_ID, 0))
        }

        groupCmuChatViewModel.isDeleteConversation.observe(this) {
            onStart()
        }


        backGroupCommunityToMainActivity()
        chatInputBtn()
    }

    private fun backGroupCommunityToMainActivity() {
        binding.ivGroupCmuBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun chatInputBtn() {
        binding.btGroupCmuChatInput.setOnClickListener {
            groupCmuChatViewModel.sendMessage(
                binding.groupCmuChatInput.text.toString(), intent.getIntExtra(GROUP_ID, 0)
            )
            binding.groupCmuChatInput.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.groupCmuChatInput.windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        groupCmuChatViewModel.getMessageList(intent.getIntExtra(GROUP_ID, 0))
    }
}
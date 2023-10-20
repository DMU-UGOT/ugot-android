package com.example.ugotprototype.ui.profile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMessageBinding
import com.example.ugotprototype.ui.profile.adapter.ProfileMessageRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileMessageBinding
    private val profileMessageViewModel: ProfileMessageViewModel by viewModels()
    private lateinit var profileMessageRecyclerViewAdapter: ProfileMessageRecyclerViewAdapter

    companion object {
        const val ROOM_ID = "roomId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_message)
        binding.vm = profileMessageViewModel

        binding.btMessageBackToMain.setOnClickListener {
            finish()
        }

        profileMessageRecyclerViewAdapter = ProfileMessageRecyclerViewAdapter(profileMessageViewModel)
        binding.rvProfileMessage.adapter = profileMessageRecyclerViewAdapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvProfileMessage.layoutManager = layoutManager

        profileMessageViewModel.getMessageData.observe(this) {
            profileMessageRecyclerViewAdapter.setData(it)
        }

        binding.btProfileDelete.setOnClickListener {
            profileMessageViewModel.toggleDeleteVisibility()
        }

        profileMessageViewModel.isDeleteVisible.observe(this) {
            profileMessageRecyclerViewAdapter.updateAllItemsVisibility(it)
        }

        profileMessageViewModel.isDelete.observe(this) {
            if (it) {
                onStart()
            }
        }

        changeMyGeneralChatCount()
    }

    private fun changeMyGeneralChatCount() {
        profileMessageViewModel.itemCount.observe(this) { count ->
            binding.tvCommunityGeneralCnt.text = count.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        profileMessageViewModel.getMessageList()
        profileMessageViewModel.setDeleteVisibility()
    }
}
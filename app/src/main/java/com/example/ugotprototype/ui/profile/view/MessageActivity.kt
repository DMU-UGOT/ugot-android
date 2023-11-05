package com.example.ugotprototype.ui.profile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMessageBinding
import com.example.ugotprototype.ui.profile.adapter.ProfileMessageRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.view.ProfileFragment.Companion.NICKNAME
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

        binding.btMessageBackToMain.setOnClickListener {
            finish()
        }

        binding.tvMessageMyName.text = intent.getStringExtra(NICKNAME)

        profileMessageRecyclerViewAdapter = ProfileMessageRecyclerViewAdapter(profileMessageViewModel)
        binding.rvProfileMessage.adapter = profileMessageRecyclerViewAdapter

        profileMessageViewModel.getMessageData.observe(this) {
            profileMessageRecyclerViewAdapter.setData(it)
        }

        profileMessageViewModel.getMessageData.observe(this) { updatedData ->
            profileMessageRecyclerViewAdapter.setData(updatedData)
        }

        profileMessageViewModel.isDeleteVisible.observe(this) {
            profileMessageRecyclerViewAdapter.updateAllItemsVisibility(it)
        }

        binding.tvProfileDelete.setOnClickListener {
            profileMessageViewModel.toggleDeleteVisibility()
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
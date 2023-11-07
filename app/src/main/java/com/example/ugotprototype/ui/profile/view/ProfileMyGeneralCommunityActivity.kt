package com.example.ugotprototype.ui.profile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyGeneralCommunityBinding
import com.example.ugotprototype.ui.profile.adapter.ProfilePostGeneralRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMyGeneralCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMyGeneralCommunityBinding
    private val viewModel: ProfileMyGeneralPostViewModel by viewModels()
    private lateinit var profilePostGeneralRecyclerViewAdapter: ProfilePostGeneralRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my_general_community)

        binding.ivProfileGeneralCmuBack.setOnClickListener {
            finish()
        }

        profilePostGeneralRecyclerViewAdapter = ProfilePostGeneralRecyclerViewAdapter(viewModel)
        binding.rvGeneral.adapter = profilePostGeneralRecyclerViewAdapter

        viewModel.generalItemList.observe(this) {
            profilePostGeneralRecyclerViewAdapter.setData(it)
        }

        viewModel.generalItemList.observe(this) { updatedData ->
            profilePostGeneralRecyclerViewAdapter.setData(updatedData)
        }

        binding.tvProfileGeneralDelete.setOnClickListener {
            viewModel.toggleDeleteVisibility()
        }

        viewModel.isDeleteVisible.observe(this) {
            profilePostGeneralRecyclerViewAdapter.updateAllItemsVisibility(it)
        }

        viewModel.isDeletePost.observe(this) {
            if (it) {
                onStart()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMyPost()
        viewModel.setDeleteVisibility()
    }
}
package com.example.ugotprototype.ui.profile.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyChangeCommunityBinding
import com.example.ugotprototype.ui.profile.adapter.ProfilePostChangeRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyChangePostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMyChangeCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMyChangeCommunityBinding
    private val viewModel: ProfileMyChangePostViewModel by viewModels()
    private lateinit var profilePostChangeRecyclerViewAdapter: ProfilePostChangeRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my_change_community)

        binding.ivProfileChangeCmuBack.setOnClickListener {
            finish()
        }

        profilePostChangeRecyclerViewAdapter = ProfilePostChangeRecyclerViewAdapter()
        binding.rvChange.adapter = profilePostChangeRecyclerViewAdapter

        viewModel.changeItemList.observe(this) {
            profilePostChangeRecyclerViewAdapter.setData(it)
        }

        viewModel.changeItemList.observe(this) { updatedData ->
            profilePostChangeRecyclerViewAdapter.setData(updatedData)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMyChangePost()
    }
}
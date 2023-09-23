package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyPostTeamBinding
import com.example.ugotprototype.ui.profile.adapter.ProfilePostRecyclerviewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyTeamPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileMyPostTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMyPostTeamBinding
    private val viewModel: ProfileMyTeamPostViewModel by viewModels()
    private lateinit var profilePostRecyclerviewAdapter: ProfilePostRecyclerviewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my_post_team)

        viewLoadingLayout()

        binding.ivGroupCmuBack.setOnClickListener {
            finish()
        }

        profilePostRecyclerviewAdapter = ProfilePostRecyclerviewAdapter(viewModel)
        binding.rvTeam.adapter = profilePostRecyclerviewAdapter

        viewModel.teamItemList.observe(this) {
            profilePostRecyclerviewAdapter.setData(it)
        }

        binding.tvGroupDelete.setOnClickListener {
            viewModel.toggleDeleteVisibility()
        }

        viewModel.isDeleteVisible.observe(this) {
            profilePostRecyclerviewAdapter.updateAllItemsVisibility(it)
        }

        viewModel.isDeletePost.observe(this) {
            if (it) {
                onStart()
            }
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMyPost()
        viewModel.setDeleteVisibility()
    }
}
package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileTeamBookmarkBinding
import com.example.ugotprototype.ui.profile.adapter.ProfilePostRecyclerviewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyBookMarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileTeamBookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileTeamBookmarkBinding
    private val viewModel: ProfileMyBookMarkViewModel by viewModels()
    private lateinit var profilePostRecyclerviewAdapter: ProfilePostRecyclerviewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_team_bookmark)

        viewLoadingLayout()

        binding.ivGroupCmuBack.setOnClickListener {
            finish()
        }

        profilePostRecyclerviewAdapter = ProfilePostRecyclerviewAdapter()
        binding.rvTeam.adapter = profilePostRecyclerviewAdapter

        viewModel.teamItemList.observe(this) {
            profilePostRecyclerviewAdapter.setData(it)
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
        viewModel.getMyBookmark()
    }
}
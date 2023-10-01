package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileTeamBookmarkBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.profile.adapter.ProfilePostRecyclerviewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyTeamPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileTeamBookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileTeamBookmarkBinding
    private val viewModel: ProfileMyTeamPostViewModel by viewModels()
    private lateinit var profilePostRecyclerviewAdapter: ProfilePostRecyclerviewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_team_bookmark)

        binding.ivGroupCmuBack.setOnClickListener {
            finish()
        }

        profilePostRecyclerviewAdapter = ProfilePostRecyclerviewAdapter(viewModel)
        binding.rvTeam.adapter = profilePostRecyclerviewAdapter

        viewModel.teamItemList.observe(this) {
            profilePostRecyclerviewAdapter.setData(it)
        }

        viewModel.isLoadingPage.observe(this) {
            if(it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.getMyBookmark()
    }
}
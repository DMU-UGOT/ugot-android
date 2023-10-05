package com.example.ugotprototype.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupEngagementRateBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.group.adapter.GroupEngagementRateRecyclerViewAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_NAME
import com.example.ugotprototype.ui.group.viewmodel.GroupEngagementRateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupEngagementRateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupEngagementRateBinding
    private lateinit var groupEngagementRateRecyclerViewAdapter: GroupEngagementRateRecyclerViewAdapter

    private val viewModel: GroupEngagementRateViewModel by viewModels()

    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_engagement_rate)

        viewModel.fetchMembers(intent.getStringExtra(GROUP_NAME) ?: "")

        groupEngagementRateRecyclerViewAdapter = GroupEngagementRateRecyclerViewAdapter()
        binding.rvRating.adapter = groupEngagementRateRecyclerViewAdapter

        viewModel.engagementRateData.observe(this) {
            groupEngagementRateRecyclerViewAdapter.setData(it)
        }

        binding.ibEngagementPrev.setOnClickListener {
            finish()
        }

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }
    }
}
package com.example.ugotprototype.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupRequestApplicationBinding
import com.example.ugotprototype.ui.group.adapter.GroupRequestApplicationAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupRequestApplicationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupRequestApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupRequestApplicationBinding
    private val viewModel: GroupRequestApplicationViewModel by viewModels()
    private lateinit var adapter: GroupRequestApplicationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_request_application)

        adapter = GroupRequestApplicationAdapter(viewModel, intent.getIntExtra(GROUP_ID, 0))
        binding.rvTeam.adapter = adapter

        viewModel.getGroupApplicationList(intent.getIntExtra(GROUP_ID, 0))

        viewModel.groupRequestApplicationList.observe(this) {
            adapter.setFilterData(it)
        }

        viewModel.isUpdate.observe(this) {
            viewModel.getGroupApplicationList(intent.getIntExtra(GROUP_ID, 0))
        }

        binding.ivTeamPrev.setOnClickListener { finish() }
    }
}
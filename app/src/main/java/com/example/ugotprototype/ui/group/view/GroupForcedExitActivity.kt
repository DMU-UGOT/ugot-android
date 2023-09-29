package com.example.ugotprototype.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupForcedExitBinding
import com.example.ugotprototype.ui.group.adapter.GroupForcedExitAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupForcedExitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupForcedExitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupForcedExitBinding
    private val viewModel: GroupForcedExitViewModel by viewModels()
    private lateinit var adapter: GroupForcedExitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_forced_exit)

        adapter = GroupForcedExitAdapter(viewModel, intent.getIntExtra(GROUP_ID, 0))
        binding.rvGroup.adapter = adapter

        viewModel.getGroupPersonList(intent.getIntExtra(GROUP_ID, 0))

        viewModel.teamPersonData.observe(this) {
            adapter.setFilterData(it)
        }

        viewModel.isUpdate.observe(this) {
            viewModel.getGroupPersonList(intent.getIntExtra(GROUP_ID, 0))
        }

        binding.ivTeamPrev.setOnClickListener { finish() }
    }
}
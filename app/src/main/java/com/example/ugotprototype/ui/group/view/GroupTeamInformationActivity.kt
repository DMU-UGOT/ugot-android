package com.example.ugotprototype.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupTeamInformationBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupTeamInformationViewModel
import com.example.ugotprototype.ui.team.adapter.GroupTeamInforRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupTeamInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupTeamInformationBinding
    private var rvAdapter: GroupTeamInforRecyclerViewAdapter = GroupTeamInforRecyclerViewAdapter()
    private val viewModel: GroupTeamInformationViewModel by viewModels()
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_team_information)

        viewLoadingLayout()

        binding.rvTeamInformation.adapter = rvAdapter

        viewModel.getTeamInformationData(intent.getIntExtra(GROUP_ID, 0))

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        viewModel.teamPersonData.observe(this) {
            rvAdapter.setData(it)
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
}
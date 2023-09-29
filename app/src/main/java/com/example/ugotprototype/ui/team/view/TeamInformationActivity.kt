package com.example.ugotprototype.ui.team.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamInformationBinding
import com.example.ugotprototype.ui.team.adapter.TeamInformationRecyclerViewAdapter
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import com.example.ugotprototype.ui.team.viewmodel.TeamInformationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamInformationBinding
    private var rvAdapter: TeamInformationRecyclerViewAdapter = TeamInformationRecyclerViewAdapter()
    private val teamInformationViewModel: TeamInformationViewModel by viewModels()
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_information)
        binding.rvTeamInformation.adapter = rvAdapter

        viewLoadingLayout()

        teamInformationViewModel.getTeamInformationList(intent.getIntExtra(TEAM_ID, 0))

        Log.d("test", intent.getIntExtra(TEAM_ID, 0).toString())

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        teamInformationViewModel.teamInforList.observe(this) {
            rvAdapter.setData(it)
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        teamInformationViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }
}
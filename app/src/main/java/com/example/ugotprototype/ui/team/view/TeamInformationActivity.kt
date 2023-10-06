package com.example.ugotprototype.ui.team.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamInformationBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.team.adapter.TeamInformationRecyclerViewAdapter
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import com.example.ugotprototype.ui.team.viewmodel.TeamInformationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamInformationBinding
    private var rvAdapter: TeamInformationRecyclerViewAdapter = TeamInformationRecyclerViewAdapter()
    private val teamInformationViewModel: TeamInformationViewModel by viewModels()
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_information)
        binding.rvTeamInformation.adapter = rvAdapter

        teamInformationViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        teamInformationViewModel.getTeamInformationList(intent.getIntExtra(TEAM_ID, 0))

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        teamInformationViewModel.teamInforList.observe(this) {
            rvAdapter.setData(it)
        }
    }
}
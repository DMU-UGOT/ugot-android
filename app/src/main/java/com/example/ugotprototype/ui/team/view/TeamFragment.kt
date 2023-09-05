package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.login.view.LoginActivity
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    companion object {
        const val TOKEN_DATA = BuildConfig.GITHUB_KEY
        const val TEAM_TITLE = "teamTitle"
        const val TEAM_DETAIL = "teamDetail"
        const val TEAM_TOPIC = "teamTopic"
        const val TEAM_STATUS_CNT = "teamStatusCnt"
        const val TEAM_STATUS_CNT_END = "teamStatusCntEnd"
        const val TEAM_LEADER_CLASS = "teamLeaderClass"
        const val TEAM_GITHUB_LINK = "teamGitHubLink"
        const val TEAM_KAKAO_LINK = "teamKakaoLink"
        const val TEAM_CREATE_TIME = "teamCreateTime"
        const val TEAM_STATUS = "teamStatus"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        binding.vm = teamViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLoadingLayout()

        teamViewModel.setCurrentPage(1)
        teamViewModel.setTotalPage(1)
        teamViewModel.getTeamList()

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter()
        binding.rvTeam.adapter = teamRecyclerViewAdapter

        teamViewModel.teamItemList.observe(viewLifecycleOwner) {
            teamRecyclerViewAdapter.setData(it)
        }

        teamViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.teamPageSecondText.text = it.toString()
        }

        teamViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.teamPageFirstText.text = it.toString()
            teamViewModel.getTeamList()
        }

        teamViewModel.isTokenExpired.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        goToTeamSearchDetail()
        goToTeamPostWriteDetail()
    }

    private fun goToTeamSearchDetail() {

        val goToSearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                }
            }

        binding.btGoDetailSearch.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(
                    requireContext(), TeamSearchDetailActivity::class.java
                )
            )
        }
    }

    private fun goToTeamPostWriteDetail() {

        val goToPostWriteResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    loadingDialog.show(requireActivity().supportFragmentManager, "loadingDialog")
                    teamViewModel.getTeamList()
                }
            }

        binding.fabTeam.setOnClickListener {
            goToPostWriteResultLauncher.launch(
                Intent(
                    requireContext(), TeamPostWriteDetailActivity::class.java
                )
            )
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.show(requireActivity().supportFragmentManager, "loadingDialog")

        teamViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
        }
    }
}
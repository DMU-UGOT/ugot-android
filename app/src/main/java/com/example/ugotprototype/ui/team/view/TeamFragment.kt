package com.example.ugotprototype.ui.team.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.R
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.login.view.LoginActivity
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class TeamFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy {
        LoadingLayoutHelper(
            parentFragmentManager
        )
    }
    private lateinit var sharedPreference: SharedPreference

    companion object {
        const val TEAM_ID = "teamId"
        const val TOKEN_DATA = BuildConfig.GITHUB_KEY
        const val TEAM_STATUS = "teamStatus"
        const val TEAM_PERSON_CNT = "teamPersonCnt"
        const val TEAM_GITHUB_LINK = "teamGitHubLink"
        var SKILL_LIST = listOf("", "")
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

        sharedPreference = SharedPreference(requireContext())

        teamViewModel.setCurrentPage(1)
        teamViewModel.setTotalPage(1)

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter(teamViewModel)
        binding.rvTeam.adapter = teamRecyclerViewAdapter

        teamViewModel.teamItemList.observe(viewLifecycleOwner) {
            teamRecyclerViewAdapter.setData(it)
        }

        teamViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.teamPageSecondText.text = it.toString()
        }

        teamViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.teamPageFirstText.text = it.toString()
        }

        teamViewModel.isTokenExpired.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        teamViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        teamViewModel.skillList.observe(viewLifecycleOwner) {
            SKILL_LIST = it
            Log.d("skill1", SKILL_LIST.toString())
        }

        goToTeamSearchDetail()
        goToTeamPostWriteDetail()
    }

    private fun goToTeamSearchDetail() {
        binding.btGoDetailSearch.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(), TeamSearchDetailActivity::class.java
                )
            )
        }
    }

    private fun goToTeamPostWriteDetail() {
        binding.fabTeam.setOnClickListener {
            startActivity(
                Intent(requireContext(), TeamPostWriteDetailActivity::class.java)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        teamViewModel.getUserInfo()
        teamViewModel.getTeamList()
    }
}
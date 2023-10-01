package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(parentFragmentManager) }
    private lateinit var sharedPreference: SharedPreference

    companion object {
        const val TEAM_ID = "teamId"
        const val TOKEN_DATA = BuildConfig.GITHUB_KEY
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
            teamViewModel.getTeamList()
        }

        teamViewModel.isTokenExpired.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        teamViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if(it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        goToTeamSearchDetail()
        goToTeamPostWriteDetail()
    }

    private fun goToTeamSearchDetail() {

        val goToSearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    teamViewModel.getTeamList()
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
                    teamViewModel.getTeamList()
                }
            }

        binding.fabTeam.setOnClickListener {
            goToPostWriteResultLauncher.launch(
                Intent(requireContext(), TeamPostWriteDetailActivity::class.java)
            )
        }
    }
}
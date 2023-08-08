package com.example.ugotprototype.ui.team.view

import android.annotation.SuppressLint
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
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.R
import com.example.ugotprototype.data.team.TeamData
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.di.api.ApiService
import com.example.ugotprototype.di.api.BackEndService
import com.example.ugotprototype.di.api.response.TeamPostResponse
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeamFragment : Fragment() {
    @Inject
    lateinit var apiService: ApiService

    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private var teamItems: List<TeamPostResponse> = emptyList()

    @Inject
    lateinit var backEndService: BackEndService

    private var currentPage = 1
    private var totalPages = 1

    override fun onStart() {
        super.onStart()
        testData(currentPage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamViewModel.setTeamData(teamItems)

        postPageNextOrPrev()

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter()
        binding.rvTeam.adapter = teamRecyclerViewAdapter

        teamViewModel.teamItemList.observe(viewLifecycleOwner) {
            teamRecyclerViewAdapter.setData(it)
        }

        teamViewModel.postLastPage.observe(viewLifecycleOwner) {
            binding.teamPageSecondText.text = it.toString()
        }

        goToTeamSearchDetail()
        goToTeamPostWriteDetail()
    }

    private fun testData(pageNumber: Int) {
        lifecycleScope.launch {
            try {
                //val response = apiService.getOrganization("DMU-UGOT")
                //val org = response?.avatarUrl
                val response = backEndService.getTeams(pageNumber, 5)
                val teams = response.data
                teamViewModel.setTeamData(teams)
                currentPage = response.pageInfo.page
                totalPages = response.pageInfo.totalPages
                teamViewModel.setPostLastPage(totalPages)
            } catch (e: Exception) {
                Log.d("test", "$e")
            }
        }
    }

    private fun goToTeamSearchDetail() {

        val goToSearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
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
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
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

    @SuppressLint("SetTextI18n")
    private fun postPageNextOrPrev() {
        binding.btTeamPrev.setOnClickListener {
            if (currentPage > 1) {
                testData(currentPage - 1)
                binding.teamPageFirstText.text =
                    (binding.teamPageFirstText.text.toString().toInt() - 1).toString()
            } else {
                Log.d("teams", "뒤로가기버튼안됨")
            }
        }

        binding.btTeamNext.setOnClickListener {
            if (currentPage < totalPages) {
                testData(currentPage + 1)
                binding.teamPageFirstText.text =
                    (binding.teamPageFirstText.text.toString().toInt() + 1).toString()
            } else {
                Log.d("teams", "다음페이지가기버튼안됨")
            }
        }
    }
}
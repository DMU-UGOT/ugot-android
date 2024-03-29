package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.team.TeamSearchHistory
import com.example.ugotprototype.databinding.ActivityTeamSearchDetailBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.team.adapter.TeamSearchRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamSearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamSearchDetailActivity : AppCompatActivity() {

    private val teamSearchViewModel: TeamSearchViewModel by viewModels()
    private lateinit var binding: ActivityTeamSearchDetailBinding
    private lateinit var teamSearchRecyclerViewAdapter: TeamSearchRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    companion object {
        private var searchQuery: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_search_detail)

        teamSearchRecyclerViewAdapter = TeamSearchRecyclerViewAdapter(teamSearchViewModel)
        binding.rvTeam.adapter = teamSearchRecyclerViewAdapter

        teamSearchViewModel.getTeamPostSearchHistory()

        teamSearchViewModel.teams.observe(this) {
            teamSearchRecyclerViewAdapter.setData(it)
            binding.chipLayout.isVisible = false
        }

        teamSearchViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        teamSearchViewModel.postSearchHistory.observe(this) {
            searchChipSetUp(it)
        }

        teamSearchViewModel.isDelete.observe(this) {
            if(it) {
                binding.chipGroup.removeAllViews()
                teamSearchViewModel.getTeamPostSearchHistory()
            }
        }

        binding.btnDelete.setOnClickListener {
            teamSearchViewModel.allDeleteTeamPostSearchHistory()
        }

        teamSearchViewModel.isAllDelete.observe(this) {
            if(it) {
                binding.chipGroup.removeAllViews()
            }
        }

        teamSearchViewModel.isSearch.observe(this) {
            if(it) {
                binding.nsvSearchHistory.visibility = VISIBLE
            }
        }

        backToMain()
        listenerSetting()
    }


    private fun backToMain() {
        binding.btBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun listenerSetting() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                teamSearchViewModel.searchTeams(query.toString())
                searchQuery = query!!
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        binding.svTextInput.setOnQueryTextListener(queryTextListener)
    }

    private fun searchChipSetUp(data: List<TeamSearchHistory>) {
        data.forEach { chipData ->
            val chip = Chip(this).apply {
                text = chipData.keyword
                isCloseIconVisible = true
                setOnCloseIconClickListener() {
                    teamSearchViewModel.deleteTeamPostSearchHistory(text.toString())
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    override fun onStart() {
        super.onStart()
        if (searchQuery != "") {
            teamSearchViewModel.searchTeams(searchQuery)
            binding.svTextInput.setQuery(searchQuery, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchQuery = ""
    }
}
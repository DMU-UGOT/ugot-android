package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamSearchDetailBinding
import com.example.ugotprototype.di.api.BackEndService
import com.example.ugotprototype.di.api.response.TeamPostResponse
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.adapter.TeamSearchRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import com.google.android.material.chip.Chip;
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TeamSearchDetailActivity : AppCompatActivity() {

    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var binding: ActivityTeamSearchDetailBinding

    private lateinit var teamSearchRecyclerViewAdapter: TeamSearchRecyclerViewAdapter

    @Inject
    lateinit var backEndService: BackEndService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_search_detail)

        teamViewModel.teamItemList.observe(this) {
            teamSearchRecyclerViewAdapter.setData(it)
            binding.chipLayout.visibility = View.INVISIBLE
        }

        teamSearchRecyclerViewAdapter = TeamSearchRecyclerViewAdapter()
        binding.rvTeam.adapter = teamSearchRecyclerViewAdapter

        checkTextInput()
        backToMain()
    }

    private fun checkTextInput() {
        binding.svTextInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchOnServer(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    private fun backToMain() {
        binding.btBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun searchOnServer(query: String) {
        Log.d("test", "test")
        lifecycleScope.launch {
            try {
                val allTeams = mutableListOf<TeamPostResponse>()
                var currentPage = 1

                while (true) {
                    val response = backEndService.getTeams(currentPage, 5)
                    val teams = response.data ?: emptyList()
                    allTeams.addAll(teams)
                    if (currentPage >= (response.pageInfo.totalPages ?: 0)) {
                        break
                    }
                    currentPage++
                }
                val data: List<TeamPostResponse> =
                    allTeams.filter { team -> team.title.contains(query) }
                teamViewModel.setTeamData(data)
            } catch (e: Exception) {
                Log.d("test2", "$e")
            }
        }
    }
}
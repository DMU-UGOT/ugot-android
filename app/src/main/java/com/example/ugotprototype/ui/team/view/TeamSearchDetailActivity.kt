package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamSearchDetailBinding
import com.example.ugotprototype.ui.team.adapter.TeamSearchRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamSearchDetailActivity : AppCompatActivity() {

    private val teamSearchViewModel: TeamSearchViewModel by viewModels()
    private lateinit var binding: ActivityTeamSearchDetailBinding
    private lateinit var teamSearchRecyclerViewAdapter: TeamSearchRecyclerViewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_search_detail)
        viewLoadingLayout()

        teamSearchRecyclerViewAdapter = TeamSearchRecyclerViewAdapter(teamSearchViewModel)
        binding.rvTeam.adapter = teamSearchRecyclerViewAdapter

        teamSearchViewModel.teams.observe(this) {
            teamSearchRecyclerViewAdapter.setData(it)
            binding.chipLayout.isVisible = false
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
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        binding.svTextInput.setOnQueryTextListener(queryTextListener)
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        teamSearchViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }
}
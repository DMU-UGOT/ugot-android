package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityMypageMybookmarkBinding
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageMybookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageMybookmarkBinding
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_mybookmark)
        binding.vm = teamViewModel

        teamViewModel.setCurrentPage(1)
        teamViewModel.setTotalPage(1)
        teamViewModel.getTeamList()

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter()
        binding.rvMyBookmark.adapter = teamRecyclerViewAdapter

        teamViewModel.teamItemList.observe(this) {
            teamRecyclerViewAdapter.setData(it)
        }

        teamViewModel.totalPage.observe(this) {
            binding.tvMyBookmarkSecondCnt.text = it.toString()
        }

        teamViewModel.currentPage.observe(this) {
            binding.tvMyBookmarkFirstCnt.text = it.toString()
            teamViewModel.getTeamList()
        }

        backToMain()
    }

    private fun backToMain() {
        binding.ivMyPagePrev.setOnClickListener {
            finish()
        }
    }
}
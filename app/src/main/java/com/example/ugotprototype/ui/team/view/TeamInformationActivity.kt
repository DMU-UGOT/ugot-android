package com.example.ugotprototype.ui.team.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamInformationBinding
import com.example.ugotprototype.ui.team.adapter.TeamInformationRecyclerViewAdapter

class TeamInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamInformationBinding
    private var rvAdapter: TeamInformationRecyclerViewAdapter = TeamInformationRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_information)

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        dataSet()
    }

    private fun dataSet() {
        val nowPersonCnt: Int = intent.getStringExtra("nowPersonCnt")?.toInt() ?: 0

        binding.rvTeamInformation.adapter = rvAdapter
        rvAdapter.setData(nowPersonCnt)
    }
}
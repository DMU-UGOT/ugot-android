package com.example.ugotprototype.ui.group.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupTeamInformationBinding
import com.example.ugotprototype.ui.team.adapter.GroupTeamInforRecyclerViewAdapter

class GroupTeamInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupTeamInformationBinding
    private var rvAdapter: GroupTeamInforRecyclerViewAdapter = GroupTeamInforRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_team_information)

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
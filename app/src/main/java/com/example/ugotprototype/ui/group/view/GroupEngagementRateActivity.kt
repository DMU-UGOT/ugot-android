package com.example.ugotprototype.ui.group.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupEngagementRateData
import com.example.ugotprototype.data.group.GroupTopViewData
import com.example.ugotprototype.databinding.ActivityGroupDetailCalendarBinding
import com.example.ugotprototype.databinding.ActivityGroupEngagementRateBinding
import com.example.ugotprototype.ui.group.adapter.GroupEngagementRateRecyclerViewAdapter
import com.example.ugotprototype.ui.group.adapter.GroupTopViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel

class GroupEngagementRateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupEngagementRateBinding
    private lateinit var groupEngagementRateRecyclerViewAdapter: GroupEngagementRateRecyclerViewAdapter
    private var groupEngagementRateItem = ArrayList<GroupEngagementRateData>()

    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_engagement_rate)

        groupEngagementRateRecyclerViewAdapter = GroupEngagementRateRecyclerViewAdapter()
        binding.rvRating.adapter = groupEngagementRateRecyclerViewAdapter

        testData()
        groupViewModel.setEngagementRate(groupEngagementRateItem)

        groupViewModel.engagementRateData.observe(this) {
            groupEngagementRateRecyclerViewAdapter.setData(groupEngagementRateItem)
        }
    }

    private fun testData() {
        groupEngagementRateItem = arrayListOf(
            GroupEngagementRateData(
                1, "testName1", 30
            ),GroupEngagementRateData(
                2, "testName2", 12
            ),GroupEngagementRateData(
                3, "testName3", 11
            ),GroupEngagementRateData(
                4, "testName4", 10
            ),GroupEngagementRateData(
                5, "testName5", 9
            ),GroupEngagementRateData(
                6,"testName6", 8
            ),GroupEngagementRateData(
                7, "testName7", 7
            ),GroupEngagementRateData(
                8, "testName8", 6
            ),GroupEngagementRateData(
                9, "testName9", 5
            ),GroupEngagementRateData(
                10,"testName10", 4
            ),GroupEngagementRateData(
                11,"testName11", 3
            ),GroupEngagementRateData(
                12,"testName12", 3
            ),GroupEngagementRateData(
                13,"testName13", 3
            ),GroupEngagementRateData(
                14,"testName14", 2
            ),GroupEngagementRateData(
                15,"testName15", 1
            ),GroupEngagementRateData(
                16,"testName16", 0
            )
        )
    }
}
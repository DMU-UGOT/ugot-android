package com.example.ugotprototype.ui.team.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel

class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()
    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter()
        binding.rvTeam.adapter = teamRecyclerViewAdapter

        binding.teamViewModel = teamViewModel
        teamViewModel.teamItemList.observe(viewLifecycleOwner, Observer {
            teamRecyclerViewAdapter.setData(it)
        })


        return binding.root
    }
}
package com.example.ugotprototype.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.viewmodel.TeamViewModel

class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    lateinit var teamViewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        teamViewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        binding.teamViewModel = teamViewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}
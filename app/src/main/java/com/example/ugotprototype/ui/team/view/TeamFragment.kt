package com.example.ugotprototype.ui.team.view

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
import com.example.ugotprototype.R
import com.example.ugotprototype.data.team.TeamData
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel


class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private var teamItems = ArrayList<TeamData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        binding.vm = teamViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testData()
        teamViewModel.setTeamData(teamItems)

        teamRecyclerViewAdapter = TeamRecyclerViewAdapter()
        binding.rvTeam.adapter = teamRecyclerViewAdapter

        teamViewModel.teamItemList.observe(viewLifecycleOwner) {
            teamRecyclerViewAdapter.setData(it)
        }

        goToTeamSearchDetail()
        goToTeamPostWriteDetail()
    }

    private fun testData() {
        teamItems = arrayListOf(
            TeamData(
                "스타트업 안드로이드 플랫폼 꾸준히 이어나갈 팀원 모집합니다",
                "스타트업의 팀원을 찾을 수 있는 모바일 앱을 만들 인원을 구합니다 저는 현재 모바일 창업을 맡고있는 사람으로 해당프로젝트는 플랫폼 서비스의 극초기 모델로 고객 유입을 최대한 이끌어내며 그 이후 비즈니스 모델에 대한 가설을 세울 수 있는 환경을 구축해야한다",
                "Android",
                "3",
                "8",
                "조회수 : 8",
                "YJ"
            ), TeamData(
                "Web 팀원 모집", "Web FrontEnd 팀원모집중", "FrontEnd", "1", "7", "조회수 : 4", "YJ"
            ), TeamData(
                "BackEnd 모집중", "BackEnd 팀원 모집중", "BackEnd", "6", "9", "조회수 : 4", "YK"
            ), TeamData(
                "SoftWare 개발 팀원 구인중", "SoftWare 개발 팀원 구인중", "Software", "4", "4", "조회수 : 4", "YJ"
            ), TeamData(
                "Security 팀원 모집중", "Security 팀원 모집중", "Security", "3", "5", "조회수 : 4", "YK"
            )
        )
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
}
package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.R
import com.example.ugotprototype.data.TeamData
import com.example.ugotprototype.databinding.FragmentTeamBinding
import com.example.ugotprototype.ui.team.adapter.TeamRecyclerViewAdapter
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel


class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private var teamItems = ArrayList<TeamData>()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team, container, false)
        binding.teamViewModel = teamViewModel

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

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }
        
        goToTeamSearchDetail()
    }

    private fun testData() {
        teamItems = arrayListOf(
            TeamData(
                "DmuIT 프로젝트를 함께 할 팀원모집",
                "팀명은 UGOT에 프로젝트명은 DmuIT로 동양미래대학교의 컴퓨터공학부 팀프로젝트에서 반 변경을 위한 앱입니다",
                "분야 : Android",
                "인원 : 3/8",
                "댓글 : 8"
            ),
            TeamData(
                "Web 팀원 모집",
                "Web FrontEnd 팀원모집중",
                "분야 : FrontEnd",
                "인원 : 2/4",
                "댓글 : 4"
            ),
            TeamData(
                "Web 팀원 모집",
                "Web FrontEnd 팀원모집중",
                "분야 : FrontEnd",
                "인원 : 2/4",
                "댓글 : 4"
            ),
            TeamData(
                "Web 팀원 모집",
                "Web FrontEnd 팀원모집중",
                "분야 : FrontEnd",
                "인원 : 2/4",
                "댓글 : 4"
            ),
            TeamData(
                "Web 팀원 모집",
                "Web FrontEnd 팀원모집중",
                "분야 : FrontEnd",
                "인원 : 2/4",
                "댓글 : 4"
            )
        )
    }

    private fun goToTeamSearchDetail() {
        binding.btGoDetailSearch.setOnClickListener {
            resultLauncher.launch(Intent(requireContext(), TeamSearchDetailActivity::class.java))
        }
    }
}
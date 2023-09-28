package com.example.ugotprototype.ui.team.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.databinding.ActivityTeamPostDetailBinding
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS
import com.example.ugotprototype.ui.team.viewmodel.TeamPostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class TeamPostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamPostDetailBinding
    private lateinit var teamStatusCnt: String
    private val viewModel: TeamPostDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_post_detail)

        viewModel.viewSetting(intent.getIntExtra(TEAM_ID, 0))

        viewModel.teamDetailData.observe(this) {
            initData(it)
        }

        viewModel.isDuplicateGroupPerson.observe(this) {
            if (it) {
                Toast.makeText(this, "신청이 성공적으로 됐습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "가입할 수 없는 그룹 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGroupApplication.setOnClickListener {
            viewModel.sendApplication(intent.getIntExtra(TEAM_ID, 0))
        }

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        goToTeamInformation()
    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, TeamInformationActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener {
            Intent(this, TeamInformationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData(data: Team) {
        with(binding) {
            tvTeamNickName.text = data.nickname
            teamStatusCnt = data.nowPersonnel.toString()
            tvPostTitle.text = data.title
            tvTeamPostDetail.text = data.content
            tvProjectField.text = data.field
            tvTotalPersonCntFirst.text = teamStatusCnt
            tvTotalPersonCntEnd.text = data.allPersonnel.toString()
            tvPersonCntCheck.text = intent.getStringExtra(TEAM_STATUS)
            tvNowClassText.text = data._class
            tvGithubLink.text = "https://github.com/" + data.gitHubLink
            tvKakaoLink.text = "https://" + data.kakaoOpenLink
            tvTime.text = LocalDateTime.parse(data.createdAt)?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            ) ?: ""
            tvPostField.text = data.language
            tvTarget.text = data.goal
            tvGroupName.text = data.groupName
        }
    }
}
package com.example.ugotprototype.ui.team.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamPostDetailBinding
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_CREATE_TIME
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_DETAIL
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GITHUB_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_KAKAO_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_LEADER_CLASS
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS_CNT
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS_CNT_END
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_TITLE
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_TOPIC
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TeamPostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamPostDetailBinding
    private lateinit var teamStatusCnt: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_post_detail)

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        dataSet()
        goToTeamInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun dataSet() {

        with(binding) {
            teamStatusCnt = intent.getIntExtra(TEAM_STATUS_CNT, 0).toString()
            tvPostTitle.text = intent.getStringExtra(TEAM_TITLE)
            tvTeamPostDetail.text = intent.getStringExtra(TEAM_DETAIL)
            tvProjectField.text = intent.getStringExtra(TEAM_TOPIC)
            tvTotalPersonCntFirst.text = teamStatusCnt
            tvTotalPersonCntEnd.text = intent.getIntExtra(TEAM_STATUS_CNT_END, 0).toString()
            tvPersonCntCheck.text = intent.getStringExtra(TEAM_STATUS)
            tvNowClassText.text = intent.getStringExtra(TEAM_LEADER_CLASS)
            tvGithubLink.text = "https://github.com/" + intent.getStringExtra(TEAM_GITHUB_LINK)
            tvKakaoLink.text = "https://" + intent.getStringExtra(TEAM_KAKAO_LINK)
            tvTime.text = LocalDateTime.parse(intent.getStringExtra((TEAM_CREATE_TIME)))?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))?: ""
        }
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
}
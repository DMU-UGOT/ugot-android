package com.example.ugotprototype.ui.team.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamPostDetailBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

        this.teamStatusCnt = intent.getIntExtra("teamStatusCnt", 0).toString()

        binding.tvPostField.text = intent.getStringExtra("teamTopic")
        binding.tvPostTitle.text = intent.getStringExtra("teamTitle")
        binding.tvTeamPostDetail.text = intent.getStringExtra("teamDetail")
        binding.tvProjectField.text = intent.getStringExtra("teamTopic")
        binding.tvTotalPersonCntFirst.text = teamStatusCnt
        binding.tvTotalPersonCntEnd.text = intent.getIntExtra("teamStatusCntEnd", 0).toString()
        binding.tvPersonCntCheck.text = intent.getStringExtra("teamCurrent")
        binding.tvNowClassText.text = intent.getStringExtra("teamLeaderClass")
        binding.tvGithubLink.text = "https://github.com/" + intent.getStringExtra("teamGitHubLink")
        binding.tvKakaoLink.text = intent.getStringExtra("teamKakaoLink")

        val formattedDateTime = LocalDateTime.parse(intent.getStringExtra(("teamCreateTime"))).format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
        binding.tvTime.text = formattedDateTime
    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, TeamInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", teamStatusCnt)
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener {
            Intent(this, TeamInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", teamStatusCnt)
                startActivity(this)
            }
        }
    }
}
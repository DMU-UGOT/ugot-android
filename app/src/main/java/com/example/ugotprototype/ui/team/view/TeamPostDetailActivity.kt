package com.example.ugotprototype.ui.team.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamPostDetailBinding

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

    private fun dataSet() {

        this.teamStatusCnt = intent.getStringExtra("teamStatusCnt").toString()

        binding.tvPostField.text = intent.getStringExtra("teamTopic")
        binding.tvPostTitle.text = intent.getStringExtra("teamTitle")
        binding.tvTeamPostDetail.text = intent.getStringExtra("teamDetail")
        binding.tvProjectField.text = intent.getStringExtra("teamTopic")
        binding.tvTotalPersonCntFirst.text = teamStatusCnt
        binding.tvTotalPersonCntEnd.text = intent.getStringExtra("teamStatusCntEnd")
        binding.tvPersonCntCheck.text = intent.getStringExtra("teamCurrent")

    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, TeamInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", teamStatusCnt)
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener{
            Intent(this, TeamInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", teamStatusCnt)
                startActivity(this)
            }
        }
    }
}
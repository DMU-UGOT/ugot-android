package com.example.ugotprototype.ui.profile.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ActivityProfileStudyPostDetailBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyStudyPostDetailViewModel
import com.example.ugotprototype.ui.study.view.StudyInformationActivity
import com.example.ugotprototype.ui.team.view.TeamFragment
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GITHUB_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_PERSON_CNT
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ProfileStudyPostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileStudyPostDetailBinding
    private lateinit var teamStatusCnt: String
    private val viewModel: ProfileMyStudyPostDetailViewModel by viewModels()
    private var totalPersonCount: Int = 0
    private var gitHubLink: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_study_post_detail)

        binding.ivTeamPrev.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        viewModel.studyItemList.observe(this) {
            viewSetting(it)
        }

        goToPatchPage()
        dataSet()
        goToTeamInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun dataSet() {
        viewModel.getMyPost(intent.getIntExtra(TeamFragment.TEAM_ID, 0))
    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra(GROUP_ID, intent.getIntExtra(GROUP_ID, 0))
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra(GROUP_ID, intent.getIntExtra(GROUP_ID, 0))
                startActivity(this)
            }
        }
    }

    private fun goToPatchPage() {
        val goToSearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    dataSet()
                }
            }

        binding.tvPatch.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(this, ProfileStudyPostPatchActivity::class.java).apply {
                    putExtra(TeamFragment.TEAM_ID, intent.getIntExtra(TeamFragment.TEAM_ID, 0))
                    putExtra(TEAM_PERSON_CNT, totalPersonCount)
                    putExtra(TEAM_GITHUB_LINK, gitHubLink)
                }
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewSetting(team: StudyGetPost) {
        totalPersonCount = team.allPersonnel
        gitHubLink = team.gitHubLink

        with(binding) {
            teamStatusCnt = team.nowPersonnel.toString()
            tvPostTitle.text = team.title
            tvTeamPostDetail.text = team.content
            tvProjectField.text = team.isContact
            tvTotalPersonCntFirst.text = teamStatusCnt + "명"
            tvTotalPersonCntEnd.text = team.allPersonnel.toString() + "명"
            tvPersonCntCheck.text = intent.getStringExtra(TeamFragment.TEAM_STATUS)
            tvGithubLink.text = "https://github.com/" + team.gitHubLink
            tvKakaoLink.text = "https://" + team.kakaoOpenLink
            tvTime.text = LocalDateTime.parse(team.createdAt)?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            ) ?: ""
            tvSubject.text = team.subject
            tvField.text = team.field
        }
    }
}
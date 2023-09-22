package com.example.ugotprototype.ui.study.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudyPostDetailBinding
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_CREATE_TIME
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_DETAIL
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_GITHUB_LINK
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_KAKAO_LINK
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS_CNT
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS_CNT_END
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_TITLE
import com.example.ugotprototype.ui.team.view.TeamFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StudyPostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyPostDetailBinding
    private lateinit var studyStatusCnt: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_post_detail)

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        dataSet()
        goToTeamInformation()
    }

    private fun dataSet() {

        with(binding) {
            tvTime.text = LocalDateTime.parse(intent.getStringExtra((STUDY_CREATE_TIME)))?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            ) ?: ""
            tvPostTitle.text = intent.getStringExtra(STUDY_TITLE)
            tvTeamPostDetail.text = intent.getStringExtra(STUDY_DETAIL)
            tvTotalPersonCntFirst.text = intent.getIntExtra(STUDY_STATUS_CNT, 0).toString()
            tvTotalPersonCntEnd.text = intent.getIntExtra(STUDY_STATUS_CNT_END, 0).toString()
            tvGithubLink.text = intent.getStringExtra(STUDY_GITHUB_LINK)
            tvKakaoLink.text = "https://" + intent.getStringExtra(STUDY_KAKAO_LINK)
            tvPersonCntCheck.text = intent.getStringExtra(STUDY_STATUS)
        }

    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", studyStatusCnt)
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra("nowPersonCnt", studyStatusCnt)
                startActivity(this)
            }
        }
    }
}
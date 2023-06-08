package com.example.ugotprototype.ui.study.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudyPostDetailBinding

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

        this.studyStatusCnt = intent.getStringExtra("studyStatusCnt").toString()

        binding.tvPostTitle.text = intent.getStringExtra("studyTitle")
        binding.tvTeamPostDetail.text = intent.getStringExtra("studyDetail")
        binding.tvTotalPersonCntFirst.text = studyStatusCnt
        binding.tvTotalPersonCntEnd.text = intent.getStringExtra("studyStatusCntEnd")
        binding.tvPersonCntCheck.text = intent.getStringExtra("studyCurrent")

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
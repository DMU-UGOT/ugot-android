package com.example.ugotprototype.ui.study.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudyInformationBinding
import com.example.ugotprototype.ui.team.adapter.StudyInformationRecyclerViewAdapter

class StudyInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyInformationBinding
    private var rvAdapter: StudyInformationRecyclerViewAdapter =
        StudyInformationRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_information)

        binding.ivStudyPrev.setOnClickListener {
            finish()
        }

        dataSet()
    }

    private fun dataSet() {
        val nowPersonCnt: Int = intent.getStringExtra("nowPersonCnt")?.toInt() ?: 0

        binding.rvStudyInformation.adapter = rvAdapter
        rvAdapter.setData(nowPersonCnt)
    }
}
package com.example.ugotprototype.ui.study.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudyInformationBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.study.viewmodel.StudyInformationViewModel
import com.example.ugotprototype.ui.team.adapter.StudyInformationRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyInformationBinding
    private var rvAdapter: StudyInformationRecyclerViewAdapter =
        StudyInformationRecyclerViewAdapter()
    private val loadingDialog = FragmentLoadingLayout()
    private val viewModel: StudyInformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_information)
        binding.rvStudyInformation.adapter = rvAdapter

        viewLoadingLayout()

        binding.ivStudyPrev.setOnClickListener {
            finish()
        }

        viewModel.getTeamInformationList(intent.getIntExtra(GROUP_ID, 0))

        viewModel.studyInforList.observe(this) {
            rvAdapter.setData(it)
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }
}
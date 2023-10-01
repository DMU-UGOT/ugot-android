package com.example.ugotprototype.ui.study.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityStudySearchDetailBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.study.adapter.StudySearchRecyclerViewAdapter
import com.example.ugotprototype.ui.study.viewmodel.StudySearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudySearchDetailActivity : AppCompatActivity() {

    private val studySearchViewModel: StudySearchViewModel by viewModels()
    private lateinit var binding: ActivityStudySearchDetailBinding
    private lateinit var studySearchRecyclerViewAdapter: StudySearchRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_search_detail)

        studySearchRecyclerViewAdapter = StudySearchRecyclerViewAdapter(studySearchViewModel)
        binding.rvStudy.adapter = studySearchRecyclerViewAdapter

        studySearchViewModel.studies.observe(this) {
            studySearchRecyclerViewAdapter.setData(it)
            binding.chipLayout.isVisible = false
        }

        studySearchViewModel.isLoadingPage.observe(this) {
            if(it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        backToMain()
        listenerSetting()
    }


    private fun backToMain() {
        binding.btBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun listenerSetting() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                studySearchViewModel.searchStudies(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        binding.svTextInput.setOnQueryTextListener(queryTextListener)
    }
}
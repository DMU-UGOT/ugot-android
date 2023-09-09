package com.example.ugotprototype.ui.study.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentStudyBinding
import com.example.ugotprototype.ui.study.adapter.StudyRecyclerViewAdapter
import com.example.ugotprototype.ui.study.viewmodel.StudyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding
    private val studyViewModel: StudyViewModel by viewModels()
    private lateinit var studyRecyclerViewAdapter: StudyRecyclerViewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    companion object {
        const val STUDY_TITLE = "teamTitle"
        const val STUDY_DETAIL = "teamDetail"
        const val STUDY_STATUS_CNT = "teamStatusCnt"
        const val STUDY_STATUS_CNT_END = "teamStatusCntEnd"
        const val STUDY_GITHUB_LINK = "teamGitHubLink"
        const val STUDY_KAKAO_LINK = "teamKakaoLink"
        const val STUDY_CREATE_TIME = "teamCreateTime"
        const val STUDY_STATUS = "teamStatus"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        binding.vm = studyViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studyViewModel.setCurrentPage(1)
        studyViewModel.setTotalPage(1)

        studyRecyclerViewAdapter = StudyRecyclerViewAdapter()
        binding.rvStudy.adapter = studyRecyclerViewAdapter

        studyViewModel.studyItemList.observe(viewLifecycleOwner) {
            studyRecyclerViewAdapter.setData(it)
        }

        studyViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.studyPageSecondText.text = it.toString()
        }

        studyViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.studyPageFirstText.text = it.toString()
            studyViewModel.getStudyList()
        }

        viewLoadingLayout()
        goToStudySearchDetail()
        goToStudyNewGroup()
    }

    private fun goToStudySearchDetail() {

        val goToStudySearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.resultCode == Activity.RESULT_OK) { }
                }
            }

        binding.btStGoDetailSearch.setOnClickListener {
            goToStudySearchResultLauncher.launch(
                Intent(
                    requireContext(), StudySearchDetailActivity::class.java
                )
            )
        }
    }

    private fun goToStudyNewGroup() {

        val goToStudyNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.resultCode == Activity.RESULT_OK) {
                        studyViewModel.getStudyList()
                    }
                }
            }

        binding.fabStudy.setOnClickListener {
            goToStudyNewGroupResultLauncher.launch(
                Intent(
                    requireContext(), StudyNewGroupActivity::class.java
                )
            )
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        studyViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(requireActivity().supportFragmentManager, "loadingDialog")
            }
        }
    }
}
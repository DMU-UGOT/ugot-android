package com.example.ugotprototype.ui.study.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentStudyBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.study.adapter.StudyRecyclerViewAdapter
import com.example.ugotprototype.ui.study.viewmodel.StudyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding
    private val studyViewModel: StudyViewModel by viewModels()
    private lateinit var studyRecyclerViewAdapter: StudyRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy {
        LoadingLayoutHelper(
            parentFragmentManager
        )
    }

    companion object {
        const val STUDY_ID = "studyId"
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

        studyRecyclerViewAdapter = StudyRecyclerViewAdapter(studyViewModel)
        binding.rvStudy.adapter = studyRecyclerViewAdapter

        studyViewModel.studyItemList.observe(viewLifecycleOwner) {
            studyRecyclerViewAdapter.setData(it)
        }

        studyViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.studyPageSecondText.text = it.toString()
        }

        studyViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.studyPageFirstText.text = it.toString()
        }

        studyViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }
        goToStudySearchDetail()
        goToStudyNewGroup()
    }

    private fun goToStudySearchDetail() {
        binding.btStGoDetailSearch.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(), StudySearchDetailActivity::class.java
                )
            )
        }
    }

    private fun goToStudyNewGroup() {
        binding.fabStudy.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(), StudyNewGroupActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        studyViewModel.getStudyList()
    }
}
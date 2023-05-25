package com.example.ugotprototype.ui.study.view

import android.app.Activity
import android.content.Context
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
import com.example.ugotprototype.R
import com.example.ugotprototype.data.study.StudyData
import com.example.ugotprototype.databinding.FragmentStudyBinding
import com.example.ugotprototype.ui.study.adapter.StudyRecyclerViewAdapter
import com.example.ugotprototype.ui.study.viewmodel.StudyViewModel

class StudyFragment : Fragment() {
    private lateinit var binding: FragmentStudyBinding
    private val studyViewModel: StudyViewModel by viewModels()

    private lateinit var studyRecyclerViewAdapter: StudyRecyclerViewAdapter
    private var studyItems = ArrayList<StudyData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)
        // fragment_study.xml에 적용
        binding.vm = studyViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testStudyData()
        studyViewModel.setStudyData(studyItems)

        studyRecyclerViewAdapter = StudyRecyclerViewAdapter()
        binding.rvStudy.adapter = studyRecyclerViewAdapter

        studyViewModel.studyItemList.observe(viewLifecycleOwner) {
            studyRecyclerViewAdapter.setData(it)
        }

        goToStudySearchDetail()
        StudySearchDetailActivity()
        goToStudyNewGroup()
    }

    private fun testStudyData() {
        studyItems = arrayListOf(
            StudyData(
                "Android Study",
                "Android 공부할 분들 모집합니다. Discord 화면 공유로 공부하실 분 구해요!",
                "조회수 : 1",
                "비대면",
                "인원 : 1/5",
                true
            ),
            StudyData(
                "Spring Tools Study",
                "Spring Tool 기반 FrontEnd 스터디 하실 분들 찾습니다. 강의 관련 주제로 협업 프로젝트 진행 예정입니다. 2학년이면 좋겠습니다",
                "조회수 : 8",
                "대면",
                "인원 : 1/6",
                true
            ),
            StudyData(
                "React.js Study",
                "FrontEnd 툴 관련 React.js 스터디 구해요! 저는 3학년 YB반이고 프로젝트를 하기 전에 같이 공부하고 싶어요! 친구와 같이 배우고 싶습니다",
                "조회수 : 22",
                "대면",
                "인원 : 2/4",
                false
            ),
            StudyData(
                "정보처리기사 자격증 스터디",
                "정보처리기사 자격증 같이 공부하실 분 구합니다. 정보처리기사 시험을 이번년도에 보시는 분만 지원 넣어주세요! 저는 정보처리산업기사 자격증 있고, 같이 CBT문제를 푸는 형식으로 진행됩니다.",
                "조회수 : 55",
                "비대면",
                "인원 : 5/8",
                false
            ),
            StudyData(
                "AWS 자격증 스터디",
                "AWS 자격증 학교에서 지원해 주는 프로그램 신청해서 같이 공부하실 분 찾습니다. 문제지 문제와 CBT위주 공부로 진행되고 문제 풀고 맞추는 형식으로 진행됩니다.",
                "조회수 : 41",
                "대면",
                "인원 : 1/4",
                false
            )
        )
    }

    private fun goToStudySearchDetail() {

        val goToStudySearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.btStGoDetailSearch.setOnClickListener {
            goToStudySearchResultLauncher.launch(
                Intent(
                    requireContext(),
                    StudySearchDetailActivity::class.java
                )
            )
        }
    }

    private fun goToStudyNewGroup() {

        val goToStudyNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.fabStudy.setOnClickListener {
            goToStudyNewGroupResultLauncher.launch(
                Intent(
                    requireContext(),
                    StudyNewGroupActivity::class.java
                )
            )
        }
    }
}
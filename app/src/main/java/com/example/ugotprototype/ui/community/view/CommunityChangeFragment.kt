package com.example.ugotprototype.ui.community.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.FragmentCommunityChangeBinding
import com.example.ugotprototype.ui.community.adapter.CommunityChangeRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel

class CommunityChangeFragment : Fragment() {

    private lateinit var binding: FragmentCommunityChangeBinding
    private val communityChangeViewModel: CommunityChangeViewModel by viewModels()

    private lateinit var communityChangeRecyclerViewAdapter: CommunityChangeRecyclerViewAdapter
    private var communityChangeItems = ArrayList<CommunityChangeViewData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_community_change, container, false)
        binding.vm = communityChangeViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testCommunityChangeData()
        communityChangeViewModel.setCommunityChangeData(communityChangeItems)

        communityChangeRecyclerViewAdapter = CommunityChangeRecyclerViewAdapter()
        binding.rvCommunityChange.adapter = communityChangeRecyclerViewAdapter

        communityChangeViewModel.communityChangeItemList.observe(viewLifecycleOwner) {
            communityChangeRecyclerViewAdapter.setData(it)
        }
    }

    private fun testCommunityChangeData() {
        communityChangeItems = arrayListOf(
            CommunityChangeViewData(
                "3학년 YB반에서 YA반 변경 하실분",
                "프로젝트 팀을 위해 YB반으로 가고 싶습니다. 1:1 맞교환하실 분 찾습니다. 댓글로 남겨주세요",
                "1",
                "4",
                "05/27"
            ),
            CommunityChangeViewData(
                "2학년 YD반인데 우리 반 올 사람???",
                "YD반은 너무 조용해서 나랑 안맞는거 같아 조용한 반 원하는 사람 있으면 말해줘",
                "3",
                "77",
                "05/28"
            ),
            CommunityChangeViewData(
                "1학년 YB반에서 YC반으로 바꾸고 싶은데",
                "1학년 2학기는 여자친구랑 같이 다니고 싶은데 반 바꿔줄 사람 있을까요??? ",
                "11",
                "82",
                "05/30"
            ),
            CommunityChangeViewData(
                "2학년 YD반인데 오고싶은 사람???",
                "프로젝트 진행하다가 한바탕 해가지고 YD반에서 못다닐꺼 같은데 우리 반 오고싶은 사람 있으면 바꿔주라 ",
                "2",
                "14",
                "06/01"
            ),
            CommunityChangeViewData(
                "1학년 YD반에서 YB반 올 사람!!?",
                "프로젝트 팀이 다 YD반이네.. 반 바꾸면 프로젝트 하기 수월할 거 같은데.. 내년을 위해서라두 바꿔줄 사람 있었으면 좋겠다",
                "4",
                "33",
                "06/02"
            ),
            CommunityChangeViewData(
                "2학년 YD반에서 YB반 원하는 쏴람~~!!?",
                "새로운 사람들이 있는 반으로 가고 싶다~~~!!",
                "2",
                "11",
                "06/02"
            ),
            CommunityChangeViewData(
                "4학년 YJ반에서 YK반 올 사람!!?",
                "아는 사람들이랑 프로젝트 하기로 했는데, 반이 혼자 다르네요.. 혹시 반 교체 하실 분 있나요...?",
                "9",
                "22",
                "06/02"
            ),
            CommunityChangeViewData(
                "4학년 학사인데 YJ반에서 YK반 원해요 !!?",
                "YK반에 교수님이 제게 가장 큰 가르침을 주신 교수님이라 반을 바꾸고 싶습니다. 제발 바꿔주세요",
                "94",
                "33",
                "06/02"
            )
        )
    }
}
package com.example.ugotprototype.ui.community.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralViewData
import com.example.ugotprototype.databinding.FragmentCommunityGeneralBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralViewModel

class CommunityGeneralFragment : Fragment() {

    private lateinit var binding: FragmentCommunityGeneralBinding
    private val communityGeneralViewModel: CommunityGeneralViewModel by viewModels()

    private lateinit var communityGeneralRecyclerViewAdapter: CommunityGeneralRecyclerViewAdapter
    private var communityGeneralItems = ArrayList<CommunityGeneralViewData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_community_general, container, false)
        binding.vm = communityGeneralViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testCommunityGeneralData()
        communityGeneralViewModel.setCommunityGeneralData(communityGeneralItems)

        communityGeneralRecyclerViewAdapter = CommunityGeneralRecyclerViewAdapter()
        binding.rvCommunity.adapter = communityGeneralRecyclerViewAdapter

        communityGeneralViewModel.communityGeneralItemList.observe(viewLifecycleOwner) {
            communityGeneralRecyclerViewAdapter.setData(it)
        }
    }

    private fun testCommunityGeneralData() {
        communityGeneralItems = arrayListOf(
            CommunityGeneralViewData(
                "같이 저녁 드실 분 구해요",
                "24세 남자며 같이 밥먹고 PC방 가서 롤할 남자분 구해요. 제 티어는 플레이고 주 라인은 서폿입니다 ",
                "3",
                "10",
                "05/29"
            ),
            CommunityGeneralViewData(
                "오늘 수업 끝나고 야구 볼 사람?",
                "나 키움 팬인데 같이 키움 응원할 사람 있어??? 같이 가면 치킨은 내가 산다 ",
                "4",
                "30",
                "05/29"
            ),
            CommunityGeneralViewData(
                "방향 같으면 같이 갈 사람?",
                "인천 구월동 사는데 같이 대중교통 타고 갈 사람 있나?? 혼자서 가니까 너무 심심해 ",
                "10",
                "40",
                "06/01"
            ),
            CommunityGeneralViewData(
                "학교 근처에 맛집 추천할 곳 있나",
                "학교 근처 음식점 거의 다 가본거 같은데 자기만 알고 있는 맛집 있어??",
                "11",
                "124",
                "06/02"
            ),
            CommunityGeneralViewData(
                "수업 시간에 안듣고 잘거면 학교는 왜 옴?",
                "2학년 알고리즘 수업 듣는데 이거 강의 안 듣고 잘거면 수업 왜 들으러 옴?? 그냥 집에 있지",
                "4",
                "33",
                "06/03"
            ),
            CommunityGeneralViewData(
                "아아아아아아아아 수업 같이 쨀 사람",
                "솔직히 수업 안들어도 시험 백점맞을거같음. 나랑 같이 수업 쨀 사람 구함. 피시방비 내줄게 아무나 와",
                "10",
                "39",
                "06/03"
            ),
            CommunityGeneralViewData(
                "학교 근처에 미용실은 어디가 잘함?????",
                "아 머리 다운펌하려고 하는데 미용실은 어디가 잘해??? 한번도 근처 가본 적이 없네",
                "9",
                "313",
                "06/04"
            ),
            CommunityGeneralViewData(
                "노래방 점수 대결할사람",
                "10cm 스토커 원곡 그대로 불러줄 수 있음. 같이 들어줄 사람 구함!!! 노래방은 내가 낼게",
                "41",
                "111",
                "06/03"
            )
        )
    }
}
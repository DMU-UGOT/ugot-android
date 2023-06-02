package com.example.ugotprototype.ui.community.view

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
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.FragmentCommunityChangeBinding
import com.example.ugotprototype.ui.community.adapter.CommunityChangeRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel
import com.example.ugotprototype.ui.study.view.StudyNewGroupActivity

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
                "3학년",
                "YA",
                "YB",
                "완료",
                "05/27"
            ),
            CommunityChangeViewData(
                "2학년",
                "YC",
                "YA",
                "가능",
                "05/28"
            ),
            CommunityChangeViewData(
                "1학년",
                "YD",
                "YA",
                "대기",
                "05/29"
            ),
            CommunityChangeViewData(
                "4학년",
                "YJ",
                "YK",
                "가능",
                "05/30"
            ),
            CommunityChangeViewData(
                "4학년",
                "YJ",
                "YK",
                "완료",
                "06/01"
            ),
            CommunityChangeViewData(
                "2학년",
                "YD",
                "YB",
                "완료",
                "06/01"
            ),
            CommunityChangeViewData(
                "3학년",
                "YD",
                "YA",
                "대기",
                "06/02"
            ),
            CommunityChangeViewData(
                "3학년",
                "YD",
                "YB",
                "가능",
                "06/02"
            ),
            CommunityChangeViewData(
                "1학년",
                "YD",
                "YB",
                "가능",
                "06/02"
            ),
            CommunityChangeViewData(
                "2학년",
                "YA",
                "YC",
                "가능",
                "06/02"
            ),
            CommunityChangeViewData(
                "4학년",
                "YK",
                "YJ",
                "가능",
                "06/03"
            )
        )
    }
}
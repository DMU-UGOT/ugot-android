package com.example.ugotprototype.ui.group.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData
import com.example.ugotprototype.databinding.FragmentGroupBinding
import com.example.ugotprototype.ui.group.adapter.GroupMiddleViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.adapter.GroupTopViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel

class GroupFragment : Fragment() {
    private lateinit var binding: FragmentGroupBinding

    private val groupViewModel: GroupViewModel by viewModels()

    private lateinit var groupTopViewAdapter: GroupTopViewRecyclerViewAdapter
    private var groupTopItems = ArrayList<GroupTopViewData>()

    private lateinit var groupMiddleViewAdapter: GroupMiddleViewRecyclerViewAdapter
    private var groupMiddleItems = ArrayList<GroupMiddleViewData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false)
        binding.vm = groupViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testData()

        groupViewModel.setGroupTopData(groupTopItems)
        groupViewModel.setGroupMiddleData(groupMiddleItems)

        groupTopViewAdapter = GroupTopViewRecyclerViewAdapter()
        binding.rvGroupTopList.adapter = groupTopViewAdapter

        groupMiddleViewAdapter = GroupMiddleViewRecyclerViewAdapter()
        binding.rvGroupMiddleList.adapter = groupMiddleViewAdapter


        groupViewModel.groupTopItemList.observe(viewLifecycleOwner) {
            groupTopViewAdapter.setData(it)
        }

        groupViewModel.groupMiddleItemList.observe(viewLifecycleOwner) {
            groupMiddleViewAdapter.setData(it)
        }

        changeMyGroupCount()
    }

    private fun testData() {
        groupTopItems = arrayListOf(
            GroupTopViewData(
                "첫 번째 그룹입니다",
            ), GroupTopViewData(
                "두 번째 그룹입니다",
            ), GroupTopViewData(
                "세그룹",
            ), GroupTopViewData(
                "네 번째 그룹입니다",
            ), GroupTopViewData(
                "다섯 번째 그룹입니다",
            ), GroupTopViewData(
                "여섯 번째 그룹입니다",
            ), GroupTopViewData(
                "일곱 번째 그룹입니다",
            ), GroupTopViewData(
                "여덟 번째 그룹입니다",
            )
        )
        groupMiddleItems = arrayListOf(
            GroupMiddleViewData(
                "안드로이드 프로그래밍을 위한 그룹입니다", "안드로이드 프로그래밍을 위한 그룹으로써 질문과 답변이 활발하게 이루어지는중입니다", "3"
            ), GroupMiddleViewData(
                "두 번째 그룹", "바보들의 집단", "3"
            ), GroupMiddleViewData(
                "세 번째 그룹", "안드로이드", "4"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "5"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "6"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "7"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "8"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "9"
            ), GroupMiddleViewData(
                "네 번째 그룹", "게임 프로그래밍", "10"
            )
        )
    }

    private fun changeMyGroupCount() {
        groupMiddleViewAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                groupViewModel.setGroupMiddleItemCount(groupMiddleViewAdapter.itemCount)
            }
        })

        groupViewModel.itemCount.observe(viewLifecycleOwner) { count ->
            binding.tvGroupCnt.text = count.toString()
        }
    }
}
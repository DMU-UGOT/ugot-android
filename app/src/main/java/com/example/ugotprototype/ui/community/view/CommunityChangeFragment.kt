package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.data.change.CommunityChangeViewData
import com.example.ugotprototype.databinding.FragmentCommunityChangeBinding
import com.example.ugotprototype.ui.community.adapter.CommunityChangeRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeDetailViewModel
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CommunityChangeFragment : Fragment() {
    private lateinit var binding: FragmentCommunityChangeBinding
    private val communityChangeViewModel: CommunityChangeViewModel by viewModels()
    private val communityChangeDetailViewModel: CommunityChangeDetailViewModel by viewModels()
    private lateinit var communityChangeRecyclerViewAdapter: CommunityChangeRecyclerViewAdapter

    companion object {
        const val CHANGE_CLASS_CHANGE_ID = "changeClassChangeId"
        const val CHANGE_GRADE = "changeGrade"
        const val CHANGE_CREATE_AT = "changeCreateAt"
        const val CHANGE_NICK_NAME = "changeNickname"
        const val CHANGE_CURRENT_CLASS = "changeCurrentClass"
        const val CHANGE_CHANGE_CLASS = "changeChangeClass"
        const val CHANGE_STATUS = "changeStatus"
        const val CHANGE_MEMBER_ID = "changeMemberId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_community_change, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityChangeRecyclerViewAdapter = CommunityChangeRecyclerViewAdapter()
        binding.rvCommunityChange.adapter = communityChangeRecyclerViewAdapter

        communityChangeViewModel.communityChangeItemList.observe(viewLifecycleOwner) {
            communityChangeRecyclerViewAdapter.setData(it)
        }

        //업데이트 구문
        communityChangeViewModel.communityChangeItemList.observe(viewLifecycleOwner) {updatedData ->
            communityChangeRecyclerViewAdapter.setData(updatedData)
        }

        communityChangeViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.communityPageSecondText.text = it.toString()
        }

        communityChangeViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.communityPageFirstText.text = it.toString()
            communityChangeViewModel.getChangeList()
        }

        goToCommunityChangeNewGroup()
        goToCommunityChangeDetail(binding.rvCommunityChange.id)
    }

    private fun goToCommunityChangeDetail(classChangeId : Int) {
        val goToCommunityChangeDetailResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    communityChangeDetailViewModel.getCommunityChangeDetailList(classChangeId)
                }
            }

        binding.rvCommunityChange.setOnClickListener {
            goToCommunityChangeDetailResultLauncher.launch(
                Intent(
                    requireContext(),
                    CommunityChangeDetailActivity::class.java
                ).putExtra(CommunityChangeFragment.CHANGE_CLASS_CHANGE_ID, classChangeId)
            )
        }
    }

    private fun goToCommunityChangeNewGroup() {
        val goToCommunityChangeNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    communityChangeViewModel.getChangeList()
                }
            }

        binding.fabChangeCommunity.setOnClickListener {
            goToCommunityChangeNewGroupResultLauncher.launch(
                Intent(
                    requireContext(),
                    CommunityChangeNewGroupActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        communityChangeViewModel.setCurrentPage(1)
        communityChangeViewModel.setTotalPage(1)
        communityChangeViewModel.getChangeList()
    }
}
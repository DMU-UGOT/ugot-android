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
import com.example.ugotprototype.databinding.FragmentCommunityGeneralBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityGeneralFragment : Fragment() {
    private lateinit var binding: FragmentCommunityGeneralBinding
    private val communityGeneralViewModel: CommunityGeneralViewModel by viewModels()
    private lateinit var communityGeneralRecyclerViewAdapter: CommunityGeneralRecyclerViewAdapter

    companion object {
        const val GENERAL_ID = "generalId"
        const val GENERAL_TITLE = "generalTitle"
        const val GENERAL_CONTENT = "generalContent"
        const val GENERAL_VOTE_COUNT = "generalVoteCount"
        const val GENERAL_VIEW_COUNT = "generalViewCount"
        const val GENERAL_CREATE_AT = "generalCreateAt"
    }

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

        communityGeneralViewModel.setCurrentPage(1)
        communityGeneralViewModel.setTotalPage(1)
        communityGeneralViewModel.getCommunityList()

        communityGeneralRecyclerViewAdapter = CommunityGeneralRecyclerViewAdapter()
        binding.rvCommunity.adapter = communityGeneralRecyclerViewAdapter

        communityGeneralViewModel.communityGeneralItemList.observe(viewLifecycleOwner) {
            communityGeneralRecyclerViewAdapter.setData(it)
        }

        communityGeneralViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.communityGeneralPageSecondText.text = it.toString()
        }

        communityGeneralViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.communityGeneralPageFirstText.text = it.toString()
            communityGeneralViewModel.getCommunityList()
        }

        goToCommunityGeneralNewGroup()
    }

    private fun goToCommunityGeneralNewGroup() {
        val goToCommunityGeneralNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    communityGeneralViewModel.getCommunityList()
                }
            }

        binding.fabGeneralCommunity.setOnClickListener {
            goToCommunityGeneralNewGroupResultLauncher.launch(
                Intent(
                    requireContext(),
                    CommunityGeneralNewGroupActivity::class.java
                )
            )
        }
    }
}
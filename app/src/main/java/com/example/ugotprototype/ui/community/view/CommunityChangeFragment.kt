package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
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
import com.example.ugotprototype.databinding.FragmentCommunityChangeBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.community.adapter.CommunityChangeRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityChangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityChangeFragment : Fragment() {
    private lateinit var binding: FragmentCommunityChangeBinding
    private val communityChangeViewModel: CommunityChangeViewModel by viewModels()
    private lateinit var communityChangeRecyclerViewAdapter: CommunityChangeRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy {
        LoadingLayoutHelper(
            parentFragmentManager
        )
    }

    companion object {
        const val CHANGE_CLASS_CHANGE_ID = "changeClassChangeId"
        const val MEMBER_ID = "memberId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_change, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communityChangeRecyclerViewAdapter = CommunityChangeRecyclerViewAdapter()
        binding.rvCommunityChange.adapter = communityChangeRecyclerViewAdapter

        communityChangeViewModel.communityChangeItemList.observe(viewLifecycleOwner) {
            communityChangeRecyclerViewAdapter.setData(it)
        }

        communityChangeViewModel.communityChangeItemList.observe(viewLifecycleOwner) {updatedData ->
            communityChangeRecyclerViewAdapter.setData(updatedData)
        }

        communityChangeViewModel.totalPage.observe(viewLifecycleOwner) {
            binding.communityPageSecondText.text = it.toString()
        }

        communityChangeViewModel.currentPage.observe(viewLifecycleOwner) {
            binding.communityPageFirstText.text = it.toString()
        }

        communityChangeViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        goToCommunityChangeNewGroup()
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
package com.example.ugotprototype.ui.group.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentGroupBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.group.adapter.GroupMiddleViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.adapter.GroupTopViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : Fragment() {
    private lateinit var binding: FragmentGroupBinding
    private val groupViewModel: GroupViewModel by viewModels()
    private lateinit var groupTopViewAdapter: GroupTopViewRecyclerViewAdapter
    private lateinit var groupMiddleViewAdapter: GroupMiddleViewRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy {
        LoadingLayoutHelper(
            parentFragmentManager
        )
    }

    companion object {
        const val GROUP_ID = "groupID"
        const val TEAM_LEADER_ID = "teamLeaderID"
        const val GROUP_NAME = "groupName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupTopViewAdapter = GroupTopViewRecyclerViewAdapter()
        binding.rvGroupTopList.adapter = groupTopViewAdapter

        groupMiddleViewAdapter = GroupMiddleViewRecyclerViewAdapter(groupViewModel)
        binding.rvGroupMiddleList.adapter = groupMiddleViewAdapter

        groupViewModel.groupMiddleItemList.observe(viewLifecycleOwner) {
            groupMiddleViewAdapter.setData(it)
        }

        groupViewModel.getFavoritesList.observe(viewLifecycleOwner) {
            groupTopViewAdapter.setData(it)
        }

        groupViewModel.groupCount.observe(viewLifecycleOwner) {
            binding.tvGroupCnt.text = it.toString() + "개"
        }

        groupViewModel.addFavorites.observe(viewLifecycleOwner) {
            onStart()
        }

        groupViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        goToNewGroup()
    }

    private fun goToNewGroup() {
        binding.fabGroup.setOnClickListener {
            startActivity(Intent(requireContext(), GroupNewGenerate::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        groupViewModel.getGroupList()
        groupViewModel.getFavoritesList()
    }
}
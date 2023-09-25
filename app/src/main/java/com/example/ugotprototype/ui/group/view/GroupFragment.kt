package com.example.ugotprototype.ui.group.view

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
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentGroupBinding
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
    private val loadingDialog = FragmentLoadingLayout()

    companion object {
        const val GROUP_ID = "groupID"
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

        viewLoadingLayout()
        goToNewGroup()
    }

    private fun goToNewGroup() {
        val goToNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    groupViewModel.getGroupList()
                }
            }

        binding.fabGroup.setOnClickListener {
            goToNewGroupResultLauncher.launch(
                Intent(
                    requireContext(), GroupNewGenerate::class.java
                )
            )
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        groupViewModel.isLoadingPage.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(requireActivity().supportFragmentManager, "loadingDialog")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        groupViewModel.getGroupList()
        groupViewModel.getFavoritesList()
    }

}
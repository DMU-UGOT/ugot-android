package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunitySearchHistory
import com.example.ugotprototype.databinding.ActivityCommunitySearchDetailBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.community.adapter.CommunitySearchRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunitySearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunitySearchDetailActivity : AppCompatActivity() {

    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()
    private lateinit var binding: ActivityCommunitySearchDetailBinding
    private lateinit var communitySearchRecyclerViewAdapter: CommunitySearchRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    companion object {
        private var searchQuery: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search_detail)

        communitySearchRecyclerViewAdapter = CommunitySearchRecyclerViewAdapter()
        binding.rvTeam.adapter = communitySearchRecyclerViewAdapter

        communitySearchViewModel.getGeneralPostSearchHistory()

        communitySearchViewModel.generals.observe(this) {
            communitySearchRecyclerViewAdapter.setData(it)
            binding.chipLayout.isVisible = false
        }

        communitySearchViewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        communitySearchViewModel.postSearchHistory.observe(this) {
            searchChipSetUp(it)
        }

        communitySearchViewModel.isDelete.observe(this) {
            if(it) {
                binding.chipGroup.removeAllViews()
                communitySearchViewModel.getGeneralPostSearchHistory()
            }
        }

        binding.btnDelete.setOnClickListener {
            communitySearchViewModel.allDeleteGeneralPostSearchHistory()
        }

        communitySearchViewModel.isAllDelete.observe(this) {
            if(it) {
                binding.chipGroup.removeAllViews()
            }
        }

        communitySearchViewModel.isSearch.observe(this) {
            if(it) {
                binding.nsvSearchHistory.visibility = View.VISIBLE
            }
        }

        backToMain()
        listenerSetting()
    }


    private fun backToMain() {
        binding.btBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun listenerSetting() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                communitySearchViewModel.searchGenerals(query.toString())
                searchQuery = query!!
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        binding.svTextInput.setOnQueryTextListener(queryTextListener)
    }

    private fun searchChipSetUp(data: List<CommunitySearchHistory>) {
        data.forEach { chipData ->
            val chip = Chip(this).apply {
                text = chipData.keyword
                isCloseIconVisible = true
                setOnCloseIconClickListener() {
                    communitySearchViewModel.deleteGeneralPostSearchHistory(text.toString())
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("1111", searchQuery)
        if (searchQuery != "") {
            communitySearchViewModel.searchGenerals(searchQuery)
            binding.svTextInput.setQuery(searchQuery, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchQuery = ""
    }
}
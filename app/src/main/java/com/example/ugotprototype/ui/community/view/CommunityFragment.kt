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
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.FragmentCommunityBinding
import com.example.ugotprototype.ui.team.view.TeamSearchDetailActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var tab1: CommunityGeneralFragment
    private lateinit var tab2: CommunityChangeFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab1 = CommunityGeneralFragment()
        tab2 = CommunityChangeFragment()

        binding.layoutCommunityTab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    replaceView(tab1)
                } else if (tab?.position == 1) {
                    replaceView(tab2)
                } else {
                    replaceView(tab1)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        goToCommunitySearchDetail()
        childFragmentManager.beginTransaction().replace(R.id.fcv_community, tab1).commit()
    }

    private fun replaceView(tab: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.fcv_community, tab).commit()
    }

    private fun goToCommunitySearchDetail() {

        val goToCommunitySearchResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.btComGoDetailSearch.setOnClickListener {
            goToCommunitySearchResultLauncher.launch(
                Intent(
                    requireContext(),
                    TeamSearchDetailActivity::class.java
                )
            )
        }
    }
}
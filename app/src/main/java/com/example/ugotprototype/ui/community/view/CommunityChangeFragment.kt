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

        goToCommunityChangeNewGroup()
    }

    private fun testCommunityChangeData() {
        communityChangeItems = arrayListOf(
            CommunityChangeViewData(
                "3학년",
                "탑sin",
                "YA",
                "YB",
                "완료",
                "2023.05.27 06:54"
            ),
            CommunityChangeViewData(
                "2학년",
                "YAHO",
                "YC",
                "YA",
                "가능",
                "2023.05.27 15:13"
            ),
            CommunityChangeViewData(
                "1학년",
                "나혼자산다",
                "YD",
                "YA",
                "대기",
                "2023.05.28 11:48"
            ),
            CommunityChangeViewData(
                "4학년",
                "vnlsdnvjlk",
                "YJ",
                "YK",
                "가능",
                "2023.05.29 18:59"
            ),
            CommunityChangeViewData(
                "4학년",
                "Ash Island",
                "YJ",
                "YK",
                "완료",
                "2023.05.30 20:11"
            ),
            CommunityChangeViewData(
                "2학년",
                "ADHD경고",
                "YD",
                "YB",
                "완료",
                "2023.06.01 01:24"
            ),
            CommunityChangeViewData(
                "3학년",
                "집이제일좋은사람",
                "YD",
                "YA",
                "대기",
                "2023.06.01 17:57"
            ),
            CommunityChangeViewData(
                "3학년",
                "홍대에만있음",
                "YD",
                "YB",
                "가능",
                "2023.06.02 00:01"
            ),
            CommunityChangeViewData(
                "1학년",
                "여름이시러",
                "YD",
                "YB",
                "가능",
                "2023.06.02 14:29"
            ),
            CommunityChangeViewData(
                "2학년",
                "된장찌개가좋아",
                "YA",
                "YC",
                "가능",
                "2023.06.02 20:00"
            ),
            CommunityChangeViewData(
                "4학년",
                "나부터빨리좀",
                "YK",
                "YJ",
                "가능",
                "2023.06.03 01:14"
            )
        )
    }

    private fun goToCommunityChangeNewGroup() {
        val goToCommunityChangeNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
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
}
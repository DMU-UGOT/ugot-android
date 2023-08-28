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
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
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
                "교환 완료",
                parseDateString("23.05.27 06:54")
            ),
            CommunityChangeViewData(
                "2학년",
                "YAHO",
                "YC",
                "YA",
                "교환 가능",
                parseDateString("23.05.29 15:13")
            ),
            CommunityChangeViewData(
                "1학년",
                "나혼자산다",
                "YD",
                "YA",
                "교환 대기",
                parseDateString("23.05.30 11:48")
            ),
            CommunityChangeViewData(
                "4학년",
                "vnlsdnvjlk",
                "YJ",
                "YK",
                "교환 가능",
                parseDateString("23.06.04 18:59")
            ),
            CommunityChangeViewData(
                "4학년",
                "Ash Island",
                "YJ",
                "YK",
                "교환 완료",
                parseDateString("23.06.12 20:11")
            ),
            CommunityChangeViewData(
                "2학년",
                "ADHD경고",
                "YD",
                "YB",
                "교환 완료",
                parseDateString("23.06.15 01:24")
            ),
            CommunityChangeViewData(
                "3학년",
                "집이제일좋은사람",
                "YD",
                "YA",
                "교환 대기",
                parseDateString("23.07.11 17:57")
            ),
            CommunityChangeViewData(
                "3학년",
                "홍대에만있음",
                "YD",
                "YB",
                "교환 가능",
                parseDateString("23.07.14 00:01")
            ),
            CommunityChangeViewData(
                "1학년",
                "여름이시러",
                "YD",
                "YB",
                "교환 완료",
                parseDateString("23.07.19 14:29")
            ),
            CommunityChangeViewData(
                "2학년",
                "된장찌개가좋아",
                "YA",
                "YC",
                "교환 대기",
                parseDateString("23.07.22 20:00")
            ),
            CommunityChangeViewData(
                "4학년",
                "나부터빨리좀",
                "YK",
                "YJ",
                "교환 가능",
                parseDateString("23.08.02 08:14")
            )
        )
        communityChangeViewModel.setCommunityChangeData(communityChangeItems)
    }

    // 문자열로 된 날짜를 Date 객체로 변환하는 함수 추가
    private fun parseDateString(dateString: String): Date {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date()
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
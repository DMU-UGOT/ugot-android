package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.databinding.FragmentCommunityGeneralDetailBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralChatRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralChatViewModel

class CommunityGeneralDetailActivity : AppCompatActivity() {
    private lateinit var binding: FragmentCommunityGeneralDetailBinding
    private val communityGeneralChatViewModel: CommunityGeneralChatViewModel by viewModels()

    private lateinit var communityGeneralChatRecyclerViewAdapter: CommunityGeneralChatRecyclerViewAdapter
    private var communityGeneralChatItems = ArrayList<CommunityGeneralChatViewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_community_general_detail)
        binding.vm = communityGeneralChatViewModel

        testCommunityGeneralChatData()
        communityGeneralChatViewModel.setCommunityGeneralChatData(communityGeneralChatItems)

        communityGeneralChatRecyclerViewAdapter = CommunityGeneralChatRecyclerViewAdapter()
        binding.rvCommunityGeneralChat.adapter = communityGeneralChatRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvCommunityGeneralChat.layoutManager = layoutManager

        communityGeneralChatViewModel.setCommunityGeneralChatData(communityGeneralChatItems)

        communityGeneralChatViewModel.communityGeneralChatItemList.observe(this) {
            communityGeneralChatRecyclerViewAdapter.setData(it)
        }

        dataGeneralSet()
        backCommunityGeneralToMainActivity()
    }

    private fun testCommunityGeneralChatData() {
        communityGeneralChatItems = arrayListOf(
            CommunityGeneralChatViewData(
                "서폿만 하는 사람",
                "저요!",
                "2023.05.29 16:22"
            ),
            CommunityGeneralChatViewData(
                "밥친구 구하는 사람",
                "저두 같이 먹어요!! ",
                "2023.05.29 17:25"
            ),
            CommunityGeneralChatViewData(
                "Zed장인",
                "pc방 같이가요!!",
                "2023.05.29 17:32"
            ),
            CommunityGeneralChatViewData(
                "썩은청어",
                "저두..!!",
                "2023.05.29 20:32"
            ),
            CommunityGeneralChatViewData(
                "유후앤다아이츠모어댄라잌",
                "저도 가능할까요?!",
                "2023.05.29 20:44"
            )
        )
    }

    private fun dataGeneralSet() {
        binding.tvCommunityGeneralName.text = intent.getStringExtra("comGeneralName")
        binding.tvCommunityGeneralNickname.text = intent.getStringExtra("comGeneralNickName")
        binding.tvCommunityGeneralTime.text = intent.getStringExtra("comGeneralDate")
        binding.tvCommunityGeneralText.text = intent.getStringExtra("comGeneralText")
        binding.tvCommunityInquireInput.text = intent.getStringExtra("comGeneralInquire")
    }

    private fun backCommunityGeneralToMainActivity() {
        binding.ivCommunityGeneralBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
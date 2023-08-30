package com.example.ugotprototype.ui.community.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.databinding.FragmentCommunityGeneralDetailBinding
import com.example.ugotprototype.ui.community.adapter.CommunityGeneralChatRecyclerViewAdapter
import com.example.ugotprototype.ui.community.viewmodel.CommunityGeneralChatViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        chatInputBtn()
        backCommunityGeneralToMainActivity()
        changeMyGeneralChatCount()
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
            ),
            CommunityGeneralChatViewData(
                "삼겹살에쏘주한잔",
                "오늘삼쏘어떠세요",
                "2023.05.30 10:01"
            ),
            CommunityGeneralChatViewData(
                "미치겠다시험때매",
                "삼쏘파티 저도 초대좀!",
                "2023.05.30 12:22"
            ),
            CommunityGeneralChatViewData(
                "볼빨간사춘기의사촌",
                "저도같이 먹고싶어요",
                "2023.05.30 14:33"
            )
        )
    }

    @SuppressLint("SetTextI18n")
    private fun dataGeneralSet() {
        with(binding) {
            tvCommunityGeneralName.text = intent.getStringExtra(CommunityGeneralFragment.GENERAL_TITLE)
            tvCommunityGeneralNickname.text = intent.getStringExtra(CommunityGeneralFragment.GENERAL_MEMBER_ID)
            tvCommunityGeneralText.text = intent.getStringExtra(CommunityGeneralFragment.GENERAL_CONTENT)
            tvCommunityGeneralTime.text = LocalDateTime.parse(intent.getStringExtra((CommunityGeneralFragment.GENERAL_CREATE_AT)))?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))?: ""
            tvCommunityGeneralCnt.text = intent.getStringExtra(CommunityGeneralFragment.GENERAL_VOTE_COUNT)
            tvCommunityInquireInput.text = intent.getStringExtra(CommunityGeneralFragment.GENERAL_VIEW_COUNT)
        }
    }

    private fun backCommunityGeneralToMainActivity() {
        binding.ivCommunityGeneralBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun chatInputBtn(){
        binding.btGeneralDetailChatInput.setOnClickListener {
            binding.generalChatInput.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.generalChatInput.windowToken, 0)
    }

    private fun changeMyGeneralChatCount() {
        communityGeneralChatViewModel.itemCount.observe(this) { count ->
            binding.tvCommunityGeneralCnt.text = count.toString()
        }
    }
}
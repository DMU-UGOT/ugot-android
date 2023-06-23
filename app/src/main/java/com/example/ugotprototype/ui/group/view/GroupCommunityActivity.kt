package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupCommunityChatViewData
import com.example.ugotprototype.databinding.ActivityGroupCommunityBinding
import com.example.ugotprototype.ui.group.adapter.GroupCommunityRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupCmuChatViewModel

class GroupCommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupCommunityBinding
    private val groupCmuChatViewModel: GroupCmuChatViewModel by viewModels()

    private lateinit var groupCommunityRecyclerViewAdapter: GroupCommunityRecyclerViewAdapter
    private var groupCmuChatItems = ArrayList<GroupCommunityChatViewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_community)
        binding.vm = groupCmuChatViewModel

        testGroupCmuChatData()
        groupCmuChatViewModel.setGroupCmuChatData(groupCmuChatItems)

        groupCommunityRecyclerViewAdapter = GroupCommunityRecyclerViewAdapter()
        binding.rvGroupCmuChat.adapter = groupCommunityRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvGroupCmuChat.layoutManager = layoutManager

        groupCmuChatViewModel.setGroupCmuChatData(groupCmuChatItems)

        groupCmuChatViewModel.groupCmuChatItemList.observe(this) {
            groupCommunityRecyclerViewAdapter.setData(it)
        }

        backGroupCommunityToMainActivity()
        chatInputBtn()
    }

    private fun backGroupCommunityToMainActivity() {
        binding.ivGroupCmuBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun chatInputBtn() {
        binding.btGroupCmuChatInput.setOnClickListener {
            binding.groupCmuChatInput.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.groupCmuChatInput.windowToken, 0)
    }

    private fun testGroupCmuChatData() {
        groupCmuChatItems = arrayListOf(
            GroupCommunityChatViewData(
                "제리만 하는 사람",
                "오늘 회의 있나요??",
                "2023.06.23 16:19"
            ),
            GroupCommunityChatViewData(
                "개미",
                "있는걸로 알아요",
                "2023.06.23 16:25"
            ),
            GroupCommunityChatViewData(
                "베짱이",
                "저는 오늘 참석 못할 것 같습니다",
                "2023.06.23 16:31"
            ),
            GroupCommunityChatViewData(
                "제리만 하는 사람",
                "감사합니다",
                "2023.06.23 16:32"
            ),
            GroupCommunityChatViewData(
                "개미",
                "다음 회의는 필참이니까 모두 참석 해주시길 바랍니다. 다음 회의는 출석 진행하겠습니다.",
                "2023.06.23 16:58"
            ),
            GroupCommunityChatViewData(
                "베짱이",
                "꼭 참여하겠습니다",
                "2023.06.23 17:19"
            ),
            GroupCommunityChatViewData(
                "개미",
                "넵",
                "2023.06.23 17:20"
            ),
            GroupCommunityChatViewData(
                "왕벌의 비행",
                "네~",
                "2023.06.23 17:27"
            )
        )
    }
}
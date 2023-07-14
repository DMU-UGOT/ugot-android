package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugotprototype.R
import com.example.ugotprototype.data.profile.ProfileMessageData
import com.example.ugotprototype.databinding.ActivityProfileMessageBinding
import com.example.ugotprototype.ui.profile.adapter.ProfileMessageRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageViewModel

class MessageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileMessageBinding
    private val profileMessageViewModel: ProfileMessageViewModel by viewModels()

    private lateinit var profileMessageRecyclerViewAdapter: ProfileMessageRecyclerViewAdapter
    private var profileMessageItems = ArrayList<ProfileMessageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_message)
        binding.vm = profileMessageViewModel

        testProfileMessageData()
        profileMessageViewModel.setProfileMessageData(profileMessageItems)

        profileMessageRecyclerViewAdapter = ProfileMessageRecyclerViewAdapter()
        binding.rvProfileMessage.adapter = profileMessageRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvProfileMessage.layoutManager = layoutManager

        profileMessageViewModel.setProfileMessageData(profileMessageItems)
        profileMessageRecyclerViewAdapter.setDataList(profileMessageItems)

        profileMessageViewModel.profileMessageItemList.observe(this) {
            profileMessageRecyclerViewAdapter.setData(it)
        }

        allDeleteMessage()
        messageBackToMainActivity()
    }

    private fun testProfileMessageData() {
        profileMessageItems = arrayListOf(
            ProfileMessageData(
                "서브웨이꿀조합",
                "YB반인 저랑 교환할 생각 있으신가요!? 010-1111-1111로 연락주세요",
                "2023.07.01 12:48"
            ),
            ProfileMessageData(
                "이탈리안마르게리따",
                "YB반 학생인데 교환 가능한가요?? 저희 반이 저랑 너무 맞지 않아서 반 교환 신청 부탁드립니다.",
                "2023.07.01 13:25"
            ),
            ProfileMessageData(
                "병아리는야옹야옹",
                "YB반 학생입니다 교환 아직 안하셨으면 교환해요! B반은 저랑 어울리지 않는 것 같아요.. 저랑 교환해요!!",
                "2023.07.02 08:10"
            ),
            ProfileMessageData(
                "신의목소리",
                "저와 교환 하실 수 있나요?? YC반 학생입니다. 교환하고 싶습니다!!!",
                "2023.07.02 10:22"
            ),
            ProfileMessageData(
                "제네시스gv80",
                "YC반은 어떠신가요?? 교환하고 싶습니다. 교환 가능하시면 쪽지 주세요!!",
                "2023.07.02 08:10"
            )
        )
    }

    private fun messageBackToMainActivity() {
        binding.btMessageBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun allDeleteMessage(){
        binding.btProfileDelete.setOnClickListener {
            // RecyclerView의 아이템을 전부 삭제하는 작업
            (binding.rvProfileMessage.adapter as? ProfileMessageRecyclerViewAdapter)?.apply {
                clearItems()
            }
        }
    }
}
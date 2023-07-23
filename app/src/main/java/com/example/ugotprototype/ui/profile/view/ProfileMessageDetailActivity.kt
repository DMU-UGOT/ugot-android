package com.example.ugotprototype.ui.profile.view

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
import com.example.ugotprototype.data.profile.ProfileMessageDetailData
import com.example.ugotprototype.databinding.ActivityProfileMessageDetailBinding
import com.example.ugotprototype.ui.profile.adapter.ProfileMessageDetailRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageDetailViewModel

class ProfileMessageDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileMessageDetailBinding
    private val profileMessageDetailViewModel: ProfileMessageDetailViewModel by viewModels()

    private lateinit var profileMessageDetailRecyclerViewAdapter: ProfileMessageDetailRecyclerViewAdapter
    private var profileMessageDetailItems = ArrayList<ProfileMessageDetailData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_message_detail)

        binding.vm = profileMessageDetailViewModel

        testProfileDetailMessageData()
        profileMessageDetailViewModel.setProfileMessageDetailData(profileMessageDetailItems)

        // 왼쪽 RecyclerView Adapter 초기화
        profileMessageDetailRecyclerViewAdapter = ProfileMessageDetailRecyclerViewAdapter()
        binding.rvProfileMessageDetail.adapter = profileMessageDetailRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvProfileMessageDetail.layoutManager = layoutManager

        profileMessageDetailViewModel.setProfileMessageDetailData(profileMessageDetailItems)

        // 데이터를 왼쪽 RecyclerView 어댑터에 설정
        profileMessageDetailViewModel.profileMessageDetailItemList.observe(this) {
            profileMessageDetailRecyclerViewAdapter.setData(it)
        }

        // 데이터 수신 및 표시
        val messageName = intent.getStringExtra("MessageName")
        if (messageName != null) {
            // TODO: 이 부분에서 messageName을 활용하여 해당 대화의 상세 정보를 가져와서 표시하는 작업을 진행합니다.
            // 예를 들어, 해당 대화에 속하는 메시지들을 가져와 RecyclerView에 표시하거나, 상단의 텍스트뷰 등에 표시할 수 있습니다.
            // (예시로 사용한 testProfileMessageData() 함수와 유사한 방식으로 데이터를 가져오시면 됩니다.)
        }

        chatMessageDetailInputBtn()
        messageDetailBackToMainActivity()
    }

    private fun testProfileDetailMessageData() {
        profileMessageDetailItems = arrayListOf(
            ProfileMessageDetailData(
                "John Park",
                "내일 13시에 학과사무실 앞에서 만나죠",
                "2023.07.02 13:03",
                true //내 대화
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "넵!! 언제가 좋으실까요?",
                "2023.07.02 13:01",
                false //상대방 대화
            ),
            ProfileMessageDetailData(
                "John Park",
                "연락드렸는데 안받으셔서 쪽지로 남겨요. 바꾸죠",
                "2023.07.02 11:59",
                true
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "답장 부탁드립니다. 진심으로 바꾸고 싶습니다.",
                "2023.07.02 10:11",
                false //상대방 대화
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "YB반 학생인데 교환 가능한가요??",
                "2023.07.01 16:25",
                false
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "YB반인 저랑 교환할 생각 있으신가요!? 010-1111-1111로 연락주세요",
                "2023.07.01 12:48",
                false
            )
        )
    }

    private fun messageDetailBackToMainActivity() {
        binding.btMessageDetailBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun chatMessageDetailInputBtn(){
        binding.btMessageDetailChatInput.setOnClickListener {
            binding.etMessageDetailChatInput.text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etMessageDetailChatInput.windowToken, 0)
    }
}
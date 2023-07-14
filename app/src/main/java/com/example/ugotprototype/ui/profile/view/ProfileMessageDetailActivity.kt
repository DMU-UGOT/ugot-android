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

        profileMessageDetailRecyclerViewAdapter = ProfileMessageDetailRecyclerViewAdapter()
        binding.rvProfileMessageDetail.adapter = profileMessageDetailRecyclerViewAdapter

        // RecyclerView의 레이아웃 매니저를 LinearLayoutManager로 설정
        val layoutManager = LinearLayoutManager(this)
        binding.rvProfileMessageDetail.layoutManager = layoutManager

        profileMessageDetailViewModel.setProfileMessageDetailData(profileMessageDetailItems)

        profileMessageDetailViewModel.profileMessageDetailItemList.observe(this) {
            profileMessageDetailRecyclerViewAdapter.setData(it)
        }

        chatMessageDetailInputBtn()
        allDeleteDetailMessage()
        messageDetailBackToMainActivity()
    }

    private fun testProfileDetailMessageData() {
        profileMessageDetailItems = arrayListOf(
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "답장 부탁드립니다. 진심으로 바꾸고 싶습니다.",
                "2023.07.02 10:11"
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "YB반 학생인데 교환 가능한가요??",
                "2023.07.01 16:25"
            ),
            ProfileMessageDetailData(
                "서브웨이꿀조합",
                "YB반인 저랑 교환할 생각 있으신가요!? 010-1111-1111로 연락주세요",
                "2023.07.01 12:48"
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

    private fun allDeleteDetailMessage(){
        binding.btProfileDetailDelete.setOnClickListener {
            // RecyclerView의 아이템을 전부 삭제하는 작업
            (binding.rvProfileMessageDetail.adapter as? ProfileMessageDetailRecyclerViewAdapter)?.apply {
                clearItems()
            }
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
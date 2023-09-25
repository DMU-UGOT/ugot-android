package com.example.ugotprototype.ui.group.view

import BottomSheetHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding
    private val viewModel: GroupDetailViewModel by viewModels()
    private val loadingDialog = FragmentLoadingLayout()
    private var groupId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail)

        viewLoadingLayout()

        viewModel.getGroupDetailData(intent.getIntExtra(GROUP_ID, 0))

        viewModel.groupDetailData.observe(this) {
            viewSetting(it)
        }

        viewModel.isNoticeCreated.observe(this) {
            if (it) {
                Log.d("test", "제발되어라")
                viewModel.getNoticeData(groupId)
            }
        }

        viewModel.latestNotice.observe(this) {
            Log.d("test", it.toString())
            binding.mbGroupDetailAnnouncement.text = it
        }

        binding.fabGroupNoticeWrite.setOnClickListener {
            bottomSheetDialogCreate()
        }

        backToMain()
        goToDetailPage()
        goToDetailCommunityPage()
        goToTeamInformationPage()
    }

    private fun viewSetting(data: GroupDetailData) {
        groupId = data.groupId

        with(binding) {
            tvGroupDetailTeamTitle.text = data.groupName
            tvGroupDetailTeamLeaderName.text = data.nickname
            tvGroupDetailTeamCnt.text = data.nowPersonnel.toString()
            tvGroupDetailStory.text = data.content
        }

        viewModel.getNoticeData(groupId)
    }

    private fun backToMain() {
        binding.ibGroupDetailPrev.setOnClickListener {
            finish()
        }
    }

    private fun goToDetailPage() {
        binding.mbGroupDetailCalendar.setOnClickListener {
            Intent(this, GroupDetailCalendarActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
                startActivity(this)
            }
        }
    }

    private fun bottomSheetDialogCreate() {
        val bottomSheetHelper = BottomSheetHelper(this@GroupDetailActivity, this, viewModel)
        bottomSheetHelper.createBottomSheet()

        viewModel.bottomSheetClickCheck.observe(this) {
            if (it) {
                Toast.makeText(this, "공지사항 등록 완료", Toast.LENGTH_LONG).show()
                viewModel.sendNoticeData(groupId)
                viewModel.isBottomSheetClickCheck(false)
            }
        }
    }

    private fun goToDetailCommunityPage() {
        binding.mbGroupDetailCommunication.setOnClickListener {
            Intent(this, GroupCommunityActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
                startActivity(this)
            }
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }

    private fun goToTeamInformationPage() {
        binding.mbGroupDetailTeamInformation.setOnClickListener {
            Intent(this, GroupTeamInformationActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
                startActivity(this)
            }
        }
    }
}
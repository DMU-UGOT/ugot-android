package com.example.ugotprototype.ui.group.view

import BottomSheetHelper
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.ui.Loading.util.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.TEAM_LEADER_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding
    private val viewModel: GroupDetailViewModel by viewModels()
    private val loadingDialog = FragmentLoadingLayout()
    private var groupId: Int = 0
    private var groupLeaderId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail)

        viewLoadingLayout()

        viewModel.groupDetailData.observe(this) {
            viewSetting(it)
        }

        viewModel.isUpdateGroup.observe(this) {
            if (it) {
                viewModel.getNoticeData(groupId)
            }
        }

        viewModel.latestNotice.observe(this) {
            binding.mbGroupDetailAnnouncement.text = it
        }

        viewModel.isDeleteGroup.observe(this) {
            if (it) {
                finish()
            }
        }

        viewModel.isQuitGroup.observe(this) {
            if (it) {
                finish()
            }
        }

        binding.fabGroupNoticeWrite.setOnClickListener {
            bottomSheetDialogCreate()
        }

        backToMain()
        goToDetailPage()
        goToDetailCommunityPage()
        goToTeamInformationPage()
        onClickHambugerButton()
    }

    private fun viewSetting(data: GroupDetailData) {
        groupId = data.groupId
        groupLeaderId = data.nickname

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
                putExtra(TEAM_LEADER_ID, groupLeaderId)
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

    private fun onClickHambugerButton() {

        binding.ivMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)

            viewModel.groupLeaderCheck(groupLeaderId) { isGroupLeader ->
                val menuResId = if (isGroupLeader) {
                    R.menu.group_leader_menu
                } else {
                    R.menu.group_menu
                }

                popupMenu.menuInflater.inflate(menuResId, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item1 -> {
                            viewModel.quitGroup(groupId)
                            true
                        }

                        R.id.menu_item2 -> {
                            viewModel.deleteGroup(groupId)
                            true
                        }

                        R.id.menu_item3 -> {
                            Intent(
                                this@GroupDetailActivity,
                                GroupRequestApplicationActivity::class.java
                            ).apply {
                                putExtra(GROUP_ID, groupId)
                                startActivity(this)
                            }
                            true
                        }

                        R.id.menu_item4 -> {
                            Intent(
                                this@GroupDetailActivity, GroupForcedExitActivity::class.java
                            ).apply {
                                putExtra(GROUP_ID, groupId)
                                startActivity(this)
                            }
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getGroupDetailData(intent.getIntExtra(GROUP_ID, 0))
    }
}
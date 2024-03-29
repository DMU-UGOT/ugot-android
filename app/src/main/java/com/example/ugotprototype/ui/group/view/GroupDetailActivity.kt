package com.example.ugotprototype.ui.group.view

import BottomSheetHelper
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupDetailData
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_NAME
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.TEAM_LEADER_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding
    private val viewModel: GroupDetailViewModel by viewModels()
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }
    private var groupId: Int = 0
    private var groupLeaderId: String = ""
    private var groupName: String = ""

    private val goToSearchResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getGroupDetailData(intent.getIntExtra(GROUP_ID, 0))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail)

        viewModel.getGroupDetailData(intent.getIntExtra(GROUP_ID, 0))

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

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
            } else {
                Toast.makeText(this, "그룹의 팀장은 탈퇴할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabGroupNoticeWrite.setOnClickListener {
            viewModel.getAccountNickname {
                if (it == groupLeaderId) {
                    bottomSheetDialogCreate()
                } else {
                    Toast.makeText(this, "그룹의 팀장만 작성이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        backToMain()
        goToDetailPage()
        goToDetailCommunityPage()
        goToTeamInformationPage()
        onClickHambugerButton()
        goToEngagementPage()
    }

    private fun viewSetting(data: GroupDetailData) {
        groupId = data.groupId
        groupLeaderId = data.nickname
        groupName = intent.getStringExtra(GROUP_NAME)!!.split("/")[0]

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
            goToSearchResultLauncher.launch(
                Intent(
                    this,
                    GroupDetailCalendarActivity::class.java
                ).apply {
                    putExtra(GROUP_ID, groupId)
                    putExtra(TEAM_LEADER_ID, groupLeaderId)
                }
            )
        }
    }

    private fun goToEngagementPage() {
        binding.mbGroupDetailProgress.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(
                    this,
                    GroupEngagementRateActivity::class.java
                ).apply {
                    putExtra(GROUP_NAME, groupName)
                }
            )
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
            goToSearchResultLauncher.launch(
                Intent(
                    this,
                    GroupCommunityActivity::class.java
                ).apply {
                    putExtra(GROUP_ID, groupId)
                }
            )
        }
    }

    private fun goToTeamInformationPage() {
        binding.mbGroupDetailTeamInformation.setOnClickListener {
            goToSearchResultLauncher.launch(
                Intent(
                    this,
                    GroupTeamInformationActivity::class.java
                ).apply {
                    putExtra(GROUP_ID, groupId)
                }
            )
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
                            goToSearchResultLauncher.launch(
                                Intent(
                                    this,
                                    GroupRequestApplicationActivity::class.java
                                ).apply {
                                    putExtra(GROUP_ID, groupId)
                                }
                            )
                            true
                        }

                        R.id.menu_item4 -> {
                            goToSearchResultLauncher.launch(
                                Intent(
                                    this,
                                    GroupForcedExitActivity::class.java
                                ).apply {
                                    putExtra(GROUP_ID, groupId)
                                }
                            )
                            true
                        }

                        R.id.menu_item5 -> {
                            goToSearchResultLauncher.launch(
                                Intent(
                                    this,
                                    GroupHandOverActivity::class.java
                                ).apply {
                                    putExtra(GROUP_ID, groupId)
                                }
                            )
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
            }
        }
    }
}
package com.example.ugotprototype.ui.study.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ActivityStudyPostDetailBinding
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_ID
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS
import com.example.ugotprototype.ui.study.viewmodel.StudyPostDetailViewModel
import com.example.ugotprototype.ui.team.view.TeamFragment
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GITHUB_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class StudyPostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudyPostDetailBinding
    private val viewModel: StudyPostDetailViewModel by viewModels()
    private var teamId: Int = 0
    private var gitHubLink: String = ""

    private val goToSearchResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.viewSetting(intent.getIntExtra(STUDY_ID, 0))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_post_detail)

        viewModel.viewSetting(intent.getIntExtra(STUDY_ID, 0))

        viewModel.studyDetailData.observe(this) {
            initData(it)
        }

        binding.tvGroupApplication.setOnClickListener {
            viewModel.sendApplication(intent.getIntExtra(GROUP_ID, 0))
        }

        viewModel.isDuplicateGroupPerson.observe(this) {
            if (it) {
                Toast.makeText(this, "신청이 성공적으로 됐습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "가입할 수 없는 그룹 입니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        viewModel.isPostDelete.observe(this) {
            finish()
        }

        viewModel.isPostRefresh.observe(this) {
            if(it) {
                finish()
            }
        }

        onClickHambugerButton()
        goToTeamInformation()
    }

    @SuppressLint("SetTextI18n")
    private fun initData(data: StudyGetPost) {
        teamId = data.studyId
        gitHubLink = data.gitHubLink

        with(binding) {
            tvTeamNickName.text = data.nickname
            tvTime.text = LocalDateTime.parse(data.createdAt)?.format(
                DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            ) ?: ""
            tvPostTitle.text = data.title
            tvTeamPostDetail.text = data.content
            tvTotalPersonCntFirst.text = data.nowPersonnel.toString()
            tvTotalPersonCntEnd.text = data.allPersonnel.toString()
            tvGithubLink.text = data.gitHubLink
            tvKakaoLink.text = data.kakaoOpenLink
            tvPersonCntCheck.text = intent.getStringExtra(STUDY_STATUS)
            tvGroupName.text = data.groupName
            tvSubject.text = data.subject
            tvField.text = data.field
        }

        viewModel.getMyNickName {
            if (it == data.nickname) {
                binding.studyHamburger.visibility = View.VISIBLE
            } else {
                binding.studyHamburger.visibility = View.INVISIBLE
            }
        }


    }

    private fun goToTeamInformation() {
        binding.ivGoTeamInformation.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra(GROUP_ID, intent.getIntExtra(GROUP_ID, 0))
                startActivity(this)
            }
        }

        binding.tvPersonCntTitle.setOnClickListener {
            Intent(this, StudyInformationActivity::class.java).apply {
                putExtra(GROUP_ID, intent.getIntExtra(GROUP_ID, 0))
                startActivity(this)
            }
        }
    }

    private fun onClickHambugerButton() {

        binding.studyHamburger.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)

            popupMenu.menuInflater.inflate(R.menu.team_patch_delete_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item1 -> {
                        goToSearchResultLauncher.launch(
                            Intent(
                                this,
                                StudyPostPatchActivity::class.java
                            ).apply {
                                putExtra(TEAM_ID, teamId)
                                putExtra(TEAM_GITHUB_LINK, gitHubLink)
                            }
                        )
                        true
                    }

                    R.id.item2 -> {
                        viewModel.deleteMyPost(teamId)
                        true
                    }

                    R.id.item3 -> {
                        viewModel.refreshMyPost(teamId)
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }
}
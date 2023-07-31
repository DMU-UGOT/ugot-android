package com.example.ugotprototype.ui.group.view

import BottomSheetHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import com.google.android.material.button.MaterialButton

class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding
    private lateinit var personCnt: String
    private val groupViewModel: GroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail)

        backToMain()
        dataSet()
        goToDetailPage()

        binding.mbGroupDetailTeamInformation.setOnClickListener {
            startActivity(
                Intent(
                    this, GroupTeamInformationActivity::class.java
                ).putExtra("nowPersonCnt", personCnt)
            )
        }

        binding.mbGroupDetailProgress.setOnClickListener {
            startActivity(
                Intent(
                    this, GroupEngagementRateActivity::class.java
                )
            )
        }

        binding.fabGroupNoticeWrite.setOnClickListener {
            bottomSheetDialogCreate()
        }

    }

    private fun dataSet() {
        this.personCnt = intent.getStringExtra("groupPersonCnt").toString()
        binding.tvGroupDetailTeamTitle.text = intent.getStringExtra("groupName")
        binding.tvGroupDetailTeamCnt.text = personCnt
        binding.tvGroupDetailStory.text = intent.getStringExtra("groupDetail")
    }

    private fun backToMain() {
        binding.ibGroupDetailPrev.setOnClickListener {
            finish()
        }
    }

    private fun goToDetailPage() {
        binding.mbGroupDetailCalendar.setOnClickListener {
            startActivity(Intent(this, GroupDetailCalendarActivity::class.java))
        }
    }

    private fun bottomSheetDialogCreate() {
        val bottomSheetHelper = BottomSheetHelper(this@GroupDetailActivity, this, groupViewModel)
        bottomSheetHelper.createBottomSheet()

        groupViewModel.bottomSheetClickCheck.observe(this) {
            if (it) {
                Toast.makeText(this, "공지사항 등록 완료", Toast.LENGTH_LONG).show()
                groupViewModel.isBottomSheetClickCheck(false)
            }
        }

    }
}
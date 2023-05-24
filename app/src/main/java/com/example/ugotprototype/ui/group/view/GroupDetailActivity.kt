package com.example.ugotprototype.ui.group.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding

class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_detail)

        backToMain()
        dataSet()
        goToDetailPage()
    }

    private fun dataSet() {
        binding.tvGroupDetailTeamTitle.text = intent.getStringExtra("groupName")
        binding.tvGroupDetailTeamCnt.text = intent.getStringExtra("groupPersonCnt")
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
}
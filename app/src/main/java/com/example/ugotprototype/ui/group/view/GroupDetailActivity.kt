package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupDetailBinding

class GroupDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupDetailBinding
    private lateinit var personCnt: String

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

        var goToPostWriteResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    val ymd = data?.getStringExtra("yearMonthDay")
                    Log.d("main", "$resultText")
                    Log.d("main", "$ymd")
                }
            }

        binding.fabGroupNoticeWrite.setOnClickListener {
            goToPostWriteResultLauncher.launch(Intent(this, GroupNoticeWriteActivity::class.java))
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
}
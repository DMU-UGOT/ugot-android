package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityChangeDetailBinding

class CommunityChangeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_detail)

        dataChangeSet()
        backCommunityChangeToMainActivity()
    }

    private fun backCommunityChangeToMainActivity() {
        binding.ivCommunityChangeBack.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun dataChangeSet() {
        binding.tvCommunityChangeDetailGrade.text = intent.getStringExtra("comChangeGrade")
        binding.tvCommunityChangeDetailNickName.text = intent.getStringExtra("comChangeNickName")
        binding.tvCommunityChangeDetailTime.text = intent.getStringExtra("comChangeDate")
        binding.tvCommunityChangeDetailNowInput.text = intent.getStringExtra("comChangeNowClass")
        binding.tvCommunityChangeDetailChangeInput.text = intent.getStringExtra("comChangeClass")
        binding.tvCommunityChangeDetailExchange.text = intent.getStringExtra("comChangeExchange")
    }
}
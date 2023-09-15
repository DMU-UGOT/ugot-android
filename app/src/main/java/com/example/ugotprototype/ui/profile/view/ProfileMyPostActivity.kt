package com.example.ugotprototype.ui.profile.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyPostBinding

class ProfileMyPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMyPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my_post)

        binding.layoutMypost.setOnClickListener { startActivity(Intent(this, ProfileMyPostTeamActivity::class.java)) }
        binding.layoutMypostStudy.setOnClickListener { startActivity(Intent(this, ProfileMyPostStudyActivity::class.java))}
        binding.layoutMypostCommunity.setOnClickListener { }
        binding.ivGroupCmuBack.setOnClickListener { finish() }
    }
}
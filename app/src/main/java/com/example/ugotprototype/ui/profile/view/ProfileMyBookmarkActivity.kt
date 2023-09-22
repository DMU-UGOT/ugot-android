package com.example.ugotprototype.ui.profile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileMyBookmarkBinding

class ProfileMyBookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileMyBookmarkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_my_bookmark)

        binding.layoutMypost.setOnClickListener { startActivity(Intent(this, ProfileTeamBookmarkActivity::class.java)) }
        binding.layoutMypostStudy.setOnClickListener { startActivity(Intent(this, ProfileStudyBookmarkActivity::class.java)) }
        binding.layoutMypostCommunity.setOnClickListener { }
        binding.ivGroupCmuBack.setOnClickListener { finish() }
    }
}
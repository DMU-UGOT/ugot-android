package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityGeneralNewGroupBinding

class CommunityGeneralNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityGeneralNewGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_general_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_general_new_group)

        backCommunityGeneralNewToMainActivity()
    }

    private fun backCommunityGeneralNewToMainActivity() {
        binding.btGeneralNewPostRegister.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btGeneralNewBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
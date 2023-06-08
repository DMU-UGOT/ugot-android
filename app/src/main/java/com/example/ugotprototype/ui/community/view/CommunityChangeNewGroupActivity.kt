package com.example.ugotprototype.ui.community.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityCommunityChangeNewGroupBinding

class CommunityChangeNewGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityChangeNewGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_change_new_group)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_change_new_group)

        spinnerChangeNewChoice()
        backCommunityChangeNewToMainActivity()
    }

    private fun spinnerChangeNewChoice() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.change_new_grade, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spChangeNewGrade.adapter = adapter
    }

    private fun backCommunityChangeNewToMainActivity() {
        binding.btChangeNewPostRegister.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }

        binding.btChangeNewBackToMain.setOnClickListener {
            Intent().putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }
}
package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamPostWriteDetailBinding

class TeamPostWriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamPostWriteDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_post_write_detail)

        postFieldSet()
        postClassSet()
        checkPostRegister()
    }

    private fun checkPostRegister() {
        binding.btTeamPostRegister.setOnClickListener {
            Log.d("test", binding.classSpinner.selectedItemId.toString())
            if (binding.classSpinner.selectedItemPosition != 0 && binding.fieldSpinner.selectedItemPosition != 0 && binding.etTitleName.getText()
                    .toString() != null && binding.etTitleDetail.getText()
                    .toString() != null && binding.etInputGithubLink.getText()
                    .toString() != null && binding.etInputKakaoOpenLink.getText().toString() != null
            ) {
                val resultIntent = Intent()
                resultIntent.putExtra("resultText", "text")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "입력되지 않은 항목이 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun postFieldSet() {

        val adapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_field_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.fieldSpinner.adapter = adapter
    }

    private fun postClassSet() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.team_post_class_item, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.classSpinner.adapter = adapter
    }
}
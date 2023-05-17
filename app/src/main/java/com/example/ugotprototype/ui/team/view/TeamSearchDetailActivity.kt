package com.example.ugotprototype.ui.team.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityTeamSearchDetailBinding
import com.google.android.material.chip.Chip;

class TeamSearchDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamSearchDetailBinding
    private lateinit var resultIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_search_detail)

        checkTextInput()
        backToMain()
    }

    private fun checkTextInput() {
        binding.svTextInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                resultIntent = Intent()
                resultIntent.putExtra("resultText", query)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    private fun backToMain() {
        binding.btBackToMain.setOnClickListener {
            resultIntent = Intent()
            resultIntent.putExtra("resultText", "text")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
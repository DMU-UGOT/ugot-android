package com.example.ugotprototype.ui.profile.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileStackBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileStackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileStackBinding
    private lateinit var skillTextViews: List<EditText>
    private val viewModel: ProfileStackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_stack)

        initSkillLayout()

        viewModel.getSkillList()

        viewModel.memberData.observe(this) {
            updateSkillLayouts(it.skill)
        }

        viewModel.patchOk.observe(this) {
            setResult(Activity.RESULT_OK)
            finish()
        }

        stackBackToMainActivity()
    }

    private fun stackBackToMainActivity() {

        binding.btStackPostRegister.setOnClickListener {
            viewModel.updateSkillList(
                listOf(
                    binding.etStack1.text.toString(),
                    binding.etStack2.text.toString()
                )
            )
        }

        binding.btStackBackToMain.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    private fun updateSkillLayouts(skillList: List<String>) {
        skillTextViews.forEachIndexed { index, editText ->
            editText.setText(skillList[index])
        }
    }

    private fun initSkillLayout() {
        skillTextViews = listOf(
            binding.etStack1,
            binding.etStack2,
        )
    }
}
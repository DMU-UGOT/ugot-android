package com.example.ugotprototype.ui.profile.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.FragmentLoadingLayout
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityProfileStudyBookmarkBinding
import com.example.ugotprototype.ui.profile.adapter.ProfilePostStudyRecyclerViewAdapter
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyStudyPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileStudyBookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileStudyBookmarkBinding
    private val viewModel: ProfileMyStudyPostViewModel by viewModels()
    private lateinit var profilePostStudyRecyclerviewAdapter: ProfilePostStudyRecyclerViewAdapter
    private val loadingDialog = FragmentLoadingLayout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_study_bookmark)

        viewLoadingLayout()

        binding.ivGroupCmuBack.setOnClickListener {
            finish()
        }

        profilePostStudyRecyclerviewAdapter = ProfilePostStudyRecyclerViewAdapter(viewModel)
        binding.rvTeam.adapter = profilePostStudyRecyclerviewAdapter

        viewModel.studyItemList.observe(this) {
            profilePostStudyRecyclerviewAdapter.setData(it)
        }
    }

    private fun viewLoadingLayout() {
        loadingDialog.isCancelable = false

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingDialog.dismiss()
            } else {
                loadingDialog.show(this.supportFragmentManager, "loadingDialog")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMyBookmark()
    }
}
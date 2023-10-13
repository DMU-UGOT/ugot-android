package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupHandOverBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.group.adapter.GroupHandOverRecyclerViewAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupHandOverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupHandOverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupHandOverBinding
    private val viewModel: GroupHandOverViewModel by viewModels()
    private lateinit var adapter: GroupHandOverRecyclerViewAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_hand_over)

        adapter = GroupHandOverRecyclerViewAdapter(viewModel, intent.getIntExtra(GROUP_ID, 0))

        binding.rvTeam.adapter = adapter

        viewModel.getTeamInformationData(intent.getIntExtra(GROUP_ID, 0))

        binding.ivTeamPrev.setOnClickListener {
            finish()
        }

        viewModel.teamPersonData.observe(this) {
            adapter.setFilterData(it)
        }

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        viewModel.isHandOver.observe(this) {
            if (it) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
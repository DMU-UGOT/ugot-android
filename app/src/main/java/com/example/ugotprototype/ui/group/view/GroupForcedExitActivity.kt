package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ugotprototype.R
import com.example.ugotprototype.databinding.ActivityGroupForcedExitBinding
import com.example.ugotprototype.ui.Loading.util.LoadingLayoutHelper
import com.example.ugotprototype.ui.group.adapter.GroupForcedExitAdapter
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.viewmodel.GroupForcedExitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupForcedExitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupForcedExitBinding
    private val viewModel: GroupForcedExitViewModel by viewModels()
    private lateinit var adapter: GroupForcedExitAdapter
    private val loadingLayoutHelper: LoadingLayoutHelper by lazy { LoadingLayoutHelper(this.supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_forced_exit)

        adapter = GroupForcedExitAdapter(viewModel, intent.getIntExtra(GROUP_ID, 0))
        binding.rvGroup.adapter = adapter

        viewModel.getGroupPersonList(intent.getIntExtra(GROUP_ID, 0))

        viewModel.teamPersonData.observe(this) {
            adapter.setFilterData(it)
        }

        viewModel.isUpdate.observe(this) {
            viewModel.getGroupPersonList(intent.getIntExtra(GROUP_ID, 0))
        }

        viewModel.isLoadingPage.observe(this) {
            if(it) {
                loadingLayoutHelper.dismissLoadingDialog()
            } else {
                loadingLayoutHelper.showLoadingDialog()
            }
        }

        binding.ivTeamPrev.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
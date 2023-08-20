package com.example.ugotprototype.ui.group.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData
import com.example.ugotprototype.databinding.FragmentGroupBinding
import com.example.ugotprototype.ui.group.adapter.GroupMiddleViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.adapter.GroupTopViewRecyclerViewAdapter
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : Fragment() {
    private lateinit var binding: FragmentGroupBinding
    private val groupViewModel: GroupViewModel by viewModels()
    private lateinit var groupTopViewAdapter: GroupTopViewRecyclerViewAdapter
    private var groupTopItems = ArrayList<GroupTopViewData>()
    private lateinit var groupMiddleViewAdapter: GroupMiddleViewRecyclerViewAdapter
    private var groupMiddleItems = ArrayList<GroupMiddleViewData>()

    companion object {
        const val TOKEN_DATA = BuildConfig.GITHUB_KEY
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group, container, false)
        binding.vm = groupViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupViewModel.getGroupList()
        testData()

        groupViewModel.setGroupTopData(groupTopItems)
        groupViewModel.setGroupMiddleData(groupMiddleItems)

        groupTopViewAdapter = GroupTopViewRecyclerViewAdapter()
        binding.rvGroupTopList.adapter = groupTopViewAdapter

        groupMiddleViewAdapter = GroupMiddleViewRecyclerViewAdapter()
        binding.rvGroupMiddleList.adapter = groupMiddleViewAdapter


        groupViewModel.groupTopItemList.observe(viewLifecycleOwner) {
            groupTopViewAdapter.setData(it)
        }

        groupViewModel.groupMiddleItemList.observe(viewLifecycleOwner) {
            groupMiddleViewAdapter.setData(it)
        }

        goToNewGroup()
        changeMyGroupCount()
    }

    private fun testData() {
        groupTopItems = arrayListOf(
            GroupTopViewData(
                "안드로이드 프로그래밍을 위한 그룹입니다", "안드로이드 프로그래밍을 위한 그룹으로써 질문과 답변이 활발하게 이루어지는중입니다", "3"
            ), GroupTopViewData(
                "집 초대 오픈 하우스 커뮤니티", "오픈 하우스 커뮤니티 인터스타일이 지속가능한 관계와 네트워킹 주제로 사이드 프로젝트를 기획 했습니다", "3"
            ), GroupTopViewData(
                "자연스러운 할 일 관리 자람잘함",
                "최근 자기개발의 중요성이 정말 중요해지고 있는거 같아요 그래서 사이드 프로젝트도 많이 활성화되고 있고 여러가지 스터디도 증가함에따라 만든 그룹입니다",
                "4"
            ), GroupTopViewData(
                "같이 앱 서비스 만들죠",
                "[목표] 앱 출시를 목표로 달리고 Jira를 통해 이슈관리, Slack을 통해 서로의 작업을 체크 할려고 하는 그룹입니다",
                "5"
            )
        )
        groupMiddleItems = arrayListOf(
            GroupMiddleViewData(
                "안드로이드 프로그래밍을 위한 그룹입니다", "안드로이드 프로그래밍을 위한 그룹으로써 질문과 답변이 활발하게 이루어지는중입니다", "3"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "집 초대 오픈 하우스 커뮤니티", "오픈 하우스 커뮤니티 인터스타일이 지속가능한 관계와 네트워킹 주제로 사이드 프로젝트를 기획 했습니다", "3"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "자연스러운 할 일 관리 자람잘함",
                "최근 자기개발의 중요성이 정말 중요해지고 있는거 같아요 그래서 사이드 프로젝트도 많이 활성화되고 있고 여러가지 스터디도 증가함에따라 만든 그룹입니다",
                "4"
            ),
            GroupMiddleViewData(
                "같이 앱 서비스 만들죠",
                "[목표] 앱 출시를 목표로 달리고 Jira를 통해 이슈관리, Slack을 통해 서로의 작업을 체크 할려고 하는 그룹입니다",
                "5"
            ),
            GroupMiddleViewData(
                "스타트업 팀빌딩 사이트", "스타트업의 팀원을 찾을 수 있는 사이트를 만들어갈 팀원이 있는 그룹입니다", "6"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "부모가 기획하는 교육용 게임",
                "아직 초등학교 입학도 하지 않은 아이를 키우는 부모님들의 아이가 재미있게 즐기면서 하는 게임을 만드는 그룹",
                "7"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "함께 성장하는 그룹", "스터디 네트워킹 목표", "8"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "블록체인 프로젝트", "블록체인 프로젝트 팀이 nft나 암호화폐를 판매한 이후 아무것도 하지않거나 자금을 들고튈때를 위한 프로젝트", "9"
                    ,"https://avatars.githubusercontent.com/u/130235792?v=4"
            ),
            GroupMiddleViewData(
                "메타커머스 프로젝트", "오픈마켓에 상품을 등록한다", "10","https://avatars.githubusercontent.com/u/130235792?v=4"
            )
        )
    }

    private fun changeMyGroupCount() {
        groupMiddleViewAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                groupViewModel.setGroupMiddleItemCount(groupMiddleViewAdapter.itemCount)
            }
        })

        groupViewModel.itemCount.observe(viewLifecycleOwner) { count ->
            binding.tvGroupCnt.text = count.toString()
        }
    }

    private fun goToNewGroup() {
        val goToNewGroupResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val resultText = data?.getStringExtra("resultText")
                    if (resultText != null) {
                        Log.d("main", resultText)
                    }
                }
            }

        binding.fabGroup.setOnClickListener {
            goToNewGroupResultLauncher.launch(
                Intent(
                    requireContext(), GroupNewGenerate::class.java
                )
            )
        }
    }
}
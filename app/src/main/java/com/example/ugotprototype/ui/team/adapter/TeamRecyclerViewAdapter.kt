package com.example.ugotprototype.ui.team.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.response.TeamPostResponse
import com.example.ugotprototype.databinding.ItemTeamListBinding
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_CREATE_TIME
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_DETAIL
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GITHUB_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_GOAL
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_ID
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_KAKAO_LINK
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_LANGUAGE
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_LEADER_CLASS
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS_CNT
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_STATUS_CNT_END
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_TITLE
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TEAM_TOPIC
import com.example.ugotprototype.ui.team.view.TeamPostDetailActivity
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel
import dagger.hilt.android.AndroidEntryPoint

class TeamRecyclerViewAdapter (private val viewModel: TeamViewModel) : RecyclerView.Adapter<TeamRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList: List<TeamPostResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TeamPostResponse) {
            binding.root.setOnClickListener {
                goToTeamPostDetail(item, binding.root.context)
            }

            binding.ivTeamBookmark.setOnClickListener {
                binding.ivTeamBookmark.isSelected = binding.ivTeamBookmark.isSelected != true
                viewModel.sendBookmark(item.teamId)
            }

            with(binding) {
                tvTitle.text = item.title
                tvDetailTitle.text = item.content
                tvFieldName.text = item.field
                tvCntEnd.text = item.allPersonnel.toString()
                tvViewCountNum.text = item.viewCount.toString()
                tvCntFirst.text = item.nowPersonnel.toString()
                Glide.with(root.context).load(item.avatarUrl).into(ivTeamLogo)
            }

            binding.ivTeamBookmark.isSelected = item.bookmark
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemTeamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(teamItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<TeamPostResponse>) {
        teamItemList = data
        notifyDataSetChanged()
    }

    fun goToTeamPostDetail(item: TeamPostResponse, context: Context) {
        Intent(context, TeamPostDetailActivity::class.java).apply {
            putExtra(TEAM_ID, item.teamId)
            putExtra(TEAM_TITLE, item.title)
            putExtra(TEAM_DETAIL, item.content)
            putExtra(TEAM_TOPIC, item.field)
            putExtra(TEAM_STATUS_CNT, item.nowPersonnel)
            putExtra(TEAM_STATUS_CNT_END, item.allPersonnel)
            putExtra(TEAM_LEADER_CLASS, item._class)
            putExtra(TEAM_GITHUB_LINK, item.gitHubLink)
            putExtra(TEAM_KAKAO_LINK, item.kakaoOpenLink)
            putExtra(TEAM_CREATE_TIME, item.createdAt)
            putExtra(TEAM_GOAL, item.goal)
            putExtra(TEAM_LANGUAGE, item.language)

            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(TEAM_STATUS, "모집 완료")
            } else {
                putExtra(TEAM_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
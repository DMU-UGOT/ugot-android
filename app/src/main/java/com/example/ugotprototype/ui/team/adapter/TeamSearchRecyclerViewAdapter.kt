package com.example.ugotprototype.ui.team.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.response.Team
import com.example.ugotprototype.databinding.ItemTeamSearchListBinding
import com.example.ugotprototype.ui.group.view.GroupFragment
import com.example.ugotprototype.ui.team.view.TeamFragment
import com.example.ugotprototype.ui.team.view.TeamPostDetailActivity
import com.example.ugotprototype.ui.team.viewmodel.TeamSearchViewModel

class TeamSearchRecyclerViewAdapter(private val viewModel: TeamSearchViewModel) :
    RecyclerView.Adapter<TeamSearchRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList: List<Team> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamSearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Team) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemTeamSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(teamItemList[position])
    }

    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<Team>) {
        teamItemList = data
        notifyDataSetChanged()
    }

    fun goToTeamPostDetail(item: Team, context: Context) {
        Intent(context, TeamPostDetailActivity::class.java).apply {
            putExtra(TeamFragment.TEAM_ID, item.teamId)
            putExtra(GroupFragment.GROUP_ID, item.groupId)

            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(TeamFragment.TEAM_STATUS, "모집 완료")
            } else {
                putExtra(TeamFragment.TEAM_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
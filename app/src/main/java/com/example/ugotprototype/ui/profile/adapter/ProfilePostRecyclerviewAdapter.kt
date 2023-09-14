package com.example.ugotprototype.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.response.TeamPostResponse
import com.example.ugotprototype.databinding.ItemMyTeamListBinding
import com.example.ugotprototype.ui.profile.view.ProfilePostDetailActivity
import com.example.ugotprototype.ui.team.view.TeamFragment

class ProfilePostRecyclerviewAdapter : RecyclerView.Adapter<ProfilePostRecyclerviewAdapter.MyViewHolder>() {

    var teamItemList: List<TeamPostResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemMyTeamListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TeamPostResponse) {
            binding.root.setOnClickListener {
                goToTeamPostDetail(item, binding.root.context)
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
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMyTeamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        Intent(context, ProfilePostDetailActivity::class.java).apply {
            putExtra(TeamFragment.TEAM_ID, item.teamId)

            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(TeamFragment.TEAM_STATUS, "모집 완료")
            } else {
                putExtra(TeamFragment.TEAM_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
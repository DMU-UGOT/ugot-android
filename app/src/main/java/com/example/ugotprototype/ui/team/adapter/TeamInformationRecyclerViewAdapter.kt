package com.example.ugotprototype.ui.team.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.databinding.ItemTeamInformationBinding
import com.example.ugotprototype.di.api.response.OrgMemberDataResponse
import com.example.ugotprototype.ui.team.view.TeamInformationActivity

class TeamInformationRecyclerViewAdapter :
    RecyclerView.Adapter<TeamInformationRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList: List<OrgMemberDataResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (bindingAdapterPosition == 0) {
                binding.ivTeamLeader.visibility = View.VISIBLE
            } else {
                binding.ivTeamLeader.visibility = View.INVISIBLE
            }

            binding.tvTeamInforName.text = teamItemList[bindingAdapterPosition].login
            binding.tvGitLink.text = teamItemList[bindingAdapterPosition].htmlUrl
            binding.tvInforTeamName.text = TeamInformationActivity.githubOrgName
            Glide.with(binding.root.context).load(teamItemList[bindingAdapterPosition].avatarUrl)
                .into(binding.ivProfileImage)
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemTeamInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(memberList: List<OrgMemberDataResponse>) {
        Log.d("test", "$memberList")
        teamItemList = memberList
        notifyDataSetChanged()
    }
}
package com.example.ugotprototype.ui.team.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.databinding.ItemTeamInformationBinding

class TeamInformationRecyclerViewAdapter :
    RecyclerView.Adapter<TeamInformationRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList: List<GroupDetailTeamInforData> = emptyList()

    inner class MyViewHolder(val binding: ItemTeamInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupDetailTeamInforData) {
            if (bindingAdapterPosition == 0) {
                binding.ivTeamLeader.visibility = View.VISIBLE
            } else {
                binding.ivTeamLeader.visibility = View.INVISIBLE
            }

            with(binding) {
                tvTeamInforName.text = item.nickname
                tvGitLink.text = item.gitHubLink
                tvInforTeamName.text = item.groupName
                tvInforField.text = item.interests
                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .into(binding.ivProfileImage)
            }
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
        holder.bind(teamItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<GroupDetailTeamInforData>) {
        Log.d("test", data.toString())
        teamItemList = data
        notifyDataSetChanged()
    }
}
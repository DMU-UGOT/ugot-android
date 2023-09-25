package com.example.ugotprototype.ui.team.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.databinding.ItemGroupTeamInformationBinding

class GroupTeamInforRecyclerViewAdapter :
    RecyclerView.Adapter<GroupTeamInforRecyclerViewAdapter.MyViewHolder>() {

    var groupItemList: List<GroupDetailTeamInforData> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupTeamInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupDetailTeamInforData) {
            if (bindingAdapterPosition == 0) {
                binding.ivTeamLeader.visibility = View.VISIBLE
            } else {
                binding.ivTeamLeader.visibility = View.INVISIBLE
            }

            with(binding) {
                tvTeamInforName.text = item.nickname
                tvInforField.text = item.interests
                tvInforTeamName.text = item.groupName
                tvGitLink.text = item.gitHubLink
                tvBlogLink.text = item.personalBlogLink
                Glide.with(root.context).load(item.avatarUrl).into(ivProfileImage)
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupTeamInformationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(groupItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(item: List<GroupDetailTeamInforData>) {
        groupItemList = item
        notifyDataSetChanged()
    }
}
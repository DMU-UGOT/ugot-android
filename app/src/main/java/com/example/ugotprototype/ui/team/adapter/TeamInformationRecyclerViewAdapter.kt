package com.example.ugotprototype.ui.team.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.databinding.ItemTeamInformationBinding

class TeamInformationRecyclerViewAdapter :
    RecyclerView.Adapter<TeamInformationRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList = arrayListOf<Int>()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            if (bindingAdapterPosition == 0) {
                binding.ivTeamLeader.visibility = View.VISIBLE
            } else {
                binding.ivTeamLeader.visibility = View.INVISIBLE
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
        holder.bind()
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(nowPersonCnt: Int) {

        // 현재 모집한 인원수만큼 리사이클러뷰 더미 아이템 추가
        for (i in 0 until nowPersonCnt) {
            teamItemList.add(i)
        }
        notifyDataSetChanged()
    }
}
package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.data.group.GroupEngagementData
import com.example.ugotprototype.databinding.ItemGroupCalendarNoticeListBinding
import com.example.ugotprototype.databinding.ItemGroupEngagementRateListBinding
import java.time.LocalDate


class GroupEngagementRateRecyclerViewAdapter() :
    RecyclerView.Adapter<GroupEngagementRateRecyclerViewAdapter.MyViewHolder>() {

    private var groupEngagementRateItemList = arrayListOf<GroupEngagementData>()

    inner class MyViewHolder(val binding: ItemGroupEngagementRateListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            binding.tvUserIndex.text = (position+1).toString()
            binding.tvUserName.text = groupEngagementRateItemList[position].login
            binding.tvCommitCount.text = groupEngagementRateItemList[position].contributions.toString()
            Glide.with(binding.root.context).load(groupEngagementRateItemList[position].avatarUrl)
                .into(binding.ivUserImg)

            if(bindingAdapterPosition % 2 == 1) {
                binding.objectLayout.setBackgroundColor(Color.parseColor("#10636363"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupEngagementRateListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = groupEngagementRateItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<GroupEngagementData>) {
        groupEngagementRateItemList = data
        Log.d("data", "$data")
        notifyDataSetChanged()
    }
}
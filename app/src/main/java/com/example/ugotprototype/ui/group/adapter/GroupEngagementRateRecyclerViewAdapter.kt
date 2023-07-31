package com.example.ugotprototype.ui.group.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.data.group.GroupEngagementRateData
import com.example.ugotprototype.databinding.ItemGroupCalendarNoticeListBinding
import com.example.ugotprototype.databinding.ItemGroupEngagementRateListBinding
import java.time.LocalDate

class GroupEngagementRateRecyclerViewAdapter() :
    RecyclerView.Adapter<GroupEngagementRateRecyclerViewAdapter.MyViewHolder>() {

    private var groupEngagementRateItemList = arrayListOf<GroupEngagementRateData>()

    inner class MyViewHolder(val binding: ItemGroupEngagementRateListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvUserIndex.text = groupEngagementRateItemList[bindingAdapterPosition].userIndex.toString()
            binding.tvUserName.text = groupEngagementRateItemList[bindingAdapterPosition].userName
            binding.tvCommitCount.text = groupEngagementRateItemList[bindingAdapterPosition].userCommitRate.toString()

            if(bindingAdapterPosition % 2 == 0) {
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

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupEngagementRateItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<GroupEngagementRateData>) {
        groupEngagementRateItemList = data
        notifyDataSetChanged()
    }
}
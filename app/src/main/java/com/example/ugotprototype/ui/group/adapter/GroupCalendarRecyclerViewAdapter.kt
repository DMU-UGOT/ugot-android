package com.example.ugotprototype.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupCalendarData
import com.example.ugotprototype.databinding.ItemGroupCalendarNoticeListBinding
import java.time.LocalDate

class GroupCalendarRecyclerViewAdapter() :
    RecyclerView.Adapter<GroupCalendarRecyclerViewAdapter.MyViewHolder>() {

    private var groupCalendarItemList = arrayListOf<GroupCalendarData>()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupCalendarNoticeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentGroupCalendarData: GroupCalendarData) {
            binding.vm = currentGroupCalendarData
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupCalendarNoticeListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(groupCalendarItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupCalendarItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setFilterData(data: ArrayList<GroupCalendarData>, selectedDate: LocalDate) {
        groupCalendarItemList =
            data.filter { it.date == selectedDate } as ArrayList<GroupCalendarData>
        notifyDataSetChanged()
    }
}
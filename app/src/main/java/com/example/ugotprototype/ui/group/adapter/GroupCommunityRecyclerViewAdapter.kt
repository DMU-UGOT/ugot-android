package com.example.ugotprototype.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupMessageList
import com.example.ugotprototype.databinding.ItemGroupCommunityChatListBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GroupCommunityRecyclerViewAdapter :
    RecyclerView.Adapter<GroupCommunityRecyclerViewAdapter.GroupCmuViewHolder>() {
    var groupItemList: List<GroupMessageList> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class GroupCmuViewHolder(val binding: ItemGroupCommunityChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupMessageList) {
            with(binding) {
                tvGroupCmuItemText.text = item.content
                tvItemGroupCmuNickName.text = item.nickname
                tvItemGroupCmuDay.text = convertDateTimeFormat(item.createdAt)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupCmuViewHolder {
        val binding =
            ItemGroupCommunityChatListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GroupCmuViewHolder(binding)
    }

    override fun getItemCount() = groupItemList.size

    override fun onBindViewHolder(holder: GroupCmuViewHolder, position: Int) {
        holder.bind(groupItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<GroupMessageList>) {
        groupItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun convertDateTimeFormat(isoDateTime: String): String {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")

        val dateTime = LocalDateTime.parse(isoDateTime, inputFormatter)
        return dateTime.format(outputFormatter)
    }
}
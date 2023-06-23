package com.example.ugotprototype.ui.group.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupCommunityChatViewData
import com.example.ugotprototype.databinding.ItemGroupCommunityChatListBinding
import com.example.ugotprototype.ui.group.view.GroupCommunityActivity

class GroupCommunityRecyclerViewAdapter : RecyclerView.Adapter<GroupCommunityRecyclerViewAdapter.GroupCmuViewHolder>(){
    var groupCmuItemList = arrayListOf<GroupCommunityChatViewData>()

    // 생성된 뷰 홀더에 값 지정
    inner class GroupCmuViewHolder(val binding: ItemGroupCommunityChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentGroupCommunityChatViewData : GroupCommunityChatViewData) {
            binding.groupCmuChatItem = currentGroupCommunityChatViewData

            binding.root.setOnClickListener {
                goToGroupCmuChatPostDetail(currentGroupCommunityChatViewData, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):GroupCmuViewHolder {
        val binding =
            ItemGroupCommunityChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupCmuViewHolder(binding)
    }

    override fun getItemCount() = groupCmuItemList.size

    override fun onBindViewHolder(holder: GroupCmuViewHolder, position: Int) {
        holder.bind(groupCmuItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<GroupCommunityChatViewData>) {
        groupCmuItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToGroupCmuChatPostDetail(item: GroupCommunityChatViewData, context: Context) {
        Intent(context, GroupCommunityActivity::class.java).apply {
            putExtra("groupCmuNickName", item.GroupCmuNickName)
            putExtra("groupCmuChatText", item.GroupCmuChatText)
            putExtra("groupCmuChatTime", item.GroupCmuChatTime)

            context.startActivity(this)
        }
    }
}
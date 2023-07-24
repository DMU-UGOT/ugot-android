package com.example.ugotprototype.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.databinding.ItemCommunityGeneralChatListBinding

class CommunityGeneralChatRecyclerViewAdapter : RecyclerView.Adapter<CommunityGeneralChatRecyclerViewAdapter.CommunityGeneralChatViewHolder>() {

    var communityGeneralChatItemList = arrayListOf<CommunityGeneralChatViewData>()

    // 생성된 뷰 홀더에 값 지정
    class CommunityGeneralChatViewHolder(val binding: ItemCommunityGeneralChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityGeneralChatViewData: CommunityGeneralChatViewData) {
            binding.communityGeneralChatItem = currentCommunityGeneralChatViewData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):CommunityGeneralChatViewHolder {
        val binding =
            ItemCommunityGeneralChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityGeneralChatViewHolder(binding)
    }

    override fun getItemCount() = communityGeneralChatItemList.size

    override fun onBindViewHolder(holder: CommunityGeneralChatViewHolder, position: Int) {
        holder.bind(communityGeneralChatItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityGeneralChatViewData>) {
        communityGeneralChatItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }
}
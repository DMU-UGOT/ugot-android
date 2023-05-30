package com.example.ugotprototype.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.ItemCommunityChangeListBinding

class CommunityChangeRecyclerViewAdapter : RecyclerView.Adapter<CommunityChangeRecyclerViewAdapter.CommunityChangeViewHolder>(){

    var communityChangeItemList = arrayListOf<CommunityChangeViewData>()

    // 생성된 뷰 홀더에 값 지정
    class CommunityChangeViewHolder(val binding: ItemCommunityChangeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityChangeViewData: CommunityChangeViewData) {
            binding.communityChangeItem = currentCommunityChangeViewData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):CommunityChangeViewHolder {
        val binding =
            ItemCommunityChangeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityChangeViewHolder(binding)
    }

    override fun getItemCount() = communityChangeItemList.size

    override fun onBindViewHolder(holder: CommunityChangeViewHolder, position: Int) {
        holder.bind(communityChangeItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityChangeViewData>) {
        communityChangeItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }
}
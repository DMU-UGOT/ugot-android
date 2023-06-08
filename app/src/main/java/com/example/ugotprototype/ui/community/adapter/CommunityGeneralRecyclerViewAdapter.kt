package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityGeneralViewData
import com.example.ugotprototype.databinding.ItemCommunityGeneralListBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralDetailActivity

class CommunityGeneralRecyclerViewAdapter : RecyclerView.Adapter<CommunityGeneralRecyclerViewAdapter.CommunityGeneralViewHolder>(){
    var communityGeneralItemList = arrayListOf<CommunityGeneralViewData>()

    // 생성된 뷰 홀더에 값 지정
    inner class CommunityGeneralViewHolder(val binding: ItemCommunityGeneralListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityGeneralViewData: CommunityGeneralViewData) {
            binding.communityGeneralItem = currentCommunityGeneralViewData

            binding.root.setOnClickListener {
                goToCommunityPostDetail(currentCommunityGeneralViewData, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):CommunityGeneralViewHolder {
        val binding =
            ItemCommunityGeneralListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityGeneralViewHolder(binding)
    }

    override fun getItemCount() = communityGeneralItemList.size

    override fun onBindViewHolder(holder: CommunityGeneralViewHolder, position: Int) {
        holder.bind(communityGeneralItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityGeneralViewData>) {
        communityGeneralItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToCommunityPostDetail(item: CommunityGeneralViewData, context: Context) {
        Intent(context, CommunityGeneralDetailActivity::class.java).apply {
            putExtra("comGeneralName", item.ComGeneralName)
            putExtra("comGeneralNickName", item.ComGeneralNickName)
            putExtra("comGeneralText", item.ComGeneralText)
            putExtra("comGeneralChat", item.ComGeneralChat)
            putExtra("comGeneralInquire", item.ComGeneralInquire)
            putExtra("comGeneralDate", item.ComGeneralDate)

            context.startActivity(this)
        }
    }
}
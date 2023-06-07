package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.ItemCommunityChangeListBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeDetailActivity

class CommunityChangeRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityChangeRecyclerViewAdapter.CommunityChangeViewHolder>() {

    var communityChangeItemList = arrayListOf<CommunityChangeViewData>()

    // 생성된 뷰 홀더에 값 지정
    inner class CommunityChangeViewHolder(val binding: ItemCommunityChangeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityChangeViewData: CommunityChangeViewData) {
            binding.communityChangeItem = currentCommunityChangeViewData

            binding.root.setOnClickListener {
                goToCommunityChangePostDetail(currentCommunityChangeViewData, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityChangeViewHolder {
        val binding =
            ItemCommunityChangeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CommunityChangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityChangeViewHolder, position: Int) {
        holder.bind(communityChangeItemList[position])
    }

    override fun getItemCount() = communityChangeItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityChangeViewData>) {
        communityChangeItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToCommunityChangePostDetail(item: CommunityChangeViewData, context: Context) {
        Intent(context, CommunityChangeDetailActivity::class.java).apply {
            putExtra("comChangeGrade", item.ComChangeGrade)
            putExtra("comChangeNickName", item.ComChangeNickName)
            putExtra("comChangeNowClass", item.ComChangeNowClass)
            putExtra("comChangeClass", item.ComChangeClass)
            putExtra("comChangeExchange", item.ComChangeExchange)
            putExtra("comChangeDate", item.ComChangeDate)

            context.startActivity(this)
        }
    }
}
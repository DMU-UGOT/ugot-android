package com.example.ugotprototype.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.profile.ProfileMessageDetailData
import com.example.ugotprototype.databinding.ItemProfileMessageDetailBinding
import com.example.ugotprototype.databinding.ItemProfileMessageDetailRightBinding

class ProfileMessageDetailRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LEFT = 1
    private val VIEW_TYPE_RIGHT = 2
    private val profileMessageDetailItemList = arrayListOf<ProfileMessageDetailData>()

    // ViewHolder 클래스 추가
    class ProfileMessageDetailViewHolder(val binding: ItemProfileMessageDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentProfileMessageDetailData: ProfileMessageDetailData) {
            binding.profileMessageDetailItem = currentProfileMessageDetailData
        }
    }

    class ProfileMessageDetailRightViewHolder(val binding: ItemProfileMessageDetailRightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentProfileMessageDetailData: ProfileMessageDetailData) {
            binding.profileMessageDetailItem = currentProfileMessageDetailData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LEFT) {
            val binding =
                ItemProfileMessageDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProfileMessageDetailViewHolder(binding)
        } else {
            val binding =
                ItemProfileMessageDetailRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProfileMessageDetailRightViewHolder(binding)
        }
    }

    override fun getItemCount() = profileMessageDetailItemList.size

    override fun getItemViewType(position: Int): Int {
        // 상대방 대화와 내 대화를 구분하여 ViewType을 반환합니다.
        return if (profileMessageDetailItemList[position].MessageDetailSender) VIEW_TYPE_RIGHT else VIEW_TYPE_LEFT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = profileMessageDetailItemList[position]
        when (holder) {
            is ProfileMessageDetailViewHolder -> holder.bind(currentItem)
            is ProfileMessageDetailRightViewHolder -> holder.bind(currentItem)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<ProfileMessageDetailData>) {
        profileMessageDetailItemList.clear()
        profileMessageDetailItemList.addAll(data)
        notifyDataSetChanged()
    }
}
package com.example.ugotprototype.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.profile.ProfileMessageDetailData
import com.example.ugotprototype.databinding.ItemProfileMessageDetailBinding

class ProfileMessageDetailRecyclerViewAdapter : RecyclerView.Adapter<ProfileMessageDetailRecyclerViewAdapter.ProfileMessageDetailViewHolder>() {
    var profileMessageDetailItemList = arrayListOf<ProfileMessageDetailData>()

    // 생성된 뷰 홀더에 값 지정
    class ProfileMessageDetailViewHolder(val binding: ItemProfileMessageDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentProfileMessageDetailData: ProfileMessageDetailData) {
            binding.profileMessageDetailItem = currentProfileMessageDetailData
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):ProfileMessageDetailViewHolder {
        val binding =
            ItemProfileMessageDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMessageDetailViewHolder(binding)
    }

    override fun getItemCount() = profileMessageDetailItemList.size

    override fun onBindViewHolder(holder: ProfileMessageDetailViewHolder, position: Int) {
        holder.bind(profileMessageDetailItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<ProfileMessageDetailData>) {
        profileMessageDetailItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun clearItems() {
        profileMessageDetailItemList.clear()
        notifyDataSetChanged()
    }
}
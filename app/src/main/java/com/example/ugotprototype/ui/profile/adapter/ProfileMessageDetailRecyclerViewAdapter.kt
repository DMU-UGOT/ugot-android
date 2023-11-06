package com.example.ugotprototype.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.ProfileMessageDetailPostResponse
import com.example.ugotprototype.databinding.ItemProfileMessageDetailRightBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileMessageDetailRecyclerViewAdapter(private val profileMessageDetailViewModel: ProfileMessageDetailViewModel) :
    RecyclerView.Adapter<ProfileMessageDetailRecyclerViewAdapter.ProfileMessageDetailViewHolder>() {

    var profileMessageDetailItemList : List<ProfileMessageDetailPostResponse> = emptyList()

    inner class ProfileMessageDetailViewHolder(val binding: ItemProfileMessageDetailRightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileMessageDetailPostResponse) {
            binding.ivMessageDetailDelete.setOnClickListener {
                profileMessageDetailViewModel.deleteMessageChatList(item.id)
            }

            with(binding) {
                tvMessageDetailRightName.text = item.senderName
                tvMessageDetailRightNameTwo.text = item.senderName
                tvMessageDetailRightText.text = item.content
                tvItemGroupRightCmuDay.text = formatDate(item.created_at)
                ivMessageDetailDelete.visibility = item.isDelete
            }

            profileMessageDetailViewModel.getUserNickname {
                if (it == item.senderName) {
                    binding.tvMessageDetailRightNameTwo.visibility = VISIBLE
                    binding.tvMessageDetailRightName.visibility = INVISIBLE
                } else{
                    binding.tvMessageDetailRightName.visibility = VISIBLE
                    binding.tvMessageDetailRightNameTwo.visibility = INVISIBLE
                }
            }
        }

        private fun formatDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val currentDate = Calendar.getInstance()
            val date = inputFormat.parse(dateString)
            val cal = Calendar.getInstance().apply { time = date }
            val dateFormat: SimpleDateFormat

            if (currentDate.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                currentDate.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
            ) {
                dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            } else {
                dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            }

            return dateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ProfileMessageDetailRecyclerViewAdapter.ProfileMessageDetailViewHolder {
        val binding =
            ItemProfileMessageDetailRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMessageDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileMessageDetailViewHolder, position: Int) {
        holder.bind(profileMessageDetailItemList[position])
    }

    override fun getItemCount() = profileMessageDetailItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<ProfileMessageDetailPostResponse>) {
        profileMessageDetailItemList = data
        notifyDataSetChanged()
    }

    fun updateAllItemsVisibility(visible: Int) {
        profileMessageDetailItemList.forEach { item ->
            item.isDelete = visible
        }
        notifyDataSetChanged()
    }
}
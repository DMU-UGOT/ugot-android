package com.example.ugotprototype.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.profile.ProfileMessageData
import com.example.ugotprototype.databinding.ItemProfileMessageDetailBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileMessageDetailRecyclerViewAdapter(private val profileMessageDetailViewModel: ProfileMessageDetailViewModel) :
    RecyclerView.Adapter<ProfileMessageDetailRecyclerViewAdapter.ProfileMessageDetailViewHolder>() {

    var profileMessageDetailItemList : List<ProfileMessageData> = emptyList()

    inner class ProfileMessageDetailViewHolder(val binding: ItemProfileMessageDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileMessageData) {
            binding.ivMessageChatDelete.setOnClickListener {
                profileMessageDetailViewModel.deleteMessageChatList(item.id)
            }

            with(binding) {
                tvMessageDetailName.text = item.senderName
                tvMessageDetailText.text = item.content
                tvItemGroupCmuDay.text = formatDate(item.created_at)
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
            ItemProfileMessageDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMessageDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileMessageDetailViewHolder, position: Int) {
        holder.bind(profileMessageDetailItemList[position])
    }

    override fun getItemCount() = profileMessageDetailItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<ProfileMessageData>) {
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
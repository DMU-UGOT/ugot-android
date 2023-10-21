package com.example.ugotprototype.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.ProfileMessagePostResponse
import com.example.ugotprototype.databinding.ItemProfileMessageBinding
import com.example.ugotprototype.ui.profile.view.MessageActivity.Companion.ROOM_ID
import com.example.ugotprototype.ui.profile.view.ProfileMessageDetailActivity
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMessageViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileMessageRecyclerViewAdapter(private val profileMessageViewModel: ProfileMessageViewModel) :
    RecyclerView.Adapter<ProfileMessageRecyclerViewAdapter.ProfileMessageViewHolder>() {

    var profileMessageItemList : List<ProfileMessagePostResponse> = emptyList()

    inner class ProfileMessageViewHolder(val binding: ItemProfileMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileMessagePostResponse) {
            binding.ivMessageDelete.setOnClickListener {
                profileMessageViewModel.deleteMessageList(item.room)
            }

            binding.root.setOnClickListener {
                goToMessageDetail(item, binding.root.context)
            }

            with(binding) {
                tvMessageName.text = item.senderName
                tvMessageText.text = item.content
                tvMessageDay.text = formatDate(item.created_at)
                ivMessageDelete.visibility = item.isDelete
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
        viewType: Int): ProfileMessageViewHolder {
        val binding =
            ItemProfileMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileMessageViewHolder, position: Int) {
        holder.bind(profileMessageItemList[position])
    }

    override fun getItemCount() = profileMessageItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<ProfileMessagePostResponse>) {
        profileMessageItemList = data
        notifyDataSetChanged()
    }

    fun updateAllItemsVisibility(visible: Int) {
        profileMessageItemList.forEach { item ->
            item.isDelete = visible
        }
        notifyDataSetChanged()
    }

    fun goToMessageDetail(item: ProfileMessagePostResponse, context: Context) {
        Intent(context, ProfileMessageDetailActivity::class.java).apply {
            putExtra(ROOM_ID, item.room)

            context.startActivity(this)
        }
    }
}
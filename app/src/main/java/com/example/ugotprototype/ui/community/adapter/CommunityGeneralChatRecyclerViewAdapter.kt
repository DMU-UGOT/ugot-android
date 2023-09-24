package com.example.ugotprototype.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.databinding.ItemCommunityGeneralChatListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommunityGeneralChatRecyclerViewAdapter : RecyclerView.Adapter<CommunityGeneralChatRecyclerViewAdapter.CommunityGeneralChatViewHolder>() {

    var communityGeneralChatItemList = arrayListOf<CommunityGeneralChatViewData>()

    // 생성된 뷰 홀더에 값 지정
    inner class CommunityGeneralChatViewHolder(val binding: ItemCommunityGeneralChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : CommunityGeneralChatViewData) {
            binding.tvCommunityGeneralItemNickName.text = item.nickname
            binding.tvCommunityGeneralItemText.text = item.content
            binding.tvCommunityGeneralItemTime.text = formatDate(item.createdAt)
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
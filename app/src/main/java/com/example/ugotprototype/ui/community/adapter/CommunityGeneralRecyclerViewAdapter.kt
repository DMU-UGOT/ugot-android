package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.databinding.ItemCommunityGeneralListBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralDetailActivity
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import java.text.SimpleDateFormat
import java.util.*

class CommunityGeneralRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityGeneralRecyclerViewAdapter.CommunityGeneralViewHolder>() {

    var communityGeneralItemList: List<CommunityGeneralPostResponse> = emptyList()

    inner class CommunityGeneralViewHolder(val binding: ItemCommunityGeneralListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityGeneralPostResponse) {
            binding.root.setOnClickListener {
                goToCommunityGeneralDetailPostDetail(item.id, binding.root.context)
            }

            with(binding) {
                tvCommunityId.text = item.id.toString()
                tvCommunityName.text = item.title
                tvCommunityText.text = item.content
                tvCommunityViewCount.text = formatViewCount(item.viewCount)
                tvCommunityVoteCount.text = formatVoteCount(item.commentCount)
                tvCommunityCreateAt.text = formatDate(item.created_at)
                tvCommunityMemberNickname.text = item.nickname
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

        private fun formatViewCount(viewCount: Int?): String {
            return viewCount?.toString() ?: "0"
        }

        private fun formatVoteCount(voteCount: Int?): String {
            return voteCount?.toString() ?: "0"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityGeneralViewHolder {
        val binding = ItemCommunityGeneralListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityGeneralViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityGeneralViewHolder, position: Int) {
        holder.bind(communityGeneralItemList[position])
    }

    override fun getItemCount() = communityGeneralItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<CommunityGeneralPostResponse>) {
        communityGeneralItemList = data
        notifyDataSetChanged()
    }

    fun goToCommunityGeneralDetailPostDetail(postId : Int, context: Context) {
        Intent(context, CommunityGeneralDetailActivity::class.java).apply {
            putExtra(GENERAL_ID, postId)

            context.startActivity(this)
        }
    }
}

package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.databinding.ItemCommunityGeneralListBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralDetailActivity
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_CONTENT
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_CREATE_AT
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_ID
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_NICKNAME
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_TITLE
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_VIEW_COUNT
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment.Companion.GENERAL_VOTE_COUNT
import java.text.SimpleDateFormat
import java.util.*

class CommunityGeneralRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityGeneralRecyclerViewAdapter.CommunityGeneralViewHolder>() {

    var communityGeneralItemList: List<CommunityGeneralPostResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class CommunityGeneralViewHolder(val binding: ItemCommunityGeneralListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityGeneralPostResponse) {
            binding.root.setOnClickListener {
                goToCommunityGeneralDetailPostDetail(item, binding.root.context)
            }

            with(binding) {
                tvCommunityId.text = item.id.toString()
                tvCommunityName.text = item.title
                tvCommunityText.text = item.content
                tvCommunityViewCount.text = formatViewCount(item.viewCount)
                tvCommunityVoteCount.text = formatVoteCount(item.voteCount)
                tvCommunityCreateAt.text = formatDate(item.created_at)
                tvCommunityMemberId.text = item.nickname
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
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToCommunityGeneralDetailPostDetail(item: CommunityGeneralPostResponse, context: Context) {
        Intent(context, CommunityGeneralDetailActivity::class.java).apply {
            putExtra(GENERAL_ID, item.id)
            putExtra(GENERAL_TITLE, item.title)
            putExtra(GENERAL_CONTENT, item.content)
            putExtra(GENERAL_VIEW_COUNT, item.viewCount)
            putExtra(GENERAL_VOTE_COUNT, item.voteCount)
            putExtra(GENERAL_CREATE_AT, item.created_at)
            putExtra(GENERAL_NICKNAME, item.nickname)

            context.startActivity(this)
        }
    }
}

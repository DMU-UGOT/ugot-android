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
                goToCommunityPostDetail(item, binding.root.context)
            }
            with(binding) {
                this.tvCommunityId.text = item.id
                this.tvCommunityName.text = item.title
                this.tvCommunityText.text = item.content
                this.tvCommunityDate.text = formatDate(item.created_at)
                this.tvCommunityViewCount.text = formatViewCount(item.viewCount)
                this.tvCommunityVoteCount.text = formatVoteCount(item.voteCount)
            }
        }

        private fun formatDate(dateString: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val date = inputFormat.parse(dateString)
            return outputFormat.format(date)
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

    fun goToCommunityPostDetail(item: CommunityGeneralPostResponse, context: Context) {
        Intent(context, CommunityGeneralDetailActivity::class.java).apply {
            putExtra(GENERAL_ID, item.id)
            putExtra(GENERAL_TITLE, item.title)
            putExtra(GENERAL_CONTENT, item.content)
            putExtra(GENERAL_VIEW_COUNT, item.viewCount)
            putExtra(GENERAL_VOTE_COUNT, item.voteCount)
            putExtra(GENERAL_CREATE_AT, item.created_at)

            context.startActivity(this)
        }
    }
}
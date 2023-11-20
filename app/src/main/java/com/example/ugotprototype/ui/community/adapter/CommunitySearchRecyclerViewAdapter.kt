package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.databinding.ItemCommunitySearchListBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralDetailActivity
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment
import java.text.SimpleDateFormat
import java.util.*

class CommunitySearchRecyclerViewAdapter :
    RecyclerView.Adapter<CommunitySearchRecyclerViewAdapter.MyViewHolder>() {

    var generalItemList: List<CommunityGeneralPostResponse> = emptyList()

    inner class MyViewHolder(val binding: ItemCommunitySearchListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityGeneralPostResponse) {
            binding.root.setOnClickListener {
                goToGeneralPostDetail(item.id ,binding.root.context)
            }

            with(binding) {
                tvCommunityTitle.text = item.title
                tvCommunityId.text = item.id.toString()
                tvCommunityNickname.text = item.nickname
                tvCommunityContent.text = item.content
                tvCommunityVoteCount.text = formatVoteCount(item.commentCount)
                tvCommunityViewCount.text = formatViewCount(item.viewCount)
                tvCommunityDate.text= formatDate(item.created_at)
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
                dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            } else {
                dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCommunitySearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(generalItemList[position])
    }

    override fun getItemCount() = generalItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<CommunityGeneralPostResponse>) {
        generalItemList = data
        notifyDataSetChanged()
    }

    fun goToGeneralPostDetail(postId : Int, context: Context) {
        Intent(context, CommunityGeneralDetailActivity::class.java).apply {
            putExtra(CommunityGeneralFragment.GENERAL_ID, postId)

            context.startActivity(this)
        }
    }
}
package com.example.ugotprototype.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.CommunityGeneralPostResponse
import com.example.ugotprototype.databinding.ItemProfileGeneralListBinding
import com.example.ugotprototype.ui.community.view.CommunityGeneralFragment
import com.example.ugotprototype.ui.profile.view.ProfileMyGeneralCommunityDetailActivity
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralPostViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfilePostGeneralRecyclerViewAdapter (private val viewModel: ProfileMyGeneralPostViewModel) :
    RecyclerView.Adapter<ProfilePostGeneralRecyclerViewAdapter.GeneralViewHolder>() {

    var generalItemList: List<CommunityGeneralPostResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class GeneralViewHolder(val binding: ItemProfileGeneralListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityGeneralPostResponse) {
            binding.ivDelete.setOnClickListener {
                viewModel.deletePost(item.id)
            }

            binding.root.setOnClickListener {
                goToProfileGeneralPostDetail(item.id, binding.root.context)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralViewHolder {
        val binding =
            ItemProfileGeneralListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GeneralViewHolder(binding)
    }

    override fun getItemCount() = generalItemList.size

    override fun onBindViewHolder(holder: GeneralViewHolder, position: Int) {
        holder.bind(generalItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<CommunityGeneralPostResponse>) {
        generalItemList = data
        notifyDataSetChanged()
    }

    fun updateAllItemsVisibility(visible: Int) {
        generalItemList.forEach { item ->
            item.isDelete = visible
        }
        notifyDataSetChanged()
    }

    fun goToProfileGeneralPostDetail(postId : Int, context: Context) {
        Intent(context, ProfileMyGeneralCommunityDetailActivity::class.java).apply {
            putExtra(CommunityGeneralFragment.GENERAL_ID, postId)

            context.startActivity(this)
        }
    }
}
package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.response.CommunityChangePostResponse
import com.example.ugotprototype.databinding.ItemCommunityChangeListBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeDetailActivity
import com.example.ugotprototype.ui.community.view.CommunityChangeFragment.Companion.CHANGE_CLASS_CHANGE_ID
import java.text.SimpleDateFormat
import java.util.*

class CommunityChangeRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityChangeRecyclerViewAdapter.CommunityChangeViewHolder>() {

    var communityChangeItemList : List<CommunityChangePostResponse> = emptyList()

    inner class CommunityChangeViewHolder(val binding: ItemCommunityChangeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityChangePostResponse) {
            binding.root.setOnClickListener {
                goToCommunityChangePostDetail(item.classChangeId, binding.root.context)
            }

            with(binding) {
                tvCommunityChangeNowClassInput.text = item.currentClass
                tvCommunityChangeClassInput.text = item.changeClass
                tvCommunityChangeGradeInput.text = item.grade
                tvCommunityChangeDateInput.text = formatDate(item.createdAt)
                tvCommunityChangeExchangeInput.text = item.status
            }

            when(item.status) {
                "교환 가능" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#14A492")) // 녹색
                "교환 대기" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#F1BD1A")) // 노란색
                "교환 완료" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#BE2E22")) // 빨간색
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
        viewType: Int
    ): CommunityChangeViewHolder {
        val binding =
            ItemCommunityChangeListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CommunityChangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityChangeViewHolder, position: Int) {
        holder.bind(communityChangeItemList[position])
    }

    override fun getItemCount() = communityChangeItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<CommunityChangePostResponse>) {
        communityChangeItemList = data
        notifyDataSetChanged()
    }

    fun goToCommunityChangePostDetail(classChangeId: Int, context: Context) {
        Intent(context, CommunityChangeDetailActivity::class.java).apply {
            putExtra(CHANGE_CLASS_CHANGE_ID, classChangeId)

            context.startActivity(this)
        }
    }
}
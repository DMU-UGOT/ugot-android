package com.example.ugotprototype.ui.community.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.ItemCommunityChangeListBinding
import com.example.ugotprototype.ui.community.view.CommunityChangeDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommunityChangeRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityChangeRecyclerViewAdapter.CommunityChangeViewHolder>() {

    var communityChangeItemList = arrayListOf<CommunityChangeViewData>()

    inner class CommunityChangeViewHolder(val binding: ItemCommunityChangeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityChangeViewData: CommunityChangeViewData) {
            binding.communityChangeItem = currentCommunityChangeViewData

            // ComChangeExchange 값에 따라 텍스트 색상 설정
            when (currentCommunityChangeViewData.ComChangeExchange) {
                "교환 가능" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#14A492")) // 녹색
                "교환 대기" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#F1BD1A")) // 노란색
                "교환 완료" -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.parseColor("#BE2E22")) // 빨간색
                else -> binding.tvCommunityChangeExchangeInput.setTextColor(Color.BLACK) // 기본 검은색
            }
            // formattedDate 속성을 사용하여 날짜 텍스트를 설정합니다.
            binding.tvCommunityChangeDateInput.text = currentCommunityChangeViewData.formattedDate

            binding.root.setOnClickListener {
                goToCommunityChangePostDetail(currentCommunityChangeViewData, binding.root.context)
            }
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

    fun setData(data: ArrayList<CommunityChangeViewData>) {
        communityChangeItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToCommunityChangePostDetail(item: CommunityChangeViewData, context: Context) {
        Intent(context, CommunityChangeDetailActivity::class.java).apply {
            putExtra("comChangeGrade", item.ComChangeGrade)
            putExtra("comChangeNickName", item.ComChangeNickName)
            putExtra("comChangeNowClass", item.ComChangeNowClass)
            putExtra("comChangeClass", item.ComChangeClass)
            putExtra("comChangeExchange", item.ComChangeExchange)
            putExtra("comChangeDate", SimpleDateFormat("yy/MM/dd hh:mm", Locale.getDefault()).format(item.ComChangeDate))

            context.startActivity(this)
        }
    }

    // 날짜가 같은지 확인하는 확장 함수 추가
    fun Date.isSameDay(other: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = this
        cal2.time = other
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }
}
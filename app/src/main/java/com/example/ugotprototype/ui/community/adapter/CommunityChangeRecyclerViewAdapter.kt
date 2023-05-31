package com.example.ugotprototype.ui.community.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.R
import com.example.ugotprototype.data.community.CommunityChangeViewData
import com.example.ugotprototype.databinding.ItemCommunityChangeListBinding

class CommunityChangeRecyclerViewAdapter :
    RecyclerView.Adapter<CommunityChangeRecyclerViewAdapter.CommunityChangeViewHolder>() {

    var communityChangeItemList = arrayListOf<CommunityChangeViewData>()

    // 생성된 뷰 홀더에 값 지정
    class CommunityChangeViewHolder(val binding: ItemCommunityChangeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCommunityChangeViewData: CommunityChangeViewData) {
            binding.communityChangeItem = currentCommunityChangeViewData
            binding.ivCommunityChangeMessage.setOnClickListener {
//                showInputDialog(currentCommunityChangeViewData)
            }
        }
//
//        private fun showInputDialog(item: CommunityChangeViewData) {
//            val dialog = Dialog(binding.root.context)
//            val dialogBinding = ItemCommunityChangeListBinding.inflate(LayoutInflater.from(dialog.context))
//            dialog.setContentView(dialogBinding.root)
//
////            dialogBinding.titleTextView.text = item.title
//
//
//            dialogBinding.ivCommunityChangeMessage.setOnClickListener {
////                val userInput = dialogBinding.editText.text.toString()
//                // 사용자의 입력 처리 코드 작성
//                // item에 대한 작업 수행
//
//                dialog.dismiss() // 모달 창 닫기
//            }
//            dialog.show() // 모달 창 표시
//        }
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

    override fun getItemCount() = communityChangeItemList.size

    override fun onBindViewHolder(holder: CommunityChangeViewHolder, position: Int) {
        holder.bind(communityChangeItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityChangeViewData>) {
        communityChangeItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }
}
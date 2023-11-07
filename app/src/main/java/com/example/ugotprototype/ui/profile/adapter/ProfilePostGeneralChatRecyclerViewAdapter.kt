package com.example.ugotprototype.ui.profile.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.community.CommunityGeneralChatViewData
import com.example.ugotprototype.databinding.ItemProfileGeneralChatListBinding
import com.example.ugotprototype.ui.profile.viewmodel.ProfileMyGeneralChatViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfilePostGeneralChatRecyclerViewAdapter (private val profileMyGeneralChatViewModel: ProfileMyGeneralChatViewModel, private val generalId: Int) :
    RecyclerView.Adapter<ProfilePostGeneralChatRecyclerViewAdapter.ProfileGeneralChatViewHolder>() {

    var profileGeneralChatItemList = arrayListOf<CommunityGeneralChatViewData>()

    inner class ProfileGeneralChatViewHolder(val binding: ItemProfileGeneralChatListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item : CommunityGeneralChatViewData) {
            binding.tvCommunityGeneralChatDelete.setOnClickListener {
                showDeleteConfirmationDialog(item)
            }

            with(binding){
                tvCommunityGeneralItemNickName.text = item.nickname
                tvCommunityGeneralItemText.text = item.content
                tvCommunityGeneralItemTime.text = formatDate(item.createdAt)
            }

            if (profileMyGeneralChatViewModel.getLoggedInUserId().toString() == item.memberId.toString()) {
                binding.tvCommunityGeneralChatDelete.visibility = View.VISIBLE
            } else {
                binding.tvCommunityGeneralChatDelete.visibility = View.GONE
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

        private fun showDeleteConfirmationDialog(item: CommunityGeneralChatViewData) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("삭제 확인")
            builder.setMessage("정말로 이 댓글을 삭제하시겠습니까?")
            builder.setPositiveButton("예") { dialog, which ->
                profileMyGeneralChatViewModel.deleteChatDetailText(generalId , item.commentId)
            }
            builder.setNegativeButton("아니오") { dialog, which ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):ProfileGeneralChatViewHolder {
        val binding =
            ItemProfileGeneralChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileGeneralChatViewHolder(binding)
    }

    override fun getItemCount() = profileGeneralChatItemList.size

    override fun onBindViewHolder(holder: ProfileGeneralChatViewHolder, position: Int) {
        holder.bind(profileGeneralChatItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<CommunityGeneralChatViewData>) {
        profileGeneralChatItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }
}
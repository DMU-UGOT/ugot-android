package com.example.ugotprototype.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.profile.ProfileMessageData
import com.example.ugotprototype.databinding.ItemProfileMessageBinding
import com.example.ugotprototype.ui.profile.view.ProfileMessageDetailActivity

class ProfileMessageRecyclerViewAdapter :
    RecyclerView.Adapter<ProfileMessageRecyclerViewAdapter.ProfileMessageViewHolder>() {
    var profileMessageItemList = arrayListOf<ProfileMessageData>()
    private val dataList: MutableList<ProfileMessageData> = mutableListOf()

    // 생성된 뷰 홀더에 값 지정
    inner class ProfileMessageViewHolder(val binding: ItemProfileMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentProfileMessageViewData: ProfileMessageData) {
            binding.profileMessageItem = currentProfileMessageViewData

            binding.root.setOnClickListener {
                goToMessageDetail(currentProfileMessageViewData, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileMessageViewHolder {
        val binding =
            ItemProfileMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMessageViewHolder(binding)
    }

    override fun getItemCount() = profileMessageItemList.size

    override fun onBindViewHolder(holder: ProfileMessageViewHolder, position: Int) {
        holder.bind(profileMessageItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<ProfileMessageData>) {
        profileMessageItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun clearItems() {
        profileMessageItemList.clear()
        notifyDataSetChanged()
    }

    fun goToMessageDetail(item: ProfileMessageData, context: Context) {
        Intent(context, ProfileMessageDetailActivity::class.java).apply {
            putExtra("MessageName", item.MessageName)
            putExtra("MessageText", item.MessageText)
            putExtra("MessageTime", item.MessageTime)

            context.startActivity(this)
        }
    }

    fun setDataList(dataList: List<ProfileMessageData>) {
        // 중복된 이름 처리를 위해 데이터 목록을 정리하는 메서드
        this.dataList.clear()
        this.dataList.addAll(removeDuplicateNames(dataList))
        notifyDataSetChanged()
    }

    private fun removeDuplicateNames(dataList: List<ProfileMessageData>): List<ProfileMessageData> {
        val filteredList: MutableList<ProfileMessageData> = mutableListOf()
        val nameSet: MutableSet<String> = mutableSetOf()

        for (item in dataList) {
            if (!nameSet.contains(item.MessageName)) {
                filteredList.add(item)
                nameSet.add(item.MessageName)
            }
        }
        return filteredList
    }
}
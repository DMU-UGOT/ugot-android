package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupGetFavoritesList
import com.example.ugotprototype.databinding.ItemGroupTopListBinding
import com.example.ugotprototype.ui.group.view.GroupDetailActivity
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID

class GroupTopViewRecyclerViewAdapter() :
    RecyclerView.Adapter<GroupTopViewRecyclerViewAdapter.MyViewHolder>() {

    var groupItemList: List<GroupGetFavoritesList> = emptyList()

    inner class MyViewHolder(val binding: ItemGroupTopListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupGetFavoritesList) {
            binding.root.setOnClickListener {
                goToPostDetail(item, binding.root.context)
            }

            with(binding) {
                Glide.with(root.context).load(item.avatarUrl).into(ivFavoritesMiddleImg)
                tvGroupTitle.text = item.groupName
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemGroupTopListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(groupItemList[position])

    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<GroupGetFavoritesList>) {
        Log.d("test", data.toString())
        groupItemList = data
        notifyDataSetChanged()
    }

    fun goToPostDetail(item: GroupGetFavoritesList, context: Context) {
        Intent(context, GroupDetailActivity::class.java).apply {
            putExtra(GROUP_ID, item.groupId)
            context.startActivity(this)
        }
    }
}
package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.databinding.ItemGroupMiddleListBinding
import com.example.ugotprototype.ui.group.view.GroupDetailActivity
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_ID
import com.example.ugotprototype.ui.group.view.GroupFragment.Companion.GROUP_NAME
import com.example.ugotprototype.ui.group.viewmodel.GroupViewModel

class GroupMiddleViewRecyclerViewAdapter(private val viewModel: GroupViewModel) :
    RecyclerView.Adapter<GroupMiddleViewRecyclerViewAdapter.MyViewHolder>() {

    var groupItemList: List<GroupMiddleViewData> = emptyList()

    inner class MyViewHolder(val binding: ItemGroupMiddleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupMiddleViewData) {
            binding.root.setOnClickListener {
                goToPostDetail(item, binding.root.context)
            }

            binding.ivBookmark.setOnClickListener {
                binding.ivBookmark.isSelected = binding.ivBookmark.isSelected != true
                viewModel.setGroupFavorites(item.groupId)
            }

            with(binding) {
                tvGroupMiddleGroupName.text = item.groupName
                tvGroupMiddleDetail.text = item.content
                ivGroupPersonCnt.text = item.nowPersonnel.toString()
                Glide.with(root.context).load(item.avatarUrl).into(ivFavoritesMiddleImg)
                ivBookmark.isSelected = item.isFavorites
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemGroupMiddleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    fun setData(data: List<GroupMiddleViewData>) {
        Log.d("test", data.toString())
        groupItemList = data
        notifyDataSetChanged()
    }

    fun goToPostDetail(item: GroupMiddleViewData, context: Context) {
        Intent(context, GroupDetailActivity::class.java).apply {
            putExtra(GROUP_ID, item.groupId)
            putExtra(GROUP_NAME, item.githubUrl)
            context.startActivity(this)
        }
    }
}
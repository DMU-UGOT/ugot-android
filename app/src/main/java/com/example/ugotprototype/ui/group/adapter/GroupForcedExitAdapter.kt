package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.databinding.ItemGroupPersonListBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupForcedExitViewModel

class GroupForcedExitAdapter(
    private val viewModel: GroupForcedExitViewModel,
    private val groupId: Int
) : RecyclerView.Adapter<GroupForcedExitAdapter.MyViewHolder>() {

    var groupItemList: List<GroupDetailTeamInforData> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupPersonListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: GroupDetailTeamInforData) {
            with(binding) {
                tvTeamInforName.text = item.nickname
                tvInforField.text = item.interests
                tvGitLink.text = item.gitHubLink
                tvBlogLink.text = item.personalBlogLink
                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .into(binding.ivProfileImage)
            }

            binding.ivMenu.setOnClickListener {
                popUpMenu(it, item.memberId)
            }

            if(bindingAdapterPosition == 0) {
                binding.ivMenu.isVisible = false
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupPersonListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
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

    fun setFilterData(data: List<GroupDetailTeamInforData>) {
        groupItemList = data
        notifyDataSetChanged()
    }

    fun popUpMenu(view: View, memberId: Int) {
        PopupMenu(view.context, view).apply {
            menuInflater.inflate(R.menu.group_forced_exit_menu, menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item1 -> {
                        viewModel.groupForcedExit(groupId, memberId)
                        true
                    }

                    else -> false
                }
            }
        }.show()
    }
}
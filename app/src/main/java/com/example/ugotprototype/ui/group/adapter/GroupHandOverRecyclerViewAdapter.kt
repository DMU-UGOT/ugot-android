package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.databinding.ItemGroupHandoverListBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupHandOverViewModel

class GroupHandOverRecyclerViewAdapter(
    private val viewModel: GroupHandOverViewModel, private val groupId: Int
) : RecyclerView.Adapter<GroupHandOverRecyclerViewAdapter.MyViewHolder>() {

    var groupItemList: List<GroupDetailTeamInforData> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupHandoverListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: GroupDetailTeamInforData) {
            if (bindingAdapterPosition == 0) {
                binding.ivTeamLeader.visibility = View.VISIBLE
            } else {
                binding.ivTeamLeader.visibility = View.INVISIBLE
            }

            with(binding) {
                tvTeamInforName.text = item.nickname
                tvInforField.text = item.interests
                tvInforTeamName.text = item.groupName
                tvGitLink.text = item.gitHubLink
                tvBlogLink.text = item.personalBlogLink
                Glide.with(root.context).load(item.avatarUrl).into(ivProfileImage)
            }

            binding.ivMenu.setOnClickListener {
                popUpMenu(it, item.memberId)
            }

            if(bindingAdapterPosition == 0) {
                binding.ivMenu.visibility = View.INVISIBLE
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupHandoverListBinding.inflate(
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
            menuInflater.inflate(R.menu.group_handover_menu, menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item1 -> {
                        viewModel.handOverLeader(groupId, memberId)
                        true
                    }

                    else -> false
                }
            }
        }.show()
    }
}
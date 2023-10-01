package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.R
import com.example.ugotprototype.data.group.GroupGetApplicationList
import com.example.ugotprototype.databinding.ItemGroupApplicationListBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupRequestApplicationViewModel

class GroupRequestApplicationAdapter(
    private val viewModel: GroupRequestApplicationViewModel, private val groupId: Int
) : RecyclerView.Adapter<GroupRequestApplicationAdapter.MyViewHolder>() {

    var groupItemList: List<GroupGetApplicationList> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupApplicationListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: GroupGetApplicationList) {
            with(binding) {
                tvTeamInforName.text = item.nickname
                tvInforField.text = item.skill.toString()
                tvGitLink.text = item.gitHubLink
                tvBlogLink.text = item.personalBlogLink
                tvInforGrade.text = item.grade.toString() + item._class
                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .into(binding.ivProfileImage)
            }

            binding.ivMenu.setOnClickListener {
                popUpMenu(it, item.applicationId)
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupApplicationListBinding.inflate(
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

    fun setFilterData(data: List<GroupGetApplicationList>) {
        groupItemList = data
        notifyDataSetChanged()
    }

    fun popUpMenu(view: View, applicationId: Int) {
        PopupMenu(view.context, view).apply {
            menuInflater.inflate(R.menu.group_accept_menu, menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item1 -> {
                        viewModel.receiveApplication(groupId, applicationId)
                        true
                    }

                    R.id.item2 -> {
                        viewModel.rejectApplication(groupId, applicationId)
                        true
                    }

                    else -> false
                }
            }
        }.show()
    }
}
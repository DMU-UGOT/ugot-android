package com.example.ugotprototype.ui.group.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.data.group.GroupTopViewData
import com.example.ugotprototype.databinding.ItemGroupTopListBinding
import com.example.ugotprototype.ui.group.view.GroupDetailActivity

class GroupTopViewRecyclerViewAdapter :
    RecyclerView.Adapter<GroupTopViewRecyclerViewAdapter.MyViewHolder>() {

    var groupTopItemList = arrayListOf<GroupTopViewData>()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupTopListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentGroupData: GroupTopViewData) {
            binding.vm = currentGroupData

            binding.root.setOnClickListener {
                goToPostDetail(currentGroupData, binding.root.context)
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
        holder.bind(groupTopItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupTopItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<GroupTopViewData>) {
        groupTopItemList = data
        notifyDataSetChanged()
    }

    fun goToPostDetail(item: GroupTopViewData, context: Context) {
        Intent(context, GroupDetailActivity::class.java).apply {
            putExtra("groupName", item.groupName)
            putExtra("groupDetail", item.groupDetail)
            putExtra("groupPersonCnt", item.groupPersonCnt)
            context.startActivity(this)
        }
    }
}
package com.example.ugotprototype.ui.group.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupMiddleViewData
import com.example.ugotprototype.databinding.ItemGroupMiddleListBinding

class GroupMiddleViewRecyclerViewAdapter() :
    RecyclerView.Adapter<GroupMiddleViewRecyclerViewAdapter.MyViewHolder>() {

    private var groupMiddleItemList = arrayListOf<GroupMiddleViewData>()
    private lateinit var listener: OnOkClickListener

    fun setOnOkClickListener(listener: (GroupMiddleViewData) -> Unit) {
        this.listener = object : OnOkClickListener {
            override fun onClick(groupMiddleViewData: GroupMiddleViewData) {
                listener(groupMiddleViewData)
            }
        }
    }

    interface OnOkClickListener {
        fun onClick(groupMiddleViewData: GroupMiddleViewData)
    }


    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupMiddleListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentGroupData: GroupMiddleViewData) {
            binding.vm = currentGroupData
            binding.root.setOnClickListener {
                listener.onClick(currentGroupData)
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
        holder.bind(groupMiddleItemList[position])

    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = groupMiddleItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<GroupMiddleViewData>) {
        groupMiddleItemList = data
        notifyDataSetChanged()
    }
}
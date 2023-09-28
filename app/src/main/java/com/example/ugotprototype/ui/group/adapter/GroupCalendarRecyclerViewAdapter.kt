package com.example.ugotprototype.ui.group.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.group.GroupOneDayNoticeData
import com.example.ugotprototype.databinding.ItemGroupCalendarNoticeListBinding
import com.example.ugotprototype.ui.group.viewmodel.GroupCalendarViewModel

class GroupCalendarRecyclerViewAdapter(
    private val viewModel: GroupCalendarViewModel,
    private val groupLeaderId: String,
    private val groupId: Int
) : RecyclerView.Adapter<GroupCalendarRecyclerViewAdapter.MyViewHolder>() {

    var groupItemList: List<GroupOneDayNoticeData> = emptyList()
    private var partsDay: List<String> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemGroupCalendarNoticeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupOneDayNoticeData) {
            binding.tvDetail.setText(item.content)

            binding.ivEdit.setOnClickListener {
                binding.tvDetail.isEnabled = true
            }

            binding.ivDelete.setOnClickListener {
                viewModel.deleteNotice(item.noticeId)
            }

            binding.tvDetail.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    viewModel.updateNotice(
                        partsDay, binding.tvDetail.text.toString(), groupId, item.noticeId
                    ) {
                        binding.tvDetail.isEnabled = !it
                    }

                    return@setOnEditorActionListener true
                }
                false
            }

            teamLeaderCheck(binding)
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGroupCalendarNoticeListBinding.inflate(
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

    fun setFilterData(data: List<GroupOneDayNoticeData>) {
        groupItemList = data
        notifyDataSetChanged()
    }

    fun teamLeaderCheck(binding: ItemGroupCalendarNoticeListBinding) {
        viewModel.groupLeaderCheck(groupLeaderId) {
            if (it) {
                with(binding) {
                    ivEdit.visibility = android.view.View.VISIBLE
                    ivDelete.visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    fun getYearMonthDay(text: String) {
        partsDay = text.split("-")
    }
}
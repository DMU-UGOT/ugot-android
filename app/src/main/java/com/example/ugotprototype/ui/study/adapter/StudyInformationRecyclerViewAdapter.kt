package com.example.ugotprototype.ui.team.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.group.GroupDetailTeamInforData
import com.example.ugotprototype.databinding.ItemStudyInformationBinding

class StudyInformationRecyclerViewAdapter :
    RecyclerView.Adapter<StudyInformationRecyclerViewAdapter.MyViewHolder>() {

    var studyItemList: List<GroupDetailTeamInforData> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemStudyInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupDetailTeamInforData) {
            if (bindingAdapterPosition == 0) {
                binding.ivStudyLeader.visibility = View.VISIBLE
            } else {
                binding.ivStudyLeader.visibility = View.INVISIBLE
            }

            with(binding) {
                tvStudyInforName.text = item.nickname
                tvInforField.text = item.interests
                tvBlogLink.text = item.personalBlogLink
                tvGitLink.text = item.gitHubLink
                tvInforStudyName.text = item.groupName

                Glide.with(binding.root.context)
                    .load(item.avatarUrl)
                    .into(binding.ivProfileImage)
            }
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemStudyInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(studyItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = studyItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<GroupDetailTeamInforData>) {

        studyItemList = data
        notifyDataSetChanged()
    }
}
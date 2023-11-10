package com.example.ugotprototype.ui.study.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ItemStudyListBinding
import com.example.ugotprototype.ui.group.view.GroupFragment
import com.example.ugotprototype.ui.study.view.StudyFragment
import com.example.ugotprototype.ui.study.view.StudyPostDetailActivity
import com.example.ugotprototype.ui.study.viewmodel.StudySearchViewModel

class StudySearchRecyclerViewAdapter(private val viewModel: StudySearchViewModel) :
    RecyclerView.Adapter<StudySearchRecyclerViewAdapter.MyViewHolder>() {

    var studyItemList: List<StudyGetPost> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemStudyListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudyGetPost) {
            binding.root.setOnClickListener {
                goToTeamPostDetail(item, binding.root.context)
            }

            binding.ivStBookmark.setOnClickListener {
                binding.ivStBookmark.isSelected = binding.ivStBookmark.isSelected != true
                viewModel.sendBookmark(item.studyId)
            }

            with(binding) {
                tvStTitle.text = item.title
                tvStText.text = item.content
                tvCntEnd.text = item.allPersonnel.toString()
                tvViewCountNum.text = item.viewCount.toString()
                tvCntFirst.text = item.nowPersonnel.toString()
                tvStMeet.text = item.isContact
                Glide.with(root.context).load(item.avatarUrl).into(ivStImage)
            }

            binding.ivStBookmark.isSelected = item.bookmark
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemStudyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(studyItemList[position])
    }

    override fun getItemCount() = studyItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<StudyGetPost>) {
        studyItemList = data
        notifyDataSetChanged()
    }

    fun goToTeamPostDetail(item: StudyGetPost, context: Context) {
        Intent(context, StudyPostDetailActivity::class.java).apply {
            putExtra(StudyFragment.STUDY_ID, item.studyId)
            putExtra(GroupFragment.GROUP_ID, item.groupId)

            //모집중인지 모집완료인지 모집현황 체크
            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(StudyFragment.STUDY_STATUS, "모집 완료")
            } else {
                putExtra(StudyFragment.STUDY_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
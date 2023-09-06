package com.example.ugotprototype.ui.study.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ItemStudyListBinding
import com.example.ugotprototype.ui.study.view.StudyFragment
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_TITLE
import com.example.ugotprototype.ui.study.view.StudyPostDetailActivity

class StudySearchRecyclerViewAdapter :
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
            putExtra(STUDY_TITLE, item.title)
            putExtra(StudyFragment.STUDY_DETAIL, item.content)
            putExtra(StudyFragment.STUDY_STATUS_CNT, item.nowPersonnel)
            putExtra(StudyFragment.STUDY_STATUS_CNT_END, item.allPersonnel)
            putExtra(StudyFragment.STUDY_GITHUB_LINK, item.gitHubLink)
            putExtra(StudyFragment.STUDY_KAKAO_LINK, item.kakaoOpenLink)
            putExtra(StudyFragment.STUDY_CREATE_TIME, item.createdAt)

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
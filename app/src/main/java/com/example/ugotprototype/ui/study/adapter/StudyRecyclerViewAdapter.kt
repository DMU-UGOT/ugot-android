package com.example.ugotprototype.ui.study.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.ui.study.view.StudyPostDetailActivity
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ItemStudyListBinding
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_CREATE_TIME
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_DETAIL
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_GITHUB_LINK
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_KAKAO_LINK
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS_CNT
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_STATUS_CNT_END
import com.example.ugotprototype.ui.study.view.StudyFragment.Companion.STUDY_TITLE
import com.example.ugotprototype.ui.study.viewmodel.StudyViewModel
import com.example.ugotprototype.ui.team.viewmodel.TeamViewModel

class StudyRecyclerViewAdapter (private val viewModel: StudyViewModel): RecyclerView.Adapter<StudyRecyclerViewAdapter.StudyViewHolder>() {

    var studyItemList: List<StudyGetPost> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class StudyViewHolder(val binding: ItemStudyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StudyGetPost) {
            binding.root.setOnClickListener {
                goToStudyPostDetail(item, binding.root.context)
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
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyViewHolder {
        val binding =
            ItemStudyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudyViewHolder(binding)
    }

    // 뷰 홀더의 개수를 리턴해 준다
    override fun getItemCount() = studyItemList.size

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: StudyViewHolder, position: Int) {
        holder.bind(studyItemList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: List<StudyGetPost>) {
        studyItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToStudyPostDetail(item: StudyGetPost, context: Context) {
        Intent(context, StudyPostDetailActivity::class.java).apply {
            putExtra(STUDY_TITLE, item.title)
            putExtra(STUDY_DETAIL, item.content)
            putExtra(STUDY_STATUS_CNT, item.nowPersonnel)
            putExtra(STUDY_STATUS_CNT_END, item.allPersonnel)
            putExtra(STUDY_GITHUB_LINK, item.gitHubLink)
            putExtra(STUDY_KAKAO_LINK, item.kakaoOpenLink)
            putExtra(STUDY_CREATE_TIME, item.createdAt)

            //모집중인지 모집완료인지 모집현황 체크
            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(STUDY_STATUS, "모집 완료")
            } else {
                putExtra(STUDY_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
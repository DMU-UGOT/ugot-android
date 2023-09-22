package com.example.ugotprototype.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.data.study.StudyGetPost
import com.example.ugotprototype.databinding.ItemProfileStudyListBinding
import com.example.ugotprototype.ui.profile.view.ProfileStudyPostDetailActivity
import com.example.ugotprototype.ui.team.view.TeamFragment

class ProfilePostStudyRecyclerViewAdapter : RecyclerView.Adapter<ProfilePostStudyRecyclerViewAdapter.StudyViewHolder>() {

    var studyItemList: List<StudyGetPost> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class StudyViewHolder(val binding: ItemProfileStudyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StudyGetPost) {
            Log.d("item", item.toString())
            binding.root.setOnClickListener {
                goToStudyPostDetail(item, binding.root.context)
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
            ItemProfileStudyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        Intent(context, ProfileStudyPostDetailActivity::class.java).apply {
            putExtra(TeamFragment.TEAM_ID, item.studyId)

            if (item.nowPersonnel == item.allPersonnel) {
                putExtra(TeamFragment.TEAM_STATUS, "모집 완료")
            } else {
                putExtra(TeamFragment.TEAM_STATUS, "모집 중")
            }

            context.startActivity(this)
        }
    }
}
package com.example.ugotprototype.ui.study.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.ui.study.view.StudyPostDetailActivity
import com.example.ugotprototype.data.study.StudyData
import com.example.ugotprototype.databinding.ItemStudyListBinding

class StudyRecyclerViewAdapter : RecyclerView.Adapter<StudyRecyclerViewAdapter.StudyViewHolder>() {

    var studyItemList = arrayListOf<StudyData>()

    // 생성된 뷰 홀더에 값 지정
    inner class StudyViewHolder(val binding: ItemStudyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentStudyData: StudyData) {
            binding.studyItem = currentStudyData

            binding.root.setOnClickListener {
                goToStudyPostDetail(currentStudyData, binding.root.context)
            }

            binding.ivStBookmark.setOnClickListener {
                binding.ivStBookmark.isSelected = binding.ivStBookmark.isSelected != true
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

    fun setData(data: ArrayList<StudyData>) {
        studyItemList = data
        notifyDataSetChanged()  // 데이터 갱신
    }

    fun goToStudyPostDetail(item: StudyData, context: Context) {
        Intent(context, StudyPostDetailActivity::class.java).apply {
            putExtra("studyTitle", item.title)
            putExtra("studyDetail", item.text)
            putExtra("studyStatusCnt", item.statusCntFirst)
            putExtra("studyStatusCntEnd", item.statusCntEnd)

            //모집중인지 모집완료인지 모집현황 체크
            if (item.statusCntEnd == item.statusCntFirst) {
                putExtra("studyCurrent", "모집 완료")
            } else {
                putExtra("studyCurrent", "모집 중")
            }

            context.startActivity(this)
        }
    }
}
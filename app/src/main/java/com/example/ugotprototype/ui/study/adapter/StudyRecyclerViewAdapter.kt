package com.example.ugotprototype.ui.study.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.study.StudyData
import com.example.ugotprototype.databinding.ItemStudyListBinding

class StudyRecyclerViewAdapter : RecyclerView.Adapter<StudyRecyclerViewAdapter.StudyViewHolder>() {

    var studyItemList = arrayListOf<StudyData>()

    // 생성된 뷰 홀더에 값 지정
    class StudyViewHolder(val binding: ItemStudyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentStudyData: StudyData) {
            binding.studyItem = currentStudyData
            binding.ivStBookmark.isSelected = currentStudyData.bookmark

            binding.ivStBookmark.setOnClickListener {
                if(binding.ivStBookmark.isSelected == true){
                    binding.ivStBookmark.isSelected = false
                }else{
                    binding.ivStBookmark.isSelected = true
                }
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
}
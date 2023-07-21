package com.example.ugotprototype.ui.team.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.ui.team.view.TeamPostDetailActivity
import com.example.ugotprototype.data.team.TeamData
import com.example.ugotprototype.databinding.ItemTeamListBinding

class TeamRecyclerViewAdapter : RecyclerView.Adapter<TeamRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList = arrayListOf<TeamData>()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentTeamData: TeamData) {
            binding.teamItem = currentTeamData

            binding.root.setOnClickListener {
                goToTeamPostDetail(currentTeamData, binding.root.context)
            }

            binding.ivTeamBookmark.setOnClickListener {
                binding.ivTeamBookmark.isSelected = binding.ivTeamBookmark.isSelected != true
            }

            Glide.with(binding.root.context).load(currentTeamData.imageUrl).into(binding.ivTeamLogo)

        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemTeamListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(teamItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount() = teamItemList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(data: ArrayList<TeamData>) {
        teamItemList = data
        notifyDataSetChanged()
    }

    fun goToTeamPostDetail(item: TeamData, context: Context) {
        Intent(context, TeamPostDetailActivity::class.java).apply {
            putExtra("teamTitle", item.title)
            putExtra("teamDetail", item.detailTitle)
            putExtra("teamTopic", item.topic)
            putExtra("teamStatusCnt", item.statusCntFirst)
            putExtra("teamStatusCntEnd", item.statusCntEnd)
            putExtra("teamLeaderClass", item.teamLeaderClass)

            //모집중인지 모집완료인지 모집현황 체크
            if (item.statusCntEnd == item.statusCntFirst) {
                putExtra("teamCurrent", "모집 완료")
            } else {
                putExtra("teamCurrent", "모집 중")
            }

            context.startActivity(this)
        }
    }
}
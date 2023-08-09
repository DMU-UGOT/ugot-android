package com.example.ugotprototype.ui.team.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ugotprototype.ui.team.view.TeamPostDetailActivity
import com.example.ugotprototype.data.team.TeamData
import com.example.ugotprototype.databinding.ItemTeamListBinding
import com.example.ugotprototype.di.api.response.OrgMemberDataResponse
import com.example.ugotprototype.di.api.response.TeamPostResponse

class TeamRecyclerViewAdapter : RecyclerView.Adapter<TeamRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList: List<TeamPostResponse> = emptyList()

    // 생성된 뷰 홀더에 값 지정
    inner class MyViewHolder(val binding: ItemTeamListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentTeamPostResponse: TeamPostResponse) {
            binding.root.setOnClickListener {
                goToTeamPostDetail(currentTeamPostResponse, binding.root.context)
            }

            binding.ivTeamBookmark.setOnClickListener {
                binding.ivTeamBookmark.isSelected = binding.ivTeamBookmark.isSelected != true
            }

            binding.tvTitle.text = teamItemList[bindingAdapterPosition].title
            binding.tvDetailTitle.text = teamItemList[bindingAdapterPosition].content
            binding.tvFieldName.text = teamItemList[bindingAdapterPosition].field
            binding.tvCntEnd.text = teamItemList[bindingAdapterPosition].allPersonnel.toString()
            binding.tvViewCountNum.text = teamItemList[bindingAdapterPosition].viewCount.toString()
            binding.tvCntFirst.text = teamItemList[bindingAdapterPosition].nowPersonnel.toString()
            Glide.with(binding.root.context).load(currentTeamPostResponse.avatarUrl).into(binding.ivTeamLogo)

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

    fun setData(data: List<TeamPostResponse>) {
        teamItemList = data
        notifyDataSetChanged()
    }

    fun goToTeamPostDetail(item: TeamPostResponse, context: Context) {
        Intent(context, TeamPostDetailActivity::class.java).apply {

            putExtra("teamTitle", item.title)
            putExtra("teamDetail", item.content)
            putExtra("teamTopic", item.field)
            putExtra("teamStatusCnt", item.nowPersonnel)
            putExtra("teamStatusCntEnd", item.allPersonnel)
            putExtra("teamLeaderClass", item._class)
            putExtra("teamGitHubLink", item.gitHubLink)
            putExtra("teamKakaoLink", item.kakaoOpenLink)

            // 총명수는 api 데이터에 있지만 현재명수가 없어가지고 현재명수 받고해야함
            if (item.nowPersonnel == item.allPersonnel) {
                putExtra("teamCurrent", "모집 완료")
            } else {
                putExtra("teamCurrent", "모집 중")
            }

            context.startActivity(this)
        }
    }
}
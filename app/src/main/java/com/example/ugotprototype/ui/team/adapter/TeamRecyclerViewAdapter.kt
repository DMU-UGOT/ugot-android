package com.example.ugotprototype.ui.team.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.TeamData
import com.example.ugotprototype.databinding.FragmentTeamItemBinding

class TeamRecyclerViewAdapter : RecyclerView.Adapter<TeamRecyclerViewAdapter.MyViewHolder>() {

    var teamItemList = arrayListOf<TeamData>()

    // 생성된 뷰 홀더에 값 지정
    class MyViewHolder(val binding: FragmentTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentTeamData: TeamData) {
            binding.teamItem = currentTeamData
        }
    }

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FragmentTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(teamItemList[position])
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return teamItemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
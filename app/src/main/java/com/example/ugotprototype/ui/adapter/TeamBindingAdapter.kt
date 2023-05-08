package com.example.ugotprototype.ui.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ugotprototype.data.TeamData

object TeamBindingAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: ArrayList<TeamData>) {

        if (recyclerView.adapter == null) {
            val adapter = TeamRecyclerViewAdapter()
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }

        val myAdapter = recyclerView.adapter as TeamRecyclerViewAdapter

        myAdapter.teamItemList = items
        myAdapter.notifyDataSetChanged()
    }
}
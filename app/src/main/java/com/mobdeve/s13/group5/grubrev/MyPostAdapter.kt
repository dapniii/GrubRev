package com.mobdeve.s13.group5.grubrev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyPostAdapter(private val data: ArrayList<Review>): RecyclerView.Adapter<MyPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_layout, parent, false)
        return MyPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.bindData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
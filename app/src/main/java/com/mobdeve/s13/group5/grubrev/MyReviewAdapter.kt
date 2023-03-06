package com.mobdeve.s13.group5.grubrev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter

class MyReviewAdapter(private val data: ArrayList<Review>): Adapter<MyReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_layout, parent, false)
        return MyReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bindData(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
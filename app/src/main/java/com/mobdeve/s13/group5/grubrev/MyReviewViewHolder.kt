package com.mobdeve.s13.group5.grubrev

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val userTv: TextView = itemView.findViewById(R.id.userTv)
    private val commentTv: TextView = itemView.findViewById(R.id.commentTv)
    private val ratingTv: TextView = itemView.findViewById(R.id.ratingTv)

    fun bindData(review: Review) {
        userTv.text = review.user
        commentTv.text = review.comment
        ratingTv.text = review.rating.toString()
    }
}
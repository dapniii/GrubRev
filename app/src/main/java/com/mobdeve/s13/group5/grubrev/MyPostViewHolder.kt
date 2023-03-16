package com.mobdeve.s13.group5.grubrev

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyPostViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    private val postRestaurantTv: TextView = itemView.findViewById(R.id.postRestaurantTv)
    private val postCommentTv: TextView = itemView.findViewById(R.id.postCommentTv)
    private val postRatingTv: TextView = itemView.findViewById(R.id.postRatingTv)

    fun bindData(review: Review) {
        postRestaurantTv.text = "@" + review.restaurant
        postCommentTv.text = review.comment
        postRatingTv.text = review.rating.toString()

    }
}
package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val reviewUserTv: TextView = itemView.findViewById(R.id.reviewUserTv)
    private val reviewCommentTv: TextView = itemView.findViewById(R.id.reviewCommentTv)
    private val reviewRatingTv: TextView = itemView.findViewById(R.id.reviewRatingTv)

    fun bindData(review: Review) {
        reviewUserTv.text = review.user
        reviewCommentTv.text = review.comment
        reviewRatingTv.text = review.rating.toString()

        reviewUserTv.setOnClickListener {
            val intent = Intent(itemView.context, ProfileActivity::class.java)
            intent.putExtra("SHOW_LOGOUT", false)
            itemView.context.startActivity(intent)
        }
    }
}
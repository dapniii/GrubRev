package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val userTv: TextView = itemView.findViewById(R.id.userTv)
    private val userRestaurantTv: TextView = itemView.findViewById(R.id.userRestaurantTv)
    private val commentTv: TextView = itemView.findViewById(R.id.commentTv)
    private val ratingTv: TextView = itemView.findViewById(R.id.ratingTv)

    fun bindData(review: Review) {
        userTv.text = review.user
        userRestaurantTv.text = "@" + review.restaurant
        commentTv.text = review.comment
        ratingTv.text = review.rating.toString()

        userTv.setOnClickListener {
            val intent = Intent(itemView.context, ProfileActivity::class.java)
            intent.putExtra("SHOW_LOGOUT", false)
            itemView.context.startActivity(intent)
        }
    }
}
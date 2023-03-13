package com.mobdeve.s13.group5.grubrev

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RestaurantActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var restaurantTv: TextView
    private lateinit var restaurantIv: ImageView
    private lateinit var overallRatingTv: TextView
    private lateinit var addCommentBtn: FloatingActionButton
    private lateinit var restaurantRv: RecyclerView
    private lateinit var backToMapIv: ImageView

    //TODO: Temp Restaurant
    private var currResto = "SecretAgno"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        //Initialize
        this.restaurantTv = findViewById(R.id.restaurantTv)
        this.restaurantIv = findViewById(R.id.restaurantIv)
        this.overallRatingTv = findViewById(R.id.overallRatingTv)
        this.addCommentBtn = findViewById(R.id.addCommentBtn)
        this.restaurantRv = findViewById(R.id.restaurantRv)
        this.backToMapIv = findViewById(R.id.backToMapIv)


        //Filter Data to Current Restaurant
        val filteredReviews = filterToRestaurant(currResto)

        //Restaurant Image
        this.restaurantIv.setImageResource(R.drawable.restaurant_placeholder)

        //Restaurant Details
        this.restaurantTv.text = currResto
        this.overallRatingTv.text = getAverageRating(filteredReviews).toString()


        //Restaurant Reviews
        this.restaurantRv.adapter = MyReviewAdapter(filteredReviews as ArrayList<Review>)
        this.restaurantRv.layoutManager = LinearLayoutManager(this)

        //OnClick
        this.backToMapIv.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun filterToRestaurant(restaurant: String): List<Review> {
        return reviewList.filter{it.restaurant == restaurant}
    }

    private fun getAverageRating(filteredReviews: List<Review>): Double {
        return filteredReviews.map{it.rating}.average()
    }

}
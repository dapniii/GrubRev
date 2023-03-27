package com.mobdeve.s13.group5.grubrev

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RestaurantActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var restaurantTv: TextView
    private lateinit var restaurantIv: ImageView
    private lateinit var overallRatingTv: TextView
    private lateinit var addCommentBtn: FloatingActionButton
    private lateinit var restaurantRv: RecyclerView
    private lateinit var backToMapIv: ImageView

    //Firebase
    private var firebaseDb = Firebase.firestore

    //TODO: Temp Restaurant
//    val currResto = intent.getStringExtra("RESTAURANT").toString()

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

        //Intents
        val resIntent = this.intent
        val currResto = resIntent.getStringExtra("RESTAURANT")


        //Filter Data to Current Restaurant
        //val filteredReviews = filterToRestaurant(currResto)
        val filteredReviews = currResto?.let { filterToRestaurant(it) }

        //Restaurant Image
        this.restaurantIv.setImageResource(R.drawable.restaurant_placeholder)

        //Restaurant Details
        this.restaurantTv.text = currResto
        //this.overallRatingTv.text = getAverageRating(filteredReviews).toString()
        this.overallRatingTv.text = filteredReviews?.let { getAverageRating(it)}


        //Restaurant Reviews
        this.restaurantRv.adapter = MyReviewAdapter(filteredReviews as ArrayList<Review>)
        this.restaurantRv.layoutManager = LinearLayoutManager(this)

        //OnClick
        this.backToMapIv.setOnClickListener(View.OnClickListener {
            finish()
        })

        this.addCommentBtn.setOnClickListener(View.OnClickListener {
            openAddReviewActivity()
        })
    }

    private fun filterToRestaurant(restaurant: String): List<Review> {
        return reviewList.filter{it.restaurant == restaurant}
    }

    private fun getAverageRating(filteredReviews: List<Review>): String {
        val average = filteredReviews.map { it.rating }.average()
        return String.format("%.1f", average)
    }

    private fun openAddReviewActivity() {
        val intent = Intent(this, AddReviewActivity::class.java)
        startActivity(intent)
    }

    //This is a temp function only to be ran once to populate db with review data
    private fun setReviewstoDB(reviewList: ArrayList<Review>) {
        for (review in reviewList) {
            val reviewData = hashMapOf(
                "restaurant" to review.restaurant,
                "user" to review.user,
                "comment" to review.comment,
                "rating" to review.rating
            )

            firebaseDb.collection("reviews").add(reviewData)
                //Notify if marker added to db
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "Review added to Firestore with ID: ${documentReference.id}")
                }
                //Notify if marker wasn't added to db
                .addOnFailureListener { error ->
                    Log.d(ContentValues.TAG, "Error adding review to Firestore: $error")
                }
        }
    }

}
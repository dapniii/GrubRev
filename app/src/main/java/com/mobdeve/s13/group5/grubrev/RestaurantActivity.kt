package com.mobdeve.s13.group5.grubrev

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import org.osmdroid.util.GeoPoint

class RestaurantActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var restaurantTv: TextView
    private lateinit var restaurantIv: ImageView
    private lateinit var overallRatingTv: TextView
    private lateinit var addCommentBtn: FloatingActionButton
    private lateinit var restaurantRv: RecyclerView
    private lateinit var backToMapIv: ImageView
    private lateinit var noReviewNoticeTv: TextView
    private lateinit var overallRatingIv: ImageView

    //Firebase
    private var firebaseDb = Firebase.firestore


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
        this.noReviewNoticeTv = findViewById(R.id.noReviewNoticeTv)
        this.overallRatingIv = findViewById(R.id.overallRatingIv)

        //Intents
        val resIntent = this.intent
        val currResto = resIntent.getStringExtra("RESTAURANT")

        /*TODO:
           1. DONE - Add Function to update Restaurant's avgReview whenever view is re-rendered
           2. DONE - Add onStart function which will refresh Reviews
           3. Fix Reviews to show in order (e.g. SecretAgno Candice 5 star review always on top)
         */
        //OnClick
        this.backToMapIv.setOnClickListener(View.OnClickListener {
            finish()
        })
        this.addCommentBtn.setOnClickListener(View.OnClickListener {
            openAddReviewActivity(currResto.toString())
        })
    }

    override fun onStart() {
        super.onStart()

        //Intents
        val resIntent = this.intent
        val currResto = resIntent.getStringExtra("RESTAURANT")

        //Filter Data to Current Restaurant
        getReviews(currResto.toString()) { filteredReviews ->
            //Restaurant Image
            this.restaurantIv.setImageResource(R.drawable.restaurant_placeholder)

            //Restaurant Details
            this.restaurantTv.text = currResto

            //If there are restaurant reviews, show average rating and
            //hide the "no review" notice
            if (filteredReviews.isNotEmpty()) {
                this.overallRatingTv.text = filteredReviews?.let { getAverageRating(it) }
                this.noReviewNoticeTv.visibility = View.GONE
                //Otherwise, set restaurant average rating to blank and hide its text box
            } else {
                this.overallRatingTv.text = " "
                this.overallRatingIv.visibility = View.GONE
            }
            //this.overallRatingTv.text = getAverageRating(filteredReviews).toString()

            //Restaurant Reviews
            this.restaurantRv.adapter = MyReviewAdapter(filteredReviews as ArrayList<Review>)
            this.restaurantRv.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun filterToRestaurant(restaurant: String): List<Review> {
        return reviewList.filter{it.restaurant == restaurant}
    }

    private fun getAverageRating(filteredReviews: List<Review>): String {
        val average = filteredReviews.map { it.rating }.average()
        return String.format("%.1f", average)
    }

    private fun openAddReviewActivity(restaurant: String) {
        val intent = Intent(this, AddReviewActivity::class.java)
        intent.putExtra("RESTAURANT", restaurant)
        startActivity(intent)
    }

    //This is a temp function only to be ran once to populate db with review data
    private fun addReviewstoDB(reviewList: ArrayList<Review>) {
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
                    Log.d(TAG, "Review added to Firestore with ID: ${documentReference.id}")
                }
                //Notify if marker wasn't added to db
                .addOnFailureListener { error ->
                    Log.d(TAG, "Error adding review to Firestore: $error")
                }
        }
    }

    private fun getReviews(currResto: String, callback: (ArrayList<Review>) -> Unit) {
        val filteredReviews = arrayListOf<Review>()

        firebaseDb.collection("reviews")
            .whereEqualTo("restaurant", currResto).get()
            .addOnSuccessListener { storedReviews ->
                for (storedReview in storedReviews) {
                    val restaurant = storedReview["restaurant"] as String
                    val user = storedReview["user"] as String
                    val comment = storedReview["comment"] as String
                    val rating = if (storedReview["rating"] is Long) {
                        (storedReview["rating"] as Long).toDouble()
                    } else {
                        storedReview["rating"] as Double
                    }

                    val review = Review(restaurant, user, comment, rating)
                    filteredReviews.add(review)
                    Log.d(ContentValues.TAG, "Review added to List: $review")
                }
                callback(filteredReviews) //kind of like return, but async
            }
            .addOnFailureListener {error ->
                Log.d(ContentValues.TAG, "ERROR: $error")
            }
    }
}
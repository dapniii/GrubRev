package com.mobdeve.s13.group5.grubrev

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var usernameTv: TextView
    private lateinit var noReviewNoticeTv: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button
    private lateinit var backIv: ImageView
    private lateinit var joinedTv: TextView

    //Firebase
    private var firebaseDb = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Initialize
        this.usernameTv = findViewById(R.id.usernameTv)
        this.noReviewNoticeTv = findViewById(R.id.noReviewNoticeTv)
        this.recyclerView = findViewById(R.id.recyclerView)
        this.logoutBtn = findViewById(R.id.logoutBtn)
        this.backIv = findViewById(R.id.backIv)
        this.joinedTv = findViewById(R.id.joinedTv)

        //Intent
        val resIntent = this.intent
        val showLogout = resIntent.getBooleanExtra("SHOW_LOGOUT", true)
//        val currUser = resIntent.getStringExtra("USERNAME").toString()


        if (!showLogout) {
            this.logoutBtn.visibility = View.GONE
        } else {
            //OnCLick Logout, go back to Login Activity
            this.logoutBtn.setOnClickListener(View.OnClickListener {
                Firebase.auth.signOut()
                openMainActivity()
            })
        }

        //OnCLick Back ImageView, finish current Profile Activity
        this.backIv.setOnClickListener(View.OnClickListener {
            finish()
        })

        getUsername { currUser ->
            //Account Details
            this.usernameTv.text = currUser

            //Filter Data to Current User
//            val filteredReviews = filterToUsername(currUser)

            getReviews(currUser) { filteredReviews ->
                //TODO: Temp
                if (filteredReviews.isNotEmpty()) {
                    this.noReviewNoticeTv.visibility = View.GONE
                }

                //Account Reviews
                this.recyclerView.adapter = MyPostAdapter(filteredReviews as ArrayList<Review>)
                this.recyclerView.layoutManager = LinearLayoutManager(this)
            }

        }

    }

    //Opens MainActivity and finishes SignupActivity
    private fun openMainActivity() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    //Filter Review Data based on Username
    private fun filterToUsername(username: String): List<Review> {
        return reviewList.filter{it.user == username}
    }

    private fun getUsername(callback: (String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    val currUser = doc.getString("username").toString()

                    callback(currUser)
                } else {
                    Log.d(TAG, "How did you manage to do this?")
                }
            }
            .addOnFailureListener { error ->
                Log.d(TAG, "ERROR: $error")
            }
    }

    private fun getReviews(currUser: String, callback: (ArrayList<Review>) -> Unit) {
        val filteredReviews = arrayListOf<Review>()

        firebaseDb.collection("reviews")
            .whereEqualTo("user", currUser).get()
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
                    Log.d(TAG, "Review added to List: $review")
                }
                callback(filteredReviews) //kind of like return, but async
            }
            .addOnFailureListener {error ->
                Log.d(TAG, "ERROR: $error")
            }
    }
}
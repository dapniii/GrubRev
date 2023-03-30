package com.mobdeve.s13.group5.grubrev

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddReviewActivity : AppCompatActivity() {

    private lateinit var backToRestoIv: ImageView
    private lateinit var addRatingRb: RatingBar
    private lateinit var addCommentEt: EditText
    private lateinit var cancelBtn: Button
    private lateinit var sendBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)

        //Initialize
        this.backToRestoIv = findViewById(R.id.backToRestoIv)
        this.addRatingRb = findViewById(R.id.addRatingRb)
        this.addCommentEt = findViewById(R.id.addCommentEt)
        this.cancelBtn = findViewById(R.id.cancelBtn)
        this.sendBtn = findViewById(R.id.sendBtn)

        //Intents
        val resIntent = this.intent
        val currResto = resIntent.getStringExtra("RESTAURANT")

        //Cancelling Add Review
        this.backToRestoIv.setOnClickListener(View.OnClickListener {
            finish()
        })
        this.cancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

        //< * * * Adding Review * * *
        this.sendBtn.setOnClickListener(View.OnClickListener {
            //1. Get inputs
            val rating = addRatingRb.rating.toDouble()
            val comment = addCommentEt.text.toString()

            //2. Get current user's username
            getUsername { currUser ->
                //3. Once username taken, add new review to db
                addReview(currResto!!, currUser, comment, rating)
            }

        })
        // * * * Adding Review * * * />


    }

    private fun isError(){
        //TODO
    }

    private fun getUsername(callback: (String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        var currUser = ""

        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    currUser = doc.getString("username").toString()
                    callback(currUser)
                } else {
                    Log.d(TAG, "How did you manage to do this?")
                }
            }
            .addOnFailureListener { error ->
                Log.d(TAG, "ERROR: $error")
            }
    }

    private fun addReview(restaurant: String, username: String, comment: String, rating: Double) {
        val db = FirebaseFirestore.getInstance()

        val newReview = hashMapOf(
            "restaurant" to restaurant,
            "user" to username,
            "comment" to comment,
            "rating" to rating,
            "timestamp" to FieldValue.serverTimestamp()
        )
        db.collection("reviews").add(newReview)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Review added to Firestore with ID: ${documentReference.id}")
                Toast.makeText(this, "Review Successfully Added", Toast.LENGTH_SHORT).show()
                finish()
            }
            //Notify if marker wasn't added to db
            .addOnFailureListener { error ->
                Log.d(TAG, "Error adding review to Firestore: $error")
                Toast.makeText(this, "Failed to Add Review", Toast.LENGTH_SHORT).show()
            }
    }
}
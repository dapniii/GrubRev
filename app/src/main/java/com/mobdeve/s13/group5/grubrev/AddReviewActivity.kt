package com.mobdeve.s13.group5.grubrev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast

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


        //OnClick
        this.backToRestoIv.setOnClickListener(View.OnClickListener {
            finish()
        })

        this.cancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
        //TODO: Temp
//        this.sendBtn.setOnClickListener(View.OnClickListener {
//            finish()
//        })
        this.sendBtn.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                this@AddReviewActivity,
                "Rating is: "+addRatingRb.rating,
                Toast.LENGTH_SHORT
            ).show()
        })


    }
}
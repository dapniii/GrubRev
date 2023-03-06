package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var usernameTv: TextView
    private lateinit var dateTv: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Initialize
        this.usernameTv = findViewById(R.id.usernameTv)
        this.dateTv = findViewById(R.id.dateTv)
        this.recyclerView = findViewById(R.id.recyclerView)
        this.logoutBtn = findViewById(R.id.logoutBtn)

        //Account Details
        this.usernameTv.text = "@Me"
        this.dateTv.text = "Joined: 03/04/2023"

        //Account Reviews
        this.recyclerView.adapter = MyReviewAdapter(this.reviewList)
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        //OnCLick Logout, go back to Login Activity
        this.logoutBtn.setOnClickListener(View.OnClickListener {
            openMainActivity()
        })

    }

    //Opens MainActivity and finishes SignupActivity
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
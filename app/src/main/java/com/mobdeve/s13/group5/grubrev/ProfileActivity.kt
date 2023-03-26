package com.mobdeve.s13.group5.grubrev

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    private lateinit var usernameTv: TextView
    private lateinit var dateTv: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutBtn: Button
    private lateinit var backIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Initialize
        this.usernameTv = findViewById(R.id.usernameTv)
        this.dateTv = findViewById(R.id.dateTv)
        this.recyclerView = findViewById(R.id.recyclerView)
        this.logoutBtn = findViewById(R.id.logoutBtn)
        this.backIv = findViewById(R.id.backIv)

        //Intent
        val resIntent = this.intent
        val showLogout = resIntent.getBooleanExtra("SHOW_LOGOUT", true)
        val currUser = resIntent.getStringExtra("USERNAME").toString()

        //Account Details
        this.usernameTv.text = currUser
        //TODO: Temp
        this.dateTv.text = "Joined: 03/04/2023"

        //Filter Data to Current User
        val filteredReviews = filterToUsername(currUser)

        //Account Reviews
        this.recyclerView.adapter = MyPostAdapter(filteredReviews as ArrayList<Review>)
        this.recyclerView.layoutManager = LinearLayoutManager(this)


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
}
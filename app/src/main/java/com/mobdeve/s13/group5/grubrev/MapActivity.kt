package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MapActivity : AppCompatActivity() {
    private lateinit var profileIv: ImageView

    //TODO: Temp
    private lateinit var tempRestoBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //Initialize
        this.profileIv = findViewById(R.id.profileIv)
        this.tempRestoBtn = findViewById(R.id.tempRestoBtn)

        //OnClick Profile ImageView
        this.profileIv.setOnClickListener(View.OnClickListener {
            openProfileActivity()
        })

        //TODO: Temp Access to Restaurant Activity
        this.tempRestoBtn.setOnClickListener(View.OnClickListener {
            openRestaurantActivity()
        })
    }

    //TODO: Temp
    private fun openProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun openRestaurantActivity() {
        val intent = Intent(this, RestaurantActivity::class.java)
        startActivity(intent)
    }
}
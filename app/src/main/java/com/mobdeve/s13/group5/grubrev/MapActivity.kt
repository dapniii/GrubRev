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
    private lateinit var yellowPinIv: ImageView
    private lateinit var orangePinIv: ImageView
    private lateinit var redPinIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //Initialize
        this.profileIv = findViewById(R.id.profileIv)
        //TODO: Temp Initialize
        this.yellowPinIv = findViewById(R.id.yellowPinIv)
        this.orangePinIv = findViewById(R.id.orangePinIv)
        this.redPinIv = findViewById(R.id.redPinIv)



        //OnClick Profile ImageView
        this.profileIv.setOnClickListener(View.OnClickListener {
            openProfileActivity()
        })

        //TODO: Temp Access to Restaurant Activity
        this.yellowPinIv.setOnClickListener(View.OnClickListener {
            openRestaurantActivity()
        })
        this.orangePinIv.setOnClickListener(View.OnClickListener {
            openRestaurantActivity()
        })
        this.redPinIv.setOnClickListener(View.OnClickListener {
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
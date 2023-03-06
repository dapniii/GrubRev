package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
//  Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var loginBtn : Button
    private lateinit var signupTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Link Variables to xml Components
        this.usernameEt = findViewById(R.id.usernameEt)
        this.passwordEt = findViewById(R.id.passwordEt)
        this.loginBtn = findViewById(R.id.loginBtn)
        this.signupTv = findViewById(R.id.signupTv)

        //OnClick "Sign up now!" TextView
        this.signupTv.setOnClickListener(View.OnClickListener {
            openSignUpActivity()
        })

        //TODO: TEMP
        this.loginBtn.setOnClickListener((View.OnClickListener {
            openMapActivity()
        }))
    }

    //Opens SignupActivity and finishes MainActivity
    private fun openSignUpActivity() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    //TODO: TEMP
    private fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SignupActivity : AppCompatActivity() {
//  Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var confirmPasswordEt : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Link Variables to xml Components
        this.usernameEt = findViewById(R.id.usernameEt)
        this.passwordEt = findViewById(R.id.passwordEt)
        this.confirmPasswordEt = findViewById(R.id.confirmPasswordEt)
        this.signupBtn = findViewById(R.id.signupBtn)
        this.loginTv = findViewById(R.id.loginTv)

        //OnClick "Login here!" TextView
        this.loginTv.setOnClickListener(View.OnClickListener {
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
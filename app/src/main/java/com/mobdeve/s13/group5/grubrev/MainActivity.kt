package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    //  Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var loginBtn : Button
    private lateinit var signupTv : TextView

    //TODO: TEMP (only here for checking if user exists)
    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

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
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()

            //Check if all fields are filled up
            if (username.isNullOrBlank() ||
                password.isNullOrBlank())  {
                Toast.makeText(
                    this,
                    "ERROR: Please fill up all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Check if user is registered
            else if (!userExists(username)) {
                Toast.makeText(
                    this,
                    "ERROR: Invalid username",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //TODO: Check if password matches with username
//            else if (!userExists(username)) {
//                Toast.makeText(
//                    this,
//                    "ERROR: Invalid username",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            else {
                openMapActivity()
            }
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

    //Checks if user exists, returns boolean
    private fun userExists(username : String) : Boolean {
        return reviewList.any { it.user == username }
    }
}
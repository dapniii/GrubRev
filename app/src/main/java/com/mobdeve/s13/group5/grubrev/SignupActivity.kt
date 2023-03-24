package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignupActivity : AppCompatActivity() {
    //  Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var confirmPasswordEt : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginTv : TextView

    //TODO: TEMP (only here for checking if user exists)
    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

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

        //TODO: Implement Proper Auth
        this.signupBtn.setOnClickListener((View.OnClickListener {
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()
            val confirmPassword = confirmPasswordEt.text.toString()

            //Check if all fields are filled up
            if (username.isNullOrBlank() ||
                password.isNullOrBlank() ||
                confirmPassword.isNullOrBlank())  {
                Toast.makeText(
                    this,
                    "ERROR: Please fill up all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Check if user already exists
            else if (userExists(username)) {
                Toast.makeText(
                    this,
                    "ERROR: User $username already exists",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Check if password is more than or equal 8 characters
            else if (password.length < 8) {
                Toast.makeText(
                    this,
                    "ERROR: Password must be more than 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Check if Password matches with Confirm Password
            else if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "ERROR: Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Otherwise, approve sign in
            else {
                openMapActivity()
            }
        }))
    }

    //Opens MainActivity and finishes SignupActivity
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //TODO: TEMP
    private fun openMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
        finish()
    }

    //Checks if username already exists, returns boolean
    private fun userExists(username : String) : Boolean {
        return reviewList.any { it.user == username }
    }
}
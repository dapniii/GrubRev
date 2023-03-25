package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    //Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var confirmPasswordEt : EditText
    private lateinit var signupBtn : Button
    private lateinit var loginTv : TextView

    //Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    //TODO: TEMP (only here for checking if user exists)
    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()

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

        //TODO: Implement Proper Error Checking
        this.signupBtn.setOnClickListener((View.OnClickListener {
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()
            val confirmPassword = confirmPasswordEt.text.toString()

            val errorMessage = isError(username, password, confirmPassword)

            if (errorMessage != null) {
                Toast.makeText(
                    this,
                    "ERROR: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Otherwise, approve sign in
            else {
                firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener{
                    if (it.isSuccessful) {
                        openMapActivity()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
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

    private fun isError(username : String, password : String, confirmPassword : String) : String? {
        //Check if all fields are filled up
        if (username.isNullOrBlank() ||
            password.isNullOrBlank() ||
            confirmPassword.isNullOrBlank())  {
            return "Please fill up all fields"
        }
        //Check if user already exists
        else if (userExists(username)) {
            return "User $username already exists"
        }
        //Check if password is more than or equal 8 characters
        else if (password.length < 8) {
            return "Password must be more than 8 characters"
        }
        //Check if Password matches with Confirm Password
        else if (password != confirmPassword) {
            return "Passwords do not match"
        } else {
            return null
        }
    }
}
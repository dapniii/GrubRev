package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    //  Instantiate Variables
    private lateinit var usernameEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var loginBtn : Button
    private lateinit var signupTv : TextView

    //Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    //TODO: TEMP (only here for checking if user exists)
    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()

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

            val errorMessage = isError(username, password)
            //Check if all fields are filled up
            if (errorMessage != null) {
                Toast.makeText(
                    this,
                    "ERROR: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Check if user is registered
//            else if (!userExists(username)) {
//                Toast.makeText(
//                    this,
//                    "ERROR: Invalid username",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            //TODO: Check if password matches with username
//            else if (!userExists(username)) {
//                Toast.makeText(
//                    this,
//                    "ERROR: Invalid username",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            else {
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener{
                    if (it.isSuccessful) {
                        openMapActivity()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }))
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            openMapActivity()
        }
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

    private fun isError(username : String, password : String) : String? {
        //Check if all fields are filled up
        if (username.isNullOrBlank() ||
            password.isNullOrBlank()) {
            return "Please fill up all fields"
        } else {
            return null
        }
    }
}
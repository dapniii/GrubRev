package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    //  Instantiate Variables
    private lateinit var emailEt : EditText
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
        this.emailEt = findViewById(R.id.emailEt)
        this.passwordEt = findViewById(R.id.passwordEt)
        this.loginBtn = findViewById(R.id.loginBtn)
        this.signupTv = findViewById(R.id.signupTv)

        //OnClick "Sign up now!" TextView
        this.signupTv.setOnClickListener(View.OnClickListener {
            openSignUpActivity()
        })

        this.loginBtn.setOnClickListener((View.OnClickListener {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            val errorMessage = isError(email, password)
            //Check if all fields are filled up
            if (errorMessage != null) {
                Toast.makeText(
                    this,
                    "ERROR: $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful) {
                        openMapActivity()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
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
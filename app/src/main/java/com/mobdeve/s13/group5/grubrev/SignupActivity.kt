package com.mobdeve.s13.group5.grubrev

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    //Instantiate Variables
    private lateinit var emailEt: EditText
    private lateinit var usernameEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var confirmPasswordEt: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginTv: TextView

    //Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseDb = Firebase.firestore

    //TODO: TEMP (only here for checking if user exists)
    private val reviewList: ArrayList<Review> = DataHelper.initializeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()


        //Link Variables to xml Components
        this.emailEt = findViewById(R.id.emailEt)
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
            val email = emailEt.text.toString()
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()
            val confirmPassword = confirmPasswordEt.text.toString()

            isError(email, username, password, confirmPassword) { errorMessage ->
                //If there are errors, show corresponding error message
                if (errorMessage != null) {
                    Toast.makeText(
                        this,
                        "ERROR: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                //Otherwise, approve signup, notify user, and redirect back to Login Activity
                else {
                    createAccount(email, username, password)
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

    private fun isError( email: String,
                         username: String,
                         password: String,
                         confirmPassword: String,
                         callback: (String?) -> Unit ) {
        //Check if all fields are filled up
        if (email.isNullOrBlank() ||
            username.isNullOrBlank() ||
            password.isNullOrBlank() ||
            confirmPassword.isNullOrBlank()
        ) {
            callback("Please fill up all fields")
        }
        //Check if password is more than or equal 8 characters
        else if (password.length < 8) {
            callback("Password must be more than 8 characters")
        }
        //Check if Password matches with Confirm Password
        else if (password != confirmPassword) {
            callback("Passwords do not match")
        }
        //Check if Username already exists
        else {
            checkUserExists(username) { exists ->
                if (exists) {
                    callback("Username is already taken")
                } else {
                    callback(null)
                }
            }
        }
    }

    //Create New Account
    /*TODO:
       1. Check if username taken
       2. DONE - fix error messages, dont show raw firebase error messages
     */
    private fun createAccount(email: String, username: String, password: String) {
        //Create new account with email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            //If account successfully created
            if (it.isSuccessful) {

                //< * * * Storing User Data * * *
                //1. Get new user's details
                val currUserID = firebaseAuth.currentUser!!.uid
                val userMap = hashMapOf(
                    "userID" to currUserID,
                    "username" to username,
                    "timestamp" to FieldValue.serverTimestamp()
                )

                firebaseDb.collection("users").document(currUserID).set(userMap)
                    //Notify user if registered successfully and go back to Login page
                    .addOnSuccessListener {
                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                        openMainActivity()
                    }
                    //Notify user if register failed
                    .addOnFailureListener {
                        Toast.makeText(this, "ERROR: Failed to Register", Toast.LENGTH_SHORT).show()
                    }
                // * * * Storing User Data * * * />

                //After successfully creating new acc and storing other details to database
                //Redirect user to Login, if user session is retained after sign up
                //it will automatically redirect to MapActivity
            } else {
                //Show specific error
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserExists(username: String, callback: (Boolean) -> Unit) {
        firebaseDb.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener {document ->
                callback(!document.isEmpty)
            }
            .addOnFailureListener {error ->
                Log.d(ContentValues.TAG, "ERROR: $error")
            }
    }
}
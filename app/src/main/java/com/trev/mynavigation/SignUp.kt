package com.trev.mynavigation

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.measureTimeMillis

class SignUp : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //initialize your analytics variable
        analytics = Firebase.analytics
        //initialize your authentication variable
        auth = Firebase.auth

        supportActionBar?.hide()

    }

    fun signupUser(view: View){
        val email = findViewById<EditText>(R.id.editEmail)
        val userEmail = email.text.toString()
        val password = findViewById<EditText>(R.id.editPassword)
        val userPassword = password.text.toString()
        val password_confirm = findViewById<EditText>(R.id.confirmPassword)
        val passwordConf = password_confirm.text.toString()

        if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(passwordConf)){
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        }else {

            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        launchMain()
                    }
                }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun authenticate(view: View){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun reload(){
        startActivity(Intent(this, SignUp::class.java))
        finish()
    }

    private fun launchMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
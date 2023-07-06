package com.trev.mynavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //initialize your analytics variable
        analytics = Firebase.analytics
        //initialize your authentication variable
        auth = Firebase.auth

        supportActionBar?.hide()

    }
    //create a view here, view is the base class for widgets
    fun loginUser(view: View){
        val email = findViewById<EditText>(R.id.editEmail)
        val userEmail = email.text.toString()
        val password = findViewById<EditText>(R.id.editPassword)
        val userPassword = password.text.toString()

        //TextUtils is a string splitter
        if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
        }else {

            auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun createAccount(view: View){
        startActivity(Intent(this, SignUp::class.java))
        finish()
    }

}
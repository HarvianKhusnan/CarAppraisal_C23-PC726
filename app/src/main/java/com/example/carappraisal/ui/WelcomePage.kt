package com.example.carappraisal.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.carappraisal.R
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.checkerframework.checker.units.qual.Length

class WelcomePage : AppCompatActivity() {
    private lateinit var btnEmail : Button
    private lateinit var btnGoogle : ImageView

    private lateinit var auth : FirebaseAuth
    private lateinit var googleClient : GoogleSignInClient
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        btnEmail = findViewById(R.id.button_email)
        btnGoogle = findViewById(R.id.btn_google)

        auth = FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            navigateToMain()
            return
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(this, gso)

        btnGoogle.setOnClickListener {
            val signInIntent = googleClient.signInIntent
            startActivityForResult(signInIntent, REQUEST_CODE)
        }
        btnEmail.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthGoogle(account.idToken!!)
            }catch (e : ApiException){
                e.printStackTrace()
            }
        }
    }

    fun firebaseAuthGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                saveLoginStatus(true)
            }
            .addOnFailureListener { error->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


    companion object{
        private const val REQUEST_CODE = 200;
    }
}
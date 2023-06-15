package com.example.carappraisal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carappraisal.R
import com.example.carappraisal.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnGoogleLgn.setOnClickListener {
            val email = binding.emailTextLogin.text.toString()
            val pass = binding.passwordTextLogin.text.toString()
            val name = binding.nameTextRegister.text.toString()

            registerFirebase(email,pass,name)
        }
    }

    private fun registerFirebase(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid

                    val database = FirebaseDatabase.getInstance()
                    val usersRef = database.reference.child("users")
                    val userRef = usersRef.child(userId!!)
                    userRef.child("name").setValue(name)
                        .addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                userRef.child("email").setValue(email)
                                    .addOnCompleteListener { emailTask ->
                                        if (emailTask.isSuccessful) {
                                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, LoginActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            Toast.makeText(this, "Failed to save email", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "Failed to save name", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
package com.example.carappraisal.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carappraisal.R
import com.example.carappraisal.databinding.FragmentProfileBinding
import com.example.carappraisal.ui.LoginActivity
import com.example.carappraisal.ui.WelcomePage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setHasOptionsMenu(true)

        val emailTextView: TextView = binding.emailProfile
        val nameTextView: TextView = binding.textView7

        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val usersRef = database.reference.child("users")
            val userRef = usersRef.child(userId)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = snapshot.child("email").getValue(String::class.java)
                    val name = snapshot.child("name").getValue(String::class.java)

                    emailTextView.text = email
                    nameTextView.text = name
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ProfileFragment", "Failed to read user data: ${error.message}")
                    Toast.makeText(requireContext(), "Failed to read user data", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
        }
        binding.logoutBtn.setOnClickListener {
            logout()
            logoutGoogle()
            val intent = Intent(requireContext(), WelcomePage::class.java)
            startActivity(intent)

        }

        return root
    }


    private fun logout() {
        Firebase.auth.signOut()
        val intent = Intent(requireContext(), WelcomePage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun logoutGoogle() {
        auth.signOut()
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(requireContext(), WelcomePage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package com.example.eatwise.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.activity.AboutmeActivity
import com.example.eatwise.activity.EditActivity
import com.example.eatwise.activity.FaqActivity
import com.example.eatwise.activity.SigninActivity
import com.example.eatwise.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Ambil data username dan email dari SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User Default")
        val email = sharedPreferences.getString("email", "example@email.com")

        // Set username dan email ke TextView
        binding.textUsername.text = userName
        binding.textEmail.text = email

        // Logout
        binding.logout.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                val intent = Intent(requireContext(), SigninActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        // Setup navigasi menggunakan CardView
        val cardViewMap = mapOf(
            R.id.edit to EditActivity::class.java,
            R.id.aboutme to AboutmeActivity::class.java,
            R.id.faq to FaqActivity::class.java
        )

        for ((cardId, targetActivity) in cardViewMap) {
            view.findViewById<CardView>(cardId).setOnClickListener {
                val intent = Intent(requireContext(), targetActivity)
                startActivity(intent)
            }
        }
    }
}

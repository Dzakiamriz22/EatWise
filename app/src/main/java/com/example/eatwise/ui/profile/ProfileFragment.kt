package com.example.eatwise.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.activity.AboutmeActivity
import com.example.eatwise.activity.EditActivity
import com.example.eatwise.activity.FaqActivity
import com.example.eatwise.R
import com.example.eatwise.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
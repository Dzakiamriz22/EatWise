package com.example.eatwise.ui.tips

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatwise.activity.DetailTipsActivity
import com.example.eatwise.adapter.TipsAdapter
import com.example.eatwise.databinding.FragmentTipsBinding

class TipsFragment : Fragment() {

    private var _binding: FragmentTipsBinding? = null
    private val binding get() = _binding!!
    private val tipsViewModel: TipsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TipsAdapter { tip ->
            val intent = Intent(requireContext(), DetailTipsActivity::class.java)
            intent.putExtra("TIP_CONTENT", tip.content)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Add progress bar and error text view to your fragment_tips.xml
        tipsViewModel.tipsList.observe(viewLifecycleOwner) { tips ->
            binding.progressBar.visibility = View.GONE
            if (tips.isNullOrEmpty()) {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = "No articles found or error occurred"
                Log.e("TipsFragment", "Article list is empty or null")
            } else {
                binding.errorTextView.visibility = View.GONE
                adapter.submitList(tips)
                Log.d("TipsFragment", "Articles loaded: ${tips.size}")
            }
        }

        tipsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
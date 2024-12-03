package com.example.eatwise.ui.tips

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatwise.adapter.TipsAdapter
import com.example.eatwise.databinding.FragmentTipsBinding
import com.example.eatwise.activity.DetailTipsActivity

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
            // When a tip is clicked, start DetailTipsActivity
            val intent = Intent(requireContext(), DetailTipsActivity::class.java)
            intent.putExtra("TIP_CONTENT", tip) // Pass the clicked tip's content to the DetailTipsActivity
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        tipsViewModel.tipsList.observe(viewLifecycleOwner) { tips ->
            adapter.submitList(tips)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

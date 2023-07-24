package com.fortuneOX.slots.ui.spin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fortuneOX.slots.R
import com.fortuneOX.slots.databinding.FragmentSpinBinding

class SpinFragment : Fragment() {

    private lateinit var binding: FragmentSpinBinding

    private val spinItems = listOf(
        R.drawable.orange,
        R.drawable.orange,
        R.drawable.orange,
        R.drawable.orange,
        R.drawable.orange,
        R.drawable.orange,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinAdapter()
    }

    private fun setupSpinAdapter() {
        val spinAdapter = SpinAdapter()
        binding.firstList.layoutManager = LinearLayoutManager(requireContext())
        binding.secondList.layoutManager = LinearLayoutManager(requireContext())
        binding.thirdList.layoutManager = LinearLayoutManager(requireContext())
        binding.fourthList.layoutManager = LinearLayoutManager(requireContext())
        binding.firstList.adapter = spinAdapter
        binding.secondList.adapter = spinAdapter
        binding.thirdList.adapter = spinAdapter
        binding.fourthList.adapter = spinAdapter
       spinAdapter.submitList(spinItems)
    }

}
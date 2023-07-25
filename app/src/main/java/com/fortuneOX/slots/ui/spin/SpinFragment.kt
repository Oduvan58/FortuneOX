package com.fortuneOX.slots.ui.spin

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortuneOX.slots.R
import com.fortuneOX.slots.databinding.FragmentSpinBinding
import kotlin.random.Random

class SpinFragment : Fragment() {

    private lateinit var binding: FragmentSpinBinding

    private var spinAdapter: SpinAdapter? = null
    private var spinAdapter1: SpinAdapter? = null
    private var spinAdapter2: SpinAdapter? = null
    private var spinAdapter3: SpinAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinAdapter()

        binding.spinButton.setOnClickListener {

            spinAdapter?.itemCount?.let { it1 -> binding.firstList.smoothScrollToPosition(it1) }
            spinAdapter1?.itemCount?.let { it1 -> binding.secondList.smoothScrollToPosition(it1) }
            spinAdapter2?.itemCount?.let { it1 -> binding.thirdList.smoothScrollToPosition(it1) }
            spinAdapter3?.itemCount?.let { it1 -> binding.fourthList.smoothScrollToPosition(it1) }

            stopSpinningList(spinAdapter, binding.firstList, 3000)
            stopSpinningList(spinAdapter1, binding.secondList, 4000)
            stopSpinningList(spinAdapter2, binding.thirdList, 5000)
            stopSpinningList(spinAdapter3, binding.fourthList, 6000)
        }

        binding.stopButton.setOnClickListener {
            stopSpinningList(spinAdapter, binding.firstList, 3000)
            stopSpinningList(spinAdapter1, binding.secondList, 4000)
            stopSpinningList(spinAdapter2, binding.thirdList, 5000)
            stopSpinningList(spinAdapter3, binding.fourthList, 6000)
        }
    }

    private fun stopSpinningList(adapter: SpinAdapter?, recyclerView: RecyclerView, time: Long) {
        Handler().postDelayed({
            val random: Random = Random
            val coinIndex = adapter?.currentList?.indexOfFirst { it == R.drawable.orange }
            coinIndex?.let {
                recyclerView.scrollToPosition(it)
            }

            recyclerView.stopScroll()
        }, time)

    }

    private fun stopSpinningSecond() {
        Handler().postDelayed({
            val coinIndex = spinAdapter1?.currentList?.indexOfFirst { it == R.drawable.orange }
            coinIndex?.let {
                binding.secondList.scrollToPosition(it)
            }

            binding.secondList.stopScroll()
        }, 3000)

    }


    private fun setupSpinAdapter() {
        spinAdapter = SpinAdapter()
        spinAdapter1 = SpinAdapter()
        spinAdapter2 = SpinAdapter()
        spinAdapter3 = SpinAdapter()
        binding.firstList.layoutManager = LinearLayoutManager(requireContext())
        binding.secondList.layoutManager = LinearLayoutManager(requireContext())
        binding.thirdList.layoutManager = LinearLayoutManager(requireContext())
        binding.fourthList.layoutManager = LinearLayoutManager(requireContext())
        binding.firstList.adapter = spinAdapter
        binding.secondList.adapter = spinAdapter1
        binding.thirdList.adapter = spinAdapter2
        binding.fourthList.adapter = spinAdapter3

        spinAdapter?.submitList(createInfiniteList(createRandomList()))
        spinAdapter1?.submitList(createInfiniteList(createRandomList()))
        spinAdapter2?.submitList(createInfiniteList(createRandomList()))
        spinAdapter3?.submitList(createInfiniteList(createRandomList()))
    }

    private fun createInfiniteList(list: List<Int>): List<Int> {
        val infiniteList = mutableListOf<Int>()
        val repeatCount = 1000

        repeat(repeatCount) {
            infiniteList.addAll(listOf(createRandomList().random()))
        }

        return infiniteList
    }

    private fun createRandomList() = listOf(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.orange,
    ).shuffled()


    companion object {
        fun newInstance() = SpinFragment()
    }

}
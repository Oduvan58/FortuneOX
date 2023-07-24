package com.fortuneOX.slots.ui.spin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fortuneOX.slots.R
import com.fortuneOX.slots.databinding.SpinListItemBinding

class SpinAdapter: ListAdapter<Int, SpinAdapter.SpinViewHolder>(SpinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = SpinListItemBinding.inflate(inflater, parent, false)
        return SpinViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SpinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SpinViewHolder(private val binding: SpinListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Int){
            binding.spinItemImageView.setImageResource(item)
        }
    }


}

class SpinDiffCallback : DiffUtil.ItemCallback<Int>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int) =
        oldItem == newItem

}

package com.udacity.asteroidradar.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ListItemBinding

class AsteroidAdapter( val itemClickListener: ItemClickListener) :
    ListAdapter<Asteroid, RecyclerView.ViewHolder>(AsteroidDiffCallback) {

    // this is a view holder  for RecyclerView   for list item of asteroid i used data binding

    class ViewHolder private constructor(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Asteroid, itemClickListener: ItemClickListener) {
            // bind item with xml
            binding.asteroid = item
            binding.itemClickListener = itemClickListener
            binding.executePendingBindings()
        }

        companion object {
            // create a view for the item
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // onBindViewHolder where we redraw the item
        val asteroidItem = getItem(position)
        holder as ViewHolder
        holder.bind(asteroidItem, itemClickListener)
    }

    // we use AsteroidDiffCallback for more performance and less refresh
    object AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }


}
interface ItemClickListener{
    fun onClick(asteroid: Asteroid)
}
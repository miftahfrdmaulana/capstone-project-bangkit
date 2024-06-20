package com.dicoding.gardenguard.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gardenguard.data.model.Vegetable
import com.dicoding.gardenguard.databinding.ItemVegetableBinding

class VegetableAdapter(
    private val context: Context,
    private var vegetableList: List<Vegetable>,
    private val onItemClick: ((Vegetable) -> Unit)?
) :
    RecyclerView.Adapter<VegetableAdapter.VegetableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VegetableViewHolder {
        val binding = ItemVegetableBinding.inflate(LayoutInflater.from(context), parent, false)
        return VegetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VegetableViewHolder, position: Int) {
        val vegetable = vegetableList[position]

        Glide.with(context)
            .load(vegetable.imageUrl)
            .into(holder.binding.ivVegetableImage)
        holder.binding.tvVegetableName.text = vegetable.name
        holder.binding.tvVegetableScientificName.text = vegetable.scientificName
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(vegetable)
        }
    }

    override fun getItemCount(): Int {
        return vegetableList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newVegetableList: List<Vegetable>) {
        vegetableList = newVegetableList
        notifyDataSetChanged()
    }

    inner class VegetableViewHolder(val binding: ItemVegetableBinding) : RecyclerView.ViewHolder(binding.root)
}
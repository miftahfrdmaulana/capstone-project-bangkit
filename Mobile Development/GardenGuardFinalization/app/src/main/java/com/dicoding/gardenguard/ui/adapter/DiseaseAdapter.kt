package com.dicoding.gardenguard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gardenguard.data.model.Disease
import com.dicoding.gardenguard.databinding.ItemDiseaseBinding

class DiseaseAdapter (
    private val context: Context,
    private var diseaseList: List<Disease>,
    private val onItemClick: ((Disease) -> Unit)?
) :
    RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val binding = ItemDiseaseBinding.inflate(LayoutInflater.from(context), parent, false)
        return DiseaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseaseList[position]
        Glide.with(context)
            .load(disease.imageUrl)
            .into(holder.binding.ivDiseaseImage)
        holder.binding.tvDiseaseName.text = disease.name
        holder.binding.tvDiseaseScientificName.text = disease.scientificName
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(disease)
        }
    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }

    inner class DiseaseViewHolder(val binding: ItemDiseaseBinding) : RecyclerView.ViewHolder(binding.root)
}
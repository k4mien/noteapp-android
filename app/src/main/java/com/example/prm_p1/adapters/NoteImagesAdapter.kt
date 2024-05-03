package com.example.prm_p1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_p1.R
import com.example.prm_p1.databinding.NoteImageBinding

class NoteImageViewHolder(val binding: NoteImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(resId: Int, isSelected: Boolean){
        binding.image.setImageResource(resId)
        binding.selectedFrame.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}

class NoteImagesAdapter : RecyclerView.Adapter<NoteImageViewHolder>() {

    private val images = listOf(R.drawable.basic, R.drawable.food, R.drawable.movies, R.drawable.school, R.drawable.money)
    private var selectedPosition: Int = 0
    val selectedIdRes: Int
        get() = images[selectedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteImageViewHolder {
        val binding = NoteImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteImageViewHolder(binding).also { vh ->
            binding.root.setOnClickListener {
                setSelected(vh.layoutPosition)
            }
        }
    }

    private fun setSelected(layoutPosition: Int) {
        notifyItemChanged(selectedPosition)
        selectedPosition = layoutPosition
        notifyItemChanged(selectedPosition)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: NoteImageViewHolder, position: Int) {
        holder.bind(images[position], position == selectedPosition)
    }

    fun setSelection(icon: Int?) {
        val index = images.indexOfFirst { it == icon }
        if (index == -1) return
        setSelected(index)
    }
}
package com.example.prm_p1.adapters

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_p1.NoteCallback
import com.example.prm_p1.databinding.ListItemBinding
import com.example.prm_p1.model.Note

class NoteViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note : Note){
        binding.title.text = note.title
        binding.desc.text = note.description
        binding.date.text = note.date
        binding.image.setImageResource(note.resId)
    }
}

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private val data = mutableListOf<Note>()
    private val handler: android.os.Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    var onItemClick: (Long) -> Unit = { }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder(binding).also { vh ->
            binding.root.setOnClickListener {
                onItemClick(data[vh.layoutPosition].id)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun replace(newData: List<Note>){
        val callback = NoteCallback(data, newData)
        data.clear()
        data.addAll(newData)
        val result = DiffUtil.calculateDiff(callback)
        handler.post {
            result.dispatchUpdatesTo(this)
        }
    }

    fun removeItem(layoutPosition: Int): Note {
        val note = data.removeAt(layoutPosition)
        notifyItemRemoved(layoutPosition)
        return note
    }
}
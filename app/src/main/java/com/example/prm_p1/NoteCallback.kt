package com.example.prm_p1

import androidx.recyclerview.widget.DiffUtil
import com.example.prm_p1.model.Note

class NoteCallback(val notSorted: List<Note>, val sorted: List<Note>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = notSorted.size

    override fun getNewListSize(): Int = sorted.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] === sorted[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] == sorted[newItemPosition]

}
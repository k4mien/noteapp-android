package com.example.prm_p1.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm_p1.Navigable
import com.example.prm_p1.adapters.NotesAdapter
import com.example.prm_p1.adapters.SwipeToRemove
import com.example.prm_p1.data.NoteDatabase
import com.example.prm_p1.databinding.FragmentListBinding
import com.example.prm_p1.model.Note
import kotlin.concurrent.thread

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var adapter: NotesAdapter? = null
    private lateinit var db: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabase.open(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter().apply {
            onItemClick = {
                (activity as? Navigable)?.navigate(Navigable.Destination.Edit, it)
            }
        }

        loadData()

        binding.list.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(SwipeToRemove{
                adapter?.removeItem(it)?.let {
                    thread {
                        db.notes.remove(it.id)
                        binding.btnCounter.text = "Notatki: " + adapter?.itemCount
                    }
                }
            }).attachToRecyclerView(it)
        }

        binding.btnAddNote.setOnClickListener{
            (activity as? Navigable)?.navigate(Navigable.Destination.Edit)
        }

    }

    @SuppressLint("DiscouragedApi", "SetTextI18n")
    fun loadData() = thread {
        val notes = db.notes.getAll().map { entity ->
            Note(
                entity.id,
                entity.title,
                entity.description,
                entity.date,
                resources.getIdentifier(entity.icon, "drawable", requireContext().packageName)
            )
        }
        binding.btnCounter.text = "Notatki: " + adapter?.itemCount
        adapter?.replace(notes)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
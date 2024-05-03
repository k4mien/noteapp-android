package com.example.prm_p1.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prm_p1.adapters.NoteImagesAdapter
import com.example.prm_p1.data.NoteDatabase
import com.example.prm_p1.data.model.NoteEntity
import com.example.prm_p1.databinding.FragmentEditBinding
import java.time.LocalDate
import kotlin.concurrent.thread

const val NOTE_ID = "edit_id"

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var adapter: NoteImagesAdapter
    private lateinit var db : NoteDatabase
    private var note: NoteEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabase.open(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteImagesAdapter()

        val id = requireArguments().getLong(NOTE_ID, -1L)
        if (id != -1L) {
            thread {
                note = db.notes.getById(id)
                requireActivity().runOnUiThread {
                    binding.title.setText(note?.title ?: "")
                    binding.description.setText(note?.description ?: "")
                    adapter.setSelection(note?.icon?.let {
                        resources.getIdentifier(it, "drawable", requireContext().packageName)
                    })
                }
            }
        }

        binding.images.apply {
            adapter = this@EditFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.btnAccept.setOnClickListener {
            val note = note?.copy(
                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                date = LocalDate.now().toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes)
            ) ?: NoteEntity(
                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                date = LocalDate.now().toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes)
            )
            this.note = note

            thread {
                db.notes.addNote(note)
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
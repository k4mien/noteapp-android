package com.example.prm_p1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.prm_p1.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY title ASC;")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM note WHERE id = :id;")
    fun getById(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    fun remove(id: Long)
}
package com.example.prm_p1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prm_p1.data.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){
    abstract val notes: NoteDao

    companion object {
        fun open(context: Context): NoteDatabase = Room.databaseBuilder(
            context, NoteDatabase::class.java, "notes.db"
        ).build()
    }
}
package com.example.prm_p1.model

import androidx.annotation.DrawableRes

data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val date: String,
    @DrawableRes
    val resId: Int
)

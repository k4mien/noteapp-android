package com.example.prm_p1

interface Navigable {
    enum class Destination {
        List, Add, Edit
    }
    fun navigate(to: Destination, id: Long? = null)

}
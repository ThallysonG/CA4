package com.example.ca4_mobileapp

data class Song(
    val title: String,
    val artist: String,
    val year: String,
    val description: String,
    val imageRes: Int? = null
)
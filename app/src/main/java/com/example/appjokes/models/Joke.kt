package com.example.appjokes.models

data class Joke(
    val categories: List<Any>,
    val id: Int,
    val joke: String
)
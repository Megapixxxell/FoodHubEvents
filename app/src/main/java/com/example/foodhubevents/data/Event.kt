package com.example.foodhubevents.data

data class Event(
    val id: Long,
    val type: String,
    val email: String,
    val name: String,
    val lastname: String,
    val starts: String,
    val ends: String,
    val phone: String
)

data class PostEvent(
    val type: String,
    val email: String,
    val name: String,
    val lastname: String,
    val starts: String,
    val ends: String,
    val phone: String
)


data class EventResponse(
    val results: List<Event>
)


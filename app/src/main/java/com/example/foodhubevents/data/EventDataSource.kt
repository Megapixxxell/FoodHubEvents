package com.example.foodhubevents.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

private const val EVENTS_KEY = "EVENTS_KEY"

class EventDataSource(private val gson: Gson, private val prefs: SharedPreferences) {

    var events: List<Event>
        get() {
            val json = serEvents

            if (json.isEmpty()) return emptyList()

            return gson.fromJson(json, Array<Event>::class.java).toList()
        }
        set(value) {
            val json = gson.toJson(value)
            prefs.edit(commit = true) {
                putString(EVENTS_KEY, json)
            }
        }

    private val serEvents: String
        get() = prefs.getString(EVENTS_KEY, "")!!
}
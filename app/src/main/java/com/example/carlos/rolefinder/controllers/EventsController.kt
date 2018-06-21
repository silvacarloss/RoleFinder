package com.example.carlos.rolefinder.controllers

import android.content.Context
import com.example.carlos.rolefinder.database.DatabaseHelper
import com.example.carlos.rolefinder.models.Event

class EventsController {

    fun insert(context : Context, event : Event) : Boolean {
        val databaseHelper = DatabaseHelper(context)
        return databaseHelper.insertEvent(event)
    }
}
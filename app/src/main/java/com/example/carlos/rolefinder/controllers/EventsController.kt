package com.example.carlos.rolefinder.controllers

import android.content.Context
import com.example.carlos.rolefinder.database.DatabaseHelper
import com.example.carlos.rolefinder.models.Event
import com.example.carlos.rolefinder.models.EventTag

class EventsController {

    fun insert(context : Context, event : Event) : Long {
        val databaseHelper = DatabaseHelper(context)
        return databaseHelper.insertEvent(event)
    }

    fun insertEventTag(context : Context, eventId : Int, tagId : Int?){
        val databaseHelper = DatabaseHelper(context)
        val eventTag = EventTag(0, tagId, eventId)
        databaseHelper.insertEventTag(eventTag)
    }
}
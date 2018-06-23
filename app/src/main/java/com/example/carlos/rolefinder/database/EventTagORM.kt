package com.example.carlos.rolefinder.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.EventTag
import com.example.carlos.rolefinder.utils.Constants

class EventTagORM {
    companion object {

        private var instance : EventTagORM? = null

        @JvmStatic
        internal fun getInstance() : EventTagORM? {
            if (instance == null)
                return EventTagORM()

            return instance
        }
    }

    lateinit var database : SQLiteDatabase

    fun insert(helper : SQLiteOpenHelper, eventTag : EventTag) : Long {
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.EventTag.COLUMN_TAG, eventTag.tag_id)
        valuesToInsert.put(Constants.EventTag.COLUMN_EVENT, eventTag.event_id)
        var id : Long = -1
        try{
            id = database.insert(Constants.EventTag.EVENTS_TAG_TABLE_NAME, null, valuesToInsert)
            database.close()
        }catch (ex : Exception){
            database.close()
        }
        return id
    }

    fun select(helper : SQLiteOpenHelper) : ArrayList<EventTag>? {
        database = helper.readableDatabase
        var listTags : ArrayList<EventTag>? = null
        var eventTag : EventTag? = null
        val cursor = database.query(Constants.EventTag.EVENTS_TAG_TABLE_NAME,
                arrayOf(Constants.EventTag.COLUMN_TAG,
                        Constants.EventTag.COLUMN_EVENT),
                null,
                null,
                null,
                null,
                null)

        if(cursor.moveToFirst()){
            do {
                eventTag!!._id = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_ID)).toInt()
                eventTag.tag_id = cursor.getString(cursor.getColumnIndex(Constants.EventTag.COLUMN_TAG)).toInt()
                eventTag.event_id = cursor.getString(cursor.getColumnIndex(Constants.EventTag.COLUMN_EVENT)).toInt()
                listTags!!.add(eventTag)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listTags
    }

    fun select(helper : SQLiteOpenHelper, userTagId : Int) : ArrayList<EventTag>? {
        database = helper.readableDatabase
        println(userTagId)
        val listTags = ArrayList<EventTag>()
        val query = "SELECT * FROM ${Constants.EventTag.EVENTS_TAG_TABLE_NAME}" +
                " WHERE ${Constants.EventTag.COLUMN_TAG}=?"
        val cursor = database.rawQuery(query, arrayOf(userTagId.toString()))
        if(cursor.moveToFirst()){
            do {
                val eventTag = EventTag(0,0,0)
                eventTag._id = cursor.getInt(cursor.getColumnIndex(Constants.EventTag.COLUMN_ID))
                eventTag.tag_id = cursor.getInt(cursor.getColumnIndex(Constants.EventTag.COLUMN_TAG))
                eventTag.event_id = cursor.getInt(cursor.getColumnIndex(Constants.EventTag.COLUMN_EVENT))
                listTags.add(eventTag)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listTags
    }

    fun delete(helper : SQLiteOpenHelper, eventId : Int){
        database = helper.writableDatabase
        database.delete(Constants.EventTag.EVENTS_TAG_TABLE_NAME,
                "${Constants.EventTag.COLUMN_EVENT}=?",
                arrayOf(eventId.toString()))

        database.close()
    }
}
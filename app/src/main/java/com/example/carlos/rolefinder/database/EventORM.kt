package com.example.carlos.rolefinder.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.Event
import com.example.carlos.rolefinder.utils.Constants

class EventORM {

    companion object {

        private var instance : EventORM? = null

        @JvmStatic
        internal fun getInstance() : EventORM? {
            if (instance == null)
                return EventORM()

            return instance
        }
    }

    lateinit var database : SQLiteDatabase

    fun insert(helper : SQLiteOpenHelper, event : Event) : Long {
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.Events.COLUMN_TITLE, event.title)
        valuesToInsert.put(Constants.Events.COLUMN_DESCRIPTION, event.description)
        valuesToInsert.put(Constants.Events.COLUMN_ADDRESS, event.address)
        valuesToInsert.put(Constants.Events.COLUMN_DATA, event.date)
        valuesToInsert.put(Constants.Events.COLUMN_PRICE, event.price)
        valuesToInsert.put(Constants.Events.COLUMN_ID_USER_CREATOR, event.idUserCreator)
        var id : Long = -1
        try{
            id = database.insert(Constants.Events.EVENTS_TABLE_NAME, null, valuesToInsert)
            database.close()
        }catch (ex : Exception){
            database.close()
        }
        return id
    }

    fun update(helper : SQLiteOpenHelper, event : Event){
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.Events.COLUMN_TITLE, event.title)
        valuesToInsert.put(Constants.Events.COLUMN_DESCRIPTION, event.description)
        valuesToInsert.put(Constants.Events.COLUMN_ADDRESS, event.address)
        valuesToInsert.put(Constants.Events.COLUMN_DATA, event.date)
        valuesToInsert.put(Constants.Events.COLUMN_PRICE, event.price)
        valuesToInsert.put(Constants.Events.COLUMN_ID_USER_CREATOR, event.idUserCreator)
        database.update(Constants.Events.EVENTS_TABLE_NAME,
                valuesToInsert, "_id=?",
                arrayOf(event._id.toString()))

    }

    fun select(helper : SQLiteOpenHelper) : ArrayList<Event>? {
        database = helper.readableDatabase
        var listEvents : ArrayList<Event>? = null
        var event : Event? = null
        val cursor = database.query(Constants.Events.EVENTS_TABLE_NAME,
                arrayOf(Constants.Events.COLUMN_ID,
                        Constants.Events.COLUMN_DESCRIPTION,
                        Constants.Events.COLUMN_ADDRESS,
                        Constants.Events.COLUMN_DATA,
                        Constants.Events.COLUMN_PRICE,
                        Constants.Events.COLUMN_ID_USER_CREATOR),
                null,
                null,
                null,
                null,
                null)

        if(cursor.moveToFirst()){
            do {
                event!!._id = cursor.getString(cursor.getColumnIndex(Constants.Events.COLUMN_ID)).toInt()
                event.description = cursor.getString(cursor.getColumnIndex(Constants.Events.COLUMN_DESCRIPTION))
                event.address = cursor.getString(cursor.getColumnIndex(Constants.Events.COLUMN_ADDRESS))
                event.date = cursor.getString(cursor.getColumnIndex(Constants.Events.COLUMN_DATA))
                event.price = cursor.getFloat(cursor.getColumnIndex(Constants.Events.COLUMN_PRICE))
                event.idUserCreator = cursor.getInt(cursor.getColumnIndex(Constants.Events.COLUMN_ID_USER_CREATOR))
                listEvents!!.add(event)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listEvents
    }

    fun delete(helper : SQLiteOpenHelper, event : Event){
        database = helper.writableDatabase
        database.delete(Constants.Events.EVENTS_TABLE_NAME,
                Constants.Events.COLUMN_ID,
                arrayOf(event._id.toString()))

        database.close()
    }

}
package com.example.carlos.rolefinder.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.*
import com.example.carlos.rolefinder.utils.Constants

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "rolefinder.db", null, 9) {

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${Constants.Tags.TAGS_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${Constants.Users.USERS_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${Constants.Events.EVENTS_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${Constants.EventTag.EVENTS_TAG_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${Constants.UserTag.USER_TAGS_TABLE_NAME}")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(Constants.Tags.CREATE_TABLE_TAGS)
        db.execSQL(Constants.Users.CREATE_TABLE_USERS)
        db.execSQL(Constants.Events.CREATE_TABLE_EVENTS)
        db.execSQL(Constants.EventTag.CREATE_TABLE_EVENTS_TAG)
        db.execSQL(Constants.UserTag.CREATE_TABLE_USER_TAGS)
        for (tag in Constants.Tags.listTags){
            val query = "INSERT INTO ${Constants.Tags.TAGS_TABLE_NAME} " +
                    "(${Constants.Tags.COLUMN_NAME}) VALUES ('${tag}');"
            println(query)
            db.execSQL(query)
        }
    }

    fun insertEvent(event : Event) : Long {
        return EventORM.getInstance()!!.insert(this, event)
    }

    fun deleteEvent(id : Int){
        EventORM.getInstance()!!.delete(this, id)
    }

    fun updateEvent(event : Event){
        EventORM.getInstance()!!.update(this, event)
    }

    fun selectAllEvents(){
        EventORM.getInstance()!!.select(this)
    }

    fun insertUser(user : User) : Long {
        return UserORM.getInstance()!!.insert(this, user)
    }

    fun deleteUser(user : User){
        UserORM.getInstance()!!.delete(this, user)
    }

    fun updateUser(user : User){
        UserORM.getInstance()!!.update(this, user)
    }

    fun selectAllUsers(){
        UserORM.getInstance()!!.select(this)
    }

    fun login(email: String) : User? {
        return UserORM.getInstance()!!.selectUser(this, email)
    }

    fun selectAllTags() : ArrayList<Tags>? {
        return TagORM.getInstance()!!.select(this)
    }

    fun insertUserTag(userTag : UserTag) : Long {
        return UserTagORM.getInstance()!!.insert(this, userTag)
    }

    fun insertEventTag(eventTag: EventTag) : Long {
        return EventTagORM.getInstance()!!.insert(this, eventTag)
    }

    fun selectMyEvents(userId: Int) : ArrayList<Event>? {
        return EventORM.getInstance()!!.selectEventByUser(this, userId)
    }

    fun selectEvent(userId: Int) : ArrayList<Event>? {
        return EventORM.getInstance()!!.select(this, userId)
    }

    fun removeAllEventTags(eventId: Int) {
        EventTagORM.getInstance()!!.delete(this, eventId)
    }

    fun selectUserTags(_id: Int?): ArrayList<UserTag>? {
        return UserTagORM.getInstance()!!.select(this, _id!!)
    }

    fun selectAllEventsByTag(userTagsList: ArrayList<UserTag>?) : ArrayList<Event>? {
        var listEventTags = ArrayList<EventTag>()
        val eventsList = ArrayList<Event>()
        for (userTag in userTagsList!!){
            listEventTags = EventTagORM.getInstance()!!.select(this, userTag.tag_id!!)!!
            for (event in listEventTags){
                val actualEvent = EventORM.getInstance()!!.selectEvent(this, event.event_id)
                if(actualEvent != null){
                    eventsList.add(actualEvent[0])
                }
            }
        }
        return eventsList
    }

    fun removeAllUserTags(userId: Int) {
        UserTagORM.getInstance()!!.delete(this, userId)
    }
}
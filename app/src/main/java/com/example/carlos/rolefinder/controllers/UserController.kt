package com.example.carlos.rolefinder.controllers

import android.content.Context
import android.content.Intent
import com.example.carlos.rolefinder.CurrentApplication
import com.example.carlos.rolefinder.UserHomeView
import com.example.carlos.rolefinder.database.DatabaseHelper
import com.example.carlos.rolefinder.database.UserORM
import com.example.carlos.rolefinder.models.Event
import com.example.carlos.rolefinder.models.Tags
import com.example.carlos.rolefinder.models.User
import com.example.carlos.rolefinder.models.UserTag

class UserController() {

    fun tryLogin(context : Context, email: String) : User? {
        var user : User? = null
        val databaseHelper = DatabaseHelper(context)
        user = databaseHelper.login(email)
        return user
    }

    fun getTags(context : Context) : ArrayList<Tags>? {
        val databaseHelper = DatabaseHelper(context)
        return databaseHelper.selectAllTags()
    }

    fun insert(context : Context, user : User) : Long {
        val databaseHelper = DatabaseHelper(context)
        return databaseHelper.insertUser(user)
    }

    fun insertUserTag(context : Context, tagId : Int?, userId : Int){
        var user : User? = null
        val databaseHelper = DatabaseHelper(context)
        val userTag = UserTag(0, tagId, userId)
        databaseHelper.insertUserTag(userTag)
    }

    fun getSuggestedEvents(context: Context) : ArrayList<Event>? {
        var user = CurrentApplication.instance.getLoggedUser()
        val databaseHelper = DatabaseHelper(context)
        var userTagsList = databaseHelper.selectUserTags(user!!._id)
        var eventsList = databaseHelper.selectAllEventsByTag(userTagsList)
        return eventsList
    }
}
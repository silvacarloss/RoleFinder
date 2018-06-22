package com.example.carlos.rolefinder.controllers

import android.content.Context
import android.content.Intent
import com.example.carlos.rolefinder.UserHomeView
import com.example.carlos.rolefinder.database.DatabaseHelper
import com.example.carlos.rolefinder.database.UserORM
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

    fun insert(context : Context, user : User) : Boolean {
        val databaseHelper = DatabaseHelper(context)
        return databaseHelper.insertUser(user)
    }

    fun insertUserTag(context : Context, email : String?, tagId : Int?){
        var user : User? = null
        val databaseHelper = DatabaseHelper(context)
        user = databaseHelper.login(email!!)
        val userTag = UserTag(0, user!!._id, tagId)
        databaseHelper.insertUserTag(userTag)
    }
}
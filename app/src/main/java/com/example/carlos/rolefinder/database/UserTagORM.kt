package com.example.carlos.rolefinder.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.UserTag
import com.example.carlos.rolefinder.utils.Constants

class UserTagORM {
    companion object {

        private var instance : UserTagORM? = null

        @JvmStatic
        internal fun getInstance() : UserTagORM? {
            if (instance == null)
                return UserTagORM()

            return instance
        }
    }

    lateinit var database : SQLiteDatabase

    fun insert(helper : SQLiteOpenHelper, userTag : UserTag) : Long {
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.UserTag.COLUMN_TAG, userTag.tag_id)
        valuesToInsert.put(Constants.UserTag.COLUMN_USER, userTag.user_id)
        var id : Long = -1
        try{
            id = database.insert(Constants.UserTag.USER_TAGS_TABLE_NAME, null, valuesToInsert)
            database.close()
        }catch (ex : Exception){
            database.close()
        }
        return id
    }

    fun select(helper : SQLiteOpenHelper) : ArrayList<UserTag>? {
        database = helper.readableDatabase
        var listUsers : ArrayList<UserTag>? = null
        var userTag : UserTag? = null
        val cursor = database.query(Constants.UserTag.USER_TAGS_TABLE_NAME,
                arrayOf(Constants.UserTag.COLUMN_TAG,
                        Constants.UserTag.COLUMN_USER),
                null,
                null,
                null,
                null,
                null)

        if(cursor.moveToFirst()){
            do {
                userTag!!._id = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_ID)).toInt()
                userTag.user_id = cursor.getString(cursor.getColumnIndex(Constants.UserTag.COLUMN_USER)).toInt()
                userTag.tag_id = cursor.getString(cursor.getColumnIndex(Constants.UserTag.COLUMN_TAG)).toInt()
                listUsers!!.add(userTag)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listUsers
    }

    fun delete(helper : SQLiteOpenHelper, userTag : UserTag){
        database = helper.writableDatabase
        database.delete(Constants.UserTag.USER_TAGS_TABLE_NAME,
                Constants.UserTag.COLUMN_ID,
                arrayOf(userTag._id.toString()))

        database.close()
    }
}
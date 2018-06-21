package com.example.carlos.rolefinder.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.User
import com.example.carlos.rolefinder.utils.Constants

class UserORM {
    companion object {

        private var instance : UserORM? = null

        @JvmStatic
        internal fun getInstance() : UserORM? {
            if (instance == null)
                return UserORM()

            return instance
        }
    }

    lateinit var database : SQLiteDatabase

    fun insert(helper : SQLiteOpenHelper, user : User) : Boolean {
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.Users.COLUMN_EMAIL, user.email)
        valuesToInsert.put(Constants.Users.COLUMN_NAME, user.name)
        valuesToInsert.put(Constants.Users.COLUMN_PASSWORD, user.password)
        valuesToInsert.put(Constants.Users.COLUMN_USER_KIND, user.userKind)
        try{
            database.insert(Constants.Users.USERS_TABLE_NAME, null, valuesToInsert)
            database.close()
            return true
        }catch (ex : Exception){
            database.close()
            return false
        }
    }

    fun update(helper : SQLiteOpenHelper, user : User){
        database = helper.writableDatabase
        val valuesToInsert = ContentValues()
        valuesToInsert.put(Constants.Users.COLUMN_EMAIL, user.email)
        valuesToInsert.put(Constants.Users.COLUMN_NAME, user.name)
        valuesToInsert.put(Constants.Users.COLUMN_PASSWORD, user.password)
        valuesToInsert.put(Constants.Users.COLUMN_USER_KIND, user.userKind)

        database.update(Constants.Users.USERS_TABLE_NAME,
                valuesToInsert, "_id=?",
                arrayOf(user._id.toString()))

    }

    fun select(helper : SQLiteOpenHelper) : ArrayList<User>? {
        database = helper.readableDatabase
        var listUsers : ArrayList<User>? = null
        var user : User? = null
        val cursor = database.query(Constants.Users.USERS_TABLE_NAME,
                arrayOf(Constants.Users.COLUMN_ID,
                        Constants.Users.COLUMN_EMAIL,
                        Constants.Users.COLUMN_PASSWORD,
                        Constants.Users.COLUMN_USER_KIND),
                null,
                null,
                null,
                null,
                null)

        if(cursor.moveToFirst()){
            do {
                user!!._id = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_ID)).toInt()
                user.email = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_EMAIL))
                user.name = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_NAME))
                user.password = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_PASSWORD))
                user.userKind = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_USER_KIND)).toInt()
                listUsers!!.add(user)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listUsers
    }

    fun delete(helper : SQLiteOpenHelper, user : User){
        database = helper.writableDatabase
        database.delete(Constants.Users.USERS_TABLE_NAME,
                Constants.Users.COLUMN_ID,
                arrayOf(user._id.toString()))

        database.close()
    }

    fun selectUser(helper : SQLiteOpenHelper, email: String) : User? {
        database = helper.readableDatabase
        val query = "SELECT * FROM ${Constants.Users.USERS_TABLE_NAME} WHERE ${Constants.Users.COLUMN_EMAIL} = ?"
        val cursor = database.rawQuery(query, arrayOf(email))
        val user = User(null, null, null, null, null)
        if (cursor.moveToFirst()){
            user._id = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_ID)).toInt()
            user.email = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_EMAIL))
            user.name = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_NAME))
            user.password = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_PASSWORD))
            user.userKind = cursor.getString(cursor.getColumnIndex(Constants.Users.COLUMN_USER_KIND)).toInt()
        }
        if(user.name != null) return user
        else return null
    }
}
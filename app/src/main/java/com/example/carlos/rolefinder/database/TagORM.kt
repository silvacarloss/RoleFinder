package com.example.carlos.rolefinder.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carlos.rolefinder.models.Tags
import com.example.carlos.rolefinder.utils.Constants

class TagORM {
    companion object {

        private var instance : TagORM? = null

        @JvmStatic
        internal fun getInstance() : TagORM? {
            if (instance == null)
                return TagORM()

            return instance
        }
    }

    lateinit var database : SQLiteDatabase

    fun select(helper : SQLiteOpenHelper) : ArrayList<Tags>? {
        database = helper.readableDatabase
        var listTags = ArrayList<Tags>()
        val cursor = database.query(Constants.Tags.TAGS_TABLE_NAME,
                arrayOf(Constants.Tags.COLUMN_ID,
                        Constants.Tags.COLUMN_NAME),
                null,
                null,
                null,
                null,
                null)

        if(cursor.moveToFirst()){
            do {
                val tag = Tags(0, "")
                tag._id = cursor.getInt(cursor.getColumnIndex(Constants.Tags.COLUMN_ID))
                tag.name = cursor.getString(cursor.getColumnIndex(Constants.Tags.COLUMN_NAME))
                listTags.add(tag)
            }while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return listTags
    }
}
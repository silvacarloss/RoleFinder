package com.example.carlos.rolefinder.utils

import java.util.regex.Pattern

class Constants {
    class Tags {
        companion object {
            val COLUMN_ID = "_id"
            val COLUMN_NAME = "name"
            val TAGS_TABLE_NAME = "tags"
            val CREATE_TABLE_TAGS = "CREATE TABLE tags " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT);"

            val listTags = arrayListOf("Kids",
                    "Balada", "LGBT", "Cultura", "Restaurante", "Dança",
                    "Teatro", "Tecnologia", "Barzinho")
        }

    }

    class Users{
        companion object {
            val COLUMN_ID = "_id"
            val COLUMN_EMAIL = "email"
            val COLUMN_NAME = "name"
            val COLUMN_PASSWORD = "password"
            val COLUMN_USER_KIND = "userKind"

            val USERS_TABLE_NAME = "users"
            val CREATE_TABLE_USERS = "CREATE TABLE users " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT unique, " +
                    "name TEXT, " +
                    "password TEXT," +
                    "userKind INTEGER);"
        }
    }

    class Events{
        companion object {
            val COLUMN_ID = "_id"
            val COLUMN_TITLE = "title"
            val COLUMN_DESCRIPTION = "description"
            val COLUMN_ADDRESS = "address"
            val COLUMN_DATA = "data"
            val COLUMN_PRICE = "price"

            val EVENTS_TABLE_NAME = "events"
            val CREATE_TABLE_EVENTS = "CREATE TABLE events " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, " +
                    "description TEXT, " +
                    "address TEXT, " +
                    "data TEXT, " +
                    "price FLOAT);"
        }
    }

    class EventTag{
        companion object {
            val COLUMN_ID = "_id"
            val COLUMN_EVENT = "event"
            val COLUMN_TAG = "tag"

            val EVENTS_TAG_TABLE_NAME = "eventtags"
            val CREATE_TABLE_EVENTS_TAG = "CREATE TABLE eventtags " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "event TEXT," +
                    "tag TEXT);"
        }

    }

    class UserTag{
        companion object {
            val COLUMN_ID = "_id"
            val COLUMN_USER = "user"
            val COLUMN_TAG = "tag"

            val USER_TAGS_TABLE_NAME = "usertags"
            val CREATE_TABLE_USER_TAGS = "CREATE TABLE usertags " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user TEXT," +
                    "tag TEXT);"
        }
    }

    class ApplicationUtils {
        companion object {
            fun validateEmail(email : String) : Boolean {
                return Pattern.compile(
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
                ).matcher(email).matches()
            }
        }
    }
}
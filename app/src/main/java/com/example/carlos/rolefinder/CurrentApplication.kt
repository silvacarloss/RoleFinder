package com.example.carlos.rolefinder

import com.example.carlos.rolefinder.models.User

class CurrentApplication {
    companion object {

        private var instance : CurrentApplication? = null

        @JvmStatic
        internal fun getInstance() : CurrentApplication? {
            if (instance == null)
                return CurrentApplication()

            return instance
        }
    }

    private var loggedUser : User? = null

    fun setLoggedUser(user : User){
        loggedUser = user
    }

    fun getLoggedUser() : User? {
        return loggedUser
    }
}
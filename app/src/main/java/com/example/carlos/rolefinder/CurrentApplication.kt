package com.example.carlos.rolefinder

import com.example.carlos.rolefinder.models.User

class CurrentApplication {

    companion object {
        val instance: CurrentApplication by lazy { Holder.INSTANCE }
    }

    private object Holder { val INSTANCE = CurrentApplication() }

    init {

    }

    private var loggedUser : User? = null

    fun setLoggedUser(user : User?){
        loggedUser = user
    }

    fun getLoggedUser() : User? {
        return loggedUser
    }
}
package com.example.carlos.rolefinder.adapters

class EventAdaptee (var _id : Int,
                    var title : String,
                    var description : String){

    override fun toString(): String {
        return "Evento: " + title + "\nDescrição: " + description
    }
}
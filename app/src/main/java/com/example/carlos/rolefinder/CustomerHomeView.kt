package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.carlos.rolefinder.controllers.EventsController

class CustomerHomeView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_view)

        val btnNewEvent = findViewById<Button>(R.id.btnNewEvent)
        btnNewEvent.setOnClickListener(View.OnClickListener {
            var wantNewEvent = Intent(this, NewEvent::class.java)
            startActivity(wantNewEvent)
        })

        fillScreenWithEvents()
    }

    private fun fillScreenWithEvents() {
        val eventController = EventsController()
        var currentUserId = CurrentApplication.instance.getLoggedUser()!!._id
        var txtMessage = findViewById<TextView>(R.id.txtMessage)
        val listMyEvents = eventController.selectMyEvents(this, currentUserId)
        if(listMyEvents != null){
            for(event in listMyEvents){
                println(event.title)
            }
        }else{
            txtMessage.text ="Woops! It looks like you haven't created any event"
        }
    }
}

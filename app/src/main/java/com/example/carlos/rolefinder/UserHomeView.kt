package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.carlos.rolefinder.adapters.EventAdaptee
import com.example.carlos.rolefinder.controllers.EventsController
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.Event

class UserHomeView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home_view)
        fillScreenWithEvents()
    }

    override fun onResume() {
        super.onResume()
        fillScreenWithEvents()
    }

    private fun fillScreenWithEvents() {
        val userController = UserController()
        val listEvents = findViewById<ListView>(R.id.userListEvents)
        val txtMessage = findViewById<TextView>(R.id.txtMessage)
        val listAdaptedEvents = ArrayList<EventAdaptee>()
        val listMyEvents = userController.getSuggestedEvents(this)
        if(listMyEvents != null){

            for(event in listMyEvents){
                var eventAdaptee = EventAdaptee(event._id!!, event.title!!, event.description!!)
                listAdaptedEvents.add(eventAdaptee)
            }
            val adapter  = ArrayAdapter<EventAdaptee>(this, android.R.layout.simple_list_item_1, listAdaptedEvents)
            addListItemListener(listEvents, adapter, listMyEvents)
            listEvents.adapter = adapter
        }else{
            txtMessage.text ="Woops! It looks like you haven't created any event"
        }
    }

    private fun addListItemListener(listView: ListView,
                                    adapter: ArrayAdapter<EventAdaptee>,
                                    listEvents : ArrayList<Event>) {
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapter: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val item = adapter!!.getItemAtPosition(position)
                val params = Bundle()
                //todo call details screen
                val intent = Intent(baseContext, NewEvent::class.java)
                intent.putExtras(params)
                startActivity(intent)
            }
        }
    }
}

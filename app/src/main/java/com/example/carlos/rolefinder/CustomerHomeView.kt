package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.carlos.rolefinder.adapters.EventAdaptee
import com.example.carlos.rolefinder.controllers.EventsController
import android.widget.ArrayAdapter
import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.example.carlos.rolefinder.models.Event


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

    override fun onResume() {
        super.onResume()
        fillScreenWithEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.editProfile -> {
                var editUser = Intent(this, Register::class.java)
                val extraParams = Bundle()
                extraParams.putBoolean("is_edit", true)
                editUser.putExtras(extraParams)
                startActivity(editUser)
            }
            R.id.btnLogout -> {
                var logoutIntent = Intent(this, MainActivity::class.java)
                CurrentApplication.instance.setLoggedUser(null)
                startActivity(logoutIntent)
            }
        }
        return true
    }

    private fun fillScreenWithEvents() {
        val eventController = EventsController()
        val listEvents = findViewById<ListView>(R.id.listEvents)
        val currentUserId = CurrentApplication.instance.getLoggedUser()!!._id
        val txtMessage = findViewById<TextView>(R.id.txtMessage)
        val listAdaptedEvents = ArrayList<EventAdaptee>()
        val listMyEvents = eventController.selectMyEvents(this, currentUserId)
        if(listMyEvents != null){
            for(event in listMyEvents){
                val eventAdaptee = EventAdaptee(event._id!!, event.title!!, event.description!!)
                listAdaptedEvents.add(eventAdaptee)
            }
            val adapter  = ArrayAdapter<EventAdaptee>(this, android.R.layout.simple_list_item_1, listAdaptedEvents)
            addListItemListener(listEvents, adapter, listMyEvents)
            listEvents.adapter = adapter
        }else{
            txtMessage.text ="Woops! It looks that you haven't created any event"
        }
    }

    private fun addListItemListener(listView: ListView,
                                    adapter: ArrayAdapter<EventAdaptee>,
                                    listEvents : ArrayList<Event>) {
        listView.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(adapter: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val item = adapter!!.getItemAtPosition(position)
                val params = Bundle()
                params.putBoolean("is_edit", true)
                params.putInt("event_id", listEvents.get(position)._id!!)
                val intent = Intent(baseContext, NewEvent::class.java)
                intent.putExtras(params)
                startActivity(intent)
            }
        }

    }
}

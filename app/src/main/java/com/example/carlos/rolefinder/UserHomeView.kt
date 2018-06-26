package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.carlos.rolefinder.adapters.EventAdaptee
import com.example.carlos.rolefinder.controllers.EventsController
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.Event
import java.util.*

class UserHomeView : AppCompatActivity() {

    var listMyEvents : ArrayList<Event>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home_view)
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

    override fun onResume() {
        super.onResume()
        fillScreenWithEvents()
    }

    private fun fillScreenWithEvents() {
        val userController = UserController()
        val listEvents = findViewById<ListView>(R.id.userListEvents)
        val txtMessage = findViewById<TextView>(R.id.txtMessage)
        val listAdaptedEvents = ArrayList<EventAdaptee>()
        listMyEvents = userController.getSuggestedEvents(this)
        if(listMyEvents != null){
            for(event in listMyEvents!!){
                if(!isPastEvent(event)){
                    val eventAdaptee = EventAdaptee(event._id!!, event.title!!, event.description!!)
                    if(!listAdaptedEvents.contains(eventAdaptee)){
                        listAdaptedEvents.add(eventAdaptee)
                    }
                }else{
                    var eventsController = EventsController()
                    eventsController.delete(this, event._id!!)
                    onResume()
                }

            }
            val adapter  = ArrayAdapter<EventAdaptee>(this, android.R.layout.simple_list_item_1, listAdaptedEvents)
            addListItemListener(listEvents, adapter, listMyEvents!!)
            listEvents.adapter = adapter
        }else{
            txtMessage.text ="Woops! It looks like you haven't created any event"
        }
    }

    fun isPastEvent(event : Event) : Boolean {
        val splittedDate = event.date!!.split("/")

        val calendar = Calendar.getInstance()

        if(splittedDate[2].toInt() < calendar.get(Calendar.YEAR)){
            Toast.makeText(this, this.getString(R.string.impossible_past), Toast.LENGTH_LONG).show()
            return true
        }else if(splittedDate[2].toInt() >= calendar.get(Calendar.YEAR)
                && splittedDate[1].toInt() < (calendar.get(Calendar.MONTH) + 1)){
            Toast.makeText(this, this.getString(R.string.impossible_past), Toast.LENGTH_LONG).show()
            return true
        }else if(splittedDate[2].toInt() >= calendar.get(Calendar.YEAR)
                && splittedDate[1].toInt() >= (calendar.get(Calendar.MONTH) + 1)
                && splittedDate[0].toInt() < calendar.get(Calendar.DAY_OF_MONTH)){
            Toast.makeText(this, this.getString(R.string.impossible_past), Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    private fun addListItemListener(listView: ListView,
                                    adapter: ArrayAdapter<EventAdaptee>,
                                    listEvents : ArrayList<Event>) {
        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapter: AdapterView<*>?, v: View?, position: Int, id: Long) {
                val item = adapter!!.getItemAtPosition(position)
                val params = Bundle()
                params.putString("event_title", listMyEvents!!.get(position).title)
                params.putString("event_description", listMyEvents!!.get(position).description)
                params.putString("event_address", listMyEvents!!.get(position).address)
                params.putString("event_date", listMyEvents!!.get(position).date)
                params.putFloat("event_price", listMyEvents!!.get(position).price!!)
                val intent = Intent(baseContext, EventDetails::class.java)
                intent.putExtras(params)
                startActivity(intent)
            }
        }
    }
}

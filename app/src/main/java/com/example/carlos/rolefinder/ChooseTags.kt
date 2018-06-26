package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.carlos.rolefinder.controllers.EventsController
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.Event
import com.example.carlos.rolefinder.models.User
import android.widget.CompoundButton
import com.example.carlos.rolefinder.models.Tags
import kotlinx.android.synthetic.main.activity_main.*


class ChooseTags : AppCompatActivity() {
    lateinit var llChecks : LinearLayout
    var objectTags = ArrayList<Tags>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_tags)
        llChecks = findViewById<LinearLayout>(R.id.llChecks)

        getTags()
    }

    private fun getTags() {
        val userController = UserController()
        val listTags = userController.getTags(this)
        for (tag in listTags!!){
            val check = CheckBox(applicationContext)
            check.text = tag.name
            check.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked) objectTags.add(tag)
                    else objectTags.remove(tag)
                }
            )
            llChecks.addView(check)
        }
        val btnNext = Button(applicationContext)
        btnNext.text = "Save"
        btnNext.setOnClickListener(View.OnClickListener {
            showHomeView()
        })
        llChecks.addView(btnNext)
    }

    private fun showHomeView() {
        if(objectTags != null){
            if(getIntent().getExtras().getBoolean("is_user")){
                saveUser()
            }else{
                saveEvent()
            }
        }else{
            Toast.makeText(this, this.getString(R.string.select_least_one), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEvent() {
        var event = Event(
            0,
            getIntent().getExtras().getString("event_title"),
            getIntent().getExtras().getString("event_description"),
            getIntent().getExtras().getString("event_address"),
            getIntent().getExtras().getString("event_date"),
            getIntent().getExtras().getFloat("event_price"),
            CurrentApplication.instance.getLoggedUser()!!._id
        )

        val eventsController = EventsController()
        var id : Long = -1
        try{
            if(intent.extras.getBoolean("is_edit")){
                event._id = getIntent().getExtras().getInt("event_id")
                eventsController.update(this, event)
                id = event._id!!.toLong()
                eventsController.removeAllEventTags(this, event._id!!)
            }else{
                id = eventsController.insert(this, event)
            }
            event._id = id.toInt()
            insertEventTags(event)
            val showHomeView = Intent(this, CustomerHomeView::class.java)
            Toast.makeText(this, this.getString(R.string.event_inserted), Toast.LENGTH_SHORT).show()
            startActivity(showHomeView)
        }catch (ex : Exception){
            Toast.makeText(this, this.getString(R.string.event_not_inserted), Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveUser() {
        val user = User(
            0,
            getIntent().getExtras().getString("user_email"),
            getIntent().getExtras().getString("user_name"),
            getIntent().getExtras().getString("user_password"),
            getIntent().getExtras().getInt("user_type")
        )

        val userController = UserController()
        try{
            if(getIntent().getExtras().getBoolean("is_edit")){
                user._id = CurrentApplication.instance.getLoggedUser()!!._id
                userController.update(this, user)
                if(user.userKind == 1){
                    userController.removeAllUserTags(this, user._id!!)
                }
            }else{
                val userId = userController.insert(this, user)
                user._id = userId.toInt()
            }
            insertUserTags(user)
            val showUserHomeView = Intent(this, UserHomeView::class.java)
            CurrentApplication.instance.setLoggedUser(user)
            startActivity(showUserHomeView)
        }catch (ex : Exception){

        }

    }

    private fun insertUserTags(user : User) {
        for(tag in objectTags){
            val userController = UserController()
            userController.insertUserTag(this, tag._id!!, user._id!!)
        }
    }

    private fun insertEventTags(event : Event) {
        for(tag in objectTags){
            val eventController = EventsController()
            eventController.insertEventTag(this, tag._id!!, event._id!!)
        }
    }
}

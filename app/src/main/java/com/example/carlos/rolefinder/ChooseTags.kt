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
            Toast.makeText(this, "Select at least one item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEvent() {
        var event = Event(
            0,
            getIntent().getExtras().getString("event_title"),
            getIntent().getExtras().getString("event_description"),
            getIntent().getExtras().getString("event_address"),
            getIntent().getExtras().getString("event_date"),
            getIntent().getExtras().getFloat("event_price")
        )

        val eventsController = EventsController()
        if(eventsController.insert(this, event)){
            val showHomeView = Intent(this, CustomerHomeView::class.java)
            Toast.makeText(this, "Event added successfully", Toast.LENGTH_SHORT).show()
            startActivity(showHomeView)
        }else{
            Toast.makeText(this, "Eerror while adding contact", Toast.LENGTH_SHORT).show()
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
        if(userController.insert(this, user)){
            val showUserHomeView = Intent(this, UserHomeView::class.java)
            insertUserTags(user)
            startActivity(showUserHomeView)
        }
    }

    private fun insertUserTags(user : User) {
        for(tag in objectTags){
            val userController = UserController()
            userController.insertUserTag(this, user.email, tag._id)
        }
    }
}

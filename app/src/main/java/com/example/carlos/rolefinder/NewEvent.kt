package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carlos.rolefinder.controllers.EventsController

class NewEvent : AppCompatActivity() {
    lateinit var txtTitle : EditText
    lateinit var txtDescription : EditText
    lateinit var txtAddress : EditText
    lateinit var txtDate : EditText
    lateinit var txtPrice : EditText
    var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        txtTitle = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        txtAddress = findViewById(R.id.txtAddress)
        txtDate = findViewById(R.id.txtDate)
        txtPrice = findViewById(R.id.txtPrice)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener(View.OnClickListener {
            showTagOptions()
        })

        verifyIsEdit()
    }

    private fun verifyIsEdit() {
        val intent = intent
        val extras = intent.extras

        if(extras != null){
            if(getIntent().getExtras().getBoolean("is_edit")){
                isEdit = true
                fillFields(getIntent().getExtras().getInt("event_id"))
            }
        }
    }

    private fun showTagOptions() {
        val canSubmit = !txtTitle.text.isNullOrBlank() &&
                !txtDescription.text.isNullOrBlank() &&
                !txtAddress.text.isNullOrBlank() &&
                !txtDate.text.isNullOrBlank() &&
                !txtPrice.text.isNullOrBlank()

        if(canSubmit){
            val paramsToSend = Bundle()
            val intentShowTags = Intent(this, ChooseTags::class.java)
            paramsToSend.putBoolean("is_user", false)
            paramsToSend.putString("event_title", txtTitle.text.toString())
            paramsToSend.putString("event_description", txtDescription.text.toString())
            paramsToSend.putString("event_address", txtAddress.text.toString())
            paramsToSend.putString("event_date", txtDate.text.toString())
            paramsToSend.putFloat("event_price", txtPrice.text.toString().toFloat())
            if(isEdit){
                paramsToSend.putBoolean("is_edit", true)
                paramsToSend.putInt("event_id", getIntent().getExtras().getInt("event_id"))
            }
            intentShowTags.putExtras(paramsToSend)
            startActivity(intentShowTags)
        }else{
            Toast.makeText(this, "Please, fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun fillFields(eventId : Int){
        val eventsController = EventsController()
        val event = eventsController.select(this, eventId)!![0]
        txtTitle.setText(event.title)
        txtDescription.setText(event.description)
        txtAddress.setText(event.address)
        txtDate.setText(event.date)
        txtPrice.setText(event.price.toString())
    }
}

package com.example.carlos.rolefinder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EventDetails : AppCompatActivity() {

    lateinit var txtTitle : TextView
    lateinit var txtDescription : TextView
    lateinit var txtPrice : TextView
    lateinit var txtDate : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        txtTitle = findViewById(R.id.lblTitle)
        txtDescription = findViewById(R.id.lblDescription)
        txtPrice = findViewById(R.id.lblPrice)
        txtDate = findViewById(R.id.lblDate)

        fillFields()
    }

    private fun fillFields() {
        txtTitle.text = getIntent().getExtras().getString("event_title")
        txtDescription.text = getIntent().getExtras().getString("event_description")
        txtDate.text = getIntent().getExtras().getString("event_date")
        txtPrice.text = "R$ " + getIntent().getExtras().getFloat("event_price")
    }


}

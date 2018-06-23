package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class EventDetails : AppCompatActivity() {

    lateinit var txtTitle : TextView
    lateinit var txtDescription : TextView
    lateinit var txtPrice : TextView
    lateinit var txtDate : TextView
    lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        txtTitle = findViewById(R.id.lblTitle)
        txtDescription = findViewById(R.id.lblDescription)
        txtPrice = findViewById(R.id.lblPrice)
        txtDate = findViewById(R.id.lblDate)
        btnBack = findViewById(R.id.btnBack)

        fillFields()
        btnBack.setOnClickListener(View.OnClickListener { showHomeView() })
    }

    private fun showHomeView() {
        var homeView = Intent(this, UserHomeView::class.java)
        startActivity(homeView)
    }

    private fun fillFields() {
        txtTitle.text = getIntent().getExtras().getString("event_title")
        txtDescription.text = getIntent().getExtras().getString("event_description")
        txtDate.text = getIntent().getExtras().getString("event_date")
        txtPrice.text = "R$ " + getIntent().getExtras().getFloat("event_price")
    }


}

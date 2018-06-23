package com.example.carlos.rolefinder

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_event.*

class EventDetails : AppCompatActivity() {

    lateinit var txtTitle : TextView
    lateinit var txtDescription : TextView
    lateinit var txtPrice : TextView
    lateinit var txtDate : TextView
    lateinit var txtAddress : TextView
    lateinit var btnBack : Button
    lateinit var btnGetRouteToGo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        txtTitle = findViewById(R.id.lblTitle)
        txtDescription = findViewById(R.id.lblDescription)
        txtPrice = findViewById(R.id.lblPrice)
        txtDate = findViewById(R.id.lblDate)
        txtAddress = findViewById(R.id.lblAddress)
        btnBack = findViewById(R.id.btnBack)
        btnGetRouteToGo = findViewById(R.id.btnGetRouteToGo)

        fillFields()
        btnBack.setOnClickListener(View.OnClickListener { showHomeView() })
        btnGetRouteToGo.setOnClickListener(View.OnClickListener { showMaps() })
    }

    private fun showMaps() {
        val map = "http://maps.google.co.in/maps?q=${lblAddress.text}"
        val openMaps = Intent(Intent.ACTION_VIEW, Uri.parse(map))
        startActivity(openMaps)

    }

    private fun showHomeView() {
        var homeView = Intent(this, UserHomeView::class.java)
        startActivity(homeView)
    }

    private fun fillFields() {
        txtTitle.text = getIntent().getExtras().getString("event_title")
        txtDescription.text = getIntent().getExtras().getString("event_description")
        txtDate.text = getIntent().getExtras().getString("event_date")
        txtAddress.text = getIntent().getExtras().getString("event_address")
        txtPrice.text = "R$ " + getIntent().getExtras().getFloat("event_price")
    }
}

package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class CustomerHomeView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_view)

        val btnNewEvent = findViewById<Button>(R.id.btnNewEvent)
        btnNewEvent.setOnClickListener(View.OnClickListener {
            var wantNewEvent = Intent(this, NewEvent::class.java)
            startActivity(wantNewEvent)
        })
    }
}

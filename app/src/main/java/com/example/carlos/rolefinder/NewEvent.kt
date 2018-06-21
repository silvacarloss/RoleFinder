package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NewEvent : AppCompatActivity() {
    lateinit var txtTitle : EditText
    lateinit var txtDescription : EditText
    lateinit var txtAddress : EditText
    lateinit var txtDate : EditText
    lateinit var txtPrice : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener(View.OnClickListener {
            showTagOptions()
        })
    }

    private fun showTagOptions() {
        txtTitle = findViewById(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        txtAddress = findViewById(R.id.txtAddress)
        txtDate = findViewById(R.id.txtDate)
        txtPrice = findViewById(R.id.txtPrice)
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
            intentShowTags.putExtras(paramsToSend)
            startActivity(intentShowTags)
        }else{
            Toast.makeText(this, "Please, fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}

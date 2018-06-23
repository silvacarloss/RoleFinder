package com.example.carlos.rolefinder

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.carlos.rolefinder.controllers.EventsController
import java.util.*

class NewEvent : AppCompatActivity() {
    lateinit var txtTitle : EditText
    lateinit var txtDescription : EditText
    lateinit var txtAddress : EditText
    lateinit var txtDate : TextView
    lateinit var txtPrice : EditText
    lateinit var lblDate : TextView
    lateinit var btnBack : Button
    val DIALOG_ID = 1
    var day : Int = 0
    var year : Int = 0
    var month : Int = 0
    var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        val calendar = Calendar.getInstance()

        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

        txtTitle = this.findViewById<EditText>(R.id.txtTitle)
        txtDescription = findViewById(R.id.txtDescription)
        txtAddress = findViewById(R.id.txtAddress)
        txtDate = findViewById(R.id.txtDate)
        txtPrice = findViewById(R.id.txtPrice)
        lblDate = findViewById(R.id.lblDate)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener(View.OnClickListener {
            showTagOptions()
        })

        txtDate.setOnClickListener(View.OnClickListener { showDialog(DIALOG_ID) })
        lblDate.setOnClickListener(View.OnClickListener { showDialog(DIALOG_ID) })

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener(View.OnClickListener { showCustomerHomeView() })

        verifyIsEdit()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        if(id == DIALOG_ID){
            return DatePickerDialog(this, dpListener, year, month, day)
        }
        return null
    }

    private var dpListener = DatePickerDialog.OnDateSetListener(function = { datePicker: DatePicker,
                                                                             year: Int,
                                                                             month: Int,
                                                                             day: Int ->
        this.year = year
        this.month = month
        this.day = day
        txtDate.text = "${day}/${month+1}/${year}"
    })

    private fun showCustomerHomeView() {
        val homeView = Intent(this, MainActivity::class.java)
        startActivity(homeView)
    }

    private fun verifyIsEdit() {
        val intent = intent
        val extras = intent.extras

        if(extras != null){
            if(getIntent().getExtras().getBoolean("is_edit")){
                isEdit = true
                fillFields(getIntent().getExtras().getInt("event_id"))
                val btnDeleteEvent = findViewById<Button>(R.id.btnDeleteEvent)
                btnDeleteEvent.visibility = View.VISIBLE
                btnDeleteEvent.setOnClickListener(View.OnClickListener { deleteEvent() })
            }
        }
    }

    private fun deleteEvent() {
        var eventsController = EventsController()
        eventsController.delete(this, intent.extras.getInt("event_id"))
        var homeView = Intent(this, CustomerHomeView::class.java)
        startActivity(homeView)
    }

    private fun showTagOptions() {
        val canSubmit = !txtTitle.text.isNullOrBlank() &&
                !txtDescription.text.isNullOrBlank() &&
                !txtAddress.text.isNullOrBlank() &&
                !txtDate.text.isNullOrBlank() &&
                !txtPrice.text.isNullOrBlank() &&
                isValidDate(txtDate.text.toString())

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

    private fun isValidDate(date : String): Boolean {
        val splittedDate = date.split("/")

        val calendar = Calendar.getInstance()

        if(splittedDate[2].toInt() < calendar.get(Calendar.YEAR)){
            Toast.makeText(this, "Impossible to create a past event", Toast.LENGTH_LONG).show()
            return false
        }else if(splittedDate[2].toInt() >= calendar.get(Calendar.YEAR)
                && splittedDate[1].toInt() < (calendar.get(Calendar.MONTH) + 1)){
            Toast.makeText(this, "Impossible to create a past event", Toast.LENGTH_LONG).show()
            return false
        }else if(splittedDate[2].toInt() >= calendar.get(Calendar.YEAR)
                && splittedDate[1].toInt() >= (calendar.get(Calendar.MONTH) + 1)
                && splittedDate[0].toInt() < calendar.get(Calendar.DAY_OF_MONTH)){
            Toast.makeText(this, "Impossible to create a past event", Toast.LENGTH_LONG).show()
            return false
        }
        return true
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

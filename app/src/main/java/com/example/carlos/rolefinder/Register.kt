package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.*
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.User
import com.example.carlos.rolefinder.utils.Constants


class Register : AppCompatActivity() {

    lateinit var txtEmail : EditText
    lateinit var txtName : EditText
    lateinit var txtPassword : EditText
    lateinit var radioGroup : RadioGroup
    var userType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtEmail = findViewById(R.id.txtEmail)
        txtName = findViewById(R.id.txtName)
        txtPassword = findViewById(R.id.txtPassword)
        radioGroup = findViewById(R.id.groupRadio)

        addListener()
    }

    private fun addListener() {
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener(View.OnClickListener {
            saveUser()
        })
    }

    private fun saveUser() {
        val canSubmit : Boolean
        canSubmit = !txtEmail.text.isNullOrBlank()
                && !txtName.text.isNullOrBlank()
                && !txtPassword.text.isNullOrBlank()
                && userType != 0
        val validEmail = Constants.ApplicationUtils.validateEmail(txtEmail.text.toString())

        if(canSubmit && validEmail){
            if(userType == 1){
                val chooseTags = Intent(this, ChooseTags::class.java)
                val paramsToSend = Bundle()
                paramsToSend.putBoolean("is_user", true)
                paramsToSend.putString("user_email", txtEmail.text.toString())
                paramsToSend.putString("user_password", txtPassword.text.toString())
                paramsToSend.putString("user_name", txtName.text.toString())
                paramsToSend.putInt("user_type", userType)
                chooseTags.putExtras(paramsToSend)
                startActivity(chooseTags)
            }else if(userType == 2){
                showHomeView()
            }
        }else{
            println(txtEmail.text.toString())
            if(!validEmail) Toast.makeText(this, "Please type a valid email", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "All items are required", Toast.LENGTH_LONG).show()
        }
    }

    fun selectUserType(view : View){
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.radioReceive -> {
                userType = 1
            }
            R.id.radioAddEvents -> if (checked){
                userType = 2
            }
        }
    }

    private fun showHomeView() {
        var user = User(
            0,
            txtEmail.text.toString(),
            txtPassword.text.toString(),
            txtName.text.toString(),
            userType
        )
        val userController = UserController()
        try{
            var userId = userController.insert(this, user)
            user._id = userId.toInt()
            val homeView = Intent(this, CustomerHomeView::class.java)
            CurrentApplication.getInstance()!!.setLoggedUser(user)
            startActivity(homeView)
        }catch (ex : Exception){

        }
    }
}

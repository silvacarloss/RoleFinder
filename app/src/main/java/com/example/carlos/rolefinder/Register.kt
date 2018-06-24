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
    var isEdit : Boolean = false
    var userType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtEmail = findViewById(R.id.txtEmail)
        txtName = findViewById(R.id.txtName)
        txtPassword = findViewById(R.id.txtPassword)
        radioGroup = findViewById(R.id.groupRadio)

        addListener()
        verifyIsEdit()
    }

    private fun verifyIsEdit() {
        val intent = intent
        val extras = intent.extras

        if(extras != null){
            isEdit = intent.extras.getBoolean("is_edit")
            fillFields()
        }
    }

    private fun fillFields() {
        txtName.setText(CurrentApplication.instance.getLoggedUser()!!.name.toString())
        txtPassword.setText(CurrentApplication.instance.getLoggedUser()!!.password.toString())
        txtEmail.setText(CurrentApplication.instance.getLoggedUser()!!.email.toString())
        if(CurrentApplication.instance.getLoggedUser()!!.userKind == 1) {
            radioGroup.check(R.id.radioReceive)
            userType = 1
        }
        else {
            radioGroup.check(R.id.radioAddEvents)
            userType = 2
        }
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
            val userController = UserController()
            var listExistentEmail : User? = null
            if(!intent.hasExtra("is_edit")){
                listExistentEmail = userController.tryLogin(this, txtEmail.text.toString())
            }

            if(userType == 1 && listExistentEmail == null){
                val chooseTags = Intent(this, ChooseTags::class.java)
                val paramsToSend = Bundle()
                paramsToSend.putBoolean("is_user", true)
                paramsToSend.putString("user_email", txtEmail.text.toString())
                paramsToSend.putString("user_password", txtPassword.text.toString())
                paramsToSend.putString("user_name", txtName.text.toString())
                paramsToSend.putInt("user_type", userType)
                chooseTags.putExtras(paramsToSend)
                startActivity(chooseTags)
            }else if(userType == 2 && listExistentEmail == null){
                showHomeView()
            }else{
                Toast.makeText(this,
                        this.getString(R.string.email_exists),
                        Toast.LENGTH_LONG).show()
            }
        }else{
            println(txtEmail.text.toString())
            if(!validEmail) Toast.makeText(this, this.getString(R.string.invalid_email), Toast.LENGTH_LONG).show()
            else Toast.makeText(this, this.getString(R.string.items_required), Toast.LENGTH_LONG).show()
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
        val user = User(
            0,
            txtEmail.text.toString(),
            txtName.text.toString(),
            txtPassword.text.toString(),
            userType
        )
        val userController = UserController()
        try{
            if(intent.hasExtra("is_edit")){
                isEdit = intent.extras.getBoolean("is_edit")
            }

            if(isEdit){
                user._id = CurrentApplication.instance.getLoggedUser()!!._id
                println("esse inferno ta editando" + user._id)
                userController.update(this, user)
            }else{
                val userId = userController.insert(this, user)
                user._id = userId.toInt()
            }
            CurrentApplication.instance.setLoggedUser(user)
            val homeView = Intent(this, CustomerHomeView::class.java)
            startActivity(homeView)
        }catch (ex : Exception){

        }
    }
}

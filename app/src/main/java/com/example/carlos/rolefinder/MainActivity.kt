package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.User

class MainActivity : AppCompatActivity() {

    lateinit var txtEmail : EditText
    lateinit var txtPassword : EditText
    lateinit var txtError : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (CurrentApplication.instance.getLoggedUser() != null){
            val user = CurrentApplication.instance.getLoggedUser()
            when(user!!.userKind){
                1 -> {
                    val showUserView = Intent(this, UserHomeView::class.java)
                    startActivity(showUserView)
                }
                2 -> {
                    val showCustomerHomeView = Intent(this, CustomerHomeView::class.java)
                    startActivity(showCustomerHomeView)
                }
            }
        }

        txtEmail = findViewById<EditText>(R.id.txtEmail)
        txtPassword = findViewById<EditText>(R.id.txtPassword)

        addButtonListeners()
    }

    private fun addButtonListeners() {
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener(View.OnClickListener {
            tryLogin()
        })

        btnRegister.setOnClickListener(View.OnClickListener {
            createUser()
        })
    }

    private fun tryLogin() {
        var isSubmitable : Boolean
        var user : User? = null
        val userController = UserController()
        isSubmitable = !txtEmail.text.isNullOrBlank() && !txtPassword.text.isNullOrBlank()

        if(isSubmitable){
            user = userController.tryLogin(this, txtEmail.text.toString())
            if (user != null){
                if(txtPassword.text.toString().equals(user.password)){
                    CurrentApplication.instance.setLoggedUser(user)
                    showNextView(user)
                }
            }else{
                Toast.makeText(this, this.getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, this.getString(R.string.please_fill_all_field), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNextView(user : User) {
        when(user.userKind){
            1 -> {
                val showUserHomeView = Intent(this, UserHomeView::class.java)
                startActivity(showUserHomeView)
            }
            2 -> {
                val showCustomerHomeView = Intent(this, CustomerHomeView::class.java)
                startActivity(showCustomerHomeView)
            }
        }
    }

    private fun createUser() {
        val registerUser = Intent(this, Register::class.java)
        startActivity(registerUser)
    }
}

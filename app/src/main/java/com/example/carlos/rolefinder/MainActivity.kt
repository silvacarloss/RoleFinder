package com.example.carlos.rolefinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.carlos.rolefinder.controllers.UserController
import com.example.carlos.rolefinder.models.User

class MainActivity : AppCompatActivity() {

    lateinit var txtEmail : EditText
    lateinit var txtPassword : EditText
    lateinit var txtError : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        txtError = findViewById<Button>(R.id.txtError)
        isSubmitable = !txtEmail.text.isNullOrBlank() && !txtPassword.text.isNullOrBlank()

        if(isSubmitable){
            user = userController.tryLogin(this, txtEmail.text.toString())
            if (user == null){

            }else{
                //todo call new activity according to user options
            }
        }
    }

    private fun createUser() {
        val registerUser = Intent(this, Register::class.java)
        startActivity(registerUser)
    }

//    fun sendRequest(requestType : String){
//        when(requestType){
//            "tags" -> callApi("tags")
//        }
//    }
//
//    private fun callApi(s: String) {
//        val textView = findViewById<TextView>(R.id.txtShowRequest)
//        val queue = Volley.newRequestQueue(this)
//        val url = "http://192.168.1.39:8000/api/event"
//
//        val stringRequest = StringRequest(Request.Method.GET, url,
//                Response.Listener<String> { response ->
//                    val myObjects = JSONObject(response)
//                    val jsonArray = myObjects.getJSONArray("objects")
//
//                    textView.text = "Response is: ${response.substring(0, 300)}"
//                },
//                Response.ErrorListener { textView.text = "That didn't work!" })
//
//        queue.add(stringRequest)
//    }
}

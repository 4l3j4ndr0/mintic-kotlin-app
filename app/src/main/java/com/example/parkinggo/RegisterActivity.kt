package com.example.parkinggo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var inputVerifyPassword: EditText? = null
    private var btnRegister: Button? = null
    private var btnGoBack: Button? = null
    private var progressBar: ProgressBar? = null

    private var auth : FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        inputEmail = findViewById<EditText>(R.id.inputUser);
        inputPassword = findViewById<EditText>(R.id.inputPassword);
        inputVerifyPassword = findViewById<EditText>(R.id.inputValidatePassword);

        btnRegister = findViewById<Button>(R.id.btnRegisterAction);
        btnGoBack = findViewById<Button>(R.id.btnRegisterGoBack);
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnGoBack!!.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnRegister!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()
            val validatePassword = inputVerifyPassword!!.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext,"El email es requerido.", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext,"La contraseña es requerida.",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (password.length < 6){
                Toast.makeText(applicationContext,"La contraseña debe tener mínimo 6 caracteres." , Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if(password != validatePassword){
                Toast.makeText(applicationContext,"Las contraseñas no coinciden." , Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            progressBar!!.visibility = View.VISIBLE
            //create user
            auth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, OnCompleteListener {
                        task ->
                    progressBar!!.visibility = View.GONE
                    if (!task.isSuccessful){
                        Toast.makeText(this@RegisterActivity,"Ocurrió un error al registrar el usuario.",Toast.LENGTH_SHORT).show()
                        return@OnCompleteListener
                    }else{
                        Toast.makeText(this@RegisterActivity,"Usuario registrado exitosamente.",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                })
        })
    }
}
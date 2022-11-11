package com.example.parkinggo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class RecoveryActivity : AppCompatActivity() {

    private var auth: FirebaseAuth?=null

    private var btnRecovery: Button? = null
    private var inputEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        auth = FirebaseAuth.getInstance()

        inputEmail = findViewById<EditText>(R.id.inputEmail)

        btnRecovery = findViewById<Button>(R.id.btnRecovery)

        btnRecovery!!.setOnClickListener {
            auth!!.sendPasswordResetEmail(inputEmail!!.text.toString().trim()).addOnCompleteListener(this, OnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"Se ha enviado un email con las instrucciones para recuperar tu contraseña.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RecoveryActivity, LoginActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(applicationContext, "Error: $task", Toast.LENGTH_SHORT).show()
                    return@OnCompleteListener
                }
            })
        }
    }
}


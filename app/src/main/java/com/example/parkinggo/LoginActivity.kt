package com.example.parkinggo
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parkinggo.databinding.ActivityLoginBinding
import com.example.parkinggo.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var auth:FirebaseAuth?=null

    private var btnLogin:Button? = null
    private var btnRegister:Button? = null
    private var btnRecoveryPage:TextView? = null

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var progressBar:ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser !=null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        inputEmail = findViewById<EditText>(R.id.inputUser)
        inputPassword = findViewById<EditText>(R.id.inputPassword)

        btnRegister = findViewById<Button>(R.id.btnRegister)
        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnRecoveryPage = findViewById<TextView>(R.id.btnGoRecovery)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnRegister!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        })

        btnRecoveryPage!!.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RecoveryActivity::class.java))
        }

        btnLogin!!.setOnClickListener(View.OnClickListener {
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext,"Debe ingresar el email.",Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Debe ingresar la contraseña.", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            progressBar!!.visibility = View.VISIBLE

            auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener {
                        task ->
                    progressBar!!.visibility = View.VISIBLE

                    if (!task.isSuccessful){
                        if (password.length < 6){
                            inputPassword!!.error = "6 caracteres requeridos como mínimo."
                        }else{
                            Toast.makeText(this@LoginActivity,"Error de autenticación, intente nuevamente o contacte el administrador.",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this@LoginActivity,"Inicio de sesión exitoso, bienvenido(a)!.",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }
                })
        })
    }

}
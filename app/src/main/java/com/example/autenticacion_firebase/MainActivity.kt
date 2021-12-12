package com.example.autenticacion_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var email: EditText
    lateinit var contraseña: EditText
    lateinit var iniciarSesion: Button
    lateinit var registro: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email)
        contraseña = findViewById(R.id.contraseña)
        iniciarSesion = findViewById(R.id.iniciarSesion)
        registro = findViewById(R.id.registro)

        auth = Firebase.auth

        registro.setOnClickListener {
            crearcuenta(email.text.toString(), contraseña.text.toString())
        }
        iniciarSesion.setOnClickListener {
            iniciarsesion(email.text.toString(), contraseña.text.toString())
        }

    }

    private fun crearcuenta(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.d("estado", "usuario registrado")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.d("estado", "usuario NO registrado")

                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("estado", "" + auth.currentUser?.uid)
    }

    private fun iniciarsesion(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Log.d("estado", "Se ha iniciado sesion")
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Log.d("estado", "No es posible iniciar sesion")
                }
            }

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
package com.utec.proyectofinaltecnicatura

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.utec.proyectofinaltecnicatura.dtos.LogintDTO
import com.utec.proyectofinaltecnicatura.services.login
import com.utec.proyectofinaltecnicatura.utils.showErrorToast

class LoginActivity : AppCompatActivity() {

    private lateinit var editNombreUsuario: EditText
    private lateinit var editContrania: EditText
    private fun showLogin() {
        val fadeIn: Animation = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 1000

        editNombreUsuario.animation = fadeIn
        editContrania.animation = fadeIn

        editNombreUsuario.isVisible = true
        editContrania.isVisible = true
    }

    private fun hideLogin() {
        editNombreUsuario.isInvisible = true
        editContrania.isInvisible = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editNombreUsuario = findViewById(R.id.editNombreUsuario)
        editContrania = findViewById(R.id.editTextContrasenia)

        var registro = findViewById<Button>(R.id.testLinkRegistro)
        registro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
            hideLogin()
        }

        var login = findViewById<Button>(R.id.loginBtn)
        login.setOnClickListener {

            if (!editNombreUsuario.isVisible || !editContrania.isVisible) {
                showLogin()
                return@setOnClickListener
            }

            val nombre = editNombreUsuario.text.toString()
            val contrania = editContrania.text.toString()

            if (nombre.isBlank()) {
                showErrorToast(this, "El nombre de usuario es requerido")
                return@setOnClickListener
            }

            if (contrania.isBlank()) {
                showErrorToast(this, "La contraseña es requerida")
                return@setOnClickListener
            }

            val credencials = LogintDTO(nombre, contrania)
            login(credencials,{
                val editor = getSharedPreferences("AUTHORIZATION", MODE_PRIVATE).edit()
                editor.putString("token", it.token)
                editor.apply()
                startActivity(Intent(this, HomeActivity::class.java))
            }, {
                showErrorToast(this, "Error al iniciar sesión: $it")
                Log.d("ERROR", it)
            })
        }
    }
}
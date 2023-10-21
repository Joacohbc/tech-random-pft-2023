package com.utec.proyectofinaltecnicatura

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.utec.proyectofinaltecnicatura.dtos.LogintDTO
import com.utec.proyectofinaltecnicatura.dtos.TokenDTO
import com.utec.proyectofinaltecnicatura.services.authServices
import com.utec.proyectofinaltecnicatura.services.validateToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        editContrania = findViewById(R.id.editTextContraseña)

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
                Toast.makeText(this, "El nombre de usuario es requerido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contrania.isBlank()) {
                Toast.makeText(this, "La contraseña es requerida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authServices.login(LogintDTO(nombre, contrania)).enqueue(object : Callback<TokenDTO> {
                override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                    if (response.isSuccessful) {
                        val token = response.body()
                        token?.let {
                            val editor = getSharedPreferences("AUTHORIZATION", MODE_PRIVATE).edit()
                            editor.putString("token", it.token)
                            editor.apply()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        }

                    } else {
                        val error = JSONObject(response.errorBody()?.string())
                        Toast.makeText(this@LoginActivity, error.getString("error"), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                    println("Error: " + t.message)
                }
            })
        }
    }
}
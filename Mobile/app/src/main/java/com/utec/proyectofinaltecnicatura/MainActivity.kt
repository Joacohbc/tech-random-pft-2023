package com.utec.proyectofinaltecnicatura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.utec.proyectofinaltecnicatura.services.setToken
import com.utec.proyectofinaltecnicatura.services.validateToken

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Si el token es nulo, entonces no hay token, por lo que no hay que validar nada
        val token = getSharedPreferences("AUTHORIZATION", MODE_PRIVATE).getString("token", "")
        if (token == null || token == "") {
            setToken(token!!)
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        validateToken(token, {
            setToken(it.token)
            startActivity(Intent(this, HomeActivity::class.java))
        }, {
            getSharedPreferences("AUTHORIZATION", MODE_PRIVATE).edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            Log.d("MainActivity", "Error al validar token: $it")
        })
    }
}

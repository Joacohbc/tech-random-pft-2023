package com.utec.proyectofinaltecnicatura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.utec.proyectofinaltecnicatura.dtos.TokenDTO
import com.utec.proyectofinaltecnicatura.menufragments.BienvenidaFragment
import com.utec.proyectofinaltecnicatura.menufragments.PerfilFragment
import com.utec.proyectofinaltecnicatura.menufragments.ReclamoFragment
import com.utec.proyectofinaltecnicatura.services.authServices
import com.utec.proyectofinaltecnicatura.services.validateToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Si el token es nulo, entonces no hay token, por lo que no hay que validar nada
        val gt = getSharedPreferences("AUTHORIZATION", MODE_PRIVATE)
        val token = gt.getString("token", "")

        if (token == null || token == "") {
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        validateToken(token, {
            startActivity(Intent(this, HomeActivity::class.java))
        }, {
            getSharedPreferences("AUTHORIZATION", MODE_PRIVATE).edit().remove("token").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            Log.d("MainActivity", "Error al validar token: ${it.message}")
        })
    }
}

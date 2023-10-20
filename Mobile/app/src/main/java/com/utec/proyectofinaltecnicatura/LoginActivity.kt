package com.utec.proyectofinaltecnicatura

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.utec.proyectofinaltecnicatura.menufragments.BienvenidaFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var registro = findViewById<Button>(R.id.testLinkRegistro)
        registro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        var login = findViewById<Button>(R.id.loginBtn)
        login.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
package com.utec.proyectofinaltecnicatura

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var registro= findViewById<Button>(R.id.testLinkRegistro)
        registro.setOnClickListener {
            var i= Intent(this, Registro::class.java)
            startActivity(i)
        }

        /*
         var login= findViewById<Button>(R.id.loginBtn)
        login.setOnClickListener {
            var i= Intent(this, Home::class.java)
            startActivity(i)
        }
*/

    }
}
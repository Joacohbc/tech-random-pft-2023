package com.utec.proyectofinaltecnicatura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {


    private lateinit var nav: NavigationBarView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vista= Intent(this, Login::class.java)
        startActivity(vista)


        nav = findViewById<BottomNavigationView>(R.id.nav)
        nav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home-> supportFragmentManager.beginTransaction().replace(R.id.contenedor,Home()).commit()
                R.id.perfil -> supportFragmentManager.beginTransaction().replace(R.id.contenedor, Perfil()).commit()
                R.id.constancias -> supportFragmentManager.beginTransaction().replace(R.id.contenedor, Constancias()).commit()
                R.id.reclamos-> supportFragmentManager.beginTransaction().replace(R.id.contenedor, Reclamos()).commit()
            }
            true
        }

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().replace(R.id.contenedor, Home()).commit()
    }


}

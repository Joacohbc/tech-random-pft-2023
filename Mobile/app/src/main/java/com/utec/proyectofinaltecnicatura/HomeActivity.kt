package com.utec.proyectofinaltecnicatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.utec.proyectofinaltecnicatura.menufragments.BienvenidaFragment
import com.utec.proyectofinaltecnicatura.menufragments.ConstanciasFragment
import com.utec.proyectofinaltecnicatura.menufragments.PerfilFragment
import com.utec.proyectofinaltecnicatura.menufragments.ReclamoFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var nav: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        nav = findViewById<BottomNavigationView>(R.id.nav)
        nav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home-> supportFragmentManager.beginTransaction().replace(R.id.contenedor, BienvenidaFragment()).commit()
                R.id.perfil -> supportFragmentManager.beginTransaction().replace(R.id.contenedor, PerfilFragment()).commit()
                R.id.reclamos-> supportFragmentManager.beginTransaction().replace(R.id.contenedor, ReclamoFragment()).commit()
                R.id.constancias -> supportFragmentManager.beginTransaction().replace(R.id.contenedor, ConstanciasFragment()).commit()
            }
            true
        }

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().replace(R.id.contenedor, BienvenidaFragment()).commit()
    }
}
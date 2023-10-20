package com.utec.proyectofinaltecnicatura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.utec.proyectofinaltecnicatura.menufragments.BienvenidaFragment
import com.utec.proyectofinaltecnicatura.menufragments.PerfilFragment
import com.utec.proyectofinaltecnicatura.menufragments.ReclamoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vista = Intent(this, LoginActivity::class.java)
        startActivity(vista)
    }
}

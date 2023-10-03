package com.utec.proyectofinaltecnicatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)



        val generos = resources.getStringArray(R.array.genero)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_genero, generos)
        val autocompletar = findViewById<AutoCompleteTextView>(R.id.genero)
        autocompletar.setAdapter(arrayAdapter)


    }
}
package com.utec.proyectofinaltecnicatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast

class Registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)


        val generos = resources.getStringArray(R.array.genero)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_genero, generos)
        val autoCompletar: AutoCompleteTextView= findViewById(R.id.genero)
        autoCompletar.setAdapter(arrayAdapter)
        autoCompletar.onItemClickListener=AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            val itemSelected= adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected",Toast.LENGTH_SHORT).show()

        }




    }
}



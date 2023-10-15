package com.utec.proyectofinaltecnicatura

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.Calendar

class Registro : AppCompatActivity() {

    val selecteCalendar= Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        var volver= findViewById<Button>(R.id.linkvolver)
        volver.setOnClickListener {
           var i= Intent(this, Login::class.java)
            startActivity(i)
        }


        val generos= resources.getStringArray(R.array.genero)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_genero, generos)
        val autoCompletar: AutoCompleteTextView= findViewById(R.id.genero)
        autoCompletar.setAdapter(arrayAdapter)
        autoCompletar.onItemClickListener=AdapterView.OnItemClickListener {
                adapterView, view, i, l ->
            val itemSelected= adapterView.getItemAtPosition(i)
            Toast.makeText(this, "Item: $itemSelected",Toast.LENGTH_SHORT).show()

        }



        fun onClickFechaNacimiento(v: View?){
            val fechnac= findViewById<EditText>(R.id.fechanac)
            val d= selecteCalendar.get(Calendar.DAY_OF_MONTH)
            val mes=selecteCalendar.get(Calendar.MONTH)
            val anio=selecteCalendar.get(Calendar.YEAR)
            val listener= DatePickerDialog.OnDateSetListener{datePicker, d, m, a ->
            selecteCalendar.set(d, m, a)
            fechnac.setText("$d/ $m/ $a")
            }
            DatePickerDialog(this, listener,  d, mes, anio).show()

        }

    }

}



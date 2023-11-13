package com.utec.proyectofinaltecnicatura

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import com.utec.proyectofinaltecnicatura.dtos.Formatos
import com.utec.proyectofinaltecnicatura.dtos.ItrDTO
import com.utec.proyectofinaltecnicatura.dtos.enums.Departamento
import com.utec.proyectofinaltecnicatura.dtos.enums.Genero
import com.utec.proyectofinaltecnicatura.services.authServices
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.itrService
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.Normalizer.Form
import java.time.LocalDate
import java.util.Calendar

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        cargarComboBox()

        val fecNacimento = findViewById<EditText>(R.id.fechaNacimientoacimiento)
        fecNacimento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                fecNacimento.setText(Formatos.ToFormatedString(selectedDate))
            }, year, month, day)
            datePickerDialog.show()
        }

        val btnRegistrar = findViewById<Button>(R.id.btnRegistro)
        btnRegistrar.setOnClickListener {
            val documento = findViewById<EditText>(R.id.documento).text.toString()
            val nombres = findViewById<EditText>(R.id.nombres).text.toString()
            val apellidos = findViewById<EditText>(R.id.apellidos).text.toString()
            val emailPersonal = findViewById<EditText>(R.id.emailPersonal).text.toString()
            val emailInstitucional = findViewById<EditText>(R.id.emailUtec).text.toString()
            val telefono = findViewById<EditText>(R.id.telefono).text.toString()
            val contrasenia = findViewById<EditText>(R.id.editTextContrasenia).text.toString()
            var fechaNacimiento= LocalDate.now()



            try {
                fechaNacimiento = Formatos.ToLocalDate(findViewById<EditText>(R.id.fechaNacimientoacimiento).text.toString())

            }catch (e: Exception){
                showErrorToast(this@RegistroActivity, "Debe seleccionar una fecha de nacimiento válida")
                return@setOnClickListener
            }

            val genero = findViewById<AutoCompleteTextView>(R.id.genero).text.toString()
            val departamento = findViewById<AutoCompleteTextView>(R.id.departamento).text.toString()

            val itrs = findViewById<AutoCompleteTextView>(R.id.itr).adapter as List<ItrDTO>
            val itr = itrs.find { itr -> itr.nombre == findViewById<AutoCompleteTextView>(R.id.itr).text.toString() }
            val localidad = findViewById<EditText>(R.id.localidad).text.toString()
            val generacion = findViewById<EditText>(R.id.generacion).text.toString()
            val estudiante = EstudianteDTO()
            estudiante.documento = documento
            estudiante.nombres = nombres
            estudiante.apellidos = apellidos
            estudiante.emailPersonal = emailPersonal
            estudiante.emailUtec = emailInstitucional
            estudiante.telefono = telefono
            estudiante.contrasenia = contrasenia
            estudiante.fecNacimiento = fechaNacimiento
            estudiante.genero = Genero.valueOf(genero)
            estudiante.departamento = Departamento.valueOf(departamento)
            estudiante.itr = itr
            estudiante.localidad = localidad
            estudiante.generacion =generacion.toInt()

            authServices.register(estudiante).enqueue(object : Callback<EstudianteDTO> {
                override fun onFailure(call: Call<EstudianteDTO>, t: Throwable) {
                    showErrorToast(this@RegistroActivity, "Error al registrar el estudiante")
                    Log.d("RegistroActivity", t.message.toString())
                }

                override fun onResponse(call: Call<EstudianteDTO>, response: Response<EstudianteDTO>) {
                    if(!response.isSuccessful) {
                        showErrorToast(baseContext, JSONObject(response.errorBody()?.string()).getString("error"))
                        Log.d("RegistroActivity", response.errorBody()?.string().toString())
                        return
                    }
                    finish()
                }
            })
        }

    }

    private fun cargarComboBox() {
        val generoAutoComplete = findViewById<AutoCompleteTextView>(R.id.genero)
        val departamentoAutoComplete = findViewById<AutoCompleteTextView>(R.id.departamento)
        val itrs = findViewById<AutoCompleteTextView>(R.id.itr)

        val generoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Genero.values())
        val departamentoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Departamento.values())

        itrService.getItrs(getToken()).enqueue(object : Callback<List<ItrDTO>> {
            override fun onFailure(call: Call<List<ItrDTO>>, t: Throwable) {
                showErrorToast(this@RegistroActivity, "Error al cargar los ITRs")
                Log.d("RegistroActivity", t.message.toString())
            }

            override fun onResponse(call: Call<List<ItrDTO>>, response: Response<List<ItrDTO>>) {
                if(!response.isSuccessful) {
                    showErrorToast(baseContext, JSONObject(response.errorBody()?.string()).getString("error"))
                    Log.d("RegistroActivity", response.errorBody()?.string().toString())
                    return
                }

                val itrAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, response.body()!!)
                itrs.setAdapter(itrAdapter)
            }
        })

        generoAutoComplete.setAdapter(generoAdapter)
        departamentoAutoComplete.setAdapter(departamentoAdapter)
    }

}
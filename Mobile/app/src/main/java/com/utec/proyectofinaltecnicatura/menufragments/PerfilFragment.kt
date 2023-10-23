package com.utec.proyectofinaltecnicatura.menufragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import com.utec.proyectofinaltecnicatura.dtos.Formatos
import com.utec.proyectofinaltecnicatura.dtos.UsuarioDTO
import com.utec.proyectofinaltecnicatura.dtos.enums.Departamento
import com.utec.proyectofinaltecnicatura.dtos.enums.Genero
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.usuarioService
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import com.utec.proyectofinaltecnicatura.utils.showInfoToast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Calendar

class PerfilFragment : Fragment() {

    private lateinit var nombresEditText : EditText
    private lateinit var apellidosEditText : EditText
    private lateinit var documentoEditText : EditText
    private lateinit var emailPersonalEditText : EditText
    private lateinit var emailUtecEditText : EditText
    private lateinit var telEditText : EditText
    private lateinit var fechaNacimientoEditText : EditText
    private lateinit var generoAutoComplete : AutoCompleteTextView
    private lateinit var departamentoAutoComplete : AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        nombresEditText = view.findViewById(R.id.nombres)
        apellidosEditText = view.findViewById(R.id.apellidos)
        documentoEditText = view.findViewById(R.id.documento)
        emailPersonalEditText = view.findViewById(R.id.emailp)
        emailUtecEditText = view.findViewById(R.id.mailutec)
        telEditText = view.findViewById(R.id.tel)
        fechaNacimientoEditText = view.findViewById(R.id.fechaNacimientoacimiento)
        generoAutoComplete = view.findViewById(R.id.genero)
        departamentoAutoComplete = view.findViewById(R.id.departamento)

        cargarComboBox(view)
        cargarDatos()

        val editTextFechaNacimiento = view.findViewById<EditText>(R.id.fechaNacimientoacimiento)
        editTextFechaNacimiento.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                editTextFechaNacimiento.setText(Formatos.ToFormatedString(selectedDate))
            }, year, month, day)
            datePickerDialog.show()
        }

        val guardarButton = view.findViewById<View>(R.id.guardar)
        guardarButton.setOnClickListener {

            val informacionEstudiante = EstudianteDTO()
            informacionEstudiante.nombres = nombresEditText.text.toString()
            informacionEstudiante.apellidos = apellidosEditText.text.toString()
            informacionEstudiante.emailPersonal = emailPersonalEditText.text.toString()
            informacionEstudiante.telefono = telEditText.text.toString()
            informacionEstudiante.fecNacimiento = Formatos.ToLocalDate(fechaNacimientoEditText.text.toString())
            informacionEstudiante.genero = Genero.values().find { genero -> genero.toString() == generoAutoComplete.text.toString() }
            informacionEstudiante.departamento = Departamento.values().find { departamento -> departamento.toString() == departamentoAutoComplete.text.toString() }

            usuarioService.modificarMiUsuario(getToken(), informacionEstudiante).enqueue(object : Callback<EstudianteDTO> {
                override fun onResponse(call: Call<EstudianteDTO>, response: Response<EstudianteDTO>) {
                    if(!response.isSuccessful) {
                        showErrorToast(requireContext(), JSONObject(response.errorBody()?.string()).getString("error"))
                        return
                    }
                    showInfoToast(requireContext(), "Datos guardados correctamente")
                }

                override fun onFailure(call: Call<EstudianteDTO>, t: Throwable) {
                    showErrorToast(requireContext(), "Error al guardar los datos")
                    Log.d("PerfilFragment", t.message.toString())
                }
            })
        }
        return view
    }

    private fun cargarComboBox(view : View) {
        val generoAutoComplete = view.findViewById<AutoCompleteTextView>(R.id.genero)
        val departamentoAutoComplete = view.findViewById<AutoCompleteTextView>(R.id.departamento)

        val generoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, Genero.values())
        val departamentoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, Departamento.values())

        generoAutoComplete.setAdapter(generoAdapter)
        departamentoAutoComplete.setAdapter(departamentoAdapter)
    }

    private fun cargarDatos() {
        usuarioService.getMiUsuario(getToken()).enqueue(object : Callback<EstudianteDTO> {
            override fun onResponse(call: Call<EstudianteDTO>, response: Response<EstudianteDTO>) {
                if(!response.isSuccessful) {
                    showErrorToast(requireContext(), JSONObject(response.errorBody()?.string()).getString("error"))
                    Log.d("PerfilFragment", response.errorBody()?.string().toString())
                    return
                }

                val estudiante = response.body()!!

                nombresEditText.setText(estudiante.nombres)
                apellidosEditText.setText(estudiante.apellidos)
                documentoEditText.setText(estudiante.documento)
                emailPersonalEditText.setText(estudiante.emailPersonal)
                emailUtecEditText.setText(estudiante.emailUtec)
                telEditText.setText(estudiante.telefono)
                fechaNacimientoEditText.setText(Formatos.ToFormatedString(estudiante.fecNacimiento))
                generoAutoComplete.setText(estudiante.genero.toString())
                departamentoAutoComplete.setText(estudiante.departamento.toString())
            }

            override fun onFailure(call: Call<EstudianteDTO>, t: Throwable) {
                showErrorToast(requireContext(), "Error al cargar los datos")
                Log.d("PerfilFragment", t.message.toString())
            }
        })

    }
}


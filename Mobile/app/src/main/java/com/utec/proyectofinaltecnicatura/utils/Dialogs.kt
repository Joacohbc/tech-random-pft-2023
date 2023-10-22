package com.utec.proyectofinaltecnicatura.utils

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.dtos.EventoDTO
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.reclamoService
import org.json.JSONObject

fun showErrorDialog(context: Context, title: String, message: String) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setMessage(message)
    alertDialogBuilder.setIcon(android.R.drawable.ic_dialog_alert)

    alertDialogBuilder.setPositiveButton("Ok") { dialog, _ ->
        dialog.dismiss()
    }

    alertDialogBuilder.create().show()
}
fun showConfirmDialog(context: Context, title: String, message: String, onClick : (Boolean) -> Unit) {

    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)
    alertDialogBuilder.setMessage(message)

    alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
        onClick(true)
        dialog.dismiss()
    }

    alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
        onClick(false)
        dialog.dismiss()
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()

    alertDialog.setOnDismissListener {
        onClick(false)
    }
}

fun showReclamoDialog(context: Context, eventoDTO: EventoDTO, reclamoDTO: ReclamoDTO, title: String, onClick: (Boolean, String) -> Unit) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setTitle(title)

    val view = LayoutInflater.from(context).inflate(R.layout.dialog_reclamo, null)
    val etDetalle = view.findViewById<EditText>(R.id.etDetalle)
    val tvReclamoId = view.findViewById<TextView>(R.id.tvReclamoId)
    val tvEventoTitulo = view.findViewById<TextView>(R.id.tvEventoTitulo)
    val tvEventoFechas = view.findViewById<TextView>(R.id.tvEventoFechas)
    val tvEventoITR = view.findViewById<TextView>(R.id.tvEventoITR)

    etDetalle.setText(reclamoDTO.detalle)
    tvReclamoId.text = "Relcamo ID: ${reclamoDTO.idReclamo ?: "N/A"}"
    tvEventoTitulo.text = "Título del Evento: ${eventoDTO.titulo}"
    tvEventoFechas.text = "Fecha del Evento: ${eventoDTO.fechaInicio} - ${eventoDTO.fechaFin}"
    tvEventoITR.text = "ITR del Evento: ${eventoDTO.itr.nombre}"

    alertDialogBuilder.setView(view)

    alertDialogBuilder.setPositiveButton("Sí") { dialog, _ ->
        onClick(true, etDetalle.text.toString())
        dialog.dismiss()
    }

    alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
        onClick(false, "")
        dialog.dismiss()
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
}
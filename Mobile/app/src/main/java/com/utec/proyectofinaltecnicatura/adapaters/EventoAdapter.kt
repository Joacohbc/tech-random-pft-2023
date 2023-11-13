package com.utec.proyectofinaltecnicatura.adapaters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.dtos.EventoDTO
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.reclamoService
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import com.utec.proyectofinaltecnicatura.utils.showInfoToast
import com.utec.proyectofinaltecnicatura.utils.showReclamoDialog
import org.json.JSONObject
import javax.security.auth.callback.Callback

class EventoAdapter(private val eventos: List<EventoDTO>) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]

        holder.tvTitulo.text = evento.titulo
        holder.tvFechas.text = "Fecha: ${evento.fechaInicio} - ${evento.fechaFin}"
        holder.tvEstado.text = "Estado: ${if (evento.estado) "Activo" else "Inactivo"}"
        holder.tvLocalizacion.text = "Localización: ${evento.localizacion}"
        holder.tvModalidad.text = "Modalidad: ${evento.modalidad}"
        holder.tvItr.text = "ITR: ${evento.itr.nombre}"
        holder.tvTipoEvento.text = "Tipo de Evento: ${evento.tipoEvento.tipo}"

        holder.itemView.setOnClickListener {
            showReclamoDialog(holder.itemView.context, evento, ReclamoDTO(), "Crear Reclamo") { success, detalle ->
                if (!success) return@showReclamoDialog

                if (detalle.isEmpty()) {
                    showErrorToast(holder.itemView.context, "El Detalle del reclamo no puede estar vacío")
                    return@showReclamoDialog
                }

                val r = ReclamoDTO()
                r.evento = evento
                r.detalle = detalle
                reclamoService.createReclamo(getToken(), r).enqueue(object : retrofit2.Callback<ReclamoDTO> {
                    override fun onResponse(
                        call: retrofit2.Call<ReclamoDTO>,
                        response: retrofit2.Response<ReclamoDTO>
                    ) {
                        if (response.isSuccessful) {
                            notifyDataSetChanged()
                            showInfoToast(holder.itemView.context, "Reclamo creado correctamente")
                            return
                        }
                        showErrorToast(holder.itemView.context, JSONObject(response.errorBody()!!.string()).getString("error"))
                        Log.d("EventoAdapter onResponse", response.errorBody()!!.string())
                    }
                    override fun onFailure(call: retrofit2.Call<ReclamoDTO>, t: Throwable) {
                        Log.d("EventoAdapter onFailure", t.message.toString())
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvFechas: TextView = itemView.findViewById(R.id.tvFecha)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val tvLocalizacion: TextView = itemView.findViewById(R.id.tvLocalizacion)
        val tvModalidad: TextView = itemView.findViewById(R.id.tvModalidad)
        val tvItr: TextView = itemView.findViewById(R.id.tvItr)
        val tvTipoEvento: TextView = itemView.findViewById(R.id.tvTipoEvento)
    }
}
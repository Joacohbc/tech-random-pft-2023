package com.utec.proyectofinaltecnicatura.adapaters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.dtos.enums.EstadoReclamo
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.reclamoService
import com.utec.proyectofinaltecnicatura.utils.showConfirmDialog
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import com.utec.proyectofinaltecnicatura.utils.showInfoToast
import com.utec.proyectofinaltecnicatura.utils.showReclamoDialog
import org.json.JSONObject

class ReclamoAdapter(private val reclamos: ArrayList<ReclamoDTO>) : RecyclerView.Adapter<ReclamoAdapter.ReclamoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReclamoViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_reclamo, parent, false)
        return ReclamoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReclamoViewHolder, position: Int) {
        val reclamo: ReclamoDTO = reclamos[position]

        holder.tvReclamo.text = "Reclamo: #R${reclamo.idReclamo}"
        holder.tvDetalle.text = "Detalle: ${reclamo.detalle}"
        holder.tvEvento.text = "Evento: ${reclamo.evento.titulo}"
        holder.tvEstado.text = "Estado: ${reclamo.estado}"

        holder.btnModificar.setOnClickListener {
            if(reclamo.estado != EstadoReclamo.INGRESADO) {
                showErrorToast(holder.itemView.context, "Solo se pueden modificar reclamos en estado Ingresado")
                return@setOnClickListener
            }

            showReclamoDialog(holder.itemView.context, reclamo.evento, reclamo, "Modificar Reclamo") { success, detalle ->
                if (!success) return@showReclamoDialog

                if (detalle.isEmpty()) {
                    showErrorToast(holder.itemView.context, "El Detalle del reclamo no puede estar vacío")
                    return@showReclamoDialog
                }

                reclamo.detalle = detalle
                reclamoService.modificarReclamo(getToken(), reclamo).enqueue(object : retrofit2.Callback<ReclamoDTO> {
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

        holder.btnBaja.setOnClickListener {
            showConfirmDialog(holder.itemView.context, "Eliminar reclamo", "¿Está seguro que desea eliminar el reclamo?") { result ->
                if (!result) return@showConfirmDialog

                reclamoService.deleteReclamo(getToken(), reclamo.idReclamo).enqueue(object : retrofit2.Callback<ReclamoDTO> {
                    override fun onResponse(
                        call: retrofit2.Call<ReclamoDTO>,
                        response: retrofit2.Response<ReclamoDTO>
                    ) {
                        if (response.isSuccessful) {
                            reclamos.remove(reclamo)
                            notifyDataSetChanged()
                            showInfoToast(holder.itemView.context, "Reclamo eliminado correctamente")
                            return
                        }
                        showErrorToast(holder.itemView.context, JSONObject(response.errorBody()!!.string()).getString("error"))
                        Log.d("ReclamoAdapter onResponse", response.errorBody()!!.string())
                    }
                    override fun onFailure(call: retrofit2.Call<ReclamoDTO>, t: Throwable) {
                        Log.d("ReclamoAdapter onFailure", t.message.toString())
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return this.reclamos.size
    }

    class ReclamoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReclamo: TextView = itemView.findViewById(R.id.tvReclamo)
        val tvDetalle: TextView = itemView.findViewById(R.id.tvDetalle)
        val tvEvento: TextView = itemView.findViewById(R.id.tvEvento)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val btnModificar: Button = itemView.findViewById(R.id.btnModificar)
        val btnBaja: Button = itemView.findViewById(R.id.btnBaja)
    }
}


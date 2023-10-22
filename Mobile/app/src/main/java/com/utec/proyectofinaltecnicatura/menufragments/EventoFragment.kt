package com.utec.proyectofinaltecnicatura.menufragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.adapaters.EventoAdapter
import com.utec.proyectofinaltecnicatura.dtos.EventoDTO
import com.utec.proyectofinaltecnicatura.services.eventoService
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import retrofit2.Callback
import retrofit2.Response

class EventoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evento, container, false)

        val container = view.findViewById<RecyclerView>(R.id.rv_Listado)
        container.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        container.adapter = EventoAdapter(ArrayList())

        eventoService.getMisEventos(getToken()).enqueue(object : Callback<List<EventoDTO>> {
            override fun onResponse(
                call: retrofit2.Call<List<EventoDTO>>,
                response: Response<List<EventoDTO>>
            ) {
                if (response.isSuccessful) {
                    val eventos = response.body()!!
                    container.adapter = EventoAdapter(eventos)
                    container.adapter?.notifyDataSetChanged()
                    return
                }
                showErrorToast(requireContext(), "Error al obtener los reclamos")
                Log.d("ReclamoFragment onResponse", response.errorBody()!!.string())
            }
            override fun onFailure(call: retrofit2.Call<List<EventoDTO>>, t: Throwable) {
                showErrorToast(requireContext(), "Error al obtener los reclamos")
                Log.d("ReclamoFragment onFailure", t.message.toString())
            }
        })
        return view
    }
}
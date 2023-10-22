package com.utec.proyectofinaltecnicatura.menufragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utec.proyectofinaltecnicatura.R
import com.utec.proyectofinaltecnicatura.adapaters.ReclamoAdapter
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.services.getToken
import com.utec.proyectofinaltecnicatura.services.reclamoService
import com.utec.proyectofinaltecnicatura.utils.showErrorToast
import com.utec.proyectofinaltecnicatura.utils.showInfoToast
import retrofit2.Callback
import retrofit2.Response

class ReclamoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reclamos, container, false)

        val container = view.findViewById<RecyclerView>(R.id.rv_Listado)
        container.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        container.adapter = ReclamoAdapter(ArrayList())

        reclamoService.getMisReclamos(getToken()).enqueue(object : Callback<List<ReclamoDTO>> {
            override fun onResponse(
                call: retrofit2.Call<List<ReclamoDTO>>,
                response: Response<List<ReclamoDTO>>
            ) {
                if (response.isSuccessful) {
                    val reclamos = response.body()!!
                    container.adapter = ReclamoAdapter(reclamos as ArrayList<ReclamoDTO>)
                    container.adapter?.notifyDataSetChanged()
                    return
                }
                showErrorToast(requireContext(), "Error al obtener los reclamos")
                Log.d("ReclamoFragment onResponse", response.errorBody()!!.string())
            }
            override fun onFailure(call: retrofit2.Call<List<ReclamoDTO>>, t: Throwable) {
                showErrorToast(requireContext(), "Error al obtener los reclamos")
                Log.d("ReclamoFragment onFailure", t.message.toString())
            }
        })

        return view
    }
}
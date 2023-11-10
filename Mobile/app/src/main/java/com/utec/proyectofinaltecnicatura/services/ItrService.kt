package com.utec.proyectofinaltecnicatura.services

import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import com.utec.proyectofinaltecnicatura.dtos.EventoDTO
import com.utec.proyectofinaltecnicatura.dtos.ItrDTO
import com.utec.proyectofinaltecnicatura.dtos.LogintDTO
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.dtos.TokenDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ItrService {
    @Headers("Content-Type: application/json")
    @GET("auth/itrs")
    fun getItrs(@Header("Authorization") token : String) : Call<List<ItrDTO>>
}

val itrService: ItrService = retrofit.create(ItrService::class.java)
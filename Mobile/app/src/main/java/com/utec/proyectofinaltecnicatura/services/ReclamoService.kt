package com.utec.proyectofinaltecnicatura.services

import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
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
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReclamoService {
    @Headers("Content-Type: application/json")
    @GET("auth/reclamos/mios")
    fun getMisReclamos(@Header("Authorization") token : String) : Call<List<ReclamoDTO>>

    @Headers("Content-Type: application/json")
    @DELETE("auth/reclamos/{idReclamo}")
    fun deleteReclamo(@Header("Authorization") token : String, @Path("idReclamo") id : Long) : Call<ReclamoDTO>

    @Headers("Content-Type: application/json")
    @POST("auth/reclamos")
    fun createReclamo(@Header("Authorization") token : String, @Body reclamoDTO: ReclamoDTO) : Call<ReclamoDTO>

    @Headers("Content-Type: application/json")
    @PUT("auth/reclamos")
    fun modificarReclamo(@Header("Authorization") token : String, @Body reclamoDTO: ReclamoDTO) : Call<ReclamoDTO>

}

val reclamoService: ReclamoService = retrofit.create(ReclamoService::class.java)
package com.utec.proyectofinaltecnicatura.services

import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import com.utec.proyectofinaltecnicatura.dtos.ReclamoDTO
import com.utec.proyectofinaltecnicatura.dtos.UsuarioDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {
    @Headers("Content-Type: application/json")
    @PUT("auth/users/me")
    fun modificarMiUsuario(@Header("Authorization") token : String, @Body estudianteDTO: EstudianteDTO) : Call<EstudianteDTO>

    @Headers("Content-Type: application/json")
    @GET("auth/users/me")
    fun getMiUsuario(@Header("Authorization") token : String) : Call<EstudianteDTO>
}

val usuarioService = retrofit.create(UsuarioService::class.java)

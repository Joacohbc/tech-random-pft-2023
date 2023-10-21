package com.utec.proyectofinaltecnicatura.services

import com.utec.proyectofinaltecnicatura.dtos.LogintDTO
import com.utec.proyectofinaltecnicatura.dtos.TokenDTO
import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthServices {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body dto : LogintDTO): Call<TokenDTO>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun register(@Body estudianteDTO : EstudianteDTO): Call<EstudianteDTO>
}

private val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/ProyectoInfra/api/")
    .addConverterFactory(JacksonConverterFactory.create())
    .build();

val authServices: AuthServices = retrofit.create(AuthServices::class.java)
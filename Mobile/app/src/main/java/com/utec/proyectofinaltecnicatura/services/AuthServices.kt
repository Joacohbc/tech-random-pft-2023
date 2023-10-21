package com.utec.proyectofinaltecnicatura.services

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utec.proyectofinaltecnicatura.HomeActivity
import com.utec.proyectofinaltecnicatura.dtos.LogintDTO
import com.utec.proyectofinaltecnicatura.dtos.TokenDTO
import com.utec.proyectofinaltecnicatura.dtos.EstudianteDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthServices {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body dto: LogintDTO): Call<TokenDTO>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun register(@Body estudianteDTO: EstudianteDTO): Call<EstudianteDTO>

    @Headers("Content-Type: application/json")
    @POST("auth/refresh")
    fun refresh(@Body tokenDTO: TokenDTO): Call<TokenDTO>
}

private val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/ProyectoInfra/api/")
    .addConverterFactory(JacksonConverterFactory.create())
    .build();

val authServices: AuthServices = retrofit.create(AuthServices::class.java)
inline fun validateToken(token: String, crossinline onSuccess: (t : TokenDTO) -> Unit, crossinline onFailure: (t: Throwable) -> Unit) {
    authServices.refresh(TokenDTO(token)).enqueue(object : Callback<TokenDTO> {
        override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
            if (response.isSuccessful) {
                response.body()?.let(onSuccess)
                return
            }
            onFailure(Throwable(response.errorBody()!!.string()))
        }

        override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
            onFailure(t)
        }
    })
}
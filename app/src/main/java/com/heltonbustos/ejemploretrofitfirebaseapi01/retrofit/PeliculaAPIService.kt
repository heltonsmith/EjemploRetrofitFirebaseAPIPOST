package com.heltonbustos.ejemploretrofitfirebaseapi01.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PeliculaAPIService {

    @POST("bd.json")
    fun agregarPelicula(@Body pelicula: Pelicula): Call<PeliculaRespuestaAPI>

}
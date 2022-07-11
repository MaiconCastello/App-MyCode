package com.generation.mycode.api

import com.generation.mycode.model.Publicacoes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("publicacoes")
    suspend fun listPublicacoes(): Response<MutableList<Publicacoes>>
}
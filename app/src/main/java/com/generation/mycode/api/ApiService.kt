package com.generation.mycode.api

import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("publicacoes")
    suspend fun listPublicacoes(): Response<MutableList<Publicacoes>>

    @POST("publicacoes")
    suspend fun addPublicacoes(
        @Body publicacao: Publicacoes): Response<Publicacoes>

    @GET("publicacoes/{id}")
    suspend fun listComentarios(
        @Path("id") id: Long): Response<Publicacoes>

    @PUT("publicacoes/comentarios/{id}")
    suspend fun addComentarios(
        @Path("id") id: Long,
        @Body comentario: Comentario): Response<Publicacoes>

    @PUT("publicacoes/{id}")
    suspend fun updatePublicacoes(
        @Path("id") id: Long,
        @Body publicacao: Publicacoes): Response<Publicacoes>

    @DELETE("publicacoes/{id}")
    suspend fun deletePublicacoes(
        @Path("id") id: Long): Response<Publicacoes>
}
package com.generation.mycode.api

import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
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

    @PUT("publicacoes/comentarios/{id}/{id2}")
    suspend fun updateComentarios(
        @Path("id") id: Long,
        @Path("id2") id2: Long,
        @Body comentario: Comentario): Response<Publicacoes>

    @PUT("publicacoes/delete/{id}/{id2}")
    suspend fun deleteComentarios(
        @Path("id") id: Long,
        @Path("id2") id2: Long): Response<Publicacoes>

    @PUT("publicacoes/reacao/{id}")
    suspend fun createReacao(
        @Path("id") id: Long,
        @Body reacao: Reacao): Response<Publicacoes>

    @PUT("publicacoes/reacao/{id}/{id2}")
    suspend fun updateReacao(
        @Path("id") id: Long,
        @Path("id2") id2: Long,
        @Body reacao: Reacao): Response<Publicacoes>

    @GET("publicacoes/categoria/{categoria}")
    suspend fun searchCategoria(
        @Path("categoria") categoria: String): Response<MutableList<Publicacoes>>

    @GET("publicacoes/usuario/{usuario}")
    suspend fun searchUsuario(
        @Path("usuario") usuario: String): Response<MutableList<Publicacoes>>

}
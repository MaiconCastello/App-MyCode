package com.generation.mycode.api

import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import retrofit2.Response

class PublicacoesRepository {

    suspend fun listPublicacoes(): Response<MutableList<Publicacoes>> {
        return RetrofitObject.api.listPublicacoes()
    }

    suspend fun addPublicacoes(publicacao: Publicacoes): Response<Publicacoes> {
        return RetrofitObject.api.addPublicacoes(publicacao)
    }

    suspend fun listComentarios(id: Long): Response<Publicacoes> {
        return  RetrofitObject.api.listComentarios(id)
    }

    suspend fun addComentarios(id: Long, comentario: Comentario): Response<Publicacoes> {
        return RetrofitObject.api.addComentarios(id, comentario)
    }

    suspend fun updatePublicacoes(id: Long, publicacao: Publicacoes): Response<Publicacoes> {
        return RetrofitObject.api.updatePublicacoes(id, publicacao)
    }

    suspend fun deletePublicacoes(id: Long): Response<Publicacoes> {
        return RetrofitObject.api.deletePublicacoes(id)
    }


}
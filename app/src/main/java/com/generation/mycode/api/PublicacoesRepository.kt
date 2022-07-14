package com.generation.mycode.api

import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
import retrofit2.Response

class PublicacoesRepository {

    suspend fun listPublicacoes(): Response<MutableList<Publicacoes>> {
        return RetrofitObject.api.listPublicacoes()
    }

    suspend fun addPublicacoes(publicacao: Publicacoes): Response<Publicacoes> {
        return RetrofitObject.api.addPublicacoes(publicacao)
    }

    suspend fun updatePublicacoes(id: Long, publicacao: Publicacoes): Response<Publicacoes> {
        return RetrofitObject.api.updatePublicacoes(id, publicacao)
    }

    suspend fun deletePublicacoes(id: Long): Response<Publicacoes> {
        return RetrofitObject.api.deletePublicacoes(id)
    }

    suspend fun listComentarios(id: Long): Response<Publicacoes> {
        return  RetrofitObject.api.listComentarios(id)
    }

    suspend fun addComentarios(id: Long, comentario: Comentario): Response<Publicacoes> {
        return RetrofitObject.api.addComentarios(id, comentario)
    }

    suspend fun updateComentarios(id: Long, id2: Long, comentario: Comentario): Response<Publicacoes> {
        return RetrofitObject.api.updateComentarios(id, id2, comentario)
    }

    suspend fun deleteComentarios(id: Long, id2: Long): Response<Publicacoes> {
        return RetrofitObject.api.deleteComentarios(id, id2)
    }

    suspend fun createReacao(id: Long, reacao: Reacao): Response<Publicacoes> {
        return RetrofitObject.api.createReacao(id, reacao)
    }

    suspend fun updateReacao(id: Long, id2: Long, reacao: Reacao): Response<Publicacoes> {
        return RetrofitObject.api.updateReacao(id, id2, reacao)
    }

}
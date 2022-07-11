package com.generation.mycode.api

import com.generation.mycode.model.Publicacoes
import retrofit2.Response

class PublicacoesRepository {

    suspend fun listPublicacoes(): Response<MutableList<Publicacoes>> {
        return RetrofitObject.api.listPublicacoes()
    }

}
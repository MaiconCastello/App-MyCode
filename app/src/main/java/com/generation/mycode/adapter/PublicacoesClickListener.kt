package com.generation.mycode.adapter

import com.generation.mycode.model.Publicacoes

interface PublicacoesClickListener {

    fun onPublicacoesClickListenerGood(id: Long)
    fun onPublicacoesClickListenerBad(id: Long)
    fun onPublicacoesClickListenerComentarios()

}
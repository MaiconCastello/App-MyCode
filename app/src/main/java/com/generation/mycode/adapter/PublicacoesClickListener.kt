package com.generation.mycode.adapter

import android.view.View
import com.generation.mycode.model.Publicacoes

interface PublicacoesClickListener {

    fun onPublicacoesClickListenerGood(id: Long)
    fun onPublicacoesClickListenerBad(id: Long)
    fun onPublicacoesClickListenerComentarios(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerEdit(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerDelete(view: View, id: Long, publicacoes: Publicacoes)

}
package com.generation.mycode.adapter

import android.view.View
import com.generation.mycode.model.Publicacoes

interface PublicacoesClickListener {

    fun onPublicacoesClickListenerGood(publicacoes: Publicacoes)
    fun onPublicacoesClickListenerBad(publicacoes: Publicacoes)
    fun onPublicacoesClickListenerComentarios(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerEdit(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerDelete(view: View, id: Long, publicacoes: Publicacoes)

}
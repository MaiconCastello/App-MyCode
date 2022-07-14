package com.generation.mycode.adapter

import android.view.View
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Usuario

interface PublicacoesClickListener {

    fun onPublicacoesClickListenerGood(id: Long)
    fun onPublicacoesClickListenerBad(id: Long)
    fun onPublicacoesClickListener(idPublicacao: Long, view: View, idUsuario: String)
    fun onPublicacoesClickListenerComentarios(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerEdit(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerDelete(view: View, id: Long, publicacoes: Publicacoes)

}
package com.generation.mycode.adapter

import android.view.View
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Usuario

interface PublicacoesClickListener {

    fun onPublicacoesClickListenerGood(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerBad(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerComentarios(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerEdit(view: View, publicacoes: Publicacoes)
    fun onPublicacoesClickListenerDelete(view: View, id: Long, publicacoes: Publicacoes)
    fun onPublicacoesClickListener(idPublicacao: Long, view: View, idUsuario: String)
}
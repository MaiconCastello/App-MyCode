package com.generation.mycode.adapter

import android.view.View
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes

interface ComentariosClickListener {

    fun onComentariosClickListenerEdit(view: View, comentario: Comentario)
    fun onComentariosClickListenerDelete(view: View, comentario: Comentario)
}
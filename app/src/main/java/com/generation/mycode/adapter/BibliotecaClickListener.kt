package com.generation.mycode.adapter

import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo

interface BibliotecaClickListener {

    fun BibliotecaClickListener(idMetodo: Long)
    fun BibliotecaClickListenerDelete(metodo: Metodo)
}
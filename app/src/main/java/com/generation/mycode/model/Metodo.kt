package com.generation.mycode.model

class Metodo(
    val id: Long,
    var nome: String,
    var descricao: String,
    var passos: MutableList<Passo>
) {
}
package com.generation.mycode.model

class Publicacoes(
    var id: Long,
    var categoria: String,
    var conteudo: String,
    var imagem : String,
    var usuario: String,
    var good: Int,
    var bad: Int,
    var comentario: MutableList<Comentario>,
    val reacao: MutableList<Reacao>
) {
}
package com.generation.mycode.model

class Publicacoes(
    var id: Long,
    var categoria: String,
    var conteudo: String,
    var usuario: String,
    var good: Int,
    var bad: Int,
    var reacao: MutableList<Reacao>,
    var comentario: MutableList<Comentario>
) {
}
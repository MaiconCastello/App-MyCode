package com.generation.mycode.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "passos")
class Passo(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val idMetodo: Long,
    var nome: String,
    var descricao: String,
    var codigo: String
) {
}
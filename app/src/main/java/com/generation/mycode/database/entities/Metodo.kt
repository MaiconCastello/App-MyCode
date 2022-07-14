package com.generation.mycode.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "biblioteca")
class Metodo(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var nome: String,
    var descricao: String,
    var passos: MutableList<Passo>
) {
}
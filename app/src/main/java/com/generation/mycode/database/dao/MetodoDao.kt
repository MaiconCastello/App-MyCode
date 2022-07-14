package com.generation.mycode.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.generation.mycode.database.entities.Metodo

@Dao
interface MetodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMetodo(metodo: Metodo)

    @Query("SELECT * FROM biblioteca ORDER BY id ASC")
    fun selectMetodo(): LiveData<List<Metodo>>

    @Update
    fun updateMetodo(metodo: Metodo)

    @Delete
    fun deleteMetodo(metodo: Metodo)
}
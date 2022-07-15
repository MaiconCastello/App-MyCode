package com.generation.mycode.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.generation.mycode.database.entities.Passo

@Dao
interface PassosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPassos(passo: Passo)

    @Query("SELECT * FROM passos ORDER BY id ASC")
    fun selectPasso(): LiveData<List<Passo>>

    @Update
    fun updatePassos(passo: Passo)

    @Delete
    fun deletePassos(passo: Passo)

}
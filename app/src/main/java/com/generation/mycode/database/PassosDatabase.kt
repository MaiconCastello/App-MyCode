package com.generation.mycode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.generation.mycode.database.dao.PassosDao
import com.generation.mycode.database.entities.Passo
import kotlinx.coroutines.InternalCoroutinesApi


@Database(entities = [Passo::class], version = 1, exportSchema = false)
abstract class PassosDatabase: RoomDatabase() {

    abstract fun passosDao(): PassosDao

    companion object {
        @Volatile
        private var INSTANCE: PassosDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): PassosDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }//Metodo que será realizado dentro de uma corrotina de forma assincrona
            kotlinx.coroutines.internal.synchronized(this) {
                //Instanciando o banco de dados pela primeira vez, caso a condição seja false
                val instance = Room.databaseBuilder(
                    //Contexto de onde vai ser criado o bd
                    context.applicationContext,
                    //Com base na classe UserDataBase convertendo
                    //em uma class java e inserindo um nome
                    PassosDatabase::class.java,
                    "passos"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
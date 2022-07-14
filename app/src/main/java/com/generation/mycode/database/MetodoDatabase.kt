package com.generation.mycode.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.dao.MetodoDao
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Metodo::class], version = 1, exportSchema = false)
abstract class MetodoDatabase: RoomDatabase() {

    abstract fun metodoDao(): MetodoDao

    companion object {
        @Volatile
        private var INSTANCE: MetodoDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): MetodoDatabase {
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
                    MetodoDatabase::class.java,
                    "cesta_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
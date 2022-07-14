package com.generation.mycode.database.repositorydao

import com.generation.mycode.database.dao.MetodoDao
import com.generation.mycode.database.entities.Metodo

class RepositoryDao(private val metodoDao: MetodoDao) {

    val selectMetodo = metodoDao.selectMetodo()

    fun addMetodo(metodo: Metodo){
        metodoDao.addMetodo(metodo)
    }

    fun updateMetodo(metodo: Metodo){
        metodoDao.updateMetodo(metodo)
    }

    fun deleteMetodo(metodo: Metodo){
        metodoDao.deleteMetodo(metodo)
    }

}
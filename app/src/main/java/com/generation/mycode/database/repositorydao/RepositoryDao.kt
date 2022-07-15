package com.generation.mycode.database.repositorydao

import com.generation.mycode.database.dao.MetodoDao
import com.generation.mycode.database.dao.PassosDao
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo

class RepositoryDao(private val metodoDao: MetodoDao, private val passosDao: PassosDao) {

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

    val selectPasso = passosDao.selectPasso()

    fun addPassos(passo: Passo){
        passosDao.addPassos(passo)
    }
    fun updatePassos(passo: Passo){
        passosDao.updatePassos(passo)
    }

    fun deletePassos(passo: Passo){
        passosDao.deletePassos(passo)
    }


}
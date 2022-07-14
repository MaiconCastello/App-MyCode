package com.generation.mycode.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.generation.mycode.database.MetodoDatabase
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.repositorydao.RepositoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BibliotecaViewModel (application: Application) : AndroidViewModel(application) {

    val selectMetodo: LiveData<List<Metodo>>
    val repository: RepositoryDao


    init {
        val metodoDao = MetodoDatabase.getDataBase(application).metodoDao()
        repository = RepositoryDao(metodoDao)
        selectMetodo = repository.selectMetodo
    }

    fun addMetodo(metodo: Metodo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addMetodo(metodo)
            } catch (e: Exception) {
                Log.d("Error Insert Room", e.message.toString())
            }
        }

    }

    fun updateMetodo(metodo: Metodo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateMetodo(metodo)

            } catch (e: Exception) {
                Log.d("Error Update Room", e.message.toString())
            }
        }
    }



    fun deleteMetodo(metodo: Metodo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteMetodo(metodo)

            } catch (e: Exception) {
                Log.d("Error Delete Room", e.message.toString())
            }
        }
    }


}
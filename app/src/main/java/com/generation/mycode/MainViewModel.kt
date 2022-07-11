package com.generation.mycode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generation.mycode.api.PublicacoesRepository
import com.generation.mycode.model.Publicacoes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PublicacoesRepository
) : ViewModel() {

    //LiveData

    private val _myPublicacoesResponse =
        MutableLiveData<Response<MutableList<Publicacoes>>>()

    val myPublicacoesResponse: LiveData<Response<MutableList<Publicacoes>>> =
        _myPublicacoesResponse

    //Corrotinas

    fun listCategoria(){
        viewModelScope.launch {
            try {
                val response = repository.listPublicacoes()
                _myPublicacoesResponse.value = response

            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }
}
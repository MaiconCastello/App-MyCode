package com.generation.mycode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.generation.mycode.api.PublicacoesRepository
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PublicacoesRepository
) : ViewModel() {

    var publicacaoSelecionada: Publicacoes? = null
    var comentarioSelecionado: Comentario? = null

    //LiveData

    private val _myPublicacoesResponse =
        MutableLiveData<Response<MutableList<Publicacoes>>>()

    val myPublicacoesResponse: LiveData<Response<MutableList<Publicacoes>>> =
        _myPublicacoesResponse

    private val _myComentariosResponse =
        MutableLiveData<Response<Publicacoes>>()

    val myComentariosResponse: LiveData<Response<Publicacoes>> =
        _myComentariosResponse

    //Corrotinas

    fun listPublicacoes(){
        viewModelScope.launch {
            try {
                val response = repository.listPublicacoes()
                _myPublicacoesResponse.value = response

            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun addPublicacoes(publicacao: Publicacoes){
        viewModelScope.launch{
            try {
                repository.addPublicacoes(publicacao)
                listPublicacoes()
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun updatePublicacoes(id: Long, publicacao: Publicacoes){
        viewModelScope.launch{
            try {
                repository.updatePublicacoes(id, publicacao)
                listPublicacoes()
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun deletePublicacoes(id: Long){
        viewModelScope.launch{
            try {
                repository.deletePublicacoes(id)
                listPublicacoes()
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun listComentarios(id: Long){
        viewModelScope.launch {
            try {
                val response = repository.listComentarios(id)
                _myComentariosResponse.value = response

            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun addComentarios(id: Long, comentario: Comentario){
        viewModelScope.launch{
            try {
                repository.addComentarios(id, comentario)
                listPublicacoes()
                listComentarios(id)
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun updateComentarios(id: Long, id2: Long, comentario: Comentario){
        viewModelScope.launch{
            try {
                repository.updateComentarios(id, id2, comentario)
                listPublicacoes()
                listComentarios(id)
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun deleteComentarios(id: Long, id2: Long){
        viewModelScope.launch{
            try {
                repository.deleteComentarios(id, id2)
                listPublicacoes()
                listComentarios(id)
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun createReacao(id: Long, reacao: Reacao){
        viewModelScope.launch{
            try {
                repository.createReacao(id, reacao)
                listPublicacoes()
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun updateReacao(id: Long, id2: Long, reacao: Reacao){
        viewModelScope.launch{
            try {
                repository.updateReacao(id, id2, reacao)
                listPublicacoes()
            }catch (e:Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

//fim da viewModel
}

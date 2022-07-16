package com.generation.mycode

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentPassosBinding
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo
import com.generation.mycode.viewmodel.RoomViewModel

class PassosFragment : Fragment() {

    private lateinit var binding: FragmentPassosBinding
    private var contador = 0
    private val roomViewModel : RoomViewModel by activityViewModels()

    private var listPassosExibir: MutableList<Passo> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPassosBinding.inflate(layoutInflater,container,false)

        carregarDados()

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_passosFragment_to_biblioteca)
        }


        binding.proximoButton.setOnClickListener{
            proximoPasso()
        }

        binding.anteriorButton.setOnClickListener{
            voltarPasso()
        }
        listPassosExibir.forEach {

        }


        return binding.root
    }



    fun carregarDados(){
        roomViewModel.selectPassos.observe(viewLifecycleOwner){
            listPassosExibir = mutableListOf()
            Log.d("Lista", "id metodo selecionado ${roomViewModel.idMetodoSelecionado}")
            it.forEach{
                if (it.idMetodo == roomViewModel.idMetodoSelecionado){
                    listPassosExibir.add(it)
                    Log.d("Lista", "id método room ${it.idMetodo}")
                }
            }
            binding.textNome.text = "Passo: \n" +
                    "\n${contador+1}- ${listPassosExibir[contador].nome}"
            binding.textDescricao.text = "Descrição: ${listPassosExibir[contador].descricao}"
            binding.textCodigo.text = "Código: \n\n${listPassosExibir[contador].codigo}"
            binding.textContador.text = "${contador+1}/${listPassosExibir.size}"

        }
    }

    private fun proximoPasso(){
        try {
            if (contador < (listPassosExibir.size-1)){
                contador++
                carregarDados()
            }
        }catch (e: Exception){
            Log.d("Erro", e.message.toString())
        }

    }

    private fun voltarPasso(){
        if (contador > 0){
            contador--
            carregarDados()
        }
    }
}
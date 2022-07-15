package com.generation.mycode

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentPassosBinding
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo

class PassosFragment : Fragment() {

    private lateinit var binding: FragmentPassosBinding
    private var contador = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPassosBinding.inflate(layoutInflater,container,false)

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_passosFragment_to_biblioteca)
        }

        /*
        binding.proximoButton.setOnClickListener{
            proximoPasso()
        }

        binding.anteriorButton.setOnClickListener{
            voltarPasso()
        }

        carregarDaddos()*/

        return binding.root
    }




    private var listMetodos = listOf<Metodo>(
        Metodo(1,
            "Activity",
            "Método para navegar entre activitys",

        ),
        Metodo(2,
            "RecyclerView",
            "Método para navegar entre activitys",

        ),
        Metodo(3,
            "BottomNavigation",
            "Método para navegar entre activitys",

        )
    )

    private var metodo = listMetodos[1]



    /*
    private fun carregarDaddos(){
        binding.textNome.text = "Passo: \n" +
                "\n${contador+1}- ${metodo.passos[contador].nome}"
        binding.textDescricao.text = "Descrição: ${metodo.passos[contador].descricao}"
        binding.textCodigo.text = "Código: \n\n${metodo.passos[contador].codigo}"
        binding.textContador.text = "${contador+1}/${metodo.passos.size}"
    }

    private fun proximoPasso(){
        try {
            if (contador < (metodo.passos.size-1)){
                contador++
                carregarDaddos()
            }
        }catch (e: Exception){
            Log.d("Erro", e.message.toString())
        }

    }

    private fun voltarPasso(){
        if (contador > 0){
            contador--
            carregarDaddos()
        }
    }*/
}
package com.generation.mycode

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo
import com.generation.mycode.databinding.FragmentCadastrarPassosBinding
import com.generation.mycode.model.PassoTemporario
import com.generation.mycode.viewmodel.RoomViewModel
import com.google.android.material.snackbar.Snackbar


class CadastrarPassos : Fragment() {

    private lateinit var binding: FragmentCadastrarPassosBinding
    private val roomViewModel : RoomViewModel by activityViewModels()

    var listPasso: MutableList<PassoTemporario> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCadastrarPassosBinding.inflate(layoutInflater, container, false)

        binding.btnVoltar2.setOnClickListener {
            findNavController().navigate(R.id.action_cadastrarPassos2_to_cadastrarMetodos2)
        }
        binding.btnAddPassos.setOnClickListener{
            inserirPasso()
        }
        binding.btnAddMetodo.setOnClickListener{
            verficarCampos()
            inserirNoBanco(it)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun inserirPasso(){
        val nomePasso = binding.inputNomePasso.text.toString()
        val descricaoPasso = binding.inputDescricaoPassos.text.toString()
        val codigo = binding.inputCodigo.text.toString()
        listPasso.add(PassoTemporario(nomePasso,descricaoPasso,codigo))
        limparCampos()
    }
    var repetido = false
    private fun inserirNoBanco(view: View){
        var listMetodos = listOf<Metodo>()

            roomViewModel.addMetodo(Metodo(0, roomViewModel.createNomeMetodo.toString(), roomViewModel.createDescricaoMetodo.toString()))
            roomViewModel.selectMetodo.observe(viewLifecycleOwner){

                listMetodos = it

                var id: Long = 0
                listMetodos.forEach {
                    if(it.nome == roomViewModel.createNomeMetodo){
                        id = it.id
                        if (id > 0){
                            listPasso.forEach {
                                roomViewModel.addPassos(Passo(0, id, it.nome, it.descricao, it.codigo))
                            }
                            val snackbar = Snackbar.make(view, "Criado com Sucesso!", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.setTextColor(Color.WHITE)
                            snackbar.show()
                        }
                    }
                }

        }
    }

    private fun limparCampos(){
        binding.inputNomePasso.setText("")
        binding.inputDescricaoPassos.setText("")
        binding.inputCodigo.setText("")
    }

    private fun verficarCampos(){
        if(binding.inputNomePasso.text.isEmpty()
            &&binding.inputDescricaoPassos.text.isEmpty()
            &&binding.inputCodigo.text.isEmpty()){

        }else{
            inserirPasso()
        }
    }
}
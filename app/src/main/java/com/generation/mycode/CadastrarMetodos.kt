package com.generation.mycode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentCadastrarMetodosBinding
import com.generation.mycode.viewmodel.RoomViewModel


class CadastrarMetodos : Fragment() {

    private lateinit var binding: FragmentCadastrarMetodosBinding
    private val roomViewModel : RoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrarMetodosBinding.inflate(layoutInflater, container, false)

        binding.btnProximo.setOnClickListener {
            roomViewModel.createNomeMetodo = binding.inputNomeMetodo.text.toString()
            roomViewModel.createDescricaoMetodo = binding.inputDescricao.text.toString()
            findNavController().navigate(R.id.action_cadastrarMetodos2_to_cadastrarPassos2)
        }
        binding.btnVoltar2.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}
package com.generation.mycode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentCadastrarMetodosBinding


class CadastrarMetodos : Fragment() {

    private lateinit var binding: FragmentCadastrarMetodosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastrarMetodosBinding.inflate(layoutInflater, container, false)

        binding.btnProximo.setOnClickListener {
            findNavController().navigate(R.id.action_cadastrarMetodos2_to_cadastrarPassos2)
        }

        return binding.root
    }


}
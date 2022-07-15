package com.generation.mycode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentCadastrarPassosBinding


class CadastrarPassos : Fragment() {

    private lateinit var binding: FragmentCadastrarPassosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCadastrarPassosBinding.inflate(layoutInflater, container, false)

        binding.btnVoltar2.setOnClickListener {
            findNavController().navigate(R.id.action_cadastrarPassos2_to_cadastrarMetodos2)
        }

        return binding.root
    }

}
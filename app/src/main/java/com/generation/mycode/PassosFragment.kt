package com.generation.mycode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.generation.mycode.databinding.FragmentPassosBinding

class PassosFragment : Fragment() {

    private lateinit var binding: FragmentPassosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPassosBinding.inflate(layoutInflater,container,false)

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_passosFragment_to_biblioteca)
        }
        return binding.root
    }


}
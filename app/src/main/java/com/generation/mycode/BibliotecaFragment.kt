package com.generation.mycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.generation.mycode.databinding.BibliotecaFragmentBinding

class BibliotecaFragment : Fragment() {

private lateinit var binding: BibliotecaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = BibliotecaFragmentBinding.inflate(layoutInflater,container,false)

        return binding.root
    }
}
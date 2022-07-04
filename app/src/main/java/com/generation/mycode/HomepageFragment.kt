package com.generation.mycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.generation.mycode.adapter.PublicacoesAdapter
import com.generation.mycode.databinding.HomepageFragmentBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes

class HomepageFragment : Fragment() {

    private lateinit var binding: HomepageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = HomepageFragmentBinding.inflate(layoutInflater,container,false)

        val listPublicacoes = listOf<Publicacoes>(
            Publicacoes(1,
                "Kotlin",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam molestie rutrum felis non ultricies. Nulla.",
                "Maicon Anjos",
                10,
                2,
                emptyList<Comentario>()
            ),
            Publicacoes(2,
                "Android",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dignissim, sem vitae pellentesque commodo, mi ligula rhoncus ante, eu bibendum.",
                "Paulo Antonio",
                15,
                1,
                emptyList<Comentario>()
            )

        )

        val adapter = PublicacoesAdapter()
        binding.recyclerPublicacoes.layoutManager = LinearLayoutManager(context)
        binding.recyclerPublicacoes.adapter = adapter
        binding.recyclerPublicacoes.setHasFixedSize(true)

        adapter.setList(listPublicacoes)

        return binding.root
    }

}
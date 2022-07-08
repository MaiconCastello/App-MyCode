package com.generation.mycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.generation.mycode.adapter.ComentariosPublicacaoAdapter
import com.generation.mycode.databinding.BibliotecaFragmentBinding
import com.generation.mycode.databinding.ComentariosPublicacaoBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao

class ComentariosPublicacaoFragment : Fragment() {

    private lateinit var binding: ComentariosPublicacaoBinding

    val listPublicacoes = listOf<Publicacoes>(
        Publicacoes(1,
            "Kotlin",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. https://github.com/MaiconCastello/App-MyCode, Etiam molestie rutrum felis non ultricies. Nulla. ",
            "Maicon Anjos",
            10,
            2,
            mutableListOf<Reacao>(),
            mutableListOf<Comentario>(
                Comentario( "Beatriz Campos",
                    "Maiconnnn, me nota!"
                ),
                Comentario( "Gustavo Buoro",
                    "É isso ai meu mano <3"
                )
            )
        ),
        Publicacoes(2,
            "Android",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dignissim, sem vitae pellentesque commodo, mi ligula rhoncus ante, eu bibendum. " +
                    "www.facebook.com",
            "Paulo Antonio",
            15,
            1,
            mutableListOf<Reacao>(),
            mutableListOf<Comentario>(
                Comentario( "Fulano",
                    "Achei muito legal a sua publicação, me identifico com tudo que você disse, e gostaria de aprofundar meus conhecimentos nessa área, alguma dica?"
                ),
                Comentario( "Ciclano",
                    "Queria escrever códigos como você! kkkrying :')"
                )
            )
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = ComentariosPublicacaoBinding.inflate(layoutInflater,container,false)

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_comentariosPublicacaoFragment_to_homepage)
        }

        val adapter = ComentariosPublicacaoAdapter()
        binding.recyclerComentariosPublicacao.layoutManager = LinearLayoutManager(context)
        binding.recyclerComentariosPublicacao.adapter = adapter
        binding.recyclerComentariosPublicacao.setHasFixedSize(true)

        adapter.setList(listPublicacoes[1].comentario)

        return binding.root
    }

}
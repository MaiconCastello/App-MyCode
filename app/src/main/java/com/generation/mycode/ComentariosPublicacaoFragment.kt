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
            "imagem",
            "1NMWNdErkgMXxhnhj7DH3XhVPHl1",
            10,
            2,
            mutableListOf<Comentario>(
                Comentario( 1,"Beatriz Campos",
                    "Maiconnnn, me nota!"
                ),
                Comentario( 2,"Gustavo Buoro",
                    "É isso ai meu mano <3"
                )
            ),
            mutableListOf<Reacao>(
                Reacao(1,"kJdvMnrAKCOvN5ZNWlAcewyEeuO2","good")
            )
        ),
        Publicacoes(2,
            "Android",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dignissim, sem vitae pellentesque commodo, mi ligula rhoncus ante, eu bibendum. " +
                    "www.facebook.com",
            "imagem",
            "kJdvMnrAKCOvN5ZNWlAcewyEeuO2",
            15,
            1,
            mutableListOf<Comentario>(
                Comentario( 3,"Beatriz Campos",
                    "Maiconnnn, me nota!"
                ),
                Comentario( 4,"Gustavo Buoro",
                    "É isso ai meu mano <3"
                )
            ),
            mutableListOf<Reacao>(
                Reacao(2,"kJdvMnrAKCOvN5ZNWlAcewyEeuO2","good")
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
package com.generation.mycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.generation.mycode.adapter.PublicacoesAdapter
import com.generation.mycode.adapter.PublicacoesClickListener
import com.generation.mycode.databinding.HomepageFragmentBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes

class HomepageFragment : Fragment(), PublicacoesClickListener {

    private lateinit var binding: HomepageFragmentBinding

    val listPublicacoes = listOf<Publicacoes>(
        Publicacoes(1,
            "Kotlin",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. https://github.com/MaiconCastello/App-MyCode, Etiam molestie rutrum felis non ultricies. Nulla. ",
            "Maicon Anjos",
            10,
            2,
            "",
            listOf<Comentario>(
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
            "",
            listOf<Comentario>(
                Comentario( "Fulano",
                    "Achei muito legal a sua publicação"
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
        binding = HomepageFragmentBinding.inflate(layoutInflater,container,false)



        val adapter = PublicacoesAdapter(this)
        binding.recyclerPublicacoes.layoutManager = LinearLayoutManager(context)
        binding.recyclerPublicacoes.adapter = adapter
        binding.recyclerPublicacoes.setHasFixedSize(true)

        adapter.setList(listPublicacoes)

        return binding.root
    }

    override fun onPublicacoesClickListenerGood(id: Long) {
        //Método do Botão Good
       if(listPublicacoes[id.toInt()].reacao == ""){
           listPublicacoes[id.toInt()].good++
           listPublicacoes[id.toInt()].reacao = "good"
       }else{
            if(listPublicacoes[id.toInt()].reacao != "good"){
                listPublicacoes[id.toInt()].bad--
                listPublicacoes[id.toInt()].good++
                listPublicacoes[id.toInt()].reacao = "good"
            }else{
                listPublicacoes[id.toInt()].good--
                listPublicacoes[id.toInt()].reacao = ""
            }
       }

    }

    override fun onPublicacoesClickListenerBad(id: Long) {
        //Método de botão Bad
        if(listPublicacoes[id.toInt()].reacao == ""){
            listPublicacoes[id.toInt()].bad++
            listPublicacoes[id.toInt()].reacao = "bad"
        }else{
            if(listPublicacoes[id.toInt()].reacao != "bad"){
                listPublicacoes[id.toInt()].bad++
                listPublicacoes[id.toInt()].good--
                listPublicacoes[id.toInt()].reacao = "bad"
            }else{
                listPublicacoes[id.toInt()].bad--
                listPublicacoes[id.toInt()].reacao = ""
            }
        }
    }

    override fun onPublicacoesClickListenerComentarios() {
        //Método de botão Comentário
        //MVVM publicacao Selecionada → para exibir os comentários da API
        findNavController().navigate(R.id.action_homepage_to_comentariosPublicacaoFragment)
    }

}
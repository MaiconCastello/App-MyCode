package com.generation.mycode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.generation.mycode.adapter.PublicacoesAdapter
import com.generation.mycode.adapter.PublicacoesClickListener
import com.generation.mycode.databinding.HomepageFragmentBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
import com.generation.mycode.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class HomepageFragment : Fragment(), PublicacoesClickListener {

    private lateinit var binding: HomepageFragmentBinding
    private lateinit var userArrayList : ArrayList<Usuario>
    private lateinit var db: FirebaseFirestore
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid

    val listPublicacoes = listOf<Publicacoes>(
        Publicacoes(1,
            "Kotlin",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. https://github.com/MaiconCastello/App-MyCode, Etiam molestie rutrum felis non ultricies. Nulla. ",
            "1NMWNdErkgMXxhnhj7DH3XhVPHl1",
            10,
            2,
            mutableListOf<Reacao>(
                Reacao("kJdvMnrAKCOvN5ZNWlAcewyEeuO2","good")
            ),
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
            "kJdvMnrAKCOvN5ZNWlAcewyEeuO2",
            15,
            1,
            mutableListOf<Reacao>(),
            mutableListOf<Comentario>(
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



        val adapter = PublicacoesAdapter(this, requireContext())
        userArrayList = mutableListOf<Usuario>() as ArrayList<Usuario>
        carregarListaUsuarios()
        binding.recyclerPublicacoes.layoutManager = LinearLayoutManager(context)
        binding.recyclerPublicacoes.adapter = adapter
        binding.recyclerPublicacoes.setHasFixedSize(true)


        adapter.setListUsuario(userArrayList)
        adapter.setList(listPublicacoes)


        //EventChangeListener()

        return binding.root
    }

    /*
    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("Usuários").
                addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null){

                            Log.e("Firestore Error", error.message.toString())
                            return

                        }

                        for(dc: DocumentChange in value?.documentChanges!!){

                            if (dc.type == DocumentChange.Type.ADDED){

                                userArrayList.add(dc.document.toObject(Usuario::class.java))

                            }

                        }

                        binding.recyclerPublicacoes.adapter!!.notifyDataSetChanged()

                    }

                })

    }

     */

    private fun carregarListaUsuarios() {
        Log.d("FirebaseUsuario","Entrou na função")
        db = FirebaseFirestore.getInstance()

        listPublicacoes.forEach {
            db.collection("Usuários").document(it.usuario)
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        var nomeUsuario = documento.getString("nome")
                        var imagem = documento.getString("imagem")
                        Log.d("FirebaseUsuario","$nomeUsuario , $imagem")
                        userArrayList.add(
                            Usuario(
                                it.usuario,
                                imagem.toString(),
                                nomeUsuario.toString()
                            )
                        )
                        binding.recyclerPublicacoes.adapter!!.notifyDataSetChanged()
                        Log.d("FirebaseUsuario","$userArrayList")
                    }

                    Log.d("FirebaseUsuario","$error")

                }
        }
    }

    override fun onPublicacoesClickListenerGood(id: Long) {
        //Método do Botão Good
        Log.d("good","Entrou na lógica")
        var controle = listPublicacoes[id.toInt()].reacao.size
                if(controle == 0){
                    listPublicacoes[id.toInt()].reacao.add(Reacao(usuarioUid.toString(),""))
                    Log.d("good","Vetor estava vázio")
                }

        var contem = false
        listPublicacoes[id.toInt()].reacao.forEach{

            if(it.idUsuario == usuarioUid){
                contem = true
            }
        }
        if (contem){

            Log.d("good","Vetor não vazio, e usuário encontrado")

        }else{
            listPublicacoes[id.toInt()].reacao.add(Reacao(usuarioUid.toString(),""))
            Log.d("good","Vetor não vazio, mas n continha reação do usuário")
        }




       listPublicacoes[id.toInt()].reacao.forEach {
           if (it.idUsuario == usuarioUid ){
               if(it.reacao.isEmpty()){
                   listPublicacoes[id.toInt()].good++
                   it.reacao = "good"
               }else{
                   if(it.reacao != "good"){
                       listPublicacoes[id.toInt()].bad--
                       listPublicacoes[id.toInt()].good++
                       it.reacao = "good"
                   }else{
                       listPublicacoes[id.toInt()].good--
                       it.reacao = ""
                   }
               }

               Log.d("good","${it.reacao}")

           }

       }


    }

    override fun onPublicacoesClickListenerBad(id: Long) {
        //Método de botão Bad

        Log.d("good","Entrou na lógica")
        var controle = listPublicacoes[id.toInt()].reacao.size
        if(controle == 0){
            listPublicacoes[id.toInt()].reacao.add(Reacao(usuarioUid.toString(),""))
            Log.d("good","Vetor estava vázio")
        }

        var contem = false
        listPublicacoes[id.toInt()].reacao.forEach{

            if(it.idUsuario == usuarioUid){
                contem = true
            }
        }
        if (contem){

            Log.d("good","Vetor não vazio, e usuário encontrado")

        }else{
            listPublicacoes[id.toInt()].reacao.add(Reacao(usuarioUid.toString(),""))
            Log.d("good","Vetor não vazio, mas n continha reação do usuário")
        }

        listPublicacoes[id.toInt()].reacao.forEach {
            if (it.idUsuario == usuarioUid ){
                if(it.reacao.isEmpty()){
                    listPublicacoes[id.toInt()].bad++
                    it.reacao = "bad"
                }else{
                    if(it.reacao != "bad"){
                        listPublicacoes[id.toInt()].bad++
                        listPublicacoes[id.toInt()].good--
                        it.reacao = "bad"
                    }else{
                        listPublicacoes[id.toInt()].bad--
                        it.reacao = ""
                    }
                }

                Log.d("good","${it.reacao}")
            }

        }
    }

    override fun onPublicacoesClickListenerComentarios() {
        //Método de botão Comentário
        //MVVM publicacao Selecionada → para exibir os comentários da API
        findNavController().navigate(R.id.action_homepage_to_comentariosPublicacaoFragment)
    }

}
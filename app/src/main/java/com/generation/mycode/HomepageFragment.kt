package com.generation.mycode

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class HomepageFragment : Fragment(), PublicacoesClickListener {

    private lateinit var binding: HomepageFragmentBinding
    private lateinit var userArrayList : ArrayList<Usuario>
    private lateinit var db: FirebaseFirestore
    private val mainviewmodel : MainViewModel by activityViewModels()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid
    private val adapter: PublicacoesAdapter by lazy {
        PublicacoesAdapter(this, requireContext(), mainviewmodel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = HomepageFragmentBinding.inflate(layoutInflater,container,false)
        mainviewmodel.listPublicacoes()

        userArrayList = mutableListOf<Usuario>() as ArrayList<Usuario>
        carregarListaUsuarios()

        binding.recyclerPublicacoes.layoutManager = LinearLayoutManager(context)
        binding.recyclerPublicacoes.adapter = adapter
        binding.recyclerPublicacoes.setHasFixedSize(true)

        setAdapter()
        adapter.setListUsuario(userArrayList)
        //adapter.setList(listPublicacoes)

        binding.conteudoPublicacao.setOnClickListener {
            if (usuarioUid != null){
                mainviewmodel.publicacaoSelecionada = null
                findNavController().navigate(R.id.action_homepage_to_novaPublicacaoFragment)
            }else{
                val snackbar = Snackbar.make(it, "Faça Login para poder publicar!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }

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

        mainviewmodel.myPublicacoesResponse.observe(viewLifecycleOwner){
            if (it.body() != null){
                val listPublicacoes = it.body()!!
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
            }
    }

    override fun onPublicacoesClickListenerGood(id: Long) {
        TODO("Not yet implemented")
    }

    override fun onPublicacoesClickListenerBad(id: Long) {
        TODO("Not yet implemented")
    }

    /*
    override fun onPublicacoesClickListenerGood(id: Long) {
        //Método do Botão Good
        Log.d("good","Entrou na lógica")
        var controle = listPublicacoes[id.toInt()].reacao.size
                if(controle == 0){
                    listPublicacoes[id.toInt()].reacao.add(Reacao(0,usuarioUid.toString(),""))
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
            listPublicacoes[id.toInt()].reacao.add(Reacao(0,usuarioUid.toString(),""))
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
            listPublicacoes[id.toInt()].reacao.add(Reacao(0,usuarioUid.toString(),""))
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
            listPublicacoes[id.toInt()].reacao.add(Reacao(0,usuarioUid.toString(),""))
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

     */

    override fun onPublicacoesClickListenerComentarios(view: View ,publicacoes: Publicacoes) {
        //Método de botão Comentário
        //MVVM publicacao Selecionada → para exibir os comentários da API
        if(usuarioUid != null) {
            mainviewmodel.publicacaoSelecionada = publicacoes
            findNavController().navigate(R.id.action_homepage_to_comentariosPublicacaoFragment)
        }else{
            val snackbar = Snackbar.make(view, "Faça Login para poder comentar!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onPublicacoesClickListenerEdit(view:View, publicacoes: Publicacoes) {
        if(usuarioUid != null){

            if(usuarioUid == publicacoes.usuario){
                mainviewmodel.publicacaoSelecionada = publicacoes
                findNavController().navigate(R.id.action_homepage_to_novaPublicacaoFragment)
            }else{
                val snackbar = Snackbar.make(view, "Não é possível alterar a publicação de outro usuário", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }else{
            val snackbar = Snackbar.make(view, "Não é possível alterar a publicação de outro usuário", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onPublicacoesClickListenerDelete(view: View, id: Long, publicacoes: Publicacoes) {
        if(usuarioUid == publicacoes.usuario){
        AlertDialog.Builder(context)
            .setTitle("Excluir publicação")
            .setMessage("Deseja excluir?")
            .setPositiveButton("Sim"){
                    _,_-> deletePublicacao(id)
            }
            .setNegativeButton("Não"){
                    _,_ ->
            }.show()
        }else{
            val snackbar = Snackbar.make(view, "Não é possível excluir a publicação de outro usuário", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }

/*

        mainviewmodel.listPublicacoes()
        mainviewmodel.myPublicacoesResponse.observe(viewLifecycleOwner) { response ->
            if (response.body() != null) {
                adapter.setList(response.body()!!)
            }
        }

 */
    }

    private fun setAdapter(){
        Log.d("Retrofit", "Entrou no método")
        mainviewmodel.myPublicacoesResponse.observe(viewLifecycleOwner){
            response ->
            Log.d("Retrofit", response.body().toString())
            if(response.body() != null){
                adapter.setList(response.body()!!)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        recuperarDados()
    }

    private fun recuperarDados(){

        if (usuarioUid != null) {
            db.collection("Usuários").document(usuarioUid)
                .addSnapshotListener { documento, error ->
                    if (documento != null){
                        Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_baseline_image_24).into(binding.imagePerfil)
                    }
                }
        }
    }
    private fun deletePublicacao(id: Long){
        mainviewmodel.deletePublicacoes(id)
        mainviewmodel.deletePublicacoes(id)
    }
}
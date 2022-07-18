package com.generation.mycode

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.generation.mycode.adapter.PublicacoesAdapter
import com.generation.mycode.adapter.PublicacoesClickListener
import com.generation.mycode.databinding.HomepageFragmentBinding
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
import com.generation.mycode.model.Usuario
import com.generation.mycode.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class HomepageFragment : Fragment(), PublicacoesClickListener, SearchView.OnQueryTextListener {

    private lateinit var binding: HomepageFragmentBinding
    private lateinit var userArrayList : ArrayList<Usuario>
    private lateinit var db: FirebaseFirestore
    private val mainviewmodel : MainViewModel by activityViewModels()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid
    private val adapter: PublicacoesAdapter by lazy {
        PublicacoesAdapter(this, requireContext(), mainviewmodel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        val swipe: SwipeRefreshLayout = binding.swipeRefreshLayout
        swipe.setOnRefreshListener {
            mainviewmodel.listPublicacoes()
            setAdapter()
            adapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menu != null){
            inflater.inflate(R.menu.toolbar_homepage,menu)

            val search = menu?.findItem(R.id.app_bar_search)
            val searchView = search?.actionView as? SearchView
            searchView?.isSubmitButtonEnabled =true
            searchView?.setOnQueryTextListener(this)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null && query != ""){
            searchCategoria(query)
            setAdapter()
        }else{
            mainviewmodel.listPublicacoes()
            setAdapter()
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null && query!= ""){
            searchCategoria(query)
            setAdapter()
        }else{
            mainviewmodel.listPublicacoes()
            setAdapter()
        }
        return true
    }

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
                                        nomeUsuario.toString(),
                                        descricao = "",
                                        linkedin = "",
                                        gitHub = ""
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

    override fun onPublicacoesClickListenerGood(view: View, publicacoes: Publicacoes) {
        if(usuarioUid != null) {
            mainviewmodel.publicacaoSelecionada = publicacoes
            var novaReacao = true
            var updateReacao: Reacao? = null
            publicacoes.reacao.forEach{
                if(usuarioUid == it.idUsuario){
                    novaReacao = false
                    updateReacao = it
                }
            }
            var reacao = Reacao(0,usuarioUid.toString(), "good")
            if(updateReacao?.reacao == "good"){
                reacao = Reacao(updateReacao!!.id,usuarioUid.toString(),"")
                mainviewmodel.updateReacao(publicacoes.id, reacao.id, reacao)
            }else{
                if(novaReacao){
                    mainviewmodel.createReacao(publicacoes.id,reacao)
                }else{
                    reacao = Reacao(updateReacao!!.id,usuarioUid.toString(),"good")
                    mainviewmodel.updateReacao(publicacoes.id, reacao.id, reacao)
                }
            }
        }else{
            val snackbar = Snackbar.make(view, "Faça Login para reagir a publicação!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onPublicacoesClickListenerBad(view: View, publicacoes: Publicacoes) {
        if(usuarioUid != null) {
            mainviewmodel.publicacaoSelecionada = publicacoes
            var novaReacao = true
            var updateReacao: Reacao? = null
            publicacoes.reacao.forEach{
                if(usuarioUid == it.idUsuario){
                    novaReacao = false
                    updateReacao = it
                }
            }
            var reacao = Reacao(0,usuarioUid.toString(), "bad")
            if(updateReacao?.reacao == "bad"){
                reacao = Reacao(updateReacao!!.id,usuarioUid.toString(),"")
                mainviewmodel.updateReacao(publicacoes.id, reacao.id, reacao)
            }else{
                if(novaReacao){
                    mainviewmodel.createReacao(publicacoes.id,reacao)
                }else{
                    reacao = Reacao(updateReacao!!.id,usuarioUid.toString(),"bad")
                    mainviewmodel.updateReacao(publicacoes.id, reacao.id, reacao)
                }
            }
        }else{
            val snackbar = Snackbar.make(view, "Faça Login para reagir a publicação!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onPublicacoesClickListener(idPublicacao: Long, view: View, idUsuario: String) {
        if (idUsuario.isNotEmpty()) {
            mainviewmodel.usuarioSelecionado = idUsuario
            findNavController().navigate(R.id.action_homepage_to_perfilUsuario)
        } else {
            val snackbar =
                Snackbar.make(view, "Erro ao acessar este perfil!!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onPublicacoesClickListenerComentarios(view: View ,publicacoes: Publicacoes) {
        //Método de botão Comentário
        //MVVM publicacao Selecionada → para exibir os comentários da API
        if(usuarioUid != null) {
            mainviewmodel.listPublicacoes()
            carregarListaUsuarios()
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
        try {
            if (usuarioUid != null) {
                db.collection("Usuários").document(usuarioUid)
                .addSnapshotListener { documento, error ->
                    if (documento != null){
                        Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_user).into(binding.imagePerfil)
                    }
                }
            }
        }catch (e: Exception){
            Log.d("Errodb", "Erro ao recuperar dados do firebase")
        }
    }

    private fun deletePublicacao(id: Long){
        mainviewmodel.deletePublicacoes(id)
        //Coloquei 2x o comando pois estranhamente se eu colocar apenas uma ele exclui da API porém não limpa a tela, tentei umas 5 formas de lógica e não consegui resolver o problema sem ser dessa forma
        mainviewmodel.deletePublicacoes(id)
    }

    private fun searchCategoria(categoria: String){
        mainviewmodel.searchCategoria(categoria)
    }

}
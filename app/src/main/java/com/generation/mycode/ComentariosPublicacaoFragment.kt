package com.generation.mycode

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.generation.mycode.adapter.ComentariosClickListener
import com.generation.mycode.adapter.ComentariosPublicacaoAdapter
import com.generation.mycode.databinding.ComentariosPublicacaoBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Usuario
import com.generation.mycode.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ComentariosPublicacaoFragment : Fragment(), ComentariosClickListener {

    private lateinit var binding: ComentariosPublicacaoBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var userArrayList : ArrayList<Usuario>
    private val mainviewmodel : MainViewModel by activityViewModels()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid
    private val adapter: ComentariosPublicacaoAdapter by lazy {
        ComentariosPublicacaoAdapter(requireContext(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = ComentariosPublicacaoBinding.inflate(layoutInflater,container,false)
        mainviewmodel.listComentarios(mainviewmodel.publicacaoSelecionada!!.id)

        userArrayList = mutableListOf<Usuario>() as ArrayList<Usuario>
        carregarListaUsuarios()
        adapter.setListUsuario(userArrayList)

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_comentariosPublicacaoFragment_to_homepage)
        }
        binding.buttonAdicionarComentario.setOnClickListener{
            findNavController().navigate(R.id.action_comentariosPublicacaoFragment_to_novoComentarioFragment)
        }

        setAdapter()
        binding.recyclerComentariosPublicacao.layoutManager = LinearLayoutManager(context)
        binding.recyclerComentariosPublicacao.adapter = adapter
        binding.recyclerComentariosPublicacao.setHasFixedSize(true)


        return binding.root
    }

    private fun setAdapter(){
        Log.d("Retrofit", "Entrou no método")
        mainviewmodel.myComentariosResponse.observe(viewLifecycleOwner){
                response ->
            Log.d("Retrofit", response.body().toString())
            if(response.body() != null){
                adapter.setList(response.body()!!.comentario)
            }
        }
    }

    private fun carregarListaUsuarios() {
        Log.d("FirebaseUsuario","Entrou na função")
        db = FirebaseFirestore.getInstance()

        mainviewmodel.myPublicacoesResponse.observe(viewLifecycleOwner){
            if (it.body() != null){
                var listPublicacoes = it.body()!!
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
                                binding.recyclerComentariosPublicacao.adapter!!.notifyDataSetChanged()
                                Log.d("FirebaseUsuario","$userArrayList")
                            }
                            Log.d("FirebaseUsuario","$error")
                        }
                }
            }
        }
    }

    override fun onComentariosClickListenerEdit(view: View, comentario: Comentario) {
        if(usuarioUid != null){
            if(usuarioUid == comentario.usuario){
                mainviewmodel.comentarioSelecionado = comentario
                findNavController().navigate(R.id.action_comentariosPublicacaoFragment_to_novoComentarioFragment)
            }else{
                val snackbar = Snackbar.make(view, "Não é possível alterar o comentário de outro usuário", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }else{
            val snackbar = Snackbar.make(view, "Não é possível alterar o comentário de outro usuário", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    override fun onComentariosClickListenerDelete(view: View, comentario: Comentario) {
        if(usuarioUid == comentario.usuario || usuarioUid == mainviewmodel.publicacaoSelecionada?.usuario){
            AlertDialog.Builder(context)
                .setTitle("Excluir comentário")
                .setMessage("Deseja excluir?")
                .setPositiveButton("Sim"){
                        _,_-> deleteComentario(mainviewmodel.publicacaoSelecionada!!.id, comentario.id)
                }
                .setNegativeButton("Não"){
                        _,_ ->
                }.show()
        }else{
            val snackbar = Snackbar.make(view, "Não é possível excluir o comentário de outro usuário", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    private fun deleteComentario (id: Long, id2: Long) {
        mainviewmodel.deleteComentarios(id, id2)
        mainviewmodel.deleteComentarios(id, id2)
    }

}
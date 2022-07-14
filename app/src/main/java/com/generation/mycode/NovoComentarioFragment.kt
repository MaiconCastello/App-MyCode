package com.generation.mycode

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.NovoComentarioFragmentBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NovoComentarioFragment: Fragment() {
    private lateinit var binding: NovoComentarioFragmentBinding
    private lateinit var db: FirebaseFirestore
    private val mainviewmodel : MainViewModel by activityViewModels()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid
    private var usuario = ""
    private var comentarioSelecionado: Comentario? = null
    private var botaoComentar = "Comentar"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = NovoComentarioFragmentBinding.inflate(layoutInflater,container,false)
        carregarDados()

        db = FirebaseFirestore.getInstance()

        binding.btnComentar.setOnClickListener{
            if(comentarioSelecionado != null){
                atualizarNoBanco(it)
            }else{
                inserirNoBanco(it)
            }
        }

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_novoComentarioFragment_to_comentariosPublicacaoFragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        carregarDadosPerfil()
    }

    private fun carregarDadosPerfil(){
        if (usuarioUid != null) {
            db.collection("Usuários").document(usuarioUid)
                .addSnapshotListener { documento, error ->
                    if (documento != null){
                        Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_user).into(binding.imagePerfil)
                        binding.textUsuario.text = documento.getString("nome")
                        usuario = documento.getString("nome").toString()
                    }
                }
        }
    }

    private fun carregarDados(){
        comentarioSelecionado = mainviewmodel.comentarioSelecionado
        if (comentarioSelecionado != null){
            binding.comentarioText.setText(comentarioSelecionado?.descricao)
            botaoComentar = "Alterar Comentário"
        }
        binding.btnComentar.setText(botaoComentar)
    }

    private fun verificarCampos(
        conteudo:String
    ): Boolean{
        return!(conteudo == "" || conteudo.length < 3 || conteudo.length > 250)
    }

    private fun inserirNoBanco(view: View){
        val conteudo = binding.comentarioText.text.toString()
        val id = mainviewmodel.publicacaoSelecionada!!.id
        if(verificarCampos(conteudo)){
            val comentario = Comentario(0, usuarioUid.toString(), conteudo)
            mainviewmodel.addComentarios(id, comentario)

            findNavController().navigate(R.id.action_novoComentarioFragment_to_comentariosPublicacaoFragment)
        }else{
            val snackbar = Snackbar.make(view, "Preencha corretamente os campos obrigatórios (*)", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    private fun atualizarNoBanco(view: View){
        val conteudo = binding.comentarioText.text.toString()
        val id = mainviewmodel.publicacaoSelecionada!!.id
        if(verificarCampos(conteudo)){
            val comentario = Comentario(0, usuarioUid.toString(), conteudo)
            mainviewmodel.updateComentarios(id, comentarioSelecionado!!.id, comentario)
            findNavController().navigate(R.id.action_novoComentarioFragment_to_comentariosPublicacaoFragment)
        }else{
            val snackbar = Snackbar.make(view, "Preencha corretamente os campos obrigatórios (*)", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

}
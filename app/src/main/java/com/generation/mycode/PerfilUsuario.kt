package com.generation.mycode

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.FragmentPerfilUsuarioBinding
import com.generation.mycode.model.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class PerfilUsuario : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentPerfilUsuarioBinding
    private val db = FirebaseFirestore.getInstance()
    private var usuarioSelecionado: String? = null
    private val usuarios = "Usuários"
    private var linkedin = ""
    private var github = ""
    private var site = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilUsuarioBinding.inflate(layoutInflater, container, false)

        binding.btnVoltar.setOnClickListener{
            viewModel.usuarioSelecionado = null
            findNavController().navigate(R.id.action_perfilUsuario_to_homepage2)
        }

        binding.linkedinPerfil.setOnClickListener {
            navLink(linkedin, it)
        }
        binding.githubPerfil.setOnClickListener {
            navLink(github, it)
        }
        binding.sitePerfil.setOnClickListener {
            navLink(site, it)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        recuperarDados()
    }

    @SuppressLint("SetTextI18n")
    private fun recuperarDados(){
        usuarioSelecionado = viewModel.usuarioSelecionado
        var id = viewModel.usuarioSelecionado
        if (usuarioSelecionado != null) {
            db.collection(usuarios).document(id.toString())
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        Glide.with(this).load(documento.getString("imagem"))
                            .placeholder(R.drawable.ic_baseline_image_24).into(binding.imgPerfil)
                        binding.nomePerfil.text = documento.getString("nome")
                        binding.descricaoPerfil.text = documento.getString("descrição")
                        linkedin = documento.getString("linkedin").toString()
                        github = documento.getString("gitHub").toString()
                        site = documento.getString("site").toString()
                        Toast.makeText(context, "Sucesso ao recuperar os dados", Toast.LENGTH_LONG)
                    } else {
                        Toast.makeText(context, "Erro ao recuperar os dados", Toast.LENGTH_LONG)
                    }
                }
        }
    }

    private fun navLink(link: String, view: View){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)

        }catch (e: Exception){
            val snackbar = Snackbar.make(view, "Erro ao acessar o link!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }

    }



}
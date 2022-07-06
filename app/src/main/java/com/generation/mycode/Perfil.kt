package com.generation.mycode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class Perfil : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private val db = FirebaseFirestore.getInstance()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)

        recuperarDados()

        return binding.root
    }


    private fun recuperarDados(){

        db.collection("Usuários").document(usuarioUid)
            .addSnapshotListener { documento, error ->
                if (documento != null){
                    Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_baseline_image_24).into(binding.imgPerfil)
                    binding.nomePerfil.text = documento.getString("nome")
                    binding.descricaoPerfil.text = documento.getString("descrição")
                    Toast.makeText(requireContext(), "Sucesso ao recuperar os dados", Toast.LENGTH_LONG)
                }else{
                    Toast.makeText(requireContext(), "Erro ao recuperar os dados", Toast.LENGTH_LONG)
                }
            }
    }


}
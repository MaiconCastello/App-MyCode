package com.generation.mycode

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.NovaPublicacaoFragmentBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Reacao
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NovaPublicacaoFragment: Fragment() {

    private lateinit var binding: NovaPublicacaoFragmentBinding
    private lateinit var db: FirebaseFirestore
    private var publicacaoSeleciona: Publicacoes? = null
    private val mainviewmodel : MainViewModel by activityViewModels()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser?.uid
    private var urlImg = ""
    private var botaoPublicar = "Publicar"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = NovaPublicacaoFragmentBinding.inflate(layoutInflater,container,false)

        carregarDados()

        db = FirebaseFirestore.getInstance()

        binding.btnAddImg.setOnClickListener{
            addImg()
        }

        binding.btnPublicar.setOnClickListener{
            if(publicacaoSeleciona != null){
                atualizarNoBanco(it)
            }else{
                inserirNoBanco(it)
            }
        }

        binding.voltarButton.setOnClickListener{
            findNavController().navigate(R.id.action_novaPublicacaoFragment_to_homepage)
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
                        Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_baseline_image_24).into(binding.imagePerfil)
                        binding.textUsuario.text = documento.getString("nome")
                    }
                }
        }

    }

    private fun addImg(){
        val popUpImg: AlertDialog.Builder = AlertDialog.Builder(context)
        popUpImg.setTitle("URL de imagem")
        popUpImg.setIcon(R.drawable.ic_baseline_add_link_24)

        val inputImg = EditText(context)
        inputImg.hint = "  Link da imagem do perfil  "
        inputImg.setBackgroundColor(Color.argb(94, 223, 240, 240))
        inputImg.inputType = InputType.TYPE_CLASS_TEXT
        popUpImg.setView(inputImg)

        popUpImg.setPositiveButton("OK") { _, _ ->
            urlImg = inputImg.text.toString()
        }
        popUpImg.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popUpImg.show()
    }

    private fun verificarCampos(
        categoria:String,
        conteudo:String
    ): Boolean{
    return!(
            (categoria == "" || categoria.length < 3 || categoria.length > 25) ||
                    (conteudo == "" || conteudo.length < 3 || conteudo.length > 450)
        )
    }

    private fun inserirNoBanco(view: View){
        val categoria = binding.textCategoria.text.toString()
        val conteudo = binding.publicacaoText.text.toString()
        val imagem = urlImg
        val usuario = usuarioUid.toString()
        if(verificarCampos(categoria,conteudo)){
            val publicacao = Publicacoes(0,categoria,conteudo,imagem,usuario,0,0, mutableListOf<Comentario>(),
                mutableListOf<Reacao>())
            mainviewmodel.addPublicacoes(publicacao)

            findNavController().navigate(R.id.action_novaPublicacaoFragment_to_homepage)
        }else{
            val snackbar = Snackbar.make(view, "Preencha corretamente os campos obrigatórios (*)", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }

    private fun carregarDados(){
        publicacaoSeleciona = mainviewmodel.publicacaoSelecionada
        if (publicacaoSeleciona != null){
            binding.textCategoria.setText(publicacaoSeleciona?.categoria)
            binding.publicacaoText.setText(publicacaoSeleciona?.conteudo)
            urlImg = publicacaoSeleciona?.imagem.toString()
            botaoPublicar = "Alterar Publicação"
        }
        binding.btnPublicar.setText(botaoPublicar)
    }

    private fun atualizarNoBanco(view: View){
        val categoria = binding.textCategoria.text.toString()
        val conteudo = binding.publicacaoText.text.toString()
        val imagem = urlImg
        val usuario = usuarioUid.toString()
        if(verificarCampos(categoria,conteudo)){
            val publicacao = Publicacoes(0,categoria,conteudo,imagem,usuario,0,0, mutableListOf<Comentario>(),
                mutableListOf<Reacao>())
            mainviewmodel.updatePublicacoes(publicacaoSeleciona!!.id, publicacao)

            findNavController().navigate(R.id.action_novaPublicacaoFragment_to_homepage)
        }else{
            val snackbar = Snackbar.make(view, "Preencha corretamente os campos obrigatórios (*)", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }
    }
}
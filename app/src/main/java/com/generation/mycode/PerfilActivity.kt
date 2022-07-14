package com.generation.mycode

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.parseIntent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.ActivityPerfilBinding
import com.generation.mycode.view.form.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private val db = FirebaseFirestore.getInstance()
    private val user = Firebase.auth.currentUser!!
    private val usuarioUid = FirebaseAuth.getInstance().currentUser!!.uid
    private val usuarios = "Usuários"
    private var linkedin = ""
    private var github = ""
    private var site = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.linkedinPerfil.setOnClickListener {
            navLink(linkedin, it)
        }
        binding.githubPerfil.setOnClickListener {
            navLink(github, it)
        }
        binding.sitePerfil.setOnClickListener {
            navLink(site, it)
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnEditPerfil.setOnClickListener {
            nav()
        }

        binding.btnDeletPerfil.setOnClickListener {
            deletConta(it)
        }


    }

    override fun onStart() {
        super.onStart()
        recuperarDados()
    }

    private fun recuperarDados(){

        db.collection(usuarios).document(usuarioUid)
            .addSnapshotListener { documento, error ->
                if (documento != null){
                    Glide.with(this).load(documento.getString("imagem")).placeholder(R.drawable.ic_baseline_image_24).into(binding.imgPerfil)
                    binding.nomePerfil.text = documento.getString("nome")
                    binding.descricaoPerfil.text = documento.getString("descrição")
                    linkedin = documento.getString("linkedin").toString()
                    github = documento.getString("gitHub").toString()
                    site = documento.getString("site").toString()
                    Toast.makeText(this, "Sucesso ao recuperar os dados", Toast.LENGTH_LONG)
                }else{
                    Toast.makeText(this, "Erro ao recuperar os dados", Toast.LENGTH_LONG)
                }
            }

    }

    private fun navLink(link: String, view: View){
        try {
            val intent = Intent(ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
            finish()

        }catch (e: Exception){
            val snackbar = Snackbar.make(view, "Erro ao acessar o link!", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }

    }

    private fun nav(){
        val intent = Intent(this, EditPerfil::class.java)
        startActivity(intent)
    }

    private fun deletConta(view: View){
        val popDeletConta: AlertDialog.Builder = AlertDialog.Builder(this)
        popDeletConta.setTitle("Deseja deletar a sua conta?")
        popDeletConta.setIcon(R.drawable.ic_baseline_delete_24)
        popDeletConta.setPositiveButton("OK") { _, _ ->
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("authD", "Usuário deletado do firebase.")
                    }else{
                        Log.d("errodelet","Erro: $task.")
                    }
                }
            db.collection(usuarios).document(usuarioUid)
                .delete().addOnCompleteListener {
                    val snackbar = Snackbar.make(view, "Conta deletada com sucesso!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.GREEN)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                    deslogar()
                }
        }
        popDeletConta.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popDeletConta.show()


    }
    private fun deslogar(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}
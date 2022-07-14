package com.generation.mycode

import android.app.AlertDialog
import android.app.appsearch.StorageInfo
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.generation.mycode.databinding.ActivityEditPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditPerfilBinding
    private val db = FirebaseFirestore.getInstance()
    private val usuarioUid = FirebaseAuth.getInstance().currentUser!!.uid
    private var urlImg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEditImg.setOnClickListener {
            addImg()
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val descricao = binding.editDescricao.text.toString()
            val linkedin = binding.editLinkedin.text.toString()
            val github = binding.editGithub.text.toString()
            val site = binding.editSite.text.toString()
            editarDados(nome, descricao, linkedin, github, site)
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        recuperarDados()
    }

    private fun editarDados(nome: String, desc: String,
                            linkedin: String, github: String, site: String){
        db.collection("Usuários").document(usuarioUid)
            .update("imagem", urlImg,
                "nome", nome,
                "descrição", desc,
                "linkedin", linkedin,
                "gitHub", github,
                "site", site)

            .addOnCompleteListener {
                    Log.d("db_Uptade", "Sucesso ao atualizar os dados")
            }

    }




    private fun recuperarDados() {

        db.collection("Usuários").document(usuarioUid)
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    Glide.with(this).load(documento.getString("imagem"))
                        .placeholder(R.drawable.ic_baseline_image_24).into(binding.imgedit)
                    urlImg = documento.getString("imagem").toString()
                    binding.editNome.setText(documento.getString("nome"))
                    binding.editDescricao.setText(documento.getString("descrição"))
                    binding.editLinkedin.setText(documento.getString("linkedin"))
                    binding.editGithub.setText(documento.getString("gitHub"))
                    binding.editSite.setText(documento.getString("site"))
                    Toast.makeText(
                        this,
                        "Sucesso ao recuperar os dados",
                        Toast.LENGTH_LONG
                    )
                } else {
                    Toast.makeText(
                        this,
                        "Erro ao recuperar os dados",
                        Toast.LENGTH_LONG
                    )
                }
            }

    }



    private fun addImg(){
        val popUpImg: AlertDialog.Builder = AlertDialog.Builder(this)
        popUpImg.setTitle("URL de imagem")
        popUpImg.setIcon(R.drawable.ic_baseline_add_link_24)

        val inputImg = EditText(this)
        inputImg.hint = "  Link da imagem do perfil  "
        inputImg.setBackgroundColor(Color.argb(94, 223, 240, 240))
        inputImg.inputType = InputType.TYPE_CLASS_TEXT
        popUpImg.setView(inputImg)

        popUpImg.setPositiveButton("OK") { _, _ ->
            urlImg = inputImg.text.toString()
            Glide.with(this)
                .load(urlImg)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.imgedit)

        }
        popUpImg.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popUpImg.show()


    }



}
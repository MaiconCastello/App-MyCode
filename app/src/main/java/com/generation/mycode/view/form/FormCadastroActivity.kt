package com.generation.mycode.view.form

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import com.bumptech.glide.Glide
import com.generation.mycode.R
import com.generation.mycode.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastroActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFormCadastroBinding
    private var urlImg = ""
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var usuarioUid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputImg.setOnClickListener {
            addImg()
        }

        binding.btnCadastrarUsuario.setOnClickListener {   view->
            cadUsuario(view)

        }

        binding.btnVoltar.setOnClickListener{
            finish()
        }


    }


    private fun cadUsuario(view: View){
        val nome = binding.inputNome.text.toString()
        val email = binding.inputEmail.text.toString()
        val senha = binding.inputSenha.text.toString()
        val confiSenha = binding.inputConfSenha.text.toString()
        val desc = binding.inputDescricao.text.toString()
        val img = urlImg
        val linkedin = binding.inputLinkedin.text.toString()
        val gitHub = binding.inputGithub.text.toString()
        val site = binding.inputSite.text.toString()

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confiSenha.isEmpty()){
            val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()

        }else{
            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this){ cadastro ->

            if (cadastro.isSuccessful){
                usuarioUid = auth.uid.toString()
                salvarDB(img, nome, email, linkedin, gitHub, site, desc)
                val snackbar = Snackbar.make(view, "Cadastrado com sucesso", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                binding.inputEmail.setText("")
                binding.inputSenha.setText("")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener { exception ->
            val mensagemErro = when(exception){
                is FirebaseAuthWeakPasswordException -> "Digíte uma senha de no mínimo 6 caracteres!"
                is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido!"
                is FirebaseAuthUserCollisionException -> "Está conta já existe!"
                is FirebaseNetworkException -> "Sem conexão com a internet!"

                else -> "Erro ao cadastrar usuário"
            }

            val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }

        }

    }

    private fun salvarDB(imagem: String, nome: String, email: String, linkedin: String,
    github: String, site: String, desc: String){
            val usuariosMap = hashMapOf(
                "imagem" to imagem,
                "nome" to nome,
                "email" to email,
                "linkedin" to linkedin,
                "gitHub" to github,
                "site" to site,
                "descrição" to desc
            )
            db.collection("Usuários").document(usuarioUid)
                .set(usuariosMap).addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar os dados do usuários!")
                }.addOnFailureListener {
                    Log.d("dbErro", "Erro ao salvar no banco de dados!")
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
                .into(binding.inputImg)

        }
        popUpImg.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        popUpImg.show()


    }


}
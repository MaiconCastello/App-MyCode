package com.generation.mycode.view.form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.generation.mycode.MainActivity
import com.generation.mycode.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogar.setOnClickListener {
            val email = binding.inputLoginEmail.text.toString()
            val senha = binding.inputLoginSenha.text.toString()
            logar(email, senha, it)
        }


        binding.btnLogarAnonimo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.linkCadastrar.setOnClickListener {
            val intent = Intent(this, FormCadastroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if (usuarioAtual != null){
            navTelaPrincipal()
        }
    }


    private fun logar(email: String, senha: String, view: View){
        if (email.isEmpty() || senha.isEmpty()){
            val snackbar = Snackbar.make(view, "Preencha todos os Campos", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
        }else{
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autentificacao ->
                if (autentificacao.isSuccessful){
                    navTelaPrincipal()
                }

            }.addOnFailureListener {
                val snackbar = Snackbar.make(view, "Erro ao fazer login", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }
        }
    }

    private fun navTelaPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
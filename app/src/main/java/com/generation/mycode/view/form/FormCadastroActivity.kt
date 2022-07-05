package com.generation.mycode.view.form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.generation.mycode.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormCadastroActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrarUsuario.setOnClickListener {   view->
            val email = binding.inputEmail.text.toString()
            val senha = binding.inputSenha.text.toString()
            val confiSenha = binding.inputConfSenha.text.toString()

            if (validarCamp(email, senha, confiSenha, view)){
                cadUsuario(email, senha, view)

            }


        }
    }


    private fun validarCamp(email: String, senha: String, confiSenha: String, view: View): Boolean{
        if (email.isEmpty() || senha.isEmpty() || confiSenha.isEmpty()){
            val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setTextColor(Color.WHITE)
            snackbar.show()
            return false
        }else{
            return true
        }



    }

    private fun cadUsuario(email: String, senha: String, view: View){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this){ cadastro ->
            if (cadastro.isSuccessful){
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

        }.addOnFailureListener {

        }
    }
}
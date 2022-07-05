package com.generation.mycode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.generation.mycode.view.form.FormCadastroActivity
import com.generation.mycode.view.form.LoginActivity

class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 segundos
    }
}
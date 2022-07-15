
package com.generation.mycode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.generation.mycode.databinding.ActivityCadastroMetodoBinding
import com.generation.mycode.databinding.ActivityMainBinding

class CadastroMetodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroMetodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroMetodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
package com.generation.mycode

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.generation.mycode.databinding.ActivityMainBinding
import com.generation.mycode.view.form.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val auth = FirebaseAuth.getInstance().currentUser?.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerNavigation()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupWithNavController(bottomNavigationView, navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }



    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container_fragment, fragment)
            transaction.commit()
        }
    }

    private fun deslogar(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()


    }

    private fun navegarPerfil(){
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }
    private fun navegarMetodo(){
        val intent = Intent(this, CadastroMetodoActivity::class.java)
        startActivity(intent)
    }

    private fun drawerNavigation(){
        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.perfil -> {if(auth != null){
                        navegarPerfil()
                    }else{
                        val snackbar = Snackbar.make(navView, "Faça login para acessar o Perfil!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                    }
                    }
                    R.id.configuracoes -> {navegarMetodo()}
                    R.id.logout -> {deslogar()}
                }
                true
            }
        }
    }

}
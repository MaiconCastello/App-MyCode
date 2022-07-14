package com.generation.mycode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.generation.mycode.adapter.BibliotecaAdapter
import com.generation.mycode.adapter.BibliotecaClickListener
import com.generation.mycode.databinding.BibliotecaFragmentBinding
import com.generation.mycode.model.Metodo
import com.generation.mycode.model.Passo

class BibliotecaFragment : Fragment(), BibliotecaClickListener {

private lateinit var binding: BibliotecaFragmentBinding
private val adapter: BibliotecaAdapter by lazy {
    BibliotecaAdapter(this)
}

private var listMetodos = listOf<Metodo>(
    Metodo(1,
    "Activity",
    "Método para navegar entre activitys",
    mutableListOf<Passo>(
        Passo(
            1,
            "Declarar a variável intent e inicializar a nova activity",
            "Vá na activity principal do projeto e declare uma variável intent atribuindo a ela o valor da activity que você deseja iniciar",
            "val intent = Intent(this, ExampleActivity::class.java) \n " +
                    "startactivity(intent)"
        ),
        Passo(
            2,
            "Finalizar a activity e retornar a activity anterior",
            "Vá na classe da segunda activity e adicione um comando para finalizá-la",
            "val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)\n" +
                    "buttonFinalizar.setOnClickListener{\n" +
                    "finish()}"
        )
    )
    ),
    Metodo(2,
        "RecyclerView",
        "Método para navegar entre activitys",
        mutableListOf<Passo>(
            Passo(
                1,
                "Declarar a variável intent e inicializar a nova activity",
                "Vá na activity principal do projeto e declare uma variável intent atribuindo a ela o valor da activity que você deseja iniciar",
                "val intent = Intent(this, ExampleActivity::class.java) \n " +
                        "startactivity(intent)"
            ),
            Passo(
                2,
                "Finalizar a activity e retornar a activity anterior",
                "Vá na classe da segunda activity e adicione um comando para finalizá-la",
                "val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)\n" +
                        "buttonFinalizar.setOnClickListener{\n" +
                        "finish()}"
            )
        )
    ),
    Metodo(3,
        "BottomNavigation",
        "Método para navegar entre activitys",
        mutableListOf<Passo>(
            Passo(
                1,
                "Declarar a variável intent e inicializar a nova activity",
                "Vá na activity principal do projeto e declare uma variável intent atribuindo a ela o valor da activity que você deseja iniciar",
                "val intent = Intent(this, ExampleActivity::class.java) \n " +
                        "startactivity(intent)"
            ),
            Passo(
                2,
                "Finalizar a activity e retornar a activity anterior",
                "Vá na classe da segunda activity e adicione um comando para finalizá-la",
                "val buttonFinalizar = findViewById<Button>(R.id.buttonFinalizar)\n" +
                        "buttonFinalizar.setOnClickListener{\n" +
                        "finish()}"
            )
        )
    )
)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = BibliotecaFragmentBinding.inflate(layoutInflater,container,false)
        setAdapter()
        binding.recyclerBiblioteca.layoutManager = GridLayoutManager(context,2)
        binding.recyclerBiblioteca.adapter = adapter
        binding.recyclerBiblioteca.setHasFixedSize(true)



        return binding.root
    }

    private fun setAdapter(){
        adapter.setList(listMetodos)
    }

    override fun BibliotecaClickListener() {
        findNavController().navigate(R.id.action_biblioteca_to_passosFragment)
    }
}
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
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo

class BibliotecaFragment : Fragment(), BibliotecaClickListener {

private lateinit var binding: BibliotecaFragmentBinding
private val adapter: BibliotecaAdapter by lazy {
    BibliotecaAdapter(this)
}

private var listMetodos = listOf<Metodo>(
    Metodo(1,
    "Activity",
    "Método para navegar entre activitys",
        ),
    Metodo(2,
        "RecyclerView",
        "Método para navegar entre activitys",

    ),
    Metodo(3,
        "BottomNavigation",
        "Método para navegar entre activitys",
        
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
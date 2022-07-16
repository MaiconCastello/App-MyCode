package com.generation.mycode

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.generation.mycode.adapter.BibliotecaAdapter
import com.generation.mycode.adapter.BibliotecaClickListener
import com.generation.mycode.databinding.BibliotecaFragmentBinding
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo
import com.generation.mycode.viewmodel.RoomViewModel

class BibliotecaFragment : Fragment(), BibliotecaClickListener {

    private lateinit var binding: BibliotecaFragmentBinding
    private val adapter: BibliotecaAdapter by lazy {
        BibliotecaAdapter(this, roomViewModel)
    }

    private val roomViewModel: RoomViewModel by activityViewModels()
    private var listPassosExibir: MutableList<Passo> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        binding = BibliotecaFragmentBinding.inflate(layoutInflater, container, false)
        setAdapter()
        binding.recyclerBiblioteca.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerBiblioteca.adapter = adapter
        binding.recyclerBiblioteca.setHasFixedSize(true)



        return binding.root
    }

    private fun setAdapter() {
        roomViewModel.selectMetodo.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    override fun BibliotecaClickListener(idMetodo: Long) {
        roomViewModel.idMetodoSelecionado = idMetodo
        findNavController().navigate(R.id.action_biblioteca_to_passosFragment)
    }

    override fun BibliotecaClickListenerDelete(metodo: Metodo) {
        AlertDialog.Builder(context)
            .setTitle("Excluir publicação")
            .setMessage("Deseja excluir?")
            .setPositiveButton("Sim"){
                    _,_-> deleteMetodo(metodo)
            }
            .setNegativeButton("Não"){
                    _,_ ->
            }.show()
    }

    fun deleteMetodo(metodo: Metodo){
        roomViewModel.selectPassos.observe(viewLifecycleOwner) {
            listPassosExibir = mutableListOf()
            Log.d("Lista", "id metodo selecionado ${roomViewModel.idMetodoSelecionado}")
            it.forEach {
                if (it.idMetodo == metodo.id) {
                    listPassosExibir.add(it)
                    Log.d("Lista", "id método room ${it.idMetodo}")
                }
            }
            roomViewModel.deleteMetodo(metodo)
            listPassosExibir.forEach {
                roomViewModel.deletePassos(it)
            }
        }
    }
}

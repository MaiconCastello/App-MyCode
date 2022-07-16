package com.generation.mycode.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.generation.mycode.databinding.CardMetodosBinding
import com.generation.mycode.database.entities.Metodo
import com.generation.mycode.database.entities.Passo
import com.generation.mycode.viewmodel.RoomViewModel

class BibliotecaAdapter(
    private val bibliotecaClickListener: BibliotecaClickListener,
    private val roomViewModel: RoomViewModel
): RecyclerView.Adapter<BibliotecaAdapter.BibliotecaViewHolder>() {

    private var listMetodos = emptyList<Metodo>()


    class BibliotecaViewHolder(val binding: CardMetodosBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibliotecaViewHolder {
        return BibliotecaViewHolder(
            CardMetodosBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BibliotecaViewHolder, position: Int) {
        val metodo = listMetodos[position]
        holder.binding.nomeMetodo.text = metodo.nome
        holder.itemView.setOnClickListener {
            bibliotecaClickListener.BibliotecaClickListener(metodo.id)
        }
        holder.binding.btnDelete.setOnClickListener {
            bibliotecaClickListener.BibliotecaClickListenerDelete(metodo)
        }
    }

    override fun getItemCount(): Int {
        return listMetodos.size
    }

    fun setList(list: List<Metodo>){
        listMetodos =list.sortedByDescending { it.id }
        notifyDataSetChanged()
    }


}


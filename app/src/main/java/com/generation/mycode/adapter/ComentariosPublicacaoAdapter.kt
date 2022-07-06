package com.generation.mycode.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.generation.mycode.databinding.CardComentarioBinding
import com.generation.mycode.model.Comentario



class ComentariosPublicacaoAdapter (): RecyclerView.Adapter<ComentariosPublicacaoAdapter.ComentariosPublicacaoViewHolder>(){

    private var listComentarios = emptyList<Comentario>()

    class ComentariosPublicacaoViewHolder(val binding: CardComentarioBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentariosPublicacaoViewHolder {
        return ComentariosPublicacaoViewHolder(
            CardComentarioBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ComentariosPublicacaoViewHolder, position: Int) {
        val comentario = listComentarios[position]

        holder.binding.usuario.text = comentario.usuario
        holder.binding.conteudoComentario.text = comentario.descricao

    }

    override fun getItemCount(): Int {
        return listComentarios.size
    }

    fun setList(list: List<Comentario>){
        listComentarios =list
        notifyDataSetChanged()
    }

}
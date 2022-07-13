package com.generation.mycode.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generation.mycode.R
import com.generation.mycode.databinding.CardComentarioBinding
import com.generation.mycode.model.Comentario
import com.generation.mycode.model.Usuario


class ComentariosPublicacaoAdapter (
    val context: Context
        ): RecyclerView.Adapter<ComentariosPublicacaoAdapter.ComentariosPublicacaoViewHolder>(){

    private var listComentarios = emptyList<Comentario>()
    private var listUsuario = emptyList<Usuario>()

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

        listUsuario.forEach{
            if (it.id == comentario.usuario){
                holder.binding.usuario.text = "@${it.nome}"
                Glide.with(context).load(it.imagem).placeholder(R.drawable.ic_user).into(holder.binding.imagePerfil)
            }
        }
        //holder.binding.usuario.text = comentario.usuario
        holder.binding.conteudoComentario.text = comentario.descricao

    }

    override fun getItemCount(): Int {
        return listComentarios.size
    }

    fun setList(list: List<Comentario>){
        listComentarios =list
        notifyDataSetChanged()
    }

    fun setListUsuario(list: List<Usuario>){
        listUsuario =list
        notifyDataSetChanged()
    }

}
package com.generation.mycode.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.generation.mycode.databinding.CardHomepageBinding
import com.generation.mycode.model.Publicacoes

class PublicacoesAdapter (
    val publicacoesClickListener: PublicacoesClickListener
        ): RecyclerView.Adapter<PublicacoesAdapter.PublicacoesViewHolder>(){

    private var listPublicacoes = emptyList<Publicacoes>()

    class PublicacoesViewHolder(val binding: CardHomepageBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacoesViewHolder {
    return PublicacoesViewHolder(
        CardHomepageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )
    }

    override fun onBindViewHolder(holder: PublicacoesViewHolder, position: Int) {
        val publicacao = listPublicacoes[position]

        holder.binding.textUsuario.text = publicacao.usuario
        holder.binding.textCategoria.text = "#${publicacao.categoria}"
        holder.binding.conteudoPublicacao.text = publicacao.conteudo
        holder.binding.qtdComentarios.text = publicacao.comentario.toString()
        holder.binding.qtdGood.text = publicacao.good.toString()
        holder.binding.qtdBad.text = publicacao.bad.toString()
        holder.binding.qtdComentarios.text = publicacao.comentario.size.toString()

        val link: TextView

        link = holder.binding.conteudoPublicacao
        link.movementMethod = LinkMovementMethod.getInstance()
        link.setLinkTextColor(Color.CYAN)

        holder.binding.comentariosButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerComentarios()

        }
        holder.binding.goodButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerGood(position.toLong())
            notifyDataSetChanged()
        }
        holder.binding.badButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerBad(position.toLong())
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return listPublicacoes.size
    }

    fun setList(list: List<Publicacoes>){
        listPublicacoes =list
        notifyDataSetChanged()
    }

}
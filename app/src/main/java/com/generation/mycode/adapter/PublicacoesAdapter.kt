package com.generation.mycode.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generation.mycode.R
import com.generation.mycode.databinding.CardHomepageBinding
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext

class PublicacoesAdapter (
    val publicacoesClickListener: PublicacoesClickListener,
    val context: Context
        ): RecyclerView.Adapter<PublicacoesAdapter.PublicacoesViewHolder>(){

    private var listPublicacoes = emptyList<Publicacoes>()
    private var listUsuario = emptyList<Usuario>()
    private val db = FirebaseFirestore.getInstance()

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

        listUsuario.forEach{
            if (it.id == publicacao.usuario){
                holder.binding.textUsuario.text = it.nome
                Glide.with(context).load(it.imagem).placeholder(R.drawable.ic_user).into(holder.binding.imagePerfil)
            }
        }

        //holder.binding.textUsuario.text = publicacao.usuario


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

    fun setListUsuario(list: List<Usuario>){
        listUsuario =list
        notifyDataSetChanged()
    }



}
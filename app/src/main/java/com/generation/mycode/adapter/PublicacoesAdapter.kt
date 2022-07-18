package com.generation.mycode.adapter

import android.content.Context
import android.graphics.Color
import android.text.BoringLayout
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generation.mycode.viewmodel.MainViewModel
import com.generation.mycode.R
import com.generation.mycode.databinding.CardHomepageBinding
import com.generation.mycode.model.Publicacoes
import com.generation.mycode.model.Usuario

class PublicacoesAdapter (
    val publicacoesClickListener: PublicacoesClickListener,
    val context: Context,
    val mainViewModel: MainViewModel
        ): RecyclerView.Adapter<PublicacoesAdapter.PublicacoesViewHolder>(){

    private var listPublicacoes = emptyList<Publicacoes>()
    private var listUsuario = emptyList<Usuario>()


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
        //Adicionar um If(imagem !="")

        if (publicacao.imagem.isEmpty() || publicacao.imagem.isBlank()
            || !verificarUrl(publicacao.imagem)){
            holder.binding.imagePost.setImageResource(0)
            holder.binding.imagePost.layoutParams.height = 0
            holder.binding.imagePost.isInvisible
        }else{
            Glide.with(context).load(publicacao.imagem).placeholder(R.drawable.ic_baseline_image_24).into(holder.binding.imagePost)
            holder.binding.imagePost.layoutParams.height = 900
        }
        val link: TextView

        link = holder.binding.conteudoPublicacao
        link.movementMethod = LinkMovementMethod.getInstance()
        link.setLinkTextColor(Color.CYAN)

        holder.binding.imagePerfil.setOnClickListener {
            publicacoesClickListener.onPublicacoesClickListener(publicacao.id, it, publicacao.usuario)
        }

        holder.binding.textUsuario.setOnClickListener {
            publicacoesClickListener.onPublicacoesClickListener(publicacao.id, it, publicacao.usuario)

        }

        holder.binding.editButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerEdit(it, publicacao)
        }

        holder.binding.deleteButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerDelete(it, publicacao.id, publicacao)
        //showAlertDialog(publicacao.id)
        }

        holder.binding.comentariosButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerComentarios(it, publicacao)

        }
        holder.binding.goodButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerGood(it,publicacao)
            notifyDataSetChanged()
        }
        holder.binding.badButton.setOnClickListener{
            publicacoesClickListener.onPublicacoesClickListenerBad(it, publicacao)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listPublicacoes.size
    }

    fun setList(list: MutableList<Publicacoes>){
        listPublicacoes =list.sortedByDescending { it.id }
        notifyDataSetChanged()
    }

    fun setListUsuario(list: List<Usuario>){
        listUsuario =list
        notifyDataSetChanged()
    }

    fun verificarUrl (link: String): Boolean{
        val valid = Patterns.WEB_URL.matcher(link).matches()

        return valid
    }
}
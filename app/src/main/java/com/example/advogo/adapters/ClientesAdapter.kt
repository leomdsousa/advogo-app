package com.example.advogo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.advogo.databinding.ItemClienteBinding
import com.example.advogo.models.Cliente
import com.example.advogo.utils.constants.Constants


open class ClientesAdapter(
    private val context: Context,
    private var list: List<Cliente>,
    private val exibirIconeTelefone: Boolean = false,
    private val exibirIconeEmail: Boolean = false,
    private val exibirIconeWhatsapp: Boolean = false
): RecyclerView.Adapter<ClientesAdapter.MyViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    private val regexTelefone = Regex("(\\+55\\s?)?\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}")
    private val regexEmail = Regex("(\\+55\\s?)?\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}")

    inner class MyViewHolder(private val binding: ItemClienteBinding)
        : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Cliente, position: Int) {
            binding.apply {
                binding.tvNomeCliente.text = item.nome
                binding.tvTelefoneCliente.text = "Telefone: ${item.telefone}"
                binding.tvEmailCliente.text = "Email: ${item.email}"
                binding.tvEnderecoCliente.text = "Endereço: ${item.endereco}"

                if (item.selecionado == true) {
                    binding.ivSelectedCliente.visibility = View.VISIBLE
                } else {
                    binding.ivSelectedCliente.visibility = View.GONE
                }

                if(exibirIconeTelefone
                    && (item.telefone != null && regexTelefone.matches(item.telefone!!))
                ) {
                    binding.imageTelefone.visibility = View.VISIBLE
                    binding.imageTelefone.setOnClickListener { _ ->
                        val numeroTelefone = item.telefone!!
                        val uri: Uri = Uri.parse("tel:$numeroTelefone")
                        val intent = Intent(Intent.ACTION_DIAL, uri)
                        context.startActivity(intent)
                    }
                } else {
                    binding.imageTelefone.visibility = View.GONE
                }

                if(exibirIconeEmail
                    && (item.email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(item.email!!).matches())
                ) {
                    binding.imageEmail.visibility = View.VISIBLE
                    binding.imageEmail.setOnClickListener { _ ->
                        val enderecoEmail = item.email

                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$enderecoEmail")
                        }

                        context.startActivity(intent)
                    }
                } else {
                    binding.imageEmail.visibility = View.GONE
                }

                if( (exibirIconeWhatsapp && item.whatsapp == true)
                    && (item.telefone != null && regexTelefone.matches(item.telefone!!))
                ) {
                    binding.imageWpp.visibility = View.VISIBLE
                    binding.imageWpp.setOnClickListener { _ ->
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${item.telefone}")
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "WhatsApp não instalado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    binding.imageWpp.visibility = View.GONE
                }

                binding.root.setOnClickListener {
                    if(item.selecionado == true) {
                        onItemClickListener!!.onClick(item, position, Constants.DESELECIONAR)
                    } else {
                        onItemClickListener!!.onClick(item, position, Constants.SELECIONAR)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesAdapter.MyViewHolder {
        return MyViewHolder(
                ItemClienteBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientesAdapter.MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(cliente: Cliente, position: Int, acao: String? = null)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateList(newList: ArrayList<Cliente>) {
        list = newList
        notifyDataSetChanged()
    }
}
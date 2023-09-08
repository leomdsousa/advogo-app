package com.example.advogo.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advogo.adapters.ProcessosTiposAdapter
import com.example.advogo.databinding.DialogListBinding
import com.example.advogo.models.ProcessoTipo

abstract class ProcessoTiposDialog(
    context: Context,
    private val list: List<ProcessoTipo>
): Dialog(context) {
    private lateinit var binding: DialogListBinding
    private var adapter: ProcessosTiposAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        binding = DialogListBinding.inflate(layoutInflater)
        binding.tvTitle.text = "Selecione o tipo de Processo"

        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setupRecyclerView(binding.root)
    }

    private fun setupRecyclerView(view: View) {
        if (list.isNotEmpty()) {
            binding.rvList.layoutManager = LinearLayoutManager(context)
            adapter = ProcessosTiposAdapter(context, list)
            binding.rvList.adapter = adapter

            adapter!!.setOnItemClickListener(object :
                ProcessosTiposAdapter.OnItemClickListener {
                override fun onClick(item: ProcessoTipo, position: Int, action: String) {
                    dismiss()
                    onItemSelected(item, action)
                }
            })
        }
    }

    protected abstract fun onItemSelected(item: ProcessoTipo, action:String)
}
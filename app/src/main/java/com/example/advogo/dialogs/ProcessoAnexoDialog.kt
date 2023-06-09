package com.example.advogo.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advogo.adapters.ProcessosAdapter
import com.example.advogo.adapters.ProcessosStatusAndamentosAdapter
import com.example.advogo.adapters.ProcessosTiposAndamentosAdapter
import com.example.advogo.databinding.DialogListBinding
import com.example.advogo.databinding.DialogProcessoAndamentoBinding
import com.example.advogo.databinding.DialogProcessoAnexoBinding
import com.example.advogo.models.*
import com.example.advogo.repositories.IProcessoStatusAndamentoRepository
import com.example.advogo.repositories.IProcessoTipoAndamentoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ProcessoAnexoDialog(
    context: Context,
    private var anexo: Anexo,
    private val binding: DialogProcessoAnexoBinding
): Dialog(context) {
    //private lateinit var binding: DialogProcessoAnexoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        //binding = DialogProcessoAnexoBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setDados(anexo)

//        binding = DialogProcessoAnexoBinding
//                        .bind(findViewById<ViewGroup>(android.R.id.content)
//                        .getChildAt(0))

        binding.btnSelecionarArquivo.setOnClickListener {
            onChooseFile()
        }

        binding.btnSubmitProcessoAnexo.setOnClickListener {
            dismiss()

            var anexo = Anexo(
                id = anexo.id,
                nome = anexo.nome,
                uri = anexo.uri,
                descricao = binding.etDescricaoAnexo.text.toString()
            )

            onSubmit(anexo)
        }
    }

    private fun setDados(anexo: Anexo) {
        if(anexo.id.isBlank()) {
            binding.tvTitle.text = "Cadastro Anexo"
            binding.btnSubmitProcessoAnexo.text = "Cadastrar"
        } else {
            binding.tvTitle.text = "Detalhes Anexo"
            binding.btnSubmitProcessoAnexo.text = "Atualizar"

            binding.etDescricaoAnexo.setText(anexo.descricao)
        }
    }

    protected abstract fun onChooseFile()
    protected abstract fun onSubmit(anexo: Anexo)
}
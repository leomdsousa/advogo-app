package com.example.advogo.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advogo.activities.DiligenciaCadastroActivity
import com.example.advogo.activities.DiligenciaDetalheActivity
import com.example.advogo.adapters.DiligenciasAdapter
import com.example.advogo.databinding.FragmentDiligenciasBinding
import com.example.advogo.models.Diligencia
import com.example.advogo.repositories.IDiligenciaRepository
import com.example.advogo.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DiligenciasFragment : BaseFragment() {
    private lateinit var binding: FragmentDiligenciasBinding
    @Inject lateinit var diligenciaRepository: IDiligenciaRepository

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiligenciasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabDiligenciaCadastro.setOnClickListener {
            val intent = Intent(binding.root.context, DiligenciaCadastroActivity::class.java)
            intent.putExtra(Constants.FROM_DILIGENCIA_ACTIVITY, Constants.FROM_DILIGENCIA_ACTIVITY)
            resultLauncher.launch(intent)
        }

        diligenciaRepository.ObterDiligencias(
            { diligencias -> setDiligenciasToUI(diligencias as ArrayList<Diligencia>) },
            { null } //TODO("Implementar")
        )

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data!!.hasExtra(Constants.FROM_DILIGENCIA_ACTIVITY)) {
                    diligenciaRepository.ObterDiligencias(
                        { lista -> setDiligenciasToUI(lista as ArrayList<Diligencia>) },
                        { ex -> null } //TODO("Imlementar OnFailure")
                    )
                }
            } else {
                Log.e("Cancelado", "Cancelado")
            }
        }
    }

    private fun setDiligenciasToUI(lista: ArrayList<Diligencia>) {
        //TODO("hideProgressDialog()")

        CoroutineScope(Dispatchers.Main).launch {
            if(lista.size > 0) {
                binding.rvDiligenciasList.visibility = View.VISIBLE
                binding.tvNoDiligenciasEncontrado.visibility = View.GONE

                binding.rvDiligenciasList.layoutManager = LinearLayoutManager(binding.root.context)
                binding.rvDiligenciasList.setHasFixedSize(true)

                val adapter = DiligenciasAdapter(binding.root.context, lista)
                binding.rvDiligenciasList.adapter = adapter

                adapter.setOnItemClickListener(object :
                    DiligenciasAdapter.OnItemClickListener {
                    override fun onClick(diligencia: Diligencia, position: Int) {
                        val intent = Intent(binding.root.context, DiligenciaDetalheActivity::class.java)
                        intent.putExtra(Constants.DILIGENCIA_PARAM, diligencia)
                        startActivity(intent)
                    }
                })

            } else {
                binding.rvDiligenciasList.visibility = View.GONE
                binding.tvNoDiligenciasEncontrado.visibility = View.VISIBLE
            }
        }
    }

//    calendarView.setOnDateChangedListener { widget, date, selected ->
//        // Obter eventos para a data selecionada
//        val events = getEventsForDate(date)
//
//        // Exibir eventos no ListView
//        eventsListView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, events)
//    }

//    fun getEventsForDate(date: Date): List<String> {
//        // Conectar-se à base de dados MongoDB
//        val mongoClient = MongoClient("localhost", 27017)
//        val database = mongoClient.getDatabase("myDatabase")
//        val collection = database.getCollection("events")
//
//        // Executar consulta para obter eventos para o mês selecionado
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//        val firstDayOfMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
//        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
//        val query = and(
//            gte("dueDate", firstDayOfMonth),
//            lte("dueDate", lastDayOfMonth)
//        )
//        val events = mutableListOf<String>()
//        collection.find(query).forEach {
//            events.add(it["description"].toString())
//        }
//
//        return events
//    }
}
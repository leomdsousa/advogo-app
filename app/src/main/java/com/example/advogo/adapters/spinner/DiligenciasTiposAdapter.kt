package com.example.advogo.adapters.spinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.advogo.R
import com.example.advogo.models.DiligenciaTipo

open class DiligenciasTiposAdapter(
    context: Context,
    private val lista: List<DiligenciaTipo>
) : ArrayAdapter<DiligenciaTipo>(context, R.layout.spinner_item_layout, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val diligenciaTipo = getItem(position)
        diligenciaTipo?.let {
            val descricaoTextView = view.findViewById<TextView>(R.id.text1)
            descricaoTextView.text = it.tipo
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val diligenciaTipo = getItem(position)
        diligenciaTipo?.let {
            val descricaoTextView = view.findViewById<TextView>(R.id.text1)
            descricaoTextView.text = it.tipo
        }
        return view
    }
}
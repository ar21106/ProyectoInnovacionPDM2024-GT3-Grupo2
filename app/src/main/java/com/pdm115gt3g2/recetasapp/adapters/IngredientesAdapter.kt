package com.pdm115gt3g2.recetasapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdm115gt3g2.recetasapp.R
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes

class IngredientesAdapter(private var mList: List<Ingredientes>) : RecyclerView.Adapter<IngredientesAdapter.ViewHolder>() {
    class ViewHolder(ingredienteView: View): RecyclerView.ViewHolder(ingredienteView) {
        val txtid: TextView = ingredienteView.findViewById(R.id.txt_ingrediente_id)
        val txtidReceta: TextView = ingredienteView.findViewById(R.id.txt_ingrediente_idReceta)
        val txtuMedida: TextView = ingredienteView.findViewById(R.id.txt_ingrediente_umedida)
        val txtnombre: TextView = ingredienteView.findViewById(R.id.txt_ingrediente_nombre)
        val txtcantidad: TextView = ingredienteView.findViewById(R.id.txt_ingrediente_cantidad)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_ingrediente,parent,false)
        return IngredientesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paso = mList[position]

        holder.txtid.text = paso.idIngrediente.toString()
        holder.txtidReceta.text = paso.idReceta.toString()
        holder.txtuMedida.text = paso.uMedida
        holder.txtnombre.text = paso.nombre
        holder.txtcantidad.text = paso.cantidad.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateItems(newItems: List<Ingredientes>) {
        mList = newItems
        notifyDataSetChanged()
    }
}
package com.pdm115gt3g2.recetasapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.pdm115gt3g2.recetasapp.R
import com.pdm115gt3g2.recetasapp.RecetaActivity
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

class RecetasAdapter(private var mList: List<Recetas>) : RecyclerView.Adapter<RecetasAdapter.ViewHolder>() {
    class ViewHolder(recetaView: View): RecyclerView.ViewHolder(recetaView), View.OnClickListener {
        val txtId: TextView = recetaView.findViewById(R.id.txt_receta_id)
        val txtNombre: TextView = recetaView.findViewById(R.id.txt_receta_nombre)
        val txtDescripcion: TextView = recetaView.findViewById(R.id.txt_receta_descripcion)
        val txtFavorito: TextView = recetaView.findViewById(R.id.txt_receta_favorito)

        init {
            recetaView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = v.context
            val intent = Intent(context, RecetaActivity::class.java)
            val bundle = bundleOf(
                "id" to txtId.text,
                "nombre" to txtNombre.text,
                "descripcion" to txtDescripcion.text,
                "favorito" to txtFavorito.text
            )
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_receta,parent,false)
        return RecetasAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receta = mList[position]

        var fav = ""
        if (receta.esFavorito) fav = "Favoritos"

        holder.txtId.text = receta.idReceta.toString()
        holder.txtNombre.text = receta.nombre
        holder.txtDescripcion.text = receta.descripcion
        holder.txtFavorito.text = fav
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateItems(newItems: List<Recetas>) {
        mList = newItems
        notifyDataSetChanged()
    }
}
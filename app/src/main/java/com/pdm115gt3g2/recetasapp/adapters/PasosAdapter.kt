package com.pdm115gt3g2.recetasapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.pdm115gt3g2.recetasapp.R
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos

class PasosAdapter(private var mList: List<Pasos>) : RecyclerView.Adapter<PasosAdapter.ViewHolder>(){
    class ViewHolder(pasoView: View): RecyclerView.ViewHolder(pasoView), View.OnClickListener {
        val txtid: TextView = pasoView.findViewById(R.id.txt_paso_id)
        val txtidReceta: TextView = pasoView.findViewById(R.id.txt_paso_idReceta)
        val txtTitulo: TextView = pasoView.findViewById(R.id.txt_paso_titulo)
        val txtDescripcion: TextView = pasoView.findViewById(R.id.txt_paso_descripcion)
        val txtTiempoTexto: TextView = pasoView.findViewById(R.id.txt_paso_tiempo)
        val txtTiempoEntero: TextView = pasoView.findViewById(R.id.txt_paso_tiempo_entero)
        val txtCompletado: TextView = pasoView.findViewById(R.id.txt_paso_completado)

        init {
            pasoView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val bundle = bundleOf(
                "id" to txtid.text,
                "idReceta" to txtidReceta.text,
                "titulo" to txtTitulo.text,
                "descripcion" to txtDescripcion.text,
                "tiempoEntero" to txtTiempoEntero.text,
                "tiempoText" to txtTiempoTexto.text,
                "completado" to txtCompletado.text
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_paso,parent,false)
        return PasosAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paso = mList[position]
        var fav = ""

        holder.txtid.text = paso.idPaso.toString()
        holder.txtidReceta.text = paso.idReceta.toString()
        holder.txtTitulo.text = paso.titulo
        holder.txtDescripcion.text = paso.descripcion
        holder.txtTiempoEntero.text = paso.tiempoSegundos.toString()

        holder.txtTiempoTexto.text = getTiempoTxt(paso.tiempoSegundos)

        if(paso.completado) fav = "Completado"
        holder.txtCompletado.text = fav
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getTiempoTxt(seg: Int): String{
        val minutos: Int = seg / 60
        val segundos: Int = seg - (minutos*60)
        var minutosTxt: String = ""
        var segundosTxt: String = ""

        if (minutos > 0) minutosTxt = "$minutos minutos "
        if (segundos > 0) segundosTxt = "$segundos segundos "
        if (minutos == 1) minutosTxt = "$minutos minuto "
        if (segundos == 1) segundosTxt = "$segundos segundo "

        val tiempo = minutosTxt + segundosTxt
        return tiempo
    }

    fun updateItems(newItems: List<Pasos>) {
        mList = newItems
        notifyDataSetChanged()
    }

}
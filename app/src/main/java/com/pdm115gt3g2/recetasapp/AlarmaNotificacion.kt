package com.pdm115gt3g2.recetasapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmaNotificacion:BroadcastReceiver() {

    lateinit var titulo: String
    lateinit var textoCorto: String

    override fun onReceive(context: Context, intent: Intent) {
        val parametros = intent.extras
        titulo = parametros?.getString("titulo")?:""
        textoCorto = "Se ha acabado el tiempo !"
        programarNotificacion(context)
    }

    companion object{
        const val id_notificacion = 1
        const val id_channel = "RecetasApp"
    }

    private fun programarNotificacion(context: Context) {

        val notificacion = NotificationCompat.Builder(context, id_channel)
            .setSmallIcon(R.drawable.timer_play)
            .setContentTitle(titulo)
            .setContentText(textoCorto)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id_notificacion, notificacion)

    }
}
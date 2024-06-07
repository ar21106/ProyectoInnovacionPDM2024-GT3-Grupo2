package com.pdm115gt3g2.recetasapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf

class AlarmaNotificacion:BroadcastReceiver() {

    lateinit var titulo: String
    lateinit var textoCorto: String

    override fun onReceive(context: Context, intent: Intent) {
        val parametros = intent.extras
        titulo = parametros?.getString("titulo")?:""
        textoCorto = "Se ha acabado el tiempo !"
        programarNotificacion(context, parametros)
    }

    companion object{
        const val id_notificacion = 1
        const val id_channel = "RecetasApp"
    }

    private fun programarNotificacion(context: Context, parametros: Bundle?) {

        //abrir la vista de paso
        val intent = Intent(context, VerPasoActivity::class.java)
        parametros?.putString("completado", "Completado")
        intent.putExtras(parametros ?: bundleOf())

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificacion = NotificationCompat.Builder(context, id_channel)
            .setSmallIcon(R.drawable.timer_play)
            .setContentTitle(titulo)
            .setContentText(textoCorto)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id_notificacion, notificacion)

    }
}
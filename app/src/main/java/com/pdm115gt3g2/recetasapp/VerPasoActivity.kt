package com.pdm115gt3g2.recetasapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pdm115gt3g2.recetasapp.db.RecetasAppDb
import com.pdm115gt3g2.recetasapp.db.Repositorio
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos

class VerPasoActivity : AppCompatActivity() {

    private lateinit var idPaso: TextView
    private lateinit var idReceta: TextView
    private lateinit var tiempoEntero: TextView
    private lateinit var titulo: TextView
    private lateinit var descripcion: TextView
    private lateinit var tiempoTexto: TextView
    private lateinit var completado: CheckBox
    private lateinit var btnLeer: Button
    private lateinit var btnTiempo: Button

    lateinit var db: RecetasAppDb
    lateinit var repositorio: Repositorio

    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_paso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idPaso = findViewById(R.id.txt_paso_id_form)
        idReceta = findViewById(R.id.txt_paso_idReceta_form)
        tiempoEntero = findViewById(R.id.txt_paso_tiempoEntero_form)
        titulo = findViewById(R.id.txt_paso_titulo_form)
        descripcion = findViewById(R.id.txt_paso_descripcion_form)
        tiempoTexto = findViewById(R.id.txt_paso_tiempoTexto_form)
        completado = findViewById(R.id.checkbox_paso_completado)
        btnLeer = findViewById(R.id.btn_leer_paso)
        btnTiempo = findViewById(R.id.btn_paso_tiempo)

        //parametros
        val parametros = intent.extras
        val idTmp = parametros?.getString("id")?:""
        val idRecetaTmp = parametros?.getString("idReceta")?:""
        val tiempoEnteroTmp = parametros?.getString("tiempoEntero")?:""
        val tituloTmp = parametros?.getString("titulo")?:""
        val descripcionTmp = parametros?.getString("descripcion")?:""
        val tiempoTextoTmp = parametros?.getString("tiempoText")?:""
        val completadoTmp = parametros?.getString("completado")?:""

        idPaso.text = idTmp
        idReceta.text = idRecetaTmp
        tiempoEntero.text = tiempoEnteroTmp
        titulo.text = tituloTmp
        descripcion.text = descripcionTmp
        tiempoTexto.text = tiempoTextoTmp
        if (completadoTmp=="Completado") completado.isChecked = true

        //actualizar como completado
        completado.setOnClickListener{
            actualizarCompletado()
        }


        //leer pasos
        textToSpeech = TextToSpeech(this) {status ->

            if (status == TextToSpeech.SUCCESS){
                btnLeer.text = getString(R.string.btn_leer_paso)
                btnLeer.setOnClickListener{
                    leerPasos()
                }
            }else{
                btnLeer.text = getString(R.string.btn_leer_paso_error)
            }
        }


        //crear alerta
        btnTiempo.setOnClickListener {
            crearCanal()
            programarNotificacion()
            Toast.makeText(this,"la notificación aparecerá en ${tiempoTexto.text}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarCompletado(){

        val paso = Pasos(idReceta.text.toString().toInt(),titulo.text.toString(),descripcion.text.toString(),tiempoEntero.text.toString().toInt(),completado.isChecked,idPaso.text.toString().toInt())
        db = RecetasAppDb.getDatabase(applicationContext)
        repositorio = Repositorio(db.recetasDao(),db.pasosDao(),db.ingredientesDao())
        repositorio.updatePaso(paso)
    }

    private fun leerPasos(){

        if (textToSpeech.isSpeaking){
            textToSpeech.stop()
            btnLeer.text = getString(R.string.btn_leer_paso)

        }else{
            textToSpeech.speak(descripcion.text.toString(), TextToSpeech.QUEUE_ADD, null)
            btnLeer.text = getString(R.string.btn_leer_paso_stop)
        }
    }

    private fun programarNotificacion() {

        val retraso = tiempoEntero.text.toString().toLong()
        val intent = Intent(applicationContext, AlarmaNotificacion::class.java)
        intent.putExtra("titulo", titulo.text)

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, AlarmaNotificacion.id_notificacion, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + retraso*1000, pendingIntent)
    }

    fun crearCanal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(AlarmaNotificacion.id_channel,AlarmaNotificacion.id_channel,NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
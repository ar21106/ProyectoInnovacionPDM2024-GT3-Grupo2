package com.pdm115gt3g2.recetasapp.db

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pdm115gt3g2.recetasapp.db.dao.IngredientesDao
import com.pdm115gt3g2.recetasapp.db.dao.PasosDao
import com.pdm115gt3g2.recetasapp.db.dao.RecetasDao
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

class LlenarDBWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){
    override suspend fun doWork(): Result {
        val db = RecetasAppDb.getDatabase(applicationContext)
        llenarDb(db.ingredientesDao(),db.pasosDao(),db.recetasDao())
        return Result.success()
    }

    private suspend fun llenarDb(
        ingredientesDao: IngredientesDao,
        pasosDao: PasosDao,
        recetasDao: RecetasDao
    ){
        //RECETAS DE PRUEBA
        val receta1 = Recetas("Receta 1","La primera receta de prueba",true)
        val receta2 = Recetas("Receta 2","La segunda receta de prueba",false)
        recetasDao.insertAll(receta1,receta2)

        //INGREDIENTES DE PRUEBA
        val ingrente1_1 = Ingredientes(1,"unidades","Tomate",3.00)
        val ingrente1_2 = Ingredientes(1,"litros","Agua",1.00)
        val ingrente1_3 = Ingredientes(1,"gramos","Azucar",5.45)
        val ingrente2_1 = Ingredientes(2,"unidades","manzana",5.00)
        val ingrente2_2 = Ingredientes(2,"kg","azucar",0.57)
        val ingrente2_3 = Ingredientes(2,"ml","miel",7.50)
        ingredientesDao.insertAll(ingrente1_1,ingrente1_2,ingrente1_3,ingrente2_1,ingrente2_2,ingrente2_3)

        //PASOS DE PRUEBA
        val paso1_1 = Pasos(1,"Paso 1","Este es el primer paso de la receta 1" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            90)

        val paso1_2 = Pasos(1,"Paso 2","Este es el segundo paso de la receta 1" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            20)

        val paso1_3 = Pasos(1,"Paso 3","Este es el tercer paso de la receta 1" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            100)

        val paso2_1 = Pasos(2,"Paso 1","Este es el primer paso de la receta 2" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            10)

        val paso2_2 = Pasos(2,"Paso 2","Este es el segundo paso de la receta 2" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            20)

        val paso2_3 = Pasos(2,"Paso 3","Este es el tercer paso de la receta 2" +
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba"+
                "\nEsta es una linea de texto de prueba",
            30)

        pasosDao.insertAll(paso1_1,paso1_2,paso1_3,paso2_1,paso2_2,paso2_3)

    }

}
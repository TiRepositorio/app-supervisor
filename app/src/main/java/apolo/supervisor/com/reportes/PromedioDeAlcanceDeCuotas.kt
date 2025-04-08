package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.utilidades.Adapter
import android.database.Cursor
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import java.lang.Exception
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_promedio_de_alcance_de_cuota.*

class PromedioDeAlcanceDeCuotas : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion : FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor : Cursor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio_de_alcance_de_cuota)

        inicializarElementos()
    }

    fun inicializarElementos(){
        FuncionesUtiles.posicionCabecera = 0
        FuncionesUtiles.posicionDetalle  = 0
        cargarCabecera()
        mostrarCabecera()
        if (FuncionesUtiles.listaCabecera.size == 0){
            return
        }
        cargaDetalle()
        mostrarDetalle()
    }

    @SuppressLint("Recycle")
    fun cargarCabecera(){

        val sql : String = ("SELECT COD_VENDEDOR || '-' || DESC_VENDEDOR AS VENDEDOR     " +
                            "     , PORC_LOG, COD_VENDEDOR, DESC_VENDEDOR " +
                            "  FROM svm_prom_alc_cuota_mensual_sup " +
                            " ORDER BY CAST(COD_VENDEDOR AS INTEGER)")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        FuncionesUtiles.listaCabecera = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_VENDEDOR"] = funcion.dato(cursor,"COD_VENDEDOR")
            datos["VENDEDOR"] = funcion.dato(cursor,"VENDEDOR")
            datos["PORC_LOG"] = funcion.porcentaje(funcion.dato(cursor,"PORC_LOG"))
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("Recycle")
    fun cargaDetalle(){

        val promedioDeCuota = funcion.decimalPunto(FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["PORC_LOG"].toString())

        val sql : String = ("SELECT distinct MONTO_PREMIO, PORC_ALC, "
                + "     CASE WHEN (" + promedioDeCuota + " >= cast(PORC_ALC as number)) "
                + "          THEN monto_premio "
                + "          ELSE 0 "
                + "     END TOTAL_PERCIBIR "
                + "  FROM svm_prom_alc_cuota_mensual_sup "
                + " WHERE COD_VENDEDOR = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_VENDEDOR"] + "' "
                + " ORDER BY CAST(COD_VENDEDOR AS INTEGER)")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        FuncionesUtiles.listaDetalle = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["PORC_ALC"] = funcion.porcentaje(funcion.dato(cursor,"PORC_ALC"))
            datos["TOTAL_PERCIBIR"] = funcion.entero(funcion.dato(cursor,"TOTAL_PERCIBIR"))
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarCabecera(){
        val adapterCabecera: Adapter.PromedioDeAlcanceDeCuotasCabecera = Adapter.PromedioDeAlcanceDeCuotasCabecera(this,
            FuncionesUtiles.listaCabecera
        )
        lvPromedioDeAlcanceDeCuotaCabecera.adapter = adapterCabecera
        tvCabeceraCuotaPromedio.text = funcion.porcentaje(adapterCabecera.getPromedioPorcCuotaLog())
        lvPromedioDeAlcanceDeCuotaCabecera.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            FuncionesUtiles.posicionDetalle  = 0
            mostrarDetalle()
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvPromedioDeAlcanceDeCuotaCabecera.invalidateViews()
        }
    }

    fun mostrarDetalle(){
        val adapterDetalle: Adapter.PromedioDeAlcanceDeCuotasDetalle = Adapter.PromedioDeAlcanceDeCuotasDetalle(this,
            FuncionesUtiles.listaDetalle
        )
        lvPromedioDeAlcanceDeCuotaDetalle.adapter = adapterDetalle
        lvPromedioDeAlcanceDeCuotaDetalle.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->

            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvPromedioDeAlcanceDeCuotaDetalle.invalidateViews()

        }
    }
}

package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.utilidades.Adapter
import java.text.DecimalFormat
import android.database.Cursor
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.FuncionesUtiles
import java.lang.Exception
import kotlinx.android.synthetic.main.activity_produccion_semanal.*

class ProduccionSemanal : AppCompatActivity() {

    companion object{
        var posicionSeleccionadoCabecera: Int = 0
        var posicionSeleccionadoDetalle:  Int = 0
        var listaCabecera: ArrayList<HashMap<String, String>> = ArrayList()
        var listaDetalle:  ArrayList<HashMap<String, String>> = ArrayList()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor : Cursor
    }

    private val formatoNumeroEntero: DecimalFormat = DecimalFormat("###,###,###.##")
    val formatoNumeroDecimal: DecimalFormat = DecimalFormat("###,###,###.00")
    var semana : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion_semanal)

        inicializarElementos()
    }

    fun inicializarElementos(){
        cargarCabecera()
        if (FuncionesUtiles.listaCabecera.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        mostrarCabecera()
        cargaDetalle()
        mostrarDetalle()
    }

    @SuppressLint("Recycle")
    fun cargarCabecera(){

        val sql : String = ("SELECT SEMANA , PERIODO     " +
                            "     , SUM(MONTO_VIATICO) AS MONTO_VIATICO " +
                            "  from svm_produccion_semanal_gcs " +
                            " GROUP BY SEMANA, PERIODO " +
                            " ORDER BY CAST(SEMANA AS INTEGER)")
        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        listaCabecera = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["SEMANA"] = cursor.getString(cursor.getColumnIndex("SEMANA"))
            datos["PERIODO"] = cursor.getString(cursor.getColumnIndex("PERIODO"))
            datos["MONTO_VIATICO"] = formatoNumeroEntero.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MONTO_VIATICO"))))
            listaCabecera.add(datos)
            cursor.moveToNext()
            semana = listaCabecera[0]["SEMANA"].toString()
        }
    }

    @SuppressLint("Recycle")
    fun cargaDetalle(){

        val sql : String = ("SELECT CANTIDAD, MONTO_VIATICO, MONTO_X_POR , CANT_CLIENTE "
                         +  "  from svm_produccion_semanal_gcs "
                         +  " where SEMANA  = '" + semana
                         +  "' ORDER BY CAST(MONTO_X_POR AS INTEGER)")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        listaDetalle = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["CANTIDAD"] = formatoNumeroEntero.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("CANTIDAD"))))
            datos["MONTO_VIATICO"] = formatoNumeroEntero.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MONTO_VIATICO"))))
            datos["MONTO_X_POR"] = formatoNumeroEntero.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MONTO_X_POR"))))
            datos["CANT_CLIENTE"] = formatoNumeroEntero.format(Integer.parseInt(cursor.getString(cursor.getColumnIndex("CANT_CLIENTE"))))
            listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("SetTextI18n")
    fun mostrarCabecera(){
        val adapterCabecera: Adapter.ProduccionSemanalCabecera = Adapter.ProduccionSemanalCabecera(this,
            listaCabecera
        )
        lvProduccionSemanalCabecera.adapter = adapterCabecera
        tvCabeceraTotalProduccion.text = formatoNumeroEntero.format(adapterCabecera.getTotalProduccion())+""
        lvProduccionSemanalCabecera.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            posicionSeleccionadoCabecera = position
            semana = listaCabecera[position]["SEMANA"].toString()
            posicionSeleccionadoDetalle = 0
            cargaDetalle()
            mostrarDetalle()
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvProduccionSemanalCabecera.invalidateViews()
        }
    }

    fun mostrarDetalle(){
        val adapterDetalle: Adapter.ProduccionSemanalDetalle = Adapter.ProduccionSemanalDetalle(this,
            listaDetalle
        )
        lvProduccionSemanalDetalle.adapter = adapterDetalle
        lvProduccionSemanalDetalle.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            posicionSeleccionadoDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvProduccionSemanalDetalle.invalidateViews()
        }
    }
}


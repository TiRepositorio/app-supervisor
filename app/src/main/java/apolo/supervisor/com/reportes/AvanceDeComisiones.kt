package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_avance_de_comisiones.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class AvanceDeComisiones : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion : FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor : Cursor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avance_de_comisiones)

        funcion = FuncionesUtiles(imgTitulo,tvTitulo)
        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion.cargarTitulo(R.drawable.ic_dolar, "Avance de comisiones")
        FuncionesUtiles.posicionCabecera = 0
        FuncionesUtiles.posicionDetalle = 0
        llBotonVendedores.visibility = View.GONE
        cargarCabecera()
        if (FuncionesUtiles.listaCabecera.size==0){
            funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargaDetalle()
        mostrarCabecera()
        mostrarDetalle()
    }

    @SuppressLint("Recycle")
    fun cargarCabecera(){

        val sql : String = (" SELECT  TIP_COM "
                         + "       ,  SUM(MONTO_VENTA)    AS MONTO_VENTA "
                         + "       ,  SUM(MONTO_A_COBRAR) AS MONTO_A_COBRAR "
                         + " FROM svm_liq_premios_sup "
                         + " GROUP BY TIP_COM ")

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
            datos["CATEGORIA"] = funcion.dato(cursor,"TIP_COM")
            datos["TOTAL"] = funcion.entero(funcion.dato(cursor,"MONTO_VENTA"))
            datos["COMISION"] = funcion.entero(funcion.dato(cursor,"MONTO_A_COBRAR"))
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("Recycle")
    fun cargaDetalle(){

        val sql : String = (" SELECT "
                + "       COD_MARCA"
                + " 	, COD_MARCA || ' - ' || DESC_MARCA AS DESC_MARCA"
                + "     , SUM(MONTO_VENTA) AS MONTO_VENTA"
                + " FROM svm_liq_premios_sup "
                + " WHERE TIP_COM  = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["CATEGORIA"]
                + "' GROUP BY COD_MARCA ORDER BY COD_MARCA")

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
            datos["MARCA"] = funcion.dato(cursor,"DESC_MARCA")
            datos["TOTAL"] = funcion.entero(funcion.dato(cursor,"MONTO_VENTA"))
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("SetTextI18n")
    fun mostrarCabecera(){
        val adapterCabecera: Adapter.ComisionCabecera = Adapter.ComisionCabecera(this,
            FuncionesUtiles.listaCabecera
        )
        lvComisionCabecera.adapter = adapterCabecera
        tvCabeceraTotalVenta.text = funcion.entero(adapterCabecera.getTotalVentas()) + " Gs."
        tvCabeceraComisionTotal.text = funcion.entero(adapterCabecera.getTotalComision()) + " Gs."
        lvComisionCabecera.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            FuncionesUtiles.posicionDetalle  = 0
            cargaDetalle()
            mostrarDetalle()
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvComisionCabecera.invalidateViews()
        }
    }

    @SuppressLint("SetTextI18n")
    fun mostrarDetalle(){
        val adapterDetalle: Adapter.ComisionDetalle = Adapter.ComisionDetalle(this,
            FuncionesUtiles.listaDetalle
        )
        lvComisionDetalle.adapter = adapterDetalle
        tvDetalleTotalVenta.text = funcion.entero(adapterDetalle.getTotalVentas()) + " Gs."
        lvComisionDetalle.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvComisionDetalle.invalidateViews()
        }
    }
}

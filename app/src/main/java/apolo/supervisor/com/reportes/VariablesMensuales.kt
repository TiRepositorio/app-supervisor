package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_variables_mensuales.*
import java.lang.Exception
import java.text.DecimalFormat

class VariablesMensuales : AppCompatActivity() {

    companion object{
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
    }

    private val formatoNumeroEntero : DecimalFormat = DecimalFormat("###,###,###.##")
    val formatoNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_variables_mensuales)

        inicializaElementos()
    }

    private fun inicializaElementos(){
        cargarCoberturaMensual()
        mostrarCoberturaMensual()
        cargarCuotaPorUnidadDeNegocio()
        mostrarCuotaPorUnidadDeNegocio()
        if (FuncionesUtiles.listaCabecera.size==0 && FuncionesUtiles.listaDetalle.size == 0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
    }

    @SuppressLint("Recycle")
    private fun cargarCoberturaMensual(){
        val sql : String = (" SELECT * from svm_cobertura_mensual_sup")

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
            datos["TOT_CLI_CART"] = cursor.getString(cursor.getColumnIndex("TOT_CLI_CART"))
            datos["CANT_POSIT"] = cursor.getString(cursor.getColumnIndex("CANT_POSIT"))
            datos["TOT_CLIEN_ASIG"] = cursor.getString(cursor.getColumnIndex("TOT_CLIEN_ASIG"))
            datos["PORC_LOGRO"] = formatoNumeroDecimal.format(
                cursor.getString(cursor.getColumnIndex("PORC_LOGRO")).replace(",", ".").toDouble())
            datos["PORC_COB"] = formatoNumeroDecimal.format(
                cursor.getString(cursor.getColumnIndex("PORC_COB")).replace(",", ".").toDouble())
            datos["PREMIOS"] = formatoNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("PREMIOS")).replace(",", ".")))
            datos["MONTO_A_COBRAR"] = formatoNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")).replace(",", ".")))
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarCoberturaMensual(){
        val adapterCoberturaMensual: Adapter.CoberturaMensual = Adapter.CoberturaMensual(this,
            FuncionesUtiles.listaCabecera
        )
        lvCoberturaMensual.adapter = adapterCoberturaMensual
        lvCoberturaMensual.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvCoberturaMensual.invalidateViews()
        }
    }

    @SuppressLint("Recycle")
    private fun cargarCuotaPorUnidadDeNegocio(){
        val sql : String = ("SELECT FEC_DESDE || '-' || FEC_HASTA AS PERIODO"
                + ",COD_UNID_NEGOCIO || '-' || DESC_UNID_NEGOCIO AS UNIDAD_DE_NEGOCIO"
                + ",PORC_PREMIO		, PORC_ALC_PREMIO		, MONTO_VENTA	"
                + ",MONTO_CUOTA		, MONTO_A_COBRAR "
                + " from svm_liq_cuota_x_und_neg "
                + " ORDER BY CAST(COD_UNID_NEGOCIO AS INTEGER)")

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
            datos["PERIODO"] = cursor.getString(cursor.getColumnIndex("PERIODO"))
            datos["UNIDAD_DE_NEGOCIO"] = cursor.getString(cursor.getColumnIndex("UNIDAD_DE_NEGOCIO"))
            datos["PORC_PREMIO"] = formatoNumeroDecimal.format(
                cursor.getString(cursor.getColumnIndex("PORC_PREMIO")).replace(",", ".").toDouble()) + "%"
            datos["PORC_ALC_PREMIO"] = formatoNumeroDecimal.format(
                cursor.getString(cursor.getColumnIndex("PORC_ALC_PREMIO")).replace(",", ".").toDouble()) + "%"
            datos["MONTO_VENTA"] = formatoNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_VENTA")).replace(",", ".")))
            datos["MONTO_CUOTA"] = formatoNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_CUOTA")).replace(",", ".")))
            datos["MONTO_A_COBRAR"] = formatoNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")).replace(",", ".")))
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarCuotaPorUnidadDeNegocio(){
        val adapterCuotaPorUnidadDeNegocios: Adapter.CuotaPorUnidadDeNegocios = Adapter.CuotaPorUnidadDeNegocios(this,
            FuncionesUtiles.listaDetalle
        )
        lvCuotaPorUnidadDeNegocio.adapter = adapterCuotaPorUnidadDeNegocios
        lvCuotaPorUnidadDeNegocio.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvCuotaPorUnidadDeNegocio.invalidateViews()
        }
    }
}

package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import kotlinx.android.synthetic.main.activity_comprobantes_pendientes.*
import java.text.DecimalFormat


class ComprobantesPendientes : AppCompatActivity() {

    companion object{
//        var posicionSeleccionadoComprobantes: Int = 0
        var subPosicionSeleccionadoComprobantes: Int = 0
        var listaComprobantes: ArrayList<HashMap<String, String>> = ArrayList()
        var sublistaComprobantes: ArrayList<HashMap<String, String>> = ArrayList()
        var subListasComprobantes: ArrayList<ArrayList<HashMap<String, String>>> = ArrayList()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
    }

    private val formatNumeroEntero : DecimalFormat = DecimalFormat("###,###,##0.##")
//    val formatNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprobantes_pendientes)

        inicializarElementos()
    }

    fun inicializarElementos(){
        cargarComprobantesPendientes()
        mostrarComprobantesPendientes()
        if (listaComprobantes.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
    }

    @SuppressLint("Recycle")
    fun cargarComprobantesPendientes(){

        var sql : String = (" SELECT TIP_COMPROBANTE_REF                , "
                + "        SUBSTR(FEC_COMPROBANTE,4,7) AS PERIODO       , "
                + "        DESCRIPCION                                  , "
                + "        SUM(TOT_EXENTA)      AS TOT_EXENTA           , "
                + "        SUM(TOT_GRAVADA + TOT_IVA) AS TOT_GRAVADA    , "
                + "        (SUM(TOT_IVA)+sum(TOT_GRAVADA))/11      AS TOT_IVA              , "
                + "        SUM(TOT_COMPROBANTE) AS TOT_COMPROBANTE        "
                + "   FROM svm_liquidacion_fuerza_venta                   "
                + "  GROUP BY TIP_COMPROBANTE_REF,SUBSTR(FEC_COMPROBANTE,4,7), DESCRIPCION ")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        listaComprobantes= ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["TIP_COMPROBANTE_REF"] = cursor.getString(cursor.getColumnIndex("TIP_COMPROBANTE_REF"))
            datos["PERIODO"] = cursor.getString(cursor.getColumnIndex("PERIODO"))
            datos["DESCRIPCION"] = cursor.getString(cursor.getColumnIndex("DESCRIPCION"))
            datos["TOT_EXENTA"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("TOT_EXENTA")).replace(",", ".").replace("null", "").toDouble())
            datos["TOT_GRAVADA"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("TOT_GRAVADA")).replace(",", ".").replace("null", "").toDouble())
            datos["TOT_IVA"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("TOT_IVA")).replace(",", ".").replace("null", "").toDouble())
            datos["TOT_COMPROBANTE"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("TOT_COMPROBANTE")).replace(",", ".")))
            listaComprobantes.add(datos)
            cursor.moveToNext()
        }

        subListasComprobantes = ArrayList()

        for (i in 0 until listaComprobantes.size){
            sql = (" SELECT TIP_COMPROBANTE_REF, "
                    + "        FEC_COMPROBANTE    , "
                    + "        OBSERVACION        , "
                    + "        DESCRIPCION        , "
                    + "        TOT_EXENTA         , "
                    + "        TOT_GRAVADA + TOT_IVA AS TOT_GRAVADA        , "
                    + "        TOT_IVA            , "
                    + "        TOT_COMPROBANTE      "
                    + " FROM   svm_liquidacion_fuerza_venta"
                    + " WHERE  TIP_COMPROBANTE_REF         = '" + listaComprobantes[i]["TIP_COMPROBANTE_REF"] + "'"
                    + "   AND  SUBSTR(FEC_COMPROBANTE,4,7) = '" + listaComprobantes[i]["PERIODO"] + "'"
                    + " ORDER BY FEC_COMPROBANTE DESC")
            try {
                cursor = MainActivity.bd!!.rawQuery(sql, null)
                cursor.moveToFirst()
            } catch (e : Exception){
                e.message
                return
            }

            sublistaComprobantes = ArrayList()

            for (j in 0 until cursor.count){
                datos = HashMap()
                datos["TIP_COMPROBANTE_REF"] = cursor.getString(cursor.getColumnIndex("TIP_COMPROBANTE_REF"))
                datos["FEC_COMPROBANTE"] = cursor.getString(cursor.getColumnIndex("FEC_COMPROBANTE"))
                datos["OBSERVACION"] = cursor.getString(cursor.getColumnIndex("OBSERVACION"))
                datos["DESCRIPCION"] = cursor.getString(cursor.getColumnIndex("DESCRIPCION"))
                datos["TOT_EXENTA"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("TOT_EXENTA")).replace(",", ".").replace("null", "0").toInt())
                datos["TOT_GRAVADA"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("TOT_GRAVADA")).replace(",", ".").replace("null", "0").toInt())
                datos["TOT_IVA"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("TOT_IVA")).replace(",", ".").replace("null", "0").toInt())
                datos["TOT_COMPROBANTE"] = formatNumeroEntero.format(Integer.parseInt(
                    cursor.getString(cursor.getColumnIndex("TOT_COMPROBANTE")).replace(",", ".").replace("null", "0")))
                sublistaComprobantes.add(datos)
                cursor.moveToNext()
            }
            subListasComprobantes.add(sublistaComprobantes)
        }

    }

    private fun mostrarComprobantesPendientes(){
        val adapterComprobantesPendientes: Adapter.ComprobantesPendientes = Adapter.ComprobantesPendientes(this,
            listaComprobantes, subListasComprobantes
        )
        lvComprobantesPendientes.adapter = adapterComprobantesPendientes
        lvComprobantesPendientes.setOnItemClickListener { _: ViewGroup, _: View, _: Int, _: Long ->
            subPosicionSeleccionadoComprobantes = 0
        }

    }

}

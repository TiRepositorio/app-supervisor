package apolo.supervisor.com.reportes

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.Adapter
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.tvCanCliTotalMetas
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.tvCanCliTotalPorcCump
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.tvCanCliTotalTotalPercibir
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.tvCanCliTotalValorDeLaCanasta
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.tvCanCliTotalVentas
import kotlinx.android.synthetic.main.activity_canasta_de_marcas.*
import java.text.DecimalFormat

class CanastaDeMarcas : AppCompatActivity() {

    companion object{
        var posicionSeleccionadoCanastaDeMarcas: Int = 0
        var subPosicionSeleccionadoCanastaDeMarcas: Int = 0
        var lista: ArrayList<HashMap<String, String>> = ArrayList()
        var sublistaCanastaDeMarcas: ArrayList<HashMap<String, String>> = ArrayList()
        var subListasCanastaDeMarcas: ArrayList<ArrayList<HashMap<String, String>>> = ArrayList()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
    }

    private val formatNumeroEntero : DecimalFormat = DecimalFormat("###,###,##0.##")
    private val formatNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canasta_de_marcas)

        inicializarElementos()
    }

    fun inicializarElementos(){
        cargarCanastaDeClientes()
        mostrarCanastaDeClientes()
        if (lista.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
    }

    @SuppressLint("Recycle")
    fun cargarCanastaDeClientes(){

        var sql : String = ("SELECT COD_UNID_NEGOCIO	                                , "
                        + "         DESC_UNID_NEGOCIO		                            , "
                        + " 	    SUM(CAST(VALOR_CANASTA AS NUMBER)) AS VALOR_CANASTA	, "
                        + "         SUM(CAST(VENTAS AS NUMBER)) AS VENTAS               , "
                        + "         SUM(CAST(CUOTA AS NUMBER)) AS CUOTA			        , "
                        + "         SUM(CAST(PORC_CUMP AS NUMBER)) AS PORC_CUMP         , "
                        + "			SUM(CAST(MONTO_A_COBRAR AS NUMBER)) AS MONTO_A_COBRAR "
                        + "   FROM svm_liq_canasta_marca_sup "
                        + "  GROUP BY COD_UNID_NEGOCIO, DESC_UNID_NEGOCIO "
                        + "  ORDER BY COD_UNID_NEGOCIO ASC, DESC_UNID_NEGOCIO ASC ")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        lista = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_UNID_NEGOCIO"] = cursor.getString(cursor.getColumnIndex("COD_UNID_NEGOCIO"))
            datos["DESC_UNID_NEGOCIO"] = cursor.getString(cursor.getColumnIndex("DESC_UNID_NEGOCIO"))
            datos["VALOR_CANASTA"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("VALOR_CANASTA")).replace(",", ".").toDouble())
            datos["VENTAS"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("VENTAS")).replace(",", ".").toDouble())
            datos["CUOTA"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("CUOTA")).replace(",", ".")))
            datos["PORC_CUMP"] = formatNumeroDecimal.format((
                    cursor.getString(cursor.getColumnIndex("PORC_CUMP")).replace(",", ".").toDouble()))+"%"
            datos["MONTO_A_COBRAR"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")).replace(",", ".")))
            lista.add(datos)
            cursor.moveToNext()
        }

        subListasCanastaDeMarcas = ArrayList()

        for (i in 0 until lista.size){
            sql = ("SELECT COD_MARCA	                                    , "
                    + "    DESC_MARCA	                                    , "
                    + "    IFNULL(VALOR_CANASTA, '0') AS VALOR_CANASTA      , "
                    + "    IFNULL(VENTAS, '0') AS VENTAS                    , "
                    + "    IFNULL(CUOTA, '0') AS CUOTA			            , "
                    + "    IFNULL(PORC_CUMP, '0') AS PORC_CUMP              , "
                    + "	   IFNULL(MONTO_A_COBRAR, '0') AS MONTO_A_COBRAR      "
                    + "   FROM svm_liq_canasta_marca_sup "
                    + "  WHERE COD_UNID_NEGOCIO = '" + lista[i]["COD_UNID_NEGOCIO"] + "' "
                    + "  ORDER BY COD_MARCA ASC, DESC_MARCA ASC ")
            try {
                cursor = MainActivity.bd!!.rawQuery(sql, null)
                cursor.moveToFirst()
            } catch (e : Exception){
                e.message
                return
            }

            sublistaCanastaDeMarcas = ArrayList()

            for (j in 0 until cursor.count){
                datos = HashMap()
                datos["COD_MARCA"] = cursor.getString(cursor.getColumnIndex("COD_MARCA"))
                datos["DESC_MARCA"] = cursor.getString(cursor.getColumnIndex("DESC_MARCA"))
                cursor.getString(cursor.getColumnIndex("VALOR_CANASTA")).replace(",", ".")
//                datos.put("VALOR_CANASTA",formatNumeroEntero.format(prueba.toInt()))
                datos["VALOR_CANASTA"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("VALOR_CANASTA")).replace(",", ".").replace("null", "0").replace(" ", "0").toInt())
                datos["VENTAS"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("VENTAS")).replace(",", ".").replace("null", "0").replace(" ", "0").toInt())
                datos["CUOTA"] = formatNumeroEntero.format(
                    cursor.getString(cursor.getColumnIndex("CUOTA")).replace(",", ".").replace("null", "0").replace(" ", "0").toInt())
                datos["PORC_CUMP"] = formatNumeroDecimal.format((
                        cursor.getString(cursor.getColumnIndex("PORC_CUMP")).replace(",", ".").replace("null", "0").replace(" ", "0").toDouble()))+"%"
                datos["MONTO_A_COBRAR"] = formatNumeroEntero.format(Integer.parseInt(
                    cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")).replace(",", ".").replace("null", "0").replace(" ", "0")))
                sublistaCanastaDeMarcas.add(datos)
                cursor.moveToNext()
            }

            subListasCanastaDeMarcas.add(sublistaCanastaDeMarcas)
        }

    }

    @SuppressLint("SetTextI18n")
    fun mostrarCanastaDeClientes(){
        val adapterCanastaDeMarcas: Adapter.CanastaDeMarcas = Adapter.CanastaDeMarcas(this,
            lista, subListasCanastaDeMarcas
        )
        lvCanastaDeMarcas.adapter = adapterCanastaDeMarcas
        lvCanastaDeMarcas.setOnItemClickListener { _: ViewGroup, _: View, _: Int, _: Long ->
            subPosicionSeleccionadoCanastaDeMarcas = 0
        }
        tvCanCliTotalValorDeLaCanasta.text = formatNumeroEntero.format(adapterCanastaDeMarcas.getTotalValorCanasta())
        tvCanCliTotalVentas.text = formatNumeroEntero.format(adapterCanastaDeMarcas.getTotalVentas())
        tvCanCliTotalMetas.text = formatNumeroEntero.format(adapterCanastaDeMarcas.getTotalMetas())
        tvCanCliTotalPorcCump.text = formatNumeroEntero.format(adapterCanastaDeMarcas.getTotalPorcCump()) + "%"
        tvCanCliTotalTotalPercibir.text = formatNumeroEntero.format(adapterCanastaDeMarcas.getTotalPercibir())

    }

}

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
import kotlinx.android.synthetic.main.activity_canasta_de_clientes.*
import java.text.DecimalFormat

class CanastaDeClientes : AppCompatActivity() {

    companion object{
        var posicionSeleccionadoCanastaDeClientes: Int = 0
        var listaCanastaDeClientes: ArrayList<HashMap<String, String>> = ArrayList()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
    }

    private val formatNumeroEntero : DecimalFormat = DecimalFormat("###,###,##0.##")
    private val formatNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canasta_de_clientes)

        inicializarElementos()
    }

    fun inicializarElementos(){
        cargarCanastaDeClientes()
        mostrarCanastaDeClientes()
    }

    @SuppressLint("Recycle")
    fun cargarCanastaDeClientes(){

        val sql : String = ("SELECT COD_CLIENTE	, DESC_CLIENTE		, "
                + " 		 VALOR_CANASTA	    , VENTAS			, " +
                "            CUOTA				, PUNTAJE_CLIENTE	, "
                + "			 PORC_CUMP		    , MONTO_A_COBRAR "
                + " FROM svm_liq_canasta_cte_sup "
                + " ORDER BY DESC_CLIENTE ASC ")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        listaCanastaDeClientes = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_CLIENTE"] = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
            datos["DESC_CLIENTE"] = cursor.getString(cursor.getColumnIndex("DESC_CLIENTE"))
            datos["VALOR_CANASTA"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("VALOR_CANASTA")).replace(",", ".").toDouble())
            datos["VENTAS"] = formatNumeroEntero.format(
                cursor.getString(cursor.getColumnIndex("VENTAS")).replace(",", ".").toDouble())
            datos["CUOTA"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("CUOTA")).replace(",", ".")))
            datos["PUNTAJE_CLIENTE"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("PUNTAJE_CLIENTE")).replace(",", ".")))
            datos["PORC_CUMP"] = formatNumeroDecimal.format((
                    cursor.getString(cursor.getColumnIndex("PORC_CUMP")).replace(",", ".").toDouble()))+"%"
            datos["MONTO_A_COBRAR"] = formatNumeroEntero.format(Integer.parseInt(
                cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")).replace(",", ".")))
            listaCanastaDeClientes.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("SetTextI18n")
    fun mostrarCanastaDeClientes(){
        val adapterCanastaDeClientes: Adapter.CanastaDeClientes = Adapter.CanastaDeClientes(this,
            listaCanastaDeClientes
        )
        lvCanastaDeClientes.adapter = adapterCanastaDeClientes
        lvCanastaDeClientes.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            posicionSeleccionadoCanastaDeClientes = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvCanastaDeClientes.invalidateViews()
        }
        tvCanCliTotalValorDeLaCanasta.text = formatNumeroEntero.format(adapterCanastaDeClientes.getTotalValorCanasta())
        tvCanCliTotalVentas.text = formatNumeroEntero.format(adapterCanastaDeClientes.getTotalVentas())
        tvCanCliTotalMetas.text = formatNumeroEntero.format(adapterCanastaDeClientes.getTotalMetas())
        tvCanCliTotalPorcCump.text = formatNumeroEntero.format(adapterCanastaDeClientes.getTotalPorcCump()) + "%"
        tvCanCliTotalTotalPercibir.text = formatNumeroEntero.format(adapterCanastaDeClientes.getTotalPercibir())
    }

}

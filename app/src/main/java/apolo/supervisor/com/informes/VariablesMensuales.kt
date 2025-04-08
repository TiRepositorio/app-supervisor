package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_variables_mensuales.*
import kotlinx.android.synthetic.main.activity_variables_mensuales2.*
import java.text.DecimalFormat

class VariablesMensuales : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        this.tvVendedor.text = item.title.toString()
        cargarCoberturaMensual()
        mostrarCoberturaMensual()
        cargarCuotaPorUnidadDeNegocio()
        mostrarCuotaPorUnidadDeNegocio()
        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion:FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
    }

    private val formatoNumeroEntero : DecimalFormat = DecimalFormat("###,###,###.##")
    val formatoNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_variables_mensuales2)

//        funcion = FuncionesUtiles(img)
        inicializarElementos()
    }

    fun inicializarElementos(){
        FuncionesUtiles.posicionCabecera = 0
        FuncionesUtiles.posicionDetalle  = 0
        llBotonVendedores.visibility = View.VISIBLE
        llBotonVendedores.setOnClickListener {
            mostrarMenu()
        }
        barraMenu.setNavigationItemSelectedListener(this)

        listaVendedores()
        if (tvVendedor.text.toString().split("-").size <2){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargarCoberturaMensual()
        mostrarCoberturaMensual()
        cargarCuotaPorUnidadDeNegocio()
        mostrarCuotaPorUnidadDeNegocio()
    }

    private fun mostrarMenu(){
        if (contMenu.isDrawerOpen(GravityCompat.START)) {
            contMenu.closeDrawer(GravityCompat.START)
        } else {
            contMenu.openDrawer(GravityCompat.START)
        }
    }

    @SuppressLint("Recycle", "SetTextI18n")
    fun listaVendedores(){
        val sql = ("select distinct COD_VENDEDOR, DESC_VENDEDOR from ("
                + "select distinct c.COD_VENDEDOR, c.DESC_VENDEDOR "
                + "  from svm_liq_cuota_x_und_neg_vend c "
                + " union all "
                + "select distinct c.COD_VENDEDOR, c.DESC_VENDEDOR "
                + "  from svm_cobertura_mensual_vend c "
                + ")"
                + " order by cast(COD_VENDEDOR as integer)")
        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
            val codigo = cursor.getString(cursor.getColumnIndex("COD_VENDEDOR"))
            val descripcion = cursor.getString(cursor.getColumnIndex("DESC_VENDEDOR"))
            tvVendedor.text = "$codigo-$descripcion"
        } catch (e : Exception){
            e.message
            return
        }

        for (i in 0 until cursor.count){
            val codigo = cursor.getString(cursor.getColumnIndex("COD_VENDEDOR"))
            val descripcion = cursor.getString(cursor.getColumnIndex("DESC_VENDEDOR"))
            barraMenu.menu.add("$codigo-$descripcion").setIcon(R.drawable.ic_usuario)
            cursor.moveToNext()
        }
    }

    @SuppressLint("Recycle")
    fun cargarCoberturaMensual(){
        val sql : String = (" SELECT TOT_CLI_CART,CANT_POSIT,PORC_LOGRO,PORC_COB,PREMIOS,MONTO_A_COBRAR" +
                            "   from svm_cobertura_mensual_vend" +
                            "  WHERE COD_VENDEDOR  = '" + tvVendedor.text.toString().split("-")[0] + "' " +
                            "    AND DESC_VENDEDOR = '" + tvVendedor.text.toString().split("-")[1] + "' " )

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
            datos["TOT_CLIEN_ASIG"] = cursor.getString(cursor.getColumnIndex("TOT_CLI_CART"))
            datos["CANT_POSIT"] = cursor.getString(cursor.getColumnIndex("CANT_POSIT"))
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
    fun cargarCuotaPorUnidadDeNegocio(){
        val sql : String = ("SELECT FEC_DESDE || '-' || FEC_HASTA AS PERIODO "
                + " ,COD_UNID_NEGOCIO || '-' || DESC_UNID_NEGOCIO AS UNIDAD_DE_NEGOCIO"
                + " ,PORC_PREMIO		, PORC_ALC_PREMIO		, MONTO_VENTA	"
                + " ,MONTO_CUOTA		, MONTO_A_COBRAR "
                + "  from svm_liq_cuota_x_und_neg_vend "
                + " WHERE COD_VENDEDOR  = '" + tvVendedor.text.toString().split("-")[0] + "' "
                + "   AND DESC_VENDEDOR = '" + tvVendedor.text.toString().split("-")[1] + "' "
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

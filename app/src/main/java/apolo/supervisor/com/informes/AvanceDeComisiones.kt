package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_avance_de_comisiones.*
import kotlinx.android.synthetic.main.activity_avance_de_comisiones2.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class AvanceDeComisiones : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion : FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor

        //linea de prueba
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        tvVendedor.text = item.title.toString()
        FuncionesUtiles.posicionCabecera = 0
        FuncionesUtiles.posicionDetalle  = 0
        cargarCabecera()
        mostrarCabecera()
        cargaDetalle()
        mostrarDetalle()
        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avance_de_comisiones2)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBotonVendedores)
        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion.cargarTitulo(R.drawable.ic_dolar,"Avance de comisiones")
        funcion.listaVendedores("COD_VENDEDOR","DESC_VENDEDOR","svm_liq_premios_vend")
        funcion.inicializaContadores()
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        barraMenu.setNavigationItemSelectedListener(this)
        if (tvVendedor.text.toString().split("-").size <2){
            funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargarCabecera()
        mostrarCabecera()
        cargaDetalle()
        mostrarDetalle()
    }

    @SuppressLint("Recycle")
    fun cargarCabecera(){
//        Toast.makeText(this,tvVendedor.text.toString(),Toast.LENGTH_LONG).show();
        val sql : String = (" SELECT  TIP_COM "
                + "       ,  SUM(MONTO_VENTA)    AS MONTO_VENTA "
                + "       ,  SUM(MONTO_A_COBRAR) AS MONTO_A_COBRAR "
                + "  FROM svm_liq_premios_vend "
                + " WHERE COD_VENDEDOR  = '" + tvVendedor.text.toString().split("-")[0] + "' "
                + "   AND DESC_VENDEDOR = '" + tvVendedor.text.toString().split("-")[1] + "' "
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
            datos["CATEGORIA"] = cursor.getString(cursor.getColumnIndex("TIP_COM"))
            datos["TOTAL"] = funcion.entero(cursor.getString(cursor.getColumnIndex("MONTO_VENTA")))
            datos["COMISION"] = funcion.entero(cursor.getString(cursor.getColumnIndex("MONTO_A_COBRAR")))
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
                + " FROM svm_liq_premios_vend "
                + " WHERE TIP_COM  = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["CATEGORIA"] + "' "
                + "   AND COD_VENDEDOR  = '" + tvVendedor.text.toString().split("-")[0] + "' "
                + "   AND DESC_VENDEDOR = '" + tvVendedor.text.toString().split("-")[1] + "' "
                + " GROUP BY COD_MARCA ORDER BY COD_MARCA")

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
            datos["MARCA"] = cursor.getString(cursor.getColumnIndex("DESC_MARCA"))
            datos["TOTAL"] = funcion.entero(cursor.getString(cursor.getColumnIndex("MONTO_VENTA")))
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

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
            cargarCabecera()
            mostrarCabecera()
            cargaDetalle()
            mostrarDetalle()
        }
    }

}

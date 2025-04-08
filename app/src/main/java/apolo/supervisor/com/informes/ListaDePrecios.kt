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
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_lista_de_precios.*
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.barraMenu
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.contMenu
import kotlinx.android.synthetic.main.barra_vendedores.*

class ListaDePrecios : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        FuncionesUtiles.posicionCabecera = 0

        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_precios)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBuscar,spBuscar,etBuscar,btBuscar,llBotonVendedores)
        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion.addItemSpinner(this,"Codigo-Descripcion","COD_ARTICULO-DESC_ARTICULO")
        funcion.inicializaContadores()
        funcion.cargarTitulo(R.drawable.ic_lista,"Lista de precios")
        cargarArticulo()
        if (FuncionesUtiles.listaCabecera.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        mostrarArticulo()
        cargarPrecios()
        mostrarPrecio()
        btBuscar.setOnClickListener{buscar()}
    }

    private fun cargarArticulo(){
        val sql : String = ("SELECT DISTINCT COD_ARTICULO, DESC_ARTICULO, REFERENCIA "
                         +  "  FROM svm_precio_fijo_consulta "
                         +  " ORDER BY DESC_ARTICULO")
        cargarLista(funcion.consultar(sql))
    }

    fun cargarLista(cursor: Cursor){
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_ARTICULO"] = funcion.dato(cursor,"COD_ARTICULO")
            datos["DESC_ARTICULO"] = funcion.dato(cursor,"DESC_ARTICULO")
            datos["REFERENCIA"] = funcion.dato(cursor,"REFERENCIA")
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarArticulo(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3)
        funcion.valores = arrayOf("COD_ARTICULO", "DESC_ARTICULO", "REFERENCIA")
        val adapter: Adapter.AdapterGenericoCabecera =
            Adapter.AdapterGenericoCabecera(this
                                            ,FuncionesUtiles.listaCabecera
                                            ,R.layout.inf_lis_prec_lista_articulo
                                            ,funcion.vistas
                                            ,funcion.valores)
        lvArticulos.adapter = adapter
        lvArticulos.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            FuncionesUtiles.posicionDetalle  = 0
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvArticulos.invalidateViews()
            cargarPrecios()
            mostrarPrecio()
            tvdCod.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_ARTICULO"]
            tvdRef.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["REFERENCIA"]
            tvdDesc.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["DESC_ARTICULO"]
        }
        tvdCod.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_ARTICULO"]
        tvdRef.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["REFERENCIA"]
        tvdDesc.text = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["DESC_ARTICULO"]
    }

    fun cargarPrecios(){
        val sql : String = "SELECT COD_LISTA_PRECIO, DESC_LISTA_PRECIO, PREC_CAJA, PREC_UNID, DECIMALES, SIGLAS" +
                           "  FROM svm_precio_fijo_consulta " +
                           " WHERE COD_ARTICULO = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_ARTICULO"] + "' " +
                           " ORDER BY CAST (COD_LISTA_PRECIO AS DOUBLE)"
        cargarPrecio(funcion.consultar(sql))
    }

    fun cargarPrecio(cursor: Cursor){
        FuncionesUtiles.listaDetalle = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_LISTA_PRECIO"] = funcion.dato(cursor,"COD_LISTA_PRECIO")
            datos["DESC_LISTA_PRECIO"] = funcion.dato(cursor,"DESC_LISTA_PRECIO")
            datos["SIGLAS"] = funcion.dato(cursor,"SIGLAS")
            datos["DECIMALES"] = funcion.dato(cursor,"DECIMALES")
            datos["PREC_UNID"] =
                funcion.numero(datos["DECIMALES"].toString(),funcion.dato(cursor,"PREC_UNID"))
            datos["PREC_CAJA"] =
                funcion.numero(datos["DECIMALES"].toString(),funcion.dato(cursor,"PREC_CAJA"))
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarPrecio(){
        funcion.subVistas  = intArrayOf(R.id.tvd1,R.id.tvd2,R.id.tvd3,R.id.tvd4)
        funcion.subValores = arrayOf("COD_LISTA_PRECIO", "DESC_LISTA_PRECIO", "SIGLAS","PREC_CAJA")
        val adapter: Adapter.AdapterGenericoDetalle =
            Adapter.AdapterGenericoDetalle(this
                ,FuncionesUtiles.listaDetalle
                ,R.layout.inf_lis_prec_lista_precio
                ,funcion.subVistas
                ,funcion.subValores)
        lvPrecios.adapter = adapter
        lvPrecios.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvPrecios.invalidateViews()
        }
    }

    fun buscar(){
        val campos = "DISTINCT COD_ARTICULO, DESC_ARTICULO, REFERENCIA "
        val groupBy = ""
        val orderBy = "DESC_ARTICULO"
        cargarLista(funcion.buscar("svm_precio_fijo_consulta",campos,groupBy,orderBy))
        mostrarArticulo()
        cargarPrecios()
        mostrarPrecio()
    }

}

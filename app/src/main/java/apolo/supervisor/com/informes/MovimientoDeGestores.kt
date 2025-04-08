package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_movimiento_de_gestores.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class MovimientoDeGestores : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        FuncionesUtiles.posicionCabecera = 0

        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
        lateinit var cursor: Cursor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimiento_de_gestores)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBotonVendedores)
        inicializarElementos()
    }

    fun inicializarElementos(){
        barraMenu.setNavigationItemSelectedListener(this)
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        funcion.inicializaContadores()
        funcion.listaVendedores("COD_VENDEDOR","NOM_VENDEDOR","svm_movimiento_gestor")
        funcion.cargarTitulo(R.drawable.ic_movimiento,"Movimiento de gestores")
        tvVendedor.setOnClickListener { funcion.mostrarMenu() }
        if (tvVendedor.text.toString().split("-").size <2){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargarFecha()
        mostrarFecha()
        cargarDetalle()
        mostrarDetalle()
    }

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
            cargarFecha()
            mostrarFecha()
            cargarDetalle()
            mostrarDetalle()
        }
    }

    private fun cargarFecha(){
        val sql : String = "SELECT DISTINCT FEC_ASISTENCIA, COD_VENDEDOR " +
                           "  FROM svm_movimiento_gestor " +
                           " WHERE COD_VENDEDOR = '" + tvVendedor.text.toString().split("-")[0] + "' " +
                           " ORDER BY CAST(FEC_ASISTENCIA AS DATE)"
        cursor = funcion.consultar(sql)
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["FEC_ASISTENCIA"] = funcion.dato(cursor,"FEC_ASISTENCIA")
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    fun cargarDetalle(){
        val sql : String = ("select COD_CLIENTE    , DESC_CLIENTE    , HORA_ENTRADA   ,"
                          + "       HORA_SALIDA    , MARCACION 	     , FEC_ASISTENCIA "
                          + "  from svm_movimiento_gestor "
                          + " where COD_VENDEDOR    = '" + tvVendedor.text.toString().split("-")[0] + "' "
                          + "   and FEC_ASISTENCIA  = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["FEC_ASISTENCIA"] + "'"
                          + " Order By HORA_ENTRADA ")
        cursor = funcion.consultar(sql)
        FuncionesUtiles.listaDetalle = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_CLIENTE"] = funcion.dato(cursor,"COD_CLIENTE")
            datos["DESC_CLIENTE"] = funcion.dato(cursor,"DESC_CLIENTE")
            datos["HORA_ENTRADA"] = funcion.dato(cursor,"HORA_ENTRADA")
            datos["HORA_SALIDA"] = funcion.dato(cursor,"HORA_SALIDA")
            datos["MARCACION"] = funcion.dato(cursor,"MARCACION")
            datos["FEC_ASISTENCIA"] = funcion.dato(cursor,"FEC_ASISTENCIA")
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarFecha(){
        funcion.valores = arrayOf("FEC_ASISTENCIA")
        funcion.vistas  = intArrayOf(R.id.tv1)
        val adapter:Adapter.AdapterGenericoCabecera =
                    Adapter.AdapterGenericoCabecera(this
                                                            ,FuncionesUtiles.listaCabecera
                                                            ,R.layout.lista_uno
                                                            ,funcion.vistas
                                                            ,funcion.valores)
        lvFecha.adapter = adapter
        lvFecha.setOnItemClickListener{ _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvFecha.invalidateViews()
            cargarDetalle()
            mostrarDetalle()
        }
    }

    fun mostrarDetalle(){
        funcion.valores = arrayOf("COD_CLIENTE","DESC_CLIENTE","HORA_ENTRADA","HORA_SALIDA","MARCACION")
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5)
        val adapter:Adapter.AdapterGenericoDetalle =
            Adapter.AdapterGenericoDetalle(this
                                                    ,FuncionesUtiles.listaDetalle
                                                    ,R.layout.inf_mov_ges_lista_movimiento
                                                    ,funcion.vistas
                                                    ,funcion.valores)
        lvMovimiento.adapter = adapter
        lvMovimiento.setOnItemClickListener{ _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvMovimiento.invalidateViews()
        }
    }
}

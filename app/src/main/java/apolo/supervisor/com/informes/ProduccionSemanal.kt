package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.utilidades.Adapter
import android.database.Cursor
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import java.lang.Exception
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_avance_de_comisiones2.*
import kotlinx.android.synthetic.main.activity_produccion_semanal.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class ProduccionSemanal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion: FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor : Cursor
    }

    var semana : String = ""
    var codVendedor = ""
    private var desVendedor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion_semanal2)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBotonVendedores)

        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion.cargarTitulo(R.drawable.ic_dolar,"Produccion semanal")
        imgTitulo.visibility = View.GONE
        tvTitulo.visibility = View.GONE
        funcion.listaVendedores("COD_VENDEDOR","DESC_VENDEDOR","svm_produccion_semanal_vend")
        funcion.inicializaContadores()
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        barraMenu.setNavigationItemSelectedListener(this)
        if (tvVendedor.text.toString().split("-").size <2){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargarCabecera()
        mostrarCabecera()
        cargaDetalle()
        mostrarDetalle()
    }

    private fun cargarCodigos(){
        codVendedor = tvVendedor.text!!.toString().split("-")[0]
        desVendedor = tvVendedor.text.toString().split("-")[1]
        if (tvVendedor.text.toString().split("-").size>2){
            desVendedor = tvVendedor.text.toString()
            while (desVendedor.indexOf("-") != 0){
                desVendedor = desVendedor.substring(1,desVendedor.length)
            }
            desVendedor = desVendedor.substring(1,desVendedor.length)
        }
//        funcion.mensaje(this,codVendedor,desVendedor)
    }

    @SuppressLint("Recycle")
    fun cargarCabecera(){
        cargarCodigos()
        val sql : String = ("SELECT SEMANA , PERIODO     " +
                            "     , SUM(MONTO_VIATICO) AS MONTO_VIATICO " +
                            "  from svm_produccion_semanal_vend " +
                            " WHERE COD_VENDEDOR  = '$codVendedor' " +
                            "   AND DESC_VENDEDOR = '$desVendedor' " +
                            " GROUP BY SEMANA, PERIODO " +
                            " ORDER BY CAST(SEMANA AS INTEGER)")
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
            datos["SEMANA"] = cursor.getString(cursor.getColumnIndex("SEMANA"))
            datos["PERIODO"] = cursor.getString(cursor.getColumnIndex("PERIODO"))
            datos["MONTO_VIATICO"] = funcion.entero(cursor.getString(cursor.getColumnIndex("MONTO_VIATICO")))
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
            semana = FuncionesUtiles.listaCabecera[0]["SEMANA"].toString()
        }
    }

    @SuppressLint("Recycle")
    fun cargaDetalle(){

        val sql : String = ("SELECT CANTIDAD, SEMANA, MONTO_TOTAL , PERIODO "
                         +  "  from svm_produccion_semanal_vend "
                         +  " where SEMANA        = '$semana' "
                         +  "   AND COD_VENDEDOR  = '$codVendedor' "
                         +  "   AND DESC_VENDEDOR = '$desVendedor' "
                         +  " ORDER BY CAST(MONTO_TOTAL AS INTEGER)")

        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            val error = e.message.toString()
            funcion.mensaje(this,"error",error)
            return
        }

        FuncionesUtiles.listaDetalle = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["CANTIDAD"] = funcion.entero(funcion.dato(cursor,"CANTIDAD"))
            datos["SEMANA"] = funcion.entero(cursor.getString(cursor.getColumnIndex("SEMANA")))
            datos["MONTO_TOTAL"] = funcion.entero(cursor.getString(cursor.getColumnIndex("MONTO_TOTAL")))
            datos["PERIODO"] = cursor.getString(cursor.getColumnIndex("PERIODO"))
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    @SuppressLint("SetTextI18n")
    fun mostrarCabecera(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3)
        funcion.valores = arrayOf("SEMANA","PERIODO","MONTO_VIATICO")
        val adapterCabecera: Adapter.AdapterGenericoCabecera = Adapter.AdapterGenericoCabecera(this,
            FuncionesUtiles.listaCabecera,
            R.layout.rep_prod_sem_lista_produccion_semanal_cabecera2,
            funcion.vistas,funcion.valores
        )
        lvProduccionSemanalCabecera.adapter = adapterCabecera
        tvCabeceraTotalProduccion.text = funcion.entero(adapterCabecera.getTotalEntero("MONTO_VIATICO"))+""
        lvProduccionSemanalCabecera.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            semana = FuncionesUtiles.listaCabecera[position]["SEMANA"].toString()
            FuncionesUtiles.posicionDetalle = 0
            cargaDetalle()
            mostrarDetalle()
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvProduccionSemanalCabecera.invalidateViews()
        }
    }

    fun mostrarDetalle(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4)
        funcion.valores = arrayOf("SEMANA","CANTIDAD","MONTO_TOTAL","PERIODO")
        val adapterDetalle: Adapter.AdapterGenericoDetalle = Adapter.AdapterGenericoDetalle(this,
            FuncionesUtiles.listaDetalle,
            R.layout.rep_prod_sem_lista_produccion_semanal_detalle2,
            funcion.vistas,
            funcion.valores
        )
        lvProduccionSemanalDetalle.adapter = adapterDetalle
        lvProduccionSemanalDetalle.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvProduccionSemanalDetalle.invalidateViews()
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

}


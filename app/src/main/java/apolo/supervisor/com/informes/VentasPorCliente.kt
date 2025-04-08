package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.*
import kotlinx.android.synthetic.main.barra_vendedores.*


class VentasPorCliente : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        tvVendedor.text = menuItem.title.toString()
        FuncionesUtiles.posicionCabecera = 0
        cargar()
        mostrar()
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
        setContentView(R.layout.activity_ventas_por_cliente)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBotonVendedores)
        inicializarElementos()
    }

    fun inicializarElementos(){
        tvMesAnterior.setBackgroundResource(R.drawable.border_textviews)
        tvMesActual.setBackgroundResource(R.drawable.border_textviews)
        barraMenu.setNavigationItemSelectedListener(this)
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        funcion.inicializaContadores()
        funcion.listaVendedores("COD_VENDEDOR","DESC_VENDEDOR","svm_metas_punto_por_cliente")
        funcion.cargarTitulo(R.drawable.ic_dolar_tach,"Ventas por clientes")
        if (tvVendedor.text.toString().split("-").size <2){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        cargar()
        mostrar()
    }

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
            cargar()
            mostrar()
        }
    }

    fun cargar(){
        val sql: String = ("SELECT COD_VENDEDOR    , DESC_VENDEDOR    , CODIGO          ,"
                        + "NOM_SUBCLIENTE  , CIUDAD         , COD_SUPERVISOR  ,"
                        + "DESC_SUPERVISOR , LISTA_PRECIO   , IFNULL(MAYOR_VENTA,'0.0') MAYOR_VENTA     ,"
                        + "IFNULL(VENTA_3,'0.0') VENTA_3    , IFNULL(MIX_3,'0.0') MIX_3     , IFNULL(VENTA_4,'0.0') VENTA_4        ,"
                        + "IFNULL(MIX_4,'0.0') MIX_4        , IFNULL(METAS,'0.0') METAS     , "
                        + "MES_1			 , MES_2"
                        + "  FROM svm_metas_punto_por_cliente  "
                        + " WHERE COD_VENDEDOR = '" + tvVendedor.text.toString().split("-")[0] + "' "
                        + " ORDER BY COD_VENDEDOR, CAST (TOT_SURTIDO AS NUMBER) DESC")
        cursor = funcion.consultar(sql)
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["CODIGO"] = funcion.dato(cursor,"CODIGO")
            datos["NOM_SUBCLIENTE"] = funcion.dato(cursor,"NOM_SUBCLIENTE")
            datos["CIUDAD"] = funcion.dato(cursor,"CIUDAD")
            datos["LISTA_PRECIO"] = funcion.dato(cursor,"LISTA_PRECIO")
            datos["MAYOR_VENTA"] = funcion.entero(funcion.dato(cursor,"MAYOR_VENTA"))
            datos["VENTA_3"] = funcion.entero(funcion.dato(cursor,"VENTA_3"))
            datos["MIX_3"] = funcion.entero(funcion.dato(cursor,"MIX_3"))
            datos["VENTA_4"] = funcion.entero(funcion.dato(cursor,"VENTA_4"))
            datos["MIX_4"] = funcion.entero(funcion.dato(cursor,"MIX_4"))
            datos["MES_1"] = funcion.dato(cursor,"MES_1")
            datos["MES_2"] = funcion.dato(cursor,"MES_2")
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
        if (cursor.count>0){
            tvMesAnterior.text = funcion.getMes(datos["MES_1"].toString())
            tvMesActual.text = funcion.getMes(datos["MES_2"].toString())
        }
    }

    fun mostrar(){
        funcion.valores = arrayOf("CODIGO"       , "NOM_SUBCLIENTE" , "CIUDAD"   ,
                                  "LISTA_PRECIO" , "MAYOR_VENTA"    , "VENTA_3"  ,
                                  "MIX_3"        , "VENTA_4"        , "MIX_4"
                                  )
        funcion.vistas = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,
                                    R.id.tv6,R.id.tv7,R.id.tv8,R.id.tv9)
        val adapter: Adapter.AdapterGenericoCabecera = Adapter.AdapterGenericoCabecera(this,
            FuncionesUtiles.listaCabecera,
            R.layout.inf_ven_cli_lista_ventas_por_clientes,
            funcion.vistas,funcion.valores)
        lvVentasPorMarca.adapter = adapter
        tvMayorVenta.text = funcion.entero(adapter.getTotalEntero("MAYOR_VENTA"))
        tvVentaAnt.text   = funcion.entero(adapter.getTotalEntero("VENTA_3"))
        tvMixAnt.text     = funcion.entero(adapter.getTotalEntero("MIX_3"))
        tvVenta.text      = funcion.entero(adapter.getTotalEntero("VENTA_4"))
        tvMix.text        = funcion.entero(adapter.getTotalEntero("MIX_4"))
        lvVentasPorMarca.setOnItemClickListener { _, view, position, _ ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvVentasPorMarca.invalidateViews()
        }
    }

}

package apolo.supervisor.com.visitas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import apolo.supervisor.com.utilidades.Mapa
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_ruteo_en_mapa.*
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.barraMenu
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.contMenu
import kotlinx.android.synthetic.main.barra_vendedores.*

class RuteoEnMapa : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        tvVendedor.text = item.title.toString()
        FuncionesUtiles.posicionCabecera = 0
        cargarClientes()
        mostrarClientes()
        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
        lateinit var cursor: Cursor
        lateinit var lm: LocationManager
    }

//    var ubicacion : FuncionesUbicacion = FuncionesUbicacion(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruteo_en_mapa)
        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBotonVendedores)
        inicializarElementos()
    }

    fun inicializarElementos(){
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        barraMenu.setNavigationItemSelectedListener(this)
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        funcion.inicializaContadores()
        funcion.listaVendedores("COD_VENDEDOR","DESC_VENDEDOR","svm_positivacion_cliente")
        funcion.cargarTitulo(R.drawable.ic_mapa_ruteo,"Ruteo del vendedor en mapa")
        tvVendedor.setOnClickListener { funcion.mostrarMenu() }
        cargarClientes()
        mostrarClientes()
        btCliente.setOnClickListener{verCliente()}
        btRuteo.setOnClickListener{verRuteo()}
    }

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
            cargarClientes()
            mostrarClientes()
        }
    }

    private fun cargarClientes(){
        val sql : String = ("SELECT   a.COD_EMPRESA    , a.COD_VENDEDOR   , a.DESC_VENDEDOR  ,"
                + "IFNULL(a.COD_SUPERVISOR,'"+ FuncionesUtiles.usuario["PASS"] +"') COD_SUPERVISOR, a.TIP_CAUSAL     , a.COD_CLIENTE    ,"
                + "a.COD_SUBCLIENTE , a.DESC_CLIENTE   , a.DESC_SUBCLIENTE,"
                + "a.DIRECCION      , a.DESC_CIUDAD    , a.RUC            ,"
                + "a.DESC_REGION    , a.DESC_ZONA      , a.TELEFONO       ,"
                + "a.MONTO_VENTA    , a.IND_POS        , b.LATITUD        ,"
                + "b.LONGITUD "
                + " FROM svm_positivacion_cliente a, svm_cliente_supervisores b"
                + " WHERE a.cod_vendedor = '" + tvVendedor.text.split("-")[0] + "'"
                + "   AND a.COD_CLIENTE    = b.COD_CLIENTE "
                + "   AND a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
                + " GROUP BY a.COD_EMPRESA    , a.COD_VENDEDOR   , a.DESC_VENDEDOR  ,"
                + "a.COD_SUPERVISOR , a.TIP_CAUSAL     , a.COD_CLIENTE    ,"
                + "a.COD_SUBCLIENTE , a.DESC_CLIENTE   , a.DESC_SUBCLIENTE,"
                + "a.DIRECCION      , a.DESC_CIUDAD    , a.RUC            ,"
                + "a.DESC_REGION    , a.DESC_ZONA      , a.TELEFONO       ,"
                + "a.MONTO_VENTA    , a.IND_POS        , b.LATITUD        ,"
                + "b.LONGITUD "
                + " ORDER BY CAST(a.COD_CLIENTE AS NUMBER), Cast(a.COD_SUBCLIENTE AS NUMBER)")

//        val sql : String = ("SELECT   IFNULL(a.COD_EMPRESA,'NULL') COD_EMPRESA   , IFNULL(a.COD_VENDEDOR,'NULL') COD_VENDEDOR  , IFNULL(a.DESC_VENDEDOR,'NULL') DESC_VENDEDOR ,"
//                + "IFNULL(a.COD_SUPERVISOR,'NULL') , IFNULL(a.TIP_CAUSAL,'NULL')     , IFNULL(a.COD_CLIENTE,'NULL')    ,"
//                + "IFNULL(a.COD_SUBCLIENTE,'NULL') , IFNULL(a.DESC_CLIENTE,'NULL')   , IFNULL(a.DESC_SUBCLIENTE,'NULL'),"
//                + "IFNULL(a.DIRECCION,'NULL')      , IFNULL(a.DESC_CIUDAD,'NULL')    , IFNULL(a.RUC,'NULL')            ,"
//                + "IFNULL(a.DESC_REGION,'NULL')    , IFNULL(a.DESC_ZONA,'NULL')      , IFNULL(a.TELEFONO,'NULL')       ,"
//                + "IFNULL(a.MONTO_VENTA,'NULL')    , IFNULL(a.IND_POS,'NULL')        , IFNULL(b.LATITUD,'NULL')        ,"
//                + "IFNULL(b.LONGITUD,'NULL') "
//                + " FROM svm_positivacion_cliente a, svm_cliente_supervisores b"
//                + " WHERE a.cod_vendedor = '" + tvVendedor.text.split("-")[0] + "'"
//                + "   AND a.COD_CLIENTE    = b.COD_CLIENTE "
//                + "   AND a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
//                + " GROUP BY a.COD_EMPRESA    , a.COD_VENDEDOR   , a.DESC_VENDEDOR  ,"
//                + "a.COD_SUPERVISOR , a.TIP_CAUSAL     , a.COD_CLIENTE    ,"
//                + "a.COD_SUBCLIENTE , a.DESC_CLIENTE   , a.DESC_SUBCLIENTE,"
//                + "a.DIRECCION      , a.DESC_CIUDAD    , a.RUC            ,"
//                + "a.DESC_REGION    , a.DESC_ZONA      , a.TELEFONO       ,"
//                + "a.MONTO_VENTA    , a.IND_POS        , b.LATITUD        ,"
//                + "b.LONGITUD "
//                + " ORDER BY CAST(a.COD_CLIENTE AS NUMBER), Cast(a.COD_SUBCLIENTE AS NUMBER)")
        cargarLista(funcion.consultar(sql))
    }

    fun cargarLista(cursor: Cursor){
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            for (j in 0 until cursor.columnCount){
                try {
                    datos[cursor.getColumnName(j)] =
                        funcion.dato(cursor,cursor.getColumnName(j)).replace("null","")
                } catch (e:Exception) {
                    funcion.mensaje(this,"Error","El campo " + cursor.getColumnName(j) + " esta nulo. \n" + e.message)
                }

            }
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrarClientes(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4 ,R.id.tv5 ,R.id.tv6,
                                     R.id.tv7,R.id.tv8,R.id.tv9,R.id.tv10,R.id.tv11)
        funcion.valores = arrayOf("TIP_CAUSAL"  , "COD_CLIENTE" 	  , "COD_SUBCLIENTE",
                                  "DESC_CLIENTE", "DESC_SUBCLIENTE"   , "DESC_CIUDAD"   ,
                                  "DIRECCION"   , "RUC"               , "DESC_REGION"   ,
                                  "DESC_ZONA"   ,	"TELEFONO")
        val adapter: Adapter.AdapterGenericoCabecera =
            Adapter.AdapterGenericoCabecera(this
                ,FuncionesUtiles.listaCabecera
                ,R.layout.vis_rut_ven_lista_ruteo_de_vendedores
                ,funcion.vistas
                ,funcion.valores)
        lvRuteo.adapter = adapter
        lvRuteo.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            FuncionesUtiles.posicionDetalle  = 0
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvRuteo.invalidateViews()
        }
    }

    private fun verCliente(){
        Mapa.lista = ArrayList()
        Mapa.lista.add(FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera])
        Mapa.modificarCliente = false
        val intent = Intent(this,Mapa::class.java)
        startActivity(intent)
    }

    private fun verRuteo(){
        Mapa.lista = ArrayList()
        Mapa.lista = FuncionesUtiles.listaCabecera
        Mapa.modificarCliente = false
        val intent = Intent(this,Mapa::class.java)
        startActivity(intent)
    }
}

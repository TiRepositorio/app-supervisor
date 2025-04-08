package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_pedidos_por_vendedor.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class PedidosPorVendedor : AppCompatActivity() {

    lateinit var funcion : FuncionesUtiles
    private lateinit var listaVendedor  : ArrayList<HashMap<String,String>>
    private lateinit var listaClientes  : ArrayList<HashMap<String,String>>
    private lateinit var listaDePrecios : ArrayList<HashMap<String,String>>
    private lateinit var adapterVendedor : Adapter.AdapterGenericoCabecera
    private lateinit var adapterClientes: Adapter.AdapterGenericoCabecera
    private lateinit var adapterListaDeP: Adapter.AdapterGenericoCabecera
    private var posicionVendedor = 0
    private var posicionClientes = 0
    private var posicionArticulo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos_por_vendedor)

        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo)
        funcion.cargarTitulo(R.drawable.ic_visitado,"Pedidos por vendedor")
        llVendedor.visibility = View.VISIBLE
        llClientes.visibility = View.GONE
        llListaDePrecios.visibility = View.GONE
        cargarVendedores()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun mostrarContenido(view: View) {
        tvVendedores.setBackgroundColor(Color.parseColor("#757575"))
        tvClientes.setBackgroundColor(Color.parseColor("#757575"))
        tvListaDePrecios.setBackgroundColor(Color.parseColor("#757575"))
        view.setBackgroundColor(Color.parseColor("#116600"))
        llVendedor.visibility = View.GONE
        llClientes.visibility = View.GONE
        llListaDePrecios.visibility = View.GONE
        when(view.id){
            tvVendedores.id     -> llVendedor.visibility = View.VISIBLE
            tvClientes.id       -> llClientes.visibility = View.VISIBLE
            tvListaDePrecios.id -> llListaDePrecios.visibility = View.VISIBLE
        }
    }

    private fun cargarVendedores(){
        val sql = ("select distinct COD_VENDEDOR, DESC_VENDEDOR, NOMBRE_VENDEDOR,"
                            + "RUTEO       , POSITIVADO   , POS_FUERA      ,"
                            + "CAST(TOTAL_PEDIDO AS NUMBER) TOTAL_PEDIDO "
                            + " from svm_control_venta_diaria_cab "
                            + " Order By Cast(COD_VENDEDOR as double)")
        listaVendedor = ArrayList()
        funcion.cargarLista(listaVendedor,funcion.consultar(sql))
        for (i in 0 until listaVendedor.size){
            listaVendedor[i]["POSITIVADO"] = listaVendedor[i]["POSITIVADO"].toString().replace(" ","0")
            listaVendedor[i]["TOTAL_PEDIDO"] = funcion.entero(listaVendedor[i]["TOTAL_PEDIDO"].toString().replace("null","0").replace(" ","0"))
        }
        mostrarVendedores()
    }

    private fun mostrarVendedores(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,R.id.tv6)
        funcion.valores = arrayOf("COD_VENDEDOR", "DESC_VENDEDOR", "RUTEO" ,
                                  "POSITIVADO"  , "POS_FUERA"    , "TOTAL_PEDIDO")
        adapterVendedor = Adapter.AdapterGenericoCabecera(this,
                                                        listaVendedor,
                                                        R.layout.inf_ped_ven_lista_pedidos_por_vendedor_vendedores,
                                                        funcion.vistas,
                                                        funcion.valores)
        lvVendedores.adapter = adapterVendedor
        lvVendedores.setOnItemClickListener { _, _, position, _ ->
            FuncionesUtiles.posicionCabecera = position
            posicionVendedor = position
            lvVendedores.invalidateViews()
            tvdCodVendedor.setText(listaVendedor[position]["COD_VENDEDOR"])
            tvdDescVendedor.text = listaVendedor[position]["DESC_VENDEDOR"]
            tvdCodVendedor2.setText(listaVendedor[position]["COD_VENDEDOR"])
            tvdDescVendedor2.text = listaVendedor[position]["DESC_VENDEDOR"]
            cargarClientes(tvdCodVendedor.text.toString().trim())
            if (listaClientes.size>0){
                cargarListaDePrecios(tvdCodVendedor.text.toString().trim()
                    ,tvdCodVendedor4.text.toString().split("-")[0].trim()
                    ,tvdCodVendedor4.text.toString().split("-")[1].trim())
            }
        }
        if (listaVendedor.size>0){
            tvdCodVendedor.setText(listaVendedor[0]["COD_VENDEDOR"])
            tvdDescVendedor.text = listaVendedor[0]["DESC_VENDEDOR"]
            tvdCodVendedor2.setText(listaVendedor[0]["COD_VENDEDOR"])
            tvdDescVendedor2.text = listaVendedor[0]["DESC_VENDEDOR"]
            tvTotalRuteo.text = funcion.entero(adapterVendedor.getTotalEntero("RUTEO"))
            tvTotalPositivado.text = funcion.entero(adapterVendedor.getTotalEntero("POSITIVADO"))
            tvTotalFueraRuta.text = funcion.entero(adapterVendedor.getTotalEntero("POS_FUERA"))
            tvTotalPedido.text = funcion.entero(adapterVendedor.getTotalEntero("TOTAL_PEDIDO"))
            cargarClientes(tvdCodVendedor.text.toString().trim())
            if (listaClientes.size>0){
                cargarListaDePrecios(tvdCodVendedor.text.toString().trim()
                                    ,tvdCodVendedor4.text.toString().split("-")[0].trim()
                                    ,tvdCodVendedor4.text.toString().split("-")[1].trim())
            }
        }
    }

    private fun cargarClientes(codVendedor: String){
        val sql = ("select COD_CLIENTE, COD_SUBCLIENTE, NOM_SUBCLIENTE, "
                + "Cast(Sum(Cast(replace(TOT_DESCUENTO,',','.') as number )) as TEXT) as TOT_DESCUENTO, "
                + "Cast(Sum(Cast(replace(MONTO_TOTAL,',','.') as number)) as TEXT) as MONTO_TOTAL"
                + " from svm_control_venta_diaria_det "
                + " where COD_VENDEDOR = '$codVendedor' "
                + " group by COD_CLIENTE, COD_SUBCLIENTE, NOM_SUBCLIENTE "
                + " ORDER BY Cast (COD_CLIENTE as double)")
        listaClientes = ArrayList()
        funcion.cargarLista(listaClientes,funcion.consultar(sql))
        for (i in 0 until listaClientes.size){
            listaClientes[i]["TOT_DESCUENTO"] =
                funcion.entero(listaClientes[i]["TOT_DESCUENTO"].toString().replace("null","0").replace(" ","0"))
            listaClientes[i]["MONTO_TOTAL"] =
                funcion.entero(listaClientes[i]["MONTO_TOTAL"].toString().replace("null","0").replace(" ","0"))
        }
        mostrarClientes()
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarClientes(){
        funcion.vistas  = intArrayOf(R.id.tvc1,R.id.tvc2,R.id.tvc3,R.id.tvc4)
        funcion.valores = arrayOf("COD_CLIENTE","NOM_SUBCLIENTE","TOT_DESCUENTO","MONTO_TOTAL")
        adapterClientes = Adapter.AdapterGenericoCabecera(this,
            listaClientes,
            R.layout.inf_ped_ven_lista_pedidos_por_vendedor_clientes,
            funcion.vistas,
            funcion.valores)
        lvClientes.adapter = adapterClientes
        lvClientes.setOnItemClickListener { _, _, position, _ ->
            FuncionesUtiles.posicionCabecera = position
            posicionClientes = position
            lvClientes.invalidateViews()
            tvdCodVendedor4.setText(listaClientes[position]["COD_CLIENTE"] +"-"+ listaClientes[position]["COD_SUBCLIENTE"])
            tvdDescVendedor4.text = listaClientes[position]["NOM_SUBCLIENTE"]
            cargarListaDePrecios(tvdCodVendedor.text.toString().trim()
                ,tvdCodVendedor4.text.toString().split("-")[0].trim()
                ,tvdCodVendedor4.text.toString().split("-")[1].trim())
        }
        if (listaClientes.size > 0){
            tvdCodVendedor4.setText(listaClientes[0]["COD_CLIENTE"] + "-" + listaClientes[0]["COD_SUBCLIENTE"])
            tvdDescVendedor4.text = listaClientes[0]["NOM_SUBCLIENTE"]
            cargarListaDePrecios(tvdCodVendedor.text.toString().trim()
                ,tvdCodVendedor4.text.toString().split("-")[0].trim()
                ,tvdCodVendedor4.text.toString().split("-")[1].trim())
        } else {
            tvdCodVendedor4.setText("")
            tvdDescVendedor4.text = ""
        }
    }

    private fun cargarListaDePrecios(codVendedor: String, codCliente: String, codSubcliente: String){
        val sql: String
        if (listaClientes.size < 1){
            sql = ("select COD_ARTICULO   , DESC_ARTICULO, CANTIDAD,"
                    + "PRECIO_UNITARIO, TOT_DESCUENTO, MONTO_TOTAL "
                    + " from svm_control_venta_diaria_det "
                    + " where COD_VENDEDOR = '$codVendedor' "
                    + "and COD_CLIENTE = '" + "' "
                    + "and COD_SUBCLIENTE = '" + "' "
                    + " ORDER BY DESC_ARTICULO")
        } else {
            sql = ("select COD_ARTICULO   , DESC_ARTICULO, CANTIDAD,"
                    + "PRECIO_UNITARIO, TOT_DESCUENTO, MONTO_TOTAL "
                    + " from svm_control_venta_diaria_det "
                    + " where COD_VENDEDOR = '$codVendedor' "
                    + "and COD_CLIENTE = '$codCliente' "
                    + "and COD_SUBCLIENTE = '$codSubcliente' "
                    + " ORDER BY DESC_ARTICULO")
        }
        listaDePrecios = ArrayList()
        funcion.cargarLista(listaDePrecios,funcion.consultar(sql))
        for (i in 0 until listaDePrecios.size){
            listaDePrecios[i]["TOT_DESCUENTO"] = funcion.entero(listaDePrecios[i]["TOT_DESCUENTO"].toString().replace("null","0").replace(" ","0"))
            listaDePrecios[i]["PRECIO_UNITARIO"] = funcion.entero(listaDePrecios[i]["PRECIO_UNITARIO"].toString().replace("null","0").replace(" ","0"))
            listaDePrecios[i]["MONTO_TOTAL"] = funcion.entero(listaDePrecios[i]["MONTO_TOTAL"].toString().replace("null","0").replace(" ","0"))
        }
        mostrarListaDePrecios()
    }

    private fun mostrarListaDePrecios(){
        funcion.vistas  = intArrayOf(R.id.tvl1,R.id.tvl2,R.id.tvl3,R.id.tvl4,R.id.tvl5,R.id.tvl6)
        funcion.valores = arrayOf("COD_ARTICULO"   , "DESC_ARTICULO", "CANTIDAD",
                                  "PRECIO_UNITARIO", "TOT_DESCUENTO", "MONTO_TOTAL")
        adapterListaDeP = Adapter.AdapterGenericoCabecera(this,
            listaDePrecios,
            R.layout.inf_ped_ven_lista_pedidos_por_vendedor_articulos,
            funcion.vistas,
            funcion.valores)
        lvListaDePrecios.adapter = adapterListaDeP
        lvListaDePrecios.setOnItemClickListener { _, _, position, _ ->
            FuncionesUtiles.posicionCabecera = position
            posicionArticulo = position
            lvListaDePrecios.invalidateViews()
        }
    }

}
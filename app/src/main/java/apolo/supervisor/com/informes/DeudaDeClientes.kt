package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.reportes.Deuda
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_deuda_de_clientes.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class DeudaDeClientes : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        FuncionesUtiles.posicionCabecera = 0
        tvVendedor.text = menuItem.title.toString()
        cargarTodo()
        mostrar()
        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
        lateinit var cursor: Cursor
        var venta : Boolean = false
        var codCliente : String = ""
        var codSubcliente : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deuda_de_clientes)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBuscar,spBuscar,etBuscar,btBuscar,llBotonVendedores,View.VISIBLE)
        btBuscar.isEnabled = true
        if (venta){
            funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBuscar,spBuscar,etBuscar,btBuscar,llBotonVendedores,View.GONE)
            btBuscar.isEnabled = false
            barraMenu.layoutParams.width = 0
        }
        inicializarElementos()
    }

    fun inicializarElementos(){
        barraMenu.setNavigationItemSelectedListener(this)
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
        funcion.inicializaContadores()
        funcion.listaVendedores("COD_VENDEDOR","DESC_VENDEDOR","svm_deuda_cliente")
        funcion.cargarTitulo(R.drawable.ic_dolar_tach,"Deuda de clientes")
        funcion.addItemSpinner(this,"Codigo-Nombre","COD_CLIENTE-DESC_CLIENTE,DESC_SUBCLIENTE")
        tvVendedor.setOnClickListener { funcion.mostrarMenu() }
        cargarTodo()
        if (FuncionesUtiles.listaCabecera.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        mostrar()
        btBuscar.setOnClickListener{buscar()}
        deuda(btDeuda)
    }

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
            cargarTodo()
            mostrar()
//            cargaDetalle()
//            mostrarDetalle()
        }
    }

    private fun cargarTodo(){
        var sql : String = ("SELECT COD_CLIENTE     , DESC_CLIENTE             , COD_SUBCLIENTE,"
                         +  "       DESC_SUBCLIENTE, Sum(SALDO_CUOTA) AS SALDO , DESC_VENDEDOR ,"
                         +  "       DESC_SUPERVISOR, COD_VENDEDOR "
                         +  "  FROM svm_deuda_cliente  "
                         +  " WHERE COD_VENDEDOR = '" + tvVendedor.text.toString().split("-")[0] + "' "
                         +  " GROUP BY COD_CLIENTE, COD_SUBCLIENTE "
                         +  " Order By Cast(COD_CLIENTE as NUMBER), Cast(COD_SUBCLIENTE as NUMBER) ")
        if (venta) {
            sql = ("SELECT COD_CLIENTE     , DESC_CLIENTE             , COD_SUBCLIENTE,"
            +  "       DESC_SUBCLIENTE, Sum(SALDO_CUOTA) AS SALDO , DESC_VENDEDOR ,"
            +  "       DESC_SUPERVISOR, COD_VENDEDOR "
            +  "  FROM svm_deuda_cliente  "
            +  " WHERE COD_CLIENTE     = '" + codCliente + "' "
            +  "   AND COD_SUBCLIENTE  = '" + codSubcliente + "' "
            +  " GROUP BY COD_CLIENTE, COD_SUBCLIENTE "
            +  " Order By Cast(COD_CLIENTE as NUMBER), Cast(COD_SUBCLIENTE as NUMBER) ")
        }
        cargarLista(funcion.consultar(sql))
    }

    fun buscar(){
        val campos = "COD_CLIENTE,DESC_CLIENTE,COD_SUBCLIENTE,DESC_SUBCLIENTE,Sum(SALDO_CUOTA) AS SALDO,DESC_VENDEDOR," +
                            "DESC_SUPERVISOR,COD_VENDEDOR "
        val groupBy = "COD_CLIENTE, COD_SUBCLIENTE"
        val orderBy = "Cast(COD_CLIENTE as NUMBER), Cast(COD_SUBCLIENTE as NUMBER)"
        cargarLista(funcion.buscar("svm_deuda_cliente",campos,groupBy,orderBy))
        mostrar()
    }

    fun cargarLista(cursor: Cursor){
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_CLIENTE"] = funcion.dato(cursor,"COD_CLIENTE")
            datos["COD_SUBCLIENTE"] = funcion.dato(cursor,"COD_SUBCLIENTE")
            datos["DESC_CLIENTE"] = funcion.dato(cursor,"DESC_CLIENTE")
            datos["DESC_SUBCLIENTE"] = funcion.dato(cursor,"DESC_SUBCLIENTE")
            datos["SALDO"] = funcion.entero(funcion.dato(cursor,"SALDO"))
            datos["DESC_VENDEDOR"] = funcion.dato(cursor,"DESC_VENDEDOR")
            datos["COD_VENDEDOR"] = funcion.dato(cursor,"COD_VENDEDOR")
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    fun mostrar() {
        funcion.vistas = intArrayOf(R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6)
        funcion.valores = arrayOf(
            "COD_CLIENTE", "COD_SUBCLIENTE", "DESC_CLIENTE",
            "DESC_SUBCLIENTE", "SALDO", "DESC_VENDEDOR"
        )
        val adapter: Adapter.AdapterGenericoCabecera =
            Adapter.AdapterGenericoCabecera(
                this
                , FuncionesUtiles.listaCabecera
                , R.layout.inf_deu_cli_lista_deuda
                , funcion.vistas
                , funcion.valores
            )
        lvDeuda.adapter = adapter
        lvDeuda.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvDeuda.invalidateViews()
        }
        tvTotalDeuda.text = funcion.entero(adapter.getTotalEntero("SALDO"))
    }

    fun deuda(btDeuda:Button){
        btDeuda.setOnClickListener{
            Deuda.codVen = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_VENDEDOR"].toString()
            Deuda.codigo = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_CLIENTE"].toString() + "-" +
                           FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["COD_SUBCLIENTE"].toString()
            Deuda.nombre = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["DESC_SUBCLIENTE"].toString()
            val deuda = Intent(this, Deuda::class.java)
            startActivity(deuda)
        }
    }

}

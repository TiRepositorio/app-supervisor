package apolo.supervisor.com.ventas

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.informes.DeudaDeClientes
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.DialogoAutorizacion
import apolo.supervisor.com.utilidades.FuncionesUtiles
import apolo.supervisor.com.utilidades.Mapa
import apolo.supervisor.com.ventas.sd.SolicitudDevolucion
import kotlinx.android.synthetic.main.activity_lista_clientes.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class ListaClientes : AppCompatActivity() {

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
        var codVendedor : String = ""
        var codCliente : String = ""
        var codSubcliente : String = ""
        var descCliente : String = ""
        var tipCliente : String = ""
        var indEspecial : String = ""
        var tipCondicion : String = ""
        var codCondicion : String = ""
        var diasInicial : String = ""
        var codSucursalCliente : String = ""
        var indDirecta : String = ""
        var lista : ArrayList<HashMap<String,String>> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)

        //solo con titulo
        funcion = FuncionesUtiles(imgTitulo,tvTitulo)
        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,llBuscar,spBuscar,etBuscar,btBuscar)

        inicializarElementos()
    }

    fun inicializarElementos(){
        funcion.addItemSpinner(this,"Codigo-Nombre-Ciudad","COD_CLIENTE-DESC_SUBCLIENTE-desc_ciudad")
        funcion.inicializaContadores()
        funcion.cargarTitulo(R.drawable.ic_persona,"Lista de clientes")
        btBuscar.setOnClickListener{buscar(" AND TRIM(FEC_VISITA) = '' ")}
        btDeuda.setOnClickListener{deuda()}
        ibtnMapa.setOnClickListener{mapa()}
        inicializaETAccion(accion)
        btModificar.setOnClickListener{modificar()}
        btSD.setOnClickListener{sd()}
        btVender.setOnClickListener{vender()}
        buscarRuteo()
        cbNoAtendidos.setOnClickListener{buscarBloqueado()}
        cbRuteoDelDia.setOnClickListener{buscarRuteo()}
        cbTodos.setOnClickListener{buscarTodo()}
    }

    fun buscar(condicion:String){
        val campos = " * "
        val groupBy = ""
        val orderBy = "COD_CLIENTE"
        val tabla = " svm_cliente_vendedor "
//        var where = " AND COD_VENDEDOR = '" + codVendedor +
        val where = " AND COD_VENDEDOR = '$codVendedor' $condicion "
        cargarLista(funcion.buscar(tabla,campos,groupBy,orderBy,where))
        mostrar()
    }

    private fun buscarRuteo(){
        cbTodos.isChecked = false
        cbNoAtendidos.isChecked = false
        if (cbRuteoDelDia.isChecked){
            buscar(" AND IND_VISITA = 'S' AND FEC_VISITA = '${funcion.getFechaActual()}' ")
        } else {
            buscar(" AND IND_VISITA = 'S' AND FEC_VISITA = '${funcion.getFechaActual()}' ")
        }
    }

    private fun buscarBloqueado(){
        cbRuteoDelDia.isChecked = false
        cbTodos.isChecked = false
        if (cbNoAtendidos.isChecked){
            buscar(" AND IND_VISITA = 'B' AND FEC_VISITA = '${funcion.getFechaActual()}' ")
        } else {
            buscar(" AND IND_VISITA = 'S' AND FEC_VISITA = '${funcion.getFechaActual()}' ")
        }
    }

    private fun buscarTodo(){
        cbRuteoDelDia.isChecked = false
        cbNoAtendidos.isChecked = false
        if (cbTodos.isChecked){
            buscar(" AND TRIM(FEC_VISITA) = '' ")
        } else {
            buscar(" AND IND_VISITA = 'S' AND FEC_VISITA = '${funcion.getFechaActual()}' ")
        }
    }

    fun cargarLista(cursor: Cursor){
        FuncionesUtiles.listaDetalle = ArrayList()
        for (i in 0 until cursor.count){
            datos = HashMap()
            for (j in 0 until cursor.columnCount){
                try {
                    datos[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
            datos["LIMITE_CREDITO"] = funcion.enteroCliente(datos["LIMITE_CREDITO"].toString())
            datos["TOT_DEUDA"] = funcion.enteroCliente(datos["TOT_DEUDA"].toString())
            datos["SALDO"] = funcion.enteroCliente(datos["SALDO"].toString())
            FuncionesUtiles.listaDetalle.add(datos)
            cursor.moveToNext()
        }
    }

    fun mostrar(){
        funcion.vistas  = intArrayOf(R.id.tv1 ,R.id.tv2 ,R.id.tv3 ,R.id.tv4 ,R.id.tv5 ,R.id.tv6 ,
                                     R.id.tv7 ,R.id.tv8 ,R.id.tv9 ,R.id.tv10,R.id.tv11,R.id.tv12,
                                     R.id.tv13,R.id.tv14,R.id.tv15,R.id.tv16,R.id.tv17 )
        funcion.valores = arrayOf("TIP_CAUSAL"      , "CATEGORIA"       ,"COD_CLIENTE"      , "COD_SUBCLIENTE"  , "TIP_CLIENTE"    ,
                                  "DESC_CLIENTE"    , "DESC_SUBCLIENTE" ,"desc_ciudad"      , "DIRECCION"       , "RUC"            ,
                                  "DESC_ZONA"       , "TELEFONO"        ,"DESC_CONDICION"   , "LIMITE_CREDITO"  , "TOT_DEUDA"      ,
                                  "SALDO"           , "FEC_VISITA"      )
        val adapter: Adapter.AdapterGenericoDetalle =
            Adapter.AdapterGenericoDetalle(this
                ,FuncionesUtiles.listaDetalle
                ,R.layout.ven_cli_lista_clientes
                ,funcion.vistas
                ,funcion.valores)
        lvClientes.adapter = adapter
        lvClientes.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionDetalle  = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvClientes.invalidateViews()
            codCliente = FuncionesUtiles.listaDetalle[position]["COD_CLIENTE"].toString()
            codSubcliente = FuncionesUtiles.listaDetalle[position]["COD_SUBCLIENTE"].toString()
            descCliente = FuncionesUtiles.listaDetalle[position]["DESC_SUBCLIENTE"].toString()
            tipCliente = FuncionesUtiles.listaDetalle[position]["TIP_CLIENTE"].toString()
            indEspecial = FuncionesUtiles.listaDetalle[position]["IND_ESP"].toString()
            tipCondicion = FuncionesUtiles.listaDetalle[position]["TIPO_CONDICION"].toString()
            codCondicion = FuncionesUtiles.listaDetalle[position]["COD_CONDICION"].toString()
            diasInicial = FuncionesUtiles.listaDetalle[position]["DIAS_INICIAL"].toString()
            indDirecta = FuncionesUtiles.listaDetalle[position]["IND_DIRECTA"].toString()
            codSucursalCliente = FuncionesUtiles.listaDetalle[position]["COD_SUCURSAL"].toString()
        }
        if (FuncionesUtiles.listaDetalle.size>0){
            codCliente = FuncionesUtiles.listaDetalle[0]["COD_CLIENTE"].toString()
            codSubcliente = FuncionesUtiles.listaDetalle[0]["COD_SUBCLIENTE"].toString()
            descCliente = FuncionesUtiles.listaDetalle[0]["DESC_SUBCLIENTE"].toString()
            tipCliente = FuncionesUtiles.listaDetalle[0]["TIP_CLIENTE"].toString()
            indEspecial = FuncionesUtiles.listaDetalle[0]["IND_ESP"].toString()
            tipCondicion = FuncionesUtiles.listaDetalle[0]["TIPO_CONDICION"].toString()
            codCondicion = FuncionesUtiles.listaDetalle[0]["COD_CONDICION"].toString()
            diasInicial = FuncionesUtiles.listaDetalle[0]["DIAS_INICIAL"].toString()
            indDirecta = FuncionesUtiles.listaDetalle[0]["IND_DIRECTA"].toString()
            codSucursalCliente = FuncionesUtiles.listaDetalle[0]["COD_SUCURSAL"].toString()
        }
    }

    fun deuda(){
        if (FuncionesUtiles.listaDetalle.size > 0){
            DeudaDeClientes.codCliente = FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle]["COD_CLIENTE"].toString()
            DeudaDeClientes.codSubcliente = FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle]["COD_SUBCLIENTE"].toString()
            if (FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle]["TOT_DEUDA"].equals("0")){
                funcion.mensaje(this,"Atención!","El cliente no tiene deudas.")
                return
            }
            DeudaDeClientes.venta = true
            val deuda = Intent(this,DeudaDeClientes::class.java)
            startActivity(deuda)
        }
    }

    fun verCliente(){
        Mapa.lista = ArrayList()
        Mapa.lista.add(FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle])
        Mapa.modificarCliente = false
        val intent = Intent(this,Mapa::class.java)
        startActivity(intent)
    }

    fun verRuteo(){
        Mapa.lista = ArrayList()
        Mapa.lista = FuncionesUtiles.listaDetalle
        Mapa.modificarCliente = false
        val intent = Intent(this,Mapa::class.java)
        startActivity(intent)
    }

    private fun inicializaETAccion(etAccion: EditText){
        etAccion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etAccion.text.toString() == "cliente"){
                    verCliente()
                    accion.setText("")
                    return
                }
                if (etAccion.text.toString().trim() == "ruteo"){
                    verRuteo()
                    accion.setText("")
                    return
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

        })
    }

    private fun mapa(){
        val dialogo = DialogoAutorizacion(this)
        dialogo.dialogoMapa(accion)
    }

    fun modificar(){
        if (FuncionesUtiles.listaDetalle.size==0){return}
        ModificarCliente.codCliente = FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle]["COD_CLIENTE"].toString()
        ModificarCliente.codSubcliente = FuncionesUtiles.listaDetalle[FuncionesUtiles.posicionDetalle]["COD_SUBCLIENTE"].toString()
        val modifcar = Intent(this,ModificarCliente::class.java)
        startActivity(modifcar)
    }

    fun vender(){
        Pedidos.nuevo = true
        val vender = Intent(this,Pedidos::class.java)
        startActivity(vender)
    }

    fun sd(){
        val sd = Intent(this,SolicitudDevolucion::class.java)
        startActivity(sd)
    }

}

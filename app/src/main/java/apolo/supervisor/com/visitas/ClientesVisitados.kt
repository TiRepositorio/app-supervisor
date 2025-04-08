package apolo.supervisor.com.visitas

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.*
import kotlinx.android.synthetic.main.activity_clientes_visitados.*
import kotlinx.android.synthetic.main.barra_vendedores.*
import java.lang.Exception

class ClientesVisitados : AppCompatActivity() {

    var calendario : DialogoCalendario = DialogoCalendario(this)
    var funcion : FuncionesUtiles = FuncionesUtiles(this)
    private var consultor: FuncionesConsultor? = null
    var dispositivo : FuncionesDispositivo = FuncionesDispositivo(this)
    var id : String = ""
    private lateinit var telMgr : TelephonyManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes_visitados)

        inicializarElementos()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun inicializarElementos(){
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo)
        funcion.cargarTitulo(R.drawable.ic_visitado,"Clientes visitados")
        consultor = FuncionesConsultor(this,etDesde,etHasta,rbPendiente,rbEnviado,rbTodo,imgBuscar,btModificar,btConsultar,btEliminar,rgFiltro)
        consultor!!.setRadioButtonText("Pendiente-ESTADO = 'P'","Enviado-ESTADO = 'E'","Todo-ESTADO LIKE '%%'")
        etDesde.setText(funcion.getFechaActual())
        etHasta.setText(funcion.getFechaActual())
        etDesde.setOnClickListener{calendario.onCreateDialog(1,etDesde,etDesde)!!.show()}
        etHasta.setOnClickListener{calendario.onCreateDialog(1,etHasta,etDesde)!!.show()}
        imgBuscar.setOnClickListener{buscar()}
        btModificar.setOnClickListener{modificar()}
        btConsultar.setOnClickListener{consultar()}
        btEliminar.setOnClickListener{eliminar()}
        imgEnviar.setOnClickListener{enviar()}
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun buscar(){
        FuncionesUtiles.posicionCabecera = 0
        cargarLista(consultor!!.buscar("svm_analisis_cab","FECHA_VISITA"))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun cargarLista(cursor:Cursor){
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            val dato: HashMap<String,String> = HashMap()
            for (j in 0 until cursor.columnCount){
                try {
                    dato[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
                } catch (e : Exception){

                }
            }
            dato["CODIGO"] = dato["COD_CLIENTE"] +"-"+ dato["COD_SUBCLIENTE"]
            dato["VENDEDOR"] = dato["COD_VENDEDOR"] +"-"+ dato["DESC_VENDEDOR"]
            FuncionesUtiles.listaCabecera.add(dato)
            id = FuncionesUtiles.listaCabecera[0]["id"].toString()
            cursor.moveToNext()
            btModificar.isEnabled = FuncionesUtiles.listaCabecera[0]["ESTADO"].toString() != "A"
        }
        mostrar()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun mostrar(){
        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        funcion.vistas = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5)
        funcion.valores = arrayOf("CODIGO","DESC_SUBCLIENTE","VENDEDOR","FECHA_VISITA","ESTADO")
        val adapter:Adapter.AdapterGenericoCabecera =
            Adapter.AdapterGenericoCabecera(this,
                                                    FuncionesUtiles.listaCabecera,
                                                    R.layout.vis_cli_vis_lista_clientes_visitados,
                                                    funcion.vistas,
                                                    funcion.valores
                                            )
        lvMarcaciones.adapter = adapter
        lvMarcaciones.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            btModificar.isEnabled = FuncionesUtiles.listaCabecera[position]["ESTADO"].toString() != "A"
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvMarcaciones.invalidateViews()
            this.id = FuncionesUtiles.listaCabecera[position]["id"].toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun modificar(){
        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        if (FuncionesUtiles.listaCabecera.size>0){
            val intent = Intent(this,VisitaCliente::class.java)
            VisitaCliente.nuevo = false
            VisitaCliente.consulta = false
            VisitaCliente.id = id
            startActivity(intent)
        } else {
            funcion.mensaje("","Seleccione una marcacion")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun consultar(){
        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        if (FuncionesUtiles.listaCabecera.size>0){
            val intent = Intent(this,VisitaCliente::class.java)
            VisitaCliente.nuevo = false
            VisitaCliente.consulta = true
            VisitaCliente.id = id
            startActivity(intent)
        } else {
            funcion.mensaje("","Seleccione una marcacion")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun eliminar(){
        val sql = "UPDATE svm_analisis_cab SET ESTADO = 'A' WHERE id = '$id' AND ESTADO = 'P'"
        funcion.ejecutar(sql,this)
        buscar()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun enviar(){
        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        EnviarMarcacion.id = id
        EnviarMarcacion.context = this
        val enviarMarcacion = EnviarMarcacion()
        enviarMarcacion.enviar()
        buscar()
    }

}

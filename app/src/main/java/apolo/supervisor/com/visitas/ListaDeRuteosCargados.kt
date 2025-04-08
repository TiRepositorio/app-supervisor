package apolo.supervisor.com.visitas

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.DialogoCalendario
import apolo.supervisor.com.utilidades.FuncionesConsultor
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.lista_de_ruteos_cargados.*

class ListaDeRuteosCargados : AppCompatActivity() {

    var calendario : DialogoCalendario = DialogoCalendario(this)
    var funcion : FuncionesUtiles = FuncionesUtiles(this)
    private var consultor: FuncionesConsultor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_de_ruteos_cargados)

        inicializarElementos()
    }

    fun inicializarElementos(){
        consultor = FuncionesConsultor(this,etDesde,etHasta,rbPendiente,rbEnviado,rbTodo,imgBuscar,btConsultar,btEliminar,rgFiltro)
        consultor!!.setRadioButtonText("Pendiente-ESTADO = 'P'","Enviado-ESTADO = 'E'","Todo-ESTADO LIKE '%%'")
        etDesde.setText(funcion.getFechaActual())
        etHasta.setText(funcion.getFechaActual())
        etDesde.setOnClickListener{calendario.onCreateDialog(1,etDesde,etDesde)!!.show()}
        etHasta.setOnClickListener{calendario.onCreateDialog(1,etHasta,etDesde)!!.show()}
        imgBuscar.setOnClickListener{buscar()}
        btEliminar.setOnClickListener{eliminar()}
        btConsultar.setOnClickListener{consultar()}
    }

    fun buscar(){
        val campos = "(strftime('%W',date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  )) + 1 ) as SEMANA, " +
                     " strftime('%d/%m/%Y',max( date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) ) as MAX_FECHA, " +
                     " strftime('%d/%m/%Y',min( date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) ) as MIN_FECHA,  ESTADO"
        FuncionesUtiles.posicionCabecera = 0
        cargarLista(consultor!!.buscar("svm_ruteo_semanal","FECHA",campos))
    }

    fun cargarLista(cursor:Cursor){
        FuncionesUtiles.listaCabecera = ArrayList()
        for (i in 0 until cursor.count){
            val dato: HashMap<String,String> = HashMap()
            for (j in 0 until cursor.columnCount){
                try {
                    dato[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
                } catch (e : Exception){
                    e.printStackTrace()
                }
            }
            FuncionesUtiles.listaCabecera.add(dato)
            cursor.moveToNext()
        }
        mostrar()
    }

    fun mostrar(){
        funcion.vistas = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4)
        funcion.valores = arrayOf("SEMANA","MIN_FECHA","MAX_FECHA","ESTADO")
        val adapter:Adapter.AdapterGenericoCabecera =
            Adapter.AdapterGenericoCabecera(this,
                FuncionesUtiles.listaCabecera,
                R.layout.vis_lis_rut_lista_ruteos_cargados,
                funcion.vistas,
                funcion.valores
            )
        lvListaRuteo.adapter = adapter
        lvListaRuteo.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvListaRuteo.invalidateViews()
        }
    }

    fun eliminar() {
        val posicion : Int      = FuncionesUtiles.posicionCabecera
        val tamanho  : Int      = FuncionesUtiles.listaCabecera.size
        val estado   : String   = FuncionesUtiles.listaCabecera[posicion]["ESTADO"].toString()
        val semana   : Int      = FuncionesUtiles.listaCabecera[posicion]["SEMANA"].toString().toInt()
        if (posicion > -1 && tamanho > 0 && estado == "P") {
            val sql = (" delete from svm_ruteo_semanal "
                    + "  where ESTADO = 'P'"
                    + "    and strftime('%W',date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  )) = '" + (semana  - 1) + "'")
            funcion.ejecutar(sql,this)
            buscar()
        }
    }

    fun consultar(){
        ProgramarRuteoT.fecha = FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["MIN_FECHA"].toString()
        val consulta = Intent(this, ProgramarRuteoT::class.java)
        startActivity(consulta)
    }

}

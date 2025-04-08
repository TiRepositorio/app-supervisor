package apolo.supervisor.com.visitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.DialogoCalendario
import apolo.supervisor.com.utilidades.FuncionesConsultor
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_clientes_visitados.*

class MoldeConsulta : AppCompatActivity() {

    var calendario : DialogoCalendario = DialogoCalendario(this)
    var funcion : FuncionesUtiles = FuncionesUtiles(this)
    private var consultor: FuncionesConsultor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.molde_consulta)

        inicializarElementos()
    }

    fun inicializarElementos(){
        consultor = FuncionesConsultor(this,etDesde,etHasta,rbPendiente,rbEnviado,rbTodo,imgBuscar,btModificar,btConsultar,btEliminar,rgFiltro)
        consultor!!.setRadioButtonText("Pendiente-ESTADO = 'P'","Enviado-ESTADO = 'E'","Todo-ESTADO LIKE '%%'")
        etDesde.setText(funcion.getFechaActual())
        etHasta.setText(funcion.getFechaActual())
        etDesde.setOnClickListener{calendario.onCreateDialog(1,etDesde,etDesde)!!.show()}
        etHasta.setOnClickListener{calendario.onCreateDialog(1,etHasta,etDesde)!!.show()}
    }
}

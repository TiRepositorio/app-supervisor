package apolo.supervisor.com.utilidades

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import apolo.supervisor.com.R
import kotlinx.android.synthetic.main.busqueda.*

class DialogoBusqueda {

    companion object{
        var posicion: Int = 0
    }

    constructor(context: Context,tabla:String,codigo:String,descripcion:String,order:String, condicion:String,tvResultado:TextView,tvResultado2:TextView?){
        this.context = context
        this.codigo = codigo
        this.descripcion = descripcion
        this.campos = ""
        this.tabla = tabla
        this.order = order
        this.condicion = condicion
        this.tvResultado = tvResultado
        this.tvResultado2 = tvResultado2
    }

    constructor(context: Context,tabla:String,codigo:String,descripcion:String,campos:String,order:String, condicion:String,tvResultado:TextView,tvResultado2:TextView?){
        this.context = context
        this.codigo = codigo
        this.descripcion = descripcion
        this.campos = campos
        this.tabla = tabla
        this.order = order
        this.condicion = condicion
        this.tvResultado = tvResultado
        this.tvResultado2 = tvResultado2
    }

    var context: Context
    var codigo: String
    var descripcion: String
    var tabla: String
    var condicion: String
    var order: String
    var tvResultado: TextView
    private var tvResultado2: TextView?
    var lista: ArrayList<HashMap<String,String>> = ArrayList()
    var funcion = FuncionesUtiles()
    var campos : String
    lateinit var dialogo: Dialog


    fun cargarDialogo(){
        dialogo = Dialog(context)
        if (campos == ""){
            dialogo.setContentView(R.layout.busqueda)
            buscar(dialogo.etBuscar)
            mostrar(dialogo.lvBuscar)
        } else {
            dialogo.setContentView(R.layout.busqueda4)
            buscar(dialogo.etBuscar)
            mostrarCampos(dialogo.lvBuscar)
        }
        dialogo.imgBuscar.setOnClickListener{
            buscar(dialogo.etBuscar)
            if (campos == ""){
                mostrar(dialogo.lvBuscar)
            } else {
                mostrarCampos(dialogo.lvBuscar)
            }
        }
        if (tvResultado2 == null){
            seleccionar(dialogo.btSeleccionar,tvResultado)
        } else {
            seleccionar(dialogo.btSeleccionar,tvResultado,tvResultado2)
        }

        dialogo.show()
    }

    fun cargarDialogo(cancelable:Boolean){
        dialogo = Dialog(context)
        if (campos == ""){
            dialogo.setContentView(R.layout.busqueda)
            buscar(dialogo.etBuscar)
            mostrar(dialogo.lvBuscar)
        } else {
            dialogo.setContentView(R.layout.busqueda4)
            buscar(dialogo.etBuscar)
            mostrarCampos(dialogo.lvBuscar)
        }
        dialogo.imgBuscar.setOnClickListener{
            buscar(dialogo.etBuscar)
            if (campos == ""){
                mostrar(dialogo.lvBuscar)
            } else {
                mostrarCampos(dialogo.lvBuscar)
            }
        }
        if (tvResultado2 == null){
            seleccionar(dialogo.btSeleccionar,tvResultado)
        } else {
            seleccionar(dialogo.btSeleccionar,tvResultado,tvResultado2)
        }
        dialogo.setCancelable(cancelable)
        dialogo.show()
    }


    private fun buscar(etBuscar:EditText){
        var sql = "SELECT DISTINCT $codigo, $descripcion"
        if (campos != ""){
            sql = "SELECT DISTINCT $campos"
        }
        sql += "  FROM " + tabla +
                " WHERE (" + codigo.split(" ")[0] + " LIKE '%" + etBuscar.text + "%' " +
                "    OR " + descripcion + " LIKE '%" + etBuscar.text + "%') "

        if (condicion != ""){
            sql += condicion
        }
        sql += " ORDER BY $order"

        cargarDatos(funcion.consultar(sql))
    }

    private fun cargarDatos(cursor:Cursor){
        lista = ArrayList()
        for (i in 0 until cursor.count){
            val datos = HashMap<String,String>()
            if (campos == ""){
                datos[codigo.split(" ")[0]] = funcion.dato(cursor,codigo.split(" ")[0])
                datos[descripcion] = funcion.dato(cursor,descripcion)
            } else {
                for (j in campos.split(",").indices){
                    datos[campos.split(",")[j]] = funcion.dato(cursor,campos.split(",")[j])
                }
            }
            lista.add(datos)
            cursor.moveToNext()
        }
    }

    private fun mostrar(lvBusqueda: ListView){
        funcion.vistas = intArrayOf(R.id.tvb1,R.id.tvb2)
        funcion.valores = arrayOf(codigo.split(" ")[0],descripcion)
        val adapter:Adapter.AdapterBusqueda =
                    Adapter.AdapterBusqueda(context
                                            ,lista
                                            ,R.layout.busqueda_lista
                                            ,funcion.vistas
                                            ,funcion.valores)
        lvBusqueda.adapter = adapter
        lvBusqueda.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            posicion = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvBusqueda.invalidateViews()
        }
    }

    private fun mostrarCampos(lvBusqueda: ListView){
        funcion.vistas = intArrayOf(R.id.tvb1,R.id.tvb2,R.id.tvb3,R.id.tvb4,R.id.tvb5)
        funcion.valores = arrayOf(campos.split(",")[0],campos.split(",")[1],campos.split(",")[2],
                                  campos.split(",")[3],campos.split(",")[4])
        val adapter:Adapter.AdapterBusqueda =
            Adapter.AdapterBusqueda(context
                ,lista
                ,R.layout.busqueda4_lista
                ,funcion.vistas
                ,funcion.valores)
        lvBusqueda.adapter = adapter
        lvBusqueda.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            posicion = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvBusqueda.invalidateViews()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun seleccionar(btSeleccionar: Button, textView: TextView){
        btSeleccionar.setOnClickListener{
            if (lista.size>0){
                if (campos == ""){
                    textView.text = lista[posicion][codigo.split(" ")[0]] + " - " + lista[posicion][descripcion]
                } else {
                    textView.text = lista[posicion][campos.split(",")[0]] +
                                    "-" + lista[posicion][campos.split(",")[1]] +
                                    "-" + lista[posicion][campos.split(",")[2]] +
                                    "-" + lista[posicion][campos.split(",")[3]] +
                                    "-" + lista[posicion][campos.split(",")[4]]
                }
            }
            dialogo.dismiss()
            posicion = 0
        }
    }

    private fun seleccionar(btSeleccionar: Button, textView: TextView,textView2: TextView?){
        btSeleccionar.setOnClickListener{
            if (lista.size>0){
                textView.text = lista[posicion][codigo.split(" ")[0]]
                textView2!!.text = lista[posicion][descripcion]
            }
            dialogo.dismiss()
            posicion = 0
        }
    }

}
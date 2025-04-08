package apolo.supervisor.com.ventas

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_promociones.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class Promociones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promociones)

        inicializarElementos()
    }

    companion object{
        var codListaPrecio : String = ""
        var condicionVenta : String = ""
        var posPromocion : Int = 0
        var promocion = true
        @SuppressLint("StaticFieldLeak")
        lateinit var etAccionPromo : EditText
    }

    lateinit var funcion : FuncionesUtiles
    private lateinit var listaPromociones : ArrayList<HashMap<String,String>>

    private fun inicializarElementos(){
        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,llBuscar,spBuscar,etBuscar,btBuscar)
        funcion.cargarTitulo(R.drawable.ic_promocion,"Promociones")
        funcion.addItemSpinner(this,"Número-Descripción","NRO_PROMOCION-DESCRIPCION")

        buscarPromociones()
        btBuscar.setOnClickListener{buscarPromociones()}
        etAccionPromo = accionPromocion
        inicializarEtAccion(accionPromocion)
        inicializarEtAccion(etAccionPromo)
    }

    private fun buscarPromociones(){
        val campos : String = " DISTINCT NRO_PROMOCION, TIP_CLIENTE, DESCRIPCION, COMENTARIO, COD_CONDICION_VENTA," +
                            " FEC_VIGENCIA , IND_TIPO   ,  IND_COMBO "
        val groupBy : String  = " NRO_PROMOCION, TIP_CLIENTE, DESCRIPCION, COMENTARIO, COD_CONDICION_VENTA," +
                             " FEC_VIGENCIA , IND_TIPO   , CANT_VENTA , IND_COMBO "
        val orderBy = " CAST(NRO_PROMOCION AS INTEGER) asc "
        val tabla = " svm_promociones_art_cab "
        val where : String  = (" AND (COD_CONDICION_VENTA   = '$condicionVenta'             or trim(COD_CONDICION_VENTA)  = '' or COD_CONDICION_VENTA IS NULL) "
                            +  "    AND (TIP_CLIENTE        = '${ListaClientes.tipCliente}' or trim(TIP_CLIENTE)          = '' or TIP_CLIENTE IS NULL)"
                            +  "    AND (COD_LISTA_PRECIO   = '$codListaPrecio'             or trim(COD_LISTA_PRECIO)     = '' or COD_LISTA_PRECIO IS NULL)"
                            +  "    AND  COD_VENDEDOR       = '${ListaClientes.codVendedor}' ")
        cbCombo.isChecked = false
        cbMonto.isChecked = false
        cbCantidad.isChecked = false
        cbBonificacion.isChecked = false
        listaPromociones = ArrayList()
        funcion.cargarLista(listaPromociones,funcion.buscar(tabla,campos,groupBy,orderBy,where))
        mostrar()
    }

    private fun mostrar(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2,R.id.tv3)
        funcion.valores = arrayOf("FEC_VIGENCIA", "DESCRIPCION", "NRO_PROMOCION")
        val adapter: Adapter.AdapterPromociones =
            Adapter.AdapterPromociones(this
                ,listaPromociones
                ,R.layout.ven_pro_lista_promociones
                ,funcion.vistas
                ,funcion.valores)
        lvPromocionCabecera.adapter = adapter
        lvPromocionCabecera.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
                posPromocion = position
                view.setBackgroundColor(Color.parseColor("#aabbaa"))
                lvPromocionCabecera.invalidateViews()
            tvComentario.text = listaPromociones[posPromocion]["COMENTARIO"]
        }
        if (listaPromociones.size>0){
            tvComentario.text = listaPromociones[posPromocion]["COMENTARIO"]
        } else {
            tvComentario.text = ""
        }
    }

    fun buscarPromociones(view: View) {
        FuncionesUtiles.posicionCabecera = 0
        posPromocion = 0
        val campos : String = " DISTINCT NRO_PROMOCION, TIP_CLIENTE, DESCRIPCION, COMENTARIO, COD_CONDICION_VENTA," +
                " FEC_VIGENCIA , IND_TIPO   , IND_COMBO "
        val groupBy : String  = " NRO_PROMOCION, TIP_CLIENTE, DESCRIPCION, COMENTARIO, COD_CONDICION_VENTA," +
                " FEC_VIGENCIA , IND_TIPO   , CANT_VENTA , IND_COMBO "
        val orderBy = " CAST(NRO_PROMOCION AS INTEGER) asc "
        val tabla = " svm_promociones_art_cab "
        var where : String  = (" AND (COD_CONDICION_VENTA   = '$condicionVenta'             or trim(COD_CONDICION_VENTA)  = '' or COD_CONDICION_VENTA IS NULL) "
                +  "    AND (TIP_CLIENTE        = '${ListaClientes.tipCliente}' or trim(TIP_CLIENTE)          = '' or TIP_CLIENTE IS NULL)"
                +  "    AND (COD_LISTA_PRECIO   = '$codListaPrecio'             or trim(COD_LISTA_PRECIO)     = '' or COD_LISTA_PRECIO IS NULL)"
                +  "    AND  COD_VENDEDOR       = '${ListaClientes.codVendedor}' ")
        if (view.id != cbCombo.id){
            cbBonificacion.isChecked = false
            cbMonto.isChecked = false
            cbCantidad.isChecked = false
            findViewById<CheckBox>(view.id).isChecked = true
        }
        if (cbCombo.isChecked){
            where += " AND IND_COMBO = 'S' "
        }
        if (cbBonificacion.isChecked){
            where += " AND IND_TIPO = 'B' "
        }
        if (cbMonto.isChecked){
            where += " AND IND_TIPO = 'M' "
        }
        if (cbCantidad.isChecked){
            where += " AND IND_TIPO = 'F' "
        }
        where += if (promocion){
            " AND IND_PROM = 'S' "
        } else {
            " AND IND_ART = 'S' "
        }
        listaPromociones = ArrayList()
        funcion.cargarLista(listaPromociones,funcion.buscar(tabla,campos,groupBy,orderBy,where))
        mostrar()
    }

    fun cargarPromocion(view: View) {
        view.id
        if (!verificaPromoCargada(posPromocion)){
            lvPromocionCabecera.invalidateViews()
            return
        }
        val dialogo = DialogoPromocion(this,"NRO_PROMOCION", "","DESC_ARTICULO",accionPromocion)
        if (listaPromociones[posPromocion]["IND_TIPO"] == "B"){
            dialogo.dialogoComboBonificacion(listaPromociones[posPromocion]["NRO_PROMOCION"].toString().trim())
        } else {
            dialogo.dialogoDescuentoM(listaPromociones[posPromocion]["NRO_PROMOCION"].toString().trim())
        }
    }

    private fun inicializarEtAccion(editText: EditText){
        editText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (editText.text.toString().trim() == "CERRAR"){
                    finish()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    private fun verificaPromoCargada(position:Int):Boolean{
        val sql : String = ("SELECT DISTINCT COD_ARTICULO FROM vt_pedidos_det "
                +  " WHERE NUMERO = '${Pedidos.maximo}' "
                +  "   AND COD_VENDEDOR = '${ListaClientes.codVendedor}' "
                +  "   AND NRO_PROMOCION = '${listaPromociones[position]["NRO_PROMOCION"]}' "
                +  " ")
        val funcion = FuncionesUtiles(this)
        if (funcion.consultar(sql).count > 0){
            funcion.mensaje(this,"¡Atención!","La promoción ya fue cargada.")
            return false
        }
        return true
    }

}
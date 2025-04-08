package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import apolo.supervisor.com.R
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_evolucion_diaria_de_ventas.*

class EvolucionDiariaDeVentas : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        val funcion : FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        lateinit var cursor: Cursor
        lateinit var vistas: IntArray
        lateinit var valores: Array<String>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evolucion_diaria_de_ventas)

        inicializarElementos()
    }

    fun inicializarElementos(){
        cargar()
        if (FuncionesUtiles.listaCabecera.size==0){
            AvanceDeComisiones.funcion.toast(this,"No hay datos para mostrar")
            finish()
            return
        }
        mostrar()
    }

    @SuppressLint("Recycle")
    fun cargar(){
        val sql = ("SELECT COD_EMPRESA  , COD_VENDEDOR	    , DESC_VENDEDOR, PEDIDO_2_ATRAS, PEDIDO_1_ATRAS,"
                    + "           TOTAL_PEDIDOS, TOTAL_FACT  	    , META_VENTA   , META_LOGRADA  , PROY_VENTA    ,"
                    + "		      TOTAL_REBOTE , TOTAL_DEVOLUCION   , CANT_CLIENTES, CANT_POSIT    , EF_VISITA 	   ,"
                    + "		      DIA_TRAB	   , PUNTAJE			, "
                    + "           IFNULL(((CAST(CANT_POSIT AS DOUBLE)/CAST(CANT_CLIENTES AS DOUBLE))*100),0.0) COBERTURA "
                    + "      FROM svm_evolucion_diaria_venta  ")
        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
        } catch (e : Exception){
            e.message
            return
        }

        FuncionesUtiles.listaCabecera = ArrayList()

        for (i in 0 until cursor.count){
            datos = HashMap()
            datos["COD_EMPRESA"] = funcion.dato(cursor,"COD_EMPRESA")
            datos["COD_VENDEDOR"] = funcion.dato(cursor,"COD_VENDEDOR")
            datos["DESC_VENDEDOR"] = funcion.dato(cursor,"DESC_VENDEDOR")
            datos["PEDIDO_2_ATRAS"] = funcion.entero(funcion.dato(cursor,"PEDIDO_2_ATRAS"))
            datos["PEDIDO_1_ATRAS"] = funcion.entero(funcion.dato(cursor,"PEDIDO_1_ATRAS"))
            datos["TOTAL_PEDIDOS"] = funcion.entero(funcion.dato(cursor,"TOTAL_PEDIDOS"))
            datos["TOTAL_FACT"] = funcion.entero(funcion.dato(cursor,"TOTAL_FACT"))
            datos["META_VENTA"] = funcion.entero(funcion.dato(cursor,"META_VENTA"))
            datos["META_LOGRADA"] = funcion.porcentaje(funcion.dato(cursor,"META_LOGRADA"))
            datos["PROY_VENTA"] = funcion.porcentaje(funcion.dato(cursor,"PROY_VENTA"))
            datos["TOTAL_REBOTE"] = funcion.porcentaje(funcion.dato(cursor,"TOTAL_REBOTE"))
            datos["TOTAL_DEVOLUCION"] = funcion.porcentaje(funcion.dato(cursor,"TOTAL_DEVOLUCION"))
            datos["CANT_CLIENTES"] = funcion.entero(funcion.dato(cursor,"CANT_CLIENTES"))
            datos["CANT_POSIT"] = funcion.entero(funcion.dato(cursor,"CANT_POSIT"))
            datos["EF_VISITA"] = funcion.porcentaje(funcion.dato(cursor,"EF_VISITA"))
            datos["DIA_TRAB"] = funcion.decimal(funcion.dato(cursor,"DIA_TRAB"))
            datos["PUNTAJE"] = funcion.decimal(funcion.dato(cursor,"PUNTAJE"))
            datos["SURTIDO_EF"] = funcion.decimal(funcion.dato(cursor,"SURTIDO_EF"))
            datos["COBERTURA"] = funcion.porcentaje(funcion.dato(cursor,"COBERTURA"))
            if (datos["COD_VENDEDOR"].toString().trim() == "0" ||
                datos["COD_VENDEDOR"].toString().trim() == "null"
            ){
                datos["COD_VENDEDOR"] = ""
            }
            if (datos["DESC_VENDEDOR"].toString().trim() == "0" ||
                datos["DESC_VENDEDOR"].toString().trim() == "null"
            ){
                datos["DESC_VENDEDOR"] = "TOTAL:"
            }
            FuncionesUtiles.listaCabecera.add(datos)
            cursor.moveToNext()
        }
    }

    fun mostrar(){
        valores = arrayOf("COD_VENDEDOR"    ,"DESC_VENDEDOR"    ,"PEDIDO_2_ATRAS"   ,
                          "PEDIDO_1_ATRAS"  ,"TOTAL_PEDIDOS"    ,"TOTAL_FACT"       ,
                          "META_VENTA"      ,"META_LOGRADA"     ,"PROY_VENTA"       ,
                          "TOTAL_REBOTE"    ,"TOTAL_DEVOLUCION" ,"CANT_CLIENTES"    ,
                          "CANT_POSIT"      ,"COBERTURA"        ,"EF_VISITA"        ,
                          "DIA_TRAB"        ,"PUNTAJE"
                         )
        vistas = intArrayOf( R.id.tv1 ,R.id.tv2 ,R.id.tv3 ,R.id.tv4 ,R.id.tv5 ,R.id.tv6 ,
                             R.id.tv7 ,R.id.tv8 ,R.id.tv9 ,R.id.tv10,R.id.tv11,R.id.tv12,
                             R.id.tv13,R.id.tv14,R.id.tv15,R.id.tv16,R.id.tv17
                            )
       val adapter: Adapter.AdapterGenericoCabecera = Adapter.AdapterGenericoCabecera(this,
           FuncionesUtiles.listaCabecera, R.layout.inf_evo_dia_lista_evolucion,vistas,valores)

        lvEvolucionDiariaDeVentas.adapter = adapter
        lvEvolucionDiariaDeVentas.setOnItemClickListener { _: ViewGroup, view: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            view.setBackgroundColor(Color.parseColor("#aabbaa"))
            lvEvolucionDiariaDeVentas.invalidateViews()
        }
    }
}

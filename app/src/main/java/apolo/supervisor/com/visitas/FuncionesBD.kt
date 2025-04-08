package apolo.supervisor.com.visitas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import apolo.supervisor.com.utilidades.FuncionesUtiles


class FuncionesBD(var context: Context) {

    var funcion : FuncionesUtiles = FuncionesUtiles(context)
    var values = ContentValues()

    fun guardarVisitaCabecera(cursor: Cursor,llegada:String,salida:String, comentario: String){
        if (cursor.count>0){
            values = ContentValues()
            values.put("COD_SUPERVISOR", funcion.dato(cursor,"COD_SUPERVISOR"))
            values.put("DESC_SUPERVISOR", funcion.dato(cursor,"DESC_SUPERVISOR"))
            values.put("COD_VENDEDOR", funcion.dato(cursor,"COD_VENDEDOR"))
            values.put("DESC_VENDEDOR", funcion.dato(cursor,"DESC_VENDEDOR"))
            values.put("DESC_ZONA", funcion.dato(cursor,"DESC_VENDEDOR"))
            values.put("HORA_LLEGADA", llegada)
            values.put("HORA_SALIDA", salida)
            values.put("FECHA_VISITA", funcion.getFechaActual())
            values.put("COD_CLIENTE", funcion.dato(cursor,"COD_CLIENTE"))
            values.put("COD_SUBCLIENTE", funcion.dato(cursor,"COD_SUBCLIENTE"))
            values.put("DESC_SUBCLIENTE", funcion.dato(cursor,"DESC_SUBCLIENTE"))
            values.put("ESTADO", "P")
            values.put("COMENTARIO", comentario)

            funcion.insertar("svm_analisis_cab",values)

        }
    }

    fun guardaVisitaDetalle(id:String,cursor:Cursor){
        for(i in 0 until cursor.count){
            values = ContentValues()
            values.put("ID_CAB", id)
            values.put("COD_MOTIVO", funcion.dato(cursor,"COD_MOTIVO"))
            if (funcion.dato(cursor,"ESTADO") == "S"){
                values.put("RESPUESTA", "N")
            } else {
                values.put("RESPUESTA", "1")
            }
            funcion.insertar("svm_analisis_det", values)
            cursor.moveToNext()
        }


    }

}
package apolo.supervisor.com.visitas

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.location.LocationManager
import android.os.AsyncTask
import android.widget.Toast
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.FuncionesUtiles


class EnviarMarcacion {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        var respuesta:String = ""
        var cabCliente : String = ""
        var detCliente : String = ""
        var codSupervisor : String = ""
        var fechaActual : String = ""
        var id : String = ""
        fun validaGPS(context: Context): Boolean {
            if (MainActivity.lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return true
            }
            var con = 0
            while (con < 5) {
                try {
                    val intent = Intent("android.intent.action.MAIN")
                    intent.addCategory("android.intent.category.LAUNCHER")
                    intent.component = ComponentName
                        .unflattenFromString("com.gps.root/com.gps.root.MainActivity")
                    context.startActivity(intent)
                    return true
                } catch (e: java.lang.Exception) {
                    val err = e.message
                    if (con == 0) {
                        try {
                            Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                        } catch (es: java.lang.Exception) {
                        }
                    }
                    con++
                }
            }
            return false
        }
        fun apagaGPS(context: Context) {
            if (MainActivity.lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    val intent = Intent("android.intent.action.MAIN")
                    intent.addCategory("android.intent.category.LAUNCHER")
                    intent.component = ComponentName
                        .unflattenFromString("com.gps.root/com.gps.root.MainActivity")
                    context.startActivity(intent)
                } catch (e: java.lang.Exception) {
                }
            }
        }
        @SuppressLint("Recycle")
        fun procesaEnviaMarcaciones() {
            val select =
                ("Select a.id, a.FECHA, a.COD_PROMOTOR, a.ESTADO, a.LATITUD, a.LONGITUD, a.COD_CLIENTE, a.COD_SUBCLIENTE   "
                        + "  from vt_marcacion_ubicacion a "
                        + "  where a.ESTADO = 'P' "
                        + "    and a.TIPO = 'G' "
                        + "  group by a.id, a.FECHA, a.COD_PROMOTOR, a.ESTADO, a.LATITUD, a.LONGITUD "
                        + "  order by a.id desc ")
            cabCliente = "INSERT ALL"
            val detalle1 = (" INTO fv_posicion_supervisor (  cod_empresa		,"
                    + "cod_supervisor	," + "fec_movimiento 	," + "latitud    		,"
                    + "longitud     	, " +
                    "cod_cliente     ," +
                    "cod_subcliente  )  " +
                    " VALUES(")
            val cursor: Cursor = MainActivity.bd!!.rawQuery(select, null)
            cursor.moveToFirst()
            val nreg = cursor.count
            for (i in 0 until nreg) {
                val codEmpresa = FuncionesUtiles.usuario["COD_EMPRESA"]
                val codVendedor: String = FuncionesUtiles.usuario["PASS"].toString()
                val fecha: String = cursor.getString(cursor.getColumnIndex("FECHA"))
                val latitud: String = cursor.getString(cursor.getColumnIndex("LATITUD"))
                val longitud: String = cursor.getString(cursor.getColumnIndex("LONGITUD"))
                val codCliente: String = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
                val codSubcliente: String = cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"))
                cabCliente += "$detalle1'$codEmpresa','$codVendedor"
                cabCliente += ("',to_date('" + fecha + "','dd/MM/yyyy hh24:mi:ss'),'"
                        + latitud + "','" + longitud + "',")
                cabCliente += "'$codCliente','$codSubcliente') "
                cursor.moveToNext()
            }
            cabCliente += " SELECT * FROM dual "
            if (nreg == 0) {
                // Toast.makeText(SincronizarCombinado.this,
                // "No existe ninguna marcacion pendiente de envio",
                // Toast.LENGTH_LONG).show();
            } else {
                val mensaje = MainActivity.conexionWS.enviaMarcacionesPendientes(FuncionesUtiles.usuario["PASS"].toString(),cabCliente).split("\\*")
                if (mensaje.size != 1) {
                    if (mensaje[0] == "01") {
                        val update =
                            "update vt_marcacion_ubicacion set ESTADO = 'E' where ESTADO = 'P' and TIPO = 'G'"
                        MainActivity.bd!!.execSQL(update)
                    }
                }
            }
        }

    }

    var cabecera : HashMap<String,String> = HashMap()
    var detalle  : HashMap<String,String> = HashMap()
    var funcion : FuncionesUtiles = FuncionesUtiles(context)
    lateinit var alist: ArrayList<HashMap<String, String>>
    lateinit var cursor : Cursor

    fun enviar(){
        cargarDatos()
        if (cabecera["HORA_LLEGADA"].toString().trim() == "" || cabecera["HORA_SALIDA"].toString().trim() == ""){
            funcion.mensaje("Error","Debe marcar entrada y salida del cliente")
            return
        }
        if (cabecera["ESTADO"].toString().trim() != "P"){
            return
        }
        EnviarMarcacion().execute()
    }

    private fun cargarDatos(){
        var sql = "SELECT * FROM svm_analisis_cab WHERE id = '$id' "
        cargarCabecera(funcion.consultar(sql))
        sql = (" SELECT a.COD_CLIENTE, a.COD_SUBCLIENTE, a.COD_SUPERVISOR, a.COD_VENDEDOR, b.COD_MOTIVO, b.RESPUESTA, c.ESTADO, c.COD_GRUPO, a.COMENTARIO "
                    + " FROM svm_analisis_cab a, "
                    + "      svm_analisis_det b,"
                    + "		 svm_motivo_analisis_cliente c  "
                    + " WHERE a.id = b.ID_CAB "
                    + "   and a.id = '" + id + "' "
                    + "   and a.COD_VENDEDOR   = c.COD_VENDEDOR "
                    + "	  and b.COD_MOTIVO 	   = c.COD_MOTIVO ")
        cargarDetalle(funcion.consultar(sql))
    }

    fun cargarCabecera(cursor:Cursor){
        cabecera = HashMap()
        for (i in 0 until cursor.count){
            for (j in 0 until cursor.columnCount){
                cabecera[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
            }
        }

        val fechaMovimiento = "to_date('" + cabecera["FECHA_VISITA"].toString() + "','dd/MM/yyyy')"
        val fechaIni = ("to_date('" + cabecera["FECHA_VISITA"].toString() + " " + cabecera["HORA_LLEGADA"].toString() + "','dd/MM/yyyy hh24:mi:ss')")
        val fechaFin = ("to_date('" + cabecera["FECHA_VISITA"].toString() + " " + cabecera["HORA_SALIDA"].toString() + "','dd/MM/yyyy hh24:mi:ss')")

        cabCliente = "${FuncionesUtiles.usuario["COD_EMPRESA"]},'" + cabecera["COD_CLIENTE"] +
                     "','" + cabecera["COD_SUBCLIENTE"] +
                     "','" + cabecera["COD_SUPERVISOR"] +
                     "','" + cabecera["COD_VENDEDOR"] +
                     "'," + fechaMovimiento + "," +  fechaIni + "," + fechaFin +
                     ",'" + cabecera["COMENTARIO"] + "';"
        codSupervisor = cabecera["COD_SUPERVISOR"].toString()
    }

    fun cargarDetalle(cursor: Cursor){
        detCliente = ""

        for (i in 0 until cursor.count){
            detalle = HashMap()
            for (j in 0 until cursor.columnCount){
                detalle[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
            }
            val estado: String = detalle["ESTADO"].toString()
            var resp: String
            var punt = ""

            if (estado == "M") {
                resp = "M"
                punt = detalle["RESPUESTA"].toString()
            } else {
                resp = detalle["RESPUESTA"].toString()
            }

            detCliente += "${FuncionesUtiles.usuario["COD_EMPRESA"]},'"  + detalle["COD_CLIENTE"] +
                    "','" + detalle["COD_SUBCLIENTE"] +
                    "','" + detalle["COD_SUPERVISOR"] +
                    "','" + detalle["COD_VENDEDOR"] +
                    "','" + detalle["COD_GRUPO"] +
                    "','" + detalle["COD_MOTIVO"] +
                    "','" + resp + "','" + punt + "';"
        }

    }

    class EnviarMarcacion : AsyncTask<Void?, Void?, Void?>() {
        lateinit var dialogo : ProgressDialog
        override fun onPreExecute() {
            dialogo = ProgressDialog.show(context, "Un momento...","Enviando Reporte...", true)
        }

        override fun doInBackground(vararg params: Void?): Void?{
            MainActivity.conexionWS.setMethodName("ProcesaSeguimiento")
            respuesta = MainActivity.conexionWS.procesaSeguimientoAct(cabCliente,detCliente,codSupervisor,fechaActual)
//            respuesta = "01*Guardado con exito";
            return null
        }

        override fun onPostExecute(unused: Void?) {
            dialogo.dismiss()
            if (respuesta.indexOf("Unable to resolve host") > -1) {
                Toast.makeText(context,"Verifique su conexion a internet y vuelva a intentarlo",Toast.LENGTH_SHORT).show()
                return
            }
            if (respuesta.indexOf("01*") >= 0) {
                MainActivity.bd!!.beginTransaction()
                val update = ("update svm_analisis_cab set ESTADO = 'E' "
                        + " WHERE id = '" + id + "'"
                        + "   and ESTADO = 'P'")
                MainActivity.bd!!.execSQL(update)
                MainActivity.bd!!.setTransactionSuccessful()
                MainActivity.bd!!.endTransaction()
            }
            Toast.makeText(context, respuesta, Toast.LENGTH_LONG).show()
        }

    }

    //Enviar pendientes
    fun enviarMarcaciones() {
        obtenerMarcaciones()
        //genera cabecera cliente
        for (position in 0 until alist.size) {
            id = alist[position]["id"].toString()
            var horas: String
            val horae: String = alist[position]["HORA_LLEGADA"].toString()
            horas = alist[position]["HORA_SALIDA"].toString()
            if (horae == "" || horas == "") {
                if (horae != "") {
                    horas = horae
                    alist[position]["HORA_SALIDA"] = alist[position]["HORA_LLEGADA"].toString()
                } else {
                    Toast.makeText(context,"Debe marcar la entrada y salida del cliente para enviar",Toast.LENGTH_SHORT).show()
                    return
                }
            }
            cabCliente = ""
            val fechaMovimiento = "to_date('" + alist[position]["FECHA_VISITA"].toString() + "','dd/MM/yyyy')"
            val fechaIni = "to_date('" + alist[position]["FECHA_VISITA"].toString() + " " + horae + "','dd/MM/yyyy hh24:mi:ss')"
            val fechaFin = "to_date('" + alist[position]["FECHA_VISITA"].toString() + " " + horas + "','dd/MM/yyyy hh24:mi:ss')"
            cabCliente = "${FuncionesUtiles.usuario["COD_EMPRESA"]},'" + alist[position]["COD_CLIENTE"].toString() +
                    "','" + alist[position]["COD_SUBCLIENTE"].toString() +
                    "','" + alist[position]["COD_SUPERVISOR"].toString() +
                    "','" + alist[position]["COD_VENDEDOR"].toString() +
                    "'," + fechaMovimiento + "," + fechaIni + "," + fechaFin +
                    ",'" + alist[position]["COMENTARIO"].toString() + "';"
            codSupervisor = alist[position]["COD_SUPERVISOR"].toString()
            fechaActual = alist[position]["FECHA_VISITA"].toString()
            EnviarMarcacionesPendientes().execute()
        }
    }

    private fun obtenerMarcaciones() {
        alist = ArrayList()
        var where: String = "ESTADO=" + "'" + "P" + "'"
        where += (" and (date(substr(FECHA_VISITA,7) || '-' ||"
                + "				substr(FECHA_VISITA,4,2) || '-' ||"
                + "				substr(FECHA_VISITA,1,2))) 		< (select date('now'))	--between date('2019-08-05') and (select date('now'))")
        alist = ArrayList()
        try {
            val sql = "select * from svm_analisis_cab where $where "
            cursor = funcion.consultar(sql)
            funcion.cargarLista(alist,cursor)
            for (i in 0 until alist.size) {
            }
            //			return;
        } catch (e: Exception) {
            funcion.mensaje(context,"Error", "D " + e.message)
            return
        }
    }

    private class EnviarMarcacionesPendientes : AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            respuesta = MainActivity.conexionWS.procesaSeguimientoAct(
                cabCliente,
                detCliente,
                FuncionesUtiles.usuario["PASS"].toString(),
                fechaActual
            )
            if (respuesta.indexOf("Unable to resolve host") > -1) {
//                Toast.makeText(context,"Verifique su conexion a internet y vuelva a intentarlo",Toast.LENGTH_SHORT).show()
                return
            }
            if (respuesta.indexOf("01*") >= 0) {
                MainActivity.bd!!.beginTransaction()
                val update = ("update svm_analisis_cab set ESTADO = 'E' "
                        + " WHERE id = '" + id + "'"
                        + "   and ESTADO = 'P'")
                MainActivity.bd!!.execSQL(update)
                MainActivity.bd!!.setTransactionSuccessful()
                MainActivity.bd!!.endTransaction()
            }
//            Toast.makeText(context, respuesta, Toast.LENGTH_LONG).show()
//            respuesta = "01*Guardado con exito";
            return
        }

        override fun doInBackground(vararg params: Void?): Void? {
//            respuesta = MainActivity.conexionWS.procesaSeguimientoAct(
//                cabCliente,
//                detCliente,
//                FuncionesUtiles.usuario.get("PASS").toString(),
//                fechaActual
//            )
            //resp_WS = "01*Guardado con exito";
            return null
        }

        override fun onPostExecute(unused: Void?) {

//			pbarDialog.dismiss();

        }
    }

}
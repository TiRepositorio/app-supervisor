package apolo.supervisor.com.visitas

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.widget.Toast
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.FuncionesDispositivo
import apolo.supervisor.com.utilidades.FuncionesUtiles


class EnviarRuteo {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        var respuesta:String = ""
        lateinit var mensaje :Array<String>
        var vruteo : String = ""
    }

    var cabecera : String = ""
    var detalle  : String = ""
    var funcion : FuncionesUtiles = FuncionesUtiles(context)
    var dispositivo : FuncionesDispositivo = FuncionesDispositivo(context)

    fun enviar(){
        if (!dispositivo.horaAutomatica()){
            return
        }
        if (!funcion.fechaRuteo(context)){
            return
        }
        if (!validaRuteoCargado()){
            return
        }
        cargarDatos()
        if (vruteo.trim() != ""){
            EnviarRuteo().execute()
        }
    }

    private fun cargarDatos(){
        val cursor: Cursor = funcion.consultar(sqlRuteo())
        cabecera = "INSERT INTO fv_sup_ruteo_semanal_cab (COD_EMPRESA, COD_SUPERVISOR, NRO_SEMANA, AÑO) " +
                   " VALUES ('" + funcion.dato(cursor,"COD_EMPRESA") + "'," +
                   "'" + FuncionesUtiles.usuario["PASS"] + "'," +
                   "'" + funcion.dato(cursor,"SEMANA") + "'," +
                   "'" + funcion.dato(cursor,"AÑO") + "')"
        vruteo = "BEGIN $cabecera;\n INSERT ALL"

        if (FuncionesUtiles.usuario["TIPO"].equals("M")){
            detalle = " INTO fv_sup_ruteo_semanal_det (  COD_EMPRESA," +
                                                        "COD_SUPERVISOR	," +
                                                        "COD_VENDEDOR 	," +
                                                        "FECHA			," +
                                                        "COD_CLIENTE	," +
                                                        "COD_SUBCLIENTE ," +
                                                        "NRO_SEMANA    ) " +
                                                        "VALUES("
        } else {
            detalle = " INTO fv_sup_ruteo_semanal_det (  COD_EMPRESA		," +
                    "COD_SUPERVISOR	," +
                    "COD_VENDEDOR 	," +
                    "FECHA			," +
                    "NRO_SEMANA      )  " +
                    "VALUES("
        }
        for (i in 0 until cursor.count) {
            val codEmpresa: String = cursor.getString(cursor.getColumnIndex("COD_EMPRESA"))
            val codSupervisor: String = cursor.getString(cursor.getColumnIndex("COD_SUPERVISOR"))
            val codVendedor: String = cursor.getString(cursor.getColumnIndex("COD_VENDEDOR"))
            val fecha: String = cursor.getString(cursor.getColumnIndex("FECHA"))
            val semana: String = cursor.getString(cursor.getColumnIndex("SEMANA"))
            val codCliente: String = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
            val codSubcliente: String = cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"))
            if (FuncionesUtiles.usuario["TIPO"].equals("M")){
                vruteo += "$detalle'$codEmpresa','$codSupervisor','$codVendedor"
                vruteo += "',to_date('$fecha','dd/MM/yyyy hh24:mi:ss'),'$codCliente','$codSubcliente','$semana')"
            } else {
                vruteo += "$detalle'$codEmpresa','$codSupervisor','$codVendedor"
                vruteo += "',to_date('$fecha','dd/MM/yyyy hh24:mi:ss'),'$semana' ) "
            }
            cursor.moveToNext()
        }
        vruteo += " SELECT * FROM dual; END;  "
    }

    private fun sqlRuteo():String{
        return ("Select COD_EMPRESA, COD_SUPERVISOR, COD_VENDEDOR, FECHA, COD_CLIENTE, COD_SUBCLIENTE, cast( (strftime('%W', date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) ) as integer) SEMANA, strftime('%Y', date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) AÑO "
                + "  from svm_ruteo_semanal "
                + "  where ESTADO = 'P'"
                + "    and cast( (strftime('%W', date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) + 1) as integer) "
                + "														 = (select cast( (strftime('%W', date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  ) ) + 1) as integer)"
                + " from svm_ruteo_semanal"
                + " where ESTADO = 'P' "
                + " order by id desc"
                + " limit 1)")
    }

    private fun validaRuteoCargado():Boolean{
        if (funcion.consultar(sqlRuteo()).count==0){
            funcion.mensaje(context,"Atención!","Debe cargar al menos un ruteo para enviar.")
            return false
        }
        return true
    }

    class EnviarRuteo : AsyncTask<Void?, Void?, Void?>() {
        lateinit var dialogo : ProgressDialog
        override fun onPreExecute() {
            dialogo = ProgressDialog.show(context, "Un momento...","Enviando Reporte...", true)
        }

        override fun doInBackground(vararg params: Void?): Void? {
            mensaje = MainActivity.conexionWS.procesaRuteoSemanal(vruteo).split("*").toTypedArray()
//            mensaje = arrayOf("01","Enviado con exito")
            return null
        }

        override fun onPostExecute(unused: Void?) {
            dialogo.dismiss()
            if (mensaje.size != 1) {
                if (mensaje[0] == "01") {
                    val update = "update svm_ruteo_semanal set ESTADO = 'E' where ESTADO = 'P'   "
                    MainActivity.bd!!.execSQL(update)
                    Toast.makeText( context,"Enviado con exito", Toast.LENGTH_LONG).show()
                    return
                } else {
                    Toast.makeText(context, mensaje[1],Toast.LENGTH_LONG).show()
                    val func = FuncionesUtiles(context)
                    func.mensaje(context,"", mensaje[1])
                }
            } else {
                Toast.makeText(context, mensaje[0], Toast.LENGTH_LONG).show()
            }
        }
    }
}
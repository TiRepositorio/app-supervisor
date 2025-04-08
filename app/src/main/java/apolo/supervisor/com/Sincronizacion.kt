@file:Suppress("DEPRECATION")

package apolo.supervisor.com

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.utilidades.FuncionesUtiles
import apolo.supervisor.com.utilidades.SentenciasSQL
import apolo.supervisor.com.visitas.EnviarMarcacion
import kotlinx.android.synthetic.main.activity_sincronizacion.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.DecimalFormat
import java.util.*

class Sincronizacion : AppCompatActivity() {

    lateinit var imeiBD: String

    companion object{
        var tipoSinc: String = "T"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var nf = DecimalFormat("000")
    }

    var funcion : FuncionesUtiles = FuncionesUtiles(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sincronizacion)
        context = this
        imeiBD = ""
        if (FuncionesUtiles.usuario["CONF"].equals("N")){
            btFinalizar.visibility = View.VISIBLE
            return
        }
//        if (MainActivity.funcion.getFechaActual().trim() == "12/05/2021"){
        if (tipoSinc == "T") {
            try {
                funcion.ejecutarB("DROP TABLE IF EXISTS svm_cliente_supervisor ")
            } catch (e : java.lang.Exception){
                val error = e.message
            }
            try {
                funcion.ejecutarB("DROP TABLE IF EXISTS svm_cliente_supervisores ")
            } catch (e : java.lang.Exception){
                val error = e.message
            }
        }

        try {
            Thread.sleep(1500)
            PreparaSincornizacion().execute()
        } catch(e: Exception){
            Log.println(Log.WARN, "Error",e.message.toString())
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class PreparaSincornizacion: AsyncTask<Void, Void, Void>(){
        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Sincronizando...")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        @SuppressLint("WrongThread", "SetTextI18n")
        override fun doInBackground(vararg p0: Void?): Void? {
            MainActivity.conexionWS.setMethodName("ProcesaImeiSupervisorAct")
            imeiBD = MainActivity.conexionWS.procesaVersion()
            if (imeiBD.indexOf("Unable to resolve host") > -1) {
                progressDialog.dismiss()
                runOnUiThread {
                    Toast.makeText(
                        context,
                        "Verifique su conexion a internet y vuelva a intentarlo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
                return null
            }
            FuncionesUtiles.usuario["COD_PERSONA"] = imeiBD.split("-")[14]
            FuncionesUtiles.usuario["TIPO"] = imeiBD.split("-")[3]
            insertarUsuario()
            if (imeiBD.split("-")[0].trim() != FuncionesUtiles.usuario["VERSION"].toString()){
                runOnUiThread {
                    tvImei.text =
                        "\n\nEste dispositivo no puede sincronizar.\nLa versión de usuario no corresponde.${
                            imeiBD.split("-")[0]
                        }"
//                    tvImei.text = "\n\nEste dispositivo no puede sincronizar.\nLa versión de usuario no corresponde."
                    pbTabla.progress = 100
                    pbProgresoTotal.progress = 100
                    btFinalizar.visibility = View.VISIBLE
                }
                return null
            }
            if (imeiBD.split("-")[11].trim() != MainActivity.version.trim()){
                runOnUiThread {
                    tvImei.text =
                        "\n\nSistema desactualizado.\nDebe actualizar el sistema para continuar."
                    pbTabla.progress = 100
                    pbProgresoTotal.progress = 100
                    btFinalizar.visibility = View.VISIBLE
                }
                return null
            }
            if (tipoSinc == "T"){
                EnviarMarcacion.context = this@Sincronizacion
                val enviarPendientes = EnviarMarcacion()
                enviarPendientes.enviarMarcaciones()
            }
            if (Build.VERSION.SDK_INT >= 26){
                runOnUiThread {
                    progressDialog.setMessage("Generando Archivos")
                }
            }
            if (tipoSinc == "T"){
                MainActivity.funcion.ejecutarB("update svm_vendedor_pedido_venta set ULTIMA_VEZ = '" + MainActivity.funcion.getFechaActual() + "'")
                if(MainActivity.conexionWS.generaArchivosSupervisor()){
                    imeiBD = "$imeiBD\n\nError al generar archivos"
                }
                if (Build.VERSION.SDK_INT >= 26){
                    runOnUiThread {
                        progressDialog.setMessage("Obteniendo Archivos")
                    }
                }
                if(!MainActivity.conexionWS.obtieneArchivosSupervisorNuevo()){
                    imeiBD = "$imeiBD\n\nError al obtener archivos"
                }
            }
            if (tipoSinc == "R"){
                if(MainActivity.conexionWS.generaInformeSegSupervisor()){
                    imeiBD = "$imeiBD\n\nError al generar reportes"
                }
                if (Build.VERSION.SDK_INT >= 26){
                    runOnUiThread {
                        progressDialog.setMessage("Obteniendo Archivos")
                    }
                }
                if(!MainActivity.conexionWS.obtieneInformesSegSupervisor()){
                    imeiBD = "$imeiBD\n\nError al obtener reportes"
                }
            }
            if (tipoSinc == "V"){
                if(MainActivity.conexionWS.generaInformeVisitaSupervisor()){
                    imeiBD = "$imeiBD\n\nError al generar datos de visitas"
                }
                if (Build.VERSION.SDK_INT >= 26){
                    runOnUiThread {
                        progressDialog.setMessage("Obteniendo Archivos")
                    }
                }
                if(!MainActivity.conexionWS.obtieneInformesVisitaSupervisor()){
                    imeiBD = "$imeiBD\n\nError al obtener datos de visitas"
                }
            }
            if (tipoSinc == "I"){
                if(MainActivity.conexionWS.generaInformeSupervisor()){
                    imeiBD = "$imeiBD\n\nError al generar informes"
                }
                if (Build.VERSION.SDK_INT >= 26){
                    runOnUiThread {
                        progressDialog.setMessage("Obteniendo Archivos")
                    }
                }
                if(!MainActivity.conexionWS.obtieneInformesSupervisor()){
                    imeiBD = "$imeiBD\n\nError al obtener informes"
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            if (btFinalizar.visibility == View.VISIBLE){
                progressDialog.dismiss()
                return
            }
            super.onPostExecute(result)
            progressDialog.dismiss()
            runOnUiThread {
                cargarRegistros()
            }
        }
    }

    fun cargarRegistros(){
        if (tipoSinc == "T"){
            sincronizarTodo()
        }
        if (tipoSinc == "R"){
            sincronizarReportes()
        }
        if (tipoSinc == "V"){
            sincronizarVisitas()
        }
        if (tipoSinc == "I"){
            sincronizarInformes()
        }
    }

    fun borrarTablas(listaTablas: Vector<String>){
        for (i in 0 until listaTablas.size){
            val sql: String = "DROP TABLE IF EXISTS " + listaTablas[i]
            try {
                MainActivity.bd!!.execSQL(sql)
            } catch (e : Exception) {
                e.message
//                return
            }
        }
    }

    private fun borrarTablasTodo(listaTablas: Vector<String>){
        for (i in 0 until listaTablas.size){
            val sql: String = "DROP TABLE IF EXISTS " + listaTablas[i].split(" ")[5]
            try {
                MainActivity.bd!!.execSQL(sql)
            } catch (e : Exception) {
                e.message
//                return
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sincronizarTodo(){
        val th = Thread {
            runOnUiThread {
                tvImei.text = tvImei.text.toString() + "\n\nSincronizando"
            }
            borrarTablasTodo(MainActivity.tablasSincronizacion.listaSQLCreateTables())
            obtenerArchivosTodo(
                MainActivity.tablasSincronizacion.listaSQLCreateTables(),
                MainActivity.tablasSincronizacion.listaCamposSincronizacion()
            )
        }
        th.start()
    }

    @SuppressLint("SetTextI18n")
    fun sincronizarReportes(){
        tvImei.text = tvImei.text.toString() + "\n\nSincronizando reportes"
        borrarTablas(MainActivity.tablasReportes.listaReportes())
        obtenerArchivos(
            MainActivity.tablasReportes.listaReportes(),
                        MainActivity.tablasReportes.listaSQLCreateTableReportes(),
                        MainActivity.tablasReportes.listaCamposReportes())
    }

    @SuppressLint("SetTextI18n")
    fun sincronizarVisitas(){
        tvImei.text = tvImei.text.toString() + "\n\nSincronizando visitas"
        borrarTablas(MainActivity.tablasVisitas.listaVisitas())
        obtenerArchivos(MainActivity.tablasVisitas.listaVisitas(),
                        MainActivity.tablasVisitas.listaSQLCreateTableVisitas(),
                        MainActivity.tablasVisitas.listaCamposVisitas())
    }

    @SuppressLint("SetTextI18n")
    fun sincronizarInformes(){
        val th = Thread {
            runOnUiThread {
                tvImei.text = tvImei.text.toString() + "\n\nSincronizando informes"
            }
            borrarTablas(MainActivity.tablasInformes.listaInformes())
            if (obtenerArchivosTodo(
                    MainActivity.tablasInformes.listaSQLCreateTableInformes(),
                    MainActivity.tablasInformes.listaCamposInformes()
                )
            ) {
                runOnUiThread {
                    tvImei.text = tvImei.text.toString() + "\n\nFinalizado con exito"
                }
            } else {
                runOnUiThread {
                    tvImei.text = tvImei.text.toString() + "\n\nError al sincronizar"
                }
            }
        }
        th.start()
    }

    @SuppressLint("SdCardPath", "SetTextI18n")
    fun obtenerArchivos(listaTablas: Vector<String>, listaSQLCreateTable: Vector<String>, listaCampos: Vector<Vector<String>>):Boolean{
        pbTabla.progress = 0
        pbProgresoTotal.progress = 0
        for (i in 0 until listaTablas.size){
            MainActivity.bd!!.beginTransaction()
            try {

                //Leer el archivo desde la direccion asignada
                var archivo         = File("/data/data/apolo.supervisor.com/" + listaTablas[i] + ".txt")
                var leeArchivo      = FileReader(archivo)
                var buffer          = BufferedReader(leeArchivo)
                val sql : String    = listaSQLCreateTable[i]

                try {
                    MainActivity.bd!!.execSQL(sql)
                } catch (e: Exception) {
                    e.message
                    return false
                }

                //Obtiene cantidad de lineas
                var numeroLineas = 0
                var linea: String? = buffer.readLine()
                while (linea != null){
                    numeroLineas++
                    linea = buffer.readLine()
                }

                archivo     = File("/data/data/apolo.supervisor.com/" + listaTablas[i] + ".txt")
                leeArchivo  = FileReader(archivo)
                buffer      = BufferedReader(leeArchivo)

                //Extrae valor de los campo e inserta a la BD
                linea = buffer.readLine()
                while (linea != null){
                    val valores : ArrayList<String> = linea.split("|") as ArrayList<String>
                    val values = ContentValues()
                    for (j in 0 until valores.size){
                        values.put(listaCampos[i][j], valores[j])
                    }
                    try {
                        MainActivity.bd!!.insert(listaTablas[i],null,values)
                    } catch (e: Exception) {
                        e.message
                        return false
                    }
                    linea = buffer.readLine()
                    pbTabla.progress = (100/numeroLineas)*(i+1)
                    if (linea == null){
                        pbTabla.progress = 100
                    }
                }

            } catch (e: Exception) {
                e.message
                tvImei.text = tvImei.text.toString() + "\n\n" + e.message
                return false
            }
            pbProgresoTotal.progress = (100/listaTablas.size)*(i+1)
            MainActivity.bd!!.setTransactionSuccessful()
            MainActivity.bd!!.endTransaction()
            tvImei.text = tvImei.text.toString() + "\n" + listaTablas[i]
        }
        pbProgresoTotal.progress = 100
        btFinalizar.visibility = View.VISIBLE
        return true
    }

    @SuppressLint("SdCardPath", "SetTextI18n")
    private fun obtenerArchivosTodo(listaSQLCreateTable: Vector<String>, listaCampos: Vector<Vector<String>>):Boolean{
        runOnUiThread {
            pbTabla.progress = 0
            pbProgresoTotal.progress = 0
        }
        for (i in 0 until listaSQLCreateTable.size){
            MainActivity.bd!!.beginTransaction()
            try {

                //Leer el archivo desde la direccion asignada
                var archivo         = File("/data/data/apolo.supervisor.com/" + listaSQLCreateTable[i].split(" ")[5] + ".txt")
                var leeArchivo      = FileReader(archivo)
                var buffer          = BufferedReader(leeArchivo)
                val sql : String    = listaSQLCreateTable[i]

                try {
                    MainActivity.bd!!.execSQL(sql)
                } catch (e: Exception) {
                    val error = e.message
                    runOnUiThread {
                        tvImei.text =
                            "Error en la tabla ${listaSQLCreateTable[i].split(" ")[5]}. \n$error.\n\n" + tvImei.text
                    }
                    return false
                }

                //Obtiene cantidad de lineas
                var numeroLineas = 0
                var linea: String? = buffer.readLine()
                while (linea != null){
                    numeroLineas++
                    linea = buffer.readLine()
                }

                archivo     = File("/data/data/apolo.supervisor.com/" + listaSQLCreateTable[i].split(" ")[5] + ".txt")
                leeArchivo  = FileReader(archivo)
                buffer      = BufferedReader(leeArchivo)

                //Extrae valor de los campo e inserta a la BD
                linea = buffer.readLine()
                var cont = 0
                runOnUiThread {
                    tvImei.text =
                        nf.format(i) + "-" + listaSQLCreateTable[i].split(" ")[5] + "\n" + tvImei.text.toString()
                }
                while (linea != null){
                    val valores = linea.split("|").toTypedArray()
                    val values = ContentValues()
                    for (j in valores.indices){
                        if (valores[j] == "null" || valores[j] == "" || valores[j].isEmpty()){
                            values.put(listaCampos[i][j], " ")
                        } else {
                            values.put(listaCampos[i][j], valores[j])
                        }
                    }

                    //inserta valores en tablas especificas
                    if (listaSQLCreateTable[i].split(" ")[5] == "svm_vendedor_pedido") {
                        values.put("FECHA",funcion.getFechaActual())
                        values.put("ULTIMA_SINCRO",funcion.getFechaActual())
                        values.put("HORA",funcion.getHoraActual())
                    }

                    try {
                        MainActivity.bd!!.insert(listaSQLCreateTable[i].split(" ")[5],null,values)
                    } catch (e: Exception) {
                        e.message
                        return false
                    }
                    linea = buffer.readLine()
                    runOnUiThread {
                        cont++
                        var progreso: Double = (100 / numeroLineas.toDouble()) * (cont)
                        if (cont == numeroLineas) {
                            progreso = 100.0
                        }
                        pbTabla.progress = progreso.toInt()
                    }
                }

            } catch (e: Exception) {
                e.message
                runOnUiThread {
                    tvImei.text = tvImei.text.toString() + "\n\n" + e.message
                }
                return false
            }
            runOnUiThread {
                pbProgresoTotal.progress = (100 / listaSQLCreateTable.size) * (i + 1)
            }
            MainActivity.bd!!.setTransactionSuccessful()
            MainActivity.bd!!.endTransaction()
        }
        runOnUiThread {
            pbProgresoTotal.progress = 100
            btFinalizar.visibility = View.VISIBLE
        }
        if (tipoSinc == "T"){
            cargarSvmVendedorPedidoVenta()
        }
        return true
    }

    override fun onBackPressed() {
        return
    }

    fun cerrar(view: View) {
        view.id
        finish()
    }

    fun insertarUsuario(){
        try {
            funcion.ejecutarB("DELETE FROM usuarios ")
            funcion.ejecutarB(SentenciasSQL.insertUsuario(FuncionesUtiles.usuario))
//            MainActivity.bd!!.execSQL(SentenciasSQL.insertUsuario(FuncionesUtiles.usuario))
        } catch (e : Exception) {
            return
        }
    }

    private fun cargarSvmVendedorPedidoVenta(){
        funcion.ejecutar("DROP TABLE IF EXISTS svm_vendedor_pedido_venta",this)
        funcion.ejecutar(SentenciasSQL.createTableSvmVendedorPedidoVenta(),this)
        val values = ContentValues()
        values.put("COD_EMPRESA", FuncionesUtiles.usuario["COD_EMPRESA"])
        values.put("COD_SUPERVISOR", FuncionesUtiles.usuario["PASS"])
        values.put("IND_PALM", "S")
        values.put("TIPO", "")
        values.put("SERIE", "")
        values.put("NUMERO", "0")
        values.put("FECHA", "")
        values.put("ULTIMA_SINCRO", imeiBD.split("-")[6])
        values.put("FEC_CARGA_RUTEO",imeiBD.split("-")[8])
        values.put("ULTIMA_VEZ",funcion.getFechaActual())
        values.put("RANGO", imeiBD.split("-")[2])
        values.put("TIPO_SUPERVISOR", imeiBD.split("-")[3])
        values.put("MIN_FOTO", imeiBD.split("-")[4])
        values.put("MAX_FOTO", imeiBD.split("-")[5])
        values.put("MAX_DESC", imeiBD.split("-")[9])
        values.put("IND_FOTO", imeiBD.split("-")[7])
        values.put("MAX_DESC_VAR", imeiBD.split("-")[10])
        values.put("VERSION", imeiBD.split("-")[11])
        values.put("PER_VENDER", imeiBD.split("-")[12])
        values.put("INT_MARCACION", imeiBD.split("-")[13])

        MainActivity.bd!!.insert("svm_vendedor_pedido_venta",null,values)
    }

}

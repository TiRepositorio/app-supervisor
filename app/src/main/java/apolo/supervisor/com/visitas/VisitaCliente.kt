package apolo.supervisor.com.visitas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_visita_cliente.*
import kotlinx.android.synthetic.main.barra_vendedores.*
import java.util.*

class VisitaCliente : AppCompatActivity() {

    companion object{
        var nuevo : Boolean = true
        var consulta : Boolean = false
        var id : String = ""
    }

    private lateinit var dialogoBusqueda : DialogoBusqueda
    var funcion: FuncionesUtiles = FuncionesUtiles()
    var tabla: String = ""
    var bd = FuncionesBD(this)
    var latitud  : String = ""
    var longitud : String = ""
    private var ubicacion : FuncionesUbicacion = FuncionesUbicacion(this)
    private var dispositivo : FuncionesDispositivo = FuncionesDispositivo(this)
    private var distancia : Int = 150
    private lateinit var lm:LocationManager
    private lateinit var telMgr : TelephonyManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visita_cliente)

        funcion = FuncionesUtiles(imgTitulo,tvTitulo)
        inicializarElementos()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun inicializarElementos(){
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        funcion.cargarTitulo(R.drawable.ic_visita, "Reporte de visita con el gestor")
        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        defineTabla()
        etCabCliente.setOnClickListener{buscarCliente()}
        tvDia.text = funcion.getDiaDeLaSemana()
        cbEntrada
        cbEntrada.setOnClickListener { marcarEntrada() }
        cbSalida.setOnClickListener { marcarSalida() }
        inicializaETCliente(etCabCliente)
        inicializaETVendedor(etCabVendedor)
        inicializaETAccion(accion)
        btEnviar.setOnClickListener{enviar()}
        ubicacion.obtenerUbicacion(lm)
        btLimpiar.setOnClickListener{borrarRegistro()}
        if (nuevo){buscarVendedor()}
        accion.setText("")
        cargarDatos()
        ubicacion.obtenerUbicacion(lm)
//        borrar()
        marcarSalidaEmergencia()
    }

    private fun marcarSalidaEmergencia(){
        val sql = "select * from svm_analisis_cab where COD_CLIENTE = '766' AND COD_SUBCLIENTE = '1' AND FECHA_VISITA = '24/05/2021' ORDER BY id DESC"
        val cursor = funcion.consultar(sql)
        try {
            cursor.moveToFirst()
        } catch (e:java.lang.Exception){
            return
        }
        if (funcion.dato(cursor,"HORA_SALIDA").trim() == "" || funcion.dato(cursor,"HORA_SALIDA").isEmpty()){
            funcion.ejecutar("UPDATE svm_analisis_cab SET HORA_SALIDA = HORA_LLEGADA " +
                                 " WHERE id = '${funcion.dato(cursor,"id")}'",this)
        }

//        val sql : String =  "UPDATE svm_analisis_cab SET HORA_SALIDA = '" + salida + "'" + " WHERE id = '" +  maxIDCabecera() + "'"
//        funcion.ejecutar(sql,this)
    }

    private fun defineTabla(){
        tabla = if (FuncionesUtiles.usuario["TIPO"].equals("T") || FuncionesUtiles.usuario["TIPO"].equals("E")){
            "svm_cliente_supervisor"
        } else {
            "svm_cliente_supervisores"
        }
    }

    private fun buscarVendedor(){
        dialogoBusqueda = DialogoBusqueda(this,
            tabla,
            "COD_VENDEDOR",
            "DESC_VENDEDOR",
            "DESC_VENDEDOR",
            "",
            etCabVendedor,
            tvCabZona)
        dialogoBusqueda.cargarDialogo()
    }

    fun buscarCliente(){
        if (etCabVendedor.text.toString() == ""){
            Toast.makeText(this,"Seleccione una cartera",Toast.LENGTH_LONG).show()
            return
        }
        dialogoBusqueda = DialogoBusqueda(this,
            tabla,
            "COD_CLIENTE || '-' || COD_SUBCLIENTE AS COD_CLIENTE",
            "DESC_SUBCLIENTE",
            "DESC_SUBCLIENTE",
            " AND COD_VENDEDOR = '" + etCabVendedor.text.toString().split("-")[0] + "' ",
            etCabCliente,null)
        dialogoBusqueda.cargarDialogo()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun marcarEntrada(){
        cbEntrada.isChecked = !cbEntrada.isChecked

        //Desmarcar entrada
        if (cbEntrada.isChecked) {
            if (cbSalida.isChecked){
                tvSalida.text = ""
                cbSalida.isChecked = false
            }
            tvEntrada.text = ""
            cbEntrada.isChecked = false
            borrarRegistro()
            return
        }

        if (registroPendiente()){
            return
        }

        if (marcacionExistente()){
            return
        }

        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }

        if (!dispositivo.horaAutomatica()){
            return
        }

        if (!dispositivo.tarjetaSim(telMgr)){ //Valida si tiene chip
            return
        }

        //Valida cliente seleccionado
        if (etCabCliente.text.toString() == "" || etCabVendedor.text.toString() == ""){
            Toast.makeText(this,"Seleccione un clietne",Toast.LENGTH_LONG).show()
            return
        }

        //verificaFechaDeSincronizacion
        if (!dispositivo.fechaCorrecta()){
            return
        }

        //Verifica el modo avion
        if (!dispositivo.modoAvion()){
            return
        }

        //Zona horaria
        if (!dispositivo.zonaHoraria()){
            return
        }

        if (FuncionesUtiles.usuario["TIPO"].equals("M") || FuncionesUtiles.usuario["TIPO"].equals("E") ){
            if (!validaIntervalo()){
                return
            }
        }

        //Valida ubicacion habilitada
        if (!ubicacion.ubicacionActivada(lm)){
            val confUbicacion = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(confUbicacion,0)
            return
        }

        if (!ubicacion.validaUbicacionSimulada(lm)){
            return
        }

        //Verifica que se haya cargado la ubicacion
        ubicacion.obtenerUbicacion(lm)
        if (!ubicacion.verificarUbicacion()){
            return
        }

        if (!obtieneUbicacionCliente()){
//            ubicacionCliente("MAPA")
            ubicacionCliente()
            return
        }

        //verifica la distancia minima
        if (latitud.isEmpty()){
            latitud = ""
            longitud= ""
        }
        if (!ubicacion.distanciaMinima(latitud,longitud,distancia)){
            return
        }
        
//        funcion.mensaje(this,"Cargado",ubicacion.latitud + "\n" + ubicacion.longitud)
        //Marcar entrada
        if (!cbEntrada.isChecked){
            tvEntrada.text = funcion.getHoraActual()

            var estadoSim = false

            try {
                var fechaOnline = funcion.obtenerHoraActualDeInternet()
                estadoSim = true
            } catch (e: Exception) {
                estadoSim = dispositivo.validaEstadoSim2(telMgr);
            }


            var observacion = ""

            if (!estadoSim) {
                observacion = "$observacion . El chip no se encuentra habilitado o no posee señal"
            }

            if (observacion != "") {
                observacion = "Entrada: $observacion "
            }



            registrarEntrada(observacion)
            cbEntrada.isChecked = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun marcarSalida(){
        cbSalida.isChecked = !cbSalida.isChecked

        if (cbSalida.isChecked){
            tvSalida.text = ""
            borrarSalida()
            cbSalida.isChecked = false
            return
        }

        if (!cbEntrada.isChecked){
            tvSalida.text = ""
            cbSalida.isChecked = false
            Toast.makeText(this,"Debe marcar entrada",Toast.LENGTH_LONG).show()
            return
        }

        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }

        if (!ubicacion.validaUbicacionSimulada(lm)){
            return
        }

        if (!ubicacion.ubicacionActivada(lm)){
            return
        }

        if (!obtieneUbicacionCliente()){
            obtieneUbicacionDeEntrada()
        }

        ubicacion.obtenerUbicacion(lm)
        ubicacion.obtenerUbicacionLatLng(lm)
        if (ubicacion.latitud == "" || ubicacion.latitud == "0.0" || ubicacion.longitud == "" || ubicacion.longitud == "0.0"){
            Toast.makeText(this, "No se encuentra ubicacion GPS",Toast.LENGTH_LONG).show()
            return
        }

        //verifica la distancia minima
        if (!ubicacion.distanciaMinima(latitud,longitud,distancia)) {
//            autorizacion("SALIDA")
            autorizacion()
            return
        }

        if (tiempoTranscurrido() < tiempoMinimo()){
            Toast.makeText(this,"Debe permanecer un minimo de " + tiempoMinimo() + " minutos en el cliente.",Toast.LENGTH_LONG).show()
            return
        }

        if (cbEntrada.isChecked){
            if (!cbSalida.isChecked){
                tvSalida.text = funcion.getHoraActual()

                var estadoSim = false

                try {
                    var fechaOnline = funcion.obtenerHoraActualDeInternet()
                    estadoSim = true
                } catch (e: Exception) {
                    estadoSim = dispositivo.validaEstadoSim(telMgr);
                }

                var paquetesUbicacionSimulada = ""
                try{
                    paquetesUbicacionSimulada = dispositivo.getAppsForMockLocation(this)
                } catch (e: Exception) {
                }


                var observacion = ""

                if (!estadoSim) {
                    observacion = "$observacion . El chip no se encuentra habilitado o no posee señal"
                }

                if (paquetesUbicacionSimulada != "") {
                    observacion = "$observacion . $paquetesUbicacionSimulada"
                }

                if (observacion != "") {
                    observacion = " Salida: $observacion"
                }


                registrarSalida(tvSalida.text.toString(), observacion)
                cbSalida.isChecked = true
                habilitar(false)
            } else {
                tvSalida.text = ""
                borrarSalida()
                cbSalida.isChecked = false
            }
        } else {
            tvSalida.text = ""
            cbSalida.isChecked = false
            Toast.makeText(this,"Debe marcar entrada",Toast.LENGTH_LONG).show()
            return
        }
    }
    fun cargaDescZona(){
        val sql : String = ("SELECT DISTINCT DESC_VENDEDOR FROM " + tabla + " WHERE COD_VENDEDOR = '" + etCabVendedor.text.toString() + "'")
        val cursor = funcion.consultar(sql)
        tvCabZona.text = funcion.dato(cursor,"DESC_VENDEDOR")
    }

    @SuppressLint("SetTextI18n")
    fun cargarTiempo(){
        val sql : String = ("SELECT LATITUD, LONGITUD,TIEMPO_MIN, TIEMPO_MAX FROM " + tabla + where())
        val cursor = funcion.consultar(sql)
        tvTiempo.text = funcion.dato(cursor,"TIEMPO_MIN") + " A " + funcion.dato(cursor,"TIEMPO_MAX") + " MIN"
    }

    private fun inicializaETCliente(etCabCliente:EditText){
        etCabCliente.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etCabCliente.text.toString().isNotEmpty()){
                    cargarTiempo()
                } else {
                    tvTiempo.text = ""
                    tvEntrada.text = ""
                    tvSalida.text = ""
                    cbEntrada.isChecked = false
                    cbSalida.isChecked = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

        })
    }

    private fun inicializaETVendedor(etCabVendedor:EditText){
        etCabVendedor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etCabVendedor.text.toString().isNotEmpty()){
                    cargaDescZona()
                    if (nuevo){buscarCliente()}
                } else {
                    tvCabZona.text = ""
                    etCabCliente.setText("")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

        })
        etCabVendedor.setOnClickListener{buscarVendedor()}
    }

    private fun inicializaETAccion(etAccion:EditText){
        etAccion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etAccion.text.toString() == "SALIDA"){
                    tvSalida.text = tvEntrada.text
                    registrarSalida(tvEntrada.text.toString(), "")
                    cbSalida.isChecked = true
                    accion.setText("")
                    habilitar(false)
                    return
                }
                if (etAccion.text.toString().trim() == "MAPA"){
                    Mapa.modificarCliente = true
                    Mapa.codVendedor    = etCabVendedor.text.toString()
                    Mapa.codCliente     = etCabCliente.text.toString().split("-")[0].trim()
                    Mapa.codSubcliente  = etCabCliente.text.toString().split("-")[1].trim()
                    val mapa = Intent(this@VisitaCliente,Mapa::class.java)
                    startActivity(mapa)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                return
            }

        })
    }

    private fun sqlEntrada():String{
        return "SELECT * FROM " + tabla + " " + where()
    }

    private fun sqlRegistro():String{
        return "select * from svm_analisis_cab where id = '$id' "
    }

    private fun maxIDCabecera():String{
        if (where() == ""){
            return ""
        }
        val sql = " SELECT IFNULL(MAX(ID),'-1') as ID FROM svm_analisis_cab " + where()
        if (funcion.consultar(sql).count>0){
            return funcion.dato(funcion.consultar(sql),"ID")
        }
        return ""
    }

    private fun sqlEntradaDetalle():String{
        return (" SELECT COD_MOTIVO, DESCRIPCION, NRO_ORDEN, ESTADO, PUNTUACION "
                       +  " FROM svm_motivo_analisis_cliente  "
                       +  " WHERE COD_VENDEDOR = '" + etCabVendedor.text.toString() + "'")
    }

    fun where():String{
        if (etCabCliente.text.toString() == ""){
            return ""
        }
        return " WHERE COD_VENDEDOR   = '" + etCabVendedor.text + "' " +
                           "   AND COD_CLIENTE    = '" + etCabCliente.text.toString().split("-")[0]         + "' " +
                           "   AND COD_SUBCLIENTE = '" + etCabCliente.text.toString().split("-")[1].trim()  + "' "
    }

    private fun registrarEntrada(comentario: String){

        MainActivity.bd!!.beginTransaction()

        bd.guardarVisitaCabecera(funcion.consultar(sqlEntrada()),tvEntrada.text.toString(),tvSalida.text.toString().trim(), comentario)
        if (!verificaCliente()){
            bd.guardaVisitaDetalle(maxIDCabecera(),funcion.consultar(sqlEntradaDetalle()))
        }

        MainActivity.bd!!.setTransactionSuccessful()
        MainActivity.bd!!.endTransaction()
    }

    fun registrarSalida(salida:String, comentario: String){
        val sql : String =  "UPDATE svm_analisis_cab SET HORA_SALIDA = '" + salida + "', COMENTARIO = (COMENTARIO || '" + comentario + "') WHERE id = '" +  maxIDCabecera() + "' "
        funcion.ejecutar(sql,this)
    }

    private fun verificaCliente():Boolean{
        val sql : String = ("Select HORA_LLEGADA, HORA_SALIDA, FECHA_VISITA, id "
                + " FROM svm_analisis_cab " + where()
                + "   and FECHA_VISITA   = '" + funcion.getFechaActual() + "' "
                + "   and ESTADO != 'A'"
                + "   and id             not in ('" + maxIDCabecera() + "')")
        return if (funcion.consultar(sql).count>0){
            funcion.dato(funcion.consultar(sql),"id")
            true
        } else {
            false
        }
    }

    private fun obtieneUbicacionCliente():Boolean{
        latitud  = ubicacion.obtenerUbicacion("LATITUD","LONGITUD",tabla,where()).split("|")[0]
        longitud = ubicacion.obtenerUbicacion("LATITUD","LONGITUD",tabla,where()).split("|")[1]
        if (latitud == "0.0" || longitud == "0.0" || latitud.trim() == "" || longitud.trim() == "" || latitud.trim() == "null" || longitud.trim() == "") {
            return false
        }
        return true
    }

    private fun obtieneUbicacionDeEntrada(){
        val sql : String = ("Select LATITUD, LONGITUD "
                + "  from svm_modifica_catastro "
                + "  where COD_CLIENTE = '" + etCabCliente.text.toString().split("-")[0] + "'"
                + "    and COD_SUBCLIENTE = '" + etCabCliente.text.toString().split("-")[1].trim()  + "'"
                + "    and LATITUD is not null "
                + "  order by id desc ")
        val cursor:Cursor = funcion.consultar(sql)
        latitud = funcion.dato(cursor,"LATITUD")
        longitud = funcion.dato(cursor,"LONGITUD")
    }

    private fun borrarRegistro(){
        if (maxIDCabecera() == ""){
            etCabVendedor.setText("")
            return
        }
        var sql:String = ("Delete from svm_analisis_cab " + "	where ID = '"  + maxIDCabecera() + "'")
        MainActivity.bd!!.beginTransaction()
        funcion.ejecutar(sql,this)
        sql = ("Delete from svm_analisis_det" + " where ID_CAB = '" + maxIDCabecera() + "'")
        funcion.ejecutar(sql,this)
        MainActivity.bd!!.setTransactionSuccessful()
        MainActivity.bd!!.endTransaction()
        etCabVendedor.setText("")
        habilitar(true)
        buscarVendedor()
    }

    private fun borrarSalida(){
        val sql : String = "UPDATE svm_analisis_cab SET HORA_SALIDA = '" + tvSalida.text + "'" + " WHERE id = '" + maxIDCabecera() + "'"
        funcion.ejecutar(sql,this)
    }

    private fun validaIntervalo(): Boolean {
        return try {
            val sql =
                ("select COD_VENDEDOR, COD_CLIENTE, COD_SUBCLIENTE, IFNULL(HORA_SALIDA,'0') HORA_SALIDA, FECHA_VISITA "
                        + "  from svm_analisis_cab " + where()
                        + "    and FECHA_VISITA = '" + funcion.getFechaActual() + "' ORDER BY id DESC")
            val cursor:Cursor = funcion.consultar(sql)
            if (cursor.count > 0) {
                val fechaUltVez: Date
                if (funcion.dato(cursor,"HORA_SALIDA") != "0"){
                    return true
                }
                val f: String = funcion.dato(cursor,"FECHA_VISITA") + " " + funcion.dato(cursor,"HORA_SALIDA")
                fechaUltVez = funcion.fechaHora(f)
                val fechaAct = Date()
                val dif: Long = fechaAct.time - fechaUltVez.time
                val tiempo: Long = 1000 * 60 * funcion.getIntervaloMarcacion().toLong()
                if (dif < tiempo) {
                    funcion.mensaje("Atencion","DEBE ESPERAR " + funcion.getIntervaloMarcacion().toString() + " MINUTOS PARA VOLVER A MARCAR EN EL CLIENTE")
                    false
                } else {
                    true
                }
            } else {
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun tiempoMinimo():Int{
        return tvTiempo.text.toString().split(" ")[0].toInt()
    }

    private fun tiempoTranscurrido():Int{
        return funcion.tiempoTranscurrido(fechaVisita() + " " + tvEntrada.text,
            funcion.getFechaActual() + " " + funcion.getHoraActual())
    }

    private fun fechaVisita():String{
        val sql : String = "SELECT FECHA_VISITA FROM svm_analisis_cab " + where() + " and id = '" + maxIDCabecera() + "' "
        return funcion.dato(funcion.consultar(sql),"FECHA_VISITA")
    }

//    private fun autorizacion(accion:String){
    private fun autorizacion(){
        val autorizacion = DialogoAutorizacion(this)
//        autorizacion.dialogoAutorizacion(accion,this.accion)
        autorizacion.dialogoAutorizacion("SALIDA",this.accion)
    }

    fun habilitar (estado:Boolean){
        etCabVendedor.isEnabled = estado
        etCabCliente.isEnabled = estado
        cbEntrada.isEnabled = estado
        cbSalida.isEnabled = estado
        btLimpiar.isEnabled = estado
    }

    private fun registroPendiente():Boolean{
        val sql : String = "SELECT COD_CLIENTE || '-' || COD_SUBCLIENTE || ' - ' || DESC_SUBCLIENTE AS CLIENTE " +
                           "  FROM svm_analisis_cab " +
                            "WHERE trim(HORA_LLEGADA) <> '' " +
                            "  AND trim(HORA_SALIDA) = '' " +
                            "  AND ESTADO = 'P' " +
                            "  AND trim(FECHA_VISITA) = '${funcion.getFechaActual().trim()}'"
        val cursor : Cursor = funcion.consultar(sql)
        if (cursor.count>0){
            funcion.mensaje(this,"Error","Debe marcar salida del cliente " + funcion.dato(cursor,"CLIENTE"))
            return true
        }
        return false
    }

    private fun marcacionExistente():Boolean{
        //try {

            val sql : String = "SELECT COD_VENDEDOR, COD_CLIENTE, COD_SUBCLIENTE " +
                    "  FROM svm_analisis_cab  " +
                    "  WHERE COD_CLIENTE    = '" + etCabCliente.text.toString().split("-")[0].trim() + "' " +
                    "    and COD_SUBCLIENTE = '" + etCabCliente.text.toString().split("-")[1].trim() +"'  " +
                    "    and FECHA_VISITA   = '" + funcion.getFechaActual() + "'"
            if (funcion.consultar(sql).count>1){
                funcion.mensaje(this,"Atencion","Se ha registrado mas de una entrada en este cliente el dia de hoy.\n" + etCabCliente.text)
                return true
            }
            
        //} catch (e: Exception) {

        //    Toast.makeText(this, etCabCliente.text, Toast.LENGTH_LONG).show();
            
        //}
        

        return false
    }

    @SuppressLint("SetTextI18n")
    private fun cargarDatos(){
        if (id != "" && !nuevo){
            habilitar(nuevo)
            val cursor:Cursor = funcion.consultar(sqlRegistro())
            etCabVendedor.setText(funcion.dato(cursor,"COD_VENDEDOR"))
            etCabCliente.setText(funcion.dato(cursor,"COD_CLIENTE")+"-"+funcion.dato(cursor,"COD_SUBCLIENTE") + " - " + funcion.dato(cursor,"DESC_SUBCLIENTE"))
            tvEntrada.text = funcion.dato(cursor,"HORA_LLEGADA")
            cbEntrada.isChecked = true
            val horaSalida: String = try {
                funcion.dato(cursor,"HORA_SALIDA")
            } catch (e:java.lang.Exception){
                ""
            }
            if (horaSalida == ""){
                cbSalida.isEnabled = true
            } else {
                tvSalida.text = horaSalida
                cbSalida.isChecked = true
            }
            if (funcion.dato(cursor,"ESTADO") != "P"){
                btEnviar.isEnabled = false
            }
        }
        if (consulta){
            habilitar(false)
            btEnviar.isEnabled = false
        }
    }

//    private fun ubicacionCliente(accion:String){
    private fun ubicacionCliente(){
        val dialogo = DialogoAutorizacion(this)
//        dialogo.dialogoAccion(accion,this.accion,"Se encuentra en el cliente","Atencion","SI")
        dialogo.dialogoAccion("MAPA",this.accion,"Se encuentra en el cliente","Atencion","SI")
    }

    fun borrar(){
        val sql = "delete from svm_analisis_cab"
        funcion.ejecutar(sql,this)
    }

    fun enviar (){
        if (id != "" && !nuevo){
            EnviarMarcacion.id = id
        } else {
            EnviarMarcacion.id = maxIDCabecera()
        }
        EnviarMarcacion.context = this
        val enviarMarcacion = EnviarMarcacion()
        enviarMarcacion.enviar()
    }

//    override fun onBackPressed() {
//        btLimpiar
//    }

}

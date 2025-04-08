package apolo.supervisor.com

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import apolo.supervisor.com.configurar.AcercaDe
import apolo.supervisor.com.configurar.ActualizarVersion
import apolo.supervisor.com.configurar.CalcularClavePrueba
import apolo.supervisor.com.configurar.ConfigurarUsuario
import apolo.supervisor.com.informes.*
import apolo.supervisor.com.prueba.VentanaAuxiliar
import apolo.supervisor.com.reportes.*
import apolo.supervisor.com.reportes.AvanceDeComisiones
import apolo.supervisor.com.reportes.ProduccionSemanal
import apolo.supervisor.com.reportes.VariablesMensuales
import apolo.supervisor.com.utilidades.*
import apolo.supervisor.com.ventas.Promotores
import apolo.supervisor.com.visitas.*
import apolo.supervisor.com.visitas.baja.Baja
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_reportes.*
import java.io.File
import java.io.IOException

@Suppress("PrivatePropertyName")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //APP DE SUPERVISORES

    companion object{
        var utilidadesBD:UtilidadesBD? = null
        var bd:SQLiteDatabase? = null
        @SuppressLint("StaticFieldLeak")
        val conexionWS: ConexionWS = ConexionWS()
        val tablasReportes: TablasReportes = TablasReportes()
        val tablasVisitas: TablasVisitas = TablasVisitas()
        val tablasInformes: TablasInformes = TablasInformes()
        val tablasSincronizacion: TablasSincronizacion= TablasSincronizacion()
        @SuppressLint("StaticFieldLeak")
        val funcion : FuncionesUtiles = FuncionesUtiles()
        const val version = "46"
        const val fechaVersion = "20240226"
        const val versionDelDia = "1"
        @SuppressLint("StaticFieldLeak")
        lateinit var dispositivo : FuncionesDispositivo
        @SuppressLint("StaticFieldLeak")
        lateinit var etAccion : EditText
        lateinit var lm: LocationManager
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private lateinit var telMgr : TelephonyManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        dispositivo = FuncionesDispositivo(this)
        dispositivo.permisoParaEscritura(this)
        utilidadesBD = UtilidadesBD(this, null)
        bd = utilidadesBD!!.writableDatabase
        inicializaElementosReporte()
        cargarUsuarioInicial()

    }

    override fun onBackPressed() {
        if (drawer_layout_aplicacion.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_aplicacion.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun inicializaElementosReporte(){
        telMgr = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        etAccion = accion

        dispositivo.verificaRoot()

        dispositivo.obtieneIDSIM(telMgr)

        if (!dispositivo.validaEstadoSim(telMgr)){
            return
        }
        if (!dispositivo.zonaHoraria()){
            return
        }
        if (!dispositivo.horaAutomatica()){
            return
        }
        crearTablas()
        modificarTablas()

        nav_view_menu.setNavigationItemSelectedListener(this)

        cardConfigurar.setOnClickListener{
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@setOnClickListener
            }
            if (!dispositivo.zonaHoraria()){
                return@setOnClickListener
            }
            if (!dispositivo.horaAutomatica()){
                return@setOnClickListener
            }
            cargarMenu(R.menu.drawer_menu_configurar, R.layout.menu_cab_configurar)
        }

        cardVenta.setOnClickListener(View.OnClickListener {
            if (!cargarUsuarioInicial()){
                funcion.mensaje(this,"Atención!","Debe configurar un usuario para continuar")
                return@OnClickListener
            }
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@OnClickListener
            }
            if (!dispositivo.zonaHoraria()){
                return@OnClickListener
            }
            if (!dispositivo.horaAutomatica()){
                return@OnClickListener
            }
            cargarMenu(R.menu.drawer_menu_vendedores_inf, R.layout.menu_cab_vendedores)
        })

        cardReportes.setOnClickListener(View.OnClickListener {
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@OnClickListener
            }
            if (!dispositivo.zonaHoraria()){
                return@OnClickListener
            }
            if (!dispositivo.horaAutomatica()){
                return@OnClickListener
            }
            if (!cargarUsuarioInicial()){
                funcion.mensaje(this,"Atención!","Debe configurar un usuario para continuar")
                return@OnClickListener
            }
            cargarMenu(R.menu.drawer_menu_reportes, R.layout.menu_cab_reportes)
        })

        cardVisitas.setOnClickListener(View.OnClickListener {
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@OnClickListener
            }
            if (!dispositivo.zonaHoraria()){
                return@OnClickListener
            }
            if (!dispositivo.horaAutomatica()){
                return@OnClickListener
            }
            if (!cargarUsuarioInicial()){
                funcion.mensaje(this,"Atención!","Debe configurar un usuario para continuar")
                return@OnClickListener
            }
            cargarMenu(R.menu.drawer_menu_visitas, R.layout.menu_cab_visitas)
        })

        cardInformes.setOnClickListener(View.OnClickListener {
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@OnClickListener
            }
            if (!dispositivo.zonaHoraria()){
                return@OnClickListener
            }
            if (!dispositivo.horaAutomatica()){
                return@OnClickListener
            }
            if (!cargarUsuarioInicial()){
                funcion.mensaje(this,"Atención!","Debe configurar un usuario para continuar")
                return@OnClickListener
            }
            cargarMenu(R.menu.drawer_menu_informes, R.layout.menu_cab_informes)
        })

        cardSincronizar.setOnClickListener(View.OnClickListener {
            if (!dispositivo.validaEstadoSim(telMgr)){
                return@OnClickListener
            }
            val dialogoAutorizacion = DialogoAutorizacion(this)
            dialogoAutorizacion.dialogoAccionOpcion("sincronizar","",accion,"¿Desea sincronizar ahora?","¡Atención!","SI","NO")
        })

        inicializaETAccion(accion)
    }

    fun sincronizar(){
        if (!cargarUsuarioInicial()){
            funcion.mensaje(this,"Atención!","Debe configurar un usuario para continuar")
            return
        }
        if (cargarUsuarioInicial()){
            Sincronizacion.tipoSinc = "T"
            val menu2 = Intent(this, Sincronizacion::class.java)
            startActivity(menu2)
        } else {
            funcion.mensaje(this,"Atencion!","Debe configurar un usuario")
        }
    }

    private fun mostrarMenu(){
        if (drawer_layout_aplicacion.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_aplicacion.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout_aplicacion.openDrawer(GravityCompat.START)
        }
    }

    private fun limpiarMenu(){
        if (nav_view_menu.headerCount>0){
            nav_view_menu.removeHeaderView(nav_view_menu.getHeaderView(0))
        }
        nav_view_menu.menu.clear()
    }

    private fun cargarMenu(menu:Int, header:Int){
        limpiarMenu()
        nav_view_menu.inflateMenu(menu)
        nav_view_menu.inflateHeaderView(header)
        mostrarMenu()
        cargarUsuarioInicial()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        //crear las vistas
        var menu2 = Intent(this, VentanaAuxiliar::class.java)
        //Reportes
        when (menuItem.itemId){
            //INFORMES
            R.id.sup_comision               -> menu2 = Intent(this, AvanceDeComisiones::class.java)
            R.id.sup_prod_semanal           -> menu2 = Intent(this, ProduccionSemanal::class.java)
            R.id.sup_salario                -> menu2 = Intent(this, ExtractoDeSalario::class.java)
            R.id.sup_comprobantes           -> menu2 = Intent(this, ComprobantesPendientes::class.java)
            R.id.sup_canasta_marcas         -> menu2 = Intent(this, CanastaDeMarcas::class.java)
//            R.id.sup_canasta_clientes       -> menu2 = Intent(this, CanastaDeClientes::class.java)
//            R.id.sup_promedio_cuotas        -> menu2 = Intent(this, PromedioDeAlcanceDeCuotas::class.java)
            R.id.vend_produccion_semanal    -> menu2 = Intent(this, apolo.supervisor.com.informes.ProduccionSemanal::class.java)
            R.id.sup_variables_mensuales    -> menu2 = Intent(this, VariablesMensuales::class.java)
            R.id.vend_deuda                 -> menu2 = Intent(this, DeudaDeClientes::class.java)
            R.id.vend_syncR                 -> menu2 = Intent(this, Sincronizacion::class.java)

            //VISITAS
            //R.id.sup_venta                  -> menu2 = Intent(this, Promotores::class.java)
            R.id.sup_visita                 -> menu2 = Intent(this, VisitaCliente::class.java)
            R.id.sup_visitado               -> menu2 = Intent(this, ClientesVisitados::class.java)
            R.id.sup_ruteo_mapa             -> menu2 = Intent(this, RuteoEnMapa::class.java)
            R.id.sup_prog_ruteo             -> menu2 = Intent(this, ProgramarRuteoT::class.java)
            R.id.sup_ruteo_lista            -> menu2 = Intent(this, ListaDeRuteosCargados::class.java)
            R.id.dar_de_baja                -> menu2 = Intent(this, Baja::class.java)
            R.id.vend_syncV                 -> menu2 = Intent(this, Sincronizacion::class.java)

            //INFORMES
            R.id.vend_evol_diaria_ventas    -> menu2 = Intent(this, EvolucionDiariaDeVentas::class.java)
            R.id.vend_pedidos_vend          -> menu2 = Intent(this, PedidosPorVendedor::class.java)
            R.id.vend_ventas                -> menu2 = Intent(this, VentasPorCliente::class.java)
            R.id.vend_movimiento            -> menu2 = Intent(this, MovimientoDeGestores::class.java)
            R.id.vend_rebotes               -> menu2 = Intent(this, RebotesPorVendedor::class.java)
            R.id.vend_pedido_reparto        -> menu2 = Intent(this, PedidosEnReparto::class.java)
            R.id.vend_lista_precio          -> menu2 = Intent(this, ListaDePrecios::class.java)
            R.id.vend_comision              -> menu2 = Intent(this, apolo.supervisor.com.informes.AvanceDeComisiones::class.java)
            R.id.vend_variables_mensuales   -> menu2 = Intent(this, apolo.supervisor.com.informes.VariablesMensuales::class.java)
            R.id.vend_cobertura_semanal     -> menu2 = Intent(this, CoberturaSemanal::class.java)
            R.id.vend_seguimiento_visitas   -> menu2 = Intent(this, SeguimientoDeVisitas::class.java)
            R.id.vend_syncI                 -> menu2 = Intent(this, Sincronizacion::class.java)

            //CONFIGURAR
            R.id.sup_clave                  -> menu2 = Intent(this, CalcularClavePrueba::class.java)
            R.id.sup_usuario                -> menu2 = Intent(this, CalcularClavePrueba::class.java)
            R.id.sup_actualizar             -> actualizarVersion()
            R.id.sup_acerca_de                  -> menu2 = Intent(this, AcercaDe::class.java)
            R.id.sup_salir                  -> finish()
        }

        when (menuItem.itemId){
            R.id.vend_syncR                 -> Sincronizacion.tipoSinc = "R"
            R.id.vend_syncV                 -> Sincronizacion.tipoSinc = "V"
            R.id.vend_syncI                 -> Sincronizacion.tipoSinc = "I"
            R.id.sup_clave                  -> CalcularClavePrueba.informe = null
            R.id.sup_usuario                -> CalcularClavePrueba.informe = ConfigurarUsuario::class.java
            R.id.sup_salir                  -> return true
        }

        if (menuItem.itemId == R.id.sup_actualizar){
            return true
        }

        VisitaCliente.id = ""
        VisitaCliente.nuevo = true
        VisitaCliente.consulta = false
        ProgramarRuteoT.fecha = ""
        if (!cargarUsuarioInicial()){
            startActivity(menu2)
            return true
        }

        if (   menuItem.itemId != R.id.sup_usuario
            && menuItem.itemId != R.id.sup_actualizar
            && menuItem.itemId != R.id.sup_clave
            && menuItem.itemId != R.id.sup_acerca_de ){
            if (!dispositivo.fechaCorrecta()){
                return false
            }
        }

//        if (!dispositivo.fechaCorrecta()){
//            return false
//        }

        DeudaDeClientes.venta = false
        if (menuItem.itemId != R.id.sup_actualizar){
            startActivity(menu2)
        }
        return true
    }

    @SuppressLint("SetTextI18n", "Recycle")
    fun cargarUsuarioInicial():Boolean{
        lateinit var cursor : Cursor

        try {
            bd!!.execSQL(SentenciasSQL.createTableUsuarios())
            cursor = bd!!.rawQuery("SELECT * FROM usuarios", null)
        } catch(e:Exception){
            e.message
            return false
        }

        if (cursor.moveToFirst()) {
            cursor.moveToLast()
            nav_view_menu.getHeaderView(0)
                .findViewById<TextView>(R.id.tvNombreSup)
                .text = cursor.getString(cursor.getColumnIndex("NOMBRE"))
            nav_view_menu.getHeaderView(0)
                .findViewById<TextView>(R.id.tvCodigoSup)
                .text = cursor.getString(cursor.getColumnIndex("PASS"))
            FuncionesUtiles.usuario["NOMBRE"] = cursor.getString(cursor.getColumnIndex("NOMBRE"))
            FuncionesUtiles.usuario["PASS"] = cursor.getString(cursor.getColumnIndex("PASS"))
            FuncionesUtiles.usuario["TIPO"] = cursor.getString(cursor.getColumnIndex("TIPO"))
//            FuncionesUtiles.usuario["ACTIVO"] = cursor.getString(cursor.getColumnIndex("ACTIVO"))
            FuncionesUtiles.usuario["COD_EMPRESA"] = cursor.getString(cursor.getColumnIndex("COD_EMPRESA"))
            FuncionesUtiles.usuario["VERSION"] = cursor.getString(cursor.getColumnIndex("VERSION"))
            FuncionesUtiles.usuario["COD_PERSONA"] = cursor.getString(cursor.getColumnIndex("COD_PERSONA"))
            FuncionesUtiles.usuario["CONF"] = "S"
            return true
        } else {
            FuncionesUtiles.usuario["CONF"] = "N"
            if (nav_view_menu.headerCount>0) {
                nav_view_menu.getHeaderView(0)
                    .findViewById<TextView>(R.id.tvNombreSup)
                    .text = "Ingrese el nombre del supervisor"
                nav_view_menu.getHeaderView(0)
                    .findViewById<TextView>(R.id.tvCodigoSup)
                    .text = "12345"
            }
            return false
        }
    }

    private fun crearTablas(){
        for (i in 0 until SentenciasSQL.listaSQLCreateTable().size){
            funcion.ejecutar(SentenciasSQL.listaSQLCreateTable()[i],this)
        }
    }

    private fun modificarTablas(){
        for (i in 0 until SentenciasSQL.listaSQLAlterTable().size){
            funcion.ejecutarB(SentenciasSQL.listaSQLAlterTable()[i])
        }
    }




    //ACTUALIZAR VERSION
    private fun actualizarVersion(){
        etAccion = accion
        val dialogo = DialogoAutorizacion(this)
        dialogo.dialogoAccionOpcion("DESCARGAR","",accion,"¿Desea actualizar la versión?","Atención!","SI","NO")
    }

    fun descargarActualizacion(){
        ActualizarVersion.context = this
        ConexionWS.context = this
        ActualizarVersion.etAccion = accion
        crearArchivo()
        val descargar = ActualizarVersion()
//        descargar.preparaActualizacion()
        descargar(descargar)
    }

    private fun descargar(descargar:ActualizarVersion){
        val progressDialog = ProgressDialog(this)
        val thread = Thread {
            runOnUiThread { progressDialog.cargarDialogo("Descargando actualización...",false) }
            descargar.descargarInstalador()
            if (ActualizarVersion.estado){
                if (ActualizarVersion.estado) {
                    val dialogoAccion = DialogoAutorizacion(this)
                    runOnUiThread {
                        progressDialog.cerrarDialogo()
                        dialogoAccion.dialogoAccion("ACTUALIZAR", etAccion, ActualizarVersion.resultado,"Atención!","OK")
                    }
                } else {
                    runOnUiThread {
                        progressDialog.cerrarDialogo()
                        funcion.mensaje(this,"Error!", ActualizarVersion.resultado)
                    }
                }
            }
        }
        thread.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun abrirInstalador(){
        try {
            verifyStoragePermissions(this)

        } catch (e : Exception){
            funcion.mensaje(this,"",e.message.toString())
        }
    }

    private fun inicializaETAccion(etAccion:EditText){
        etAccion.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun afterTextChanged(s: Editable?) {
                if (etAccion.text.toString() == "DESCARGAR"){
                    descargarActualizacion()
                    etAccion.setText("")
                    return
                }
                if (etAccion.text.toString() == "ACTUALIZAR"){
                    abrirInstalador()
                    etAccion.setText("")
                }
                if (etAccion.text.toString() == "sincronizar"){
                    etAccion.setText("")
                    if (!cargarUsuarioInicial()){
                        funcion.mensaje(this@MainActivity,"Atención!","Debe configurar un usuario para continuar")
                        return
                    }
                    if (cargarUsuarioInicial()){
                        val tiempo = if (FuncionesUtiles.usuario["LOGIN"] == "12345"){
                            16
                        } else {
                            verificaTiempoTranscurrido()
//                            16
                        }
//                        if ( 16 > 15){
                        if ( tiempo > 15){
                            Sincronizacion.tipoSinc = "T"
                            val menu2 = Intent(this@MainActivity, Sincronizacion::class.java)
                            startActivity(menu2)
//                            sincronizar()
                        }else{
                            funcion.toast(this@MainActivity,"Debe esperar 15 minutos antes de volver a sincronizar")
                            return
                        }
                    } else {
                        funcion.mensaje(this@MainActivity,"Atencion!","Debe configurar un usuario")
                        return
                    }
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

    @Throws(IOException::class)
    private fun crearArchivo() {
        // Crea el archivo para ubicar el instalador
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val archivo = File(storageDir,"supervisor_02.apk")
        archivo.createNewFile()
        AcercaDe.nombre = archivo.absolutePath
    }

    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission( activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so Prompt the user
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE)
            val file = File(AcercaDe.nombre)
            if (Build.VERSION.SDK_INT >= 24) {
                val fileUri = FileProvider.getUriForFile(baseContext,"apolo.supervisor.com.fileprovider",file)
                val intent = Intent(Intent.ACTION_DEFAULT, fileUri)
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, false)
//                intent.setDataAndType(fileUri, "application/vnd.android.package-archive")
                intent.data = fileUri
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

//    private fun abrir(){
//        val file = File(AcercaDe.nombre)
//        val fileUri = FileProvider.getUriForFile(baseContext,"apolo.supervisor.com.fileprovider",file)
//        val intent = Intent(Intent.ACTION_DEFAULT, fileUri)
//        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, false)
//        intent.setDataAndType(fileUri, "application/vnd.android.package-archive")
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        startActivity(intent)
//    }

    @SuppressLint("Recycle")
    fun verificaTiempoTranscurrido():Int{
        lateinit var cursor : Cursor
        var tiempo = 16
        try {
            cursor = bd!!.rawQuery("SELECT * FROM svm_vendedor_pedido", null)
        } catch(e: java.lang.Exception){
            e.message
        }
        var fechaHoraSincro = "01/01/2020 00:00:00"
        if (cursor.moveToFirst()) {
            cursor.moveToLast()
            val fechaSincro =  cursor.getString(cursor.getColumnIndex("ULTIMA_SINCRO"))
            val horaSincro = cursor.getString(cursor.getColumnIndex("HORA"))
            fechaHoraSincro = "$fechaSincro $horaSincro" //+ ":00"
            if (horaSincro.length<6){
                fechaHoraSincro = "$fechaSincro $horaSincro:00"
            }
            tiempo = funcion.tiempoTranscurrido(fechaHoraSincro, funcion.getFechaHoraActual())
        }
        tiempo = 16
        return tiempo
    }
}

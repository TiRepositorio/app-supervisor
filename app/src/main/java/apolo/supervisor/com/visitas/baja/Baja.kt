package apolo.supervisor.com.visitas.baja

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.*
import kotlinx.android.synthetic.main.activity_baja_cliente.*
import java.io.File
import java.io.IOException
import java.util.*

class Baja: AppCompatActivity() {

    companion object{
        var codVendedorOld = ""
        @SuppressLint("StaticFieldLeak")
        lateinit var foto : FuncionesFoto
        var codCliente    = ""
        var codSubcliente = ""
    }

    private lateinit var funcion : FuncionesUtiles
    private lateinit var ubicacion: FuncionesUbicacion
    private lateinit var dispositivo : FuncionesDispositivo
    private lateinit var lm : LocationManager
    private var tipoFoto : String = ""
    var nombre : String = ""
    private var imagenFachada: String? = null
    private lateinit var thread: Thread
    private lateinit var dialogo: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baja_cliente)

        inicializar()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun inicializar(){
        dispositivo = FuncionesDispositivo(this)
        funcion = FuncionesUtiles(this)
        ubicacion = FuncionesUbicacion(this)
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        ubicacion.obtenerUbicacion(lm)
        foto = FuncionesFoto(this)


        val telMgr : TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (!dispositivo.horaAutomatica() ||
            !dispositivo.modoAvion() ||
            !dispositivo.zonaHoraria() ||
            !dispositivo.tarjetaSim(telMgr) ||
            !ubicacion.validaUbicacionSimulada(lm) ){
            funcion.toast(this,"Verifique su configuración para continuar.")
            finish()
        }
        imgBuscarCliente.setOnClickListener { buscar() }
        etCodigo()
        btVolver.setOnClickListener { finish() }
        btBuscarEnMapa.setOnClickListener { verCliente() }
        ibtFotoFachada.setOnClickListener { if (validacion()) { sacarFoto() } }
        etComentario.setOnClickListener { funcion.dialogoEntradaTextoLargo(etComentario,this) }
        btEnviar.setOnClickListener { enviar() }
    }

    private fun buscar(){
        val dialogo = DialogoBusqueda(this,"svm_cliente_vendedor"
            ,"COD_CLIENTE || '-' || COD_SUBCLIENTE COD_CLIENTE","DESC_CLIENTE"
            ,"DESC_CLIENTE",/*" and COD_VENDEDOR = '$codVendedor' "*/""
            ,etCodigo,etNombreCliente)
        dialogo.cargarDialogo(true)
    }

    private fun etCodigo(){
        etCodigo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                if (etCodigo.text.toString().split("-").size>1){
                    cargarDatos(s.toString().trim())
                }
            }
        })
    }

    private fun cargarDatos(cod:String){
        codCliente    = cod.split("-")[0].trim()
        codSubcliente = cod.split("-")[1].trim()
        val sql = "select * from svm_cliente_vendedor           " +
//                " where COD_VENDEDOR      = '$codVendedor'    " +
//                "   and COD_CLIENTE       = '$codCliente'     " +
                " where COD_CLIENTE       = '$codCliente'    " +
                "   and COD_SUBCLIENTE    = '$codSubcliente'  "
        val cursor = funcion.consultar(sql)
        etNombreSubcliente.setText(funcion.dato(cursor,"DESC_SUBCLIENTE"))
        etRUC.setText(funcion.dato(cursor,"RUC"))
        etCodZona.setText(funcion.dato(cursor,"COD_ZONA"))
        etDescZona.setText(funcion.dato(cursor,"DESC_ZONA"))
        etRegion.setText(funcion.dato(cursor,"DESC_REGION"))
        etCiudad.setText(funcion.dato(cursor,"DESC_CIUDAD"))
        etDireccion.setText(funcion.dato(cursor,"DIRECCION"))
        etCercaDe.setText(funcion.dato(cursor,"CERCA_DE"))
        etCelular.setText(funcion.dato(cursor, "TELEFONO"))
        etLimiteCredito.setText(funcion.numero("0",funcion.dato(cursor,"LIMITE_CREDITO")))
        etCodTipoCliente.setText(funcion.dato(cursor,"TIP_CLIENTE"))
        etDescTipoCliente.setText(funcion.dato(cursor,"DESC_TIPO"))
        etTotalDeuda.setText(funcion.numero("0",funcion.dato(cursor,"TOT_DEUDA")))
        etCodCondVenta.setText(funcion.dato(cursor,"COD_CONDICION"))
        etDescCondVenta.setText(funcion.dato(cursor,"DESC_CONDICION"))
        tvLatitud.text = funcion.dato(cursor,"LATITUD")
        tvLongitud.text = funcion.dato(cursor,"LONGITUD")
        codVendedorOld = funcion.dato(cursor,"COD_VENDEDOR")
        ivFachada.visibility = View.GONE
        etComentario.setText("")
        imagenFachada = null
    }

    private fun verCliente(){
        if (codCliente.trim().isNotEmpty()){
            val sql = "select DISTINCT COD_CLIENTE, DESC_CLIENTE, DIRECCION, LATITUD, LONGITUD from svm_cliente_vendedor " +
//                    " where COD_VENDEDOR      = '$codVendedor'    " +
//                    "   and COD_CLIENTE       = '$codCliente'   " +
                    " where COD_CLIENTE       = '$codCliente'    " +
                    "   and COD_SUBCLIENTE    = '$codSubcliente'  "
            funcion.cargarLista(Mapa.lista,funcion.consultar(sql))
            Mapa.modificarCliente = false
            val intent = Intent(this, Mapa::class.java)
            startActivity(intent)
        }
    }

    private fun sacarFoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val code = 1
        tipoFoto = "1"

        try {
            nombre = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/fachada.jpg"

            lateinit var output : Uri

            if (Build.VERSION.SDK_INT >= 25){
                sacarFoto25()
            } else {
                output = Uri.fromFile(File(nombre))
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output)
                startActivityForResult(intent, code)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun validacion():Boolean{
        if (!dispositivo.horaAutomatica()){
            return false
        }
        if (!dispositivo.modoAvion()){
            return false
        }
        if (!ubicacion.validaUbicacionSimulada(lm)){
            return false
        }
        ubicacion.obtenerUbicacion(lm)
        if (!ubicacion.verificarUbicacion()){
            return false
        }
        if (tvLatitud.text.toString().trim() == "" || tvLongitud.text.toString().trim() == ""){
            return false
        }
        if (!ubicacion.distanciaMinima(tvLatitud.text.toString(),tvLongitud.text.toString(),75)){
            return false
        }
        return true
    }

    private fun verificaCampos():Boolean{
        if (etComentario.text.toString().trim() == ""){
            funcion.toast(this,"El campo comentario es obligatorio.")
            return false
        }
        if (etComentario.text.toString().indexOf(" ") == -1){
            funcion.toast(this,"Comentario inválido.")
            return false
        }
        if (etComentario.text.toString().split(" ").size < 5){
            funcion.toast(this,"Comentario demasiado corto. " )
            return false
        }
        if (imagenFachada.isNullOrEmpty()){
            funcion.toast(this,"Debe tomar una foto de la fachada del local del cliente.")
            return false
        }
        return true
    }

    private fun enviar(){
        if (!verificaCampos()){
            return
        }
        EnviarBaja.context = this
        EnviarBaja.accion  = etAccion
        EnviarBaja.fotoFachada = imagenFachada!!
        EnviarBaja.comentario = etComentario.text.toString().uppercase(Locale.ROOT)
        val enviar = EnviarBaja()
        enviar.cargar()
        enviarN()
    }

    @Throws(IOException::class)
    private fun crearImagen(): File {
        // Crea el archivo para ubicar la foto
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("fachada", ".jpg", storageDir).apply {
            // Save a file: path for use with ACTION_VIEW intents
            nombre = absolutePath
        }
    }

    private fun sacarFoto25() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    crearImagen()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
                photoFile?.also {
                    val output : Uri = FileProvider.getUriForFile(
                        this,
                        "apolo.supervisor.com.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, output)
                    startActivityForResult(takePictureIntent, 1)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagenFachada = foto.foto1(requestCode, nombre, ivFachada, tipoFoto)
        if (imagenFachada!!.isEmpty()){
            imagenFachada = foto.foto2(requestCode, data, ivFachada, ivFachada, nombre, tipoFoto)
        }
        if (!imagenFachada.isNullOrEmpty()){
            ivFachada.visibility = View.VISIBLE
        }
    }

    private fun enviarN(){
        dialogo = ProgressDialog(this)
        thread = Thread{
            runOnUiThread{ dialogo.cargarDialogo("Comprobando conexion",false) }
            try {
                EnviarBaja.resultado = MainActivity.conexionWS.enviarBaja(codVendedorOld,codCliente,codSubcliente,
                    EnviarBaja.cliente,
                    EnviarBaja.fotoFachada,
                    FuncionesUtiles.usuario["PASS"]!!
                )
            } catch (e: java.lang.Exception) {
                EnviarBaja.resultado = e.message.toString()
            }
            runOnUiThread {
                dialogo.cerrarDialogo()
                if (EnviarBaja.resultado.split("*")[0].trim() == "01") {
                    funcion.mensaje(
                        EnviarBaja.context,
                        "Atención",
                        "Se guardó correctamente"
                    )
                } else {
                    funcion.mensaje(
                        EnviarBaja.context,
                        "Error al intentar enviar",
                        "No se ha podido enviar el archivo, intente más tarde.\n${EnviarBaja.resultado}"
                    )
                }
            }
            if (EnviarBaja.resultado != "null") {
                return@Thread
            }
            runOnUiThread {
                funcion.toast(this,"Verifique su conexion a internet y vuelva a intentarlo")
                finish()
            }
        }
        thread.start()
    }

}
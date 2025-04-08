package apolo.supervisor.com.ventas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.DialogoAutorizacion
import apolo.supervisor.com.utilidades.FuncionesFoto
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_modificar_cliente.*
import java.io.File
import java.io.IOException


class ModificarCliente : AppCompatActivity() {

    companion object{
        var codCliente : String = ""
        var codSubcliente : String = ""
        @SuppressLint("StaticFieldLeak")
        lateinit var foto : FuncionesFoto
    }

    var funcion : FuncionesUtiles = FuncionesUtiles()
    private var idCat : String = ""
    var tipo  : String = ""
    private var fotoFachada : String = ""
    private var tipoFoto : String = ""
    var nombre : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_cliente)

        foto = FuncionesFoto(this)
        funcion = FuncionesUtiles(this)
        inicializarElementos()
    }

    fun inicializarElementos(){
        cargar(funcion.consultar(sqlModificar()),funcion.consultar(sqlCliente()))
        inicializarEt(etTel1,cbConfirmado1)
        inicializarEt(etTel2,cbConfirmado2)
        inicializarEt(etDireccion,cbConfirmado3)
        inicializarEt(etCercaDe,cbConfirmado4)
        ibtnFotoFachada.setOnClickListener{sacarFoto()}
        btnAceptar.setOnClickListener{registrar()}
        btnCancelar.setOnClickListener{finish()}
        inicializaETAccion(accion)
    }

    private fun sqlModificar():String{
        return ("Select id, COD_CLIENTE, COD_SUBCLIENTE, TELEFONO1, TELEFONO2, DIRECCION, CERCA_DE, FOTO_FACHADA, TIPO "
                    + " from svm_modifica_catastro_venta "
                    + " WHERE COD_CLIENTE    = '" + codCliente + "'"
                    + "   and COD_SUBCLIENTE = '" + codSubcliente + "'"
                    + "   and ESTADO         = 'P' ")
    }

    private fun sqlCliente():String{
        return "SELECT COD_CLIENTE,COD_SUBCLIENTE,TELEFONO,TELEFONO2,DIRECCION,CERCA_DE,FOTO_FACHADA " +
                           "  FROM svm_cliente_vendedor " +
                           " WHERE COD_CLIENTE    = '" + codCliente + "' " +
                           "   AND COD_SUBCLIENTE = '" + codSubcliente + "' " +
                           " GROUP BY COD_CLIENTE,COD_SUBCLIENTE,TELEFONO,TELEFONO2,DIRECCION,CERCA_DE,FOTO_FACHADA"
    }

    fun cargar(modificar: Cursor,cliente:Cursor){
        if (modificar.count == 0){
            etTel1.setText(funcion.dato(cliente,"TELEFONO").replace("null",""))
            etTel2.setText(funcion.dato(cliente,"TELEFONO2").replace("null",""))
            etDireccion.setText(funcion.dato(cliente,"DIRECCION").replace("null",""))
            etCercaDe.setText(funcion.dato(cliente,"CERCA_DE").replace("null",""))
            idCat = ""
            tipo = ""
        } else {
            etTel1.setText(funcion.dato(modificar,"TELEFONO1").replace("null",""))
            etTel2.setText(funcion.dato(modificar,"TELEFONO2").replace("null",""))
            etDireccion.setText(funcion.dato(modificar,"DIRECCION").replace("null",""))
            etCercaDe.setText(funcion.dato(modificar,"CERCA_DE").replace("null",""))
            idCat = funcion.dato(modificar,"id").replace("null","")
            tipo = funcion.dato(modificar,"id").replace("null","")
            try {
                fotoFachada = funcion.dato(modificar,"FOTO_FACHADA").replace("null","")
            } catch (e : java.lang.Exception) {
                e.printStackTrace()
            }
            if (fotoFachada.trim().replace("null","") != "") {
                try {
                    val img: ByteArray? = foto.stringToByte2(fotoFachada)
                    val bitmap = BitmapFactory.decodeByteArray(img, 0, img!!.size)
                    ivFachada.setImageBitmap(bitmap)
                } catch (e2: Exception) {
                    e2.message
                }
            }
        }
    }

    private fun inicializarEt(editText: EditText, checkBox: CheckBox){
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkBox.isChecked = editText.text.toString().isNotEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
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
        } catch (e:Exception){
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun crearImagen(): File {
        // Crea el archivo para ubicar la foto
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("fachada",".jpg", storageDir).apply {
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
                    val output : Uri = FileProvider.getUriForFile(this,"apolo.supervisor.com.fileprovider",it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, output)
                    startActivityForResult(takePictureIntent, 1)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        foto.foto1(requestCode, nombre, ivFachada, tipoFoto)
        foto.foto2(requestCode, data, ivFachada, ivFachada, nombre, tipoFoto)
    }

    private fun validaDatos():Boolean{
        val validacion : Boolean = (cbConfirmado1.isChecked && cbConfirmado2.isChecked && cbConfirmado3.isChecked and cbConfirmado4.isChecked)
        if (!validacion){
            funcion.mensaje(this,"Atención!","Debe confirmar todos los campos.")
        }
        return validacion
    }

    private fun registrar(){
        if (validaDatos()){
            if (idCat == "") {
                val cv = ContentValues()
                cv.put("COD_EMPRESA", FuncionesUtiles.usuario["COD_EMPRESA"])
                cv.put("COD_CLIENTE", codCliente)
                cv.put("COD_SUBCLIENTE", codSubcliente)
                cv.put("TELEFONO1", etTel1.text.toString())
                cv.put("TELEFONO2", etTel2.text.toString())
                cv.put("DIRECCION", etDireccion.text.toString())
                cv.put("CERCA_DE", etCercaDe.text.toString())
                cv.put("LATITUD", "")
                cv.put("LONGITUD", "")
                cv.put("FECHA", funcion.getFechaActual())
                cv.put("ESTADO", "P")
                cv.put("FOTO_FACHADA", foto.fotoFachada)
                cv.put("TIPO", "D")
                funcion.insertar("svm_modifica_catastro_venta", cv)
            } else {
                val cv = ContentValues()
                cv.put("COD_EMPRESA", FuncionesUtiles.usuario["COD_EMPRESA"])
                cv.put("COD_CLIENTE", codCliente)
                cv.put("COD_SUBCLIENTE", codSubcliente)
                cv.put("TELEFONO1", etTel1.text.toString())
                cv.put("TELEFONO2", etTel2.text.toString())
                cv.put("DIRECCION", etDireccion.text.toString())
                cv.put("CERCA_DE", etCercaDe.text.toString())
                cv.put("ESTADO", "P")
                cv.put("FOTO_FACHADA", foto.fotoFachada)
                if (tipo == "G") {
                    cv.put("TIPO", "A")
                } else {
                    cv.put("TIPO", tipo)
                }
                funcion.actualizar("svm_modifica_catastro_venta",cv, " id = '$idCat'")
            }
            val gatillo = DialogoAutorizacion(this)
            gatillo.dialogoAccionOpcion("enviar","cerrar",accion,"Desea enviar los datos al servidor?","Guardado con exito","Si","No")
        }
    }

    private fun inicializaETAccion(etAccion:EditText){
        etAccion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etAccion.text.toString() == "cerrar"){
                    etAccion.setText("")
                    finish()
                    return
                }
                if (etAccion.text.toString() == "enviar"){
                    etAccion.setText("")
                    enviar()
                    return
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun enviar(){
        EnviarModificacion.context = this
        EnviarModificacion.codCliente = codCliente
        EnviarModificacion.codSubcliente = codSubcliente
        EnviarModificacion.fotoFachada = fotoFachada
        EnviarModificacion.accion = accion
        val enviarModificacion = EnviarModificacion()
        enviarModificacion.enviar()
    }

}

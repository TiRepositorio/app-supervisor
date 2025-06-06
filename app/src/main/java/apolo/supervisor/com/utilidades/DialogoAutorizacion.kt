package apolo.supervisor.com.utilidades

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import apolo.supervisor.com.R
import kotlinx.android.synthetic.main.menu_cliente.*
import kotlinx.android.synthetic.main.menu_cliente_texto.*

class DialogoAutorizacion(var context: Context) {

    private var claves : Clave = Clave()
    var funcion : FuncionesUtiles = FuncionesUtiles(context)

    fun dialogoAutorizacion(accion:String, cargaAcion : EditText){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
//        var claveTemp : String = "54127854"
        val claveTemp : String = claves.smvClave()
        dialogo.setTitle("Solicitar autorizacion")

        dialogo.setMessage(claveTemp)
        val contraClave = EditText(context)
        contraClave.inputType = InputType.TYPE_CLASS_NUMBER
        dialogo.setView(contraClave)
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (contraClave.text.isEmpty()||contraClave.text.length != 8){
                funcion.mensaje("Error","Clave incorrecta")
            } else {
                if (contraClave.text.toString().trim() == claves.contraClave(claveTemp).trim()){
                    cargaAcion.setText(accion)
                    funcion.mensaje("Correcto","La clave fue aceptada")
                } else {
                    funcion.mensaje("Error","La clave no es valida")
                }
            }
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

    fun dialogoAutorizacion(accion:String, cargaAcion : EditText,mensaje:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
//        var claveTemp : String = "54127854"
        val claveTemp : String = claves.smvClave()
        dialogo.setTitle("Solicitar autorizacion")

        dialogo.setMessage("$mensaje \n$claveTemp")
        val contraClave = EditText(context)
        contraClave.inputType = InputType.TYPE_CLASS_NUMBER
        dialogo.setView(contraClave)
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (contraClave.text.isEmpty()||contraClave.text.length != 8){
                funcion.mensaje("Error","Clave incorrecta")
            } else {
                if (contraClave.text.toString().trim() == claves.contraClave(claveTemp).trim()){
                    cargaAcion.setText(accion)
                    funcion.mensaje("Correcto","La clave fue aceptada")
                } else {
                    funcion.mensaje("Error","La clave no es valida")
                }
            }
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

    @SuppressLint("SetTextI18n")
    fun dialogoAutorizacion(accion:String, noAccion: String, cargaAcion : EditText, mensaje:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
//        var claveTemp : String = "54127854"
        val claveTemp : String = claves.smvClave()
        dialogo.setTitle("Solicitar autorizacion")

        dialogo.setMessage("$mensaje \n$claveTemp")
        val contraClave = EditText(context)
        contraClave.inputType = InputType.TYPE_CLASS_NUMBER
        dialogo.setView(contraClave)
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (contraClave.text.isEmpty()||contraClave.text.length != 8){
                cargaAcion.setText("$noAccion*${contraClave.text}*")
                funcion.mensaje("Error","Clave incorrecta")
            } else {
                if (contraClave.text.toString().trim() == claves.contraClave(claveTemp).trim()){
                    cargaAcion.setText("$accion*${contraClave.text}*")
                    funcion.mensaje("Correcto","La clave fue aceptada")
                } else {
                    cargaAcion.setText("$noAccion*${contraClave.text}*")
                    funcion.mensaje("Error","La clave no es valida")
                }
            }
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

    fun dialogoAutorizacion(accion:String,noAccion:String, cargaAcion : EditText,mensaje:String, titulo:String,codigo:String,mTrue:String,mFalse:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        dialogo.setTitle(titulo)
        dialogo.setMessage(mensaje)
        val contraClave = EditText(context)
        contraClave.inputType = InputType.TYPE_CLASS_NUMBER
        dialogo.setView(contraClave)
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (contraClave.text.toString().trim() == codigo){
                cargaAcion.setText(accion)
                if (mTrue.isNotEmpty()){
                    funcion.mensaje(context,"Atencion!",mTrue)
                }
            } else {
                cargaAcion.setText(noAccion)
                funcion.mensaje(context,"Error!",mFalse)
            }
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

    fun dialogoAccion(accion:String, cargaAcion : EditText, mensaje:String,titulo:String,boton:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        dialogo.setTitle(titulo)
        dialogo.setMessage(mensaje)
        dialogo.setPositiveButton(boton) { _: DialogInterface, _: Int ->
            cargaAcion.setText(accion)
        }
        dialogo.setCancelable(true)
        dialogo.show()
    }

    fun dialogoAccionOpcion(accionAceptar:String,accionCancelar:String, cargaAcion : EditText, mensaje:String,titulo:String,botonAceptar:String,botonCancelar:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        dialogo.setTitle(titulo)
        dialogo.setMessage(mensaje)
        dialogo.setPositiveButton(botonAceptar) { _: DialogInterface, _: Int ->
            cargaAcion.setText(accionAceptar)
        }
        dialogo.setNegativeButton(botonCancelar) { _: DialogInterface, _: Int ->
            cargaAcion.setText(accionCancelar)
        }
        dialogo.setCancelable(true)
        dialogo.show()
    }

    @SuppressLint("SetTextI18n")
    fun dialogoMapa(cargaAcion: EditText){
        val dialogo = Dialog(context)
        try {
            dialogo.setContentView(R.layout.menu_cliente)
        } catch (e:Exception){
            dialogo.setContentView(R.layout.menu_cliente_texto)
            dialogo.btCliente.setOnClickListener{
                cargaAcion.setText("cliente")
                dialogo.dismiss()
            }
            dialogo.btRuteo.setOnClickListener{
                cargaAcion.setText("ruteo")
                dialogo.dismiss()
            }
            dialogo.setCancelable(true)
            dialogo.show()
            dialogo.show()
            return
        }
        dialogo.ibtnCliente.setOnClickListener{
            cargaAcion.setText("cliente")
            dialogo.dismiss()
        }
        dialogo.ibtnRuteo.setOnClickListener{
            cargaAcion.setText("ruteo")
            dialogo.dismiss()
        }
        dialogo.imgCliente.setOnClickListener{
            cargaAcion.setText("cliente")
            dialogo.dismiss()
        }
        dialogo.imgRuteo.setOnClickListener{
            cargaAcion.setText("ruteo")
            dialogo.dismiss()
        }
        dialogo.setCancelable(true)
        dialogo.show()
    }

}
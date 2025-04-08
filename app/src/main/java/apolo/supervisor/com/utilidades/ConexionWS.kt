package apolo.supervisor.com.utilidades

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.Base64
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.configurar.AcercaDe
import apolo.supervisor.com.configurar.ActualizarVersion
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.io.*
import java.util.*
import java.util.zip.GZIPInputStream

@Suppress("PrivatePropertyName", "LocalVariableName")
class ConexionWS {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var resultado: String
    }

    private val NAMESPACE = "http://edsystem/servidor"
    private var METHOD_NAME = ""
    private val URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
    private var SOAP_ACTION = "$NAMESPACE/$METHOD_NAME"

    fun setMethodName(name: String) {
        METHOD_NAME = name
        SOAP_ACTION = "$NAMESPACE/$ "
    }

    fun procesaVersion():String{

        lateinit var resultado: String

        val request = SoapObject(NAMESPACE, METHOD_NAME)
        request.addProperty("usuario", "edsystem")
        request.addProperty("password", "#edsystem@polo")
        request.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
        request.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)

        envelope.dotNet = false
        envelope.setOutputSoapObject(request)

        val transporte = HttpTransportSE(URL)

        try {
            transporte.call(SOAP_ACTION, envelope)
            val sp:SoapPrimitive? = envelope.response as SoapPrimitive?
            resultado = sp.toString()

        } catch (e: Exception){
            var error : String = e.message.toString()
            error += ""
            resultado = error
        }

        return resultado
    }

    private fun descomprimir(direccionEntrada: String, direccionSalida: String):Boolean{
        try {
            val entrada = GZIPInputStream(FileInputStream(direccionEntrada))
            val salida = FileOutputStream(direccionSalida)
            val buf = ByteArray(1024)
            var len: Int = entrada.read(buf)
            while (len>0){
                salida.write(buf,0,len)
                len = entrada.read(buf)
            }
            salida.close()
            entrada.close()
            val archivo = File(direccionEntrada)
            archivo.delete()
            return true
        }catch (e:FileNotFoundException){
//            var error = e.message
//            error = "" + error
        }catch (e:IOException){
//            var error = e.message
//            error = "" + error
        }
        return false
    }

    fun generaArchivosSupervisor()  : Boolean{
        val NAMESPACE = "http://edsystem/servidor"
        val URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "GeneraArchivosSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/GeneraArchivosSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
            solicitud.addProperty("codPersona", FuncionesUtiles.usuario["COD_PERSONA"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,480000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
            if (resultado.indexOf("01*") != -1){
                return false
            }
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }
        return true
    }

    fun generaInformeSegSupervisor()  : Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "GeneraInformeSegSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/GeneraInformeSegSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
            solicitud.addProperty("codPersona", FuncionesUtiles.usuario["COD_PERSONA"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,480000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
            if (resultado.indexOf("01*") != -1){
                return false
            }
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }
        return true
    }

    fun generaInformeVisitaSupervisor()  : Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "GeneraInformeVisitaSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/GeneraInformeVisitaSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
            solicitud.addProperty("codPersona", FuncionesUtiles.usuario["COD_PERSONA"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,480000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
            if (resultado.indexOf("01*") != -1){
                return false
            }
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }
        return true
    }

    fun generaInformeSupervisor()  : Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "GeneraInformeSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/GeneraInformeSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,480000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
            if (resultado.indexOf("01*") != -1){
                return false
            }
        } catch (e : Exception){
            e.message
//            error = "" + error
            return false
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun obtieneArchivosSupervisorNuevo(): Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ObtieneArchivosSupervisorNuevo"
        val SOAP_ACTION = "http://edsystem/servidor/ObtieneArchivosSupervisorNuevo"

        lateinit var solicitud : SoapObject
        lateinit var resultado : Vector<*>
        try {
            solicitud = SoapObject(NAMESPACE, METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response as Vector<*>
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        try {
            val listaTablas : Vector<String> = MainActivity.tablasSincronizacion.listaSQLCreateTables()
            if (resultado.size>50){
                extraerDatos(resultado, listaTablas)
            }

        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        return true
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun obtieneInformesSegSupervisor(): Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ObtieneInformesSegSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesSegSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : Vector<*>
        try {
            solicitud = SoapObject(NAMESPACE, METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response as Vector<*>
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        try {
            val listaTablas : Vector<String> = MainActivity.tablasReportes.listaSQLCreateTableReportes()
            if (resultado.size>7){
                extraerDatos(resultado, listaTablas)
            }

        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        return true
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun obtieneInformesVisitaSupervisor(): Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ObtieneInformesVisitaSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesVisitaSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : Vector<*>
        try {
            solicitud = SoapObject(NAMESPACE, METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response as Vector<*>
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        try {
            val listaTablas : Vector<String> = MainActivity.tablasVisitas.listaSQLCreateTableVisitas()
            if (resultado.size>3){
                extraerDatos(resultado, listaTablas)
            }
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        return true
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun obtieneInformesSupervisor(): Boolean{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ObtieneInformesSupervisor"
        val SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesSupervisor"

        lateinit var solicitud : SoapObject
        lateinit var resultado : Vector<*>
        try {
            solicitud = SoapObject(NAMESPACE, METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codSupervisor", FuncionesUtiles.usuario["PASS"])
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response as Vector<*>
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        try {
            val listaTablas : Vector<String> = MainActivity.tablasInformes.listaSQLCreateTableInformes()
            if (resultado.size>15){
                extraerDatos(resultado, listaTablas)
            }
        } catch (e : Exception){
//            var error = e.message
//            error = "" + error
            return false
        }

        return true
    }

    @SuppressLint("SdCardPath")
    fun extraerDatos(resultado : Vector<*>, listaTablas:Vector<String>) : Boolean{
        lateinit var fos : FileOutputStream
        try {
            for (i in 0 until resultado.size){
                fos = FileOutputStream("/data/data/apolo.supervisor.com/" +listaTablas[i].split(" ")[5] + ".gzip")
                fos.write(Base64.decode(resultado[i].toString(),0))
//                fos.write(java.util.Base64.getDecoder().decode(resultado.get(i).toString()))
                fos.close()
                descomprimir("/data/data/apolo.supervisor.com/"+listaTablas[i].split(" ")[5] +".gzip",
                    "/data/data/apolo.supervisor.com/"+listaTablas[i].split(" ")[5] +".txt" )
            }
        } catch (e : Exception) {
            e.message
            return false
        }
        return true
    }

    //enviar datos para modificar clientes
    fun procesaActualizaClienteSupAct(clientes:String,codVendedor:String,codSupervisor:String) : String{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaActualizaClienteSupAct"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaClienteSupAct"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("vcodSupervisor", codSupervisor)
            solicitud.addProperty("vcodVendedor", codVendedor)
            solicitud.addProperty("vclientes", clientes.replace("''"," ").replace("'",""))
        } catch (e: Exception) {
            return e.message.toString()
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }
        return resultado
    }

    fun procesaAutorizaSDSup(codRepartidor:String,cabecera:String,detalle:String) : String{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaAutorizaSDSup"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaAutorizaSDSup"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codRepartidor", codRepartidor)
            solicitud.addProperty("cabecera", cabecera)
            solicitud.addProperty("detalle", detalle)
        } catch (e: Exception) {
            return e.message.toString()
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }
        return resultado
    }

    fun procesaActualizaDatosClienteFinal(codVendedor:String,clientes:String,vFotoFachada:String) : String{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaActualizaClienteSupAct"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaClienteSupAct"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("vcodVendedor", codVendedor)
            solicitud.addProperty("vclientes", clientes.replace("''"," ").replace("'",""))
            solicitud.addProperty("vfoto_fachada", vFotoFachada)
        } catch (e: Exception) {
            return e.message.toString()
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }
        return resultado
    }

    fun procesaSeguimientoAct(cabCliente:String, detCliente:String,codSupervisor:String,fechaVisita:String) : String{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaSeguimiento"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaSeguimiento"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codSupervisor", codSupervisor)
            solicitud.addProperty("fecMovimiento", fechaVisita)
            solicitud.addProperty("cab_cliente", cabCliente)
            solicitud.addProperty("det_cliente", detCliente)
        } catch (e: Exception) {
            return e.message.toString()
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }
        return resultado
    }

    fun enviaMarcacionesPendientes(codVendedor: String,marcaciones:String) : String{
        val NAMESPACE   = "http://edsystem/servidor"
        val URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaMarcacion"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaMarcacion"

        lateinit var solicitud : SoapObject
        lateinit var resultado : String

        try {
            solicitud = SoapObject(NAMESPACE,METHOD_NAME)
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codVendedor", codVendedor)
            solicitud.addProperty("detalle", marcaciones)
        } catch (e: Exception) {
            return e.message.toString()
        }

        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL,240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            resultado = envelope.response.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }
        return resultado
    }

    fun procesaRuteoSemanal(vdetalle: String): String {
        val NAMESPACE = "http://edsystem/servidor"
        val URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaMarcacion"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaMarcacion"
        lateinit var solicitud : SoapObject
        lateinit var resultado : String
        try {
            solicitud = SoapObject(NAMESPACE, METHOD_NAME)
            solicitud.addProperty("usuario", "edsystem")
            solicitud.addProperty("password", "#edsystem@polo")
            solicitud.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
            solicitud.addProperty( "codVendedor", FuncionesUtiles.usuario["PASS"])
            solicitud.addProperty("detalle", vdetalle)
        } catch (e: Exception) {
            var err = e.message
            err = "" + err
            return err
        }
        val envelope = SoapSerializationEnvelope(
            SoapEnvelope.VER11
        )
        envelope.dotNet = false
        envelope.setOutputSoapObject(solicitud)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            val sp = envelope.response as SoapPrimitive
            resultado = sp.toString()
        } catch (e: Exception) {
            var err = e.message
            err = "" + err
            return err
        }
        return resultado
    }

    fun obtieneInstalador(): Boolean {
        val NAMESPACE = "http://edsystem/servidor"
        val URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaInstaladorSupervisores"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaInstaladorSupervisores"
        lateinit var request: SoapObject
        try {
            request = SoapObject(NAMESPACE, METHOD_NAME)
            request.addProperty("usuario", "edsystem")
            request.addProperty("password", "#edsystem@polo")
        } catch (e: java.lang.Exception) {
            e.message
            return false
        }
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(request)
        val transporte = HttpTransportSE(URL, 240000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            val sp = envelope.response as SoapPrimitive
            resultado = sp.toString()
            val fos: FileOutputStream?
//            fos = FileOutputStream("/data/data/apolo.supervisor.com/supervisor_02.apk")
            fos = FileOutputStream(AcercaDe.nombre)
            fos.write(Base64.decode(resultado.toByteArray(),Base64.DEFAULT))
            fos.close()
        } catch (e: java.lang.Exception) {
            var err = e.message
            err = "" + err
            resultado = err
            return false
        }
        return true
    }

    fun enviarPedido(cabecera:String,detalles:String,nroComprobante:String,codVendedor:String): String {
        val NAMESPACE = "http://edsystem/servidor"
        val URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem"
        val METHOD_NAME = "ProcesaPedidoSup"
        val SOAP_ACTION = "http://edsystem/servidor/ProcesaPedidoSup"
        val request = SoapObject(NAMESPACE, METHOD_NAME)
        request.addProperty("usuario", "edsystem")
        request.addProperty("password", "#edsystem@polo")
        request.addProperty("codEmpresa", FuncionesUtiles.usuario["COD_EMPRESA"])
        request.addProperty("tipComprobante", "PRO")
        request.addProperty("serComprobante", codVendedor)
        request.addProperty("nroComprobante", nroComprobante)
        request.addProperty("cabecera", cabecera)
        request.addProperty("detalle", detalles)
        val envelope = SoapSerializationEnvelope(
            SoapEnvelope.VER11
        )
        envelope.dotNet = false
        envelope.setOutputSoapObject(request)
        val transporte = HttpTransportSE(URL, 240000)
        val res: String
        var error: String
        try {
            transporte.call(SOAP_ACTION, envelope)
            val resultado_xml = envelope
                .response as SoapPrimitive
            res = resultado_xml.toString()
            error = res
        } catch (e: Exception) {
            error = e.message.toString()
        }
        return error
    }



    fun enviarBaja(codVendedor: String, codCliente: String, codSubcliente: String, cliente: String, fotoFachada: String, codSupervisor: String): String {
        setMethodName("ProcesaBajaClienteSup")
        val request: SoapObject?
        val resultado: String?
        try {
            request = SoapObject(NAMESPACE, METHOD_NAME)
            request.addProperty("usuario", "edsystem")
            request.addProperty("password", "#edsystem@polo")
            request.addProperty("vcodVendedor", codVendedor)
            request.addProperty("vcodCliente", codCliente)
            request.addProperty("vcodSubcliente", codSubcliente)
            request.addProperty("vcliente", cliente)
            request.addProperty("vfoto_fachada", fotoFachada)
            request.addProperty("vcodSupervisor", codSupervisor)
        } catch (e: java.lang.Exception) {
            var err = e.message
            err = "" + err
            return err
        }
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        envelope.dotNet = false
        envelope.setOutputSoapObject(request)
        val transporte = HttpTransportSE(URL, 120000)
        try {
            transporte.call(SOAP_ACTION, envelope)
            val sp = envelope.response as SoapPrimitive
            resultado = sp.toString()
        } catch (e: java.lang.Exception) {
            var err = e.message
            err = "" + err
            return err
        }
        return resultado
    }

}
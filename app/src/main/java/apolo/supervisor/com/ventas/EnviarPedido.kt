package apolo.supervisor.com.ventas

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.location.LocationManager
import android.os.AsyncTask
import android.telephony.TelephonyManager
import android.widget.Toast
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.DialogoAutorizacion
import apolo.supervisor.com.utilidades.FuncionesDispositivo
import apolo.supervisor.com.utilidades.FuncionesUbicacion
import apolo.supervisor.com.utilidades.FuncionesUtiles
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@Suppress("LocalVariableName")
class EnviarPedido(
    var context: Context,
    var lm: LocationManager,
    private var telMgr: TelephonyManager,
    private var cabeceraHash: HashMap<String, String>
) {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var contexto : Context
        var cabecera = ""
        var detalles = ""
        var resultado = ""
        var descVarios = Pedidos.etDescVariosPedidos.text.toString().trim()
        var descFin = Pedidos.etDescFinancPedidos.text.toString().trim()
    }

    var error = ""
    var dispositivo : FuncionesDispositivo = FuncionesDispositivo(context)
    private var ubicacion : FuncionesUbicacion = FuncionesUbicacion(context)
    var funcion : FuncionesUtiles = FuncionesUtiles(context)
    private var totalPedido = 0
    lateinit var cursor : Cursor
    

    @SuppressLint("Recycle")
    fun enviarPedido() {
        if (!dispositivo.modoAvion()) {
            return
        }
        if (!dispositivo.zonaHoraria()) {
            return
        }
        if (!dispositivo.fechaCorrecta()) {
//            return
        }
        if (!dispositivo.tarjetaSim(telMgr)) {
            return
        }
		if (!ubicacion.validaUbicacionSimulada(lm)) {
			return
		}
        if (Pedidos.etTotalPedidos.toString() == "" || Pedidos.etTotalPedidos.toString() == "0") {
            return
        }
        if (descVarios == "0"){descVarios = ""}
        if (descFin == "0"){descFin=""}
        contexto = context
        try {
//            var vInsert: String
            var vIntoCab: String

            // 25 campos
            ubicacion.obtenerUbicacion(lm)
            vIntoCab = ("'" + FuncionesUtiles.usuario["COD_EMPRESA"] + "'" // cod_empresa
                    + ",'" + ListaClientes.codSucursalCliente
                    + "'" // cod_sucursal
                    + ",to_date('" + Pedidos.etFechaPedido.text
                    + "','dd/MM/yyyy')" // fec_comprobante
                    + "," + "'" + "PRO" + "'" // tip_comprobante
                    + ",'" + ListaClientes.codVendedor + "'" // ser_comprobante
                    // +","+ et_tot_pedido.getText().toString().replace(".", "")
                    + ",'" + ListaClientes.codVendedor + "'" // cod_Vendedor
                    + ",'" + ListaClientes.codCliente + "'" // cod_Cliente
                    + ",'" + ListaClientes.codSubcliente + "'" // cod_Subcliente
                    + ",'" + cabeceraHash["COD_CONDICION_VENTA"] + "'" // cod_Condicion_Venta
                    + ",'" + cabeceraHash["COD_LISTA_PRECIO"] + "'" // cod_Lista_Precio
                    + ",'" + cabeceraHash["COD_MONEDA"] + "'" // cod_Moneda
                    + ",'" + "P" + "'" // estado
                    + ",'" + "PRO" + "'" // tip_Comprobante_Ref
                    + ",'" + ListaClientes.codVendedor + "'" // ser_Comprobante_Ref
                    + ",'" + Pedidos.etNroPedidos.text.toString() + "'" // nro_Comprobante_ref
//                    					+ ",'" + Aplicacion._lati + "'" // latitud
//                    					+ ",'" + Aplicacion._longi + "'" // longitud
                    + ",'" + ubicacion.latitud  + "'" // latitud
                    + ",'" + ubicacion.longitud + "'" // longitud
//                    + ",''" // latitud
//                    + ",''" // longitud
                    + ",'" + Pedidos.etNroOrdenCompra.text.toString() + "'" // nro_Orden_Compra
                    + ",'" + Pedidos.depo + "'" // ind_Deposito
                    + ",'" + "S" + "'" // ind_Sistema
                    + ",'" + descVarios.replace(",",".") + "'" // porc_Desc_Var
                    + ",'" + descFin.replace(",",".") + "'") // descuento_fin
            if (Pedidos.spListaPrecios.getDato("DECIMALES") == "0") {
                vIntoCab = if (descVarios != "") {
                    val des_var: Float = "0".toFloat()
                    (vIntoCab + ",'" + des_var.roundToInt() + "'") // descuento_var
                } else {
                    "$vIntoCab,0" // descuento_var
                }
                vIntoCab = if (descFin != "") {
                    val des_var: Float = "0".toFloat()
                    (vIntoCab + ",'"
                            + des_var.roundToInt() + "'") // descuento_fin
                } else {
                    "$vIntoCab,0" // descuento_fin
                }
            } else {
                vIntoCab = if (descVarios != "") {
                    val des_var: Double = Pedidos.totalPedido.replace(",", ".").toDouble() * descVarios.replace(",",".")
                        .toDouble() / 100
                    "$vIntoCab,'$des_var'" // descuento_var
                } else {
                    "$vIntoCab,0" // descuento_var
                }
                vIntoCab = if (descFin != "") {
                    val des_var: Double = Pedidos.totalPedido.replace(",", ".").toDouble() * descFin.replace(",",".")
                        .toDouble() / 100
                    "$vIntoCab,'$des_var'" // descuento_fin
                } else {
                    "$vIntoCab,0" // descuento_fin
                }
            }
            vIntoCab = "$vIntoCab,'${Pedidos.indBloqueado}'" // bloqueado_x_prec
            vIntoCab = (vIntoCab + "," + "'" + Pedidos.pedidoBloqCond + "'" // bloqueado_x_cond
                    + ",'" + Pedidos.vent + "'" // ind_venta
                    + ",'" + cabeceraHash["COMENTARIO"] + "'" // comentario
                    + ",'" + cabeceraHash["NRO_AUTORIZACION"] + "'" // "nro_autorizacion
                    + ",'" + cabeceraHash["IND_PRESENCIAL"].toString().replace("null","") + "'" // "indica venta presencial
                    + ",to_date('" + funcion.getFechaActual() + " " + funcion.getHoraActual() + "','dd/MM/yyyy hh24:mi:ss')" // "fecha y hora de alta
                    + ",'" + Pedidos.claveAutorizacion + "'") //numero de autorizacion descuentos varios
            cabecera = vIntoCab
            var v_into_det: String
            var _where = (" NUMERO=" + "'" + Pedidos.maximo.toString() + "' "
                    + "   AND TRIM(COD_ARTICULO) <> '' "
                    + "   and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'")
            _where = "$_where "
            cursor = MainActivity.bd!!.query(
                "vt_pedidos_det", arrayOf(
                    "COD_EMPRESA", "ORDEN", "COD_ARTICULO", "CANTIDAD",
                    "COD_UNIDAD_MEDIDA", "COD_IVA", "PORC_IVA", "PRECIO_UNITARIO_C_IVA",
                    "MONTO_TOTAL_CONIVA", "PORC_DESC_VAR", "DESCUENTO_VAR", "PRECIO_LISTA",
                    "MULT", "ORDEN_REF", "IND_SISTEMA", "IND_TRANSLADO",
                    "IND_DEPOSITO", "IND_BLOQUEADO", "NRO_PROMOCION", "TIP_PROMOCION",
                    "NRO_AUTORIZACION", "MONTO_DESC_TC"
                ), _where,  // null, 
                null, null, null, null
            )
            val nreg: Int = cursor.count
            cursor.moveToFirst()
            totalPedido = 0
            var cont = 1
            detalles = ""
            var sql2: String
            for (i in 0 until nreg) {
//                vInsert = ("insert into vt_vt_pedidos_det_prov ( "
//                        + ""
//                        + "cod_empresa , "
//                        + "cod_sucursal,"
//                        + "tip_comprobante ,"
//                        + "ser_comprobante ,"
//                        + "orden , "
//                        + "cod_articulo ,"
//                        + "cantidad , "
//                        + "cod_unidad_medida ,"
//                        + "cod_iva ,"
//                        + "porc_iva , "
//                        + "precio_unitario_c_iva , "
//                        + "porc_desc_var ,"
//                        + "descuento_var , "
//                        + "porc_desc_fin,"
//                        + "descuento_fin,"
//                        + "precio_lista , "
//                        + "mult ,"
//                        + "tip_comprobante_ref , "
//                        + "ser_comprobante_ref , "
//                        + "nro_comprobante_ref , "
//                        + "nro_orden_ref,"
//                        + "ind_sistema,"
//                        + " ind_bloqueado  "
//                        + " ) ")
                v_into_det = ("'${FuncionesUtiles.usuario["COD_EMPRESA"]}'," //cod_empresa
                        + "'" + ListaClientes.codSucursalCliente + "'," //cod_sucursal
                        + "'PRO'," //tip_comprobante
                        + "'" + ListaClientes.codVendedor + "'," //ser_comprobante
                        + "'" + cont.toString() + "'," //orden
                        + "'" + cursor.getString(cursor.getColumnIndex("COD_ARTICULO")) + "'," //cod_articulo
                        + cursor.getString(cursor.getColumnIndex("CANTIDAD")) + "," //cantidad
                        + "'" + cursor.getString(cursor.getColumnIndex("COD_UNIDAD_MEDIDA")) + "'," //cod_unidad_medida
                        + "'" + cursor.getString(cursor.getColumnIndex("COD_IVA")) + "'," //cod_iva
                        + cursor.getString(cursor.getColumnIndex("PORC_IVA"))) //porc_iva
                if (Pedidos.spListaPrecios.getDato("DECIMALES") == "0") {
                    v_into_det = (v_into_det
                            + ","
                            + cursor.getString(cursor.getColumnIndex("PRECIO_UNITARIO_C_IVA")
                    ).replace(",", "").replace(".","") + "") //precio_unitario_c_iva
                    v_into_det = if (descVarios != "") {
                        (v_into_det + "," + descVarios.replace(",",".").toFloat() //porc_desc_var
                        )
                    } else {
                        "$v_into_det,0"
                    }
                    v_into_det = if (descFin != "") {
                        (v_into_det + "," + descFin.replace(",",".").toFloat()) //porc_desc_fin
                    } else {
                        "$v_into_det,0"
                    }
                    v_into_det = (v_into_det
                            + ","
                            + cursor
                        .getString(
                            cursor
                                .getColumnIndex("PRECIO_LISTA")
                        ) //precio_lista
                        .replace(".", ""))
                } else {
                    v_into_det = (v_into_det
                            + ","
                            + cursor
                        .getString(
                            cursor
                                .getColumnIndex("PRECIO_UNITARIO_C_IVA")
                        ) //precio_unitario_c_iva
                        .replace(",", "."))
                    v_into_det = if (descVarios != "") {
                        (v_into_det + "," + descVarios.replace(",",".").toDouble() //porc_desc_var
                        )
                    } else {
                        "$v_into_det,0"
                    }
                    v_into_det = if (descFin != "") {
                        (v_into_det + "," + descFin.replace(",",".").toDouble() //porc_desc_fin
                        )
                    } else {
                        "$v_into_det,0"
                    }
                    v_into_det = (v_into_det
                            + ","
                            + cursor
                        .getString(
                            cursor
                                .getColumnIndex("PRECIO_LISTA")
                        ) //precio_lista
                        .replace(",", "."))
                }
                v_into_det = (v_into_det
                        + "," + cursor.getString(cursor.getColumnIndex("MULT")) + "," //mult
                        + "'PRO'," //tip_comprobante_ref
                        + "'" + ListaClientes.codVendedor + "'," //ser_comprobante_ref
                        + "'" + Pedidos.etNroPedidos.text.toString() + "'," //nro_comprobante_ref
                        + "'" + cont.toString() + "'," //nro_orden_ref
                        + "'" + cursor.getString(cursor.getColumnIndex("IND_SISTEMA")) + "'," //ind_sistema
                        + "'" + cursor.getString(cursor.getColumnIndex("IND_BLOQUEADO")) + "'," //ind_bloqueado
                        + "'" + cursor.getString(cursor.getColumnIndex("NRO_PROMOCION")) + "'," //nro_promocion
                        + "'" + cursor.getString(cursor.getColumnIndex("TIP_PROMOCION")) + "'," //tipo_promocion
                        + "'" + cursor.getString(cursor.getColumnIndex("NRO_AUTORIZACION")) + "'," //nro_autorizacion
                        + "'" + cursor.getString(cursor.getColumnIndex("MONTO_DESC_TC")) + "'") //monto_desc_a_favor
                cont += 1
                sql2 = v_into_det
                detalles = if (detalles == "") {
                    "$sql2;"
                } else {
                    "$detalles$sql2;"
                }
                cursor.moveToNext()
            }
            try {
                val t: Double = Pedidos.etTotalPedidos.text.toString().replace(".", "").replace(",", ".").toDouble()
                if (t < funcion.minVenta() && !cabeceraHash["COD_CONDICION"].equals("99") && Pedidos.spListaPrecios.getDato("DECIMALES") == "0") {
                    val nf = NumberFormat.getInstance()
                    nf.minimumFractionDigits = 0
                    nf.maximumFractionDigits = 0
                    Toast.makeText(context,"El monto minimo para una venta es: " + nf.format(funcion.minVenta()),Toast.LENGTH_LONG
                    ).show()
                    return
                }
            } catch (e: java.lang.Exception) {
            }
            Enviar().execute()
        } catch (e: java.lang.Exception) {
            error = e.message.toString()
            funcion.mensaje(context,"Error",e.message.toString())
        }
    }

    private class Enviar : AsyncTask<Void?, Void?, Void?>() {
        private var pbarDialog: ProgressDialog? = null
        override fun onPreExecute() {
            try {
                pbarDialog!!.dismiss()
            } catch (e: java.lang.Exception) {
            }
            pbarDialog = ProgressDialog.show(contexto,"Un momento...", "Enviando el pedido al servidor...", true)
        }

        override fun doInBackground(vararg params: Void?): Void? {
            resultado = MainActivity.conexionWS.enviarPedido(cabecera, detalles,Pedidos.maximo.toString(),ListaClientes.codVendedor)
//            resultado = "01*Enviado con exito"
            return null
        }

        @SuppressLint("Recycle", "SimpleDateFormat", "SetTextI18n")
        override fun onPostExecute(unused: Void?) {
            var ult = 0
            var cantidad: String
            var codigo = ""
            pbarDialog!!.dismiss()
            if (resultado.indexOf("03*") >= 0) {
                resultado = resultado.replace("03*", "")

                // Limpiar la existencia de todos los productos del detalle
                var values = ContentValues()
                values.put("existencia_actual", "")
                try {
                    MainActivity.bd!!.update("vt_pedidos_det", values,
                        " NUMERO = '" + Pedidos.maximo
                            .toString() + "' and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'", null)
                } catch (e: java.lang.Exception) {
                }

                // Muestra la existencia de los productos con corte
                while (resultado.indexOf(";") > 0) {
                    codigo = resultado.substring(ult, resultado.indexOf("/"))
                    ult = resultado.indexOf(";")
                    cantidad = resultado.substring(resultado.indexOf("/") + 1, ult)
                    resultado = resultado.replaceFirst(
                        "$codigo/$cantidad;",
                        ""
                    )
                    ult = 0
                    values = ContentValues()
                    values.put("existencia_actual", cantidad)
                    try {
                        MainActivity.bd!!.update("vt_pedidos_det", values,
                            (" NUMERO = '"
                                    + Pedidos.maximo
                                    ) + "'" + " and cod_articulo = '" + codigo + "'" + " and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'", null)
                        codigo = ""
                    } catch (e: java.lang.Exception) {
                        resultado = "2"
                        e.printStackTrace()
                    }
                }
                if (codigo != "") {
                    codigo = resultado.substring(ult, resultado.indexOf("/"))
                    cantidad = resultado.substring(
                        resultado.indexOf("/") + 1,
                        resultado.length
                    )
                    values = ContentValues()
                    values.put("existencia_actual", cantidad)
                    try {
                        MainActivity.bd!!.update("vt_pedidos_det",values,
                            ("NUMERO = '" + Pedidos.maximo ) +
                                    "'" + " and cod_articulo = '" + codigo + "'" +
                                    " and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'", null
                        )
                    } catch (e: java.lang.Exception) {
                        resultado = "2"
                        e.printStackTrace()
                    }
                }
                resultado =  "Corte de Stock!! favor verificar los productos sin stock  y vuelva a intentarlo."
                Pedidos.etAccionPedidos.setText("cargarDetallePedido")
            }
            if (resultado.indexOf("01*") >= 0) {
                val values = ContentValues()
                values.put("FECHA", Pedidos.etFechaPedido.text.toString())
                values.put("FECHA_INT", Pedidos.fechaInt)
                values.put("NRO_ORDEN_COMPRA",Pedidos.etNroOrdenCompra.text.toString())
                values.put("ESTADO", "E")
                values.put("COMENTARIO", Pedidos.etObservacionPedido.text.toString())
                values.put("porc_desc_fin", descFin.replace(",","."))
                values.put("porc_desc_var", descVarios.replace(",","."))
                values.put("NRO_AUTORIZACION_DESC", Pedidos.claveAutorizacion)
                values.put("tot_descuento", Pedidos.etTotalDescPedidos.text.toString().replace(".", ""))
                values.put("TOT_COMPROBANTE", Pedidos.etTotalPedidos.text.toString().replace(".", ""))
                val d2: String?
                val cal2: Calendar = Calendar.getInstance()
                val dfDate2 = SimpleDateFormat("dd/MM/yyyy")
                d2 = dfDate2.format(cal2.time)
                values.put("FEC_ALTA", d2)
                try {
                    MainActivity.bd!!.update(
                        "vt_pedidos_cab", values,
                        "NUMERO = '" + Pedidos.maximo
                            .toString() + "' and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'", null )
                } catch (e: java.lang.Exception) {
                    resultado = "Error al grabar! Intente otra vez!!"
                    e.printStackTrace()
                }
                val _sqlupdate: String
                var _por_descuento = 0.toFloat()
                if (descFin != "") {
                    _por_descuento = descFin.replace(",",".").toFloat()
                }
                if (descVarios != "") {
                    _por_descuento = (_por_descuento + descVarios.replace(",",".").toFloat())
                }
                _sqlupdate = if (_por_descuento == 0f) {
                    ("update vt_pedidos_det  set "
                            + " monto_total = (precio_unitario * cantidad) "
                            + " where NUMERO = '"
                            + Pedidos.maximo + "'"
                            + " and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'")
                } else {
                    ("update vt_pedidos_det  set "
                            + "monto_total = (precio_unitario*cantidad) -"
                            + "((precio_unitario * cantidad) * "
                            + _por_descuento / 100
                            + ") where NUMERO = '"
                            + Pedidos.maximo + "'"
                            + " and COD_VENDEDOR = '" + ListaClientes.codVendedor + "'")
                }
                try {
                    MainActivity.bd!!.rawQuery(_sqlupdate, null)
                } catch (e: java.lang.Exception) {
                    resultado = e.message.toString()
                    e.printStackTrace()
                }
                resultado = "Pedido enviado con exito!!"
            }
            if (resultado != "Pedido enviado con exito!!"){
                MainActivity.funcion.mensaje(contexto,"", resultado)
            } else {
                val dialogo = DialogoAutorizacion(contexto)
                dialogo.dialogoAccion("cerrarTodo",Pedidos.etAccionPedidos, resultado,"","OK")
            }
        }

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

    init {
        ubicacion.obtenerUbicacion(lm)
    }


}
package apolo.supervisor.com.utilidades

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class FuncionesDispositivo(var context: Context) {

    val funcion : FuncionesUtiles = FuncionesUtiles(context)

    fun modoAvion():Boolean{
        val valor: Int =
            Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0)
        return if (valor != 0){
            Toast.makeText(context,"Debe desactivar el modo avion", Toast.LENGTH_LONG).show()
            false
        } else {
            true
        }
    }

    @SuppressLint("SimpleDateFormat", "DefaultLocale")
    fun zonaHoraria(): Boolean {
        Date()
        val americaAsuncion: TimeZone = TimeZone.getTimeZone("America/Asuncion")
        val nowAmericaAsuncion: Calendar = Calendar.getInstance(americaAsuncion)
        val df: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val phoneDateTime: String = df.format(Calendar.getInstance().time)
        val zoneTime = (java.lang.String.format(
            "%02d",
            nowAmericaAsuncion
                .get(Calendar.HOUR_OF_DAY)
        ) + ":"
                + java.lang.String.format(
            "%02d",
            nowAmericaAsuncion
                .get(Calendar.MINUTE)
        ))
        val zoneDate = (java.lang.String.format(
            "%02d",
            nowAmericaAsuncion
                .get(Calendar.DAY_OF_MONTH)
        ) + "/"
                + java.lang.String.format(
            "%02d",
            nowAmericaAsuncion
                .get(Calendar.MONTH) + 1
        )
                + "/"
                + java.lang.String.format("%02d", nowAmericaAsuncion.get(Calendar.YEAR)))
        val zoneDateTime = "$zoneDate $zoneTime"
        return if (phoneDateTime != zoneDateTime) {
            Toast.makeText(
                context,
                "La zona horaria no coincide con America/Asuncion",
                Toast.LENGTH_SHORT
            ).show()
            false
            //true
        } else {
            true
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun fechaCorrecta(): Boolean {
//        if (true) { return true }
        val sql = "SELECT distinct IFNULL(fecha,'01/01/2020') AS ULTIMA_SINCRO FROM svm_vendedor_pedido order by id desc"
        val cursor:Cursor = funcion.consultar(sql)
        val fecUltimaSincro: String
        if (cursor.count > 0) {
            fecUltimaSincro = funcion.dato(cursor,"ULTIMA_SINCRO")
        } else {
            funcion.toast(context,"No posee permisos.")
            return false
        }

        val dfDate = SimpleDateFormat("dd/MM/yyyy")
        var d: Date? = null
        var d1: Date? = null
        val cal = Calendar.getInstance()
        try {
            d = dfDate.parse(fecUltimaSincro)
            d1 = dfDate.parse(dfDate.format(cal.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val diffInDays = ((d1!!.time - d!!.time) / (1000 * 60 * 60 * 24)).toInt()
        return if (diffInDays != 0) {
            Toast.makeText( context,"La fecha actual del sistema no coincide con la fecha de sincronizacion", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getFechaActual():String{
        val dfDate = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        return dfDate.format(cal.time) + ""
    }

    fun tarjetaSim(telMgr:TelephonyManager): Boolean {
        var state = true
        when (telMgr.simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                Toast.makeText(
                    context,
                    "Insertar SIM para realizar la operacion",
                    Toast.LENGTH_SHORT
                ).show()
                state = false
            }
            TelephonyManager.SIM_STATE_UNKNOWN -> {
                Toast.makeText(
                    context,
                    "Insertar SIM para realizar la operacion",
                    Toast.LENGTH_SHORT
                ).show()
                state = false
            }
        }
        return state
    }

    fun horaAutomatica(): Boolean {
//        if (true) { return true }
        if (Settings.System.getInt(
                context.contentResolver,
                Settings.Global.AUTO_TIME,
                0
            ) != 1
            || Settings.System.getInt(
                context.contentResolver,
                Settings.Global.AUTO_TIME_ZONE,
                0
            ) != 1
        ) {
            Toast.makeText(
                context,
                "Debe configurar su hora de manera automatica",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    fun permisoParaEscritura(activity: Activity) {
        val permissionCheck = ContextCompat.checkSelfPermission( context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            funcion.toast(context,"No se tiene permiso para leer.")
            ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        } else {
//            funcion.toast(context,"Se tiene permiso para leer!")
        }
    }

    @SuppressLint("HardwareIds")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun obtieneIDSIM(telMgr:TelephonyManager){
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
//        var serial : String = telMgr.simSerialNumber
        "Numero de telefono: ${telMgr.line1Number}\nEstado:"
        telMgr.simState
        var variable = "\nOperadora: " + telMgr.simOperatorName.toString()
        variable += "\nOperador de red: " + telMgr.networkOperator.toString()
        variable += "\nEstado de tarjeta sim: " + telMgr.getSimState(0).toString()
        variable += "\nTipo de red: " + telMgr.dataNetworkType
        variable += "\nOperador de red: " + telMgr.networkOperator
//        variable += "\nRed especifica: " + telMgr.networkSpecifier
        variable += "\nServicio de llamada: " + telMgr.voiceNetworkType
//        funcion.mensaje(context,"Numero de serie y telefono","$serial\n$numero\n$estado")

//        val suscripcion = SubscriptionManager.from(context).activeSubscriptionInfoList
//        for (subscriptionInfo in suscripcion){
//            variable += "\nNumero: ${subscriptionInfo.number}"
//            variable += "\nOperadora: ${subscriptionInfo.displayName}"
//            variable += "\nICC del sim: ${subscriptionInfo.iccId}"
//            variable += "\nTipo de suscripcion: ${subscriptionInfo.subscriptionId}"
//            variable += "\nPais: ${subscriptionInfo.countryIso}"
//        }
//        funcion.mensaje(context,"Numero de serie y telefono","$numero\n$estado$variable")
        try {
            Runtime.getRuntime().exec("su")
//            funcion.mensaje(context,"llamada en proceso","corte ahora")
        } catch (e : Exception) {
//            funcion.mensaje(context,"",e.message.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun validaEstadoSim(telMgr:TelephonyManager):Boolean{
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return true
        }
        telMgr.voiceMailNumber
//        try {
////            funcion.mensaje(context,"Numero",telMgr.voiceMailNumber)
//        } catch (e : Exception){
//            funcion.mensaje(context,"Error",e.message.toString())
//        }
//        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////            funcion.mensaje(context,"Validacion de telefono correcta","54781254\nComuniquese con el soporte.")
//            funcion.mensaje(context,"Error!","Debe otorgar a la aplicacion los permisos para acceder al telefono.")
//            return false
//        }
//        if (telMgr.dataNetworkType==0){
////            funcion.mensaje(context,"Validacion de tarjeta correcta","54781254\nComuniquese con el soporte.")
//            funcion.mensaje(context,"Error!","Tarjeta sim fuera de servicio.")
//            return false
//        }
//        val suscripcion = SubscriptionManager.from(context).activeSubscriptionInfoList
//        for (subscriptionInfo in suscripcion){
//            if (subscriptionInfo.iccId.toString().substring(0,4) != "8959"){
//                funcion.mensaje(context,"Tarjeta SIM extranjera", "Para continuar utilice una tarjeta sim de una operadora nacional.")
////                funcion.mensaje(context,"Validacion de tarjeta correcta", "Envie esta imagen al departamento de informatica.")
//                return false
//            }
//            if (subscriptionInfo.countryIso.toString().toUpperCase() != "PY"){
////                funcion.mensaje(context,"Tarjeta SIM extranjera", "Para continuar utilice una tarjeta sim de una operadora nacional.")
////                return false
//            }
//        }
        return true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun validaEstadoSim2(telMgr:TelephonyManager):Boolean{
        try {

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //funcion.mensaje(context,"Error!","Debe otorgar a la aplicacion los permisos para acceder al telefono.")
                return false
            }
            if (telMgr.dataNetworkType==0){
                //funcion.mensaje(context,"Error!","Tarjeta sim fuera de servicio.")
                return false
            }
            val suscripcion = SubscriptionManager.from(context).activeSubscriptionInfoList
            for (subscriptionInfo in suscripcion){
                if (subscriptionInfo.iccId.toString().substring(0,4) != "8959"){
                    //funcion.mensaje(context,"Tarjeta SIM extranjera", "Para continuar utilice una tarjeta sim de una operadora nacional.")
                    return false
                }
                if (subscriptionInfo.countryIso.toString().toUpperCase() != "PY"){
                    //funcion.mensaje(context,"Tarjeta SIM extranjera", "Para continuar utilice una tarjeta sim de una operadora nacional.")
                    return false
                }
            }
        } catch (e : Exception){
            return false
        }
        return true
    }



    //obtener aplicaciones con ubicacion simulada
    fun getAppsForMockLocation(context: Context): String {
        val appList: MutableList<String> = mutableListOf()

        val packageManager: PackageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (app in installedApps) {
            try {
                val appInfo = packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
                val appPermissions = packageManager.getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS)
                val permissions = appPermissions.requestedPermissions



                if (permissions != null) {
                    for (permission in permissions) {
                        if (permission == "android.permission.ACCESS_MOCK_LOCATION") {
                            //appList.add(appInfo.loadLabel(packageManager).toString())
                            if (appInfo.packageName.toString().indexOf("apolo") == -1) {
                                appList.add(appInfo.packageName.toString())
                            }
                            break
                        }
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        var paquetes = ""
        appList.forEach {

            if (paquetes == "") {
                paquetes = "(Apps Instaladas: "
            }
            paquetes += "$it "

        }

        if (paquetes !== "") {
            paquetes += ")"
        }

        return paquetes
    }



    fun verificaRoot():Boolean{
        return try {
            Runtime.getRuntime().exec("su")
            funcion.mensaje(context,"Atención","El teléfono está rooteado.")
            false
        } catch (e : Exception) {
            true
        }
    }

}
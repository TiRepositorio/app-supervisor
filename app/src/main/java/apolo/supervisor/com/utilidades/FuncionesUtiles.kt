package apolo.supervisor.com.utilidades

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.os.StrictMode
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.ventas.ListaClientes
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import org.threeten.bp.Instant
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId


class FuncionesUtiles {

    //CONSTRUCTORES
    constructor()
    constructor(imgTitulo: ImageView?, tvTitulo: TextView?) {
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
    }

    constructor(context: Context,imgTitulo: ImageView?, tvTitulo: TextView?) {
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
    }

    constructor(context: Context){
        this.context = context
    }

    constructor(context: Context,llTitulo:LinearLayout,llBotonVendedor: LinearLayout,llBuscar: LinearLayout,spBuscar: Spinner,etBuscar: EditText,btBuscar:Button){
        this.context = context
        llTitulo.visibility = View.GONE
        llBotonVendedor.visibility = View.GONE
        llBuscar.visibility = View.VISIBLE
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        etBuscar.inputType = 2
        spBuscar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }

    constructor(
        context: Context,
        imgTitulo: ImageView?,
        tvTitulo: TextView?,
        imgAnterior: ImageView?,
        imgSiguiente: ImageView?,
        tvVendedor: TextView?,
        contMenu: DrawerLayout,
        barraMenu: NavigationView?,
        llBotonVendedor: LinearLayout
    ) {
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.imgAnterior = imgAnterior
        this.imgSiguiente = imgSiguiente
        this.tvVendedor = tvVendedor
        this.contMenu = contMenu
        this.barraMenu = barraMenu
        tvVendedor!!.setOnClickListener{
            mostrarMenu()
        }
        llBotonVendedor.visibility = View.VISIBLE
    }

    constructor(
        context: Context,
        imgTitulo: ImageView?,
        tvTitulo: TextView?,
        imgAnterior: ImageView?,
        imgSiguiente: ImageView?,
        tvVendedor: TextView?,
        contMenu: DrawerLayout,
        barraMenu: NavigationView?,
        llBuscar: LinearLayout?,
        spBuscar: Spinner?,
        etBuscar: EditText?,
        btBuscar: Button?
    ) {
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.imgAnterior = imgAnterior
        this.imgSiguiente = imgSiguiente
        this.tvVendedor = tvVendedor
        this.contMenu = contMenu
        this.barraMenu = barraMenu
        this.llBuscar = llBuscar
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        tvVendedor!!.setOnClickListener{
            mostrarMenu()
        }
        llBuscar!!.visibility = View.VISIBLE
        etBuscar!!.inputType = 2
        spBuscar!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }

    constructor(
        context: Context,
        imgTitulo: ImageView?,
        tvTitulo: TextView?,
        imgAnterior: ImageView?,
        imgSiguiente: ImageView?,
        tvVendedor: TextView?,
        contMenu: DrawerLayout,
        barraMenu: NavigationView?,
        llBuscar: LinearLayout?,
        spBuscar: Spinner?,
        etBuscar: EditText?,
        btBuscar: Button?,
        llBotonVendedor: LinearLayout,
        visibilidad: Int
    ) {
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.imgAnterior = imgAnterior
        this.imgSiguiente = imgSiguiente
        this.tvVendedor = tvVendedor
        this.contMenu = contMenu
        this.barraMenu = barraMenu
        this.llBuscar = llBuscar
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        tvVendedor!!.setOnClickListener{
            mostrarMenu()
        }
        llBotonVendedor.visibility = visibilidad
        llBuscar!!.visibility = View.VISIBLE
        etBuscar!!.inputType = 2
        spBuscar!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }


    constructor(
        context: Context,
        imgTitulo: ImageView?,
        tvTitulo: TextView?,
        imgAnterior: ImageView?,
        imgSiguiente: ImageView?,
        tvVendedor: TextView?,
        contMenu: DrawerLayout,
        barraMenu: NavigationView?,
        llBuscar: LinearLayout?,
        spBuscar: Spinner?,
        etBuscar: EditText?,
        btBuscar: Button?,
        llBotonVendedor:LinearLayout
    ) {
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.imgAnterior = imgAnterior
        this.imgSiguiente = imgSiguiente
        this.tvVendedor = tvVendedor
        this.contMenu = contMenu
        this.barraMenu = barraMenu
        this.llBuscar = llBuscar
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        tvVendedor!!.setOnClickListener{
            mostrarMenu()
        }
        llBotonVendedor.visibility = View.GONE
        barraMenu!!.layoutParams.width = 0
        llBuscar!!.visibility = View.VISIBLE
        etBuscar!!.inputType = 2
        spBuscar!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }

    //Titulo y buscar
    constructor(context: Context,imgTitulo:ImageView,tvTitulo:TextView,llBuscar:LinearLayout,spBuscar:Spinner,etBuscar:EditText,btBuscar:Button){
        this.context = context
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.llBuscar = llBuscar
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        llBuscar.visibility = View.VISIBLE
        etBuscar.inputType = 2
        spBuscar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }

    //titulo y buscador separados
    constructor(context: Context,imgTitulo: ImageView?,tvTitulo: TextView?,spBuscar: Spinner,etBuscar: EditText,btBuscar:Button){
        this.context = context
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        this.imgTitulo = imgTitulo
        this.tvTitulo = tvTitulo
        this.spBuscar = spBuscar
        this.etBuscar = etBuscar
        this.btBuscar = btBuscar
        etBuscar.inputType = 2
        spBuscar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    etBuscar.inputType = 3 //NUMBER
                } else {
                    etBuscar.inputType = 1 //TEXTO
                }
            }
        }
    }


    //Variables
    companion object{
        lateinit var cursor: Cursor
        val usuario : HashMap<String, String> = HashMap()
        var posicionCabecera: Int = 0
        var posicionDetalle:  Int = 0
        var posicionGenerico: Int = 0
        var listaCabecera: ArrayList<HashMap<String, String>> = ArrayList()
        var listaDetalle: ArrayList<HashMap<String, String>> = ArrayList()
        var subListaDetalle: ArrayList<ArrayList<HashMap<String, String>>> = ArrayList()
        val formatoNumeroEntero : DecimalFormat = DecimalFormat("###,###,###.##")
        val formatoNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")
        var formatoGenerico: NumberFormat = NumberFormat.getInstance()
        var ultimaVenta : Int = -1
    }

    //adaptador
    var llBuscar: LinearLayout? = null
    var spBuscar: Spinner?  = null
    var etBuscar: EditText? = null
    var btBuscar: Button?   = null
    var imgTitulo: ImageView? = null
    var tvTitulo: TextView? = null
    private var imgAnterior: ImageView? = null
    private var imgSiguiente: ImageView? = null
    var tvVendedor : TextView? = null
    var contMenu: DrawerLayout? = null
    var barraMenu: NavigationView? = null
    var context : Context? = null
    private var spinnerAdapter : ArrayAdapter<String>? = null
    private var valoresSpinner: ArrayList<HashMap<String,String>> = ArrayList()
    private var parametros : Array<String> = arrayOf()
    lateinit var vistas : IntArray
    lateinit var valores: Array<String>
    lateinit var subVistas : IntArray
    lateinit var subValores: Array<String>
    lateinit var listaVendedores: ArrayList<HashMap<String,String>>
    var posVend : Int = 0

    //FUNCIONES DE BD
    fun dato(cursor: Cursor, index:String): String {
        return try {
            cursor.getString(cursor.getColumnIndex(index))
        } catch (e:Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun datoEntero(cursor: Cursor, index:String): Int{
        return try {
            cursor.getInt(cursor.getColumnIndex(index))
        } catch (e:Exception){
            0
        }
    }
    private fun datoDecimal(cursor: Cursor, index:String): Double{
        return try {
            cursor.getDouble(cursor.getColumnIndex(index))
        } catch (e:Exception){
            0.0
        }
    }
    fun datoPorcentaje(cursor: Cursor,totalS:String, valorS:String):Double{

        val total: Double =
            cursor.getString(cursor.getColumnIndex(totalS)).replace(".","").replace(",",".").replace("%","").toDouble()
        val valor: Double =
            cursor.getString(cursor.getColumnIndex(valorS)).replace(".","").replace(",",".").replace("%","").toDouble()

        return (valor*100)/total
    }
    @SuppressLint("Recycle")
    fun consultar(sql: String): Cursor{
        return try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
            cursor
        } catch (e: Exception){
            e.printStackTrace()
            e.message
            cursor
        }
    }
    fun cargarLista(lista:ArrayList<HashMap<String,String>>,cursor:Cursor){
        for (i in 0 until cursor.count){
            val datos : HashMap<String,String> = HashMap()
            for(j in 0 until cursor.columnCount){
                datos[cursor.getColumnName(j)] = dato(cursor,cursor.getColumnName(j))
            }
            lista.add(datos)
            cursor.moveToNext()
        }
    }
    fun ejecutar(sql:String,context: Context): Boolean {
        return try {
            MainActivity.bd!!.execSQL(sql)
            true
        } catch (e : Exception) {
    //            var dialogo:AlertDialog.Builder = AlertDialog.Builder(context)
    //            dialogo.setMessage(e.message)
    //            dialogo.setTitle("ERROR")
    //            dialogo.show()
            mensaje(context,"Error",e.message.toString())
            Toast.makeText(context, "Error al ejecutar $sql",Toast.LENGTH_LONG).show()
            false
        }
    }
    fun ejecutarB(sql: String): Boolean {
        return try {
            MainActivity.bd!!.execSQL(sql)
            true
        } catch (e : Exception) {
            false
        }
    }
    fun insertar(tabla:String, valores:ContentValues){
        try {
            MainActivity.bd!!.insert(tabla,null,valores)
//            mensaje("Correcto","Insertado correctamente")
        } catch (e:Exception){
//            mensaje("Error",e.message.toString())
        }
    }
    fun insertar(tabla:String, campos:String, valores:ContentValues){
        try {
            MainActivity.bd!!.insert(tabla,campos,valores)
//            mensaje("Correcto","Insertado correctamente")
        } catch (e:Exception){
            mensaje("Error",e.message.toString())
        }
    }
    fun actualizar(tabla:String, valores:ContentValues,where:String){
        try {
            MainActivity.bd!!.update(tabla,valores,where,null)
//            mensaje("Correcto","Actualizado correctamente")
        } catch (e:Exception){
            mensaje("Error",e.message.toString())
        }
    }
    fun listaVendedores(codVendedor:String, descVendedor:String,tabla: String){
        val sql = ("SELECT DISTINCT " + codVendedor + "," + descVendedor + " "
                + " FROM " + tabla
                + " ORDER BY CAST(" + codVendedor + " AS NUMBER)")
        cargarVendedores(sql,codVendedor,descVendedor)
    }
    @SuppressLint("Recycle", "SetTextI18n")
    fun listaVendedores(codVendedor1:String, descVendedor1:String, tabla1: String, codVendedor2:String, descVendedor2:String, tabla2: String){
        val sql = "select distinct " +   codVendedor1 + "," + descVendedor1 + " from (" +
                "select distinct c." +   codVendedor1 + ",c." + descVendedor1 + " from (" +
                "  from " + tabla1 + " c " +
                " union all " +
                "select distinct c." + codVendedor2 + "," + "c." + descVendedor2 +
                "  from " + tabla2 + " c " +
                ")" +
                " order by cast(" + codVendedor1 + " as integer)"
        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
            val codigo = cursor.getString(cursor.getColumnIndex(codVendedor1))
            val descripcion = cursor.getString(cursor.getColumnIndex(descVendedor1))
            tvVendedor!!.text = "$codigo-$descripcion"

        } catch (e : Exception){
            e.message
            return
        }

        barraMenu!!.getHeaderView(0).findViewById<TextView>(R.id.tvNombreSup).text = "Sup.: " + usuario["NOMBRE"]
        barraMenu!!.getHeaderView(0).findViewById<TextView>(R.id.tvCodigoSup).text = "Cod.: " + usuario["PASS"]
        barraMenu!!.menu.clear()

        listaVendedores = ArrayList()
        for (i in 0 until cursor.count){
            val codigo = dato(cursor,codVendedor2)
            val descripcion = dato(cursor,descVendedor2)
            barraMenu!!.menu.add("$codigo-$descripcion").setIcon(R.drawable.ic_usuario)
            addVendedor(codigo,descripcion)
            cursor.moveToNext()
        }
    }
    @SuppressLint("Recycle", "SetTextI18n")
    fun cargarVendedores(sql:String, codVendedor:String, descVendedor:String){
        try {
            cursor = MainActivity.bd!!.rawQuery(sql, null)
            cursor.moveToFirst()
            val codigo = cursor.getString(cursor.getColumnIndex(codVendedor))
            val descripcion = cursor.getString(cursor.getColumnIndex(descVendedor))
            tvVendedor!!.text = "$codigo-$descripcion"

        } catch (e : Exception){
            e.message
            return
        }

        barraMenu!!.getHeaderView(0).findViewById<TextView>(R.id.tvNombreSup).text = "Sup.: " + usuario["NOMBRE"]
        barraMenu!!.getHeaderView(0).findViewById<TextView>(R.id.tvCodigoSup).text = "Cod.: " + usuario["PASS"]
        barraMenu!!.menu.clear()

        listaVendedores = ArrayList()
        for (i in 0 until cursor.count){
            val codigo = dato(cursor,codVendedor)
            val descripcion = dato(cursor,descVendedor)
            barraMenu!!.menu.add("$codigo-$descripcion").setIcon(R.drawable.ic_usuario)
            addVendedor(codigo,descripcion)
            cursor.moveToNext()
        }
    }
    private fun addVendedor(codigo: String, descripcion:String){
        val dato : HashMap<String,String> = HashMap()
        dato["codigo"] = codigo
        dato["descripcion"] = descripcion
        listaVendedores.add(dato)
    }
    fun addItemSpinner(context: Context,parametro: String, campo:String){
        parametros = parametro.split("-").toTypedArray()
        for (i in parametros.indices){
            val valor : HashMap<String, String> = HashMap()
            valor[parametros[i]] = campo.split("-")[i]
            valoresSpinner.add(valor)
        }
        spinnerAdapter = ArrayAdapter(context,R.layout.spinner_adapter,parametros)
        spBuscar!!.adapter = spinnerAdapter
    }
    @SuppressLint("SetTextI18n")
    fun buscar (tabla: String) : Cursor{
        val sql: String
        if (spBuscar!!.selectedItemPosition != 0){
            sql = "SELECT * FROM " + tabla +
                    " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem] +
                    "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
            tvVendedor!!.text = "Todos"
            return consultar(sql)
        }
        sql = "SELECT * FROM " + tabla +
              " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem] +
              "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
        tvVendedor!!.text = "Todos"
        return consultar(sql)
    }
    @SuppressLint("SetTextI18n")
    fun buscar (tabla: String, campos:String?, groupBy:String?, orderBy:String?) : Cursor{
        var sql: String
        if (spBuscar!!.selectedItemPosition != 0){
            sql = "SELECT " + campos + " FROM " + tabla +
                    " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",")[0].toUpperCase(Locale.ROOT) +
                    "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
            if (valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",").size>1){
                for(i in 1 until valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",").size){
                    sql = "$sql OR " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",")[i].toUpperCase(
                        Locale.ROOT) +
                            " LIKE '%" + etBuscar!!.text.toString() + "%' "
                }
            }
        } else {
            sql = "SELECT " + campos + " FROM " + tabla +
                    " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem] +
                    "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
        }
        if (!groupBy.equals("")){ sql = "$sql GROUP BY $groupBy" }
        if (!orderBy.equals("")){ sql = "$sql ORDER BY $orderBy" }
        tvVendedor!!.text = "Todos"
        return consultar(sql)
    }
    fun buscar (tabla: String, campos:String?,groupBy:String?,orderBy:String?,where:String) : Cursor{
        var sql: String
        if (spBuscar!!.selectedItemPosition != 0){
            sql = "SELECT " + campos + " FROM " + tabla +
                    " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!
                            .split(",")[0].toUpperCase(Locale.ROOT) +
                    "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
            if (valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",").size>1){
                for(i in 1 until valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",").size){
                    sql = "$sql OR " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem]!!.split(",")[i].toUpperCase(Locale.ROOT) +
                            " LIKE '%" + etBuscar!!.text.toString() + "%' "
                }
            }
        } else {
            sql = "SELECT " + campos + " FROM " + tabla +
                    " WHERE " + valoresSpinner[spBuscar!!.selectedItemPosition][spBuscar!!.selectedItem].toString().split(",")[0] +
                    "  LIKE '%"  + etBuscar!!.text.toString() + "%' "
        }
        sql += where
        if (!groupBy.equals("")){
            sql = "$sql GROUP BY $groupBy"
        }
        if (!orderBy.equals("")){
            sql = "$sql ORDER BY $orderBy"
        }
//        if (tvVendedor!! != null){
//            tvVendedor!!.setText("Todos")
//        }
        return consultar(sql)
    }
    fun cargarDatos(cursor: Cursor):ArrayList<HashMap<String,String>>{
        val lista = ArrayList<HashMap<String,String>>()
        for (i in 0 until cursor.count){
            val dato : HashMap<String,String> = HashMap()
            for (j in 0 until cursor.columnCount){
                try {
                    dato[cursor.getColumnName(j)] = dato(cursor,cursor.getColumnName(j))
                } catch (e:Exception){}
            }
            lista.add(dato)
            cursor.moveToNext()
        }
        return lista
    }
    fun maxDescuento():Double{
        val sql : String = ("SELECT NUMERO MAXIMO, IND_PALM, ULTIMA_SINCRO, RANGO, TIPO_SUPERVISOR, MIN_FOTO, "
                         +  "       MAX_FOTO, IND_FOTO, FEC_CARGA_RUTEO, MAX_DESC, "
                         +  "       VERSION, MAX_DESC_VAR, PER_VENDER, INT_MARCACION  "
                         +  "  FROM svm_vendedor_pedido_venta WHERE COD_SUPERVISOR = '${usuario["PASS"]}'")
        return datoDecimal(consultar(sql),"MAX_DESC")
    }
    fun minVenta():Double{
        val sql : String = ("SELECT * FROM svm_vendedor_pedido WHERE COD_VENDEDOR = '${ListaClientes.codVendedor}'")
        return datoDecimal(consultar(sql),"MIN_VENTA")
    }
    fun ultPedidoVenta():Int{
        val sql : String = ("SELECT NUMERO MAXIMO FROM svm_vendedor_pedido  WHERE COD_VENDEDOR = '${ListaClientes.codVendedor}'")
        return datoEntero(consultar(sql),"MAXIMO")
    }

    //FUNCIONES DE FORMATO DE NUMEROS
    fun entero(entero: String):String{
        if (entero.trim() == ""){
            return "0"
        }
        return formatoNumeroEntero.format(entero.replace(".","").replace("null","0").trim().toLong())
    }
    fun enteroCliente(entero: String):String{
        if (entero.trim() == ""){
            return "0"
        }
        if (entero.trim().indexOf(".")>-1){
            return "0"
        }
        return formatoNumeroEntero.format(entero.replace(".","").replace("null","0").trim().toLong())
    }
    fun entero(entero: Int):String{
        return formatoNumeroEntero.format(entero)
    }
    fun entero(entero: Long):String{
        return formatoNumeroEntero.format(entero)
    }
    fun aEntero(numero:String):Int{
        return numero.replace(".","").toInt()
    }
    fun decimal(decimal: String):String{
        if (decimal.trim() == "" || decimal.trim() == "null"){
            return "0.0"
        }
        if (decimal.indexOf(",")>-1){
            return formatoNumeroDecimal.format(decimal.replace(".","")
                .replace(",",".")
                .replace("%","").trim().toDouble()).toString()
        }
        return formatoNumeroDecimal.format(decimal.replace(",",".")
            .replace("%","")
            .replace("null","")
            .trim().toDouble()).toString()
    }
    fun decimal(decimal: Double):String{
        return formatoNumeroDecimal.format(decimal)
    }
    fun decimalPunto(decimal: String):String{
        return decimal(decimal).replace(".","").replace(",",".")
    }
    fun numero(decimales: String, numero:String):String{
        formatoGenerico.minimumFractionDigits = decimales.toInt()
        formatoGenerico.maximumFractionDigits = decimales.toInt()
        return formatoGenerico.format(numero.replace(",",".").toDouble())
    }
    fun porcentaje(decimal: String):String{
        return decimal(decimal) + "%"
    }
    fun porcentaje(decimal: Double):String{
        return formatoNumeroDecimal.format(decimal) + "%"
    }
    fun inicializaContadores(){
        posicionCabecera = 0
        posicionDetalle = 0
        posicionGenerico = 0
    }
    @SuppressLint("SimpleDateFormat")
    fun fechaHora(fecha:String): Date {
        val hourFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return hourFormat.parse(fecha)!!
    }
    @SuppressLint("SimpleDateFormat")
    fun fecha(fecha:String): Date {
        val hourFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return hourFormat.parse(fecha)!!
    }
    @SuppressLint("SimpleDateFormat")
    fun fechaF(fecha:String): Date {
        val hourFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return hourFormat.parse(hourFormat.format(fecha))!!
    }
    @SuppressLint("SimpleDateFormat")
    fun fecha1(fecha:String): String {
        val hourFormat: DateFormat = SimpleDateFormat("yyyy-dd-MM")
        return hourFormat.format(fecha)
    }
    fun tiempoTranscurrido(fecha1:String,fecha2:String):Int{
        var diferencia : Long = (fechaHora(fecha2).time/60000) - (fechaHora(fecha1).time / 60000)
        if (diferencia<0){
            diferencia *= (-1)
        }
        return diferencia.toInt()
    }
    fun getAnho(): Int {
        val calendar : Calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
    fun getMes(): Int {
        val calendar : Calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH)
    }
    fun getDia(): Int {
        val calendar : Calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    //FECHAS
    @SuppressLint("SimpleDateFormat")
    fun getDiaDeLaSemana(strFecha: String): String {
        val formatoDelTexto = SimpleDateFormat("dd/MM/yyyy")
        val fecha: Date
        try {
            fecha = formatoDelTexto.parse(strFecha)!!
            val cal: Calendar = GregorianCalendar.getInstance()
            cal.time = fecha
            return getDia(cal.get(Calendar.DAY_OF_WEEK))
        } catch (ex: java.lang.Exception) {
        }
        return "Lunes"
    }
    fun getDia(dia:Int):String{
        return when(dia){
            1 -> "Domingo"
            2 -> "Lunes"
            3 -> "Martes"
            4 -> "Miercoles"
            5 -> "Jueves"
            6 -> "Viernes"
            7 -> "Sabado"
            else -> "No corresponde"
        }
    }
    fun getDiaDeLaSemana(): String {
        val cal = GregorianCalendar.getInstance()
        return getDia(cal[Calendar.DAY_OF_WEEK])
    }
    fun getMes(mes:String):String{
        return when (mes){
            "01" -> "Enero"
            "02" -> "Febrero"
            "03" -> "Marzo"
            "04" -> "Abril"
            "05" -> "Mayo"
            "06" -> "Junio"
            "07" -> "Julio"
            "08" -> "Agosto"
            "09" -> "Setiembre"
            "10" -> "Octubre"
            "11" -> "Noviembre"
            "12" -> "Diciembre"
            else -> "Valor no corresponde"
        }
    }
    fun getMes(mes:Int):String{
        return when (mes){
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Setiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "Valor no corresponde"
        }
    }
    fun getHoraActual(): String {
        val calendario = Calendar.getInstance()
        val hora: Int = calendario[Calendar.HOUR_OF_DAY]
        val minutos: Int = calendario[Calendar.MINUTE]
        val segundos: Int = calendario[Calendar.SECOND]

        // GUARDAR LA HORA
        var horaTem = ""
        horaTem += if (hora < 10) {
            "0$hora:"
        } else {
            "$hora:"
        }
        horaTem += if (minutos < 10) {
            "0$minutos:"
        } else {
            "$minutos:"
        }
        if (segundos < 10) {
            horaTem += "0$segundos"
        } else {
            horaTem += segundos
        }
        return horaTem
    }
    @SuppressLint("SimpleDateFormat")
    fun getFechaActual():String{
        val dfDate = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        return dfDate.format(cal.time) + ""
    }
    fun getFechaHoraActual():String{
        return getFechaActual() + " " + getHoraActual()
    }
    @SuppressLint("SimpleDateFormat")
    fun getFechaActual1():String{
        val dfDate: DateFormat = SimpleDateFormat("yyyy-dd-MM")
        val cal = Calendar.getInstance()
        return dfDate.format(cal.time) + ""
    }
    fun fechaRuteo(context:Context):Boolean{
        var d: Date? = null
        var d1: Date? = null
        Calendar.getInstance()
        val sql : String = ("SELECT numero MAXIMO, ind_palm, ultima_sincro, RANGO, TIPO_SUPERVISOR, MIN_FOTO, MAX_FOTO, IND_FOTO, FEC_CARGA_RUTEO, MAX_DESC "
                + ",version, MAX_DESC_VAR, PER_VENDER, INT_MARCACION  from svm_vendedor_pedido_venta  where COD_SUPERVISOR ="
                + "'" + usuario["PASS"] + "'")
        val cursor : Cursor = consultar(sql)
        var fecha = ""
        if (cursor.count>0){
            fecha = try {
                dato(cursor,"FEC_CARGA_RUTEO").trim()
            } catch (e:java.lang.Exception){
                ""
            }
        }
        if (fecha == ""){
            mensaje(context, "Atención!","No se ha habilitado este dia para enviar ruteo.")
            return false
        }
        try {
            d = fecha(fecha)
            d1 = fecha(getFechaActual())
        } catch (e:java.text.ParseException) {
            e.printStackTrace()
        }

        val diffInDays : Int = ((d1!!.time.toInt() - d!!.time.toInt()) / (1000 * 60 * 60 * 24))

        if (diffInDays != 0) {
            mensaje(context, "Atención!","No se ha habilitado este dia para enviar ruteo.")
            return false
        }
        return true
    }


    //MENU
    fun mostrarMenu(){
        if (contMenu!!.isDrawerOpen(GravityCompat.START)) {
            contMenu!!.closeDrawer(GravityCompat.START)
        } else {
            contMenu!!.openDrawer(GravityCompat.START)
        }
    }
    fun cargarTitulo(icon:Int,titulo: String){
        imgTitulo!!.setImageResource(icon)
        tvTitulo!!.text = titulo
    }
    @SuppressLint("SetTextI18n")
    fun actualizaVendedor(context: Context){
        if (posVend == listaVendedores.size){
            Toast.makeText(context,"Es el ultimo registro",Toast.LENGTH_SHORT).show()
            posVend--
        } else {
            if (posVend == -1){
                Toast.makeText(context,"Es el primer registro",Toast.LENGTH_SHORT).show()
                posVend++
            } else {
                tvVendedor!!.text = listaVendedores[posVend]["codigo"] + "-" +
                        listaVendedores[posVend]["descripcion"]
                inicializaContadores()
            }
        }
    }
    fun mensaje(context: Context,titulo:String,mensaje:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        dialogo.setTitle(titulo)
        dialogo.setMessage(mensaje)
        dialogo.setPositiveButton("OK",null)
        dialogo.show()
    }
    fun mensaje(titulo:String,mensaje:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        dialogo.setTitle(titulo)
        dialogo.setMessage(mensaje)
        dialogo.show()
    }
    fun getIntervaloMarcacion(): Int {
        val sql : String =
            ("SELECT numero MAXIMO, ind_palm, ultima_sincro, RANGO, TIPO_SUPERVISOR, MIN_FOTO, MAX_FOTO, IND_FOTO, FEC_CARGA_RUTEO, MAX_DESC "
                    + ",version, MAX_DESC_VAR, PER_VENDER, INT_MARCACION  from svm_vendedor_pedido  where COD_SUPERVISOR ="
                    + "'" + usuario["PASS"] + "'")
        return if (consultar(sql).count>0){
            datoEntero(consultar(sql),"INT_MARCACION")
        } else {
            0
        }
    }

    //DIALOGOS
    fun dialogoEntrada(et:EditText,context: Context){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        val entrada = EditText(context)
        dialogo.setTitle(et.hint)
        entrada.inputType = InputType.TYPE_CLASS_TEXT
        dialogo.setView(entrada)
        entrada.text = et.text
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            et.text = entrada.text
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }
    fun dialogoEntrada(et:EditText,context: Context,etAccion:EditText,accion:String){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        val entrada = EditText(context)
        dialogo.setTitle(et.hint)
        entrada.inputType = InputType.TYPE_CLASS_TEXT
        dialogo.setView(entrada)
        entrada.text = et.text
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            et.text = entrada.text
            etAccion.setText(accion)
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }
    fun dialogoEntradaNumero(et:EditText,context: Context){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        val entrada = EditText(context)
        dialogo.setTitle(et.hint)
        entrada.inputType = InputType.TYPE_CLASS_NUMBER
        dialogo.setView(entrada)
        entrada.text = et.text
        if (et.text.toString().trim() == "0"){
            entrada.setText("")
        }
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (entrada.text.trim() == "" || entrada.text.trim() == "null" || entrada.text.isEmpty()
            ) {
                entrada.setText("0")
            }
            et.setText(entero(entrada.text.toString()))
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }
    fun dialogoEntradaNumeroDecimal(et:EditText,context: Context){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        val entrada = EditText(context)
        dialogo.setTitle(et.hint)
        dialogo.setView(entrada)
//        entrada.inputType = InputType.TYPE_CLASS_PHONE
        entrada.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        entrada.keyListener = DigitsKeyListener.getInstance(false,true)
        entrada.text = et.text
        if (et.text.toString().trim() == "0"){
            entrada.setText("")
        }
        dialogo.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            if (entrada.text.trim() == "" || entrada.text.trim() == "null" || entrada.text.isEmpty()
            ) {
                entrada.setText("0.0")
            }
            et.setText(decimal(entrada.text.toString().replace(",", ".")))
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

    //DATOS
    fun ultimoNroOrden(tabla: String): Int {
        val cursor: Cursor
        val sql: String = ("SELECT  MAX(id),nro_orden FROM '" + tabla + "' "
                + "where est_enviO= 'N'")
        cursor = consultar(sql)
        var id: String?
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex("NRO_ORDEN"))
            if (id == null) {
                id = "0"
            }
        } else {
            id = "0"
        }
        return id.toInt()
    }
    fun recalcularNroOrden(tabla: String,context: Context) {
        val cursor: Cursor
        var upd: String
        val sql: String = ("SELECT  id FROM '" + tabla + "' "
                + "where est_enviO= 'N' ORDER BY id ASC ")
        cursor = consultar(sql)
        val nreg = cursor.count
        cursor.moveToFirst()
        var cont = 0
        var id: String
        for (i in 0 until nreg) {
            cont += 1
            id = cursor.getString(cursor.getColumnIndex("id"))
            upd = ("UPDATE '" + tabla + "' SET nro_orden = '" + cont + "" + "' "
                    + "WHERE id=" + id)
            ejecutar(upd,context)
            cursor.moveToNext()
        }
    }

    fun toast(context:Context,mensaje:String){
        Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show()
    }




    fun obtenerHoraActualDeInternet(): LocalDateTime {


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        val url = "http://worldtimeapi.org/api/timezone/Etc/UTC"

        val (request, response, result) = Fuel.get(url)
            .responseString()

        when (result) {
            is Result.Success -> {
                val jsonResponse = JSONObject(result.value)
                val unixTime = jsonResponse.getString("unixtime")

                val zoneId = "America/Asuncion"
                val formattedDate = convertUnixTimeToDate(unixTime.toLong(), zoneId)

                //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                //val formattedDateTime = formattedDate.format(formatter)

                return formattedDate
            }
            is Result.Failure -> {
                val error = result.error.exception.message
                throw Exception("Error al obtener la fecha y hora actual: $error")
            }
        }
    }

    fun convertUnixTimeToDate(unixTime: Long, zoneId: String): LocalDateTime {
        val instant = Instant.ofEpochSecond(unixTime)
        val zone = ZoneId.of(zoneId)
        val localDateTime = LocalDateTime.ofInstant(instant, zone)


        //var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        //formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return localDateTime
    }


    fun dialogoEntradaTextoLargo(et: EditText, context: Context){
        val dialogo : AlertDialog.Builder = AlertDialog.Builder(context)
        val entrada = EditText(context)
        dialogo.setTitle(et.hint)
        dialogo.setView(entrada)
        entrada.text = et.text
        dialogo.setPositiveButton(
            "OK"
        ) { _: DialogInterface, _: Int ->
            et.text = entrada.text
        }
        dialogo.setCancelable(false)
        dialogo.show()
    }

}
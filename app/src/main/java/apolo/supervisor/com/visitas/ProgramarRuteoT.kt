package apolo.supervisor.com.visitas

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Adapter
import apolo.supervisor.com.utilidades.DialogoBusqueda
import apolo.supervisor.com.utilidades.DialogoCalendario
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_programar_ruteo_t.*
import kotlinx.android.synthetic.main.barra_vendedores.*


class ProgramarRuteoT : AppCompatActivity() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        var funcion : FuncionesUtiles = FuncionesUtiles()
        var datos: HashMap<String, String> = HashMap()
        var fecha : String = ""
        lateinit var cursor: Cursor
    }

    var calendario : DialogoCalendario = DialogoCalendario(this)
    lateinit var buscar : DialogoBusqueda
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programar_ruteo_t)

        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo)
        if (fecha == ""){
            inicializarElementos()
            return
        }
        inicializaElementosConsulta()
    }

    fun inicializarElementos(){
        eliminaRuteoViejoAnoAnterior()
        if (!validaEnvioSemanal()){
            return
        }
        funcion.cargarTitulo(R.drawable.ic_ruteo_prog,"Programar ruteo")
        buscar = if (FuncionesUtiles.usuario["TIPO"].equals("M")){
            DialogoBusqueda(this,"svm_cliente_supervisores","COD_CLIENTE","DESC_SUBCLIENTE","COD_CLIENTE,COD_SUBCLIENTE,DESC_SUBCLIENTE,COD_VENDEDOR,DESC_VENDEDOR","CAST(COD_CLIENTE AS NUMBER),CAST(COD_SUBCLIENTE AS NUMBER)","",accion,null)
        } else {
            DialogoBusqueda(this,"svm_cliente_supervisores","COD_VENDEDOR","NOMBRE_VENDEDOR","COD_VENDEDOR","",accion,null)
        }
        cargar()
        mostrar()
        btAgregar.setOnClickListener{agregar()}
        btEliminar.setOnClickListener{eliminar()}
        btCancelar.setOnClickListener{cancelar()}
        imgEnviar.setOnClickListener{enviar()}
        inicializaETAccion(accion)
        eliminaRuteoViejo()
    }

    private fun inicializaElementosConsulta(){
        funcion.cargarTitulo(R.drawable.ic_ruteo_prog,"Ruteo cargado")
        buscar = if (FuncionesUtiles.usuario["TIPO"].equals("M")){
            DialogoBusqueda(this,"svm_cliente_supervisores","COD_CLIENTE","DESC_SUBCLIENTE","COD_CLIENTE,COD_SUBCLIENTE,DESC_SUBCLIENTE,COD_VENDEDOR,DESC_VENDEDOR","CAST(COD_CLIENTE AS NUMBER),CAST(COD_SUBCLIENTE AS NUMBER)","",accion,null)
        } else {
            DialogoBusqueda(this,"svm_cliente_supervisores","COD_VENDEDOR","NOMBRE_VENDEDOR","COD_VENDEDOR","",accion,null)
        }
        cargar()
        mostrar()
    }

    fun cargar(){
        FuncionesUtiles.listaCabecera = ArrayList()
        var fechas : ArrayList<String> = calendario.fechasProxSemana()
        if (fecha != ""){
            fechas = calendario.fechasSemana(fecha)
        }
        for (i in 0 until fechas.size){
            datos = HashMap()
            datos["DIA"] = funcion.getDia(i+2)
            datos["FECHA"] = fechas[i]
            if ((i+2)<8){
                FuncionesUtiles.listaCabecera.add(datos)
            }
        }
        FuncionesUtiles.subListaDetalle = ArrayList()
        for(i in 0 until FuncionesUtiles.listaCabecera.size){
            var sql : String = ("SELECT a.COD_VENDEDOR CODIGO, b.DESC_VENDEDOR DESCRIPCION, a.FECHA "
                    + "  FROM svm_ruteo_semanal a, "
                    + "       svm_cliente_supervisores b "
                    + "  where cast(a.COD_VENDEDOR as number) = cast(b.COD_VENDEDOR as number) "
                    + "    and a.FECHA = '" + fechas[i] + "' ")
                    if(fecha == ""){
                        sql += "    and a.ESTADO = 'P' "
                    }
                    sql += "  group by a.COD_VENDEDOR, b.DESC_VENDEDOR, a.FECHA "

            if (FuncionesUtiles.usuario["TIPO"].toString().trim() == "M"){
                sql = ("SELECT DISTINCT a.COD_CLIENTE || '-' || a.COD_SUBCLIENTE CODIGO, b.DESC_SUBCLIENTE DESCRIPCION, a.FECHA "
                        + "  FROM svm_ruteo_semanal a, "
                        + "       svm_cliente_supervisores b "
                        + "  where cast(a.COD_VENDEDOR as number) = cast(b.COD_VENDEDOR as number) "
                        + "    and a.COD_CLIENTE = b.COD_CLIENTE "
                        + "    and a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
                        + "    and a.FECHA = '" + fechas[i] + "' ")
                        if(fecha == ""){
                            sql += "    and a.ESTADO = 'P' "
                        }
            }

//            sql = "SELECT COD_VENDEDOR, '' AS DESC_VENDEDOR, FECHA FROM svm_ruteo_semanal "

            cursor = funcion.consultar(sql)
            FuncionesUtiles.listaDetalle = ArrayList()

            for (k in 0 until cursor.count){
                datos = HashMap()
                for (j in 0 until cursor.columnCount){
                    datos[cursor.getColumnName(j)] = funcion.dato(cursor,cursor.getColumnName(j))
                }
                FuncionesUtiles.listaDetalle.add(datos)
                cursor.moveToNext()
            }
            FuncionesUtiles.subListaDetalle.add(FuncionesUtiles.listaDetalle)
        }
    }

    fun mostrar(){
        funcion.vistas  = intArrayOf(R.id.tv1,R.id.tv2)
        funcion.valores = arrayOf("DIA" ,"FECHA")
        funcion.subVistas  = intArrayOf(R.id.tvs1,R.id.tvs2)
        funcion.subValores = arrayOf("CODIGO"     , "DESCRIPCION")

        val adapter:Adapter.ListaConSubitem =
            Adapter.ListaConSubitem(
                this,
                FuncionesUtiles.listaCabecera,
                FuncionesUtiles.subListaDetalle,
                R.layout.vis_rut_pro_lista_proramar_ruteo,
                R.layout.vis_rut_pro_sub_lista_programar_ruteo,
                funcion.vistas,
                funcion.valores,
                funcion.subVistas,
                funcion.subValores,
                R.id.lvSubtabla,
                lvRuteoProgramado
            )

        lvRuteoProgramado.adapter = adapter
        lvRuteoProgramado.setOnItemClickListener { _: ViewGroup, _: View, position: Int, _: Long ->
            FuncionesUtiles.posicionCabecera = position
            FuncionesUtiles.posicionDetalle = -1
            lvRuteoProgramado.invalidateViews()
        }

    }

    private fun agregar(){
        buscar.cargarDialogo()
    }

    fun eliminar(){
        if (FuncionesUtiles.posicionDetalle<0 || FuncionesUtiles.posicionCabecera<0){
            funcion.mensaje(this,"Atención","Seleccione registro para eliminar")
            return
        }
        var sql : String = "DELETE FROM svm_ruteo_semanal " +
                           " WHERE COD_VENDEDOR = '" + FuncionesUtiles.subListaDetalle[FuncionesUtiles.posicionCabecera][FuncionesUtiles.posicionDetalle]["CODIGO"] + "' " +
                           "   AND FECHA        = '" + FuncionesUtiles.subListaDetalle[FuncionesUtiles.posicionCabecera][FuncionesUtiles.posicionDetalle]["FECHA"] + "' "
        if (FuncionesUtiles.usuario["TIPO"].equals("M")){
            sql = "DELETE FROM svm_ruteo_semanal " +
                  " WHERE COD_CLIENTE     = '" + FuncionesUtiles.subListaDetalle[FuncionesUtiles.posicionCabecera][FuncionesUtiles.posicionDetalle]["CODIGO"].toString().split("-")[0] + "' " +
                  "   AND COD_SUBCLIENTE  = '" + FuncionesUtiles.subListaDetalle[FuncionesUtiles.posicionCabecera][FuncionesUtiles.posicionDetalle]["CODIGO"].toString().split("-")[1] + "' "
                  "   AND FECHA           = '" + FuncionesUtiles.subListaDetalle[FuncionesUtiles.posicionCabecera][FuncionesUtiles.posicionDetalle]["FECHA"] + "' "
        }
        funcion.ejecutar(sql,this)
        cargar()
        mostrar()
        FuncionesUtiles.posicionDetalle = -1
    }

    private fun inicializaETAccion(etAccion: EditText){
        etAccion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (etAccion.text.isNotEmpty()){
                    registrarRuteo()
                    cargar()
                    mostrar()
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

    fun registrarRuteo(){
        if (validarCarga()){
            var sql = "INSERT INTO svm_ruteo_semanal (COD_EMPRESA,COD_SUPERVISOR,COD_VENDEDOR,ESTADO,COD_CLIENTE,COD_SUBCLIENTE,FECHA) VALUES (" +
                    "'${FuncionesUtiles.usuario["COD_EMPRESA"]}"  +
                    "','" + buscarSupervisor() +
                    "','" + accion.text.toString().split("-")[0].trim() +
                    "','" + "P" +
                    "','" + "0" +
                    "','" + "0" +
                    "','" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["FECHA"] +
                    "')"
            if (FuncionesUtiles.usuario["TIPO"].toString().trim() == "M"){
                sql = "INSERT INTO svm_ruteo_semanal (COD_EMPRESA,COD_SUPERVISOR,COD_VENDEDOR,ESTADO,COD_CLIENTE,COD_SUBCLIENTE,FECHA) VALUES (" +
                        "'${FuncionesUtiles.usuario["COD_EMPRESA"]}"  +
                        "','" + buscarSupervisor() +
                        "','" + accion.text.toString().split("-")[3].trim() +
                        "','" + "P" +
                        "','" + accion.text.toString().split("-")[0].trim() +
                        "','" + accion.text.toString().split("-")[1].trim() +
                        "','" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["FECHA"] +
                        "')"
            }
            funcion.ejecutar(sql,this)
            accion.setText("")
        }
    }

    private fun buscarSupervisor():String{
//        var sql:String = "SELECT DISTINCT COD_SUPERVISOR FROM svm_cliente_supervisores " +
//                         " WHERE COD_VENDEDOR = '" + accion.text.toString().split("-")[0].trim() + "'"
        return try {
    //            return funcion.dato(funcion.consultar(sql),"COD_SUPERVISOR")
            FuncionesUtiles.usuario["PASS"].toString()
        } catch (e : Exception) {
            FuncionesUtiles.usuario["PASS"].toString()
        }

    }

    private fun eliminaRuteoViejo(){
        val fec: ArrayList<String> = calendario.fechasProxSemana()
        val fecHasta = fec[0].split("/")[2] +
                  "-" + fec[0].split("/")[1] +
                  "-" + fec[0].split("/")[0]

        val sql = ("delete from svm_ruteo_semanal "
                + " where date(substr(FECHA,7) 		|| '-' ||"
                + "substr(FECHA,4,2) || '-' ||"
                + "substr(FECHA,1,2)) < date('" + fecHasta + "') "
                + " and ESTADO = 'P'")
        funcion.ejecutar(sql,this)


    }


    private fun eliminaRuteoViejoAnoAnterior(){

        //OBTENER AÑO ACTUAL
        val year : String = funcion.getFechaActual().split("/")[2]

        val fecHasta = "$year-01-01"

        //ELIMINAR RUTEO AÑO ANTERIOR
        val sql = ("delete from svm_ruteo_semanal "
                + " where date(substr(FECHA,7) 		|| '-' ||"
                + "substr(FECHA,4,2) || '-' ||"
                + "substr(FECHA,1,2)) < date('" + fecHasta + "') ")
        funcion.ejecutar(sql,this)


    }

    fun cancelar(){
        funcion.ejecutar("delete from svm_ruteo_semanal",this)
        cargar()
        mostrar()
    }
    
    private fun validarCarga():Boolean{
        var sql = ("select COD_VENDEDOR "
                + " FROM  svm_ruteo_semanal "
                + " where COD_VENDEDOR = '" + accion.text.split("-")[0].trim() + "'"
                + "   and ESTADO = 'P'")
        if (funcion.consultar(sql).count>1 && !FuncionesUtiles.usuario["TIPO"].equals("M")){
            funcion.mensaje(this,"Atención","Solo se puede seleccionar 2 veces un mismo vendedor.")
            return false
        }
        sql = ("select COD_VENDEDOR "
                + " FROM  svm_ruteo_semanal "
                + " where COD_VENDEDOR = '" + accion.text.split("-")[0].trim() + "'"
                + "   and FECHA = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["FECHA"] + "' "
                + "   and ESTADO = 'P'")
        if (FuncionesUtiles.usuario["TIPO"].equals("M")){
            sql = ("select COD_CLIENTE,COD_SUBCLIENTE "
                    + " FROM  svm_ruteo_semanal "
                    + " where COD_VENDEDOR   = '" + accion.text.split("-")[3].trim() + "'"
                    + "   and COD_CLIENTE    = '" + accion.text.split("-")[0].trim() + "'"
                    + "   AND COD_SUBCLIENTE = '" + accion.text.split("-")[1].trim() + "'"
                    + "   and FECHA  = '" + FuncionesUtiles.listaCabecera[FuncionesUtiles.posicionCabecera]["FECHA"] + "' "
                    + "   and ESTADO = 'P'")
        }
        if (funcion.consultar(sql).count>0){
            funcion.mensaje(this,"Atención","Ya se cargo el vendedor para este día.")
            return false
        }
        return true
    }

    private fun validaEnvioSemanal(): Boolean {

        val sql = ("Select id"
                + "   from svm_ruteo_semanal"
                + "   where cast( (strftime('%W', 'now') + 1) as integer) = cast( strftime('%W',date( substr(FECHA,7) || '-' || substr(FECHA,4,2) || '-' || substr(FECHA,1,2)  )) as integer) "
                + "     and ESTADO = 'E'")
        val cursor: Cursor = funcion.consultar(sql)
        val nreg = cursor.count
        if (nreg == 0) {
            return true
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Atención")
        builder.setMessage("Ya se envio un ruteo para la siguiente semana")
            .setCancelable(false)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { _, _ ->
                    finish()
                    return@OnClickListener
                })
        val alert: AlertDialog = builder.create()
        alert.show()
        return false
    }

    fun enviar(){
        EnviarRuteo.context = this
        val enviarRuteo = EnviarRuteo()
        enviarRuteo.enviar()
    }
}

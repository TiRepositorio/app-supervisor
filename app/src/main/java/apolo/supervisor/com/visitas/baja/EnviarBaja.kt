package apolo.supervisor.com.visitas.baja

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.widget.EditText
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.utilidades.FuncionesUtiles

class EnviarBaja {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
        @SuppressLint("StaticFieldLeak")
        lateinit var accion : EditText
        var resultado = ""
        var cliente = ""
        var comentario = ""
        var fotoFachada = ""
    }

    fun cargar(){
        val sql = "select * from svm_cliente_vendedor " +
                "   where COD_VENDEDOR   = '${Baja.codVendedorOld}'   " +
                "     and COD_CLIENTE    = '${Baja.codCliente}'    " +
                "     and COD_SUBCLIENTE = '${Baja.codSubcliente}' "
        cargarCliente(MainActivity.funcion.consultar(sql))
    }

    private fun cargarCliente(cursor: Cursor){
        cliente = "INSERT INTO cc_clientes_baja_prov " +
                "(COD_EMPRESA" +
                ",COD_VENDEDOR" +
                ",DESC_VENDEDOR" +
                ",COD_CLIENTE" +
                ",COD_SUBCLIENTE" +
                ",DESC_CLIENTE" +
                ",DESC_SUBCLIENTE" +
                ",COMENTARIO" +
                ",ESTADO" +
                ",FOTO_FACHADA) " +
                "VALUES " +
                "(" +
                //"'${FuncionesUtiles.usuario[COD_EMPRESA]}'," +
                "'1'," +
                "'${Baja.codVendedorOld}'," +
                "'${FuncionesUtiles.usuario["NOMBRE"]}'," +
                "'${Baja.codCliente}'," +
                "'${Baja.codSubcliente}'," +
                "'${MainActivity.funcion.dato(cursor,"DESC_CLIENTE")}'," +
                "'${MainActivity.funcion.dato(cursor,"DESC_SUBCLIENTE")}'," +
                "'$comentario'," +
                "'P'," +
                "'$fotoFachada'" +
                ")"
        MainActivity.funcion.ejecutar(cliente, context)
        cliente = //"${FuncionesUtiles.usuario[COD_EMPRESA]}" +
            "1" +
                    "|${Baja.codVendedorOld}" +
                    "|${FuncionesUtiles.usuario["NOMBRE"]}" +
                    "|${Baja.codCliente}" +
                    "|${Baja.codSubcliente}" +
                    "|${MainActivity.funcion.dato(cursor,"DESC_CLIENTE")}" +
                    "|${MainActivity.funcion.dato(cursor,"DESC_SUBCLIENTE")}" +
                    "|$comentario" +
                    "|P" +
                    "|$fotoFachada"
    }

}
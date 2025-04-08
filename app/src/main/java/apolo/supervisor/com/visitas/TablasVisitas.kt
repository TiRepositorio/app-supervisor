package apolo.supervisor.com.visitas

import apolo.supervisor.com.utilidades.SentenciasSQL
import java.util.*

class TablasVisitas {

    fun listaVisitas(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"svm_motivo_analisis_cliente")
        lista.add(1,"svm_cliente_supervisor")
        lista.add(2,"svm_cliente_supervisores")
        lista.add(3,"svm_positivacion_cliente")
        return lista
    }

    fun listaSQLCreateTableVisitas(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0, SentenciasSQL.createTableSvmMotivoAnalisisCliente())
        lista.add(1, SentenciasSQL.createTableSvmClienteSupervisor())
        lista.add(2, SentenciasSQL.createTableSvmClienteSupervisores())
        lista.add(3, SentenciasSQL.createTableSvmPositivacionCliente())
        return lista
    }

    fun listaCamposVisitas(): Vector<Vector<String>> {
        val lista : Vector<Vector<String>> = Vector()
        lista.add(0,camposTablaSvmMotivoAnalisisCliente())
        lista.add(1,camposTablaSvmClienteSupervisor())
        lista.add(2,camposTablaSvmClienteSupervisores())
        lista.add(3,camposTablaSvmPositivacionCliente())
        return lista
    }

    fun camposTablaSvmMotivoAnalisisCliente(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_MOTIVO")
        lista.add(1,"DESCRIPCION")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"ESTADO")
        lista.add(4,"PUNTUACION")
        lista.add(5,"COD_GRUPO")
        lista.add(6,"NRO_ORDEN")
        lista.add(7,"")
        return lista
    }

    fun camposTablaSvmClienteSupervisor(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_SUPERVISOR")
        lista.add(1,"DESC_SUPERVISOR")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"DESC_VENDEDOR")
        lista.add(4,"NOMBRE_VENDEDOR")
        lista.add(5,"COD_CLIENTE")
        lista.add(6,"COD_SUBCLIENTE")
        lista.add(7,"DESC_SUBCLIENTE")
        lista.add(8,"LONGITUD")
        lista.add(9,"TIEMPO_MIN")
        lista.add(10,"TIEMPO_MAX")
        lista.add(11,"LATITUD")
        lista.add(12,"")
        return lista
    }

    fun camposTablaSvmClienteSupervisores(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_SUPERVISOR")
        lista.add(1,"DESC_SUPERVISOR")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"DESC_VENDEDOR")
        lista.add(4,"NOMBRE_VENDEDOR")
        lista.add(5,"COD_CLIENTE")
        lista.add(6,"COD_SUBCLIENTE")
        lista.add(7,"DESC_SUBCLIENTE")
        lista.add(8,"LONGITUD")
        lista.add(9,"TIEMPO_MIN")
        lista.add(10,"TIEMPO_MAX")
        lista.add(11,"LATITUD")
        lista.add(12,"")
        return lista
    }

    private fun camposTablaSvmPositivacionCliente(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"TIP_CAUSAL")
        lista.add(4,"COD_CLIENTE")
        lista.add(5,"COD_SUBCLIENTE")
        lista.add(6,"DESC_CLIENTE")
        lista.add(7,"DESC_SUBCLIENTE")
        lista.add(8,"DIRECCION")
        lista.add(9,"DESC_CIUDAD")
        lista.add(10,"RUC")
        lista.add(11,"DESC_REGION")
        lista.add(12,"DESC_ZONA")
        lista.add(13,"TELEFONO")
        lista.add(14,"IND_POS")
        lista.add(15,"COD_SUPERVISOR")
        lista.add(16,"")
        return lista
    }



}
package apolo.supervisor.com.reportes

import apolo.supervisor.com.utilidades.SentenciasSQL
import java.util.*

class TablasReportes {
    fun listaReportes(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"svm_liq_premios_sup")
        lista.add(1,"svm_liq_canasta_marca_sup")
        lista.add(2,"svm_liq_canasta_cte_sup")
        lista.add(3,"svm_produccion_semanal_gcs")
        lista.add(4,"svm_liq_cuota_x_und_neg")
        lista.add(5,"svm_liquidacion_fuerza_venta")
        lista.add(6,"svm_liq_comision_supervisor")
        lista.add(7,"svm_cobertura_mensual_sup")
        return lista
    }

    fun listaSQLCreateTableReportes(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0, SentenciasSQL.createTableSvmLiqPremiosSup())
        lista.add(1, SentenciasSQL.createTableSvmLiqCanastaMarcaSup())
        lista.add(2, SentenciasSQL.createTableSvmLiqCanastaCteSup())
        lista.add(3, SentenciasSQL.createTableSvmProduccionSemanal())
        lista.add(4, SentenciasSQL.createTableSvmLiqCuotaXUnidadNeg())
        lista.add(5, SentenciasSQL.createTableSvmLiquidacionFuerzaVenta())
        lista.add(6, SentenciasSQL.createTableSvmLiqComisionSupervisor())
        lista.add(7, SentenciasSQL.createTableSvmCoberturaMensualSup())
        return lista
    }

    fun listaCamposReportes(): Vector<Vector<String>> {
        val lista : Vector<Vector<String>> = Vector()
        lista.add(0,camposTablaSvmLiqPremiosSup())
        lista.add(1,camposTablaSvmLiqCanastaMarcaSup())
        lista.add(2,camposTablaSvmLiqCanastaCteSup())
        lista.add(3,camposTablaSvmProduccionSemanal())
        lista.add(4,camposTablaSvmLiqCuotaXUndNeg())
        lista.add(5,camposTablaSvmLiquidacionFuerzaVenta())
        lista.add(6,camposTablaSvmLiqComisionSupervisor())
        lista.add(7,camposTablaSvmCoberturaMensualSup())
        return lista
    }

    fun camposTablaSvmLiqPremiosSup(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_MARCA")
        lista.add(2,"DESC_MARCA")
        lista.add(3,"MONTO_VENTA")
        lista.add(4,"MONTO_META")
        lista.add(5,"PORC_LOG")
        lista.add(6,"PORC_COBERTURA")
        lista.add(7,"TOT_CLIENTES")
        lista.add(8,"CLIENTES_VISITADOS")
        lista.add(9,"MONTO_A_COBRAR")
        lista.add(10,"MONTO_COBRAR")
        lista.add(11,"TIP_COM")
        lista.add(12,"")
        return lista
    }

    fun camposTablaSvmLiqCanastaMarcaSup(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_MARCA")
        lista.add(2,"DESC_MARCA")
        lista.add(3,"VENTAS")
        lista.add(4,"CUOTA")
        lista.add(5,"VALOR_CANASTA")
        lista.add(6,"PESO_PUNT")
        lista.add(7,"PUNTAJE_MARCA")
        lista.add(8,"PORC_CUMP")
        lista.add(9,"FEC_DESDE")
        lista.add(10,"FEC_HASTA")
        lista.add(11,"COD_UNID_NEGOCIO")
        lista.add(12,"DESC_UNID_NEGOCIO")
        lista.add(13,"MONTO_A_COBRAR")
        lista.add(14,"")
        return lista
    }

    fun camposTablaSvmLiqCanastaCteSup(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"FEC_DESDE")
        lista.add(2,"FEC_HASTA")
        lista.add(3,"COD_CLIENTE")
        lista.add(4,"DESC_CLIENTE")
        lista.add(5,"VENTAS")
        lista.add(6,"CUOTA")
        lista.add(7,"VALOR_CANASTA")
        lista.add(8,"PUNTAJE_CLIENTE")
        lista.add(9,"PORC_CUMP")
        lista.add(10,"PESO_PUNT")
        lista.add(11,"MONTO_A_COBRAR")
        lista.add(12,"")
        return lista
    }

    fun camposTablaSvmProduccionSemanal(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"CANTIDAD")
        lista.add(2,"SEMANA")
        lista.add(3,"MONTO_VIATICO")
        lista.add(4,"MONTO_TOTAL")
        lista.add(5,"PERIODO")
        lista.add(6,"MONTO_X_POR")
        lista.add(7,"CANT_CLIENTE")
        lista.add(8,"")
        return lista
    }

    fun camposTablaSvmLiqCuotaXUndNeg(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"FEC_DESDE")
        lista.add(2,"FEC_HASTA")
        lista.add(3,"COD_UNID_NEGOCIO")
        lista.add(4,"DESC_UNID_NEGOCIO")
        lista.add(5,"PORC_PREMIO")
        lista.add(6,"PORC_ALC_PREMIO")
        lista.add(7,"MONTO_VENTA")
        lista.add(8,"MONTO_CUOTA")
        lista.add(9,"MONTO_A_COBRAR")
        lista.add(10,"")
        return lista
    }

    fun camposTablaSvmLiquidacionFuerzaVenta(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"FEC_COMPROBANTE")
        lista.add(2,"OBSERVACION")
        lista.add(3,"DESCRIPCION")
        lista.add(4,"TIP_COMPROBANTE_REF")
        lista.add(5,"TOT_IVA")
        lista.add(6,"TOT_GRAVADA")
        lista.add(7,"TOT_EXENTA")
        lista.add(8,"TOT_COMPROBANTE")
        lista.add(9,"")
        return lista
    }

    fun camposTablaSvmLiqComisionSupervisor(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_CONCEPTO")
        lista.add(1,"DESC_CONCEPTO")
        lista.add(2,"MONTO")
        lista.add(3,"TIPO")
        lista.add(4,"NRO_ORDEN")
        lista.add(5,"NRO_CUOTA")
        lista.add(6,"FEC_HASTA")
        lista.add(7,"COD_MONEDA")
        lista.add(8,"DECIMALES")
        lista.add(9,"SIGLAS")
        lista.add(10,"COMENT")
        lista.add(11,"")
        return lista
    }

    fun camposTablaSvmCoberturaMensualSup(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"TOT_CLI_CART")
        lista.add(2,"CANT_POSIT")
        lista.add(3,"TOT_CLIEN_ASIG")
        lista.add(4,"PORC_LOGRO")
        lista.add(5,"PORC_COB")
        lista.add(6,"PREMIOS")
        lista.add(7,"MONTO_A_COBRAR")
        lista.add(8,"")
        return lista
    }

//    fun camposTablaS(): Vector<String> {
//        val lista : Vector<String> = Vector<String>()
//        lista.add(0,"")
//        lista.add(1,"")
//        lista.add(2,"")
//        lista.add(3,"")
//        lista.add(4,"")
//        lista.add(5,"")
//        lista.add(6,"")
//        lista.add(7,"")
//        lista.add(8,"")
//        lista.add(9,"")
//        lista.add(10,"")
//        lista.add(11,"")
//        lista.add(12,"")
//        lista.add(13,"")
//        lista.add(14,"")
//        lista.add(15,"")
//        lista.add(16,"")
//        lista.add(17,"")
//        lista.add(18,"")
//        lista.add(19,"")
//        lista.add(20,"")
//        lista.add(21,"")
//        lista.add(22,"")
//        lista.add(23,"")
//        lista.add(24,"")
//        lista.add(25,"")
//        lista.add(26,"")
//        lista.add(27,"")
//        lista.add(28,"")
//        lista.add(29,"")
//        return lista
//    }

}
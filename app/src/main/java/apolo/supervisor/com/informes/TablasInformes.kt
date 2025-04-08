package apolo.supervisor.com.informes

import apolo.supervisor.com.utilidades.SentenciasSQL
import java.util.*

class TablasInformes {
    fun listaInformes(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"svm_cliente_dia_visita_ruteo")
        lista.add(1,"svm_control_venta_diaria_cab")
        lista.add(2,"svm_control_venta_diaria_det")
        lista.add(3,"svm_rebotes_por_cliente")
        lista.add(4,"svm_pedidos_en_reparto")
        lista.add(5,"svm_seg_visitas_semanal")
        lista.add(6,"svm_liq_cuota_x_und_neg_vend")
        lista.add(7,"svm_prom_alc_cuota_mensual_sup")
        lista.add(8,"svm_cobertura_mensual_vend")
        lista.add(9,"svm_cob_semanal_vend")
        lista.add(10,"svm_liq_premios_vend")
        lista.add(11,"svm_metas_punto_por_cliente")
        lista.add(12,"svm_evolucion_diaria_venta")
        lista.add(13,"svm_deuda_cliente")
        lista.add(14,"svm_precio_fijo_consulta")
        lista.add(15,"svm_movimiento_gestor")
        return lista
    }

    fun listaSQLCreateTableInformes(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0, SentenciasSQL.createTableSvmClienteDiaVisitaRuteo())
        lista.add(1, SentenciasSQL.createTableSvmControlVentaDiariaCab())
        lista.add(2, SentenciasSQL.createTableSvmControlVentaDiariaDet())
        lista.add(3, SentenciasSQL.createTableSvmRebotesPorCliente())
        lista.add(4, SentenciasSQL.createTableSvmPedidosEnReparto())
        lista.add(5, SentenciasSQL.createTableSvmSegVisitasSemanal())
        lista.add(6, SentenciasSQL.createTableSvmLiqCuotaXUnidadNegVend())
        lista.add(7, SentenciasSQL.createTableSvmPromAlcCuotaMensualSup())
        lista.add(8, SentenciasSQL.createTableSvmCoberturaMensualVend())
        lista.add(9, SentenciasSQL.createTableSvmCobSemanalVend())
        lista.add(10, SentenciasSQL.createTableSvmLiqPremiosVend())
        lista.add(11, SentenciasSQL.createTableSvmMetasPuntoPorCliente())
        lista.add(12, SentenciasSQL.createTableSvmEvolucionDiariaVenta())
        lista.add(13, SentenciasSQL.createTableSvmDeudaPorClientes())
        lista.add(14, SentenciasSQL.createTableSvmPrecioFijoConsulta())
        lista.add(15, SentenciasSQL.createTableSvmMovimientoGestor())
        return lista
    }

    fun listaCamposInformes(): Vector<Vector<String>> {
        val lista : Vector<Vector<String>> = Vector()
        lista.add(0,camposTablaSvmClienteDiaVisitaRuteo())
        lista.add(1,camposTablaSvmControlVentaDiariaCab())
        lista.add(2,camposTablaSvmControlVentaDiariaDet())
        lista.add(3,camposTablaSvmRebotesPorCliente())
        lista.add(4,camposTablaSvmPedidosEnReparto())
        lista.add(5,camposTablaSvmSegVisitasSemanal())
        lista.add(6,camposTablaSvmLiqCuotaXUndNegVend())
        lista.add(7,camposTablaSvmPromAlcCuotaMensualSup())
        lista.add(8,svmCoberturaMensualVend())
        lista.add(9,camposTablaSvmCobSemanalVend())
        lista.add(10,camposTablaSvmLiqPremiosVend())
        lista.add(11,camposTablaSvmMetasPuntoPorCliente())
        lista.add(12,camposTablaSvmEvolDiariaVenta())
        lista.add(13,camposTablaSvmDeudaCliente())
        lista.add(14,camposTablaSvmPrecioFijoConsulta())
        lista.add(15,camposTablaSvmMovimientoGestor())
        return lista
    }

    fun camposTablaSvmClienteDiaVisitaRuteo(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"NOMBRE_VENDEDOR")
        lista.add(3,"FEC_MOVIMIENTO")
        lista.add(4,"CANT_CLIENTES")
        lista.add(5,"CANT_VENDIDO")
        lista.add(6,"CANT_NO_VENTA")
        lista.add(7,"PORC")
        lista.add(8,"")
        return lista
    }

    fun camposTablaSvmControlVentaDiariaCab(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"NOMBRE_VENDEDOR")
        lista.add(4,"RUTEO")
        lista.add(5,"POSITIVADO")
        lista.add(6,"POS_FUERA")
        lista.add(7,"TOTAL_PEDIDO")
        lista.add(8,"TOTAL_PEDIDO")
        lista.add(9,"")
        return lista
    }

    fun camposTablaSvmControlVentaDiariaDet(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"FEC_COMPROBANTE")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"COD_CLIENTE")
        lista.add(4,"COD_SUBCLIENTE")
        lista.add(5,"NOM_CLIENTE")
        lista.add(6,"NOM_SUBCLIENTE")
        lista.add(7,"COD_ARTICULO")
        lista.add(8,"DESC_ARTICULO")
        lista.add(9,"CANTIDAD")
        lista.add(10,"PRECIO_UNITARIO")
        lista.add(11,"TOT_DESCUENTO")
        lista.add(12,"MONTO_TOTAL")
        lista.add(13,"MONTO_TOTAL")
        lista.add(14,"")
        return lista
    }

    fun camposTablaSvmRebotesPorCliente(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"CODIGO")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"NOM_VENDEDOR")
        lista.add(4,"COD_SUPERVISOR")
        lista.add(5,"DESC_SUPERVISOR")
        lista.add(6,"DESC_PENALIDAD")
        lista.add(7,"MONTO_TOTAL")
        lista.add(8,"FECHA")
        lista.add(9,"")
        return lista
    }

    fun camposTablaSvmPedidosEnReparto(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"NRO_PLANILLA")
        lista.add(2,"DESC_REPARTIDOR")
        lista.add(3,"TEL_REPARTIDOR")
        lista.add(4,"FEC_PLANILLA")
        lista.add(5,"FEC_COMPROBANTE")
        lista.add(6,"TIP_COMPROBANTE")
        lista.add(7,"NRO_COMPROBANTE")
        lista.add(8,"COD_CLIENTE")
        lista.add(9,"COD_SUBCLIENTE")
        lista.add(10,"NOM_CLIENTE")
        lista.add(11,"NOM_SUBCLIENTE")
        lista.add(12,"SIGLAS")
        lista.add(13,"DECIMALES")
        lista.add(14,"COD_VENDEDOR")
        lista.add(15,"DESC_VENDEDOR")
        lista.add(16,"COD_SUPERVISOR")
        lista.add(17,"DESC_SUPERVISOR")
        lista.add(18,"ESTADO_ENTREGA")
        lista.add(19,"TOT_COMPROBANTE")
        lista.add(20,"")
        return lista
    }

    fun camposTablaSvmSegVisitasSemanal(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"FEC_MOVIMIENTO")
        lista.add(2,"COD_VENDEDOR")
        lista.add(3,"NOMBRE_VENDEDOR")
        lista.add(4,"FEC_INICIO")
        lista.add(5,"FEC_FIN")
        lista.add(6,"CANTIDAD")
        lista.add(7,"CANT_VENDIDO")
        lista.add(8,"CANT_NO_VENDIDO")
        lista.add(9,"CANT_NO_VISITADO")
        lista.add(10,"SEMANA")
        lista.add(11,"PORC")
        lista.add(12,"")
        return lista
    }

    fun camposTablaSvmLiqCuotaXUndNegVend(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_UNID_NEGOCIO")
        lista.add(2,"DESC_UNID_NEGOCIO")
        lista.add(3,"COD_VENDEDOR")
        lista.add(4,"DESC_VENDEDOR")
        lista.add(5,"MONTO_VENTA")
        lista.add(6,"MONTO_CUOTA")
        lista.add(7,"PORC_PREMIO")
        lista.add(8,"FEC_DESDE")
        lista.add(9,"FEC_HASTA")
        lista.add(10,"PORC_ALC_PREMIO")
        lista.add(11,"MONTO_A_COBRAR")
        lista.add(12,"")
        return lista
    }

    fun camposTablaSvmPromAlcCuotaMensualSup(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"PORC_LOG")
        lista.add(4,"PORC_ALC")
        lista.add(5,"MONTO_PREMIO")
        lista.add(6,"TOTAL_FACTURADO")
        lista.add(7,"META")
        lista.add(8,"")
        return lista
    }

    fun svmCoberturaMensualVend(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"TOT_CLI_CART")
        lista.add(4,"CANT_POSIT")
        lista.add(5,"PORC_COB")
        lista.add(6,"PORC_LOGRO")
        lista.add(7,"FEC_DESDE")
        lista.add(8,"FEC_HASTA")
        lista.add(9,"PREMIOS")
        lista.add(10,"MONTO_A_COBRAR")
        lista.add(11,"")
        return lista
    }

    fun camposTablaSvmCobSemanalVend(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"SEMANA")
        lista.add(4,"TOT_CLIENTES")
        lista.add(5,"CLIENT_VENTAS")
        lista.add(6,"PORC_COBERTURA")
        lista.add(7,"PERIODO")
        lista.add(8,"MONTO_A_COBRAR")
        lista.add(9,"")
        return lista
    }

    fun camposTablaSvmLiqPremiosVend(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"VENDEDOR_PERSONA")
        lista.add(3,"DESC_VENDEDOR")
        lista.add(4,"TIP_COM")
        lista.add(5,"COD_MARCA")
        lista.add(6,"DESC_MARCA")
        lista.add(7,"MONTO_VENTA")
        lista.add(8,"MONTO_META")
        lista.add(9,"PORC_COBERTURA")
        lista.add(10,"PORC_LOG")
        lista.add(11,"MONTO_A_COBRAR")
        lista.add(12,"MONTO_COBRAR")
        lista.add(13,"TOT_CLIENTES")
        lista.add(14,"CLIENTES_VISITADOS")
        lista.add(15,"")
        return lista
    }

    fun camposTablaSvmMetasPuntoPorCliente(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"DESC_VENDEDOR")
        lista.add(3,"CODIGO")
        lista.add(4,"NOM_SUBCLIENTE")
        lista.add(5,"CIUDAD")
        lista.add(6,"COD_SUPERVISOR")
        lista.add(7,"DESC_SUPERVISOR")
        lista.add(8,"LISTA_PRECIO")
        lista.add(9,"MAYOR_VENTA")
        lista.add(10,"VENTA_3")
        lista.add(11,"MIX_3")
        lista.add(12,"VENTA_4")
        lista.add(13,"MIX_4")
        lista.add(14,"METAS")
        lista.add(15,"TOT_SURTIDO")
        lista.add(16,"MES_1")
        lista.add(17,"MES_2")
        lista.add(18,"MES_2")
        lista.add(19,"")
        return lista
    }

    fun camposTablaSvmEvolDiariaVenta(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_SUPERVISOR")
        lista.add(2,"NOM_SUPERVISOR")
        lista.add(3,"COD_VENDEDOR")
        lista.add(4,"DESC_VENDEDOR")
        lista.add(5,"PEDIDO_2_ATRAS")
        lista.add(6,"PEDIDO_1_ATRAS")
        lista.add(7,"TOTAL_PEDIDOS")
        lista.add(8,"TOTAL_FACT")
        lista.add(9,"META_VENTA")
        lista.add(10,"META_LOGRADA")
        lista.add(11,"PROY_VENTA")
        lista.add(12,"TOTAL_REBOTE")
        lista.add(13,"TOTAL_DEVOLUCION")
        lista.add(14,"CANT_CLIENTES")
        lista.add(15,"CANT_POSIT")
        lista.add(16,"EF_VISITA")
        lista.add(17,"DIA_TRAB")
        lista.add(18,"PUNTAJE")
        lista.add(19,"SURTIDO_EF")
        lista.add(20,"")
        return lista
    }

    fun camposTablaSvmDeudaCliente(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_CLIENTE")
        lista.add(2,"DESC_CLIENTE")
        lista.add(3,"COD_SUBCLIENTE")
        lista.add(4,"DESC_SUBCLIENTE")
        lista.add(5,"COD_VENDEDOR")
        lista.add(6,"DESC_VENDEDOR")
        lista.add(7,"COD_SUPERVISOR")
        lista.add(8,"DESC_SUPERVISOR")
        lista.add(9,"FEC_EMISION")
        lista.add(10,"FEC_VENCIMIENTO")
        lista.add(11,"TIP_DOCUMENTO")
        lista.add(12,"NRO_DOCUMENTO")
        lista.add(13,"DIAS_ATRAZO")
        lista.add(14,"ABREVIATURA")
        lista.add(15,"SALDO_CUOTA")
        lista.add(16,"")
        return lista
    }

    fun camposTablaSvmPrecioFijoConsulta(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_LISTA_PRECIO")
        lista.add(2,"DESC_LISTA_PRECIO")
        lista.add(3,"COD_ARTICULO")
        lista.add(4,"DESC_ARTICULO")
        lista.add(5,"PREC_CAJA")
        lista.add(6,"PREC_UNID")
        lista.add(7,"REFERENCIA")
        lista.add(8,"DECIMALES")
        lista.add(9,"SIGLAS")
        lista.add(10,"")
        return lista
    }

    fun camposTablaSvmMovimientoGestor(): Vector<String> {
        val lista : Vector<String> = Vector()
        lista.add(0,"COD_EMPRESA")
        lista.add(1,"COD_VENDEDOR")
        lista.add(2,"NOM_VENDEDOR")
        lista.add(3,"COD_CLIENTE")
        lista.add(4,"DESC_CLIENTE")
        lista.add(5,"FEC_ASISTENCIA")
        lista.add(6,"HORA_ENTRADA")
        lista.add(7,"HORA_SALIDA")
        lista.add(8,"MARCACION")
        lista.add(9,"")
        return lista
    }

}
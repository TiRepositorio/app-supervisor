package apolo.supervisor.com.utilidades

import java.util.*

class SentenciasSQL {
    companion object{
        var sql: String = ""


        fun createTableUsuarios(): String{
            return "CREATE TABLE IF NOT EXISTS usuarios " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT  , NOMBRE TEXT       , " +
                    " PASS TEXT                             , TIPO TEXT         , " +
                    " ACTIVO TEXT                           , COD_EMPRESA TEXT  , " +
                    " VERSION TEXT                          , COD_PERSONA TEXT);"
        }

        fun insertUsuario(usuario: HashMap<String, String>):String{
            return "INSERT INTO usuarios (NOMBRE, PASS, TIPO, COD_PERSONA, COD_EMPRESA, VERSION) VALUES " +
                    "('" + usuario["NOMBRE"] + "'," +
                     "'" + usuario["PASS"] + "'," +
                     "'" + usuario["TIPO"] + "'," +
                     "'" + usuario["COD_PERSONA"] + "'," +
                     "'" + usuario["COD_EMPRESA"] + "'," +
                     "'" + usuario["VERSION"] + "')"
        }

        //REPORTES
        fun createTableSvmLiqPremiosSup(): String{ //AVANCE DE COMISIONES DE SUPERVISORES
            return ("CREATE TABLE IF NOT EXISTS svm_liq_premios_sup "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA        TEXT  ,  COD_MARCA       TEXT    , "
                    + " DESC_MARCA         TEXT  ,  MONTO_VENTA     NUMBER  , "
                    + " MONTO_META         NUMBER,  PORC_LOG        TEXT    , "
                    + " PORC_COBERTURA     TEXT  ,  TOT_CLIENTES    NUMBER  , "
                    + " CLIENTES_VISITADOS NUMBER,  MONTO_A_COBRAR  NUMBER  , "
                    + " MONTO_COBRAR    NUMBER   ,  TIP_COM         TEXT )")
        }

        fun createTableSvmLiqCanastaMarcaSup(): String{ //CANASTA DE MARCAS
            return ("CREATE TABLE IF NOT EXISTS svm_liq_canasta_marca_sup "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_MARCA TEXT 		,"
                    + " DESC_MARCA TEXT				 		 , VENTAS TEXT 			, CUOTA TEXT 			,"
                    + " VALOR_CANASTA TEXT 					 , PESO_PUNT TEXT		, PUNTAJE_MARCA TEXT 	,"
                    + " PORC_CUMP TEXT 				 		 , FEC_DESDE TEXT		, FEC_HASTA TEXT 		,"
                    + " MONTO_A_COBRAR 						 , COD_UNID_NEGOCIO TEXT, DESC_UNID_NEGOCIO TEXT "
                    + ")")
        }

        fun createTableSvmLiqCanastaCteSup(): String{ //CANASTA DE CLIENTES
            return ("CREATE TABLE IF NOT EXISTS svm_liq_canasta_cte_sup "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, FEC_DESDE TEXT 		,"
                    + " FEC_HASTA TEXT				 		 , COD_CLIENTE TEXT 	, DESC_CLIENTE TEXT 	,"
                    + " VENTAS TEXT 						 , CUOTA TEXT			, VALOR_CANASTA TEXT 	,"
                    + " PUNTAJE_CLIENTE TEXT 				 , PORC_CUMP TEXT		, PESO_PUNT TEXT 		,"
                    + " MONTO_A_COBRAR TEXT 																 "
                    + ")")
        }

        fun createTableSvmProduccionSemanal(): String{ //PRODUCCION SEMANAL
            return ("CREATE TABLE IF NOT EXISTS svm_produccion_semanal_gcs"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, CANTIDAD TEXT 		,"
                    + " SEMANA TEXT					 		 , MONTO_VIATICO TEXT 	, MONTO_TOTAL TEXT 		,"
                    + " PERIODO TEXT 						 , FEC_DESDE TEXT		, FEC_HASTA TEXT 		,"
                    + " DESC_SUPERVISOR TEXT 				 , MONTO_X_POR TEXT		, CANT_CLIENTE TEXT 	 "
                    + ")")
        }

        fun createTableSvmLiqCuotaXUnidadNeg(): String{ //CUOTA POR UNIDAD DE NEGOCIO
            return ("CREATE TABLE IF NOT EXISTS svm_liq_cuota_x_und_neg "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, FEC_DESDE TEXT 		,"
                    + " FEC_HASTA TEXT				 		 , COD_UNID_NEGOCIO TEXT, DESC_UNID_NEGOCIO TEXT,"
                    + " PORC_PREMIO TEXT 					 , PORC_ALC_PREMIO TEXT	, MONTO_VENTA TEXT 		,"
                    + " MONTO_CUOTA TEXT 					 , MONTO_A_COBRAR TEXT							 "
                    + ")")
        }

        fun createTableSvmLiquidacionFuerzaVenta(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_liquidacion_fuerza_venta "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA         TEXT  , FEC_COMPROBANTE  DATE  , "
                    + " OBSERVACION         TEXT  , DESCRIPCION      TEXT  , "
                    + " TIP_COMPROBANTE_REF TEXT  , TOT_IVA          NUMBER, "
                    + " TOT_GRAVADA         NUMBER, TOT_EXENTA       NUMBER(15,2), "
                    + " TOT_COMPROBANTE     NUMBER)")
        }

        fun createTableSvmLiqComisionSupervisor(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_liq_comision_supervisor "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_CONCEPTO TEXT  , DESC_CONCEPTO TEXT,"
                    + " MONTO TEXT							, TIPO TEXT          , NRO_ORDEN TEXT    ,"
                    + " NRO_CUOTA TEXT 						, FEC_HASTA TEXT  	 , COD_MONEDA TEXT   ,"
                    + " DECIMALES TEXT   					, SIGLAS TEXT  		 , COMENT TEXT)")
        }

        fun createTableSvmCoberturaMensualSup(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cobertura_mensual_sup "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, TOT_CLI_CART TEXT 	,"
                    + " CANT_POSIT TEXT				 		 , TOT_CLIEN_ASIG TEXT	, PORC_LOGRO TEXT		,"
                    + " PORC_COB TEXT 						 , PREMIOS TEXT			,  MONTO_A_COBRAR TEXT	 "
                    + ")")
        }

        //VISITAS
        fun createTableSvmMotivoAnalisisCliente(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_motivo_analisis_cliente "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_MOTIVO TEXT, DESCRIPCION TEXT,"
                    + " ESTADO TEXT							, PUNTUACION TEXT, COD_VENDEDOR TEXT,"
                    + " NRO_ORDEN TEXT 						, COD_GRUPO TEXT)")
        }

        fun createTableSvmClienteSupervisor(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cliente_supervisor "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
                    + " COD_VENDEDOR TEXT					, DESC_VENDEDOR TEXT    , NOMBRE_VENDEDOR TEXT  ,"
                    + " COD_CLIENTE TEXT				    , COD_SUBCLIENTE TEXT   , DESC_SUBCLIENTE TEXT  ,"
                    + " LONGITUD TEXT					    , LATITUD TEXT	        , TIEMPO_MIN TEXT       ,"
                    + " TIEMPO_MAX TEXT  )")
        }

        fun createTableSvmClienteSupervisores(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cliente_supervisores "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
                    + " COD_VENDEDOR TEXT					, DESC_VENDEDOR TEXT    , NOMBRE_VENDEDOR TEXT  ,"
                    + " COD_CLIENTE TEXT				    , COD_SUBCLIENTE TEXT   , DESC_SUBCLIENTE TEXT  ,"
                    + " LONGITUD TEXT					    , LATITUD TEXT	        , TIEMPO_MIN TEXT       ,"
                    + " TIEMPO_MAX TEXT  )")
        }

        fun createTableSvmPositivacionCliente(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_positivacion_cliente "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_VENDEDOR TEXT,"
                    + " DESC_VENDEDOR TEXT                  , COD_SUPERVISOR TEXT, TIP_CAUSAL TEXT  ,"
                    + " COD_CLIENTE TEXT                    , COD_SUBCLIENTE TEXT, DESC_CLIENTE TEXT,"
                    + " DESC_SUBCLIENTE TEXT                , DIRECCION TEXT     , DESC_CIUDAD TEXT ,"
                    + " RUC TEXT                            , DESC_REGION TEXT   , DESC_ZONA TEXT   ,"
                    + " TELEFONO TEXT						, MONTO_VENTA TEXT   , IND_POS TEXT );")
        }

        //INFORMES
        fun createTableSvmClienteDiaVisitaRuteo(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cliente_dia_visita_ruteo"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA    TEXT   , COD_VENDEDOR  TEXT		,"
                    + " NOMBRE_VENDEDOR TEXT				 , FEC_MOVIMIENTO TEXT	 , CANT_CLIENTES TEXT		,"
                    + " CANT_VENDIDO NUMBER					 , CANT_NO_VENTA  NUMBER , PORC          NUMBER)")
        }

        fun createTableSvmControlVentaDiariaCab(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_control_venta_diaria_cab "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_VENDEDOR TEXT,"
                    + " DESC_VENDEDOR TEXT                   , NOMBRE_VENDEDOR TEXT, RUTEO TEXT       ,"
                    + " POSITIVADO TEXT                      , POS_FUERA TEXT      , TOTAL_PEDIDO TEXT )")
        }

        fun createTableSvmControlVentaDiariaDet(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_control_venta_diaria_det "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , FEC_COMPROBANTE TEXT,"
                    + " COD_VENDEDOR TEXT                    , COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT ,"
                    + " NOM_CLIENTE TEXT                     , NOM_SUBCLIENTE TEXT , COD_ARTICULO TEXT   ,"
                    + " DESC_ARTICULO TEXT                   , CANTIDAD TEXT       , PRECIO_UNITARIO TEXT,"
                    + " TOT_DESCUENTO TEXT                   , MONTO_TOTAL TEXT )")
        }

        fun createTableSvmRebotesPorCliente(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_rebotes_por_cliente "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , CODIGO TEXT         ,"
                    + " COD_VENDEDOR TEXT                   , NOM_VENDEDOR TEXT  , COD_SUPERVISOR TEXT ,"
                    + " DESC_SUPERVISOR TEXT                , DESC_PENALIDAD TEXT, MONTO_TOTAL TEXT    ,"
                    + " FECHA TEXT)")
        }

        fun createTableSvmPedidosEnReparto(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_pedidos_en_reparto "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , NRO_PLANILLA TEXT    ,"
                    + " DESC_REPARTIDOR TEXT                , TEL_REPARTIDOR TEXT , ESTADO_ENTREGA TEXT  ,"
                    + " FEC_PLANILLA TEXT                   , FEC_COMPROBANTE TEXT, TIP_COMPROBANTE TEXT ,"
                    + " NRO_COMPROBANTE TEXT                , COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT  ,"
                    + " NOM_CLIENTE TEXT                    , NOM_SUBCLIENTE TEXT , SIGLAS TEXT          ,"
                    + " DECIMALES TEXT                      , TOT_COMPROBANTE TEXT, COD_VENDEDOR TEXT    ,"
                    + " DESC_VENDEDOR TEXT					, COD_SUPERVISOR TEXT , DESC_SUPERVISOR TEXT)")
        }

        fun createTableSvmSegVisitasSemanal(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_seg_visitas_semanal"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA        TEXT   , FEC_MOVIMIENTO  	TEXT		,"
                    + " COD_VENDEDOR 		TEXT		 	 , NOMBRE_VENDEDOR    TEXT	 , FEC_INICIO 		TEXT 		,"
                    + " FEC_FIN 			TEXT           	 , CANTIDAD  		  TEXT 	 , CANT_VENDIDO  	TEXT 		,"
                    + " CANT_NO_VENDIDO 	TEXT			 , CANT_NO_VISITADO   TEXT 	 , SEMANA			TEXT		,"
                    + " PORC TEXT)")
        }

        fun createTableSvmLiqCuotaXUnidadNegVend(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_liq_cuota_x_und_neg_vend "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, FEC_DESDE TEXT 		,"
                    + " FEC_HASTA TEXT				 		 , COD_VENDEDOR TEXT		, DESC_VENDEDOR TEXT	,"
                    + " COD_UNID_NEGOCIO TEXT 				 , DESC_UNID_NEGOCIO TEXT	, COD_PERFIL_VEND TEXT 	,"
                    + " PORC_PREMIO TEXT 					 , MONTO_VENTA TEXT			, MONTO_CUOTA TEXT 		,"
                    + " MONTO_A_COBRAR TEXT					 , MONTO_PREMIO TEXT		, PORC_ALC_PREMIO TEXT	 "
                    + ")")
        }

        fun createTableSvmPromAlcCuotaMensualSup(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_prom_alc_cuota_mensual_sup"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_VENDEDOR TEXT 	,"
                    + " DESC_VENDEDOR TEXT					 , TIP_CARTERA TEXT    	, PORC_LOG TEXT 		,"
                    + " PORC_ALC TEXT 						 , MONTO_PREMIO TEXT	, TOTAL_FACTURADO TEXT 	,"
                    + " META TEXT         					 "
                    + ")")
        }

        fun createTableSvmCoberturaMensualVend(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cobertura_mensual_vend "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
                    + " DESC_VENDEDOR TEXT			 		 , TOT_CLI_CART TEXT		, CANT_POSIT TEXT		,"
                    + " PORC_COB TEXT 				 		 , PORC_LOGRO TEXT			, PREMIOS TEXT 			,"
                    + " MONTO_A_COBRAR TEXT					 , FEC_DESDE TEXT			, FEC_HASTA	TEXT		 "
                    + ")")
        }

        fun createTableSvmCobSemanalVend(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_cob_semanal_vend "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
                    + " DESC_VENDEDOR TEXT			 		 , SEMANA TEXT				, TOT_CLIENTES TEXT		,"
                    + " CLIENT_VENTAS TEXT 				 	 , PORC_COBERTURA TEXT		, PERIODO TEXT 			,"
                    + " MONTO_A_COBRAR TEXT					 													 "
                    + ")")
        }

        fun createTableSvmLiqPremiosVend(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_liq_premios_vend "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " COD_EMPRESA       TEXT 	, COD_VENDEDOR 		TEXT	, "
                    + " VENDEDOR_PERSONA  TEXT  , DESC_VENDEDOR 	TEXT	, "
                    + " TIP_COM      	  TEXT	, COD_MARCA 		TEXT	, "
                    + " DESC_MARCA 		  TEXT	, MONTO_VENTA 		TEXT	, "
                    + " MONTO_META        TEXT  , PORC_COBERTURA 	TEXT	, "
                    + " PORC_LOG 		  TEXT	, MONTO_A_COBRAR 	TEXT	, "
                    + " MONTO_COBRAR   	  TEXT	, TOT_CLIENTES 		TEXT	, "
                    + " CLIENTES_VISITADOS TEXT)")
        }

        fun createTableSvmMetasPuntoPorCliente(): String{//
            return ("CREATE TABLE IF NOT EXISTS svm_metas_punto_por_cliente "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_VENDEDOR TEXT   ,"
                    + " DESC_VENDEDOR TEXT                   , CODIGO TEXT         , NOM_SUBCLIENTE TEXT ,"
                    + " CIUDAD TEXT                          , COD_SUPERVISOR TEXT , DESC_SUPERVISOR TEXT,"
                    + " LISTA_PRECIO TEXT                    , MAYOR_VENTA TEXT    , VENTA_3 TEXT        ,"
                    + " MIX_3 TEXT                           , VENTA_4 TEXT        , MIX_4 TEXT          ,"
                    + " METAS TEXT                           , TOT_SURTIDO TEXT    , MES_1 TEXT   		 ,"
                    + " MES_2 TEXT )")
        }

        fun createTableSvmEvolucionDiariaVenta(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_evolucion_diaria_venta"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_SUPERVISOR TEXT  ,"
                    + "  NOM_SUPERVISOR TEXT				 , COD_VENDEDOR TEXT  , DESC_VENDEDOR TEXT   ,"
                    + "  PEDIDO_2_ATRAS TEXT				 , PEDIDO_1_ATRAS TEXT, TOTAL_PEDIDOS TEXT   ,"
                    + "  TOTAL_FACT TEXT    				 , META_VENTA TEXT	  , META_LOGRADA TEXT    ,"
                    + "  PROY_VENTA TEXT	  				 , TOTAL_REBOTE TEXT  , TOTAL_DEVOLUCION TEXT,"
                    + "  CANT_CLIENTES TEXT 				 , CANT_POSIT TEXT	  , EF_VISITA TEXT		 ,"
                    + "  DIA_TRAB TEXT	  					 , PUNTAJE TEXT	      , SURTIDO_EF TEXT	)")
            return sql
        }

        fun createTableSvmDeudaPorClientes(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_deuda_cliente"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_CLIENTE TEXT    ,"
                    + " DESC_CLIENTE TEXT                    , COD_SUBCLIENTE TEXT , DESC_SUBCLIENTE TEXT,"
                    + " COD_VENDEDOR TEXT                    , DESC_VENDEDOR TEXT  , COD_SUPERVISOR TEXT ,"
                    + " DESC_SUPERVISOR TEXT                 , FEC_EMISION TEXT    , FEC_VENCIMIENTO TEXT,"
                    + " TIP_DOCUMENTO TEXT                   , NRO_DOCUMENTO TEXT  , DIAS_ATRAZO TEXT    ,"
                    + " ABREVIATURA TEXT                     , SALDO_CUOTA TEXT )")
            return sql
        }

        fun createTableSvmPrecioFijoConsulta(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_precio_fijo_consulta "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , COD_LISTA_PRECIO TEXT,"
                    + " DESC_LISTA_PRECIO TEXT               , COD_ARTICULO TEXT, DESC_ARTICULO TEXT   ,"
                    + " PREC_CAJA TEXT                       , PREC_UNID TEXT   , REFERENCIA TEXT      ,"
                    + " DECIMALES TEXT                       , SIGLAS TEXT)")
            return sql
        }

        fun createTableSvmMovimientoGestor(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_movimiento_gestor"
                    + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA 		TEXT   	, COD_VENDEDOR  TEXT		,"
                    + " NOM_VENDEDOR 		TEXT		 	 , COD_CLIENTE 		TEXT 	, DESC_CLIENTE 	TEXT 		,"
                    + " FEC_ASISTENCIA 		TEXT           	 , HORA_ENTRADA 	TEXT 	, HORA_SALIDA  	TEXT 		,"
                    + " MARCACION 			TEXT)")
            return sql
        }



        //NO SINCRONIZADOS

        private fun createTableVtNegociacionVentaCab(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS vt_negociacion_venta_cab"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT      , COD_SUCURSAL TEXT       , DESC_SUCURSAL TEXT  , "
                    + " FEC_COMPROBANTE TEXT                 , TIP_COMPROBANTE TEXT  , SER_COMPROBANTE TEXT    , NRO_COMPROBANTE TEXT, "
                    + " COD_CLIENTE TEXT                     , NOM_CLIENTE TEXT      , COD_SUBCLIENTE TEXT     , NOM_SUBCLIENTE TEXT ,"
                    + " COD_VENDEDOR TEXT                    , DESC_VENDEDOR TEXT    , COD_CONDICION_VENTA TEXT, DESC_COND_VENTA TEXT,"
                    + " COD_LISTA_PRECIO TEXT                , DESC_LISTA_PRECIO TEXT, COD_MONEDA TEXT         , DESC_MONEDA TEXT    ,"
                    + " IND_VENTA TEXT                       , TOT_COMPROBANTE NUMBER, PENDIENTE TEXT          , DECIMALES TEXT      ,"
                    + " FEC_ALTA TEXT                        , OBSERVACIONES TEXT    , IND_PRECIO TEXT         , IND_VAR TEXT        ,"
                    + " IND_BON TEXT                         , PORC_DESC_VAR TEXT    , DESCUENTO_VAR TEXT      , AUT_DESC TEXT       ,"
                    + " COD_PERFIL TEXT						 , DESC_PERFIL TEXT );")
            return sql
        }

        private fun createTableVtNegociacionVentaDet(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS vt_negociacion_venta_det"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT      , TIP_COMPROBANTE TEXT, SER_COMPROBANTE TEXT      ,"
                    + " NRO_COMPROBANTE TEXT                 , ORDEN TEXT            , COD_ARTICULO TEXT   , PRECIO_UNITARIO_C_IVA TEXT,"
                    + " PRECIO_LISTA TEXT                    , COD_UNIDAD_MEDIDA TEXT, CANTIDAD NUMBER     , MONTO_TOTAL_CONIVA TEXT   ,"
                    + " DESC_UNIDAD_MEDIDA TEXT              , DESC_ARTICULO TEXT    , IND_BLOQUEADO TEXT  , IND_AUTORIZACION TEXT     ,"
                    + " IND_PRECIO TEXT                      , IND_VAR TEXT          , IND_BON TEXT );")
            return sql
        }

        fun createTableSvmMetasPuntoPorLinea(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	 , COD_VENDEDOR TEXT   ,"
                    + " DESC_VENDEDOR TEXT                   , COD_LINEA TEXT      	 , DESC_LINEA TEXT     ,"
                    + " ABREV TEXT                           , DESC_GTE_MARKETIN TEXT, DESC_MODULO TEXT    ,"
                    + " COD_SUPERVISOR TEXT 				 , DESC_SUPERVISOR TEXT  , MAYOR_VENTA TEXT    ,"
                    + " VENTA_3 TEXT        				 , VENTA_4 TEXT          , METAS TEXT          ,"
                    + " MES_1 TEXT          				 , MES_2 TEXT 		 	 , NRO_ORD_MAG TEXT    ,"
                    + " ORD_GTE_MARK TEXT   				 , ORD_CATEGORIA TEXT)")
            return sql
        }

        private fun createTableSvmMetasPuntoPorLineaSup(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea_sup "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_LINEA TEXT        ,"
                    + " DESC_LINEA TEXT                     , ABREVIATURA TEXT    , DESC_GTE_MARKETIN TEXT,"
                    + " DESC_MODULO TEXT					, COD_SUP_TEL TEXT 	  , DESC_SUPERVISOR TEXT  ,"
                    + " MAYOR_VENTA TEXT					, VENTA_3 TEXT 		  , VENTA_4 TEXT          ,"
                    + " METAS TEXT      					, NRO_ORD_MAG TEXT    , ORD_GTE_MARK TEXT     ,"
                    + " ORD_CATEGORIA TEXT 					, MES_1 TEXT      	  , MES_2 TEXT )")
            return sql
        }

        fun createTableSvmVendedorPedidoVenta(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_vendedor_pedido_venta " +
                     " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_SUPERVISOR TEXT, IND_PALM TEXT,"    +
                     " TIPO            TEXT                 , SERIE TEXT      , NUMERO TEXT        , FECHA TEXT   ,"    +
                     " ULTIMA_SINCRO   TEXT                 , VERSION TEXT	   , ULTIMA_VEZ TEXT    , RANGO TEXT   ,"   +
                     " TIPO_SUPERVISOR TEXT				 , MIN_FOTO TEXT   , MAX_FOTO TEXT		, IND_FOTO TEXT,"       +
                     " FEC_CARGA_RUTEO TEXT				 , MAX_DESC TEXT   , MAX_DESC_VAR TEXT  , PER_VENDER TEXT,"     +
                     " INT_MARCACION   TEXT)")
            return sql
        }

        fun createTableSvmVendedorPedido(): String{
            sql = "CREATE TABLE IF NOT EXISTS svm_vendedor_pedido" +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT       , COD_VENDEDOR TEXT         ," +
                    " IND_PALM TEXT                        , TIPO TEXT              , SERIE TEXT                ," +
                    " NUMERO TEXT                          , FECHA TEXT             , ULTIMA_SINCRO TEXT        ," +
                    " VERSION TEXT                         , COD_CLI_VEND TEXT      , COD_PERSONA TEXT          ," +
                    " RANGO TEXT						   , PER_VENDER TEXT	    , PER_PRESENCIAL TEXT       ," +
                    " HORA TEXT							   , MIN_FOTOS TEXT	        , MAX_FOTOS TEXT            ," +
                    " VERSION_SISTEMA TEXT 				   , PER_ASISTENCIA TEXT    , FRECUENCIA_RASTREO TEXT   ," +
                    " HORA_INICIO TEXT					   , HORA_FIN TEXT          , TIEMPO_ASISTENCIA TEXT    ," +
                    " ULTIMA_VEZ TEXT 					   , IND_FOTO TEXT          , MIN_VENTA TEXT);"
            return sql
        }

        private fun createTableSvmRebotesPorVendedores(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_rebote_por_vendedor "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , CODIGO TEXT         ,"
                    + " COD_VENDEDOR TEXT                   , NOM_VENDEDOR TEXT  , COD_SUPERVISOR TEXT ,"
                    + " DESC_SUPERVISOR TEXT                , DESC_PENALIDAD TEXT, MONTO_TOTAL TEXT    ,"
                    + " FECHA TEXT)")
            return sql
        }

        fun createTableSvmMetasPuntoPorLineaEmpresa(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea_empresa "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , METAS TEXT     ,"
                    + " VENTA TEXT                          , CANT_PUNTO TEXT    , PUNT_ACUM TEXT);")
            return sql
        }

        private fun createTableSvmSeguimientoPremioSup(): String{//
            sql = ("CREATE TABLE IF NOT EXISTS svm_seguimiento_premio_sup "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT  , COD_SUPERVISOR TEXT,"
                    + " DESC_SUPERVISOR TEXT				 , FECHA TEXT		 , VENTAS TEXT	      ,"
                    + " METAS TEXT		  					 , PUNT_VOLUM TEXT	 , PREMIO_VOLUM TEXT  ,"
                    + " PUNT_ACUM_LIN TEXT 					 , PREMIO_LINEA TEXT , PUNT_ACUM_CTE TEXT ,"
                    + " PREMIO_CLIENTE TEXT					 , PUN_COBERT TEXT	 , PREMIO_COBERT TEXT ,"
                    + " PUN_ACUM_RUTEO TEXT					 , PREMIO_RUTEO TEXT )")
            return sql
        }

        fun createTableSvmControlGeneralSup(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_control_general_sup "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
                    + " NRO_ORDEN TEXT					    , METAS TEXT            , VENTAS TEXT		    ,"
                    + " PORC_VOL_VENTAS TEXT				, PUNT_ACUM_LINEAS TEXT , PUNT_CLI_ACUM TEXT    ,"
                    + " CANT_CLI_POS TEXT					, MONTO_REBOTE TEXT	    , MONTO_DEV TEXT	    ,"
                    + " CANT_COB TEXT					    , CANT_RUTEO TEXT	    , VENTAS_ESTR TEXT	    ,"
                    + " METAS_ESTR TEXT						, CATASTRO TEXT			, PORC_MONTO_REBOTE TEXT,"
                    + " PORC_MONTO_DEV TEXT)")
            return sql
        }

        fun createTableSvmMotivoAnalisisVendedor(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_motivo_analisis_vendedor "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_MOTIVO TEXT, DESCRIPCION TEXT,"
                    + "	ESTADO TEXT							, PUNTUACION TEXT, COD_VENDEDOR TEXT,"
                    + " NRO_ORDEN TEXT						, COD_GRUPO TEXT )")
            return sql
        }

        private fun createTableSvmAnalisisCab(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_analisis_cab "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT, DESC_SUPERVISOR TEXT, COD_VENDEDOR TEXT  ,"
                    + "	DESC_VENDEDOR TEXT   				, DESC_ZONA TEXT	 , HORA_LLEGADA TEXT   , HORA_SALIDA TEXT	,"
                    + " FECHA_VISITA TEXT  					, COD_CLIENTE TEXT   , COD_SUBCLIENTE TEXT , DESC_SUBCLIENTE TEXT,"
                    + " ESTADO TEXT                         , COMENTARIO TEXT )")
            return sql
        }

        private fun createTableSvmAnalisisDet(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_analisis_det "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, ID_CAB TEXT, COD_MOTIVO TEXT, RESPUESTA TEXT )")
            return sql
        }

        private fun createTableSvmAnalisisVendedorDet(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_analisis_vendedor_det "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_VENDEDOR TEXT, DESC_VENDEDOR TEXT, FECHA TEXT,"
                    + " COD_MOTIVO TEXT						, RESPUESTA TEXT   , COMENTARIO TEXT   )")
            return sql
        }

        private fun createTableVtMarcacionUbicacion(): String {
            sql = ("CREATE TABLE IF NOT EXISTS vt_marcacion_ubicacion"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , FECHA TEXT   , COD_PROMOTOR TEXT, "
                    + "  COD_CLIENTE TEXT   				 , COD_SUBCLIENTE TEXT, TIPO TEXT  	 , ESTADO TEXT      , "
                    + "  LATITUD TEXT    					 , LONGITUD TEXT	  , OBSERVACION TEXT );")
            return sql
        }

        private fun createTableVtMarcacionVisita(): String {
            sql = ("CREATE TABLE IF NOT EXISTS vt_marcacion_visita "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_SUCURSAL TEXT, "
                    + " COD_CLIENTE TEXT					, COD_SUBCLIENTE TEXT, COD_VENDEDOR TEXT, "
                    + " COD_MOTIVO TEXT  					, OBSERVACION TEXT   , FECHA TEXT	    , "
                    + " LATITUD TEXT	  					, LONGITUD TEXT		 , ESTADO TEXT	    ,"
                    + " HORA_ALTA TEXT		)")
            return sql
        }

        fun createTableSvmCostoVentaArticulo(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_costo_venta_articulo "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , COD_SUCURSAL TEXT    ,"
                    + " DESC_SUCURSAL TEXT                   , COD_ARTICULO TEXT, REFERENCIA TEXT      ,"
                    + " COSTO_VENTA TEXT   )")
            return sql
        }

        fun createTableSvmSurtidoEficiente(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_surtido_eficiente "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
                    + " DESC_VENDEDOR TEXT			 		 , COD_CLIENTE TEXT			, COD_SUBCLIENTE TEXT	,"
                    + " TIP_CLIENTE TEXT 				 	 , COD_ARTICULO TEXT		, TIP_SURTIDO TEXT 		 "
                    + ")")
            return sql
        }

        fun createTableSvmSurtidoEficiente(nombreTabla:String): String {
            sql = ("CREATE TABLE IF NOT EXISTS $nombreTabla "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
                    + " DESC_VENDEDOR TEXT			 		 , COD_CLIENTE TEXT			, COD_SUBCLIENTE TEXT	,"
                    + " TIP_CLIENTE TEXT 				 	 , COD_ARTICULO TEXT		, TIP_SURTIDO TEXT 		 "
                    + ")")
            return sql
        }

        fun createTableSvmPremiosSupervisores(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_premios_supervisores "
                    + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA 		TEXT   	, COD_MARCA	  	TEXT		,"
                    + " DESC_MARCA 			TEXT		 	 , FEC_COMPROBANTE	TEXT 	, VENTA			TEXT 		,"
                    + " META		 		TEXT           	 , CANT_PUNTO 		TEXT 	, PORC_CUMP  	TEXT 		,"
                    + " PUNT_ACUM 			TEXT)")
            return sql
        }

        fun createTableSvmCategoria(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_categoria"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT		, COD_CATEGORIA TEXT, DESC_CATEGORIA TEXT,"
                    + "	 COD_SEGMENTO TEXT					 , DESC_SEGMENTO TEXT	, ORDEN TEXT		, TIPO TEXT		 	 , "
                    + "  COD_TIP_CLIENTE TEXT)")
            return sql
        }

        fun createTableSvmRetornoComentario(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_retorno_comentario"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , FEC_ENTRADA TEXT   , FEC_SALIDA TEXT   , "
                    + "  COD_PROMOTOR TEXT					 , COD_CLIENTE TEXT   , COD_SUBCLIENTE TEXT, COD_CATEGORIA TEXT, "
                    + "  COD_SEGMENTO TEXT					 , TIPO TEXT 		  , PUNTUACION TEXT    , COMENTARIO_SUPERVISOR TEXT);")
            return sql
        }

        fun createTableSvmPeriodoFoto(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_periodo_foto"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_TIP_CLIENTE TEXT, FEC_INICIO TEXT,"
                    + "  FEC_FIN TEXT);")
            return sql
        }

        fun createTableSvmClienteListPrec(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cliente_list_prec"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT, COD_SUBCLIENTE TEXT,"
                    + "COD_VENDEDOR TEXT                     , COD_LISTA_PRECIO TEXT, DESC_LISTA TEXT , COD_MONEDA TEXT    ,"
                    + "DECIMALES TEXT                        , IND_DEFECTO TEXT);")
            return sql
        }

        fun createTableSvmArticulosPrecios(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_articulos_precios"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA  TEXT     , COD_VENDEDOR TEXT    , MODULO TEXT      ,"
                    + " COD_LISTA_PRECIO TEXT                , COD_ARTICULO TEXT     , DESC_ARTICULO TEXT   , PREC_UNID NUMBER ,"
                    + " PREC_CAJA NUMBER                     , COD_UNIDAD_MEDIDA TEXT, PORC_IVA NUMBER      , REFERENCIA TEXT  ,"
                    + " MULT NUMBER                          , DIV NUMBER            , IND_LIM_VENTA NUMBER , COD_LINEA TEXT   ,"
                    + " COD_FAMILIA TEXT                     , COD_BARRA TEXT        , PORC_DESCUENTO NUMBER, CANT_MINIMA TEXT ,"
                    + " IND_PROMO_ACT TEXT DEFAULT 'N'		/* , TIP_SURTIDO TEXT DEFAULT 'N'*/);"
                    + " CREATE INDEX IF NOT EXISTS ind_art_prec on svm_articulos_precios " +
                      "(DESC_ARTICULO, COD_VENDEDOR, COD_LISTA_PRECIO, COD_ARTICULO)")
            return sql
        }

        fun createTableSvmArticulosPrecios(tabla:String): String {
            sql = ("CREATE TABLE IF NOT EXISTS $tabla "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA  TEXT     , COD_VENDEDOR TEXT    , MODULO TEXT      ,"
                    + " COD_LISTA_PRECIO TEXT                , COD_ARTICULO TEXT     , DESC_ARTICULO TEXT   , PREC_UNID NUMBER ,"
                    + " PREC_CAJA NUMBER                     , COD_UNIDAD_MEDIDA TEXT, PORC_IVA NUMBER      , REFERENCIA TEXT  ,"
                    + " MULT NUMBER                          , DIV NUMBER            , IND_LIM_VENTA NUMBER , COD_LINEA TEXT   ,"
                    + " COD_FAMILIA TEXT                     , COD_BARRA TEXT        , PORC_DESCUENTO NUMBER, CANT_MINIMA TEXT ,"
                    + " IND_PROMO_ACT TEXT DEFAULT 'N'		/* , TIP_SURTIDO TEXT DEFAULT 'N'*/);"
                    + " CREATE INDEX IF NOT EXISTS ind_art_prec on $tabla " +
                    "(DESC_ARTICULO, COD_VENDEDOR, COD_LISTA_PRECIO, COD_ARTICULO);")
            return sql
        }

        fun createTableSvmStArticulos(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_st_articulos"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA  TEXT    , COD_ARTICULO TEXT, DESC_ARTICULO TEXT,"
                    + " COD_UNIDAD_REL TEXT                  , REFERENCIA TEXT      , MULT NUMBER      , DIV  NUMBER       ,"
                    + " COD_IVA TEXT                         , PORC_IVA TEXT        , COD_LINEA TEXT   , COD_FAMILIA TEXT  ,"
                    + " IND_BASICO TEXT                      , CANT_MINIMA NUMBER   , CONT_EXIST TEXT);" +
                    "CREATE INDEX IF NOT EXISTS ind_art_prec on svm_st_articulos (COD_ARTICULO,DESC_ARTICULO,COD_EMPRESA,COD_LINEA,COD_UNIDAD_REL,CANT_MINIMA);")
            return sql
        }

        fun createTableSvmCondicionVenta(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_condicion_venta"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_CONDICION_VENTA TEXT,"
                    + " DESCRIPCION TEXT                     , TIPO_CONDICION TEXT, ABREVIATURA TEXT        ,"
                    + " DIAS_INICIAL TEXT                    , PORC_DESC TEXT);")
            return sql
        }

        fun createTableSvmClienteVendedor(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cliente_vendedor "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT , COD_SUBCLIENTE TEXT,"
                    + " DESC_SUBCLIENTE TEXT                 , DESC_CLIENTE TEXT    , COD_VENDEDOR TEXT, TELEFONO TEXT      ,"
                    + " LONGI REAL                           , LATI REAL            , TIP_CAUSAL TEXT  , desc_ciudad TEXT   ,"
                    + " RUC TEXT                             , DESC_ZONA TEXT       , DESC_REGION TEXT , DIRECCION TEXT     ,"
                    + " COD_SUCURSAL TEXT                    , TIP_CLIENTE TEXT     , DESC_TIPO TEXT   , COD_CONDICION TEXT ,"
                    + " DESC_CONDICION TEXT                  , LIMITE_CREDITO NUMBER, TOT_DEUDA NUMBER , SALDO NUMBER       ,"
                    + " FEC_VISITA TEXT                      , IND_VISITA text      , COD_ZONA         , TIPO_CONDICION TEXT,"
                    + " IND_DIRECTA TEXT                     , IND_ATRAZO TEXT      , FRECUENCIA TEXT  , DIAS_INICIAL TEXT  ,"
                    + " COMENTARIO TEXT						 , IND_ESP TEXT 		, CATEGORIA TEXT   , TELEFONO2 TEXT		,"
                    + " LATITUD TEXT						 , LONGITUD TEXT		, CERCA_DE TEXT	   , IND_CADUCADO TEXT  ,"
                    + " IND_VITRINA TEXT 					 , ESTADO TEXT 			, FOTO_FACHADA TEXT, TIEMPO_MIN TEXT    ,"
                    + " TIEMPO_MAX TEXT 					 , SOL_TARG TEXT );"
                    + "CREATE INDEX if not exists cliente_vendedor on svm_cliente_vendedor(COD_VENDEDOR, COD_CLIENTE, DESC_CLIENTE, DESC_SUBCLIENTE, DESC_CIUDAD, FEC_VISITA);"
                    + "CREATE INDEX if not exists cliente_vendedores on svm_cliente_vendedor(COD_VENDEDOR);")
            return sql
        }

        fun createTableSvmUltimaVentaCliente(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_ultima_venta_cliente "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT ,"
                    + " FEC_COMPROBANTE TEXT                , COD_ARTICULO TEXT   , DESC_ARTICULO TEXT  ,"
                    + " PRECIO_UNITARIO TEXT                , CANTIDAD NUMBER     , DESC_UNIDAD TEXT    ,"
                    + " SIGLAS TEXT                         , DECIMALES TEXT      , COD_VENDEDOR TEXT   ,"
                    + " DESC_VENDEDOR TEXT                  , COD_EMPRESA TEXT); ")
            return sql
        }

        fun createTableSvmPromArticulosTarCred(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_prom_articulos_tar_cred "
                    + "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , NRO_PROMOCION TEXT,"
                    + "  COD_CONDICION_VENTA TEXT			 , COD_ARTICULO TEXT, DESC_ARTICULO TEXT,"
                    + "  PORC_DESCUENTO TEXT				 , DESCRIPCION TEXT , COMENTARIO TEXT )")
            return sql
        }

        fun createTableSvmCondVentaVendedor(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cond_venta_vendedor "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_LISTA_PRECIO TEXT, COD_CONDICION_VENTA TEXT,"
                    + " COD_CLIENTE TEXT					 , COD_SUBCLIENTE TEXT  , COD_EMPRESA         TEXT,"
                    + " COD_VENDEDOR TEXT)")
            return sql
        }

        fun createTableSvmMotivoNoVenta(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_motivo_no_venta"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_MOTIVO TEXT,"
                    + "	 DESC_MOTIVO TEXT 					 , CIERRA TEXT)")
            return sql
        }

        fun createTableSvmPromocionesPerfilMerc(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_promociones_perfilMerc"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	 , TIP_CLIENTE TEXT		,"
                    + " NRO_PROMOCION TEXT					 , DESCRIPCION TEXT	 , COMENTARIO TEXT		,"
                    + " FEC_INI_PROMO TEXT					 , FEC_FIN_PROMO TEXT, COD_ARTICULO TEXT )")
            return sql
        }

        fun createTableSvmPromocionesArtCab(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_promociones_art_cab "
                    + "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
                    + "  COD_UNID_NEGOCIO TEXT				 , TIP_CLIENTE TEXT			, DESCRIPCION TEXT	  	,"
                    + "  COMENTARIO TEXT					 , COD_CONDICION_VENTA TEXT , FEC_VIGENCIA TEXT   	,"
                    + "  COD_ARTICULO TEXT					 , DESC_ARTICULO TEXT		, COD_UNIDAD_MEDIDA TEXT,"
                    + "  REFERENCIA TEXT					 , IND_TIPO TEXT			,"
                    + "  CANT_VENTA TEXT 					 , MULT TEXT                , COD_LISTA_PRECIO TEXT ,"
                    + "  COD_VENDEDOR TEXT					 , IND_COMBO TEXT			, IND_PROM TEXT			,"
                    + "  IND_ART TEXT)")
            return sql
        }

        fun createTableSvmPromocionesArtCab(tabla:String): String {
            sql = ("CREATE TABLE IF NOT EXISTS $tabla "
                    + "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
                    + "  COD_UNID_NEGOCIO TEXT				 , TIP_CLIENTE TEXT			, DESCRIPCION TEXT	  	,"
                    + "  COMENTARIO TEXT					 , COD_CONDICION_VENTA TEXT , FEC_VIGENCIA TEXT   	,"
                    + "  COD_ARTICULO TEXT					 , DESC_ARTICULO TEXT		, COD_UNIDAD_MEDIDA TEXT,"
                    + "  REFERENCIA TEXT					 , IND_TIPO TEXT			,"
                    + "  CANT_VENTA TEXT 					 , MULT TEXT                , COD_LISTA_PRECIO TEXT ,"
                    + "  COD_VENDEDOR TEXT					 , IND_COMBO TEXT			, IND_PROM TEXT			,"
                    + "  IND_ART TEXT)")
            return sql
        }

        fun createTableSvmPromocionesArtDet(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_promociones_art_det "
                    + "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
                    + "  COD_UNID_NEGOCIO TEXT				 , NRO_GRUPO TEXT			, CANT_DESDE TEXT	  	,"
                    + "  CANT_HASTA TEXT					 , COD_ARTICULO TEXT 		, COD_UNIDAD_MEDIDA TEXT,"
                    + "  DESC_UND_MEDIDA TEXT			 	 , IND_TIPO TEXT			, DESCUENTO TEXT		,"
                    + "  PRECIO_GS TEXT					 	 , PRECIO_RS TEXT		 	, MULT TEXT				,"
                    + "  IND_MULTIPLE TEXT                   , COD_VENDEDOR TEXT)")
            return sql
        }

        fun createTableSvmPromocionesArtDet(tabla:String): String {
            sql = ("CREATE TABLE IF NOT EXISTS $tabla "
                    + "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
                    + "  COD_UNID_NEGOCIO TEXT				 , NRO_GRUPO TEXT			, CANT_DESDE TEXT	  	,"
                    + "  CANT_HASTA TEXT					 , COD_ARTICULO TEXT 		, COD_UNIDAD_MEDIDA TEXT,"
                    + "  DESC_UND_MEDIDA TEXT			 	 , IND_TIPO TEXT			, DESCUENTO TEXT		,"
                    + "  PRECIO_GS TEXT					 	 , PRECIO_RS TEXT		 	, MULT TEXT				,"
                    + "  IND_MULTIPLE TEXT                   , COD_VENDEDOR TEXT)")
            return sql
        }

        fun createTableSvmProduccionSemanalVend(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_produccion_semanal_vend "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA    TEXT  , COD_VENDEDOR   TEXT    , "
                    + " COD_PERSONA    TEXT  , DESC_VENDEDOR  TEXT    , "
                    + " CANTIDAD       NUMBER, "
                    + " SEMANA         TEXT  , FECHA          TEXT    , "
                    + " MONTO_VIATICO  NUMBER, MONTO_TOTAL    NUMBER  , "
                    + " PERIODO TEXT )")
            return sql
        }

        fun createTableSvmCoberturaMarcaSup(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cobertura_marca_sup "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, PORC_DESDE TEXT,"
                    + " PORC_HASTA TEXT						, PORC_COM TEXT		, LINHA TEXT		)")
            return sql
        }

        fun createTableSvmCoberturaVisVendedores(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cobertura_vis_vendedores "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_GRUPO TEXT	, COD_VENDEDOR TEXT, DESC_VENDEDOR TEXT, "
                    + " PORC_DESDE TEXT 					, PORC_HASTA TEXT	, PORC_COM TEXT		)")
            return sql
        }

        fun createTableSvmMotivosSdDev(): String {
            //estado(S=enviado,N=pendiente)
            sql = ("CREATE TABLE IF NOT EXISTS svm_motivos_sd_dev"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT	, COD_EMPRESA TEXT		, COD_MOTIVO TEXT	, "
                    + " DESC_MOTIVO TEXT) ")
            return sql
        }

        fun createTableSvmClientePromVendedor(): String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_cliente_prom_vendedor "
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT , COD_SUBCLIENTE TEXT,"
                    + " DESC_SUBCLIENTE TEXT                 , DESC_CLIENTE TEXT    , COD_VENDEDOR TEXT, TELEFONO TEXT      ,"
                    + " LONGI REAL                           , LATI REAL            , TIP_CAUSAL TEXT  , desc_ciudad TEXT   ,"
                    + " RUC TEXT                             , DESC_ZONA TEXT       , DESC_REGION TEXT , DIRECCION TEXT     ,"
                    + " COD_SUCURSAL TEXT                    , TIP_CLIENTE TEXT     , DESC_TIPO TEXT   , COD_CONDICION TEXT ,"
                    + " DESC_CONDICION TEXT                  , LIMITE_CREDITO NUMBER, TOT_DEUDA NUMBER , SALDO NUMBER       ,"
                    + " FEC_VISITA TEXT                      , IND_VISITA text      , COD_ZONA         , TIPO_CONDICION TEXT,"
                    + " IND_DIRECTA TEXT                     , IND_ATRAZO TEXT      , FRECUENCIA TEXT  , DIAS_INICIAL TEXT  ,"
                    + " COMENTARIO TEXT						 , IND_ESP TEXT 		, CATEGORIA TEXT   , TELEFONO2 TEXT		,"
                    + " LATITUD TEXT						 , LONGITUD TEXT		, CERCA_DE TEXT	   , IND_CADUCADO TEXT  ,"
                    + " IND_VITRINA TEXT 					 , ESTADO TEXT 			, FOTO_FACHADA TEXT, TIEMPO_MIN TEXT    ,"
                    + " TIEMPO_MAX TEXT );"
                    + "CREATE INDEX if not exists cliente_vendedor on svm_cliente_prom_vendedor(COD_VENDEDOR, COD_CLIENTE, DESC_CLIENTE, DESC_SUBCLIENTE, DESC_CIUDAD, FEC_VISITA)")
            return sql
        }

        private fun createTableSvmModificaCatastro() : String {
            sql = "CREATE TABLE IF NOT EXISTS svm_modifica_catastro" +
             " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_CLIENTE TEXT		," +
             " COD_SUBCLIENTE TEXT					 , TELEFONO1 TEXT	, TELEFONO2 TEXT		," +
             " DIRECCION TEXT						 , CERCA_DE TEXT	, LATITUD TEXT			," +
             " LONGITUD TEXT						 , ESTADO TEXT		, FECHA TEXT		    ," +
             " FOTO_FACHADA BLOB		 			 , TIPO TEXT        , COD_VENDEDOR TEXT     ," +
             " COD_SUPERVISOR TEXT)"

            return sql
        }

        private fun createTableSvmRuteoSemanal() : String {
            sql = "CREATE TABLE IF NOT EXISTS svm_ruteo_semanal " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_SUPERVISOR TEXT, " +
                    " COD_VENDEDOR TEXT					, FECHA TEXT      , COD_CLIENTE    TEXT, " +
                    " COD_SUBCLIENTE TEXT					, ESTADO TEXT)"

            return sql
        }

        private fun createTableSvmModificaCatastroVenta() : String{
            sql = "CREATE TABLE IF NOT EXISTS svm_modifica_catastro_venta " +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_CLIENTE TEXT		," +
                    " COD_SUBCLIENTE TEXT					 , TELEFONO1 TEXT	, TELEFONO2 TEXT		," +
                    " DIRECCION TEXT						 , CERCA_DE TEXT	, LATITUD TEXT			," +
                    " LONGITUD TEXT						     , ESTADO TEXT		, FECHA TEXT		    ," +
                    " FOTO_FACHADA BLOB		 			     , TIPO TEXT)"

            return sql
        }

        private fun createTablePedidoCab() : String {
            sql = ("CREATE TABLE IF NOT EXISTS vt_pedidos_cab "
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT  , COD_SUBCLIENTE TEXT      ,"
            + " COD_VENDEDOR TEXT                    , COD_LISTA_PRECIO TEXT, FECHA TEXT        , FECHA_INT TEXT           ,"
            + " NUMERO NUMBER                        , NRO_ORDEN_COMPRA TEXT, COD_CONDICION TEXT, TIP_CAMBIO               ,"
            + " TOT_COMPROBANTE TEXT                 , ESTADO TEXT          , COD_MONEDA TEXT   , DECIMALES TEXT           ,"
            + " COMENTARIO TEXT                      , PORC_DESC_VAR TEXT   , PORC_DESC_FIN TEXT, DESCUENTO_VAR TEXT       ,"
            + " DESCUENTO_FIN TEXT                   , TOT_DESCUENTO TEXT   , DESC_CLIENTE TEXT , DIAS_INICIAL             ,"
            + " COD_CONDICION_VENTA TEXT             , NRO_AUTORIZACION TEXT, FEC_ALTA TEXT     , LATITUD TEXT		       ,"
            + " LONGITUD TEXT						 , IND_PRESENCIAL TEXT  , HORA_ALTA TEXT    , NRO_AUTORIZACION_DESC TEXT,"
            + " HORA_REGISTRO DATETIME DEFAULT (datetime('now','localtime')))")

            return sql
        }

        fun renameTablePedidoCab() : String {
            sql = "ALTER TABLE 'vt_pedidos_cab' RENAME TO 'vt_pedidos_prov';"

            return sql
        }

        fun fillTablePedidoCab() : String {
            sql = ("insert into vt_pedidos_cab "
            + " (id					, COD_EMPRESA 		, COD_CLIENTE	, COD_SUBCLIENTE		,"
            + " COD_VENDEDOR		, COD_LISTA_PRECIO	, FECHA			, FECHA_INT				,"
            + " NUMERO				, NRO_ORDEN_COMPRA	, COD_CONDICION	, TIP_CAMBIO			,"
            + " TOT_COMPROBANTE		, ESTADO			, COD_MONEDA	, DECIMALES				,"
            + " COMENTARIO			, porc_desc_var		, porc_desc_fin	, descuento_var			,"
            + " descuento_fin		, tot_descuento		, DESC_CLIENTE	, DIAS_INICIAL			,"
            + " COD_CONDICION_VENTA	, NRO_AUTORIZACION	, FEC_ALTA		, LATITUD				,"
            + " LONGITUD			, IND_PRESENCIAL	, HORA_ALTA		, NRO_AUTORIZACION_DESC)"
            + " "
            + " select id			, COD_EMPRESA 		, COD_CLIENTE	, COD_SUBCLIENTE		,"
            + " COD_VENDEDOR		, COD_LISTA_PRECIO	, FECHA			, FECHA_INT				,"
            + " NUMERO				, NRO_ORDEN_COMPRA	, COD_CONDICION	, TIP_CAMBIO			,"
            + " TOT_COMPROBANTE		, ESTADO			, COD_MONEDA	, DECIMALES				,"
            + " COMENTARIO			, porc_desc_var		, porc_desc_fin	, descuento_var			,"
            + " descuento_fin		, tot_descuento		, DESC_CLIENTE	, DIAS_INICIAL			,"
            + " COD_CONDICION_VENTA	, NRO_AUTORIZACION	, FEC_ALTA		, LATITUD				,"
            + " LONGITUD			, IND_PRESENCIAL	, HORA_ALTA		, NRO_AUTORIZACION_DESC"
            + " from vt_pedidos_prov;")
            return sql
        }

        private fun createTablePedidoDet():String{
            sql = ("CREATE TABLE IF NOT EXISTS vt_pedidos_det"
            +" (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT       , NUMERO NUMBER               , COD_ARTICULO TEXT        ,"
            +" CANTIDAD NUMBER                      , PRECIO_UNITARIO NUMBER , MONTO_TOTAL NUMBER          , TOTAL_IVA NUMBER         ,"
            +" ORDEN NUMBER                         , COD_UNIDAD_MEDIDA TEXT , COD_IVA TEXT                , PORC_IVA NUMBER          ,"
            +" COD_SUCURSAL TEXT                    , COD_ZONA TEXT          , PRECIO_UNITARIO_C_IVA NUMBER, MONTO_TOTAL_CONIVA NUMBER,"
            +" PORC_DESC_VAR NUMBER                 , DESCUENTO_VAR NUMBER   , PORC_DESC_FIN NUMBER        , DESCUENTO_FIN NUMBER     ,"
            +" PRECIO_LISTA NUMBER                  , MULT NUMBER            , TIP_COMPROBANTE_REF TEXT    , SER_COMPROBANTE_REF TEXT ,"
            +" NRO_COMPROBANTE_REF TEXT             , ORDEN_REF TEXT         , IND_SISTEMA TEXT            , IND_TRANSLADO TEXT       ,"
            +" IND_BLOQUEADO TEXT                   , IND_DEPOSITO TEXT      , EXISTENCIA_ACTUAL TEXT 	    , PROMOCION NUMBER		   ,"
            +" TIP_PROMOCION TEXT					 , NRO_AUTORIZACION TEXT  , MONTO_DESC_TC TEXT			, NRO_PROMOCION TEXT       ,"
            +" COD_VENDEDOR  TEXT);")

            return sql
        }

        private fun createTableSvmSolicitudDevDet() : String {
            //estado(S=enviado,N=pendiente)
            sql = ("CREATE TABLE IF NOT EXISTS svm_solicitud_dev_det"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT	, COD_EMPRESA TEXT	 	, TIP_PLANILLA TEXT	, "
                    + " NRO_PLANILLA TEXT						, SER_COMP TEXT			, TIP_COMP TEXT		, "
                    + " NRO_COMP TEXT							, COD_VENDEDOR TEXT		, COD_CLIENTE		, "
                    + " COD_SUBCLIENTE							, NRO_REGISTRO_REF TEXT	, NRO_ORDEN TEXT	, "
                    + " COD_ARTICULO TEXT						, DESC_ARTICULO TEXT	, CANTIDAD TEXT		, "
                    + " PAGO TEXT								, MONTO TEXT			, TOTAL TEXT		, "
                    + " COD_UNIDAD_REL TEXT						, REFERENCIA TEXT 		, IND_BASICO TEXT	, "
                    + " COD_PENALIDAD TEXT						, GRABADO_CAB TEXT		, EST_ENVIO TEXT 	, "
                    + " FECHA TEXT	)")

            return sql
        }

	    private fun createTableSvmSolicitudDevCab() : String {
            sql = ("CREATE TABLE IF NOT EXISTS svm_solicitud_dev_cab"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_VENDEDOR TEXT 	,"
                    + " NRO_COMPROBANTE TEXT				 , COD_CLIENTE TEXT    	, COD_SUBCLIENTE TEXT 	,"
                    + " NOM_CLIENTE TEXT                     , TOT_COMPROBANTE TEXT	, NRO_PLANILLA TEXT   	,"
                    + " SIGLAS TEXT         				 , OBSERVACIONES TEXT	, EST_ENVIO TEXT 		,"
                    + " NRO_REGISTRO_REF TEXT 				 , FECHA TEXT )")

            return sql
        }


        private fun createTableCcClientesBajaProv():String{
            return ("CREATE TABLE IF NOT EXISTS cc_clientes_baja_prov (" +
                    "       id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "       COD_EMPRESA VARCHAR(5)," +
                    "       COD_VENDEDOR VARCHAR(10)," +
                    "       DESC_VENDEDOR VARCHAR(50)," +
                    "       COD_CLIENTE VARCHAR(20)," +
                    "       COD_SUBCLIENTE VARCHAR(20)," +
                    "       DESC_CLIENTE VARCHAR(100)," +
                    "       DESC_SUBCLIENTE VARCHAR(100)," +
                    "       COMENTARIO VARCHAR(300)," +
                    "       ESTADO CHARACTER DEFAULT 'P'," +
                    "       FOTO_FACHADA BLOB       " +
                    ");")
        }



        fun listaSQLCreateTable(): Vector<String> {
            val lista : Vector<String> = Vector()
            lista.add(0, createTableVtNegociacionVentaCab())
            lista.add(1, createTableVtNegociacionVentaDet())
            lista.add(2, createTableSvmControlVentaDiariaCab())
            lista.add(3, createTableSvmControlVentaDiariaDet())
            lista.add(4, createTableSvmMetasPuntoPorCliente())
            lista.add(5, createTableSvmMetasPuntoPorLinea())
            lista.add(6, createTableSvmMetasPuntoPorLineaSup())
            lista.add(7, createTableSvmEvolucionDiariaVenta())
            lista.add(8, createTableSvmPrecioFijoConsulta())
            lista.add(9, createTableSvmVendedorPedido())
            lista.add(10, createTableSvmDeudaPorClientes())
            lista.add(11, createTableSvmRebotesPorVendedores())
            lista.add(12, createTableSvmMetasPuntoPorLineaSup())
            lista.add(13, createTableSvmMetasPuntoPorLineaEmpresa())
            lista.add(14, createTableSvmPositivacionCliente())
            lista.add(15, createTableSvmPedidosEnReparto())
            lista.add(16, createTableSvmSeguimientoPremioSup())
            lista.add(17, createTableSvmControlGeneralSup())
            lista.add(18, createTableSvmClienteSupervisor())
            lista.add(19, createTableSvmAnalisisCab())
            lista.add(20, createTableSvmAnalisisDet())
            lista.add(21, createTableSvmAnalisisVendedorDet())
            lista.add(22, createTableVtMarcacionUbicacion())
            lista.add(23, createTableVtMarcacionVisita())
            lista.add(24, createTableSvmMotivoAnalisisVendedor())
            lista.add(25, createTableSvmVendedorPedidoVenta())
            lista.add(26, createTableSvmModificaCatastro())
            lista.add(27, createTableSvmRuteoSemanal())
            lista.add(28, createTableSvmModificaCatastroVenta())
            lista.add(29, createTablePedidoCab())
            lista.add(30, createTablePedidoDet())
            lista.add(31, createTableSvmSolicitudDevCab())
            lista.add(32, createTableSvmSolicitudDevDet())
            lista.add(33, createTableCcClientesBajaProv())

            return lista
        }



        fun listaSQLAlterTable(): ArrayList<String> {
            val lista : ArrayList<String> = ArrayList()
            lista.add(0, addCodEmpresaSvmAnalisisCab())


            return lista
        }

        private fun addCodEmpresaSvmAnalisisCab():String{
            return ("alter table svm_analisis_cab add column COMENTARIO TEXT;")
        }





    }
}
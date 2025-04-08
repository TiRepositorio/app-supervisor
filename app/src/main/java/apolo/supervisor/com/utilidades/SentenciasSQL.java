//package apolo.supervisor.com.utilidades;
//
//public class SentenciasSQL {
//	String sql;
//
//	public String createTableUsuarios() {
//		sql = "CREATE TABLE IF NOT EXISTS usuarios"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT     , PASS TEXT,TIPO TEXT,"
//				+ " ACTIVO TEXT                          , COD_EMPRESA TEXT, VERSION TEXT, "
//				+ " COD_PERSONA TEXT);";
//		return sql;
//	}
//
//	public String createTableVT_NEGOCIACION_VENTA_CAB() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_negociacion_venta_cab"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT      , COD_SUCURSAL TEXT       , DESC_SUCURSAL TEXT  , "
//				+ " FEC_COMPROBANTE TEXT                 , TIP_COMPROBANTE TEXT  , SER_COMPROBANTE TEXT    , NRO_COMPROBANTE TEXT, "
//				+ " COD_CLIENTE TEXT                     , NOM_CLIENTE TEXT      , COD_SUBCLIENTE TEXT     , NOM_SUBCLIENTE TEXT ,"
//				+ " COD_VENDEDOR TEXT                    , DESC_VENDEDOR TEXT    , COD_CONDICION_VENTA TEXT, DESC_COND_VENTA TEXT,"
//				+ " COD_LISTA_PRECIO TEXT                , DESC_LISTA_PRECIO TEXT, COD_MONEDA TEXT         , DESC_MONEDA TEXT    ,"
//				+ " IND_VENTA TEXT                       , TOT_COMPROBANTE NUMBER, PENDIENTE TEXT          , DECIMALES TEXT      ,"
//				+ " FEC_ALTA TEXT                        , OBSERVACIONES TEXT    , IND_PRECIO TEXT         , IND_VAR TEXT        ,"
//				+ " IND_BON TEXT                         , PORC_DESC_VAR TEXT    , DESCUENTO_VAR TEXT      , AUT_DESC TEXT       ,"
//				+ " COD_PERFIL TEXT						 , DESC_PERFIL TEXT );";
//
//		return sql;
//	}
//
//	public String createTableVT_NEGOCIACION_VENTA_DET() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_negociacion_venta_det"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT      , TIP_COMPROBANTE TEXT, SER_COMPROBANTE TEXT      ,"
//				+ " NRO_COMPROBANTE TEXT                 , ORDEN TEXT            , COD_ARTICULO TEXT   , PRECIO_UNITARIO_C_IVA TEXT,"
//				+ " PRECIO_LISTA TEXT                    , COD_UNIDAD_MEDIDA TEXT, CANTIDAD NUMBER     , MONTO_TOTAL_CONIVA TEXT   ,"
//				+ " DESC_UNIDAD_MEDIDA TEXT              , DESC_ARTICULO TEXT    , IND_BLOQUEADO TEXT  , IND_AUTORIZACION TEXT     ,"
//				+ " IND_PRECIO TEXT                      , IND_VAR TEXT          , IND_BON TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSVM_VENDEDOR_PEDIDO() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_vendedor_pedido"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_SUPERVISOR TEXT, IND_PALM TEXT,"
//				+ " TIPO            TEXT                 , SERIE TEXT      , NUMERO TEXT        , FECHA TEXT   ,"
//				+ " ULTIMA_SINCRO   TEXT                 , VERSION TEXT	   , ULTIMA_VEZ TEXT    , RANGO TEXT   ,"
//				+ " TIPO_SUPERVISOR TEXT				 , MIN_FOTO TEXT   , MAX_FOTO TEXT		, IND_FOTO TEXT,"
//				+ " FEC_CARGA_RUTEO TEXT				 , MAX_DESC TEXT   , MAX_DESC_VAR TEXT  , PER_VENDER TEXT,"
//				+ " INT_MARCACION   TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_control_venta_diaria_cab() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_control_venta_diaria_cab "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_VENDEDOR TEXT,"
//				+ " DESC_VENDEDOR TEXT                   , NOMBRE_VENDEDOR TEXT, RUTEO TEXT       ,"
//				+ " POSITIVADO TEXT                      , POS_FUERA TEXT      , TOTAL_PEDIDO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_control_venta_diaria_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_control_venta_diaria_det "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , FEC_COMPROBANTE TEXT,"
//				+ " COD_VENDEDOR TEXT                    , COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT ,"
//				+ " NOM_CLIENTE TEXT                     , NOM_SUBCLIENTE TEXT , COD_ARTICULO TEXT   ,"
//				+ " DESC_ARTICULO TEXT                   , CANTIDAD TEXT       , PRECIO_UNITARIO TEXT,"
//				+ " TOT_DESCUENTO TEXT                   , MONTO_TOTAL TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_metas_punto_por_cliente() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_metas_punto_por_cliente "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_VENDEDOR TEXT   ,"
//				+ " DESC_VENDEDOR TEXT                   , CODIGO TEXT         , NOM_SUBCLIENTE TEXT ,"
//				+ " CIUDAD TEXT                          , COD_SUPERVISOR TEXT , DESC_SUPERVISOR TEXT,"
//				+ " LISTA_PRECIO TEXT                    , MAYOR_VENTA TEXT    , VENTA_3 TEXT        ,"
//				+ " MIX_3 TEXT                           , VENTA_4 TEXT        , MIX_4 TEXT          ,"
//				+ " METAS TEXT                           , TOT_SURTIDO TEXT    , MES_1 TEXT   		 ,"
//				+ " MES_2 TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_metas_punto_por_linea() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	 , COD_VENDEDOR TEXT   ,"
//				+ " DESC_VENDEDOR TEXT                   , COD_LINEA TEXT      	 , DESC_LINEA TEXT     ,"
//				+ " ABREV TEXT                           , DESC_GTE_MARKETIN TEXT, DESC_MODULO TEXT    ,"
//				+ " COD_SUPERVISOR TEXT 				 , DESC_SUPERVISOR TEXT  , MAYOR_VENTA TEXT    ,"
//				+ " VENTA_3 TEXT        				 , VENTA_4 TEXT          , METAS TEXT          ,"
//				+ " MES_1 TEXT          				 , MES_2 TEXT 		 	 , NRO_ORD_MAG TEXT    ,"
//				+ " ORD_GTE_MARK TEXT   				 , ORD_CATEGORIA TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_metas_punto_por_linea_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea_sup "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_LINEA TEXT        ,"
//				+ " DESC_LINEA TEXT                     , ABREVIATURA TEXT    , DESC_GTE_MARKETIN TEXT,"
//				+ " DESC_MODULO TEXT					, COD_SUP_TEL TEXT 	  , DESC_SUPERVISOR TEXT  ,"
//				+ " MAYOR_VENTA TEXT					, VENTA_3 TEXT 		  , VENTA_4 TEXT          ,"
//				+ " METAS TEXT      					, NRO_ORD_MAG TEXT    , ORD_GTE_MARK TEXT     ,"
//				+ " ORD_CATEGORIA TEXT 					, MES_1 TEXT      	  , MES_2 TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_evolucion_diaria_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_evolucion_diaria_venta "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_SUPERVISOR TEXT    ,"
//				+ " NOM_SUPERVISOR TEXT                  , META_SUPERVISOR TEXT, PTOS_SUPERVISOR TEXT   ,"
//				+ " COD_VENDEDOR TEXT                    , DESC_VENDEDOR TEXT  , NOM_VENDEDOR TEXT      ,"
//				+ " META_VENTA TEXT                      , PED_3_ATRAS TEXT    , PED_2_ATRAS TEXT       ,"
//				+ " PED_AYER TEXT                        , TOTAL_PED TEXT      , TOTAL_FACTURADO TEXT   ,"
//				+ " PORC_MES TEXT                        , NRO_ORDEN TEXT      , PTOS_LINEA TEXT        ,"
//				+ " PTOS_LINEA_ACUM TEXT                 , PTOS_CLIENTE TEXT   , PTOS_CLIENTE_ACUM TEXT ,"
//				+ " PROY_VENDEDOR TEXT                   , DIA_UTILES TEXT     , DIAS_TRABAJO TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_precio_fijo_consulta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_precio_fijo_consulta "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , COD_LISTA_PRECIO TEXT,"
//				+ " DESC_LISTA_PRECIO TEXT               , COD_ARTICULO TEXT, DESC_ARTICULO TEXT   ,"
//				+ " PREC_CAJA TEXT                       , PREC_UNID TEXT   , REFERENCIA TEXT      ,"
//				+ " DECIMALES TEXT                       , SIGLAS TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_costo_venta_articulo() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_costo_venta_articulo "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , COD_SUCURSAL TEXT    ,"
//				+ " DESC_SUCURSAL TEXT                   , COD_ARTICULO TEXT, REFERENCIA TEXT      ,"
//				+ " COSTO_VENTA TEXT   )";
//
//		return sql;
//	}
//
//	public String createTableSvm_deuda_por_clientes() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_deuda_cliente"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_CLIENTE TEXT    ,"
//				+ " DESC_CLIENTE TEXT                    , COD_SUBCLIENTE TEXT , DESC_SUBCLIENTE TEXT,"
//				+ " COD_VENDEDOR TEXT                    , DESC_VENDEDOR TEXT  , COD_SUPERVISOR TEXT ,"
//				+ " DESC_SUPERVISOR TEXT                 , FEC_EMISION TEXT    , FEC_VENCIMIENTO TEXT,"
//				+ " TIP_DOCUMENTO TEXT                   , NRO_DOCUMENTO TEXT  , DIAS_ATRAZO TEXT    ,"
//				+ " ABREVIATURA TEXT                     , SALDO_CUOTA TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_rebotes_por_vendedores() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_rebote_por_vendedor "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , CODIGO TEXT         ,"
//				+ " COD_VENDEDOR TEXT                   , NOM_VENDEDOR TEXT  , COD_SUPERVISOR TEXT ,"
//				+ " DESC_SUPERVISOR TEXT                , DESC_PENALIDAD TEXT, MONTO_TOTAL TEXT    ,"
//				+ " FECHA TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_metas_punto_por_linea_empresa() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_metas_punto_por_linea_empresa "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , METAS TEXT     ,"
//				+ " VENTA TEXT                          , CANT_PUNTO TEXT    , PUNT_ACUM TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_positivacion_cliente() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_positivacion_cliente "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_VENDEDOR TEXT,"
//				+ " DESC_VENDEDOR TEXT                  , COD_SUPERVISOR TEXT, TIP_CAUSAL TEXT  ,"
//				+ " COD_CLIENTE TEXT                    , COD_SUBCLIENTE TEXT, DESC_CLIENTE TEXT,"
//				+ " DESC_SUBCLIENTE TEXT                , DIRECCION TEXT     , DESC_CIUDAD TEXT ,"
//				+ " RUC TEXT                            , DESC_REGION TEXT   , DESC_ZONA TEXT   ,"
//				+ " TELEFONO TEXT						, MONTO_VENTA TEXT   , IND_POS TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_Ultima_Venta_Cliente() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_ultima_venta_cliente "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT ,"
//				+ " FEC_COMPROBANTE TEXT                , COD_ARTICULO TEXT   , DESC_ARTICULO TEXT  ,"
//				+ " PRECIO_UNITARIO TEXT                , CANTIDAD NUMBER     , DESC_UNIDAD TEXT    ,"
//				+ " SIGLAS TEXT                         , DECIMALES TEXT      , COD_VENDEDOR TEXT   ,"
//				+ " DESC_VENDEDOR TEXT                  , COD_EMPRESA TEXT); ";
//
//		return sql;
//	}
//
//	public String createTableSvm_pedidos_en_reparto() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_pedidos_en_reparto "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , NRO_PLANILLA TEXT    ,"
//				+ " DESC_REPARTIDOR TEXT                , TEL_REPARTIDOR TEXT ,"
//				+ " FEC_PLANILLA TEXT                   , FEC_COMPROBANTE TEXT, TIP_COMPROBANTE TEXT ,"
//				+ " NRO_COMPROBANTE TEXT                , COD_CLIENTE TEXT    , COD_SUBCLIENTE TEXT  ,"
//				+ " NOM_CLIENTE TEXT                    , NOM_SUBCLIENTE TEXT , SIGLAS TEXT          ,"
//				+ " DECIMALES TEXT                      , TOT_COMPROBANTE TEXT, COD_VENDEDOR TEXT    ,"
//				+ " DESC_VENDEDOR TEXT					, COD_SUPERVISOR TEXT , DESC_SUPERVISOR TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_seguimiento_premio_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_seguimiento_premio_sup "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT  , COD_SUPERVISOR TEXT,"
//				+ " DESC_SUPERVISOR TEXT				 , FECHA TEXT		 , VENTAS TEXT	      ,"
//				+ " METAS TEXT		  					 , PUNT_VOLUM TEXT	 , PREMIO_VOLUM TEXT  ,"
//				+ " PUNT_ACUM_LIN TEXT 					 , PREMIO_LINEA TEXT , PUNT_ACUM_CTE TEXT ,"
//				+ " PREMIO_CLIENTE TEXT					 , PUN_COBERT TEXT	 , PREMIO_COBERT TEXT ,"
//				+ " PUN_ACUM_RUTEO TEXT					 , PREMIO_RUTEO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_control_general_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_control_general_sup "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
//				+ " NRO_ORDEN TEXT					    , METAS TEXT            , VENTAS TEXT		    ,"
//				+ " PORC_VOL_VENTAS TEXT				, PUNT_ACUM_LINEAS TEXT , PUNT_CLI_ACUM TEXT    ,"
//				+ " CANT_CLI_POS TEXT					, MONTO_REBOTE TEXT	    , MONTO_DEV TEXT	    ,"
//				+ " CANT_COB TEXT					    , CANT_RUTEO TEXT	    , VENTAS_ESTR TEXT	    ,"
//				+ " METAS_ESTR TEXT						, CATASTRO TEXT			, PORC_MONTO_REBOTE TEXT,"
//				+ " PORC_MONTO_DEV TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_cliente_supervisor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cliente_supervisor "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
//				+ " COD_VENDEDOR TEXT					, DESC_VENDEDOR TEXT    , NOMBRE_VENDEDOR TEXT  ,"
//				+ " COD_CLIENTE TEXT				    , COD_SUBCLIENTE TEXT   , DESC_SUBCLIENTE TEXT  ,"
//				+ " LONGITUD TEXT					    , LATITUD TEXT	        , TIEMPO_MIN TEXT       ,"
//				+ " TIEMPO_MAX TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_motivo_analisis_cliente() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_motivo_analisis_cliente "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_MOTIVO TEXT, DESCRIPCION TEXT,"
//				+ " ESTADO TEXT							, PUNTUACION TEXT, COD_VENDEDOR TEXT,"
//				+ " NRO_ORDEN TEXT 						, COD_GRUPO TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_motivo_analisis_vendedor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_motivo_analisis_vendedor "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_MOTIVO TEXT, DESCRIPCION TEXT,"
//				+ "	ESTADO TEXT							, PUNTUACION TEXT, COD_VENDEDOR TEXT,"
//				+ " NRO_ORDEN TEXT						, COD_GRUPO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_analisis_cab() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_analisis_cab "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT, DESC_SUPERVISOR TEXT, COD_VENDEDOR TEXT  ,"
//				+ "	DESC_VENDEDOR TEXT   				, DESC_ZONA TEXT	 , HORA_LLEGADA TEXT   , HORA_SALIDA TEXT	,"
//				+ " FECHA_VISITA TEXT  					, COD_CLIENTE TEXT   , COD_SUBCLIENTE TEXT , DESC_SUBCLIENTE TEXT,"
//				+ " ESTADO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_analisis_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_analisis_det "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, ID_CAB TEXT, COD_MOTIVO TEXT, RESPUESTA TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_analisis_vendedor_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_analisis_vendedor_det "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_VENDEDOR TEXT, DESC_VENDEDOR TEXT, FECHA TEXT,"
//				+ " COD_MOTIVO TEXT						, RESPUESTA TEXT   , COMENTARIO TEXT   )";
//
//		return sql;
//	}
//
//	public String createTableSvm_modifica_catastro() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_modifica_catastro"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_CLIENTE TEXT		,"
//				+ " COD_SUBCLIENTE TEXT					 , TELEFONO1 TEXT	, TELEFONO2 TEXT		,"
//				+ " DIRECCION TEXT						 , CERCA_DE TEXT	, LATITUD TEXT			,"
//				+ " LONGITUD TEXT						 , ESTADO TEXT		, FECHA TEXT		    ,"
//				+ " FOTO_FACHADA BLOB		 			 , TIPO TEXT        , COD_VENDEDOR TEXT     ,"
//				+ " COD_SUPERVISOR TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_ruteo_semanal() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_ruteo_semanal"
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_SUPERVISOR TEXT,"
//				+ " COD_VENDEDOR TEXT					, FECHA TEXT      , COD_CLIENTE    TEXT,"
//				+ " COD_SUBCLIENTE TEXT					, ESTADO TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_asistencia_vendedores() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_asistencia_vendedores "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_VENDEDOR TEXT	, NOM_VENDEDOR TEXT  , "
//				+ "  TOT_CLIENTE TEXT					 , VISITA_DIA TEXT	  , TOT_EFECT TEXT		, TOT_RUTEO TEXT     , "
//				+ "  PORC_RUTEO TEXT 					 , HORAS_TRAB TEXT	  , CANT_CLI_SEMANA TEXT, EST_PENALIDAD TEXT , "
//				+ "  DIAS_TRAB TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_cliente_prom_vendedor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cliente_prom_vendedor "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT , COD_SUBCLIENTE TEXT,"
//				+ " DESC_SUBCLIENTE TEXT                 , DESC_CLIENTE TEXT    , COD_VENDEDOR TEXT, TELEFONO TEXT      ,"
//				+ " LONGI REAL                           , LATI REAL            , TIP_CAUSAL TEXT  , desc_ciudad TEXT   ,"
//				+ " RUC TEXT                             , DESC_ZONA TEXT       , DESC_REGION TEXT , DIRECCION TEXT     ,"
//				+ " COD_SUCURSAL TEXT                    , TIP_CLIENTE TEXT     , DESC_TIPO TEXT   , COD_CONDICION TEXT ,"
//				+ " DESC_CONDICION TEXT                  , LIMITE_CREDITO NUMBER, TOT_DEUDA NUMBER , SALDO NUMBER       ,"
//				+ " FEC_VISITA TEXT                      , IND_VISITA text      , COD_ZONA         , TIPO_CONDICION TEXT,"
//				+ " IND_DIRECTA TEXT                     , IND_ATRAZO TEXT      , FRECUENCIA TEXT  , DIAS_INICIAL TEXT  ,"
//				+ " COMENTARIO TEXT						 , IND_ESP TEXT 		, CATEGORIA TEXT   , TELEFONO2 TEXT		,"
//				+ " LATITUD TEXT						 , LONGITUD TEXT		, CERCA_DE TEXT	   , IND_CADUCADO TEXT  ,"
//				+ " IND_VITRINA TEXT 					 , ESTADO TEXT 			, FOTO_FACHADA TEXT, TIEMPO_MIN TEXT    ,"
//				+ " TIEMPO_MAX TEXT );";
//
//		return sql;
//	}
//
//	public String createTableStv_categorias() {
//		sql = "CREATE TABLE IF NOT EXISTS stv_categoria"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT		, COD_CATEGORIA TEXT, DESC_CATEGORIA TEXT,"
//				+ "	 COD_SEGMENTO TEXT					 , DESC_SEGMENTO TEXT	, ORDEN TEXT		, TIPO TEXT		 	 , "
//				+ "  COD_TIP_CLIENTE TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_retorno_comentario() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_retorno_comentario"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , FEC_ENTRADA TEXT   , FEC_SALIDA TEXT   , "
//				+ "  COD_PROMOTOR TEXT					 , COD_CLIENTE TEXT   , COD_SUBCLIENTE TEXT, COD_CATEGORIA TEXT, "
//				+ "  COD_SEGMENTO TEXT					 , TIPO TEXT 		  , PUNTUACION TEXT    , COMENTARIO_SUPERVISOR TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_fotos_cab() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_fotos_cab "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_PROMOTOR TEXT, COD_CLIENTE TEXT	,"
//				+ "  COD_SUBCLIENTE TEXT, ESTADO TEXT    , FECHA_ENTRADA TEXT , HORA_ENTRADA TEXT, LATITUD TEXT	 	,"
//				+ "  LONGITUD TEXT						 , FECHA_ENVIO TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_fotos_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_fotos_det "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, ID_CAB INTEGER,  COD_CATEGORIA TEXT, TIPO TEXT,"
//				+ "  COMENTARIO TEXT 					 , COD_SEGMENTO TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_imagenes_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_imagenes_det "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, ID_DET INTEGER, IMAGEN BLOB, NRO_ITEM TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_periodo_foto() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_periodo_foto"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT, COD_TIP_CLIENTE TEXT, FEC_INICIO TEXT,"
//				+ "  FEC_FIN TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_cliente_supervisor_full() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cliente_supervisor_full "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_SUPERVISOR TEXT   , DESC_SUPERVISOR TEXT  ,"
//				+ " COD_VENDEDOR TEXT					, DESC_VENDEDOR TEXT    , NOMBRE_VENDEDOR TEXT  ,"
//				+ " COD_CLIENTE TEXT				    , COD_SUBCLIENTE TEXT   , DESC_SUBCLIENTE TEXT  ,"
//				+ " LONGITUD TEXT					    , LATITUD TEXT	        , TIEMPO_MIN TEXT       ,"
//				+ " TIEMPO_MAX TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_evol_diaria_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_evol_diaria_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_SUPERVISOR TEXT  ,"
//				+ "  NOM_SUPERVISOR TEXT				 , COD_VENDEDOR TEXT  , DESC_VENDEDOR TEXT   ,"
//				+ "  PEDIDO_2_ATRAS TEXT				 , PEDIDO_1_ATRAS TEXT, TOTAL_PEDIDOS TEXT   ,"
//				+ "  TOTAL_FACT TEXT    				 , META_VENTA TEXT	  , META_LOGRADA TEXT    ,"
//				+ "  PROY_VENTA TEXT	  				 , TOTAL_REBOTE TEXT  , TOTAL_DEVOLUCION TEXT,"
//				+ "  CANT_CLIENTES TEXT 				 , CANT_POSIT TEXT	  , EF_VISITA TEXT		 ,"
//				+ "  DIA_TRAB TEXT	  					 , PUNTAJE TEXT	      , SURTIDO_EF TEXT	)";
//
//		return sql;
//	}
//
//	public String createTablaSvm_autorizaciones() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_autorizaciones"
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_VENDEDOR TEXT, COD_CLIENTE TEXT,"
//				+ "  COD_SUBCLIENTE TEXT				, COD_ARTICULO TEXT, COD_UNIDAD_MEDIDA TEXT,"
//				+ "  CANTIDAD TEXT						, DESCUENTO TEXT   , CLAVE TEXT		 ,"
//				+ "  CONTRA_CLAVE TEXT 					, ESTADO TEXT	   , TOTAL_PEDIDO TEXT,"
//				+ "  TIPO TEXT							, COMENTARIO TEXT)";
//
//		return sql;
//	}
//
//	public String createTablevt_marcacion_ubicacion() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_marcacion_ubicacion"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , FECHA TEXT   , COD_PROMOTOR TEXT, "
//				+ "  COD_CLIENTE TEXT   				 , COD_SUBCLIENTE TEXT, TIPO TEXT  	 , ESTADO TEXT      , "
//				+ "  LATITUD TEXT    					 , LONGITUD TEXT	  , OBSERVACION TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_control_hora() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_control_hora "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, FECHA TEXT, HORA TEXT)";
//
//		return sql;
//	}
//
//	//nuevos venta
//
//	public String createTablevt_marcacion_visita() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_marcacion_visita "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_SUCURSAL TEXT, "
//				+ " COD_CLIENTE TEXT					, COD_SUBCLIENTE TEXT, COD_VENDEDOR TEXT, "
//				+ " COD_MOTIVO TEXT  					, OBSERVACION TEXT   , FECHA TEXT	    , "
//				+ " LATITUD TEXT	  					, LONGITUD TEXT		 , ESTADO TEXT	    ,"
//				+ " HORA_ALTA TEXT		)";
//
//		return sql;
//	}
//
//	public String createTablePedidoCab() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_pedidos_cab "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT  , COD_SUBCLIENTE TEXT      ,"
//				+ " COD_VENDEDOR TEXT                    , COD_LISTA_PRECIO TEXT, FECHA TEXT        , FECHA_INT TEXT           ,"
//				+ " NUMERO NUMBER                        , NRO_ORDEN_COMPRA TEXT, COD_CONDICION TEXT, TIP_CAMBIO               ,"
//				+ " TOT_COMPROBANTE TEXT                 , ESTADO TEXT          , COD_MONEDA TEXT   , DECIMALES TEXT           ,"
//				+ " COMENTARIO TEXT                      , porc_desc_var TEXT   , porc_desc_fin TEXT, descuento_var TEXT       ,"
//				+ " descuento_fin TEXT                   , tot_descuento TEXT   , DESC_CLIENTE TEXT , DIAS_INICIAL             ,"
//				+ " COD_CONDICION_VENTA TEXT             , NRO_AUTORIZACION TEXT, FEC_ALTA TEXT     , LATITUD TEXT		       ,"
//				+ " LONGITUD TEXT						 , IND_PRESENCIAL TEXT  , HORA_ALTA TEXT    , NRO_AUTORIZACION_DESC TEXT,"
//				+ " HORA_REGISTRO DATETIME default (datetime('now','localtime')))";
//
//		return sql;
//	}
//
//	public String renameTablePedidoCab() {
//		sql = "alter table 'vt_pedidos_cab' rename to 'vt_pedidos_prov';";
//
//		return sql;
//	}
//
//	public String fillTablePedidoCab() {
//		sql = "insert into vt_pedidos_cab "
//				+ " (id					, COD_EMPRESA 		, COD_CLIENTE	, COD_SUBCLIENTE		,"
//				+ " COD_VENDEDOR		, COD_LISTA_PRECIO	, FECHA			, FECHA_INT				,"
//				+ " NUMERO				, NRO_ORDEN_COMPRA	, COD_CONDICION	, TIP_CAMBIO			,"
//				+ " TOT_COMPROBANTE		, ESTADO			, COD_MONEDA	, DECIMALES				,"
//				+ " COMENTARIO			, porc_desc_var		, porc_desc_fin	, descuento_var			,"
//				+ " descuento_fin		, tot_descuento		, DESC_CLIENTE	, DIAS_INICIAL			,"
//				+ " COD_CONDICION_VENTA	, NRO_AUTORIZACION	, FEC_ALTA		, LATITUD				,"
//				+ " LONGITUD			, IND_PRESENCIAL	, HORA_ALTA		, NRO_AUTORIZACION_DESC)"
//				+ " "
//				+ " select id			, COD_EMPRESA 		, COD_CLIENTE	, COD_SUBCLIENTE		,"
//				+ " COD_VENDEDOR		, COD_LISTA_PRECIO	, FECHA			, FECHA_INT				,"
//				+ " NUMERO				, NRO_ORDEN_COMPRA	, COD_CONDICION	, TIP_CAMBIO			,"
//				+ " TOT_COMPROBANTE		, ESTADO			, COD_MONEDA	, DECIMALES				,"
//				+ " COMENTARIO			, porc_desc_var		, porc_desc_fin	, descuento_var			,"
//				+ " descuento_fin		, tot_descuento		, DESC_CLIENTE	, DIAS_INICIAL			,"
//				+ " COD_CONDICION_VENTA	, NRO_AUTORIZACION	, FEC_ALTA		, LATITUD				,"
//				+ " LONGITUD			, IND_PRESENCIAL	, HORA_ALTA		, NRO_AUTORIZACION_DESC"
//				+ " from vt_pedidos_prov;";
//
//		return sql;
//	}
//
//	public String createTablePedidoDet() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_pedidos_det"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod_empresa TEXT       , numero NUMBER               , cod_articulo TEXT        ,"
//				+ " cantidad NUMBER                      , precio_unitario NUMBER , monto_total NUMBER          , total_iva NUMBER         ,"
//				+ " orden NUMBER                         , cod_unidad_medida TEXT , cod_iva TEXT                , porc_iva NUMBER          ,"
//				+ " cod_sucursal TEXT                    , cod_zona TEXT          , precio_unitario_c_iva NUMBER, monto_total_coniva NUMBER,"
//				+ " porc_desc_var NUMBER                 , descuento_var NUMBER   , porc_desc_fin NUMBER        , descuento_fin NUMBER     ,"
//				+ " precio_lista NUMBER                  , mult NUMBER            , tip_comprobante_ref TEXT    , ser_comprobante_ref TEXT ,"
//				+ " nro_comprobante_ref TEXT             , orden_ref TEXT         , ind_sistema TEXT            , ind_translado TEXT       ,"
//				+ " ind_bloqueado TEXT                   , ind_deposito TEXT      , existencia_actual TEXT 	    , promocion NUMBER		   ,"
//				+ " tip_promocion TEXT					 , nro_autorizacion TEXT  , monto_desc_tc TEXT			, nro_promocion TEXT       ,"
//				+ " COD_VENDEDOR  TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_vendedor_pedido_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_vendedor_pedido_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod_empresa TEXT  , cod_vendedor TEXT ,"
//				+ " ind_palm TEXT                        , tipo TEXT         , serie TEXT        ,"
//				+ " numero TEXT                          , fecha TEXT        , ultima_sincro TEXT,"
//				+ " version TEXT                         , COD_CLI_VEND TEXT , COD_PERSONA TEXT  ,"
//				+ " RANGO TEXT							 , PER_VENDER TEXT	 , PER_PRESENCIAL TEXT,"
//				+ " HORA TEXT							 , MIN_FOTOS TEXT	 , MAX_FOTOS TEXT    ,"
//				+ " VERSION_SISTEMA TEXT 				 , PER_ASISTENCIA TEXT, FRECUENCIA_RASTREO TEXT,"
//				+ " HORA_INICIO TEXT					 , HORA_FIN TEXT     , TIEMPO_ASISTENCIA TEXT,"
//				+ " ULTIMA_VEZ TEXT 					 , IND_FOTO TEXT     , MIN_VENTA TEXT);";
//
//		return sql;
//	}
//
//	public String createTablevt_marcacion_ubicacion_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS vt_marcacion_ubicacion_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , FECHA TEXT   , COD_PROMOTOR TEXT, "
//				+ "  COD_CLIENTE TEXT   				 , COD_SUBCLIENTE TEXT, TIPO TEXT  	 , ESTADO TEXT      , "
//				+ "  LATITUD TEXT    					 , LONGITUD TEXT	  , OBSERVACION TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_deuda_cliente_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_deuda_cliente_venta "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    , COD_VENDEDOR TEXT   ,"
//				+ " COD_CLIENTE TEXT                    , COD_SUBCLIENTE TEXT , FEC_EMISION TEXT    ,"
//				+ " FEC_VENCIMIENTO NUMBER              , TIP_DOCUMENTO TEXT  , NRO_DOCUMENTO TEXT  ,"
//				+ " SALDO_CUOTA TEXT                    , DIA_ATRAZO TEXT     , ABREVIATURA TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_modifica_catastro_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_modifica_catastro_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_CLIENTE TEXT		,"
//				+ " COD_SUBCLIENTE TEXT					 , TELEFONO1 TEXT	, TELEFONO2 TEXT		,"
//				+ " DIRECCION TEXT						 , CERCA_DE TEXT	, LATITUD TEXT			,"
//				+ " LONGITUD TEXT						 , ESTADO TEXT		, FECHA TEXT		    ,"
//				+ " FOTO_FACHADA BLOB		 			 , TIPO TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_motivo_no_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_motivo_no_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, COD_MOTIVO TEXT,"
//				+ "	 DESC_MOTIVO TEXT 					 , CIERRA TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_solicitud_tc() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_solicitud_tc"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	 , COD_CLIENTE TEXT  , COD_SUBCLIENTE TEXT ,"
//				+ "  COD_MOTIVO TEXT				 	 , COMENTARIO TEXT		 , FOTO1 BLOB    	 , FOTO2 BLOB		   , "
//				+ "  FECHA TEXT				 			 , ESTADO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableSvm_motivo_solicitud_tc() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_motivo_solicitud_tc"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_MOTIVO TEXT    	 , DESC_MOTIVO TEXT, IND_FOTO TEXT )";
//
//
//		return sql;
//	}
//
//	public String createTableCli_Vendedor() {
//  		sql = "CREATE TABLE IF NOT EXISTS CLI_VENDEDOR "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT , COD_SUBCLIENTE TEXT,"
//				+ " DESC_SUBCLIENTE TEXT                 , DESC_CLIENTE TEXT    , COD_VENDEDOR TEXT, TELEFONO TEXT      ,"
//				+ " LONGI REAL                           , LATI REAL            , TIP_CAUSAL TEXT  , desc_ciudad TEXT   ,"
//				+ " RUC TEXT                             , DESC_ZONA TEXT       , DESC_REGION TEXT , DIRECCION TEXT     ,"
//				+ " COD_SUCURSAL TEXT                    , TIP_CLIENTE TEXT     , DESC_TIPO TEXT   , COD_CONDICION TEXT ,"
//				+ " DESC_CONDICION TEXT                  , LIMITE_CREDITO NUMBER, TOT_DEUDA NUMBER , SALDO NUMBER       ,"
//				+ " FEC_VISITA TEXT                      , IND_VISITA text      , COD_ZONA         , TIPO_CONDICION TEXT,"
//				+ " IND_DIRECTA TEXT                     , IND_ATRAZO TEXT      , FRECUENCIA TEXT  , DIAS_INICIAL TEXT  ,"
//				+ " COMENTARIO TEXT						 , IND_ESP TEXT 		, CATEGORIA TEXT   , TELEFONO2 TEXT		,"
//				+ " LATITUD TEXT						 , LONGITUD TEXT		, CERCA_DE TEXT	   , IND_CADUCADO TEXT  ,"
//				+ " IND_VITRINA TEXT 					 , ESTADO TEXT 			, FOTO_FACHADA TEXT, TIEMPO_MIN TEXT    ,"
//				+ " TIEMPO_MAX TEXT 					 , SOL_TARG TEXT );";
//
//		return sql;
//	}
//
//	public String createTableSvm_promociones_art_cab() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_promociones_art_cab"
//				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
//				+ "  COD_UNID_NEGOCIO TEXT				 , TIP_CLIENTE TEXT			, DESCRIPCION TEXT	  	,"
//				+ "  COMENTARIO TEXT					 , COD_CONDICION_VENTA TEXT , FEC_VIGENCIA TEXT   	,"
//				+ "  COD_ARTICULO TEXT					 , DESC_ARTICULO TEXT		, COD_UNIDAD_MEDIDA TEXT,"
//				+ "  REFERENCIA TEXT					 , NRO_GRUPO TEXT			, IND_TIPO TEXT			,"
//				+ "  CANT_VENTA TEXT 					 , MULT TEXT                , COD_LISTA_PRECIO TEXT ,"
//				+ "  COD_VENDEDOR TEXT					 , IND_COMBO TEXT			, IND_PROM TEXT			,"
//				+ "  IND_ART TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_promociones_art_det() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_promociones_art_det"
//				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT			, NRO_PROMOCION TEXT	,"
//				+ "  COD_UNID_NEGOCIO TEXT				 , NRO_GRUPO TEXT			, CANT_DESDE TEXT	  	,"
//				+ "  CANT_HASTA TEXT					 , COD_ARTICULO TEXT 		, COD_UNIDAD_MEDIDA TEXT,"
//				+ "  DESC_UND_MEDIDA TEXT			 	 , IND_TIPO TEXT			, DESCUENTO TEXT		,"
//				+ "  PRECIO_GS TEXT					 	 , PRECIO_RS TEXT		 	, MULT TEXT				,"
//				+ "  IND_MULTIPLE TEXT                   , COD_VENDEDOR TEXT)";
//		return sql;
//	}
//
//	public String createTableSvm_st_articulos() {
//  		sql = "CREATE TABLE IF NOT EXISTS svm_st_articulos"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod_empresa  TEXT, cod_articulo TEXT, desc_articulo TEXT,"
//				+ " cod_unidad_rel TEXT                  , referencia TEXT  , mult NUMBER      , div  NUMBER       ,"
//				+ " cod_iva TEXT                         , porc_iva TEXT    , cod_linea TEXT   , cod_familia TEXT  ,"
//				+ " ind_basico TEXT                      , CANT_MINIMA NUMBER);";
//
//		return sql;
//	}
//
//	public String createTableSvm_condicion_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_condicion_venta"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT   , COD_CONDICION_VENTA TEXT,"
//				+ " DESCRIPCION TEXT                     , TIPO_CONDICION TEXT, ABREVIATURA TEXT        ,"
//				+ " DIAS_INICIAL TEXT                    , PORC_DESC TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_cond_venta_vendedor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cond_venta_vendedor "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_LISTA_PRECIO TEXT, COD_CONDICION_VENTA TEXT,"
//				+ " COD_CLIENTE TEXT					 , COD_SUBCLIENTE TEXT  , COD_EMPRESA         TEXT,"
//				+ " COD_VENDEDOR TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_prom_articulos_tar_cred() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_prom_articulos_tar_cred"
//				+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT , NRO_PROMOCION TEXT,"
//				+ "  COD_CONDICION_VENTA TEXT			 , COD_ARTICULO TEXT, DESC_ARTICULO TEXT,"
//				+ "  PORC_DESCUENTO TEXT				 , DESCRIPCION TEXT , COMENTARIO TEXT )";
//
//		return sql;
//	}
//
//	public String createTableArticulos_Precios () {
// 		sql = "CREATE TABLE IF NOT EXISTS articulos_precios"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, cod_empresa  TEXT     , cod_vendedor TEXT    , modulo TEXT      ,"
//				+ " cod_lista_precio TEXT                , cod_articulo TEXT     , desc_articulo TEXT   , prec_unid NUMBER ,"
//				+ " prec_caja NUMBER                     , cod_unidad_medida TEXT, porc_iva number      , referencia TEXT  ,"
//				+ " mult NUMBER                          , div NUMBER            , ind_lim_venta NUMBER , cod_linea TEXT   ,"
//				+ " cod_familia TEXT                     , cod_barra TEXT        , porc_descuento NUMBER, cant_minima TEXT ,"
//				+ " IND_PROMO_ACT TEXT DEFAULT 'N'		/* , TIP_SURTIDO TEXT DEFAULT 'N'*/);";
//
//		return sql;
//	}
//
//	public String createIndexArticulos_precios() {
//		sql = "create index if not exists articulo_cod_empresa 		 on articulos_precios(cod_empresa);"
//			+ "create index if not exists articulo_cod_vendedor 	 on articulos_precios(cod_vendedor);"
//			+ "create index if not exists articulo_cod_lista_precio  on articulos_precios(cod_lista_precio);"
//			+ "create index if not exists articulo_cod_articulo 	 on articulos_precios(cod_articulo);"
//			+ "create index if not exists articulo_desc_articulo 	 on articulos_precios(desc_articulo);"
//			+ "create index if not exists articulo_prec_caja 		 on articulos_precios(prec_caja);"
//			+ "create index if not exists articulo_cod_unidad_medida on articulos_precios(cod_unidad_medida);"
//			+ "create index if not exists articulo_porc_iva 		 on articulos_precios(porc_iva);"
//			+ "create index if not exists articulo_mult 			 on articulos_precios(mult);"
//			+ "create index if not exists articulo_div 		 		 on articulos_precios(div);"
//			+ "create index if not exists articulo_ind_lim_venta 	 on articulos_precios(ind_lim_venta);"
//			+ "create index if not exists articulo_cod_familia 		 on articulos_precios(cod_familia);"
//			+ "create index if not exists articulo_cod_barra 		 on articulos_precios(cod_barra);"
//			+ "create index if not exists articulo_porc_descuento 	 on articulos_precios(porc_descuento);"
//			+ "create index if not exists articulo_ind_promo_act 	 on articulos_precios(IND_PROMO_ACT);"
//			+ "create index if not exists articulo_tip_surtido 		 on articulos_precios(TIP_SURTIDO);"
//			+ "create index if not exists articulo_modulo 			 on articulos_precios(modulo);"
//			+ "create index if not exists articulo_prec_unid 		 on articulos_precios(prec_unid);"
//			+ "create index if not exists articulo_referencia 		 on articulos_precios(referencia);"
//			+ "create index if not exists articulo_cod_linea 		 on articulos_precios(cod_linea);"
//			+ "create index if not exists articulo_cant_minima 		 on articulos_precios(cant_minima);"
//			+ "";
//
//		return sql;
//	}
//
//	public String createTableCliente_List_Prec() {
//
//		 sql = "CREATE TABLE IF NOT EXISTS cliente_list_prec"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT     , COD_CLIENTE TEXT, COD_SUBCLIENTE TEXT,"
//				+ "COD_VENDEDOR TEXT                     , COD_LISTA_PRECIO TEXT, DESC_LISTA TEXT , COD_MONEDA TEXT    ,"
//				+ "DECIMALES TEXT                        , IND_DEFECTO TEXT);";
//
//		return sql;
//	}
//
//	public String createTableSvm_promociones_por_perfil() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_promociones_por_perfil"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	 , TIP_CLIENTE TEXT		,"
//				+ " NRO_PROMOCION TEXT					 , DESCRIPCION TEXT	 , COMENTARIO TEXT		,"
//				+ " FEC_INI_PROMO TEXT					 , FEC_FIN_PROMO TEXT, COD_ARTICULO TEXT )";
//
//
//		return sql;
//	}
//
//	public String createTableSvm_cliente_dia_visita_ruteo() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cliente_dia_visita_ruteo"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA    TEXT   , COD_VENDEDOR  TEXT		,"
//				+ " NOMBRE_VENDEDOR TEXT				 , FEC_MOVIMIENTO TEXT	 , CANT_CLIENTES TEXT		,"
//				+ " CANT_VENDIDO NUMBER					 , CANT_NO_VENTA  NUMBER , PORC          NUMBER)";
//
//
//		return sql;
//	}
//
//	public String createTableSvm_cliente_sem_visita_ruteo() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cliente_sem_visita_ruteo"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA    TEXT   , COD_VENDEDOR  TEXT		,"
//				+ " NOMBRE_VENDEDOR TEXT				 , FEC_INICIO    TEXT	 , FEC_FINAL TEXT           ,"
//				+ " CANT_CLIENTES TEXT		             , CANT_VENDIDO  NUMBER	 , CANT_NO_VENTA  NUMBER    ,"
//				+ " CANT_NO_ANTENDIDO TEXT               , PORC          NUMBER)";
//
//		return sql;
//	}
//
//	public String createTableSvm_extracto_tarjeta_pro() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_extracto_tarjeta_pro "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, TIP_COMPROBANTE TEXT, FEC_COMPROBANTE TEXT  , "
//				+ " TOT_DEBE  NUMBER    				 , TOT_HABER NUMBER    , SALDO_ANTERIOR  NUMBER, "
//				+ " NRO_TARJETA_CREDITO TEXT             , NRO_ORDEN NUMBER    )";
//
//		return sql;
//	}
//
//
//	public String createTableSvm_anuncio_movil() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_anuncio_movil "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, NRO_REGISTRO TEXT, FECHA TEXT, "
//				+ " TITULO TEXT            				 , CONTENIDO TEXT   )";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_premios_vend(){
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_premios_vend "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA     	  TEXT 	, COD_VENDEDOR 		TEXT	, "
//													   + " VENDEDOR_PERSONA   TEXT 	, DESC_VENDEDOR 	TEXT	, "
//													   + " TIP_COM      	  TEXT	, COD_MARCA 		TEXT	, "
//													   + " DESC_MARCA 		  TEXT	, MONTO_VENTA 		TEXT	, "
//													   + " MONTO_META         TEXT  , PORC_COBERTURA 	TEXT	, "
//													   + " PORC_LOG 		  TEXT	, MONTO_A_COBRAR 	TEXT	, "
//													   + " MONTO_COBRAR   	  TEXT	, TOT_CLIENTES 		TEXT	, "
//													   + " CLIENTES_VISITADOS TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_produccion_semanal_vend() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_produccion_semanal_vend "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA    TEXT  , COD_VENDEDOR   TEXT    , "
//													   + " COD_PERSONA    TEXT  , DESC_VENDEDOR  TEXT    , "
//													   + " CANTIDAD       NUMBER, "
//													   + " SEMANA         TEXT  , FECHA          TEXT    , "
//													   + " MONTO_VIATICO  NUMBER, MONTO_TOTAL    NUMBER  , "
//										               + " PERIODO TEXT )";
//		return sql;
//	}
//
//	public String createTableSvm_liq_premios_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_premios_sup "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA        TEXT  ,  COD_MARCA       TEXT    , "
//													   + " DESC_MARCA         TEXT  ,  MONTO_VENTA     NUMBER  , "
//													   + " MONTO_META         NUMBER,  PORC_LOG        TEXT    , "
//  													   + " PORC_COBERTURA     TEXT  ,  TOT_CLIENTES    NUMBER  , "
//  													   + " CLIENTES_VISITADOS NUMBER,  MONTO_A_COBRAR  NUMBER  , "
//  													   + " MONTO_COBRAR    NUMBER   ,  TIP_COM         TEXT )";
//		return sql;
//	}
//
//	public String createTableSvm_seg_visitas_semanal() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_seg_visitas_semanal"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA        TEXT   , FEC_MOVIMIENTO  	TEXT		,"
//				+ " COD_VENDEDOR 		TEXT		 	 , NOMBRE_VENDEDOR    TEXT	 , FEC_INICIO 		TEXT 		,"
//				+ " FEC_FIN 			TEXT           	 , CANTIDAD  		  TEXT 	 , CANT_VENDIDO  	TEXT 		,"
//				+ " CANT_NO_VENDIDO 	TEXT			 , CANT_NO_VISITADO   TEXT 	 , SEMANA			TEXT		,"
//				+ " PORC TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_movimiento_gestor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_movimiento_gestor"
//				+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA 		TEXT   	, COD_VENDEDOR  TEXT		,"
//				+ " NOM_VENDEDOR 		TEXT		 	 , COD_CLIENTE 		TEXT 	, DESC_CLIENTE 	TEXT 		,"
//				+ " FEC_ASISTENCIA 		TEXT           	 , HORA_ENTRADA 	TEXT 	, HORA_SALIDA  	TEXT 		,"
//				+ " MARCACION 			TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_premios_supervisores() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_premios_supervisores "
//				+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA 		TEXT   	, COD_MARCA	  	TEXT		,"
//				+ " DESC_MARCA 			TEXT		 	 , FEC_COMPROBANTE	TEXT 	, VENTA			TEXT 		,"
//				+ " META		 		TEXT           	 , CANT_PUNTO 		TEXT 	, PORC_CUMP  	TEXT 		,"
//				+ " PUNT_ACUM 			TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_comision_supervisor() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_comision_supervisor "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_CONCEPTO TEXT  , DESC_CONCEPTO TEXT,"
//				+ " MONTO TEXT							, TIPO TEXT          , NRO_ORDEN TEXT    ,"
//				+ " NRO_CUOTA TEXT 						, FEC_HASTA TEXT  	 , COD_MONEDA TEXT   ,"
//				+ " DECIMALES TEXT   					, SIGLAS TEXT  		 , COMENT TEXT)";
//
//		return sql;
//	}
//
//	public String createTableSvm_cobertura_marca_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cobertura_marca_sup "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT	, PORC_DESDE TEXT,"
//				+ " PORC_HASTA TEXT						, PORC_COM TEXT		, LINHA TEXT		)";
//
//		return sql;
//	}
//
//	public String createTableSvm_cobertura_vis_vendedores() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cobertura_vis_vendedores "
//				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, COD_GRUPO TEXT	, COD_VENDEDOR TEXT, DESC_VENDEDOR TEXT, "
//				+ " PORC_DESDE TEXT 					, PORC_HASTA TEXT	, PORC_COM TEXT		)";
//
//		return sql;
//	}
//
//	public String createTableSvm_liquidacion_fuerza_venta() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liquidacion_fuerza_venta "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA         TEXT  , FEC_COMPROBANTE  DATE  , "
//													   + " OBSERVACION         TEXT  , DESCRIPCION      TEXT  , "
//				                                       + " TIP_COMPROBANTE_REF TEXT  , TOT_IVA          NUMBER, "
//				                                       + " TOT_GRAVADA         NUMBER, TOT_EXENTA       NUMBER(15,2), "
//													   + " TOT_COMPROBANTE     NUMBER)";
//		return sql;
//	}
//
//	public String createTablesvm_solicitud_dev_det() {
//		//estado(S=enviado,N=pendiente)
//		sql = "CREATE TABLE IF NOT EXISTS svm_solicitud_dev_det"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT	, COD_EMPRESA TEXT	 	, TIP_PLANILLA TEXT	, "
//				+ " NRO_PLANILLA TEXT						, SER_COMP TEXT			, TIP_COMP TEXT		, "
//				+ " NRO_COMP TEXT							, COD_VENDEDOR TEXT		, COD_CLIENTE		, "
//				+ " COD_SUBCLIENTE							, NRO_REGISTRO_REF TEXT	, NRO_ORDEN TEXT	, "
//				+ " COD_ARTICULO TEXT						, DESC_ARTICULO TEXT	, CANTIDAD TEXT		, "
//				+ " PAGO TEXT								, MONTO TEXT			, TOTAL TEXT		, "
//				+ " COD_UNIDAD_REL TEXT						, REFERENCIA TEXT 		, IND_BASICO TEXT	, "
//				+ " COD_PENALIDAD TEXT						, GRABADO_CAB TEXT		, EST_ENVIO TEXT 	, "
//				+ " FECHA TEXT	)";
//
//		return sql;
//	}
//
//	public String createTablesvm_solicitud_dev_cab() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_solicitud_dev_cab"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_VENDEDOR TEXT 	,"
//				+ " NRO_COMPROBANTE TEXT				 , COD_CLIENTE TEXT    	, COD_SUBCLIENTE TEXT 	,"
//				+ " NOM_CLIENTE TEXT                     , TOT_COMPROBANTE TEXT	, NRO_PLANILLA TEXT   	,"
//				+ " SIGLAS TEXT         				 , OBSERVACIONES TEXT	, EST_ENVIO TEXT 		,"
//				+ " NRO_REGISTRO_REF TEXT 				 , FECHA TEXT )";
//
//		return sql;
//	}
//
//	public String createTablesvm_vt_motivos_sod_dev() {
//		//estado(S=enviado,N=pendiente)
//		sql = "CREATE TABLE IF NOT EXISTS svm_motivos_sd_dev"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT	, COD_EMPRESA TEXT		, COD_MOTIVO TEXT	, "
//				+ " DESC_MOTIVO TEXT) ";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_canasta_marca_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_canasta_marca_sup "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_MARCA TEXT 		,"
//				+ " DESC_MARCA TEXT				 		 , VENTAS TEXT 			, CUOTA TEXT 			,"
//				+ " VALOR_CANASTA TEXT 					 , PESO_PUNT TEXT		, PUNTAJE_MARCA TEXT 	,"
//				+ " PORC_CUMP TEXT 				 		 , FEC_DESDE TEXT		, FEC_HASTA TEXT 		,"
//				+ " MONTO_A_COBRAR 						 , COD_UNID_NEGOCIO TEXT, DESC_UNID_NEGOCIO TEXT "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_canasta_cte_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_canasta_cte_sup "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, FEC_DESDE TEXT 		,"
//				+ " FEC_HASTA TEXT				 		 , COD_CLIENTE TEXT 	, DESC_CLIENTE TEXT 	,"
//				+ " VENTAS TEXT 						 , CUOTA TEXT			, VALOR_CANASTA TEXT 	,"
//				+ " PUNTAJE_CLIENTE TEXT 				 , PORC_CUMP TEXT		, PESO_PUNT TEXT 		,"
//				+ " MONTO_A_COBRAR TEXT 																 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_produccion_semanal() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_produccion_semanal_gcs"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, CANTIDAD TEXT 		,"
//				+ " SEMANA TEXT					 		 , MONTO_VIATICO TEXT 	, MONTO_TOTAL TEXT 		,"
//				+ " PERIODO TEXT 						 , FEC_DESDE TEXT		, FEC_HASTA TEXT 		,"
//				+ " DESC_SUPERVISOR TEXT 				 , MONTO_X_POR TEXT		, CANT_CLIENTE TEXT 	 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_prom_alc_cuota_mensual_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_prom_alc_cuota_mensual_sup"
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, COD_VENDEDOR TEXT 	,"
//				+ " DESC_VENDEDOR TEXT					 , TIP_CARTERA TEXT    	, PORC_LOG TEXT 		,"
//				+ " PORC_ALC TEXT 						 , MONTO_PREMIO TEXT	, TOTAL_FACTURADO TEXT 	,"
//				+ " META TEXT         					 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_cuota_x_unidad_neg() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_cuota_x_und_neg "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, FEC_DESDE TEXT 		,"
//				+ " FEC_HASTA TEXT				 		 , COD_UNID_NEGOCIO TEXT, DESC_UNID_NEGOCIO TEXT,"
//				+ " PORC_PREMIO TEXT 					 , PORC_ALC_PREMIO TEXT	, MONTO_VENTA TEXT 		,"
//				+ " MONTO_CUOTA TEXT 					 , MONTO_A_COBRAR TEXT							 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_cobertura_mensual_sup() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cobertura_mensual_sup "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    	, TOT_CLI_CART TEXT 	,"
//				+ " CANT_POSIT TEXT				 		 , TOT_CLIEN_ASIG TEXT	, PORC_LOGRO TEXT		,"
//				+ " PORC_COB TEXT 						 , PREMIOS TEXT			,  MONTO_A_COBRAR TEXT	 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_liq_cuota_x_unidad_neg_vend() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_liq_cuota_x_und_neg_vend "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, FEC_DESDE TEXT 		,"
//				+ " FEC_HASTA TEXT				 		 , COD_VENDEDOR TEXT		, DESC_VENDEDOR TEXT	,"
//				+ " COD_UNID_NEGOCIO TEXT 				 , DESC_UNID_NEGOCIO TEXT	, COD_PERFIL_VEND TEXT 	,"
//				+ " PORC_PREMIO TEXT 					 , MONTO_VENTA TEXT			, MONTO_CUOTA TEXT 		,"
//				+ " MONTO_A_COBRAR TEXT					 , MONTO_PREMIO TEXT		, PORC_ALC_PREMIO TEXT	 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_cobertura_mensual_vend() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cobertura_mensual_vend "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
//				+ " DESC_VENDEDOR TEXT			 		 , TOT_CLI_CART TEXT		, CANT_POSIT TEXT		,"
//				+ " PORC_COB TEXT 				 		 , PORC_LOGRO TEXT			, PREMIOS TEXT 			,"
//				+ " MONTO_A_COBRAR TEXT					 , FEC_DESDE TEXT			, FEC_HASTA	TEXT		 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_cob_semanal_vend() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_cob_semanal_vend "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
//				+ " DESC_VENDEDOR TEXT			 		 , SEMANA TEXT				, TOT_CLIENTES TEXT		,"
//				+ " CLIENT_VENTAS TEXT 				 	 , PORC_COBERTURA TEXT		, PERIODO TEXT 			,"
//				+ " MONTO_A_COBRAR TEXT					 													 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createTableSvm_surtido_eficiente() {
//		sql = "CREATE TABLE IF NOT EXISTS svm_surtido_eficiente "
//				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, COD_EMPRESA TEXT    		, COD_VENDEDOR TEXT 	,"
//				+ " DESC_VENDEDOR TEXT			 		 , COD_CLIENTE TEXT			, COD_SUBCLIENTE TEXT	,"
//				+ " TIP_CLIENTE TEXT 				 	 , COD_ARTICULO TEXT		, TIP_SURTIDO TEXT 		 "
//				+ ")";
//
//		return sql;
//	}
//
//	public String createIndexSvm_surtido_eficiente() {
//		sql = "create index if not exists surtido_cod_empresa 		on svm_surtido_eficiente(COD_EMPRESA);"
//			+ "create index if not exists surtido_cod_vendedor 		on svm_surtido_eficiente(COD_VENDEDOR);"
//			+ "create index if not exists surtido_desc_vendedor 	on svm_surtido_eficiente(DESC_VENDEDOR);"
//			+ "create index if not exists surtido_cod_cliente 		on svm_surtido_eficiente(COD_CLIENTE);"
//			+ "create index if not exists surtido_cod_subcliente 	on svm_surtido_eficiente(COD_SUBCLIENTE);"
//			+ "create index if not exists surtido_tip_cliente 		on svm_surtido_eficiente(TIP_CLIENTE);"
//			+ "create index if not exists surtido_cod_articulo 		on svm_surtido_eficiente(COD_ARTICULO);"
//			+ "create index if not exists surtido_tip_surtido 		on svm_surtido_eficiente(TIP_SURTIDO);"
//			+ "";
//
//		return sql;
//	}
//
//	public String createIndexSvm_surtido_eficienteCompuesto() {
//		sql = "create index if not exists surtido_compuesto	"
//			+ "    on svm_surtido_eficiente(COD_EMPRESA,"
//			+ "								COD_VENDEDOR,"
//			+ "								DESC_VENDEDOR,"
//			+ "								COD_CLIENTE,"
//			+ "								COD_SUBCLIENTE,"
//			+ "								TIP_CLIENTE,"
//			+ "								COD_ARTICULO,"
//			+ "								TIP_SURTIDO);"
//			+ "";
//
//		return sql;
//	}
//
//}

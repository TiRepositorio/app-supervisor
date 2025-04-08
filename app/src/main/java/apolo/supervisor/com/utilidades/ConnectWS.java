//package apolo.supervisor.com.utilidades;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.Vector;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//
//import org.apache.commons.codec.binary.Base64;
//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;
//
//
//public class ConnectWS {
//	String NAMESPACE = "http://edsystem/servidor";
//	String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//	String METHOD_NAME = "";
//	String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
//
//	public void setMethodName(String name) {
//		METHOD_NAME = name;
//		SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
//	}
//
//	public String procesaNegociacionWS(String negociacion) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaActualizaNegociacion";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaNegociacion";
//
//		String resultado = "";
//
//		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("usuario", "edsystem");
//		request.addProperty("password", "#edsystem@polo");
//		request.addProperty("codEmpresa", "1");
//		request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//				.toString());
//		request.addProperty("vnegociacion", negociacion);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp;
//			sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			resultado = err;
//		}
//
//		return resultado;
//	}
//
//	public String procesaAutorizacionWS(String cabecera, String detalle) {
//
//		String resultado = "";
//
//		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("usuario", "edsystem");
//		request.addProperty("password", "#edsystem@polo");
//		request.addProperty("cabecera", cabecera);
//		request.addProperty("detalle", detalle);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp;
//			sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			resultado = err;
//		}
//
//		return resultado;
//	}
//
//	public String procesaSeguimientoPDV(String cab_gestor, String det_gestor,
//			String cab_cliente, String det_cliente, String cod_vendedor,
//			String cod_supervisor, String fecha_visita) {
//
//		String resultado = "";
//
//		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("codEmpresa", "1");
//		request.addProperty("usuario", "edsystem");
//		request.addProperty("password", "#edsystem@polo");
//		request.addProperty("codVendedor", cod_vendedor);
//		request.addProperty("codSupervisor", cod_supervisor);
//		request.addProperty("fecMovimiento", fecha_visita);
//		request.addProperty("cab_gestor", cab_gestor);
//		request.addProperty("det_gestor", det_gestor);
//		request.addProperty("cab_cliente", cab_cliente);
//		request.addProperty("det_cliente", det_cliente);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp;
//			sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			resultado = err;
//		}
//
//		return resultado;
//	}
//
//	public String procesaSeguimientoPDV(String cab_cliente, String det_cliente,
//			String cod_supervisor, String fecha_visita) {
//
//		String resultado = "";
//
//		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("codEmpresa", "1");
//		request.addProperty("usuario", "edsystem");
//		request.addProperty("password", "#edsystem@polo");
//		request.addProperty("codSupervisor", cod_supervisor);
//		request.addProperty("fecMovimiento", fecha_visita);
//		request.addProperty("cab_cliente", cab_cliente);
//		request.addProperty("det_cliente", det_cliente);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp;
//			sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			resultado = err;
//		}
//
//		return resultado;
//	}
//
//	public String procesaImeiSupervisor(String codSupervisor) {
//
//		String resultado = "";
//
//		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
//		request.addProperty("usuario", "edsystem");
//		request.addProperty("password", "#edsystem@polo");
//		request.addProperty("codEmpresa", "1");
//		request.addProperty("codSupervisor", codSupervisor);
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp;
//			sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			resultado = err;
//		}
//
//		return resultado;
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean onClickTraeArchivos() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ObtieneArchivosSupervisorNuevo";
//		final String SOAP_ACTION = "http://edsystem/servidor/ObtieneArchivosSupervisorNuevo";
//
//		SoapObject request = null;
//		Vector<SoapPrimitive> resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			//request.addProperty("codPersona", "19");
//			//request.addProperty("codEmpresa", "1");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (Vector<SoapPrimitive>) envelope.getResponse();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		try {
//
//			if (resultado.size() > 36) {
//				FileOutputStream fos = null;
//
//				/*
//				 * fos = new FileOutputStream(
//				 * "/data/data/apolo.supervisores.com/svm_vt_pedidos_cab.gzip");
//				 * fos.write(Base64.decodeBase64(resultado.get(0).toString()
//				 * .getBytes())); fos.close();
//				 *
//				 * fos = new FileOutputStream(
//				 * "/data/data/apolo.supervisores.com/svm_vt_pedidos_det.gzip");
//				 * fos.write(Base64.decodeBase64(resultado.get(1).toString()
//				 * .getBytes())); fos.close();
//				*/
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(0).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(1).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(2).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(3).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_evol_diaria_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(4).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(5).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_costo_venta_articulo.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(6).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_deuda_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(7).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_rebote_por_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(8).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_surtido_eficiente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(9).toString()
//						.getBytes()));
//				fos.close();
//
////				fos = new FileOutputStream(
////						"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_sup.gzip");
////				fos.write(Base64.decodeBase64(resultado.get(9).toString()
////						.getBytes()));
////				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_empresa.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(10).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_positivacion_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(11).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(12).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_premios_supervisores.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(13).toString()
//						.getBytes()));
//				fos.close();
//
////				fos = new FileOutputStream(
////						"/data/data/apolo.supervisores.com/svm_seguimiento_premio_sup.gzip");
////				fos.write(Base64.decodeBase64(resultado.get(13).toString()
////						.getBytes()));
////				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_control_general_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(14).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_supervisor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(15).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(16).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_motivo_analisis_vendedor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(17).toString()
//						.getBytes()));
//				fos.close();
//
//				// fos = new FileOutputStream(
//				// "/data/data/apolo.supervisores.com/svm_asistencia_vendedores.gzip");//svm_dia_visita_ruteo
//				// fos.write(Base64.decodeBase64(resultado.get(18).toString()
//				// .getBytes()));
//				// fos.close();
//				//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.gzip");// svm_dia_visita_ruteo
//				fos.write(Base64.decodeBase64(resultado.get(18).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_prom_vendedor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(19).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_categoria.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(20).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_retorno_comentario.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(21).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_periodo_foto.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(22).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(23).toString()
//						.getBytes()));
//				fos.close();
//
//				// nuevos para venta
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_list_prec.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(24).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_articulos_precios.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(25).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_st_articulos.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(26).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_condicion_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(27).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_vendedor_pedido.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(28).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_vendedor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(29).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_ultima_venta_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(30).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_prom_articulos_tar_cred.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(31).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cond_venta_vendedor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(32).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_motivo_no_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(33).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_promociones_perfilMerc.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(34).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_movimiento_gestor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(35).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_promociones_art_cab.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(36).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_promociones_art_det.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(37).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(38).toString()
//						.getBytes()));
//				fos.close();
//
//				//Insertar aca nueva importacizzn
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(39).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_premios_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(40).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_produccion_semanal_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(41).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_premios_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(42).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_marca_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(43).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_vis_vendedores.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(44).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(45).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_motivos_sd_dev.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(46).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(47).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(48).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(49).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(50).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(51).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(52).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(53).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(54).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(55).toString()
//						.getBytes()));
//				fos.close();
//
//
//			} else {
//
//				return false;
//			}
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		/*
//		 * descomprimir("/data/data/apolo.supervisores.com/svm_vt_pedidos_cab.gzip",
//		 * "/data/data/apolo.supervisores.com/svm_vt_pedidos_cab.txt");
//		 * descomprimir
//		 * ("/data/data/apolo.supervisores.com/svm_vt_pedidos_det.gzip",
//		 * "/data/data/apolo.supervisores.com/svm_vt_pedidos_det.txt");
//		 */
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.gzip",
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.gzip",
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea.gzip",
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_evol_diaria_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_evol_diaria_venta.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.gzip",
//				"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_costo_venta_articulo.gzip",
//				"/data/data/apolo.supervisores.com/svm_costo_venta_articulo.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_deuda_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_rebote_por_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_rebote_por_vendedor.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_surtido_eficiente.gzip",
//				"/data/data/apolo.supervisores.com/svm_surtido_eficiente.txt");
////		descomprimir(
////				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_sup.gzip",
////				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_empresa.gzip",
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_empresa.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_positivacion_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.gzip",
//				"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_premios_supervisores.gzip",
//				"/data/data/apolo.supervisores.com/svm_premios_supervisores.txt");
////		descomprimir(
////				"/data/data/apolo.supervisores.com/svm_seguimiento_premio_sup.gzip",
////				"/data/data/apolo.supervisores.com/svm_seguimiento_premio_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_control_general_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_control_general_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_vendedor.gzip",
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_vendedor.txt");
//		// descomprimir(
//		// "/data/data/apolo.supervisores.com/svm_asistencia_vendedores.gzip",
//		// "/data/data/apolo.supervisores.com/svm_asistencia_vendedores.txt");//svm_cliente_dia_visita_ruteo
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_prom_vendedor.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_prom_vendedor.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_categoria.gzip",
//				"/data/data/apolo.supervisores.com/svm_categoria.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_retorno_comentario.gzip",
//				"/data/data/apolo.supervisores.com/svm_retorno_comentario.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_periodo_foto.gzip",
//				"/data/data/apolo.supervisores.com/svm_periodo_foto.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//
//		// nuevo venta
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_list_prec.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_list_prec.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_articulos_precios.gzip",
//				"/data/data/apolo.supervisores.com/svm_articulos_precios.txt");
//
//		descomprimir("/data/data/apolo.supervisores.com/svm_st_articulos.gzip",
//				"/data/data/apolo.supervisores.com/svm_st_articulos.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_condicion_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_condicion_venta.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_vendedor_pedido.gzip",
//				"/data/data/apolo.supervisores.com/svm_vendedor_pedido.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_vendedor.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_vendedor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_ultima_venta_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_ultima_venta_cliente.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_prom_articulos_tar_cred.gzip",
//				"/data/data/apolo.supervisores.com/svm_prom_articulos_tar_cred.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cond_venta_vendedor.gzip",
//				"/data/data/apolo.supervisores.com/svm_cond_venta_vendedor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_motivo_no_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_motivo_no_venta.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_promociones_perfilMerc.gzip",
//				"/data/data/apolo.supervisores.com/svm_promociones_perfilMerc.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_movimiento_gestor.gzip",
//				"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_promociones_art_cab.gzip",
//				"/data/data/apolo.supervisores.com/svm_promociones_art_cab.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_promociones_art_det.gzip",
//				"/data/data/apolo.supervisores.com/svm_promociones_art_det.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.gzip",
//				"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//
//		//Insertar aca nueva importacion
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_premios_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_premios_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//
//		//NUEVA TABLA --RENOMBRAR ARCHIVO
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_marca_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_marca_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_vis_vendedores.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_vis_vendedores.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_motivos_sd_dev.gzip",
//				"/data/data/apolo.supervisores.com/svm_motivos_sd_dev.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.gzip",
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//
////		svm_liq_cuota_x_und_neg_vend
////		svm_cobertura_mensual_vend
////		svm_liq_canasta_cte_sup
////		svm_liq_canasta_marca_sup
////		svm_produccion_semanal_gcs
////		svm_prom_alc_cuota_mensual_sup
////		svm_liq_cuota_x_und_neg
////		svm_cobertura_mensual_sup
////		svm_cob_semanal_vend
//
//		return true;
//
//	}
//
//	public boolean onClickGeneraArchivos() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "GeneraArchivosSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/GeneraArchivosSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//			request.addProperty("codPersona", Aplicacion._cod_persona);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 480000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*") == -1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public boolean onClickGeneraArchivosInformes() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "GeneraInformeSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/GeneraInformeSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 480000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*") == -1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public boolean onClickGeneraInformeSegSupervisor() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "GeneraInformeSegSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/GeneraInformeSegSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//			request.addProperty("codPersona", Aplicacion._cod_persona);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 480000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*") == -1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public boolean onClickGeneraInformeVisitaSupervisor() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "GeneraInformeVisitaSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/GeneraInformeVisitaSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//			request.addProperty("codPersona", Aplicacion._cod_persona);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 480000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*") == -1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public static boolean descomprimir(String inFilePath, String outFilePath) {
//		try {
//
//			// Descomprimir
//			// String inFilename =
//			// "/data/data/com.sql.lite/databases/fichero.gzip";
//			String inFilename = inFilePath;
//			GZIPInputStream in = new GZIPInputStream(new FileInputStream(
//					inFilename));
//
//			// String outFilename =
//			// "/data/data/com.sql.lite/databases/fichero.txt";
//			String outFilename = outFilePath;
//			FileOutputStream out = new FileOutputStream(outFilename);
//
//			byte[] buf = new byte[1024];
//			int len;
//			while ((len = in.read(buf)) > 0) {
//				out.write(buf, 0, len);
//			}
//
//			out.close();
//			in.close();
//
//			File fil = new File(inFilePath);
//			fil.delete();
//
//			return true;
//
//		} catch (FileNotFoundException e) {
//			String err = e.getMessage();
//			err = "" + err;
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		return false;
//	}
//
//	public boolean onClickObtieneInstalador() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaInstaladorSupervisores";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaInstaladorSupervisores";
//
//		SoapObject request = null;
//
//		String resultado = null;
//
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			FileOutputStream fos = null;
//
//			fos = new FileOutputStream("/sdcard/supervisor_02.apk");
//			fos.write(Base64.decodeBase64(resultado.toString().getBytes()));
//			fos.close();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public String onClickProcesaActualizaDatosClienteSup(String vclientes,
//			String codVendedor, String codSupervisor) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaActualizaClienteSupAct";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaClienteSupAct";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("vcodSupervisor", codSupervisor);
//			request.addProperty("vcodVendedor", codVendedor);
//			request.addProperty("vclientes", vclientes.replace("''", " ")
//					.replace("'", ""));
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//
//		}
//
//		return resultado;
//	}
//
//	public String onClickProcesaRuteoSemanal(String vdetalle) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaMarcacion";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaMarcacion";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codVendedor", Aplicacion.et_login.getText()
//					.toString());
//			request.addProperty("detalle", vdetalle);
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//
//		}
//
//		return resultado;
//	}
//
//	@SuppressWarnings({ "resource", "static-access" })
//	public String onClickProcesaImagen(String cab, String det1,
//			String cod_vendedor) {
//
//		comprimir("/data/data/apolo.supervisores.com/detalle2.txt",
//				"/data/data/apolo.supervisores.com/detalle2.txt.gz");
//
//		String resultado = "";
//		// DataInputStream input;
//		BufferedInputStream bis;
//		BufferedOutputStream bos;
//		int in;
//		byte[] byteArray;
//		// Fichero a transferir
//		final String filename = "/data/data/apolo.supervisores.com/detalle2.txt.gz";
//
//		try {
//			final File localFile = new File(filename);
//			Socket client = new Socket("201.217.23.214", 6170);
//			bis = new BufferedInputStream(new FileInputStream(localFile));
//			bos = new BufferedOutputStream(client.getOutputStream());
//			// Enviamos el nombre del fichero
//			DataOutputStream dos = new DataOutputStream(
//					client.getOutputStream());
//			dos.writeUTF(cod_vendedor);
//			// Enviamos el fichero
//			byteArray = new byte[8192];
//			while ((in = bis.read(byteArray)) != -1) {
//				bos.write(byteArray, 0, in);
//			}
//
//			bis.close();
//			bos.close();
//
//			Aplicacion.utilidades.crearArchivo(cab, "cabecera.txt", true);
//			Aplicacion.utilidades.crearArchivo(det1, "detalle1.txt", true);
//
//			try {
//				comprimir("/data/data/apolo.supervisores.com/cabecera.txt",
//						"/data/data/apolo.supervisores.com/cabecera.txt.gz");
//				comprimir("/data/data/apolo.supervisores.com/detalle1.txt",
//						"/data/data/apolo.supervisores.com/detalle1.txt.gz");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			cab = "";
//			det1 = "";
//
//			try {
//				File file = new File(
//						"/data/data/apolo.supervisores.com/cabecera.txt.gz");
//				byteArray = new byte[(int) file.length()];
//
//				FileInputStream fileInputStream;
//				fileInputStream = new FileInputStream(file);
//				fileInputStream.read(byteArray);
//				String byteArrayStr = new String(Base64.encodeBase64(byteArray));
//				cab = byteArrayStr;
//
//				file = new File(
//						"/data/data/apolo.supervisores.com/detalle1.txt.gz");
//				byteArray = new byte[(int) file.length()];
//
//				fileInputStream = new FileInputStream(file);
//				fileInputStream.read(byteArray);
//				byteArrayStr = new String(Base64.encodeBase64(byteArray));
//				det1 = byteArrayStr;
//
//			} catch (FileNotFoundException e1) {
//				e1.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (OutOfMemoryError e) {
//				String err = e.getMessage();
//				err = err + "";
//			}
//
//			final String NAMESPACE = "http://edsystem/servidor";
//			// final String NAMESPACE = "http://array";
//			final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//			// final String URL =
//			// "http://IndustriaSun:8080/prosystemWS/prosystemWS/prosystem";
//			final String METHOD_NAME = "ProcesaImagen";
//			final String SOAP_ACTION = "http://edsystem/servidor/ProcesaImagen";
//
//			SoapObject request = null;
//
//			try {
//				request = new SoapObject(NAMESPACE, METHOD_NAME);
//				request.addProperty("usuario", "edsystem");
//				request.addProperty("password", "#edsystem@polo");
//				request.addProperty("codEmpresa", "1");
//				request.addProperty("codVendedor", cod_vendedor);
//				request.addProperty("cabecera", cab);
//				request.addProperty("detalle1", det1);
//			} catch (Exception e) {
//				String err = e.getMessage();
//				err = "" + err;
//				return err;
//			}
//
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//					SoapEnvelope.VER11);
//
//			envelope.dotNet = false;
//
//			envelope.setOutputSoapObject(request);
//
//			HttpTransportSE transporte = new HttpTransportSE(URL, 960000);
//
//			try {
//				transporte.call(SOAP_ACTION, envelope);
//				SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//				resultado = sp.toString();
//
//			} catch (Exception e) {
//
//				String err = e.getMessage();
//				err = "" + err;
//				return err;
//
//			}
//
//			return resultado;
//
//		} catch (Exception e) {
//			System.err.println(e);
//			return e.getMessage();
//		}
//
//	}
//
//	public static void comprimir(String inPutFile, String outPutFile) {
//		File fichero = new File(outPutFile);
//		File Archivo = new File(inPutFile);
//
//		if (fichero.exists()) {
//			fichero.delete();
//		}
//
//		try {
//			String inFilename = inPutFile;
//			FileInputStream in = new FileInputStream(inFilename);
//
//			String outFilename = outPutFile;
//			GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(
//					outFilename));
//
//			byte[] buf = new byte[1024];
//			int len;
//			while ((len = in.read(buf)) > 0) {
//				out.write(buf, 0, len);
//			}
//
//			out.finish();
//			in.close();
//			out.close();
//
//			if (Archivo.exists()) {
//				Archivo.delete();
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public String onClickProcesaMarcacionAsistencia(String cod_vendedor,
//			String vmarcaciones) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaMarcacion";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaMarcacion";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codEmpresa", "1");
//			request.addProperty("codVendedor", cod_vendedor);
//			request.addProperty("detalle", vmarcaciones);
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//
//		}
//
//		return resultado;
//	}
//
//	public String onClickProcesaActualizaDatosClienteFinal(String cod_vendedor,
//			String vclientes, String vFotoFachada) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaActualizaClienteFinal";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaClienteFinal";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("vcodVendedor", cod_vendedor);
//			request.addProperty("vclientes", vclientes.replace("''", " ")
//					.replace("'", ""));
//			request.addProperty("vfoto_fachada", vFotoFachada);
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//
//		}
//
//		return resultado;
//	}
//
//	public String onClickEnviaMarcacionVisita2(String _nopositivado) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaNoVenta";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaNoVenta";
//
//		SoapObject request;
//		try {
//
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("vcodEmpresa", "1");
//			request.addProperty("vcodVendedor", Aplicacion._cod_prom_vendedor);
//			request.addProperty("nopositivado", _nopositivado);
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//		envelope.dotNet = false;
//		envelope.setOutputSoapObject(request);
//		HttpTransportSE transporte = new HttpTransportSE(URL);
//		String res = "";
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//
//			SoapPrimitive resultado_xml = (SoapPrimitive) envelope
//					.getResponse();
//
//			res = resultado_xml.toString();
//		} catch (Exception e) {
//			res = e.getMessage();
//		}
//
//		if (res.contains("unique constraint (INV")) {
//			res = "01*Ya se guardo la justificacion";
//		}
//		return res;
//	}
//
//	public String onClickProcesaSolicitudTC(String cod_cliente,
//			String vcliente, String foto1, String foto2) {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaSolicituTarjetaCredito";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaSolicituTarjetaCredito";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("vcodVendedor", Aplicacion._cod_prom_vendedor);
//			request.addProperty("vcodCliente", cod_cliente);
//			request.addProperty("vclientes", vcliente.replace("''", " ")
//					.replace("'", ""));
//			request.addProperty("vfoto_frente", foto1);
//			request.addProperty("vfoto_dorso", foto2);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		return resultado;
//
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean onClickTraeArchivosInformes() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ObtieneInformesSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesSupervisor";
//
//		SoapObject request = null;
//		Vector<SoapPrimitive> resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (Vector<SoapPrimitive>) envelope.getResponse();
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		try {
//
//			if (resultado.size() > 4) {
//				FileOutputStream fos = null;
//
//				// NUEVO INFORME
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.gzip");// svm_dia_visita_ruteo
//				fos.write(Base64.decodeBase64(resultado.get(0).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(1).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(2).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(3).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(4).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(5).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(6).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(7).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(8).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(9).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_premios_vend.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(10).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(11).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_evolucion_diaria_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(12).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_deuda_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(13).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(14).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_movimiento_gestor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(15).toString()
//						.getBytes()));
//				fos.close();
//
//			} else {
//				return false;
//			}
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		// nuevo informe//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.gzip",
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.gzip",
//				"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.gzip",
//				"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_sem_visita_ruteo.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_sem_visita_ruteo.txt");
//
////		descomprimir(
////				"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.gzip",
////				"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_premios_vend.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_evolucion_diaria_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_evolucion_diaria_venta.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_deuda_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.gzip",
//				"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_movimiento_gestor.gzip",
//				"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//
//		return true;
//
//	}
//
//	public boolean onClickTraeArchivosInformesSegSupervisor() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ObtieneInformesSegSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesSegSupervisor";
//
//		SoapObject request = null;
//		Vector<SoapPrimitive> resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (Vector<SoapPrimitive>) envelope.getResponse();
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		try {
//
//			if (resultado.size() > 4) {
//				FileOutputStream fos = null;
//
//				// NUEVO INFORME
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_premios_sup.gzip");// svm_dia_visita_ruteo
//				fos.write(Base64.decodeBase64(resultado.get(0).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(1).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(2).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(3).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(4).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(5).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(6).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(7).toString()
//						.getBytes()));
//				fos.close();
//
//
//			} else {
//				return false;
//			}
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		// nuevo informe//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_premios_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.gzip",
//				"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.gzip",
//				"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.gzip",
//				"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.gzip",
//				"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//
//		return true;
//
//	}
//
//
//	public boolean onClickGeneraArchivosExtractoTarjeta() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		// final String URL =
//		// "http://192.168.42.114:8080/pruebaWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaActualizaPrepagaSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaPrepagaSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//			request.addProperty("codEmpresa", "1");
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive) envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*") == -1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//		return true;
//
//	}
//
//	public boolean onClickTraeArchivosExtractoTarjeta() {
//
//		final String NAMESPACE   = "http://edsystem/servidor";
//		final String URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "OptieneDatoPrepagaSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/OptieneDatoPrepagaSupervisor";
//
//		SoapObject request = null;
//		SoapPrimitive resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario","edsystem");
//			request.addProperty("password","#edsystem@polo");
//			request.addProperty("codEmpresa","1");
//			request.addProperty("codSupervisor",Aplicacion.et_login.getText().toString());
//		} catch(Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (SoapPrimitive)envelope.getResponse();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//
//        try {
//
//        	if (resultado != null) {
//        		FileOutputStream fos = null;
//
//    			fos = new FileOutputStream("/data/data/apolo.supervisores.com/svm_extracto_tarjeta_pro.gzip");
//    			fos.write(Base64.decodeBase64(resultado.toString().getBytes()));
//    			fos.close();
//
//        	} else {
//
//        		return false;
//        	}
//
//
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//        descomprimir("/data/data/apolo.supervisores.com/svm_extracto_tarjeta_pro.gzip","/data/data/apolo.supervisores.com/svm_extracto_tarjeta_pro.txt");
//
//        return true;
//
//	}
//
//	public boolean onClickTraeArchivosInformesVisitaSupervisor() {
//
//		final String NAMESPACE = "http://edsystem/servidor";
//		final String URL = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ObtieneInformesVisitaSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ObtieneInformesVisitaSupervisor";
//
//		SoapObject request = null;
//		Vector<SoapPrimitive> resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario", "edsystem");
//			request.addProperty("password", "#edsystem@polo");
//			request.addProperty("codSupervisor", Aplicacion.et_login.getText()
//					.toString());
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (Vector<SoapPrimitive>) envelope.getResponse();
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		try {
//
//			if (resultado.size() > 2) {
//				FileOutputStream fos = null;
//
//				// NUEVO INFORME
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.gzip");// svm_dia_visita_ruteo
//				fos.write(Base64.decodeBase64(resultado.get(0).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_supervisor.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(1).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_cliente_supervisores.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(2).toString()
//						.getBytes()));
//				fos.close();
//
//				fos = new FileOutputStream(
//						"/data/data/apolo.supervisores.com/svm_positivacion_cliente.gzip");
//				fos.write(Base64.decodeBase64(resultado.get(3).toString()
//						.getBytes()));
//				fos.close();
//
//			} else {
//				return false;
//			}
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//		// nuevo informe//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisores.gzip",
//				"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//
//		descomprimir(
//				"/data/data/apolo.supervisores.com/svm_positivacion_cliente.gzip",
//				"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//
//		return true;
//
//	}
//
//
//	/**************SINCRONIZA ANUNCIO MOVIL**************/
//
//	public boolean onClickGeneraArchivoAnuncio() {
//
//		final String NAMESPACE   = "http://edsystem/servidor";
//		final String URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
////		final String URL         = "http://10.1.1.253:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaActualizaAnuncionSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaActualizaAnuncionSupervisor";
//
//		SoapObject request = null;
//		String resultado = null;
//
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario","edsystem");
//			request.addProperty("password","#edsystem@polo");
//			request.addProperty("codEmpresa","1");
//			request.addProperty("codSupervisor",Aplicacion.et_login.getText().toString());
//		} catch(Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive)envelope.getResponse();
//			resultado = sp.toString();
//
//			if (!(resultado.indexOf("01*")==-1)) {
//				return false;
//			}
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//	    return true;
//
//	}
//
//	public boolean onClickTraeArchivosAnuncio() {
//
//		final String NAMESPACE   = "http://edsystem/servidor";
//		final String URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
////		final String URL         = "http://10.1.1.253:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "OptieneDatoAnuncionSupervisor";
//		final String SOAP_ACTION = "http://edsystem/servidor/OptieneDatoAnuncionSupervisor";
//
//		SoapObject request = null;
//		SoapPrimitive resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario","edsystem");
//			request.addProperty("password","#edsystem@polo");
//			request.addProperty("codEmpresa","1");
//			request.addProperty("codSupervisor",Aplicacion.et_login.getText().toString());
//		} catch(Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//		}
//
//
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//		envelope.dotNet = false;
//
//		envelope.setOutputSoapObject(request);
//
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			resultado = (SoapPrimitive)envelope.getResponse();
//
//		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//
//        try {
//
//        	if (resultado != null) {
//        		FileOutputStream fos = null;
//
//    			fos = new FileOutputStream("/data/data/apolo.supervisores.com/svm_anuncio_movil.gzip");
//    			fos.write(Base64.decodeBase64(resultado.toString().getBytes()));
//    			fos.close();
//
//        	} else {
//
//        		return false;
//        	}
//
//
//
//		} catch (IOException e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return false;
//
//		}
//
//        descomprimir("/data/data/apolo.supervisores.com/svm_anuncio_movil.gzip","/data/data/apolo.supervisores.com/svm_anuncio_movil.txt");
//
//        return true;
//
//	}
//
//	//-----------------------------------------------------------------------------------------//
//	//-----------------------------------------------------------------------------------------//
//	//Nuevo para prueba
//	public String onClickProcesaVersion() {
//
//		final String NAMESPACE   = "http://edsystem/servidor";
//		final String URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaVersionVendedor";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaVersionVendedor";
//
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario","edsystem");
//			request.addProperty("password","#edsystem@polo");
//			request.addProperty("codVendedor",Aplicacion.et_login.getText().toString());
//		} catch(Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//		envelope.dotNet = false;
//		envelope.setOutputSoapObject(request);
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive)envelope.getResponse();
//			resultado = sp.toString();
//
// 		} catch (Exception e) {
//
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//
//		}
//
//        return resultado;
//
//	}
//
//	public String onClick_procesa_envia_solicitud_sd(String cod_repartidor, String vchecklist1,String vchecklist2) {
//
//		final String NAMESPACE   = "http://edsystem/servidor";
//		final String URL         = "http://sistmov.apolo.com.py:8280/edsystemWS/edsystemWS/edsystem";
//		final String METHOD_NAME = "ProcesaAutorizaSDSup";
//		final String SOAP_ACTION = "http://edsystem/servidor/ProcesaAutorizaSDSup"; //ProcesaAutorizaSDVend
//
//
//		// Necesitamos corregir el nombfre de la pagina
//		SoapObject request = null;
//		String resultado = null;
//		try {
//			request = new SoapObject(NAMESPACE, METHOD_NAME);
//			request.addProperty("usuario","edsystem");
//			request.addProperty("password","#edsystem@polo");
//			request.addProperty("codEmpresa","1");
//			request.addProperty("codRepartidor",cod_repartidor);
//			request.addProperty("cabecera", vchecklist1);
//			request.addProperty("detalle", vchecklist2);
//
//		} catch(Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//		envelope.dotNet = false;
//		envelope.setOutputSoapObject(request);
//		HttpTransportSE transporte = new HttpTransportSE(URL, 240000);
//
//		try {
//			transporte.call(SOAP_ACTION, envelope);
//			SoapPrimitive sp = (SoapPrimitive)envelope.getResponse();
//			resultado = sp.toString();
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//			return err;
//		}
//        return resultado;
//	}
//	//-----------------------------------------------------------------------------------------//
////
//
//}

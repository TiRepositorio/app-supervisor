//package apolo.supervisor.com.utilidades;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.sql.ResultSet;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Vector;
//
//import android.R.string;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.app.ProgressDialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//import apolo.supervisores.com.R;
//
//public class sincronizar extends Activity {
//	String _sqldelete, _query;
//	ResultSet rsconta = null;
//	ResultSet rs = null;
//	private ProgressBar progressBar;
//	private ProgressBar progressBarProcess;
//	boolean respuestaWS;
//	private TextView tvCab;
//	private TextView tvDet;
//	private TextView tvcontrolcab;
//	private TextView tvcontroldet;
//	private TextView tvmeta_cliente;
//	private TextView tvmeta_linea;
//	private TextView tvevo_venta;
//	private TextView tvpreciofijo;
//	private TextView tvcosto_articulo;
//	private TextView tvdeuda_cliente_vendedor;
//	private TextView tvrebote_por_vendedor;
//
//	BufferedWriter bw;
//	String sincronizando = "N";
//	String sincronizaAnuncio = "N";
//
//	ProgressDialog pbarDialog;
//	final Context context = this;
//
//	Integer _contador;
//	Integer _cont;
//
//	int i = 0;
//	@SuppressWarnings("rawtypes")
//	Vector res = null;
//	String imeiBD = "";
//
//	private Thread th;
//	Handler handler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			progressBar.setProgress(0);
//			tvCab.setText("");
//			tvDet.setText("");
//			tvcontrolcab.setText("");
//			tvcontroldet.setText("");
//			tvmeta_cliente.setText("");
//			tvmeta_linea.setText("");
//			tvevo_venta.setText("");
//			tvpreciofijo.setText("");
//			tvcosto_articulo.setText("");
//			tvdeuda_cliente_vendedor.setText("");
//			tvrebote_por_vendedor.setText("");
//
//			if (th != null) {
//				Thread temp = th;
//				th = null;
//				temp.interrupt();
//			}
//
//		};
//	};
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//
//		setContentView(R.dialogo_bonificacion_combo.sincronizacion);
//
//
//		String _bloquear = "N";
//
//		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
//		Date d = null;
//		Date d1 = null;
//		Calendar cal = Calendar.getInstance();
//
//		if (Aplicacion._fecha_ult_actualizacion.toString().equals("")) {
//
//			_bloquear = "S";
//
//		} else {
//
//			try {
//				d = dfDate
//						.parse(Aplicacion._fecha_ult_actualizacion.toString());
//				d1 = dfDate.parse(dfDate.format(cal.getTime()));
//			} catch (java.text.ParseException e) {
//
//				e.printStackTrace();
//
//			}
//
//			int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
//
//			if (diffInDays != 0) {
//
//				_bloquear = "S";
//			}
//
//		}
//
//		if (_bloquear.equals("S")) {
//			Aplicacion._primera_vez = 1;
//		}
//
//		progressBar = (ProgressBar) findViewById(R.id.progressBar);
//		progressBarProcess = (ProgressBar) findViewById(R.id.progressBarProcess);
//		tvCab = (TextView) findViewById(R.id.tvCab);
//		tvDet = (TextView) findViewById(R.id.lblDet);
//		tvcontrolcab = (TextView) findViewById(R.id.lblcontrol_cab);
//		tvcontroldet = (TextView) findViewById(R.id.lblcontrol_det);
//		tvmeta_cliente = (TextView) findViewById(R.id.lblmetas_cliente);
//		tvmeta_linea = (TextView) findViewById(R.id.lblmetas_linea);
//		tvevo_venta = (TextView) findViewById(R.id.lblevolucion_diaria);
//		tvpreciofijo = (TextView) findViewById(R.id.lblprecio_fijo);
//		tvcosto_articulo = (TextView) findViewById(R.id.lblcosto_articulo);
//		tvdeuda_cliente_vendedor = (TextView) findViewById(R.id.lbldeuda_cliente_vendedor);
//		tvrebote_por_vendedor = (TextView) findViewById(R.id.lblrebote_por_vendedor);
//
//		_contador = 1;
//		_cont = 1;
//
//		String select = "Select ULTIMA_VEZ from svm_vendedor_pedido";
//		Cursor cursor;
//		String mensaje = "Debe esperar 15 minutos para volver a sincronizar";
//		Date fechaUltVez;
//
//		DateFormat hourFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//		try {
//			cursor = Aplicacion.bdatos.rawQuery(select, null);
//			cursor.moveToFirst();
//			String f = cursor.getString(cursor.getColumnIndex("ULTIMA_VEZ"));
//			fechaUltVez = hourFormat.parse(f);
//			Date fecha_act = new Date();
//			long dif = fecha_act.getTime() - fechaUltVez.getTime();
//			long tiempo = 1000 * 60 * 15;
//
//			if (dif < tiempo && Aplicacion._tip_sincroniza == "T"
//							 && !Aplicacion._imei.equals("355299076199057")
//							 && !Aplicacion._imei.equals("358212085168242")
//							 && !Aplicacion._imei.equals("356556072264857")
//							 && !Aplicacion._imei.equals("356074080243319")
//							 && !Aplicacion._imei.equals("864466042333557")
//							 ) {
//				Builder builder2 = new Builder(sincronizar.this);
//
//				builder2.setMessage(mensaje);
//
//				builder2.setNeutralButton(("OK"), new OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Aplicacion._tip_sincroniza = "T";
//						finish();
//
//					}
//				});
//
//				AlertDialog alertDialog2 = builder2.create();
//				alertDialog2.show();
//				alertDialog2.setCancelable(false);
//				return;
//			}
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			Toast.makeText(this, err, Toast.LENGTH_LONG).show();
//		}
//
//		if (Aplicacion.utilidades.isOnline(sincronizar.this)
//				&& Aplicacion.utilidades.pingApolo(sincronizar.this)) {
//
//
//			verificaAnuncio();
//
//			if(Aplicacion._tip_sincroniza == "E"){
//				Aplicacion._tip_sincroniza = "T";
//				new PbtExtractoTarjetaPro1().execute();
//			}else{
//				new performBackgroundTask3().execute();
//			}
//
//		} else {
//			Toast.makeText(sincronizar.this,
//					"Verifique su conexion a internet y vuelva a intentarlo",
//					Toast.LENGTH_SHORT).show();
//		}
//
//	}
//
//	private void verificaAnuncio(){
//
//		try {
//
//			Cursor cursor = Aplicacion.bdatos.query("svm_anuncio_movil",
//					new String[] { "FECHA" },
//					null, null, null, null, null);
//
//			int nreg = cursor.getCount();
//
//			if (nreg != 0) {
//				cursor.moveToLast();
//
//				SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
//		        Date d = null;
//		        Date d1 = null;
//		        Calendar cal = Calendar.getInstance();
//
//		        d = dfDate.parse(cursor.getString(cursor.getColumnIndex("FECHA")));
//	            d1 = dfDate.parse(dfDate.format(cal.getTime()));
//
//
//	            try {
//		             d = dfDate.parse(cursor.getString(cursor.getColumnIndex("FECHA")));
//		             d1 = dfDate.parse(dfDate.format(cal.getTime()));
//		         } catch (java.text.ParseException e) {
//		         	e.printStackTrace();
//		         }
//
//				int diffInDays = (int) ((d1.getTime() - d.getTime())/ (1000 * 60 * 60 * 24));
//
//		        if (diffInDays!=0) {
//		        	deleteTablaAnuncio();
//		        	sincronizaAnuncio = "S";
//		        }
//			} else{
//				sincronizaAnuncio = "S";
//			}
//
//		} catch (Exception e) {
//			 e.getMessage();
//		}
//
//	}
//
//	@Override
//	public void onBackPressed() {
//		if (sincronizando.toString().equals("S")) {
//			return;
//		} else {
//			super.onBackPressed();
//		}
//	}
//
//	// constructor
//	public void datos(int id, String cod_medv, String cod_boca,
//			String cod_prod, String cod_vari, String inv_act, String compras,
//			String siventa, String lati, String longi, Double precio,
//			Double inv_adep, Double precio_pack
//
//	) {
//
//	}
//
//	public void onvolver_sincronizar(View arg0) {
//
//		Log.i("onclick", "BEgin");
//		startActivity(new Intent(sincronizar.this, Aplicacion.class));
//		finish();
//
//		Toast.makeText(this, "De vuelta al Menu Principal", Toast.LENGTH_SHORT)
//				.show();
//	}
//
//	public void sincroniza() {
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					String erra = Boolean.toString(respuestaWS);
//					erra = erra + "";
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//						int auxActual = 0, auxInc = 3;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						borraTablas();
//
//						Aplicacion.bdatos.beginTransaction();
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvCab.setText("Act. Tabla de Pedidos Cab.");
//
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vt_pedidos_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableVT_NEGOCIACION_VENTA_CAB();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vt_pedidos_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"SER_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"NRO_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"COD_CONDICION_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"DESC_COND_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"COD_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"DESC_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"COD_MONEDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"DESC_MONEDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 20:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 21:
//											values.put(
//													"IND_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 22:
//											values.put(
//													"IND_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 23:
//											values.put(
//													"IND_VAR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 24:
//											values.put(
//													"IND_BON",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 25:
//											values.put(
//													"PORC_DESC_VAR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 26:
//											values.put(
//													"DESCUENTO_VAR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 27:
//											values.put(
//													"COD_PERFIL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 28:
//											values.put(
//													"DESC_PERFIL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOT_COMPROBANTE",
//													eliminarNull(
//															linea.substring(
//																	ini,
//																	linea.length()))
//															.replace(";", ""));
//											values.put("PENDIENTE", "N");
//
//											try {
//												Aplicacion.bdatos
//														.insert("vt_negociacion_venta_cab",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvCab.setText("Act. Tabla de Pedidos Cab. (ok)");
//								tvDet.setText("Act. Tabla de Pedidos Det ");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vt_pedidos_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableVT_NEGOCIACION_VENTA_DET();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vt_pedidos_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"SER_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NRO_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"ORDEN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PRECIO_UNITARIO_C_IVA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"PRECIO_LISTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_UNIDAD_MEDIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESC_UNIDAD_MEDIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"MONTO_TOTAL_CONIVA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"IND_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"IND_VAR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"IND_BON",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"IND_BLOQUEADO",
//													eliminarNull(
//															linea.substring(
//																	ini,
//																	linea.length()))
//															.replace(";", ""));
//
//											if (values.get("IND_BLOQUEADO")
//													.toString().equals("S")
//													|| values.get("IND_BON")
//															.toString()
//															.equals("S")) {
//												values.put("IND_AUTORIZACION",
//														"P");
//											} else {
//												values.put("IND_AUTORIZACION",
//														"B");
//											}
//											try {
//												Aplicacion.bdatos
//														.insert("vt_negociacion_venta_det",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvDet.setText("Act. Tabla de Pedidos Det. (ok)");
//								tvcontrolcab
//										.setText("Act. Tabla Control de Venta Cab");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_cab();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"RUTEO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"POSITIVADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"POS_FUERA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_cab",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcontrolcab
//										.setText("Act. Tabla Control de Venta Cab. (ok)");
//								tvcontroldet
//										.setText("Act. Tabla Control de Venta Det.");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_det();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"PRECIO_UNITARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"TOT_DESCUENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_det",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcontroldet
//										.setText("Act. Tabla Control de Venta Det. (ok)");
//								tvmeta_cliente
//										.setText("Act. Tabla de Metas y Punt. por Cliente ");
//
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_metas_punto_por_cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"CODIGO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CIUDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"MAYOR_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"VENTA_3",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"MIX_3",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"VENTA_4",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"MIX_4",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"METAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"TOT_SURTIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"MES_1",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"MES_2",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MES_2",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_metas_punto_por_cliente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcontroldet
//										.setText("Act. Tabla de metas y Punt. por cliente (ok)");
//								tvmeta_cliente
//										.setText("Act. Tabla de motivos de solicitud de devolucion ");
//
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivos_sd_dev.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTablesvm_vt_motivos_sod_dev();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivos_sd_dev.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_MOTIVO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DESC_MOTIVO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_motivos_sd_dev",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvmeta_cliente
//										.setText("Act. Tabla de Metas y Punt. por Cliente (ok)");
//								tvmeta_linea
//										.setText("Act. Tabla de Metas y Puntuaciones por Linea");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_metas_punto_por_linea();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_LINEA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_LINEA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"ABREV",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_GTE_MARKETIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_MODULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"MAYOR_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"VENTA_3",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"VENTA_4",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"METAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"NRO_ORD_MAG",
//													eliminarNull(linea
//															.substring(ini, fin)));
//										case 16:
//											values.put(
//													"ORD_GTE_MARK",
//													eliminarNull(linea
//															.substring(ini, fin)));
//										case 17:
//											values.put(
//													"ORD_CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//										case 18:
//											values.put(
//													"MES_1",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MES_2",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_metas_punto_por_linea",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvmeta_linea
//										.setText("Act. Tabla de Metas y Puntuaciones por Linea (ok)");
//								tvevo_venta
//										.setText("Act. Tabla de Evolucion de Venta Diaria");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_evol_diaria_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_evol_diaria_venta();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_evol_diaria_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOM_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PEDIDO_2_ATRAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PEDIDO_1_ATRAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"TOTAL_PEDIDOS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"TOTAL_FACT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"META_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"META_LOGRADA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"PROY_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"TOTAL_REBOTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"TOTAL_DEVOLUCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"CANT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"CANT_POSIT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"EF_VISITA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DIA_TRAB",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"PUNTAJE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"SURTIDO_EF",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_evol_diaria_venta",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvevo_venta
//										.setText("Act. Tabla de Evolucion de Venta Diaria(ok)");
//								tvpreciofijo
//										.setText("Act. Tabla de Precio Fijo Consulta");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_precio_fijo_consulta();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PREC_CAJA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PREC_UNID",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"REFERENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_precio_fijo_consulta",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvpreciofijo
//										.setText("Act. Tabla de Precio Fijo Consulta (ok)");
//								tvcosto_articulo
//										.setText("Act. Tabla de Costo Venta Articulo");
//
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_costo_venta_articulo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_costo_venta_articulo();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_costo_venta_articulo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"REFERENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COSTO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COSTO_VENTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_costo_venta_articulo",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcosto_articulo
//										.setText("Act. Tabla de Costo Venta Articulo (ok)");
//								tvdeuda_cliente_vendedor
//										.setText("Act. Tabla de Deuda de Clientes");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_deuda_por_clientes();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//								String indice = "CREATE INDEX IF NOT EXISTS ind_cod_vendedor on svm_deuda_cliente "
//										+ "(cod_vendedor)";
//								Aplicacion.bdatos.execSQL(indice);
//
//								indice = "CREATE INDEX IF NOT EXISTS ind_descripcion on svm_deuda_cliente "
//										+ "(desc_cliente, desc_subcliente)";
//								Aplicacion.bdatos.execSQL(indice);
//
//								indice = "CREATE INDEX IF NOT EXISTS ind_cod_cliente on svm_deuda_cliente "
//										+ "(cod_cliente)";
//								Aplicacion.bdatos.execSQL(indice);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"FEC_EMISION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"FEC_VENCIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"TIP_DOCUMENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"NRO_DOCUMENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"DIAS_ATRAZO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"ABREVIATURA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"SALDO_CUOTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_deuda_cliente",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvdeuda_cliente_vendedor
//										.setText("Act. Tabla de Deuda de Clientes (ok)");
//								tvrebote_por_vendedor
//										.setText("Act. Tabla de Rebotes");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebote_por_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_rebotes_por_vendedores();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebote_por_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"CODIGO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOM_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_PENALIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"FECHA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_rebote_por_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvrebote_por_vendedor
//										.setText("Act. Tabla de Rebotes(ok)");
//							}
//						});
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_surtido_eficiente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_surtido_eficiente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
////							_sql = Aplicacion.sentencias
////									.createIndexSvm_surtido_eficiente();
////
////							try {
////								Aplicacion.bdatos.execSQL(_sql);
////							} catch (Exception e) {
////								String err = e.getMessage();
////								err = "" + err;
////							}
////
////							_sql = Aplicacion.sentencias
////									.createIndexSvm_surtido_eficienteCompuesto();
////
////							try {
////								Aplicacion.bdatos.execSQL(_sql);
////							} catch (Exception e) {
////								String err = e.getMessage();
////								err = "" + err;
////							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++; //24940
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_surtido_eficiente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TIP_SURTIDO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_surtido_eficiente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//
////						Aplicacion.bdatos.beginTransaction();
////
////						runOnUiThread(new Runnable() {
////							@Override
////							public void run() {
////								tvrebote_por_vendedor
////										.setText("Act. Tabla de Rebotes(ok)");
////							}
////						});
////
////						try {
////							archivo = new File(
////									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_sup.txt");
////							fr = new FileReader(archivo);
////							br = new BufferedReader(fr);
////
////							_sql = Aplicacion.sentencias
////									.createTableSvm_metas_punto_por_linea_sup();
////
////							try {
////								Aplicacion.bdatos.execSQL(_sql);
////							} catch (Exception e) {
////								String err = e.getMessage();
////								err = "" + err;
////							}
////
////							String linea;
////							double sum = 0.0;
////							int lNumeroLineas = 0;
////
////							while ((linea = br.readLine()) != null) {
////								lNumeroLineas++;
////							}
////							fac = (float) 100 / (float) lNumeroLineas;
////
////							archivo = new File(
////									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_sup.txt");
////							fr = new FileReader(archivo);
////							br = new BufferedReader(fr);
////
////							while ((linea = br.readLine()) != null) {
////
////								int ini = 0;
////								int fin = 0;
////								int c = 0;
////
////								ContentValues values = new ContentValues();
////								for (int i = 0; i < linea.length(); i++) {
////
////									if (linea.charAt(i) == '|') {
////										fin = i;
////										c = c + 1;
////
////										switch (c) {
////										case 1:
////											values.put(
////													"COD_EMPRESA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 2:
////											values.put(
////													"COD_LINEA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 3:
////											values.put(
////													"DESC_LINEA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 4:
////											values.put(
////													"ABREVIATURA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 5:
////											values.put(
////													"DESC_GTE_MARKETIN",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 6:
////											values.put(
////													"DESC_MODULO",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 7:
////											values.put(
////													"COD_SUP_TEL",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 8:
////											values.put(
////													"DESC_SUPERVISOR",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 9:
////											values.put(
////													"MAYOR_VENTA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 10:
////											values.put(
////													"VENTA_3",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 11:
////											values.put(
////													"VENTA_4",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 12:
////											values.put(
////													"METAS",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 13:
////											values.put(
////													"NRO_ORD_MAG",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 14:
////											values.put(
////													"ORD_GTE_MARK",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 15:
////											values.put(
////													"ORD_CATEGORIA",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////										case 16:
////											values.put(
////													"MES_1",
////													eliminarNull(linea
////															.substring(ini, fin)));
////											break;
////
////										}
////										ini = i + 1;
////									}
////
////									if (i == linea.length() - 1) {
////										if (i > 0) {
////
////											sum = sum + 1;
////
////											values.put(
////													"MES_2",
////													eliminarNull(linea
////															.substring(
////																	ini,
////																	linea.length())));
////
////											try {
////												Aplicacion.bdatos
////														.insert("svm_metas_punto_por_linea_sup",
////																null, values);
////
////											} catch (Exception e) {
////												String err = e.getMessage();
////												err = "" + err;
////											}
////
////											//
////											progressBarProcess
////													.setProgress((int) (fac * sum));
////										}
////										values = new ContentValues();
////										ini = 0;
////										c = 0;
////
////									}
////								}
////
////							}
////
////						} catch (Exception e) {
////							String err = e.getMessage();
////							err = "" + err;
////
////						}
////
////						archivo.delete();
////
////						auxActual = auxActual + auxInc;
////						progressBar.setProgress(auxActual);
////						progressBarProcess.setProgress(0);
////
////						Aplicacion.bdatos.setTransactionSuccessful();
////						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_empresa.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_metas_punto_por_linea_empresa();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_metas_punto_por_linea_empresa.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"METAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"CANT_PUNTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PUNT_ACUM",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_metas_punto_por_linea_empresa",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_positivacion_cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TIP_CAUSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DIRECCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"DESC_CIUDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"RUC",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"DESC_REGION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DESC_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"TELEFONO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"IND_POS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//											values.put("COD_SUPERVISOR",
//													Aplicacion.et_login
//															.getText()
//															.toString());
//											values.put(
//													"MONTO_VENTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_positivacion_cliente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_pedidos_en_reparto();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"NRO_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TEL_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"NRO_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOT_COMPROBANTE",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_pedidos_en_reparto",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_premios_supervisores.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_premios_supervisores();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_premios_supervisores.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_MARCA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_MARCA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"META",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANT_PUNTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PORC_CUMP",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//																				}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PUNT_ACUM",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_premios_supervisores",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_general_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_general_sup();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_general_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"METAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"VENTAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PORC_VOL_VENTAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PUNT_ACUM_LINEAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PUNT_CLI_ACUM",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"CANT_CLI_POS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"MONTO_REBOTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"MONTO_DEV",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"CANT_COB",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"CANT_RUTEO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"VENTAS_ESTR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"METAS_ESTR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"CATASTRO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"PORC_MONTO_REBOTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC_MONTO_DEV",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_general_sup",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_supervisor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_supervisor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_motivo_analisis_cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_MOTIVO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										case 4:
//											values.put(
//													"ESTADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										case 5:
//											values.put(
//													"PUNTUACION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_GRUPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_motivo_analisis_cliente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_motivo_analisis_vendedor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_MOTIVO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"ESTADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"PUNTUACION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_GRUPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_motivo_analisis_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_dia_visita_ruteo();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_MOVIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CANT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANT_NO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_dia_visita_ruteo",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_prom_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_prom_vendedor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//								String indice = "CREATE INDEX if not exists cliente_vendedor on svm_cliente_prom_vendedor(COD_VENDEDOR, COD_CLIENTE, DESC_CLIENTE, DESC_SUBCLIENTE, DESC_CIUDAD, FEC_VISITA)";
//								Aplicacion.bdatos.execSQL(indice);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_prom_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TELEFONO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"TIP_CAUSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESC_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"DESC_REGION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DIRECCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//
//											break;
//										case 15:
//											values.put(
//													"COD_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"DESC_TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"COD_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DESC_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"LIMITE_CREDITO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 20:
//											values.put(
//													"TOT_DEUDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 21:
//											values.put(
//													"SALDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 22:
//											values.put(
//													"FEC_VISITA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 23:
//											values.put(
//													"IND_VISITA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 24:
//											values.put(
//													"COD_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 25:
//											values.put(
//													"TIPO_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 26:
//											values.put(
//													"IND_DIRECTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 27:
//											values.put(
//													"IND_ATRAZO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 28:
//											values.put(
//													"FRECUENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 29:
//											values.put(
//													"COMENTARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 30:
//											values.put(
//													"IND_ESP",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 31:
//											values.put(
//													"CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 32:
//											values.put(
//													"TELEFONO2",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 33:
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 34:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 35:
//											values.put(
//													"CERCA_DE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 36:
//											values.put(
//													"IND_CADUCADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 37:
//											values.put(
//													"TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 38:
//											values.put(
//													"IND_VITRINA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 39:
//											values.put(
//													"FOTO_FACHADA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 40:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 41:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DIAS_INICIAL",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_prom_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_categoria.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableStv_categorias();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_categoria.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_SEGMENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_SEGMENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"ORDEN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COD_TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"stv_categoria", null,
//														values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_retorno_comentario.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_retorno_comentario();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_retorno_comentario.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_ENTRADA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"FEC_SALIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_PROMOTOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_SEGMENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"PUNTUACION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COMENTARIO_SUPERVISOR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_retorno_comentario",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_supervisor_full();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_supervisor_full",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_periodo_foto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_periodo_foto();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_periodo_foto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"FEC_INICIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"FEC_FIN",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_periodo_foto",
//														null, values);
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						// /NUEVO SINCRONIZA-VENTA //////
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_list_prec.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableCliente_List_Prec();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_list_prec.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"DESC_LISTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"IND_DEFECTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_MONEDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"cliente_list_prec",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_articulos_precios.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableArticulos_Precios();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//								String indice = "CREATE INDEX IF NOT EXISTS ind_art_prec on articulos_precios "
//										+ "(desc_articulo, cod_vendedor, cod_lista_precio, cod_articulo)";
//								Aplicacion.bdatos.execSQL(indice);
//
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_articulos_precios.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"cod_empresa",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"cod_vendedor",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"modulo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"cod_lista_precio",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"cod_articulo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"desc_articulo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"prec_caja",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"prec_unid",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"cod_unidad_medida",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"porc_iva",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"referencia",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"mult",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"div",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"ind_lim_venta",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"cod_linea",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"cod_familia",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"cod_barra",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"cant_minima",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"cant_minima",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
////											values.put("TIP_SURTIDO", tipoSurtido(values.get("cod_empresa") 	+"",
////																				  values.get("cod_vendedor")	+"",
////																				  values.get("cod_cliente")		+"",
////																				  values.get("cod_subcliente")	+"",
////																				  values.get("tip_cliente")		+"",
////																				  values.get("cod_articulo")	+""));
//
//											values.put(
//													"porc_descuento",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"articulos_precios",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_st_articulos.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_st_articulos();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_st_articulos.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"cod_empresa",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"cod_articulo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"desc_articulo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"cod_unidad_rel",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"referencia",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"mult",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"div",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"cod_iva",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"porc_iva",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"cod_linea",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"cod_familia",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"CANT_MINIMA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"ind_basico",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_st_articulos",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBar
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_condicion_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_condicion_venta();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_condicion_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"cod_empresa",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"cod_condicion_venta",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"descripcion",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"tipo_condicion",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"abreviatura",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"dias_inicial",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"porc_desc",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"porc_desc",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_condicion_venta",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vendedor_pedido.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_vendedor_pedido_venta();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_vendedor_pedido.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"cod_empresa",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"cod_vendedor",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"ind_palm",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"tipo",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"serie",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"numero",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"fecha",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											values.put(
//													"ultima_sincro",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_CLI_VEND",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"COD_PERSONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"RANGO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"MIN_FOTOS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"MAX_FOTOS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"PER_VENDER",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"PER_PRESENCIAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"HORA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"VERSION_SISTEMA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"PER_ASISTENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"FRECUENCIA_RASTREO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"HORA_INICIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 20:
//											values.put(
//													"HORA_FIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 21:
//											values.put(
//													"TIEMPO_ASISTENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 22:
//											values.put(
//													"IND_FOTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 23:
//											values.put(
//													"MIN_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"ULTIMA_VEZ",
//													Aplicacion.utilidades
//															.obtieneFechaHoraActual());
//
//											values.put(
//													"version",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_vendedor_pedido_venta",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableCli_Vendedor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//								String indice = "CREATE INDEX if not exists cliente_vendedor on cli_vendedor(COD_VENDEDOR, COD_CLIENTE, DESC_CLIENTE, DESC_SUBCLIENTE, DESC_CIUDAD, FEC_VISITA)";
//								Aplicacion.bdatos.execSQL(indice);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TELEFONO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"RUC",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"TIP_CAUSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"desc_ciudad",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESC_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"DESC_REGION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DIRECCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//
//											break;
//										case 15:
//											values.put(
//													"COD_SUCURSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"DESC_TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"COD_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DESC_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"LIMITE_CREDITO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 20:
//											values.put(
//													"TOT_DEUDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 21:
//											values.put(
//													"SALDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 22:
//											values.put(
//													"FEC_VISITA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 23:
//											values.put(
//													"IND_VISITA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 24:
//											values.put(
//													"COD_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 25:
//											values.put(
//													"TIPO_CONDICION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 26:
//											values.put(
//													"IND_DIRECTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 27:
//											values.put(
//													"IND_ATRAZO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 28:
//											values.put(
//													"FRECUENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 29:
//											values.put(
//													"COMENTARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 30:
//											values.put(
//													"IND_ESP",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 31:
//											values.put(
//													"CATEGORIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 32:
//											values.put(
//													"TELEFONO2",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 33:
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 34:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 35:
//											values.put(
//													"CERCA_DE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 36:
//											values.put(
//													"IND_CADUCADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 37:
//											values.put(
//													"TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 38:
//											values.put(
//													"IND_VITRINA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 39:
//											values.put(
//													"FOTO_FACHADA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 40:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 41:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 42:
//											values.put(
//													"SOL_TARG",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DIAS_INICIAL",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"CLI_VENDEDOR", null,
//														values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//							Toast.makeText(sincronizar.this, err,
//									Toast.LENGTH_LONG).show();
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_ultima_venta_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_Ultima_Venta_Cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//								String indice = "CREATE INDEX if not exists ult_venta_cliente on svm_ultima_venta_cliente(cod_cliente, cod_subcliente)";
//								Aplicacion.bdatos.execSQL(indice);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_ultima_venta_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//										case 2:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PRECIO_UNITARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_UNIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_ultima_venta_cliente",
//																null, values);
//
//											} catch (Exception e) {
//
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_prom_articulos_tar_cred.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_prom_articulos_tar_cred();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_prom_articulos_tar_cred.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"NRO_PROMOCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_CONDICION_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PORC_DESCUENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//											values.put(
//													"COMENTARIO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_prom_articulos_tar_cred",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cond_venta_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cond_venta_vendedor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cond_venta_vendedor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COD_CONDICION_VENTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cond_venta_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_no_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_motivo_no_venta();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_no_venta.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_MOTIVO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"CIERRA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"DESC_MOTIVO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_motivo_no_venta",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_perfilMerc.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_promociones_por_perfil();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_perfilMerc.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NRO_PROMOCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COMENTARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_INI_PROMO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"FEC_FIN_PROMO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_promociones_por_perfil",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_movimiento_gestor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOM_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_ASISTENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"HORA_ENTRADA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"HORA_SALIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MARCACION",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_movimiento_gestor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_art_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_promociones_art_cab();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							try {
//								String indice = "CREATE INDEX IF NOT EXISTS ind_art_prec on svm_promociones_art_cab "
//										+ "(NRO_PROMOCION, COD_VENDEDOR, COD_LISTA_PRECIO, COD_ARTICULO)";
//								Aplicacion.bdatos.execSQL(indice);
//
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_art_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NRO_PROMOCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"TIP_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COMENTARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_CONDICION_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"FEC_VIGENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"COD_UNIDAD_MEDIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"REFERENCIA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"IND_TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"MULT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"COD_LISTA_PRECIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"IND_COMBO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"IND_PROM",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 19:
//											values.put(
//													"IND_ART",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"CANT_VENTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
////											if (values.get("NRO_PROMOCION").equals("3556")) {
////												mensajebd("Exitoso", "Promo 3556 insertada");
////											}
////
////											if (values.get("NRO_PROMOCION").equals("3555")) {
////												mensajebd("Exitoso", "Promo 3555 insertada");
////											}
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_promociones_art_cab",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_art_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_promociones_art_det();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							try {
//								String indice = "CREATE INDEX IF NOT EXISTS ind_art_prom_det on svm_promociones_art_det "
//										+ "(NRO_PROMOCION, COD_ARTICULO)";
//								Aplicacion.bdatos.execSQL(indice);
//
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_promociones_art_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NRO_PROMOCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CANT_DESDE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CANT_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_UNIDAD_MEDIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_UND_MEDIDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"DESCUENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"IND_MULTIPLE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PRECIO_RS",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_promociones_art_det",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_seg_visitas_semanal();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_MOVIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_INICIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_FIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"CANT_NO_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"CANT_NO_VISITADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"SEMANA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_seg_visitas_semanal",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_liq_comision_supervisor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_CONCEPTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_CONCEPTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"MONTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TIPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"NRO_CUOTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"FEC_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_MONEDA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"COMENT",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_liq_comision_supervisor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBar
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcontrolcab
//										.setText("Act. Tabla Control de Venta Cab. (ok)");
//								tvcontroldet
//										.setText("Act. Tabla Control de Venta Det.");
//							}
//						});
//
//
//						try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_premios_vend();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"VENDEDOR_PERSONA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"TIP_COM",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"COD_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"DESC_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"MONTO_META",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"PORC_COBERTURA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"PORC_LOG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"MONTO_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 14:
//												values.put(
//														"TOT_CLIENTES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//
//												values.put(
//														"CLIENTES_VISITADOS",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_premios_vend",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//
//
//						   archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							Aplicacion.bdatos.beginTransaction();
//
//							 try {
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_produccion_semanal_vend.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									_sql = Aplicacion.sentencias
//											.createTableSvm_produccion_semanal_vend();
//
//									try {
//										Aplicacion.bdatos.execSQL(_sql);
//									} catch (Exception e) {
//										String err = e.getMessage();
//										err = "" + err;
//									}
//
//									String linea;
//									double sum = 0.0;
//									int lNumeroLineas = 0;
//
//									while ((linea = br.readLine()) != null) {
//										lNumeroLineas++;
//									}
//									fac = (float) 100 / (float) lNumeroLineas;
//
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_produccion_semanal_vend.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									while ((linea = br.readLine()) != null) {
//
//										int ini = 0;
//										int fin = 0;
//										int c = 0;
//
//
//										/*
//										 *
//										 SELECT COD_EMPRESA      , COD_VENDEDOR       ,
//													     DESC_VENDEDOR    , CANTIDAD         ,
//													     SEMANA           , FECHA              ,
//													     MONTO_VIATICO  , MONTO_TOTAL      ,
//										                 PERIODO
//										   FROM svm_produccion_semanal_vend
//										 */
//										ContentValues values = new ContentValues();
//										for (int i = 0; i < linea.length(); i++) {
//
//											if (linea.charAt(i) == '|') {
//												fin = i;
//												c = c + 1;
//
//												switch (c) {
//												case 1:
//													values.put(
//															"COD_EMPRESA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 2:
//													values.put(
//															"COD_VENDEDOR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 3:
//													values.put(
//															"COD_PERSONA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 4:
//													values.put(
//															"DESC_VENDEDOR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 5:
//													values.put(
//															"CANTIDAD",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 6:
//													values.put(
//															"SEMANA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 7:
//													values.put(
//															"FECHA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 8:
//													values.put(
//															"MONTO_VIATICO",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 9:
//													values.put(
//															"MONTO_TOTAL",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												 }
//            								ini = i + 1;
//											}
//
//											if (i == linea.length() - 1) {
//												if (i > 0) {
//
//													sum = sum + 1;
//
//													values.put(
//															"PERIODO",
//															eliminarNull(linea
//																	.substring(
//																			ini,
//																			linea.length())));
//
//
//													try {
//														Aplicacion.bdatos
//																.insert("svm_produccion_semanal_vend",
//																		null, values);
//
//													} catch (Exception e) {
//														String err = e.getMessage();
//														err = "" + err;
//													}
//
//													progressBarProcess
//															.setProgress((int) (fac * sum));
//
//												}
//												values = new ContentValues();
//												ini = 0;
//												c = 0;
//
//											}
//										}
//
//									}
//
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//
//								}
//
//
//
//
//						    archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//comprobantes pendientes a emitir
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
////								mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n");
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liquidacion_fuerza_venta();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"FEC_COMPROBANTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"OBSERVACION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"DESCRIPCION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"TIP_COMPROBANTE_REF",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"TOT_IVA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"TOT_GRAVADA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"TOT_EXENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"TOT_COMPROBANTE",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liquidacion_fuerza_venta",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liquidacion_fuerza_venta\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//CANASTA DE MARCAS
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_canasta_marca_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liq_canasta_marca_sup\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"VENTAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"VALOR_CANASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PESO_PUNT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"PUNTAJE_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"PORC_CUMP",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"COD_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"DESC_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_canasta_marca_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liq_canasta_marca_sup\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//CANASTA DE CLIENTES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
////								mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n");
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_canasta_cte_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liq_canasta_cte_sup\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"VENTAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"VALOR_CANASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"PUNTAJE_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"PORC_CUMP",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"PESO_PUNT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_canasta_cte_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liq_canasta_cte_sup\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//PRODUCCION SEMANAL
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_produccion_semanal();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_produccion_semanal_gcs\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"CANTIDAD",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"SEMANA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"MONTO_VIATICO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"MONTO_TOTAL",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PERIODO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"MONTO_X_POR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"CANT_CLIENTE",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_produccion_semanal_gcs",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_produccion_semanal_gcs\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//PROMEDIO DE ALCANCE DE CUOTA MENSUAL DE VENDEDORES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_prom_alc_cuota_mensual_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_prom_alc_cuota_mensual_sup\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"PORC_LOG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"PORC_ALC",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"MONTO_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"TOTAL_FACTURADO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"META",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_prom_alc_cuota_mensual_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_prom_alc_cuota_mensual_sup\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//CUOTA POR UNIDAD DE NEGOCIO
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_cuota_x_unidad_neg();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liq_cuota_x_und_neg\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PORC_ALC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"MONTO_CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_cuota_x_und_neg",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liq_cuota_x_und_neg\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//COBERTURA MENSUAL
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_cobertura_mensual_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_cobertura_mensual_sup\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"TOT_CLI_CART",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"CANT_POSIT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"TOT_CLIEN_ASIG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"PORC_LOGRO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_COB",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PREMIOS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_cobertura_mensual_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_cobertura_mensual_sup\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//CUOTA POR UNIDAD DE NEGOCIO DE VENDEDORES - VENDEDORES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_cuota_x_unidad_neg_vend();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liq_cuota_x_und_neg_vend\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"MONTO_CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"PORC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"PORC_ALC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_cuota_x_und_neg_vend",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liq_cuota_x_und_neg_vend\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//COBERTURA MENSUAL DE VENDEDORES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_cobertura_mensual_vend();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_cobertura_mensual_vend\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"TOT_CLI_CART",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"CANT_POSIT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_COB",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PORC_LOGRO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"PREMIOS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_cobertura_mensual_vend",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_cobertura_mensual_vend\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//COBERTURA SEMANAL DE VENDEDORES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_cob_semanal_vend();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_cob_semanal_vend\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"SEMANA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"TOT_CLIENTES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"CLIENT_VENTAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PORC_COBERTURA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"PERIODO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_cob_semanal_vend",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_cob_semanal_vend\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							Aplicacion.bdatos.beginTransaction();
//
//							 try {
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									_sql = Aplicacion.sentencias
//											.createTableSvm_liq_premios_sup();
//
//									try {
//										Aplicacion.bdatos.execSQL(_sql);
//									} catch (Exception e) {
//										String err = e.getMessage();
//										err = "" + err;
//									}
//
//									String linea;
//									double sum = 0.0;
//									int lNumeroLineas = 0;
//
//									while ((linea = br.readLine()) != null) {
//										lNumeroLineas++;
//									}
//									fac = (float) 100 / (float) lNumeroLineas;
//
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									while ((linea = br.readLine()) != null) {
//
//										int ini = 0;
//										int fin = 0;
//										int c = 0;
//
//										ContentValues values = new ContentValues();
//										for (int i = 0; i < linea.length(); i++) {
//
//											if (linea.charAt(i) == '|') {
//												fin = i;
//												c = c + 1;
//
//												switch (c) {
//												case 1:
//													values.put(
//															"COD_EMPRESA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 2:
//													values.put(
//															"COD_MARCA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 3:
//													values.put(
//															"DESC_MARCA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 4:
//													values.put(
//															"MONTO_VENTA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 5:
//													values.put(
//															"MONTO_META",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 6:
//													values.put(
//															"PORC_LOG",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 7:
//													values.put(
//															"PORC_COBERTURA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 8:
//													values.put(
//															"TOT_CLIENTES",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 9:
//													values.put(
//															"CLIENTES_VISITADOS",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 10:
//													values.put(
//															"MONTO_A_COBRAR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 11:
//													values.put(
//															"MONTO_COBRAR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//
//												}
//
//												ini = i + 1;
//											}
//
//
//											if (i == linea.length() - 1) {
//												if (i > 0) {
//
//													sum = sum + 1;
//
//													values.put(
//															"TIP_COM",
//															eliminarNull(linea
//																	.substring(
//																			ini,
//																			linea.length())));
//
//													try {
//														Aplicacion.bdatos
//																.insert("svm_liq_premios_sup",
//																		null, values);
//
//													} catch (Exception e) {
//														String err = e.getMessage();
//														err = "" + err;
//													}
//
//													progressBarProcess
//															.setProgress((int) (fac * sum));
//
//												}
//												values = new ContentValues();
//												ini = 0;
//												c = 0;
//
//											}
//										}
//
//									}
//
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//
//								}
//
//
//						// Kia Fin
//
//
//					   archivo.delete();
//
//
//
//
//
//
//					  if(sincronizaAnuncio.equals("S") && Aplicacion._tip_sincroniza.equals("T")){
//
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_anuncio_movil.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_anuncio_movil();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_anuncio_movil.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"NRO_REGISTRO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"CONTENIDO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//											case 3:
//												values.put(
//														"TITULO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//												values.put(
//														"FECHA",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_anuncio_movil",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBar
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//
//						}
//
//						//NUEVA TABLA SINCRONIZACION
//
//						// /////FIN SINCRONIZA VENTA////////////
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(1000);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								tvcontrolcab
//										.setText("Act. Tabla Control de Venta Cab. (ok)");
//								tvcontroldet
//										.setText("Act. Tabla Control de Venta Det.");
//							}
//						});
//
//
//						try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_marca_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_cobertura_marca_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_marca_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"PORC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"PORC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"PORC_COM",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//
//												values.put(
//														"LINHA",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_cobertura_marca_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							///NUEVA TABLA
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_cobertura_vis_vendedores.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									_sql = Aplicacion.sentencias
//											.createTableSvm_cobertura_vis_vendedores();
//
//									try {
//										Aplicacion.bdatos.execSQL(_sql);
//									} catch (Exception e) {
//										String err = e.getMessage();
//										err = "" + err;
//									}
//
//									String linea;
//									double sum = 0.0;
//									int lNumeroLineas = 0;
//
//									while ((linea = br.readLine()) != null) {
//										lNumeroLineas++;
//									}
//									fac = (float) 100 / (float) lNumeroLineas;
//
//									archivo = new File(
//											"/data/data/apolo.supervisores.com/svm_cobertura_vis_vendedores.txt");
//									fr = new FileReader(archivo);
//									br = new BufferedReader(fr);
//
//									while ((linea = br.readLine()) != null) {
//
//										int ini = 0;
//										int fin = 0;
//										int c = 0;
//
//										ContentValues values = new ContentValues();
//										for (int i = 0; i < linea.length(); i++) {
//
//											if (linea.charAt(i) == '|') {
//												fin = i;
//												c = c + 1;
//
//												switch (c) {
//												case 1:
//													values.put(
//															"COD_GRUPO",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 2:
//													values.put(
//															"COD_VENDEDOR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 3:
//													values.put(
//															"DESC_VENDEDOR",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 4:
//													values.put(
//															"PORC_DESDE",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//												case 5:
//													values.put(
//															"PORC_HASTA",
//															eliminarNull(linea
//																	.substring(ini, fin)));
//													break;
//
//												}
//
//												ini = i + 1;
//											}
//
//											if (i == linea.length() - 1) {
//												if (i > 0) {
//
//													sum = sum + 1;
//
//
//													values.put(
//															"PORC_COM",
//															eliminarNull(linea
//																	.substring(
//																			ini,
//																			linea.length())));
//
//													try {
//														Aplicacion.bdatos
//																.insert("svm_cobertura_vis_vendedores",
//																		null, values);
//
//													} catch (Exception e) {
//														String err = e.getMessage();
//														err = "" + err;
//													}
//
//													progressBarProcess
//															.setProgress((int) (fac * sum));
//
//												}
//												values = new ContentValues();
//												ini = 0;
//												c = 0;
//
//											}
//										}
//
//									}
//
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//
//								}
//
//
//
//								archivo.delete();
//
//								auxActual = auxActual + auxInc;
//								progressBar.setProgress(auxActual);
//								progressBarProcess.setProgress(0);
//
//								Aplicacion.bdatos.setTransactionSuccessful();
//								Aplicacion.bdatos.endTransaction();
//
//						_sql = "DROP TABLE IF EXISTS svm_vendedor_pedido;";
//						Aplicacion.bdatos.execSQL(_sql);
//
//						_sql = Aplicacion.sentencias
//								.createTableSVM_VENDEDOR_PEDIDO();
//						Aplicacion.bdatos.execSQL(_sql);
//
//						ContentValues values = new ContentValues();
//						values.put("COD_EMPRESA", "1");
//						values.put("COD_SUPERVISOR", Aplicacion.et_login
//								.getText().toString());
//						values.put("IND_PALM", "S");
//						values.put("TIPO", "");
//						values.put("SERIE", "");
//						values.put("NUMERO", "0");
//						values.put("FECHA", "");
//						values.put("ULTIMA_SINCRO",
//								Aplicacion._fecha_ult_actualizacion);
//						values.put("FEC_CARGA_RUTEO",
//								Aplicacion._fecha_carga_ruteo);
//
//						values.put("ULTIMA_VEZ",
//								Aplicacion.utilidades.obtieneFechaHoraActual());
//						values.put("RANGO", Aplicacion.rango_distancia);
//						values.put("TIPO_SUPERVISOR", Aplicacion.tipo_sup);
//						values.put("MIN_FOTO", Aplicacion.min_fotos);
//						values.put("MAX_FOTO", Aplicacion.max_fotos);
//						values.put("MAX_DESC", Aplicacion.max_desc);
//						values.put("IND_FOTO", Aplicacion.ind_foto);
//						values.put("MAX_DESC_VAR", Aplicacion.max_desc_var);
//						values.put("VERSION", Aplicacion.version_sistema);
//						values.put("PER_VENDER", Aplicacion._per_vender_sup);
//						values.put("INT_MARCACION", Aplicacion._int_marcacion);
//
//						Aplicacion._activo = "S";
//
//						Aplicacion.bdatos.insert("svm_vendedor_pedido", null,
//								values);
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//
//	}
//
//	private class performBackgroundTask extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			pbarDialog = ProgressDialog.show(sincronizar.this, "Un momento...",
//					"Generando archivos...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			try {
//
//				String update = "update svm_vendedor_pedido set ULTIMA_VEZ = '"
//						+ Aplicacion.utilidades.obtieneFechaHoraActual() + "'";
//				Aplicacion.bdatos.rawQuery(update, null);
//				if(Aplicacion._tip_sincroniza == "T"){
//					respuestaWS = Aplicacion.cWS.onClickGeneraArchivos();
//
//
//					if(sincronizaAnuncio.equals("S")){
//				     	Aplicacion.cWS.onClickGeneraArchivoAnuncio();
//					}
//
//				}else{
//					if (Aplicacion._tip_sincroniza == "I") {
//						respuestaWS = Aplicacion.cWS.onClickGeneraArchivosInformes();
//					}
//					if (Aplicacion._tip_sincroniza == "R") {
//						respuestaWS = Aplicacion.cWS.onClickGeneraInformeSegSupervisor();
//					}
//					if (Aplicacion._tip_sincroniza == "V") {
//						respuestaWS = Aplicacion.cWS.onClickGeneraInformeVisitaSupervisor();
//					}
//				}
//
//
//
//				String err = Boolean.toString(respuestaWS);
//				err = err + "";
//				if (respuestaWS) {
//					Aplicacion._conectado = "S";
//				} else {
//					Aplicacion._conectado = "N";
//				}
//
//				return null;
//			} catch (Exception e) {
//				mensajebd("Error al conectar",
//						"Intente otra vez o llame al Soporte Informatico de la empresa!");
//				return null;
//
//			}
//		}
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//			new performBackgroundTask2().execute();
//
//		}
//
//	}
//
//	private class performBackgroundTask2 extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			pbarDialog = ProgressDialog.show(sincronizar.this, "Un momento...",
//					"Obteniendo Archivos...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			try {
//				if(Aplicacion._tip_sincroniza == "T"){
//					respuestaWS = Aplicacion.cWS.onClickTraeArchivos();
//
//					if(sincronizaAnuncio.equals("S")){
//						Aplicacion.cWS.onClickTraeArchivosAnuncio();
//					}
//
//				}else{
//					if (Aplicacion._tip_sincroniza == "I") {
//						respuestaWS = Aplicacion.cWS.onClickTraeArchivosInformes();
//					}
//					if (Aplicacion._tip_sincroniza == "R") {
//						respuestaWS = Aplicacion.cWS.onClickTraeArchivosInformesSegSupervisor();
//					}
//					if (Aplicacion._tip_sincroniza == "V") {
//						respuestaWS = Aplicacion.cWS.onClickTraeArchivosInformesVisitaSupervisor();
//					}
//				}
//				if (respuestaWS) {
//					Aplicacion._conectado = "S";
//				} else {
//					Aplicacion._conectado = "N";
//				}
//
//				return null;
//			} catch (Exception e) {
//				mensajebd("Error al conectar",
//						"Intente otra vez o llame al Soporte Informatico de la empresa!");
//				return null;
//
//			}
//		}
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//
//			if (Aplicacion._migrar_ahora == "S")
//				;
//			{
//				sincronizando = "S";
//				if(Aplicacion._tip_sincroniza == "T"){
//					sincroniza();
//				}else{
//					if(Aplicacion._tip_sincroniza == "I"){
//						sincronizaInformeNuevo();
//					}
//					if(Aplicacion._tip_sincroniza == "R"){
//						sincronizaReportes(); //Reportes crear
//					}
//					if(Aplicacion._tip_sincroniza == "V"){
//						sincronizaVisitas(); //Visitas crear
//					}
//				}
//			}
//
//		}
//
//	}
//
//	private class performBackgroundTask3 extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			pbarDialog = ProgressDialog.show(sincronizar.this, "Un momento...",
//					"Consultando disponibilidad...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			Aplicacion.cWS.setMethodName("ProcesaImeiSupervisorAct");
//
//			imeiBD = Aplicacion.cWS.procesaImeiSupervisor(Aplicacion.et_login
//					.getText().toString());
//
//			procesa_envia_autorizaciones();
//			anulaVentasTresDias();
//			procesa_envia_no_positivados();
//			procesa_envia_datos_cliente();
//			return null;
//
//		}
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//
//			if (Aplicacion._imei == null) {
//				Aplicacion._imei = "";
//			}
//
//			if (!Aplicacion._imei.equals("")) {
//
//				if (imeiBD.indexOf("Unable to resolve host") > -1) {
//					Toast.makeText(
//							sincronizar.this,
//							"Verifique su conexion a internet y vuelva a intentarlo",
//							Toast.LENGTH_SHORT).show();
//					sincronizar.super.onBackPressed();
//					return;
//				}
//
//				// 5664
//
//				String[] im = imeiBD.split("-");
//
//				if (!Aplicacion._imei.equals(im[0]) && !Aplicacion._imei.equals("355299076199057")
//													&& !Aplicacion._imei.equals("358212085168242")
//													&& !Aplicacion._imei.equals("356556072264857")
//													&& !Aplicacion._imei.equals("356074080243319")
//													&& !Aplicacion._imei.equals("868662020804959")
//													&& !Aplicacion._imei.equals("864466042333557")
//													) {
//
//					// if (Aplicacion._imei.equals(im[0])) {
//					Builder builder2 = new Builder(sincronizar.this);
//
//					builder2.setMessage("Este equipo no puede sincronizar. Contacte con el soporte informatico!! ");
//					Aplicacion._fecha_ult_actualizacion = "01/01/2013";
//					Aplicacion._fecha_carga_ruteo       = "01/01/2013";
//
//					String sql = "update svm_vendedor_pedido "
//							+ "set ULTIMA_SINCRO = ''";
//
//					Aplicacion.bdatos.execSQL(sql);
//
//					borraTablas();
//
//					builder2.setNeutralButton(("OK"), new OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//
//							finish();
//
//						}
//					});
//
//					AlertDialog alertDialog2 = builder2.create();
//					alertDialog2.show();
//					alertDialog2.setCancelable(false);
//
//				} else {
//
//					if (!im[1].equals("Y")) {
//
//						Builder builder2 = new Builder(sincronizar.this);
//
//						builder2.setMessage(im[1]);
//
//						builder2.setNeutralButton(("OK"),
//								new OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//
//										finish();
//
//									}
//								});
//
//						AlertDialog alertDialog2 = builder2.create();
//						alertDialog2.show();
//						alertDialog2.setCancelable(false);
//
//					} else {
//
//						Aplicacion.rango_distancia = im[2];
//						Aplicacion.tipo_sup = im[3];
//						Aplicacion.min_fotos = Integer.parseInt(im[4]);
//						Aplicacion.max_fotos = Integer.parseInt(im[5]);
//						Aplicacion._fecha_ult_actualizacion = im[6];
//						Aplicacion.ind_foto = im[7];
//						Aplicacion._fecha_carga_ruteo = im[8];
//						Aplicacion.max_desc = Double.parseDouble(im[9]);
//						Aplicacion.max_desc_var = Double.parseDouble(im[10]);
//						Aplicacion.version_sistema = Double.parseDouble(im[11]);
//						Aplicacion._per_vender_sup = im[12];
//						Aplicacion._int_marcacion = Integer.parseInt(im[13]);
//						Aplicacion._cod_persona = im[14];
//
//						if (Aplicacion._migrar_ahora == "S")
//							;
//						{
//							if (!(Aplicacion.version_sistema == Double
//									.parseDouble(Aplicacion._version_soft))
//									&& !Aplicacion._imei.equals("358212085168242")) {
//
//								Builder builder2 = new Builder(sincronizar.this);
//
//								builder2.setMessage("FAVOR ACTUALIZAR LA VERSION !!");
//
//								builder2.setNeutralButton(("OK"),
//										new OnClickListener() {
//
//											@Override
//											public void onClick(
//													DialogInterface dialog,
//													int which) {
//
//												finish();
//
//											}
//										});
//
//								AlertDialog alertDialog2 = builder2.create();
//								alertDialog2.show();
//								alertDialog2.setCancelable(false);
//								return;
//
//							} else {
//								sincronizando = "S";
//
//								new performBackgroundTask().execute();
//							}
//
//						}
//
//					}
//
//				}
//
//			}
//
//		}
//
//	}
//
//	public void mensajebd(String Titulo, String Texto) {
//		Builder mensaje = new Builder(sincronizar.this);
//		mensaje.setTitle(Titulo);
//		mensaje.setMessage(Texto);
//		mensaje.setNeutralButton("OK", null);
//		mensaje.show();
//	}
//
//	Handler getHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//
//			Aplicacion._migrar_ahora = "N";
//			Builder builder = new Builder(sincronizar.this);
//
//			if (progressBar.getProgress() == 100) {
//				builder.setMessage("Proceso de Sincronizaci�n Terminado!!");
//			} else {
//				builder.setMessage("La sincronizaci�n no se pudo concluir. Intente otra vez o contacte con el soporte informatico!!");
//			}
//
//			builder.setNeutralButton(("OK"), new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//					if(sincronizaAnuncio.equals("S")){
//
//
//						try {
//							Cursor cursor = Aplicacion.bdatos.query("svm_anuncio_movil",
//									new String[] { "FECHA" },
//									null, null, null, null, null);
//
//							int nreg = cursor.getCount();
//
//							if(nreg > 0){
//								startActivity(new Intent(sincronizar.this, PPopUp.class));
//							}
//
//
//						} catch (Exception e) {
//							e.getMessage();
//						}
//
//
//					}
//
//
//					if (Aplicacion._primera_vez == 1) {
//						Aplicacion._primera_vez = 0;
//						startActivity(new Intent(sincronizar.this,
//								ControlGeneralSupervisores.class));
//					}
//
//					finish();
//
//				}
//			});
//
//			AlertDialog alertDialog = builder.create();
//			alertDialog.show();
//			alertDialog.setCancelable(false);
//
//		}
//
//	};
//
//	private void borraTablas() {
//		String _sql = "DROP TABLE IF EXISTS vt_negociacion_venta_cab;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS vt_negociacion_venta_det;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_cab;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_det;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_metas_punto_por_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_metas_punto_por_linea;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_evolucion_diaria_venta;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_precio_fijo_consulta;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_costo_venta_articulo;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_deuda_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_rebote_por_vendedor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_metas_punto_por_linea_sup;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_metas_punto_por_linea_empresa;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_positivacion_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_pedidos_en_reparto;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_seguimiento_premio_sup;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_premios_supervisores;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_general_sup;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_supervisor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_motivo_analisis_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_motivo_analisis_vendedor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_asistencia_vendedores;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_prom_vendedor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS stv_categoria;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_retorno_comentario;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_periodo_foto;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_supervisor_full;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_evol_diaria_venta;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS cliente_list_prec;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS articulos_precios;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_st_articulos;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_condicion_venta_cliente";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_vendedor_pedido_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS CLI_VENDEDOR;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_ultima_venta_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_prom_articulos_tar_cred";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cond_venta_vendedor";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_motivo_no_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_promociones_por_perfil";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_movimiento_gestor";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS SVM_MOVIMIENTO_GESTOR";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_promociones_art_cab";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_promociones_art_det";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_dia_visita_ruteo";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_seg_visitas_semanal";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_condicion_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_premios_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_produccion_semanal_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_premios_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_comision_supervisor";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_marca_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_vis_vendedores";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liquidacion_fuerza_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//47
//		_sql = "DROP TABLE IF EXISTS svm_liq_cuota_x_und_neg_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//48
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_mensual_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//49
//		_sql = "DROP TABLE IF EXISTS svm_liq_canasta_cte_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//50
//		_sql = "DROP TABLE IF EXISTS svm_liq_canasta_marca_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//51
//		_sql = "DROP TABLE IF EXISTS svm_produccion_semanal_gcs";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//52
//		_sql = "DROP TABLE IF EXISTS svm_prom_alc_cuota_mensual_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//53
//		_sql = "DROP TABLE IF EXISTS svm_liq_cuota_x_und_neg";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//54
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_mensual_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//55
//		_sql = "DROP TABLE IF EXISTS svm_cob_semanal_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		//SURTIDO EFICIENTE
//		_sql = "DROP TABLE IF EXISTS svm_surtido_eficiente";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//	}
//
//	private String eliminarNull(String palabra) {
//		if (palabra.equals("null")) {
//			palabra = "";
//		}
//		return palabra;
//	}
//
//	String _datosc;
//	String _id;
//
//	private void procesa_envia_datos_cliente() {
//
//		try {
//
//			String select = "Select COD_EMPRESA , COD_CLIENTE, COD_SUBCLIENTE, TELEFONO1, TELEFONO2	, "
//					+ " DIRECCION   , CERCA_DE 	 , LATITUD	  	, LONGITUD , TIPO 		, "
//					+ " FOTO_FACHADA, ESTADO	 , id 			, COD_VENDEDOR, COD_SUPERVISOR "
//					+ "  from svm_modifica_catastro " + "  where ESTADO = 'P' ";
//
//			Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//			cursor.moveToFirst();
//
//			int nreg = cursor.getCount();
//
//			for (int i = 0; i < nreg; i++) {
//
//				_id = cursor.getString(cursor.getColumnIndex("id"));
//				String cod_vendedor, cod_supervisor;
//
//				cod_vendedor = cursor.getString(cursor
//						.getColumnIndex("COD_VENDEDOR"));
//				cod_supervisor = cursor.getString(cursor
//						.getColumnIndex("COD_SUPERVISOR"));
//
//				if (cod_vendedor == null) {
//					cod_vendedor = "";
//				}
//
//				if (cod_supervisor == null) {
//					cod_supervisor = "";
//				}
//
//				_datosc = "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_EMPRESA"))
//						+ "'|"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
//						+ "'|"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_SUBCLIENTE")) + "'|" + "'"
//						+ cursor.getString(cursor.getColumnIndex("LATITUD"))
//						+ "'|" + "'"
//						+ cursor.getString(cursor.getColumnIndex("LONGITUD"))
//						+ "'|" + "'"
//						+ cursor.getString(cursor.getColumnIndex("TIPO")) + "'";
//
//				String _error;
//				_error = Aplicacion.cWS.onClickProcesaActualizaDatosClienteSup(
//						_datosc, cod_vendedor, cod_supervisor);
//
//				if (_error.indexOf("01*") >= 0 || _error.indexOf("03*") >= 0) {
//					ContentValues values = new ContentValues();
//					values.put("ESTADO", "E");
//
//					Aplicacion.bdatos.update("svm_modifica_catastro", values,
//							" id = " + _id, null);
//				}
//
//				if (_error.indexOf("Unable to resolve host") > -1) {
//					_error = "07*"
//							+ "Verifique su conexion a internet y vuelva a intentarlo";
//				}
//
//				cursor.moveToNext();
//
//			}
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//	String _autorizacion = "";
//
//	private void procesa_envia_autorizaciones() {
//
//		try {
//
//			String select = "Select COD_VENDEDOR , COD_CLIENTE, COD_SUBCLIENTE, COD_ARTICULO, COD_UNIDAD_MEDIDA, "
//					+ " CANTIDAD     , DESCUENTO  , CLAVE	  	  , CONTRA_CLAVE, ESTADO 		   ,"
//					+ " id 			 , LISTA_PRECIO, TOTAL_PEDIDO , TIPO        , ifnull(COMENTARIO,'') COMENTARIO "
//					+ "  from svm_autorizaciones " + "  where ESTADO = 'P' ";
//
//			Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//			cursor.moveToFirst();
//
//			int nreg = cursor.getCount();
//
//			for (int i = 0; i < nreg; i++) {
//
//				_id = cursor.getString(cursor.getColumnIndex("id"));
//
//				_autorizacion = "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_SUBCLIENTE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_VENDEDOR"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("LISTA_PRECIO"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_ARTICULO"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_UNIDAD_MEDIDA"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("CANTIDAD"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("DESCUENTO"))
//								.replace(",", ".")
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("CLAVE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("CONTRA_CLAVE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("TOTAL_PEDIDO")) + "'," + "'"
//						+ cursor.getString(cursor.getColumnIndex("TIPO"))
//						+ "'," + "'"
//						+ cursor.getString(cursor.getColumnIndex("COMENTARIO"))
//						+ "'";
//
//				String _error = "";
//				_error = Aplicacion.cWS.procesaNegociacionWS(_autorizacion);
//				// INSERT INTO vt_negociacion_precio ( cod_cliente,
//				// cod_subcliente ,cod_vendedor, cod_lista_precio, cod_articulo,
//				// cod_unidad_medida, cantidad, porc_desc, nro_control,
//				// nro_autorizacion, total_pedido, tipo
//
//				if (_error.indexOf("01*") >= 0 || _error.indexOf("03*") >= 0) {
//					ContentValues values = new ContentValues();
//					values.put("ESTADO", "E");
//
//					Aplicacion.bdatos.update("svm_autorizaciones", values,
//							" id = " + _id, null);
//				}
//
//				if (_error.indexOf("Unable to resolve host") > -1) {
//					_error = "07*"
//							+ "Verifique su conexion a internet y vuelva a intentarlo";
//				}
//
//				cursor.moveToNext();
//
//			}
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//	private void anulaVentasTresDias() {
//		try {
//
//			String select = " select FECHA, NUMERO, COD_VENDEDOR from vt_pedidos_cab where ESTADO = 'P' ";
//
//			Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//			cursor.moveToFirst();
//			int nreg = cursor.getCount();
//
//			for (int i = 0; i < nreg; i++) {
//
//				String numero, fecha, cod_vendedor;
//				fecha = cursor.getString(cursor.getColumnIndex("FECHA"));
//				numero = cursor.getString(cursor.getColumnIndex("NUMERO"));
//				cod_vendedor = cursor.getString(cursor.getColumnIndex("COD_VENDEDOR"));
//
//				final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos
//																	// al d�a
//				Date hoy = new Date(); // Fecha de hoy
//
//				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//				Date date = dateFormat.parse(fecha);
//
//				Calendar calendar = new GregorianCalendar();
//				calendar.setTime(date);
//				java.sql.Date fecha_ped = new java.sql.Date(
//						calendar.getTimeInMillis());
//
//				long diferencia = (hoy.getTime() - fecha_ped.getTime())
//						/ MILLSECS_PER_DAY;
//
//				if (diferencia >= 3) {
//					String update = "update vt_pedidos_cab set ESTADO = 'A'"
//							 + "  where NUMERO    = '"+numero+"' "
//							 + "    and COD_VENDEDOR = '"+cod_vendedor+"'";
//
//					Aplicacion.bdatos.execSQL(update);
//				}
//
//				cursor.moveToNext();
//			}
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			Toast.makeText(sincronizar.this, "Error" + err,
//					Toast.LENGTH_LONG).show();
//		}
//	}
//
//	String _nopositivado;
//	private void procesa_envia_no_positivados() {
//
//		try {
//
//			String select = "Select COD_EMPRESA, COD_SUCURSAL, COD_CLIENTE, COD_SUBCLIENTE, COD_VENDEDOR, COD_MOTIVO, "
//					+ " OBSERVACION, FECHA		 , LATITUD	  , LONGITUD	  , ESTADO 	  , HORA_ALTA	, id "
//					+ "  from vt_marcacion_visita "
//					+ "  where FECHA <> '"
//					+ Aplicacion.utilidades.obtieneFechaActual()
//					+ "'" + "    and ESTADO = 'P' ";
//
//			Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//			cursor.moveToFirst();
//
//			int nreg = cursor.getCount();
//
//			for (int i = 0; i < nreg; i++) {
//
//				_id = cursor.getString(cursor.getColumnIndex("id"));
//				_nopositivado = "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_EMPRESA"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_SUCURSAL"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_CLIENTE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_SUBCLIENTE"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor
//								.getColumnIndex("COD_VENDEDOR"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("COD_MOTIVO"))
//						+ "',"
//						+ "'"
//						+ cursor.getString(cursor.getColumnIndex("OBSERVACION"))
//						+ "'," + "to_date('"
//						+ cursor.getString(cursor.getColumnIndex("FECHA"))
//						+ "','DD/MM/YYYY')," + "to_date('"
//						+ cursor.getString(cursor.getColumnIndex("FECHA"))
//						+ " "
//						+ cursor.getString(cursor.getColumnIndex("HORA_ALTA"))
//						+ "','dd/MM/yyyy hh24:mi:ss')," + "'"
//						+ cursor.getString(cursor.getColumnIndex("LATITUD"))
//						+ "'," + "'"
//						+ cursor.getString(cursor.getColumnIndex("LONGITUD"))
//						+ "'";
//
//				String _error;
//				_error = Aplicacion.cWS
//						.onClickEnviaMarcacionVisita2(_nopositivado);
//
//				if (_error.indexOf("01*") >= 0) {
//
//					_error = "Marcaci�n de visita enviada con exito!!";
//				}
//
//				ContentValues values = null;
//				if (_error.equals("Marcaci�n de visita enviada con exito!!")) {
//					values = new ContentValues();
//					values.put("ESTADO", "E");
//					try {
//						Aplicacion.bdatos.update(
//								"CLI_VENDEDOR",
//								values,
//								" COD_CLIENTE = '"
//										+ Aplicacion._code_cli.toString()
//										+ "'" + " and COD_SUBCLIENTE = '"
//										+ Aplicacion._code_subcli + "'",
//								null);
//
////						if (!_id.equals("")) {
////							actualiza_estado_envio_marcacion(_id);
////						}
//					} catch (Exception e) {
//
//						e.printStackTrace();
//					}
//
//				}
//
//				cursor.moveToNext();
//
//			}
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//	private void borraTablasInformes() {
//
//		String _sql = "DROP TABLE IF EXISTS svm_cliente_dia_visita_ruteo";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_cab;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_det;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_rebote_por_vendedor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_pedidos_en_reparto;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_seg_visitas_semanal";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		//BORRAR NUEVA TABLA SI EXISTE
//
//	}
//
//	private void borraTablasInformes2() {
//
//		String _sql = "DROP TABLE IF EXISTS svm_cliente_dia_visita_ruteo";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_cab;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_control_venta_diaria_det;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_rebote_por_vendedor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_pedidos_en_reparto;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_seg_visitas_semanal";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_sem_visita_ruteo";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_cuota_x_und_neg_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_prom_alc_cuota_mensual_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_mensual_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cob_semanal_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_premios_vend";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_metas_punto_por_cliente";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_evolucion_diaria_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_deuda_cliente";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_precio_fijo_consulta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_movimiento_gestor";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		//BORRAR NUEVA TABLA SI EXISTE
//
//	}
//
//	private void borraTablasReportes() {
//
//		String _sql = "DROP TABLE IF EXISTS svm_liq_premios_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_canasta_marca_sup;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_canasta_cte_sup;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_produccion_semanal_gcs;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_cuota_x_und_neg;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liquidacion_fuerza_venta";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_liq_comision_supervisor";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cobertura_mensual_sup";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		//BORRAR NUEVA TABLA SI EXISTE
//
//	}
//
//	private void borraTablasVisitas() {
//
//		String _sql = "DROP TABLE IF EXISTS svm_motivo_analisis_cliente";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_supervisor;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_cliente_supervisor_full;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//		_sql = "DROP TABLE IF EXISTS svm_positivacion_cliente;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//		//BORRAR NUEVA TABLA SI EXISTE
//	}
//
//	public void sincronizaInforme() {
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					String erra = Boolean.toString(respuestaWS);
//					erra = erra + "";
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//						int auxActual = 0, auxInc = 17;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						borraTablasInformes();
//
//
//
//						///inicio informe //
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_dia_visita_ruteo();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_MOVIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CANT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANT_NO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_dia_visita_ruteo",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_cab();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"RUTEO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"POSITIVADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"POS_FUERA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_cab",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_det();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"PRECIO_UNITARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"TOT_DESCUENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_det",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_rebotes_por_vendedores();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"CODIGO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOM_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_PENALIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"FECHA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_rebote_por_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_pedidos_en_reparto();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"NRO_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TEL_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"NRO_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOT_COMPROBANTE",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_pedidos_en_reparto",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_sem_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_sem_visita_ruteo();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_sem_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_INICIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_FINAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CANT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"CANT_NO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"CANT_NO_ANTENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_sem_visita_ruteo",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						// Kia Inicio
//
//						// Kia Fin
//
//
//
//						archivo.delete();
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(1000);
//
//
//
//					 try{
//
//						ContentValues values = new ContentValues();
//
//						values.put("ULTIMA_SINCRO",
//								Aplicacion._fecha_ult_actualizacion);
//						values.put("ULTIMA_VEZ",
//								Aplicacion.utilidades.obtieneFechaHoraActual());
//
//
//						Aplicacion.bdatos.update("svm_vendedor_pedido", values, null, null);
//					 }catch(Exception e){
//						 e.printStackTrace();
//					 }
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//
//	}
//
//	public void sincronizaInformeNuevo() {
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//			private int contador = 0, divisor  = 16;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					String erra = Boolean.toString(respuestaWS);
//					erra = erra + "";
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//						int auxActual = 0, auxInc = 17;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						borraTablasInformes2();
//
//
//
//						///inicio informe //
//
//						//REPORTE DE VISITAS - RUTEO DEL DIA
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_dia_visita_ruteo();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_dia_visita_ruteo.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"FEC_MOVIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CANT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANT_NO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_dia_visita_ruteo",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						//PEDIDO DE VENDEDOR CABECERA
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_cab();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_cab.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"RUTEO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"POSITIVADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"POS_FUERA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOTAL_PEDIDO",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_cab",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//PEDIDO DE VENDEDOR DETALLE
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_control_venta_diaria_det();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_control_venta_diaria_det.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"COD_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DESC_ARTICULO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"PRECIO_UNITARIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"TOT_DESCUENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_control_venta_diaria_det",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//REBOTE POR VENDEDOR
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_rebotes_por_vendedores();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_rebotes_por_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"CODIGO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOM_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_PENALIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"FECHA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_rebote_por_vendedor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//PEDIDO EN REPARTO
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_pedidos_en_reparto();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_pedidos_en_reparto.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"NRO_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TEL_REPARTIDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_PLANILLA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"NRO_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"NOM_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"NOM_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"SIGLAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"DECIMALES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 16:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 17:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 18:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"TOT_COMPROBANTE",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_pedidos_en_reparto",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//SEGUIMIENTO DE VISITA
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_seg_visitas_semanal();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_seg_visitas_semanal.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_MOVIMIENTO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"FEC_INICIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"FEC_FIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"CANT_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"CANT_NO_VENDIDO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"CANT_NO_VISITADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"SEMANA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"PORC",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_seg_visitas_semanal",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//CUOTA POR UNIDAD DE NEGOCIO DE VENDEDORES
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_liq_cuota_x_unidad_neg_vend();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_liq_cuota_x_und_neg_vend\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"MONTO_VENTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"MONTO_CUOTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PORC_PREMIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"FEC_DESDE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"FEC_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"PORC_ALC_PREMIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_A_COBRAR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_liq_cuota_x_und_neg_vend",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_liq_cuota_x_und_neg_vend\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//PROMEDIO DE ALCANCE DE CUOTA DE VENDEDOR
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_prom_alc_cuota_mensual_sup();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_prom_alc_cuota_mensual_sup\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_prom_alc_cuota_mensual_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"PORC_LOG",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"PORC_ALC",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"MONTO_PREMIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"TOTAL_FACTURADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"META",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_prom_alc_cuota_mensual_sup",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_prom_alc_cuota_mensual_sup\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//COBERTURA MENSUAL DE VENDEDORES
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cobertura_mensual_vend();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_cobertura_mensual_vend\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cobertura_mensual_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TOT_CLI_CART",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CANT_POSIT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PORC_COB",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PORC_LOGRO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"FEC_DESDE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"FEC_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"PREMIOS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_A_COBRAR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cobertura_mensual_vend",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_cobertura_mensual_vend\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//COBERTURA SEMANAL DE VENDEDORES
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cob_semanal_vend();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_cob_semanal_vend\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//							contador++;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cob_semanal_vend.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"SEMANA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"TOT_CLIENTES",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"CLIENT_VENTAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PORC_COBERTURA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PERIODO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_A_COBRAR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cob_semanal_vend",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_cob_semanal_vend\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress((100/divisor)*contador);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//AVANCE DE COMISION DE VENDEDORES
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_premios_vend();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_vend.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"VENDEDOR_PERSONA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"TIP_COM",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"COD_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"DESC_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"MONTO_META",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"PORC_COBERTURA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"PORC_LOG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"MONTO_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 14:
//												values.put(
//														"TOT_CLIENTES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//
//												values.put(
//														"CLIENTES_VISITADOS",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_premios_vend",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//
//
//						   archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//						//VENTAS POR CLIENTES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_metas_punto_por_cliente();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_metas_punto_por_cliente.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"CODIGO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"NOM_SUBCLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"CIUDAD",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"COD_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"DESC_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"LISTA_PRECIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"MAYOR_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"VENTA_3",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"MIX_3",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"VENTA_4",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 14:
//												values.put(
//														"MIX_4",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 15:
//												values.put(
//														"METAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 16:
//												values.put(
//														"TOT_SURTIDO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 17:
//												values.put(
//														"MES_1",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 18:
//												values.put(
//														"MES_2",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MES_2",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_metas_punto_por_cliente",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//						//EVOLUCION DIARIA DE VENTAS
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_evolucion_diaria_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_evol_diaria_venta();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_evolucion_diaria_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"NOM_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PEDIDO_2_ATRAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PEDIDO_1_ATRAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"TOTAL_PEDIDOS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"TOTAL_FACT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"META_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"META_LOGRADA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"PROY_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"TOTAL_REBOTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 14:
//												values.put(
//														"TOTAL_DEVOLUCION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 15:
//												values.put(
//														"CANT_CLIENTES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 16:
//												values.put(
//														"CANT_POSIT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 17:
//												values.put(
//														"EF_VISITA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 18:
//												values.put(
//														"DIA_TRAB",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 19:
//												values.put(
//														"PUNTAJE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"SURTIDO_EF",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_evol_diaria_venta",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//						//DEUDA DE CLIENTES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_deuda_por_clientes();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//
//									String indice = "CREATE INDEX IF NOT EXISTS ind_cod_vendedor on svm_deuda_cliente "
//											+ "(cod_vendedor)";
//									Aplicacion.bdatos.execSQL(indice);
//
//									indice = "CREATE INDEX IF NOT EXISTS ind_descripcion on svm_deuda_cliente "
//											+ "(desc_cliente, desc_subcliente)";
//									Aplicacion.bdatos.execSQL(indice);
//
//									indice = "CREATE INDEX IF NOT EXISTS ind_cod_cliente on svm_deuda_cliente "
//											+ "(cod_cliente)";
//									Aplicacion.bdatos.execSQL(indice);
//
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_deuda_cliente.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_SUBCLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_SUBCLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"DESC_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"COD_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"DESC_SUPERVISOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"FEC_EMISION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"FEC_VENCIMIENTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 12:
//												values.put(
//														"TIP_DOCUMENTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 13:
//												values.put(
//														"NRO_DOCUMENTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 14:
//												values.put(
//														"DIAS_ATRAZO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 15:
//												values.put(
//														"ABREVIATURA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"SALDO_CUOTA",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos.insert(
//															"svm_deuda_cliente",
//															null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//						//LISTA DE PRECIOS
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_precio_fijo_consulta();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_precio_fijo_consulta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_LISTA_PRECIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_LISTA_PRECIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_ARTICULO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_ARTICULO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PREC_CAJA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PREC_UNID",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"REFERENCIA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"DECIMALES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"SIGLAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"SIGLAS",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_precio_fijo_consulta",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//						//MOVIMIENTO DE GESTORES
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_movimiento_gestor();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//								contador++;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_movimiento_gestor.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"NOM_VENDEDOR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_CLIENTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"FEC_ASISTENCIA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"HORA_ENTRADA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"HORA_SALIDA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MARCACION",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_movimiento_gestor",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress((100/divisor)*contador);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(1000);
//
//
//
//					 try{
//
//						ContentValues values = new ContentValues();
//
//						values.put("ULTIMA_SINCRO",
//								Aplicacion._fecha_ult_actualizacion);
//						values.put("ULTIMA_VEZ",
//								Aplicacion.utilidades.obtieneFechaHoraActual());
//
//
//						Aplicacion.bdatos.update("svm_vendedor_pedido", values, null, null);
//					 }catch(Exception e){
//						 e.printStackTrace();
//					 }
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//
//	}
//
//	public void sincronizaReportes() {
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					String erra = Boolean.toString(respuestaWS);
//					erra = erra + "";
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//						int auxActual = 0, auxInc = 17;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						borraTablasReportes();
//
//						///inicio informe //
//
//						//Comision de supervisores
//						Aplicacion.bdatos.beginTransaction();
//
//						 try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_premios_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_premios_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"COD_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"DESC_MARCA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"MONTO_META",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_LOG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PORC_COBERTURA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"TOT_CLIENTES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"CLIENTES_VISITADOS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 11:
//												values.put(
//														"MONTO_COBRAR",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//
//											ini = i + 1;
//										}
//
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"TIP_COM",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_premios_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//
//
//						 archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						//Canasta de marcas
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_liq_canasta_marca_sup();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_liq_canasta_marca_sup\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_canasta_marca_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_MARCA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_MARCA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"VENTAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"CUOTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"VALOR_CANASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"PESO_PUNT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"PUNTAJE_MARCA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"PORC_CUMP",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"FEC_DESDE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"FEC_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"COD_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DESC_UNID_NEGOCIO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_A_COBRAR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_liq_canasta_marca_sup",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_liq_canasta_marca_sup\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						//Canasta de clientes
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
////							mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n");
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_liq_canasta_cte_sup();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_liq_canasta_cte_sup\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_liq_canasta_cte_sup.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_DESDE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"FEC_HASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"VENTAS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"CUOTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"VALOR_CANASTA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"PUNTAJE_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"PORC_CUMP",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"PESO_PUNT",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"MONTO_A_COBRAR",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_liq_canasta_cte_sup",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_liq_canasta_cte_sup\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//
//						//Produccion semanal
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_produccion_semanal();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//								mensajebd("Atencion", "Error al crear la tabla svm_produccion_semanal_gcs\n" + err);
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_produccion_semanal_gcs.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"CANTIDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"SEMANA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"MONTO_VIATICO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"MONTO_TOTAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"PERIODO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"MONTO_X_POR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"CANT_CLIENTE",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_produccion_semanal_gcs",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//												mensajebd("Atencion", "Error al cargar la tabla svm_produccion_semanal_gcs\n" + err);
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//						}
//
//						archivo.delete();
//
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						//Cuota por unidad de negocios
//						Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_cuota_x_unidad_neg();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liq_cuota_x_und_neg\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_cuota_x_und_neg.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"FEC_DESDE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"COD_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"DESC_UNID_NEGOCIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PORC_ALC_PREMIO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"MONTO_VENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"MONTO_CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_cuota_x_und_neg",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liq_cuota_x_und_neg\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//						//Comprobantes a emitir
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
////								mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n");
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liquidacion_fuerza_venta();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_liquidacion_fuerza_venta\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liquidacion_fuerza_venta.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"FEC_COMPROBANTE",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"OBSERVACION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"DESCRIPCION",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"TIP_COMPROBANTE_REF",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"TOT_IVA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"TOT_GRAVADA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"TOT_EXENTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"TOT_COMPROBANTE",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liquidacion_fuerza_venta",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_liquidacion_fuerza_venta\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//							//Extracto de salario
//							Aplicacion.bdatos.beginTransaction();
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_liq_comision_supervisor();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_liq_comision_supervisor.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_CONCEPTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"DESC_CONCEPTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"MONTO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"TIPO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"NRO_ORDEN",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"NRO_CUOTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"FEC_HASTA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 8:
//												values.put(
//														"COD_MONEDA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 9:
//												values.put(
//														"DECIMALES",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 10:
//												values.put(
//														"SIGLAS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"COMENT",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_liq_comision_supervisor",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//												}
//
//												progressBar
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//
//							}
//							archivo.delete();
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//						//Cobertura mensual
//							Aplicacion.bdatos.beginTransaction();
//
//							try {
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								_sql = Aplicacion.sentencias
//										.createTableSvm_cobertura_mensual_sup();
//
//								try {
//									Aplicacion.bdatos.execSQL(_sql);
//								} catch (Exception e) {
//									String err = e.getMessage();
//									err = "" + err;
//									mensajebd("Atencion", "Error al crear la tabla svm_cobertura_mensual_sup\n" + err);
//								}
//
//								String linea;
//								double sum = 0.0;
//								int lNumeroLineas = 0;
//
//								while ((linea = br.readLine()) != null) {
//									lNumeroLineas++;
//								}
//								fac = (float) 100 / (float) lNumeroLineas;
//
//								archivo = new File(
//										"/data/data/apolo.supervisores.com/svm_cobertura_mensual_sup.txt");
//								fr = new FileReader(archivo);
//								br = new BufferedReader(fr);
//
//								while ((linea = br.readLine()) != null) {
//
//									int ini = 0;
//									int fin = 0;
//									int c = 0;
//
//									ContentValues values = new ContentValues();
//									for (int i = 0; i < linea.length(); i++) {
//
//										if (linea.charAt(i) == '|') {
//											fin = i;
//											c = c + 1;
//
//											switch (c) {
//											case 1:
//												values.put(
//														"COD_EMPRESA",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 2:
//												values.put(
//														"TOT_CLI_CART",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 3:
//												values.put(
//														"CANT_POSIT",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 4:
//												values.put(
//														"TOT_CLIEN_ASIG",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 5:
//												values.put(
//														"PORC_LOGRO",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 6:
//												values.put(
//														"PORC_COB",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											case 7:
//												values.put(
//														"PREMIOS",
//														eliminarNull(linea
//																.substring(ini, fin)));
//												break;
//											}
//											ini = i + 1;
//										}
//
//										if (i == linea.length() - 1) {
//											if (i > 0) {
//
//												sum = sum + 1;
//
//												values.put(
//														"MONTO_A_COBRAR",
//														eliminarNull(linea
//																.substring(
//																		ini,
//																		linea.length())));
//
//												try {
//													Aplicacion.bdatos
//															.insert("svm_cobertura_mensual_sup",
//																	null, values);
//
//												} catch (Exception e) {
//													String err = e.getMessage();
//													err = "" + err;
//													mensajebd("Atencion", "Error al cargar la tabla svm_cobertura_mensual_sup\n" + err);
//												}
//
//												//
//												progressBarProcess
//														.setProgress((int) (fac * sum));
//											}
//											values = new ContentValues();
//											ini = 0;
//											c = 0;
//										}
//									}
//
//								}
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							archivo.delete();
//
//
//							auxActual = auxActual + auxInc;
//							progressBar.setProgress(auxActual);
//							progressBarProcess.setProgress(0);
//
//
//							Aplicacion.bdatos.setTransactionSuccessful();
//							Aplicacion.bdatos.endTransaction();
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(1000);
//
//
//
//					 try{
//
//						ContentValues values = new ContentValues();
//
//						values.put("ULTIMA_SINCRO",
//								Aplicacion._fecha_ult_actualizacion);
//						values.put("ULTIMA_VEZ",
//								Aplicacion.utilidades.obtieneFechaHoraActual());
//
//
//						Aplicacion.bdatos.update("svm_vendedor_pedido", values, null, null);
//					 }catch(Exception e){
//						 e.printStackTrace();
//					 }
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//
//	}
//
//	public void sincronizaVisitas() {
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					String erra = Boolean.toString(respuestaWS);
//					erra = erra + "";
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//						int auxActual = 0, auxInc = 17;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						borraTablasVisitas();
//
//						///inicio informe //
//						//1visita
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_motivo_analisis_cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_motivo_analisis_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_MOTIVO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESCRIPCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										case 4:
//											values.put(
//													"ESTADO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										case 5:
//											values.put(
//													"PUNTUACION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_GRUPO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_motivo_analisis_cliente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//2visita
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_supervisor();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_supervisor",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//3visita
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_cliente_supervisor_full();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_cliente_supervisor_full.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//
//										case 1:
//											values.put(
//													"COD_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"DESC_SUPERVISOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"NOMBRE_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"LONGITUD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"TIEMPO_MIN",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"TIEMPO_MAX",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"LATITUD",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_cliente_supervisor_full",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//						//4visita
//						Aplicacion.bdatos.beginTransaction();
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias
//									.createTableSvm_positivacion_cliente();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_positivacion_cliente.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"COD_EMPRESA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"COD_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"DESC_VENDEDOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TIP_CAUSAL",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"COD_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"COD_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 7:
//											values.put(
//													"DESC_CLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 8:
//											values.put(
//													"DESC_SUBCLIENTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 9:
//											values.put(
//													"DIRECCION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 10:
//											values.put(
//													"DESC_CIUDAD",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 11:
//											values.put(
//													"RUC",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 12:
//											values.put(
//													"DESC_REGION",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 13:
//											values.put(
//													"DESC_ZONA",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 14:
//											values.put(
//													"TELEFONO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 15:
//											values.put(
//													"IND_POS",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//											values.put("COD_SUPERVISOR",
//													Aplicacion.et_login
//															.getText()
//															.toString());
//											values.put(
//													"MONTO_VENTA",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos
//														.insert("svm_positivacion_cliente",
//																null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											//
//											progressBarProcess
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//						auxActual = auxActual + auxInc;
//						progressBar.setProgress(auxActual);
//						progressBarProcess.setProgress(0);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(1000);
//
//					 try{
//
//						ContentValues values = new ContentValues();
//
//						values.put("ULTIMA_SINCRO",
//								Aplicacion._fecha_ult_actualizacion);
//						values.put("ULTIMA_VEZ",
//								Aplicacion.utilidades.obtieneFechaHoraActual());
//
//
//						Aplicacion.bdatos.update("svm_vendedor_pedido", values, null, null);
//					 }catch(Exception e){
//						 e.printStackTrace();
//					 }
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//
//	}
//
//
//	//SINCRONIZA EXTEACTO TARJETA PRONET
//
//	private class PbtExtractoTarjetaPro1 extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			pbarDialog = ProgressDialog.show(sincronizar.this, "Un momento...",
//					"Consultando disponibilidad...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			Aplicacion.cWS.setMethodName("ProcesaImeiSupervisorAct");
//
//			imeiBD = Aplicacion.cWS.procesaImeiSupervisor(Aplicacion.et_login
//					.getText().toString());
//
//			procesa_envia_autorizaciones();
//			anulaVentasTresDias();
//			procesa_envia_no_positivados();
//			procesa_envia_datos_cliente();
//			return null;
//
//		}
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//
//			if (Aplicacion._imei == null) {
//				Aplicacion._imei = "";
//			}
//
//			if (!Aplicacion._imei.equals("")) {
//
//				if (imeiBD.indexOf("Unable to resolve host") > -1) {
//					Toast.makeText(
//							sincronizar.this,
//							"Verifique su conexion a internet y vuelva a intentarlo",
//							Toast.LENGTH_SHORT).show();
//					sincronizar.super.onBackPressed();
//					return;
//				}
//
//				// 5664
//
//				String[] im = imeiBD.split("-");
//
//				if (!Aplicacion._imei.equals(im[0])) {
//
//					// if (Aplicacion._imei.equals(im[0])) {
//					Builder builder2 = new Builder(sincronizar.this);
//
//					builder2.setMessage("Este equipo no puede sincronizar. Contacte con el soporte informatico!! ");
//					Aplicacion._fecha_ult_actualizacion = "01/01/2013";
//					Aplicacion._fecha_carga_ruteo = "01/01/2013";
//
//					String sql = "update svm_vendedor_pedido "
//							+ "set ULTIMA_SINCRO = ''";
//
//					Aplicacion.bdatos.execSQL(sql);
//
//					deleteTablasExtractos();
//
//					builder2.setNeutralButton(("OK"), new OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//
//							finish();
//
//						}
//					});
//
//					AlertDialog alertDialog2 = builder2.create();
//					alertDialog2.show();
//					alertDialog2.setCancelable(false);
//
//				} else {
//
//					if (!im[1].equals("Y")) {
//
//						Builder builder2 = new Builder(sincronizar.this);
//
//						builder2.setMessage(im[1]);
//
//						builder2.setNeutralButton(("OK"),
//								new OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//
//										finish();
//
//									}
//								});
//
//						AlertDialog alertDialog2 = builder2.create();
//						alertDialog2.show();
//						alertDialog2.setCancelable(false);
//
//					} else {
//
//						Aplicacion.rango_distancia = im[2];
//						Aplicacion.tipo_sup = im[3];
//						Aplicacion.min_fotos = Integer.parseInt(im[4]);
//						Aplicacion.max_fotos = Integer.parseInt(im[5]);
//						Aplicacion._fecha_ult_actualizacion = im[6];
//						Aplicacion.ind_foto = im[7];
//						Aplicacion._fecha_carga_ruteo = im[8];
//						Aplicacion.max_desc = Double.parseDouble(im[9]);
//						Aplicacion.max_desc_var = Double.parseDouble(im[10]);
//						Aplicacion.version_sistema = Double.parseDouble(im[11]);
//						Aplicacion._per_vender_sup = im[12];
//						Aplicacion._int_marcacion = Integer.parseInt(im[13]);
//
//						if (Aplicacion._migrar_ahora == "S")
//							;
//						{
//							if (!(Aplicacion.version_sistema == Double
//									.parseDouble(Aplicacion._version_soft))
//									&& !Aplicacion._imei.equals("358212085168242")) {
//
//								Builder builder2 = new Builder(sincronizar.this);
//
//								builder2.setMessage("FAVOR ACTUALIZAR LA VERSION !!");
//
//								builder2.setNeutralButton(("OK"),
//										new OnClickListener() {
//
//											@Override
//											public void onClick(
//													DialogInterface dialog,
//													int which) {
//
//												finish();
//
//											}
//										});
//
//								AlertDialog alertDialog2 = builder2.create();
//								alertDialog2.show();
//								alertDialog2.setCancelable(false);
//								return;
//
//							} else {
//								sincronizando = "S";
//
//								new PbtExtractoTarjetaPro2().execute();
//							}
//
//						}
//
//					}
//
//				}
//
//			}
//
//		}
//
//
//		private class PbtExtractoTarjetaPro2 extends AsyncTask<Void, Void, Void> {
//
//			@Override
//			protected void onPreExecute() {
//
//				try {
//					pbarDialog.dismiss();
//				} catch (Exception e) {
//
//				}
//
//				pbarDialog = ProgressDialog.show(sincronizar.this,
//						"Un momento...", "Generando Archivos...", true);
//
//			}
//
//			@Override
//			protected Void doInBackground(Void... params) {
//
//				try {
//
//					respuestaWS = Aplicacion.cWS.onClickGeneraArchivosExtractoTarjeta();
//					if (respuestaWS) {
//						Aplicacion._conectado = "S";
//					} else {
//						Aplicacion._conectado = "N";
//					}
//
//					return null;
//				} catch (Exception e) {
//					mensajebd("Error al conectar",
//							"Intente otra vez o llame al Soporte Informatico de la empresa!");
//					return null;
//
//				}
//			}
//
//			@Override
//			protected void onPostExecute(Void unused) {
//
//				pbarDialog.dismiss();
//				new PbtExtractoTarjetaPro3().execute();
//
//			}
//		}
//
//	}
//
//	private class PbtExtractoTarjetaPro3 extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			try {
//				pbarDialog.dismiss();
//			} catch (Exception e) {
//
//			}
//
//			pbarDialog = ProgressDialog.show(sincronizar.this,
//					"Un momento...", "Obteniendo Archivos...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			try {
//
//				respuestaWS = Aplicacion.cWS.onClickTraeArchivosExtractoTarjeta();
//				if (respuestaWS) {
//					Aplicacion._conectado = "S";
//				} else {
//					Aplicacion._conectado = "N";
//				}
//
//				return null;
//			} catch (Exception e) {
//				mensajebd("Error al conectar",
//						"Intente otra vez o llame al Soporte Informatico de la empresa!");
//				return null;
//
//			}
//		}
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//
//			if (Aplicacion._conectado.equals("S")){
//				SincronizaExtracto();
//				return;
//			}
//
//		}
//	}
//
//	private void SincronizaExtracto(){
//
//
//		th = new Thread(new Runnable() {
//			private String _sql;
//
//			@SuppressWarnings("resource")
//			@Override
//			public void run() {
//
//				progressBarProcess.setProgress(0);
//				if (progressBar.getProgress() == 100) {
//					progressBar.setProgress(0);
//				} else {
//					if (respuestaWS) {
//
//						progressBar.setProgress(0);
//						progressBarProcess.setProgress(0);
//						float fac = 0;
//
//						File archivo = null;
//						FileReader fr = null;
//						BufferedReader br = null;
//
//						deleteTablasExtractos();
//
//						Aplicacion.bdatos.beginTransaction();
//
//
//						try {
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_extracto_tarjeta_pro.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							_sql = Aplicacion.sentencias.createTableSvm_extracto_tarjeta_pro();
//
//							try {
//								Aplicacion.bdatos.execSQL(_sql);
//
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = "" + err;
//							}
//
//							String linea;
//							double sum = 0.0;
//							int lNumeroLineas = 0;
//
//							while ((linea = br.readLine()) != null) {
//								lNumeroLineas++;
//							}
//							fac = (float) 100 / (float) lNumeroLineas;
//
//							archivo = new File(
//									"/data/data/apolo.supervisores.com/svm_extracto_tarjeta_pro.txt");
//							fr = new FileReader(archivo);
//							br = new BufferedReader(fr);
//
//							while ((linea = br.readLine()) != null) {
//
//								int ini = 0;
//								int fin = 0;
//								int c = 0;
//
//								ContentValues values = new ContentValues();
//								for (int i = 0; i < linea.length(); i++) {
//
//									if (linea.charAt(i) == '|') {
//										fin = i;
//										c = c + 1;
//
//										switch (c) {
//										case 1:
//											values.put(
//													"TIP_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 2:
//											values.put(
//													"FEC_COMPROBANTE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 3:
//											values.put(
//													"TOT_DEBE",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 4:
//											values.put(
//													"TOT_HABER",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 5:
//											values.put(
//													"SALDO_ANTERIOR",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//										case 6:
//											values.put(
//													"NRO_TARJETA_CREDITO",
//													eliminarNull(linea
//															.substring(ini, fin)));
//											break;
//
//										}
//										ini = i + 1;
//									}
//
//									if (i == linea.length() - 1) {
//										if (i > 0) {
//
//											sum = sum + 1;
//
//											values.put(
//													"NRO_ORDEN",
//													eliminarNull(linea
//															.substring(
//																	ini,
//																	linea.length())));
//
//											try {
//												Aplicacion.bdatos.insert(
//														"svm_extracto_tarjeta_pro",
//														null, values);
//
//											} catch (Exception e) {
//												String err = e.getMessage();
//												err = "" + err;
//											}
//
//											progressBar
//													.setProgress((int) (fac * sum));
//										}
//										values = new ContentValues();
//										ini = 0;
//										c = 0;
//
//									}
//								}
//
//							}
//
//						} catch (Exception e) {
//							String err = e.getMessage();
//							err = "" + err;
//
//						}
//
//						archivo.delete();
//
//
//						progressBar.setProgress(100);
//						progressBarProcess.setProgress(100);
//
//						Aplicacion.bdatos.setTransactionSuccessful();
//						Aplicacion.bdatos.endTransaction();
//
//					}
//
//				}
//
//				Message message = new Message();
//				message.what = 2;
//				getHandler.sendMessage(message);
//
//			}
//
//		});
//
//		th.start();
//	}
//
//	private void deleteTablasExtractos() {
//
//		String _sql = "delete from svm_extracto_tarjeta_pro;";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = "" + err;
//		}
//
//	}
//
//	private void deleteTablaAnuncio(){
//		String _sql = " Delete from svm_anuncio_movil ";
//
//		try {
//			Aplicacion.bdatos.execSQL(_sql);
//
//		} catch (Exception e) {
//			String err = e.getMessage();
//			err = err + "";
//		}
//	}
//
//	public void actualizaIndiceDePromocion(){
//
//		String sql = "";
//		sql = "UPDATE articulos_precios SET IND_PROMO_ACT = 'N'";
//
//		try {
//			Aplicacion.bdatos.execSQL(sql);
////			mensajebd("Operacion exitosa", "Se actualizo el campo");
//		} catch (Exception e) {
//			mensajebd("Error", "No se pudo actualizar el campo\n" + e.getMessage());
//		}
//
//
////		sql = "UPDATE articulos_precios SET IND_PROMO_ACT = 'S'"
////			+ " WHERE EXISTS "
////			+ "(SELECT * FROM svm_promociones_art_det b "
////			+ "  WHERE b.COD_ARTICULO = articulos_precios.cod_articulo)";
////
//
//		sql = "UPDATE articulos_precios SET IND_PROMO_ACT = 'S'"
//				+ " WHERE cod_empresa = 1"
//				+ "   and cod_empresa      = '" + Aplicacion.codEmpresa + "' "
//				+ "   and EXISTS "
//				+ "(SELECT 1 FROM svm_promociones_art_det b, svm_promociones_art_cab c "
//				+ "  WHERE (b.COD_ARTICULO = articulos_precios.cod_articulo or "
//				+ "			c.COD_ARTICULO = articulos_precios.cod_articulo) "
//				+ "    and  c.COD_LISTA_PRECIO = articulos_precios.cod_lista_precio "
//				+ "    and b.COD_EMPRESA   = c.COD_EMPRESA "
//				+ "    and b.COD_VENDEDOR  = c.COD_VENDEDOR "
//				+ "    and b.IND_TIPO	   = c.IND_TIPO"
//				+ "    and b.NRO_PROMOCION = c.NRO_PROMOCION "
//				+ ")";
//
//
//		try {
//			Aplicacion.bdatos.execSQL(sql);
//			mensajebd("Operacion exitosa", "Se actualizo el campo");
//		} catch (Exception e) {
//			mensajebd("Error", "No se pudo actualizar el campo\n" + e.getMessage());
//		}
//
//
//	}
//
//}

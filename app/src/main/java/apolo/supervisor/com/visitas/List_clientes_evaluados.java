//package apolo.supervisor.com.visitas;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.database.Cursor;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.SimpleAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import apolo.supervisores.com.R;
//
//public class List_clientes_evaluados extends Activity {
//
//	final Context context = this;
//
//	private TextView fec_desde;
//	private TextView fec_hasta;
//
//	static final int DATE_DIALOG_ID = 1;
//	private int mYear;
//	private int mMonth;
//	private int mDay;
//	private int fecha;
//
//
//	String resp_WS;
//	ProgressDialog pbarDialog;
//	String cab_cliente = "", det_cliente = "", cod_supervisor = "", fecha_actual = "";
//
//
//	private RadioButton radio0;
//	private RadioButton radio1;
//	private RadioButton radio2;
//
//
//	static String _filter;
//
//
//	List<HashMap<String, String>> alist;
//	ListView gridView;
//	static adapter_clientes_evaluados sd;
//	private int save = -1;
//
//	Cursor cursor_datos;
//
//
//
//	String[] cod_vendedor, desc_vendedor, cod_cliente, cod_subcliente, desc_subcliente, fecha_visita, estado, id_cab;
//
//
//
//	static Adapter_lista_preguntas sd2;
//	Dialog dialogPreguntas = null;
//	ListView list_view_preguntas;
//	List<HashMap<String, String>> alist2 ;
//
//	String[] respuesta;
//	String[] estado_consulta_pregunta;
//
//
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//		if (Aplicacion._tip_tela == 5) {
//			setContentView(R.dialogo_bonificacion_combo.list_clientes_evaluados);
//		}
//
//
//		String _bloquear = "N";
//		String _msg = "";
//		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
//		Date d = null;
//		Date d1 = null;
//		Calendar cal = Calendar.getInstance();
//		if (Aplicacion._activo.equals("N")) {
//			_bloquear = "S";
//			_msg = "Sincronize primero,o debe pedir una clave en la Empresa.";
//		}
//		if (Aplicacion._fecha_ult_actualizacion.toString().equals("")) {
//			_bloquear = "S";
//			_msg = "Sincronize primero,o debe pedir una clave en la Empresa.";
//		} else {
//			try {
//				d = dfDate
//						.parse(Aplicacion._fecha_ult_actualizacion.toString());
//				d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
//																// 15/10/2012
//			} catch (java.text.ParseException e) {
//
//				e.printStackTrace();
//			}
//			int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
//			System.out.println(String.valueOf(diffInDays));
//			if (diffInDays != 0) {
//
//				_bloquear = "S";
//				_msg = "Sincronize primero,o debe pedir una clave en la Empresa.";
//			}
//
//		}
//
//		if (_bloquear.equals("S")) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(context);
//			builder.setTitle("Atenci�n");
//			builder.setMessage(_msg)
//
//					.setCancelable(false)
//					.setPositiveButton("OK",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int id) {
//									finish();
//									return;
//								}
//							});
//			AlertDialog alert = builder.create();
//			alert.show();
//		} else {
//
//
//			fec_desde = (EditText) findViewById(R.id.fec_desde);
//			fec_hasta = (EditText) findViewById(R.id.fec_hasta);
//
//			radio0 = (RadioButton) findViewById(R.id.radio0);
//			radio1 = (RadioButton) findViewById(R.id.radio1);
//			radio2 = (RadioButton) findViewById(R.id.radio2);
//
//
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			fec_desde.setText(sdf.format(new Date()));
//			fec_hasta.setText(sdf.format(new Date()));
//
//			fec_desde.setOnClickListener(new OnClickListener() {
//
//				@SuppressWarnings("deprecation")
//				public void onClick(View v) {
//
//					final Calendar c = Calendar.getInstance();
//					mYear = c.get(Calendar.YEAR);
//					mMonth = c.get(Calendar.MONTH);
//					mDay = c.get(Calendar.DAY_OF_MONTH);
//					showDialog(DATE_DIALOG_ID);
//					fecha = 0;
//					fecha_desde();
//				}
//			});
//
//			fec_hasta.setOnClickListener(new OnClickListener() {
//
//				@SuppressWarnings("deprecation")
//				public void onClick(View v) {
//
//					final Calendar c = Calendar.getInstance();
//					mYear = c.get(Calendar.YEAR);
//					mMonth = c.get(Calendar.MONTH);
//					mDay = c.get(Calendar.DAY_OF_MONTH);
//					showDialog(DATE_DIALOG_ID);
//					fecha = 1;
//					fecha_hasta();
//				}
//			});
//
//
//		}
//
//	}
//
//
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//
//		case DATE_DIALOG_ID:
//			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
//					mDay);
//		}
//		return null;
//	}
//
//	protected void onPrepareDialog(int id, Dialog dialog) {
//		switch (id) {
//
//		case DATE_DIALOG_ID:
//			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
//			break;
//		}
//	}
//
//	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//		@Override
//		public void onDateSet(DatePicker view, int year, int monthOfYear,
//				int dayOfMonth) {
//
//			mYear = year;
//			mMonth = monthOfYear;
//			mDay = dayOfMonth;
//			if (fecha == 0) {
//				fecha_desde();
//			} else {
//				fecha_hasta();
//			}
//
//		}
//
//	};
//
//
//
//	private void fecha_desde() {
//
//		final String mes;
//		mMonth = mMonth + 1;
//		if (mMonth <= 9) {
//			mes = "0" + new StringBuilder().append(mMonth);
//		} else {
//			mes = "" + new StringBuilder().append(mMonth);
//		}
//		final String dia;
//		if (mDay <= 9) {
//			dia = "0" + new StringBuilder().append(mDay);
//		} else {
//			dia = "" + new StringBuilder().append(mDay);
//		}
//
//		fec_desde.setText(new StringBuilder()
//
//		.append(dia).append("/").append(mes).append("/").append(mYear)
//				.append(""));
//
//		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
//		Date d = null;
//		Date d1 = null;
//		Calendar cal = Calendar.getInstance();
//
//		try {
//			d = dfDate.parse(fec_desde.getText().toString());
//			d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
//															// 15/10/2012
//		} catch (java.text.ParseException e) {
//
//			e.printStackTrace();
//		}
//
//		int diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
//		System.out.println(String.valueOf(diffInDays));
//
//	}
//
//	private void fecha_hasta() {
//
//		final String mes;
//		mMonth = mMonth + 1;
//		if (mMonth <= 9) {
//			mes = "0" + new StringBuilder().append(mMonth);
//		} else {
//			mes = "" + new StringBuilder().append(mMonth);
//		}
//		final String dia;
//		if (mDay <= 9) {
//			dia = "0" + new StringBuilder().append(mDay);
//		} else {
//			dia = "" + new StringBuilder().append(mDay);
//		}
//
//		fec_hasta.setText(new StringBuilder()
//
//		.append(dia).append("/").append(mes).append("/").append(mYear)
//				.append(""));
//
//		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
//		Date d = null;
//		Date d1 = null;
//		Calendar cal = Calendar.getInstance();
//
//		try {
//			d = dfDate.parse(fec_desde.getText().toString());
//			d1 = dfDate.parse(fec_hasta.getText().toString());// Returns
//																// 15/10/2012
//		} catch (java.text.ParseException e) {
//
//			e.printStackTrace();
//		}
//
//		int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
//		System.out.println(String.valueOf(diffInDays));
//		if (diffInDays < 0) {
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(context);
//			builder.setTitle("Atenci�n");
//			builder.setMessage("Fecha Invalida")
//
//					.setCancelable(false)
//					.setPositiveButton("OK",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int id) {
//									SimpleDateFormat sdf = new SimpleDateFormat(
//											"dd/MM/yyyy");
//									fec_hasta.setText(sdf.format(new Date()));
//									return;
//								}
//							});
//			AlertDialog alert = builder.create();
//			alert.show();
//		}
//
//		try {
//			d = dfDate.parse(fec_hasta.getText().toString());
//			d1 = dfDate.parse(dfDate.format(cal.getTime()));// Returns
//															// 15/10/2012
//		} catch (java.text.ParseException e) {
//
//			e.printStackTrace();
//		}
//
//		diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
//		System.out.println(String.valueOf(diffInDays));
//
//	}
//
//
//
//	public String filtro_selecionado() {
//		String radioButtonSelected = null;
//		if (radio0.isChecked() == true)
//			radioButtonSelected = "Pendientes";
//
//		else if (radio1.isChecked() == true)
//
//			radioButtonSelected = "Enviados";
//
//		else if (radio2.isChecked() == true)
//			radioButtonSelected = "Todos";
//
//		return radioButtonSelected;
//
//	}
//
//
//	public void busca_clientes(View arg0) {
//		save = -1;
//		filtraEvaluaciones();
//	}
//
//
//
//	private void filtraEvaluaciones() {
//
//		String desde = null;
//		String hasta = null;
//		_filter = filtro_selecionado();
//
//		desde = Aplicacion.utilidades.convertirFechatoSQLFormat(fec_desde
//				.getText().toString());
//		hasta = Aplicacion.utilidades.convertirFechatoSQLFormat(fec_hasta
//				.getText().toString());
//
//		alist = new ArrayList<HashMap<String, String>>();
//		String _where = null;
//		if (_filter.equals("Todos")) {
//			_where = "(ESTADO = 'P' OR ESTADO = 'E')";
//		} else if (_filter.equals("Pendientes")) {
//			_where = "ESTADO=" + "'" + "P" + "'";
//		} else if (_filter.equals("Enviados")) {
//			_where = "ESTADO=" + "'" + "E" + "'";
//		}
//
//		_where += " and (date(substr(FECHA_VISITA,7) || '-' ||"
//			+ "				substr(FECHA_VISITA,4,2) || '-' ||"
//			+ "				substr(FECHA_VISITA,1,2))) 			between date('" + desde + "') and date('" + hasta + "')";
//
////		_where += " and FECHA_VISITA BETWEEN '" + desde + "' and '" + hasta + "'";
//
//		alist = new ArrayList<HashMap<String, String>>();
//
//		try {
//			String[] select = new String[] {"COD_VENDEDOR", "DESC_VENDEDOR", "COD_CLIENTE", "COD_SUBCLIENTE", "DESC_SUBCLIENTE",
//											"FECHA_VISITA", "ESTADO"       , "id"};
//
//			cursor_datos = Aplicacion.bdatos.query("svm_analisis_cab",
//					select, _where, null, null, null, "COD_VENDEDOR, COD_CLIENTE");
//
//			int nreg = cursor_datos.getCount();
//
//			cursor_datos.moveToFirst();
//
//			cod_vendedor = new String[nreg];
//			desc_vendedor = new String[nreg];
//			cod_cliente = new String[nreg];
//			cod_subcliente = new String[nreg];
//			desc_subcliente = new String[nreg];
//			fecha_visita = new String[nreg];
//			estado = new String[nreg];
//			id_cab = new String[nreg];
//
//
//
//			for (int i = 0; i < nreg; ++i) {
//
//				HashMap<String, String> map = new HashMap<String, String>();
//
//				try {
//					crearListView(map);
//					alist.add(map);
//				} catch (Exception e) {
//					String err = e.getMessage();
//					err = "" + err;
//				}
//
//				cod_vendedor[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("COD_VENDEDOR")) ;
//
//				desc_vendedor[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("DESC_VENDEDOR"));
//
//				cod_cliente[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("COD_CLIENTE"));
//
//				cod_subcliente[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("COD_SUBCLIENTE"));
//
//				desc_subcliente[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("DESC_SUBCLIENTE"));
//
//				fecha_visita[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("FECHA_VISITA"));
//
//				estado[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("ESTADO"));
//
//				id_cab[i] = cursor_datos.getString(cursor_datos
//						.getColumnIndex("id"));
//
//
//				cursor_datos.moveToNext();
//
//
//
//			}
//
//			gridView = (ListView) findViewById(R.id.lvdpedidos);
//
//			sd = new adapter_clientes_evaluados(List_clientes_evaluados.this, alist,
//					R.dialogo_bonificacion_combo.list_text_clientes_evaluados,
//					new String[] { "COD_CLIENTE", "DESC_SUBCLIENTE", "COD_VENDEDOR", "DESC_VENDEDOR", "FECHA_VISITA", "ESTADO" },
//
//					new int[] { R.id.td1, R.id.td2, R.id.td3,
//								R.id.td4, R.id.td5, R.id.td6 });
//
//			gridView.setAdapter(sd);
//
//			gridView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View v,
//						int position, long id) {
//
//
//					save = position;
//
//					gridView.invalidateViews();
//
//
//
//				}
//			});
//			Aplicacion._nro_pedido = 0;
//			return;
//		} catch (Exception e) {
//			mensajebd("Error", "D " + e.getMessage());
//		}
//
//
//	}
//
//
//
//	private void crearListView(HashMap<String, String> map) {
//
//		map.put("COD_CLIENTE", cursor_datos.getString(cursor_datos
//				.getColumnIndex("COD_CLIENTE")) + " - " +
//				cursor_datos.getString(cursor_datos
//						.getColumnIndex("COD_SUBCLIENTE")));
//
//		map.put("DESC_SUBCLIENTE", cursor_datos.getString(cursor_datos
//				.getColumnIndex("DESC_SUBCLIENTE")));
//
//		map.put("COD_VENDEDOR", cursor_datos.getString(cursor_datos
//				.getColumnIndex("COD_VENDEDOR")));
//
//		map.put("DESC_VENDEDOR", cursor_datos.getString(cursor_datos
//				.getColumnIndex("DESC_VENDEDOR")));
//
//		map.put("FECHA_VISITA", cursor_datos.getString(cursor_datos
//				.getColumnIndex("FECHA_VISITA")));
//
//		map.put("ESTADO", cursor_datos.getString(cursor_datos
//				.getColumnIndex("ESTADO")));
//
//
//	}
//
//
//
//	public class adapter_clientes_evaluados extends SimpleAdapter {
//		private int[] colors = new int[] { Color.parseColor("#696969"),
//				Color.parseColor("#808080") };
//
//		public adapter_clientes_evaluados(Context context,
//				List<HashMap<String, String>> items, int resource,
//				String[] from, int[] to) {
//			super(context, items, resource, from, to);
//		}
//
//		public class ViewHolder {
//			TextView codCliente;
//			TextView descCliente;
//			TextView codVendedor;
//			TextView descVendedor;
//			TextView fecha;
//			TextView estado;
//			Button btnEnviar;
//
//
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			View view = super.getView(position, convertView, parent);
//			int colorPos = position % colors.length;
//
//			view.setBackgroundColor(colors[colorPos]);
//			final ViewHolder holder = new ViewHolder();
//
//			holder.codCliente = (TextView) view.findViewById(R.id.td1);
//			holder.descCliente = (TextView) view.findViewById(R.id.td2);
//			holder.codVendedor = (TextView) view.findViewById(R.id.td3);
//			holder.descVendedor = (TextView) view.findViewById(R.id.td4);
//			holder.fecha = (TextView) view.findViewById(R.id.td5);
//			holder.estado = (TextView) view.findViewById(R.id.td6);
//			holder.btnEnviar = (Button) view.findViewById(R.id.btnEnviar);
//
//
//			if (colorPos == 1) {
//
//				holder.codCliente.setBackgroundResource(R.drawable.border_textview);
//				holder.descCliente.setBackgroundResource(R.drawable.border_textview);
//				holder.codVendedor.setBackgroundResource(R.drawable.border_textview);
//				holder.descVendedor.setBackgroundResource(R.drawable.border_textview);
//				holder.fecha.setBackgroundResource(R.drawable.border_textview);
//				holder.estado.setBackgroundResource(R.drawable.border_textview);
//
//
//			} else {
//				holder.codCliente.setBackgroundResource(R.drawable.border_textview2);
//				holder.descCliente.setBackgroundResource(R.drawable.border_textview2);
//				holder.codVendedor.setBackgroundResource(R.drawable.border_textview2);
//				holder.descVendedor.setBackgroundResource(R.drawable.border_textview2);
//				holder.fecha.setBackgroundResource(R.drawable.border_textview2);
//				holder.estado.setBackgroundResource(R.drawable.border_textview2);
//
//			}
//
//			if (estado[position].equals("P")) {
//				holder.btnEnviar.setEnabled(true);
//
//
//				holder.btnEnviar.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
//						Calendar cal = Calendar.getInstance();
//						String fecha = dfDate.format(cal.getTime());
//						if (!fecha.equals(fecha_visita[position])) {
//							Toast.makeText(List_clientes_evaluados.this, "Solo se puede enviar seguimiento del dia actual", Toast.LENGTH_SHORT).show();
//							return;
//						}
//
//
//						save = position;
//						if (estado[position].equals("P")) {
//							//genera cabecera cliente
//							String select = "Select COD_CLIENTE, COD_SUBCLIENTE, COD_SUPERVISOR, COD_VENDEDOR, FECHA_VISITA, HORA_LLEGADA, HORA_SALIDA "
//										+ " FROM svm_analisis_cab "
//										+ " WHERE id = '" + id_cab[position] + "'";
//
//							cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//							int nreg = cursor_datos.getCount();
//							cursor_datos.moveToFirst();
//
//							String horae, horas;
//							horae = cursor_datos.getString(cursor_datos.getColumnIndex("HORA_LLEGADA"));
//							horas = cursor_datos.getString(cursor_datos.getColumnIndex("HORA_SALIDA"));
//
//							if (horae.equals("") || horas.equals("")) {
//								Toast.makeText(List_clientes_evaluados.this, "Debe marcar la entrada y salida del cliente para enviar", Toast.LENGTH_SHORT).show();
//								return;
//							}
//
//							cab_cliente = "";
//
//							for (int i = 0; i < nreg; i++) {
//
//								String fecha_movimiento = "to_date('" + cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + "','dd/MM/yyyy')";
//
//								String fecha_ini = "to_date('" + cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + " "
//										   		 +  cursor_datos.getString(cursor_datos.getColumnIndex("HORA_LLEGADA")) + "','dd/MM/yyyy hh24:mi:ss')";
//								String fecha_fin = "to_date('" +  cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + " "
//								   		 		 +  cursor_datos.getString(cursor_datos.getColumnIndex("HORA_SALIDA")) + "','dd/MM/yyyy hh24:mi:ss')";
//
//								cab_cliente = "1," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_CLIENTE")) +
//											    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUBCLIENTE")) +
//											    "'," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR")) +
//											    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR")) +
//											    "'," + fecha_movimiento + "," +  fecha_ini + "," + fecha_fin + ";";
//
//								cod_supervisor = cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR"));
//								fecha_actual = cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA"));
//
//								cursor_datos.moveToNext();
//
//							}
//
//
//
//							select = " Select a.COD_CLIENTE, a.COD_SUBCLIENTE, a.COD_SUPERVISOR, a.COD_VENDEDOR, b.COD_MOTIVO, b.RESPUESTA, c.ESTADO, c.COD_GRUPO "
//									+ " from svm_analisis_cab a, "
//									+ "      svm_analisis_det b,"
//									+ "		 svm_motivo_analisis_cliente c  "
//									+ " WHERE a.id = b.ID_CAB "
//									+ "   and a.id = '" + id_cab[position] + "' "
//									+ "   and a.COD_VENDEDOR   = c.COD_VENDEDOR "
//									+ "	  and b.COD_MOTIVO 	   = c.COD_MOTIVO "  ;
//
//							try {
//								cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//							} catch (Exception e) {
//								String err = e.getMessage();
//								err = err + "";
//							}
//
//
//							cursor_datos.moveToFirst();
//							nreg = cursor_datos.getCount();
//
//							for (int i = 0; i < nreg; i++) {
//
//								String estado = cursor_datos.getString(cursor_datos.getColumnIndex("ESTADO"));
//								String resp = "", punt = "";
//
//								if (estado.equals("M")) {
//									resp = "M";
//									punt = cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA"));
//								} else {
//									resp = cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA"));
//								}
//
//								det_cliente += "1," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_CLIENTE")) +
//											    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUBCLIENTE")) +
//											    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR")) +
//											    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR")) +
//											    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_GRUPO")) +
//											    "','" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_MOTIVO")) +
//											    "','" + resp + "','" + punt + "';";
//
//								cursor_datos.moveToNext();
//
//							}
//
////							Toast.makeText(ReporteGeneralVendedor.this, det_vendedor, Toast.LENGTH_LONG).show();
//							new performBackgroundTaskEnviarReporteCliente().execute();
//
//
//						}
//
//					}
//				});
//			} else {
//				holder.btnEnviar.setEnabled(false);
//			}
//
//
//
//
//			if (position == save) {
//				view.setBackgroundColor(Color.BLUE);
//			}
//
//			return view;
//		}
//	}
//
//
//
//	private class performBackgroundTaskEnviarReporteCliente extends AsyncTask<Void, Void, Void> {
//
//		@Override
//		protected void onPreExecute() {
//
//			pbarDialog = ProgressDialog.show(List_clientes_evaluados.this, "Un momento...",
//					"Enviando Reporte...", true);
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//
//			Aplicacion.cWS.setMethodName("ProcesaSeguimientoAct");
//
//	   	 	resp_WS = Aplicacion.cWS.procesaSeguimientoPDV(cab_cliente, det_cliente, cod_supervisor, fecha_actual);
//			//resp_WS = "01*Guardado con exito";
//
//	   	 	return null;
//
//		}
//
//
//		@Override
//		protected void onPostExecute(Void unused) {
//
//			pbarDialog.dismiss();
//
//			if (resp_WS.indexOf("Unable to resolve host") > -1) {
//				Toast.makeText(List_clientes_evaluados.this, "Verifique su conexion a internet y vuelva a intentarlo", Toast.LENGTH_SHORT).show();
//				return;
//			}
//
//
//			if(resp_WS.indexOf("01*") >= 0) {
//
//				Aplicacion.bdatos.beginTransaction();
//
//				String update = "update svm_analisis_cab set ESTADO = 'E' "
//							+ " WHERE id = '" + id_cab[save] + "'"
//							+ "   and ESTADO = 'P'";
//
//				Aplicacion.bdatos.execSQL(update);
//
//
//
//
//
//				Aplicacion.bdatos.setTransactionSuccessful();
//				Aplicacion.bdatos.endTransaction();
//
//			}
//
//			Toast.makeText(List_clientes_evaluados.this, resp_WS, Toast.LENGTH_LONG).show();
//			busca_clientes(null);
//
//
//
//		}
//
//	}
//
//
//
//	public void mensajebd(String Titulo, String Texto) {
//		AlertDialog.Builder mensaje = new AlertDialog.Builder(
//				List_clientes_evaluados.this);
//		mensaje.setTitle(Titulo);
//		mensaje.setMessage(Texto);
//		mensaje.setNeutralButton("OK", null);
//		mensaje.show();
//	}
//
//
//
//	public void cancela_evaluacion(View arg0) {
//		if( save > -1 && cod_cliente.length > 0 && estado[save].equals("P")) {
//
//			 String delete = "Update svm_analisis_cab set ESTADO = 'A' where id = '" + id_cab[save] + "'";
//
//			 Aplicacion.bdatos.beginTransaction();
//
//			 Aplicacion.bdatos.execSQL(delete);
//			 Aplicacion.bdatos.setTransactionSuccessful();
//			 Aplicacion.bdatos.endTransaction();
//
//			 filtraEvaluaciones();
//
//
//
//		}
//	}
//
//
//	public void modifica_evaluacion(View arg0) {
//
//		String fecha = Aplicacion.utilidades.obtieneFechaActual();
//
//		if (cod_cliente[save].equals("0") ) {
//
//			if (fecha_visita[save].equals(fecha) && estado[save].equals("P")) {
//				abre_marcar_reunion();
//			}
//
//
//
//		} else {
//
//			if(  save > -1 && cod_cliente.length > 0  && fecha_visita[save].equals(fecha) && estado[save].equals("P")) {
//
//				 Aplicacion._id_cab_modifica_analisis = id_cab[save];
//				 startActivity(new Intent(List_clientes_evaluados.this, VisitaGestor.class));
//
//				 finish();
//
//			}
//
//		}
//
//	}
//
//
//	//Dialog Marcacion Reunion
//	Dialog dialogMarcarPresenciaCliente = null;
//
//	protected void abre_marcar_reunion() {
//
//
//		try {
//			dialogMarcarPresenciaCliente.dismiss();
//		} catch(Exception e) {
//
//		}
//
//
//		dialogMarcarPresenciaCliente = new Dialog(List_clientes_evaluados.this);
//		dialogMarcarPresenciaCliente.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//
//		dialogMarcarPresenciaCliente.setContentView(R.dialogo_bonificacion_combo.marcacion_reunion_enviar);
//
//
//
//		Button btnVolver = (Button) dialogMarcarPresenciaCliente.findViewById(R.id.btn_volver);
//		btnVolver.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				dialogMarcarPresenciaCliente.dismiss();
//			}
//		});
//
//
//		final CheckBox chkEntrada;
//		final CheckBox chkSalida;
//		chkEntrada = (CheckBox) dialogMarcarPresenciaCliente.findViewById(R.id.chkEntrada);
//		chkSalida = (CheckBox) dialogMarcarPresenciaCliente.findViewById(R.id.chkSalida);
//
//
//
//	    String select = " select COD_VENDEDOR, COD_CLIENTE, COD_SUBCLIENTE, HORA_LLEGADA, HORA_SALIDA, FECHA_VISITA "
//				+ "  from svm_analisis_cab "
//				+ "  where id = '" + id_cab[save] + "'"  ;
//
//	    Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//	    cursor.moveToFirst();
//
//	    int can_reg = cursor.getCount();
//
//
//	    if (can_reg == 0) {
//	    	chkSalida.setChecked(false);
//	    	chkEntrada.setChecked(false);
//	    	chkSalida.setEnabled(false);
//	    	chkEntrada.setEnabled(true);
//	    } else {
//	    	chkEntrada.setText(cursor.getString(cursor.getColumnIndex("FECHA_VISITA")) + " " + cursor.getString(cursor.getColumnIndex("HORA_LLEGADA")));
//	    	chkEntrada.setEnabled(false);
//	    	chkEntrada.setChecked(true);
//	    	chkSalida.setText(cursor.getString(cursor.getColumnIndex("FECHA_VISITA")) + " " + cursor.getString(cursor.getColumnIndex("HORA_SALIDA")));
//			if (chkSalida.getText().toString().length() < 13) {
//				chkSalida.setEnabled(true);
//				chkSalida.setChecked(false);
//			} else {
//				chkSalida.setEnabled(false);
//				chkSalida.setChecked(true);
//			}
//
//	    }
//
//
//	    Button btnEnviar, btnCancelar;
//	    btnEnviar = (Button) dialogMarcarPresenciaCliente.findViewById(R.id.btnEnviar);
//	    btnEnviar.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
//				if (chkEntrada.isChecked() && chkSalida.isChecked()) {
//
//					String select = "Select id, COD_CLIENTE, COD_SUBCLIENTE, COD_SUPERVISOR, COD_VENDEDOR, FECHA_VISITA, HORA_LLEGADA, HORA_SALIDA "
//							+ " FROM svm_analisis_cab "
//							+ " WHERE id = '" + id_cab[save] + "'";
//
//					cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//					int nreg = cursor_datos.getCount();
//					cursor_datos.moveToFirst();
//
//					if (nreg == 0) {
//						Toast.makeText(List_clientes_evaluados.this, "No se encontro ningun registro", Toast.LENGTH_SHORT).show();
//						return;
//					}
//
//
//
//					String fecha_movimiento = "to_date('" + cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + "','dd/MM/yyyy')";
//
//					String fecha_ini = "to_date('" + cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + " "
//							   		 +  cursor_datos.getString(cursor_datos.getColumnIndex("HORA_LLEGADA")) + "','dd/MM/yyyy hh24:mi:ss')";
//					String fecha_fin = "to_date('" +  cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA")) + " "
//					   		 		 +  cursor_datos.getString(cursor_datos.getColumnIndex("HORA_SALIDA")) + "','dd/MM/yyyy hh24:mi:ss')";
//
//					cab_cliente += "1," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_CLIENTE")) +
//								    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUBCLIENTE")) +
//								    "'," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR")) +
//								    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR")) +
//								    "'," + fecha_movimiento + "," +  fecha_ini + "," + fecha_fin + ";";
//
//					cod_supervisor = cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR"));
//					fecha_actual = cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA"));
//
//
//
//
//
//
//					select = " Select a.COD_CLIENTE, a.COD_SUBCLIENTE, a.COD_SUPERVISOR, a.COD_VENDEDOR, b.COD_MOTIVO, b.RESPUESTA, c.ESTADO, c.COD_GRUPO "
//							+ " from svm_analisis_cab a, "
//							+ "      svm_analisis_det b,"
//							+ "		 svm_motivo_analisis_cliente c  "
//							+ " WHERE a.id = b.ID_CAB "
//							+ "   and a.id = '" + id_cab[save] + "' "
//							+ "   and a.COD_VENDEDOR   = c.COD_VENDEDOR "
//							+ "	  and b.COD_MOTIVO 	   = c.COD_MOTIVO "  ;
//
//					try {
//						cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//					} catch (Exception e) {
//						String err = e.getMessage();
//						err = err + "";
//					}
//
//
//					cursor_datos.moveToFirst();
//					nreg = cursor_datos.getCount();
//
//					for (int i = 0; i < nreg; i++) {
//
//						String estado = cursor_datos.getString(cursor_datos.getColumnIndex("ESTADO"));
//						String resp = "", punt = "";
//
//						if (estado.equals("M")) {
//							resp = "M";
//							punt = cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA"));
//						} else {
//							resp = cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA"));
//						}
//
//						det_cliente += "1," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_CLIENTE")) +
//									    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUBCLIENTE")) +
//									    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUPERVISOR")) +
//									    "," + cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR")) +
//									    ",'" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_GRUPO")) +
//									    "','" + cursor_datos.getString(cursor_datos.getColumnIndex("COD_MOTIVO")) +
//									    "','" + resp + "','" + punt + "';";
//
//						cursor_datos.moveToNext();
//
//					}
//
////					Toast.makeText(ReporteGeneralVendedor.this, det_vendedor, Toast.LENGTH_LONG).show();
//					new performBackgroundTaskEnviarReporteCliente().execute();
//					dialogMarcarPresenciaCliente.dismiss();
//
//				} else {
//					Toast.makeText(List_clientes_evaluados.this, "Debe marcar la entrada y salida de este cliente", Toast.LENGTH_SHORT).show();
//				}
//
//
//			}
//		});
//
//
//	    btnCancelar = (Button) dialogMarcarPresenciaCliente.findViewById(R.id.btnCancelar);
//	    btnCancelar.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				dialogMarcarPresenciaCliente.dismiss();
//			}
//		});
//
//
//
//	    chkEntrada.setEnabled(false);
//
//
//
//	 	chkSalida.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				boolean isChecked = ((CheckBox)v).isChecked();
//
//
//				if (isChecked) {
//
//					String hora_salida = Aplicacion.utilidades.obtieneHoraActual();
//
//					String fecha_salida = Aplicacion.utilidades.obtieneFechaActual() + " " + hora_salida;
//
//						((CheckBox)v).setText(fecha_salida);
//						chkSalida.setEnabled(true);
//
//
//
//						//INSERTA CABECERA
//					ContentValues values = new ContentValues();
//					values.put("HORA_SALIDA", hora_salida);
//
//					Aplicacion.bdatos.update("svm_analisis_cab", values, " id = '" + id_cab[save] + "'", null);
//
//
//
//					chkEntrada.setEnabled(false);
//				} else {
//
//
//
//					String update = " update svm_analisis_cab set HORA_SALIDA = '' "
//									+ " where id = '" + id_cab[save] + "'" ;
//
//
//					Aplicacion.bdatos.execSQL(update);
//
//
//
//					((CheckBox)v).setText("");
//
//
//
//				}
//
//
//
//			}
// 		});
////
//
//		dialogMarcarPresenciaCliente.setCanceledOnTouchOutside(false);
//		dialogMarcarPresenciaCliente.show();
//
//
//
//
//	}
//
//
//
//
//
//
//	public void consulta_evaluacion(View arg0) {
//		if(( save > -1 && cod_cliente.length > 0) ) {
//
//			try {
//				dialogPreguntas.dismiss();
//			} catch (Exception e) {
//
//			}
//
//			dialogPreguntas = new Dialog(context);
//
//			dialogPreguntas.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//			if (Aplicacion._tip_tela == 5) {
//				dialogPreguntas
//						.setContentView(R.dialogo_bonificacion_combo.list_consulta_evaluacion_cliente);
//			}
//
//			list_view_preguntas = (ListView) dialogPreguntas
//					.findViewById(R.id.lvdet_analisis_zona);
//
//			trae_lista_preguntas();
//
//			dialogPreguntas.show();
//
//		}
//	}
//
//
//
//	private void trae_lista_preguntas(){
//
//		alist2 = new ArrayList<HashMap<String, String>>();
//
//		String select = "Select COD_SUPERVISOR, DESC_SUPERVISOR, COD_VENDEDOR, DESC_VENDEDOR,"
//						    + " DESC_ZONA 	  , HORA_LLEGADA   , HORA_SALIDA , FECHA_VISITA ,"
//						    + " COD_CLIENTE   , COD_SUBCLIENTE , DESC_SUBCLIENTE,  ESTADO  "
//					+ " FROM svm_analisis_cab"
//					+ " WHERE id = '" + id_cab[save] + "' ";
//
//		String cod_vendedor = "";
//
//		cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//		cursor_datos.moveToFirst();
//		TextView tvSupervisor, tvGestor, tvZona, tvDia, tvHoraLlegada, tvHoraSalida, tvCliente;
//		CheckBox cbEntrada, cbSalida;
//		tvSupervisor = (TextView) dialogPreguntas.findViewById(R.id.tvDescSupervisor);
//		tvGestor = (TextView) dialogPreguntas.findViewById(R.id.tvNomVendedor);
//		tvZona = (TextView)dialogPreguntas.findViewById(R.id.tvDescVendedor);
//		tvDia = (TextView)dialogPreguntas.findViewById(R.id.tvDiaVisita);
//		tvHoraLlegada = (TextView)dialogPreguntas.findViewById(R.id.tvHoraLlegada);
//		tvHoraSalida = (TextView)dialogPreguntas.findViewById(R.id.tvHoraSalida);
//		tvCliente = (TextView)dialogPreguntas.findViewById(R.id.tvCliente);
//		cbEntrada = (CheckBox)dialogPreguntas.findViewById(R.id.cbEntrada);
//		cbSalida = (CheckBox)dialogPreguntas.findViewById(R.id.cbSalida);
//		String llegada, salida ;
//		llegada = cursor_datos.getString(cursor_datos.getColumnIndex("HORA_LLEGADA"));
//		salida = cursor_datos.getString(cursor_datos.getColumnIndex("HORA_SALIDA"));
//
//		cod_vendedor = cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR"));
//
//		tvSupervisor.setText("SUPERVISOR: " + cursor_datos.getString(cursor_datos.getColumnIndex("DESC_SUPERVISOR")));
//		tvGestor.setText("GV/PV: " + cursor_datos.getString(cursor_datos.getColumnIndex("COD_VENDEDOR")));
//		tvZona.setText("ZONA: " + cursor_datos.getString(cursor_datos.getColumnIndex("DESC_ZONA")));
//		tvDia.setText("DIA DE VISITA: " + Aplicacion.utilidades.getDayOfTheWeek(cursor_datos.getString(cursor_datos.getColumnIndex("FECHA_VISITA"))));
//		tvHoraLlegada.setText("HORA DE LLEGADA: " + llegada + "  ");
//		tvHoraSalida.setText("HORA DE SALIDA: " + salida + "  ");
//		tvCliente.setText(cursor_datos.getString(cursor_datos.getColumnIndex("COD_CLIENTE")) + "-"
//						+ cursor_datos.getString(cursor_datos.getColumnIndex("COD_SUBCLIENTE")) + " - "
//						+ cursor_datos.getString(cursor_datos.getColumnIndex("DESC_SUBCLIENTE")));
//
//		if (llegada.equals("")) {
//			cbEntrada.setChecked(false);
//		} else {
//			cbEntrada.setChecked(true);
//		}
//		if (salida.equals("")) {
//			cbSalida.setChecked(false);
//		} else {
//			cbSalida.setChecked(true);
//		}
//
//		Button volver = (Button)dialogPreguntas.findViewById(R.id.btn_volver);
//		volver.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				dialogPreguntas.dismiss();
//			}
//		});
//
//		select = " SELECT a.COD_MOTIVO, a.RESPUESTA, b.DESCRIPCION, b.NRO_ORDEN, ESTADO "
//					  + " FROM svm_analisis_det a,"
//					  	    + "svm_motivo_analisis_cliente b "
//		  	    	  + " WHERE a.COD_MOTIVO = b.COD_MOTIVO "
//		  	    	  + " and a.ID_CAB = '" + id_cab[save] + "' "
//  	    	  		  + " and b.COD_VENDEDOR = '" + cod_vendedor + "'"
//		  	    	  + " ORDER BY cast(b.NRO_ORDEN as double)";
//
//		cursor_datos = Aplicacion.bdatos.rawQuery(select, null);
//
//
//
//
//		cursor_datos.moveToFirst();
//
//
//		int nreg = cursor_datos.getCount();
//
//		respuesta = new String[nreg];
//		estado_consulta_pregunta = new String[nreg];
//
//
//		for (int i = 0; i < nreg; ++i) {
//
//			HashMap<String, String>map2=new HashMap<String, String>();
//
//
//			map2.put("PREGUNTA", cursor_datos.getString(cursor_datos.getColumnIndex("DESCRIPCION")));
//
//			map2.put("RESPUESTA", cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA")));
//
//			respuesta[i] = cursor_datos.getString(cursor_datos.getColumnIndex("RESPUESTA"));
//
//			estado_consulta_pregunta[i] = cursor_datos.getString(cursor_datos.getColumnIndex("ESTADO"));
//
//      		alist2.add(map2);
//
//
//			cursor_datos.moveToNext();
//
//		}
//
//
//
//		sd2 = new Adapter_lista_preguntas(List_clientes_evaluados.this, alist2,
//					R.dialogo_bonificacion_combo.list_text_preguntas, new String[] {
//							 "PREGUNTA", "RESPUESTA" },
//					new int[] {R.id.td1, R.id.td2  });
//
//		list_view_preguntas.setAdapter(sd2);
//
//
//
//
//	}
//
//
//
//
//	public class Adapter_lista_preguntas extends SimpleAdapter {
//		String _sqlupdate;
//		private int[] colors = new int[] { Color.parseColor("#696969"),
//				Color.parseColor("#808080") };
//
//		public Adapter_lista_preguntas(Context context,
//				List<HashMap<String, String>> items, int resource,
//				String[] from, int[] to) {
//			super(context, items, resource, from, to);
//		}
//
//		public class ViewHolder {
//
//			RadioButton rbSi;
//			RadioButton rbNo;
//			RadioButton rbB;
//			Spinner sp_puntuacion;
//
//		}
//
//
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			View view = super.getView(position, convertView, parent);
//			final int colorPos = position % colors.length;
//
//			final ViewHolder holder = new ViewHolder();
//
//			holder.rbSi = (RadioButton) view.findViewById(R.id.rbSi);
//			holder.rbNo = (RadioButton) view.findViewById(R.id.rbNo);
//			holder.rbB = (RadioButton) view.findViewById(R.id.rbB);
//			holder.sp_puntuacion = (Spinner) view.findViewById(R.id.spPuntuacion);
//
//			holder.sp_puntuacion.setEnabled(false);
//
//			if(estado_consulta_pregunta[position].equals("M")){
//
//				holder.rbB.setChecked(true);
//
//
//				List<String> list = new ArrayList<String>();
//
//				list.add(respuesta[position]);
//
//				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
//						List_clientes_evaluados.this,
//						android.R.dialogo_bonificacion_combo.simple_spinner_item, list);
//				dataAdapter
//						.setDropDownViewResource(android.R.dialogo_bonificacion_combo.simple_spinner_dropdown_item);
//
//				holder.sp_puntuacion.setAdapter(dataAdapter);
//
//				holder.sp_puntuacion.setSelection(dataAdapter.getPosition(respuesta[position]));
//
//			} else {
//
//				if (respuesta[position].equals("S")) {
//					holder.rbSi.setChecked(true);
//				} else {
//					holder.rbNo.setChecked(true);
//				}
//
//
//				List<String> list = new ArrayList<String>();
//				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
//						List_clientes_evaluados.this,
//						android.R.dialogo_bonificacion_combo.simple_spinner_item, list);
//				dataAdapter
//						.setDropDownViewResource(android.R.dialogo_bonificacion_combo.simple_spinner_dropdown_item);
//				holder.sp_puntuacion.setAdapter(dataAdapter);
//
//			}
//
//
//
//
//
//
//			view.setBackgroundColor(colors[colorPos]);
//
//			view.setTag(holder);
//
//			return view;
//		}
//	}
//
//
//
//}

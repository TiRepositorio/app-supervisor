//5067 lineas
//
//public class List_clientes extends Activity {
//
//    int can_cli_bloq = 0;
//
//
//    Integer i, cont;
//    ArrayAdapter<String> ad;
//    ResultSet rs = null;
//    ResultSet rsconta = null;
//    static EditText selection;
//
//    Dialog dialogDeudaCliente = null;
//    ListView list_view_deuda_cliente = null;
//    List<HashMap<String, String>> alist_deuda_cliente ;
//    String decimal_deuda_cliente = null;
//    static Adapter_lista_deuda_cliente sd3;
//
//    Dialog dialogDatosClienteCaducado = null;
//    Dialog dialogModificarCliente = null;
//
//    protected ListAdapter adapter;
//    private int save;
//    private int saveDeuda;
//    static RadioButton rb[] ;
//    static String _filter;
//    static String _tip_causal;
//    static int _tip_filter;
//
//    static RadioGroup rGroup;
//
//    String query;
//    int[] colors = new int[] {Color.parseColor("#696969"), Color.parseColor("#808080") };
//    static ListView employeeList;
//    static ListView employeeList2;
//
//    String clientes[] = null;
//
//    static adapter_cliente  sd;
//    List<HashMap<String, String>> alist ;
//    Cursor cursor_datos;
//    Cursor cursor_datos2;
//    ListView gridView;
//    final Context context = this;
//    private Spinner cli_Spinner;
//
//    private String date_to_string;
//
//    static String codigo[] = null;
//    static String codigo_subcli[] = null;
//    static String codigo_cli[] = null;
//    static String tip_condicion[] = null;
//    String tip_causal[]=null;
//    static String dias_inicial_def[] = null;
//    static String cod_condicion[] = null;
//    static String cod_sucursal[] = null;
//    static String descripcion[] = null;
//    static String mensaje[] = null;
//    static String ind_esp[] = null;
//    static String categ[] = null;
//    static String estado2[] = null;
//
//    static String tel1[] = null;
//    static String tel2[] = null;
//    static String direccion[] = null;
//    static String cercade[] = null;
//    static String latitud[] = null;
//    static String longitud[] = null;
//    static String ind_caducado[] = null;
//    static String tip_cliente[] = null;
//    static String ind_visita[] = null;
//    static String tiempo_min[] = null;
//    static String tiempo_max[] = null;
//    static String sol_targ[] = null;
//
//    double rango_distancia_cliente = 0.0;
//
//
//    ProgressDialog pbarDialog;
//
//    //	VARIABLES PARA EL WEB SERVICE
//    String vCliente = "";
//    String respuestaWS = "";
//
//
//
//
//
//    //variable para No positivacion
//    static String cod_motivo[] = null;
//    static String desc_motivo[] = null;
//    static String cierra_motivo[] = null;
//    private String _motivo;
//    private String _cierra_motivo;
//    private String _id = "";
//
//
//    String _nopositivado;
//
//
//
//
//
//    //	Dialog dialogPromocionesPerfil = null;
//    ListView list_view_promociones_cab = null;
//    List<HashMap<String, String>> alist_promociones_cab ;
//    static Adapter_lista_promociones_cab sd4;
//    int savePromocionesCab = 0;
//
//
//
//    //IND_PRESENCIAL
//    String _ind_presencial = "";
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//
//        setContentView(R.dialogo_bonificacion_combo.list_clientes5);
//
//
//        String _bloquear = "N";
//        String _msg ="";
//
//
//        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
//        java.util.Date d = null;
//        java.util.Date d1 = null;
//        Calendar cal = Calendar.getInstance();
//
//        if (Aplicacion._activo.equals("N")) {
//
//            _bloquear="S";
//            _msg="No esta permitido Ver";
//
//        }
//
//        //Busqueda inicial
////			String[] sel = selectColumnasCliVendedor();
//            Date date = new Date();
//
//            SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
//            date_to_string = formatDateJava.format(date);
//
//            String consulta = "SELECT id "
//                    + "  from CLI_VENDEDOR a "
//                    + "  where COD_VENDEDOR = '" + Aplicacion._cod_prom_vendedor + "'"
//                    + "    and ind_visita   = 'B'"
//                    + "    and FEC_VISITA   = '" + date_to_string + "'"
//                    + "    and COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"' "
//                    + "    and not exists (Select 1"
//                    + "					  from vt_pedidos_cab b"
//                    + "				      where a.COD_CLIENTE = b.COD_CLIENTE"
//                    + "  					and a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
//                    + "                     and a.COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                    + "						and b.FEC_ALTA = '" + Aplicacion.utilidades.obtieneFechaActual() + "'"
//                    + "						and b.ESTADO IN ('P','E') )"
//                    + "    and not exists (Select 1"
//                    + "					  from vt_marcacion_visita c"
//                    + "				      where a.COD_CLIENTE = c.COD_CLIENTE"
//                    + "  					and a.COD_SUBCLIENTE = c.COD_SUBCLIENTE"
//                    + "                     and a.COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                    + "						and c.FECHA = '" + Aplicacion.utilidades.obtieneFechaActual() + "'"
//                    + "						and c.ESTADO IN ('P','E') )";
//
//
//            cursor_datos = Aplicacion.bdatos.rawQuery(consulta, null);
//
//            can_cli_bloq = cursor_datos.getCount();
//
//            String select = "select RANGO from svm_vendedor_pedido_venta "
//                    + "       where COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"' ";
//            Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//            cursor.moveToFirst();
//            int nreg = cursor.getCount();
//            if (nreg > 0) {
//                rango_distancia_cliente = Double.parseDouble(cursor.getString(cursor.getColumnIndex("RANGO")));
//            }
//
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(selection.getWindowToken(), 0);
//            Aplicacion._code_cli="";
//            genera_tipo_filtro();
//
//            alist = new ArrayList<HashMap<String, String>>();
//            llama_sugestion(null);
//
//
//            Button btnNoPositivacion, btnEntradaSalida, btnSD;
//            btnNoPositivacion = (Button) findViewById(R.id.btnNoPositivacion);
//            btnNoPositivacion.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//
//
//                    if (Aplicacion._per_asistencia.equals("N")) {
//                        valida_ubicacion();
//                    } else {
//                        Toast.makeText(List_clientes.this, "Esta cartera no puede justificar la no venta", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//
//
//                }
//            });
//
//            btnEntradaSalida = (Button) findViewById(R.id.btnMarcarEntradaSalida);
//            btnEntradaSalida.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//
//
//                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(List_clientes.this);
//
//                    myAlertDialog.setMessage("¿Se encuentra en el cliente?");
//                    myAlertDialog.setPositiveButton("Si",
//                            new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                    actualizaLatLongCli();
//
//                                    _ind_presencial = "S";
//
//                                    if (!valida_ubicacion_simulada()) {
//                                        return;
//                                    }
//
//                                    if (latitud[save].equals("") || longitud[save].equals("")) {
//                                        //ABRIR EL MAPA
//                                        Aplicacion._ind_mapa_cliente = "7";
//                                        Aplicacion._code = codigo_cli[save] + " - " + codigo_subcli[save];
//                                        startActivity(new Intent(List_clientes.this, MapaCliente.class));
//                                        return;
//
//                                    } else {
//
//                                        if (Aplicacion._lati.equals("") || Aplicacion._longi.equals("")) {
//                                            Toast.makeText(List_clientes.this, "No se encuentra la ubicacion GPS del telefono", Toast.LENGTH_SHORT).show();
//                                            return;
//                                        }
//
//                                        double distancia_cliente = Aplicacion.utilidades.calculaDistanciaCoordenadas(
//                                                Double.parseDouble(Aplicacion._lati),
//                                                Double.parseDouble(latitud[save]),
//                                                Double.parseDouble(Aplicacion._longi),
//                                                Double.parseDouble(longitud[save])) * 1000;
//
//
//                                        if (distancia_cliente > rango_distancia_cliente) {
//                                            Toast.makeText(List_clientes.this, "No se encuentra en el cliente. Se encuentra a " + Math.round(distancia_cliente) + " m." , Toast.LENGTH_SHORT).show();
//                                            return;
//                                        } else {
//                                            marcarEntradaSalidaCliente();
//                                            return;
//                                        }
//
//                                    }
//
////
//
//                                }
//                            });
//
//
//                    if (!Aplicacion._per_presencial.equals("S")) {
//
//
//                        myAlertDialog.setNegativeButton("No",
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        _ind_presencial = "N";
//                                        marcarEntradaSalidaCliente();
//
//                                    }
//                                });
//
//
//                    }
//
//
//                    myAlertDialog.show();
//
//                }
//            });
//            btnEntradaSalida.setVisibility(View.GONE);
//
//            btnSD = (Button) findViewById(R.id.btnSD);
//            btnSD.setText("SD");
////	        btnSD.setVisibility(View.GONE);
//            btnSD.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
////					Toast.makeText(List_clientes.this, "PROXIMAMENTE...", Toast.LENGTH_LONG).show();
//                    if (alist.size()==0 || save < 0) {
//                        Toast.makeText(List_clientes.this, "Seleccione un cliente.", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    Aplicacion._code_cli = codigo_cli[save];
//                    Aplicacion._code_subcli = codigo_subcli[save];
//                    Aplicacion._nom_cliente = descripcion[save];
//                    startActivity(new Intent(List_clientes.this,
//                            pedidos_apolo2.class));
//                }
//            });
//
//        }
//    }
//
//
//
//    //Dialog Marcacion
//    Dialog dialogMarcacionesCliente = null;
//    //Dialog Marcacion
//    Dialog dialogMarcarPresenciaCliente = null;
//
//
//    ListView listaMarcaciones;
//    List<HashMap<String, String>> list_marcaciones ;
//    static Adapter_lista_marcaciones adaptadorMarcaciones;
//    int saveMarcacion = 0;
//
//
//    private void marcarEntradaSalidaCliente() {
//
//        /*VALIDAR EL GPS DEL CELULAR*/
//        if (!Aplicacion.lm2.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            //CONECTAR EL GPS
//            Intent callGPSSettingIntent = new Intent(
//                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(callGPSSettingIntent);
//            return;
//        }
//
//
//
//
//        try {
//            dialogMarcacionesCliente.dismiss();
//        } catch(Exception e) {
//
//        }
//
//
//        dialogMarcacionesCliente = new Dialog(context);
//        dialogMarcacionesCliente.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//
//        dialogMarcacionesCliente.setContentView(R.dialogo_bonificacion_combo.list_lista_marcaciones);
//
//        listaMarcaciones = (ListView) dialogMarcacionesCliente.findViewById(R.id.lvlista_marcaciones);
//
//        cargarMarcaciones();
//
//
//        TextView tvCliente, tvTiempoMin, tvTiempoMax;
//        tvCliente = (TextView) dialogMarcacionesCliente.findViewById(R.id.tvCliente);
//        tvTiempoMin = (TextView) dialogMarcacionesCliente.findViewById(R.id.tvTiempoMin);
//        tvTiempoMax = (TextView) dialogMarcacionesCliente.findViewById(R.id.tvTiempoMax);
//
//
//        tvCliente.setText(codigo[save] + " " + descripcion[save]);
//        tvTiempoMin.setText(tiempo_min[save] + " min.");
//        tvTiempoMax.setText(tiempo_max[save] + " min.");
//
//
//
//        Button btnEnviar, btnCancelar;
//        btnEnviar = (Button) dialogMarcacionesCliente.findViewById(R.id.btnEnviar);
//        btnEnviar.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                procesa_envia_marcaciones();
//
//
//            }
//        });
//
//        btnCancelar = (Button) dialogMarcacionesCliente.findViewById(R.id.btnCancelar);
//        btnCancelar.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                dialogMarcacionesCliente.dismiss();
//            }
//        });
//
//        ImageButton ibtnAgregar, ibtnEliminar;
//        ibtnAgregar = (ImageButton) dialogMarcacionesCliente.findViewById(R.id.ibtnAgregar);
//        ibtnEliminar = (ImageButton) dialogMarcacionesCliente.findViewById(R.id.ibtnEliminar);
//
//
//        ibtnAgregar.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                try {
//                    dialogMarcarPresenciaCliente.dismiss();
//                } catch(Exception e) {
//
//                }
//
//
//                dialogMarcarPresenciaCliente = new Dialog(context);
//                dialogMarcarPresenciaCliente.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//
//                dialogMarcarPresenciaCliente.setContentView(R.dialogo_bonificacion_combo.marcacion_cliente);
//
//
//
//                Button btnVolver = (Button) dialogMarcarPresenciaCliente.findViewById(R.id.btn_volver);
//                btnVolver.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View arg0) {
//                        dialogMarcarPresenciaCliente.dismiss();
//                    }
//                });
//
//
//                final CheckBox chkEntrada;
//                final CheckBox chkSalida;
//                chkEntrada = (CheckBox) dialogMarcarPresenciaCliente.findViewById(R.id.chkEntrada);
//                chkSalida = (CheckBox) dialogMarcarPresenciaCliente.findViewById(R.id.chkSalida);
//
//
//                TextView codCliente = (TextView) dialogMarcarPresenciaCliente.findViewById(R.id.tvCodCliente);
//                TextView descCliente = (TextView) dialogMarcarPresenciaCliente.findViewById(R.id.tvDescCliente);
//
//                String cod, desc;
//
//
//                String select = "Select a.COD_CLIENTE, a.COD_SUBCLIENTE, a.FECHA, a.COD_PROMOTOR, a.TIPO, a.ESTADO, a.LATITUD, a.LONGITUD, b.DESC_SUBCLIENTE  "
//                        + "  from vt_marcacion_ubicacion_venta a,"
//                        + "		  CLI_VENDEDOR b "
////			    			+ "  where a.FECHA like '%" + MenuCombinado.utilidadesPromotor.obtieneFechaActual() + "%' "
//                        + "  where a.COD_CLIENTE    = '" + codigo_cli[save] + "'"
//                        + "    and a.COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                        + "    and a.COD_CLIENTE    = b.COD_CLIENTE "
//                        + "    and a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
//                        + "    and a.COD_PROMOTOR   = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                        + "    and b.COD_VENDEDOR   = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                        + "    and ( a.TIPO = 'E' or a.TIPO = 'S' ) "
//                        + "  order by a.id desc " ;
//
//
//
//
//
//                Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//                cursor.moveToFirst();
//
//                int can_reg = 0;
//
//                if (cursor.getCount() > 0) {
//
//                    String ult_tip = cursor.getString(cursor.getColumnIndex("TIPO"));
//                    if (ult_tip.equals("E")) {
//
//                        can_reg = 1;
//
//                    }
//
//                } else {
//
//
//                    select = "Select b.COD_CLIENTE, b.COD_SUBCLIENTE, b.DESC_SUBCLIENTE  "
//                            + "  from CLI_VENDEDOR b "
//                            + "  where b.COD_CLIENTE    = '" + codigo_cli[save] + "'"
//                            + "    and b.COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                            + "    and b.COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'";
//
//
//                    cursor = Aplicacion.bdatos.rawQuery(select, null);
//                    cursor.moveToFirst();
//
//                }
//
//
//
//
//                cod = cursor.getString(cursor.getColumnIndex("COD_CLIENTE")) + " - " + cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"));
//                desc = cursor.getString(cursor.getColumnIndex("DESC_SUBCLIENTE"));
//
//
//
//
//
//                if (can_reg == 0) {
//                    chkSalida.setChecked(false);
//                    chkEntrada.setChecked(false);
//                    chkSalida.setEnabled(false);
//                    chkEntrada.setEnabled(true);
//                } else {
//                    String estado = cursor.getString(cursor.getColumnIndex("ESTADO"));
//                    if (estado.equals("E")) {
//                        chkEntrada.setEnabled(false);
//                    } else {
//                        chkEntrada.setEnabled(false);
//                    }
//
//                    chkEntrada.setText(cursor.getString(cursor.getColumnIndex("FECHA")));
//                    chkEntrada.setChecked(true);
//                    chkSalida.setEnabled(true);
//                    chkSalida.setChecked(false);
//                }
//
//
//                codCliente.setText(cod);
//                descCliente.setText(desc);
//
//
//
//
//
//                chkEntrada.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        boolean isChecked = ((CheckBox)v).isChecked();
//
//
//
//
//
//                        if (isChecked) {
//
//                            if (!valida_hora_automatica()) {
//                                ((CheckBox)v).setChecked(false);
//                                return;
//                            }
//
//                            if (_ind_presencial.equals("S")) {
//                                if (Aplicacion._lati.equals("") || Aplicacion._longi.equals("")) {
//                                    Toast.makeText(List_clientes.this, "No se encuentra la ubicacion GPS del telefono", Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//
//                                double distancia_cliente = Aplicacion.utilidades.calculaDistanciaCoordenadas(
//                                        Double.parseDouble(Aplicacion._lati),
//                                        Double.parseDouble(latitud[save]),
//                                        Double.parseDouble(Aplicacion._longi),
//                                        Double.parseDouble(longitud[save])) * 1000;
//
//
//                                if (distancia_cliente > rango_distancia_cliente) {
//                                    Toast.makeText(List_clientes.this, "No se encuentra en el cliente. Se encuentra a " + Math.round(distancia_cliente) + " m." , Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//                            }
//
//
//
//                            String select = "Select COD_CLIENTE, COD_SUBCLIENTE, FECHA, TIPO "
//                                    + "  from vt_marcacion_ubicacion_venta "
//                                    + "  where TIPO in ('E','S')"
//                                    + "    and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                                    + "  order by id desc ";
//                            Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//                            int nreg = cursor.getCount();
//                            cursor.moveToFirst();
//                            if (nreg > 0) {
//
//
//                                String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
//                                String cod_cliente = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"));
//                                String cod_subcliente = cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"));
//                                if (!tipo.equals("S")) {
//                                    Toast.makeText(List_clientes.this, "Debe marcar la salida del cliente " + cod_cliente + " - " + cod_subcliente, Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//
//                            }
//
//
//
//
//                            String fec_entrada;
//                            fec_entrada = Aplicacion.utilidades.obtieneFechaActual() + " " + Aplicacion.utilidades.obtieneHoraActual();
//
//                            ((CheckBox)v).setText(Aplicacion.utilidades.obtieneFechaActual() + " " + Aplicacion.utilidades.obtieneHoraActual());
//                            chkSalida.setEnabled(true);
//
//
//
//                            //INSERTA CABECERA
//                            ContentValues values = new ContentValues();
//                            values.put("COD_EMPRESA", Aplicacion.codEmpresa);
//                            values.put("COD_PROMOTOR",Aplicacion._cod_prom_vendedor);
//                            values.put("COD_CLIENTE", codigo_cli[save]);
//                            values.put("COD_SUBCLIENTE", codigo_subcli[save]);
//                            values.put("ESTADO", "P");
//                            values.put("FECHA", fec_entrada);
//                            values.put("TIPO", "E");
//                            values.put("LATITUD", Aplicacion._lati);
//                            values.put("LONGITUD", Aplicacion._longi);
//
//                            Aplicacion.bdatos.insert("vt_marcacion_ubicacion_venta", null, values);
//
//                        } else {
//
//                            String select = " Select id, COD_EMPRESA, COD_PROMOTOR, COD_CLIENTE, COD_SUBCLIENTE, ESTADO, FECHA, TIPO, LATITUD, LONGITUD "
//                                    + " from vt_marcacion_ubicacion_venta  "
//                                    + " where COD_CLIENTE = '" + codigo_cli[save] + "'"
//                                    + "   and COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                                    + "   and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
////											+ "   and FECHA like '%" + MenuCombinado.utilidadesPromotor.obtieneFechaActual() + "%'"
//                                    + "   and TIPO = 'E' "
//                                    + " order by id desc ";
//
//
//                            Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//                            cursor.moveToFirst();
//
//                            int nreg = cursor.getCount();
//
//                            if (nreg > 0) {
//
//                                String update = "";
//
//                                ((CheckBox)v).setText("");
//                                chkSalida.setEnabled(false);
//
//                                String id = cursor.getString(cursor.getColumnIndex("id"));
//
//                                update = "delete from vt_marcacion_ubicacion_venta where id = " + id+" "
//                                        + "  and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'";
//                                Aplicacion.bdatos.execSQL(update);
//
//
//                            }
//
//
//                        }
//                        cargarMarcaciones();
//                    }
//
//
//                });
//
//
//
//
//
//                chkSalida.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        boolean isChecked = ((CheckBox)v).isChecked();
//
//
//                        if (isChecked) {
//
//                            if (!valida_hora_automatica()) {
//                                ((CheckBox)v).setChecked(false);
//                                return;
//                            }
//
//                            if (!valida_visita_cliente()) {
//                                ((CheckBox)v).setChecked(false);
//                                return;
//                            }
//
//
//                            if (_ind_presencial.equals("S")) {
//                                if (Aplicacion._lati.equals("") || Aplicacion._longi.equals("")) {
//                                    Toast.makeText(List_clientes.this, "No se encuentra la ubicacion GPS del telefono", Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//
//                                double distancia_cliente = Aplicacion.utilidades.calculaDistanciaCoordenadas(
//                                        Double.parseDouble(Aplicacion._lati),
//                                        Double.parseDouble(latitud[save]),
//                                        Double.parseDouble(Aplicacion._longi),
//                                        Double.parseDouble(longitud[save])) * 1000;
//
//
//                                if (distancia_cliente > rango_distancia_cliente) {
//                                    Toast.makeText(List_clientes.this, "No se encuentra en el cliente. Se encuentra a " + Math.round(distancia_cliente) + " m." , Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//                            }
//
//                            String select = "Select FECHA, TIPO "
//                                    + "  from vt_marcacion_ubicacion_venta "
//                                    + "  where COD_CLIENTE 	  = '" + codigo_cli[save] 	 + "'"
//                                    + "    and COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                                    + "    and TIPO in ('S','E')"
//                                    + "  order by id desc ";
//                            Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//                            int nreg = cursor.getCount();
//                            cursor.moveToFirst();
//                            if (nreg == 0) {
//                                Toast.makeText(List_clientes.this, "Debe marcar la entrada al cliente", Toast.LENGTH_SHORT).show();
//                                ((CheckBox)v).setChecked(false);
//                                return;
//                            } else {
//
//                                String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
//                                if (!tipo.equals("E")) {
//                                    Toast.makeText(List_clientes.this, "Debe marcar la entrada al cliente", Toast.LENGTH_SHORT).show();
//                                    ((CheckBox)v).setChecked(false);
//                                    return;
//                                }
//
//                                try {
//                                    DateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
//                                    Date date1, date2 ;
//                                    date1 = dateFormat.parse(cursor.getString(cursor.getColumnIndex("FECHA")));
//                                    date2 = dateFormat.parse(Aplicacion.utilidades.obtieneFechaActual() + " " + Aplicacion.utilidades.obtieneHoraActual());
//
//                                    long milis1 = date1.getTime() / 60000;
//                                    long milis2 = date2.getTime() / 60000;
//                                    long diff = milis2 - milis1;
//
//                                    if (diff < 0) {
//                                        diff = diff * -1;
//                                    }
//
//                                    double min_lapso = Double.parseDouble(tiempo_min[save]);
//
//                                    if (Aplicacion._per_asistencia.equals("N")) {
//
//                                        if (diff < min_lapso) {
//
//
//
//                                            Toast.makeText(List_clientes.this, "DEBE PERMANECER UN MINIMO DE " + tiempo_min[save] + " min. EN EL CLIENTE", Toast.LENGTH_LONG).show();
////														mensajebd("ATENCION!", "DEBE PERMANECER UN MINIMO DE " + 5 + " min. EN EL CLIENTE", context);
//
//                                            ((CheckBox)v).setChecked(false);
//
//                                            return;
//
//                                        }
//
//                                    }
//
//
//
//
//
//                                } catch (Exception e) {
//                                    Toast.makeText(List_clientes.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//
//
//
//
//                            String fec_entrada;
//                            fec_entrada = Aplicacion.utilidades.obtieneFechaActual() + " " + Aplicacion.utilidades.obtieneHoraActual();
//
//                            ((CheckBox)v).setText(Aplicacion.utilidades.obtieneFechaActual() + " " + Aplicacion.utilidades.obtieneHoraActual());
//                            chkSalida.setEnabled(true);
//
//
//
//                            //INSERTA CABECERA
//                            ContentValues values = new ContentValues();
//                            values.put("COD_EMPRESA", Aplicacion.codEmpresa);
//                            values.put("COD_PROMOTOR", Aplicacion._cod_prom_vendedor);
//                            values.put("COD_CLIENTE", codigo_cli[save]);
//                            values.put("COD_SUBCLIENTE", codigo_subcli[save]);
//                            values.put("ESTADO", "P");
//                            values.put("FECHA", fec_entrada);
//                            values.put("TIPO", "S");
//                            values.put("LATITUD", Aplicacion._lati);
//                            values.put("LONGITUD", Aplicacion._longi);
//
//                            Aplicacion.bdatos.insert("vt_marcacion_ubicacion_venta", null, values);
//
//
//
//                            chkEntrada.setEnabled(false);
//                        } else {
//
//                            String select = " Select id, COD_EMPRESA, COD_PROMOTOR, COD_CLIENTE, COD_SUBCLIENTE, ESTADO, FECHA, TIPO, LATITUD, LONGITUD "
//                                    + " from vt_marcacion_ubicacion_venta  "
//                                    + " where COD_CLIENTE = '" + codigo_cli[save] + "'"
//                                    + "   and COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                                    + "   and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
////											+ "   and FECHA like '%" + MenuCombinado.utilidadesPromotor.obtieneFechaActual() + "%'"
//                                    + "   and TIPO = 'S' "
//                                    + " order by id desc ";
//
//
//                            Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//                            cursor.moveToFirst();
//
//                            int nreg = cursor.getCount();
//
//                            if (nreg > 0) {
//                                String id = cursor.getString(cursor.getColumnIndex("id"));
//
//                                String update = "";
//
//                                ((CheckBox)v).setText("");
//
//                                select = " Select id, ESTADO, FECHA, TIPO, LATITUD, LONGITUD "
//                                        + " from vt_marcacion_ubicacion_venta  "
//                                        + " where COD_CLIENTE = '" + codigo_cli[save] + "'"
//                                        + "   and COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                                        + "   and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
////										+ "   and FECHA like '%" + MenuCombinado.utilidadesPromotor.obtieneFechaActual() + "%'"
//                                        + "   and TIPO = 'E' "
//                                        + " order by id desc ";
//
//
//                                cursor = Aplicacion.bdatos.rawQuery(select, null);
//                                cursor.moveToFirst();
//                                String estado = cursor.getString(cursor.getColumnIndex("ESTADO"));
//                                if (estado.equals("E")) {
//                                    chkEntrada.setEnabled(false);
//                                } else {
//                                    chkEntrada.setEnabled(true);
//                                }
//
//
//
//                                update = "delete from vt_marcacion_ubicacion_venta where id = " + id+" "
//                                        + "  and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'";
//                                Aplicacion.bdatos.execSQL(update);
//
//
//                            }
//
//
//                        }
//
//                        cargarMarcaciones();
//
//                    }
//                });
////
//
//
//
//
//
//
//
//                dialogMarcarPresenciaCliente.setCanceledOnTouchOutside(false);
//                dialogMarcarPresenciaCliente.show();
//
//            }
//        });
//
//
//        ibtnEliminar.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
//
//
//
//
//
//
//
//
//
//
//        dialogMarcacionesCliente.setCanceledOnTouchOutside(false);
//        dialogMarcacionesCliente.show();
//
//    }
//
//    String[] id, fecha, tipo, estado_marcacion;
//
//    private void cargarMarcaciones() {
//        Cursor cursor;
//
//        String select = "Select a.id, a.COD_CLIENTE, a.COD_SUBCLIENTE, a.FECHA, a.COD_PROMOTOR, case when a.TIPO = 'E' then 'ENTRADA' else 'SALIDA' end TIPO, a.ESTADO, a.LATITUD, a.LONGITUD, b.DESC_SUBCLIENTE  "
//                + "  from vt_marcacion_ubicacion_venta a,"
//                + "		  CLI_VENDEDOR b "
////    			+ "  where a.FECHA like '%" + MenuCombinado.utilidadesPromotor.obtieneFechaActual() + "%' "
//                + "  where a.COD_CLIENTE    = '" + codigo_cli[save] + "'"
//                + "    and a.COD_SUBCLIENTE = '" + codigo_subcli[save] + "'"
//                + "    and a.COD_CLIENTE    = b.COD_CLIENTE"
//                + "    and a.COD_SUBCLIENTE = b.COD_SUBCLIENTE "
//                + "    and a.COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                + "    and b.COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                + "    and ( a.TIPO = 'E' or a.TIPO = 'S' ) "
//                + "  group by a.id, a.COD_CLIENTE, a.COD_SUBCLIENTE, a.FECHA, a.COD_PROMOTOR, a.TIPO, a.ESTADO, a.LATITUD, a.LONGITUD, b.DESC_SUBCLIENTE "
//                + "  order by a.id desc " ;
//
//        cursor = Aplicacion.bdatos.rawQuery(select, null);
//        cursor.moveToFirst();
//        int nreg = cursor.getCount();
//
//
//        id = new String[nreg];
//        fecha = new String[nreg];
//        tipo = new String[nreg];
//        estado_marcacion = new String[nreg];
//
//        list_marcaciones = new ArrayList<HashMap<String, String>>();
//
//
//        for (int i = 0; i < nreg; i++) {
//
//
//            HashMap<String, String>map=new HashMap<String, String>();
//
//            map.put("FECHA", cursor.getString(cursor
//                    .getColumnIndex("FECHA")));
//
//            map.put("TIPO", cursor.getString(cursor
//                    .getColumnIndex("TIPO")));
//
//            map.put("ESTADO", cursor.getString(cursor
//                    .getColumnIndex("ESTADO")));
//
//            list_marcaciones.add(map);
//
//            id[i] = cursor.getString(cursor
//                    .getColumnIndex("id"));
//
//            fecha[i] = cursor.getString(cursor
//                    .getColumnIndex("FECHA"));
//
//            tipo[i] = cursor.getString(cursor
//                    .getColumnIndex("TIPO"));
//
//            estado_marcacion[i] = cursor.getString(cursor
//                    .getColumnIndex("ESTADO"));
//
//
//
//            cursor.moveToNext();
//        }
//
//
//
//        adaptadorMarcaciones = new Adapter_lista_marcaciones(List_clientes.this, list_marcaciones,
//                R.dialogo_bonificacion_combo.list_text_lista_marcaciones, new String[] { "FECHA", "TIPO", "ESTADO" },
//                new int[] { R.id.td1 , R.id.td2 , R.id.td3 });
//
//
//        listaMarcaciones.setAdapter(adaptadorMarcaciones);
//
//        listaMarcaciones.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//
//                saveMarcacion = position;
//                listaMarcaciones.invalidateViews();
//
//            }
//
//        });
//    }
//
//
//
//
//
//    String cadena = "";
//    private void procesa_envia_marcaciones() {
//
//
//        String select = "Select a.id, a.COD_CLIENTE, a.COD_SUBCLIENTE, a.FECHA, a.COD_PROMOTOR, a.TIPO, a.ESTADO, a.LATITUD, a.LONGITUD, ifnull(a.OBSERVACION,'') OBSERVACION   "
//                + "  from vt_marcacion_ubicacion_venta a "
//                + "  where a.ESTADO = 'P' "
//                + "    and a.TIPO in ('E','S') "
//                + "    and a.COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                + "  group by a.id, a.COD_CLIENTE, a.COD_SUBCLIENTE, a.FECHA, a.COD_PROMOTOR, a.TIPO, a.ESTADO, a.LATITUD, a.LONGITUD, a.OBSERVACION  "
//                + "  order by a.id desc " ;
//
//        cadena = "INSERT ALL";
//        String detalle1 = " INTO fv_marcacion_asistencia (  cod_empresa		," +
//                "cod_vendedor	," +
//                "cod_cliente 	," +
//                "cod_subcliente 	," +
//                "tipo	    	," +
//                "fec_asistencia 	," +
//                "latitud    		," +
//                "longitud     	," +
//                "cod_persona     ," +
//                "comentario   )  " +
//                "VALUES(";
//
//
//        Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//        cursor.moveToFirst();
//        int nreg = cursor.getCount();
//
//        for (int i = 0; i < nreg; i++) {
//            String cod_empresa, cod_vendedor, cod_cliente, cod_subcliente, tipo, fecha, latitud, longitud, observacion;
//            cod_empresa = "1";
//            cod_vendedor = Aplicacion.et_login.getText().toString();
//            cod_cliente = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"));
//            cod_subcliente = cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"));
//            tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
//            fecha = cursor.getString(cursor.getColumnIndex("FECHA"));
//            latitud = cursor.getString(cursor.getColumnIndex("LATITUD"));
//            longitud = cursor.getString(cursor.getColumnIndex("LONGITUD"));
//            observacion = cursor.getString(cursor.getColumnIndex("OBSERVACION"));
//
//            cadena += detalle1 + "'" + cod_empresa + "','" + cod_vendedor + "','" + cod_cliente + "','" + cod_subcliente;
//            cadena += "','" + tipo + "',to_date('" + fecha + "','dd/MM/yyyy hh24:mi:ss'),'" + latitud + "','" + longitud + "','" + Aplicacion._cod_persona + "','" + observacion + "') ";
//
//            cursor.moveToNext();
//        }
//
//        cadena += " SELECT * FROM dual ";
//
//        if (nreg == 0) {
//            Toast.makeText(List_clientes.this, "No existe ninguna marcacion pendiente de envio", Toast.LENGTH_LONG).show();
//        } else {
//            new performBackgroundTaskEnvioMarcacion().execute();
//        }
//
//    }
//
//
//
//
//    String resultado = "";
//
//    private class performBackgroundTaskEnvioMarcacion extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//
//            try {
//                pbarDialog.dismiss();
//            } catch (Exception e) {
//
//            }
//
//            pbarDialog = ProgressDialog.show(context, "Un momento...",
//                    "Enviando Marcaciones", true);
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            try {
//                resultado = Aplicacion.cWS.onClickProcesaMarcacionAsistencia(Aplicacion.et_login.getText().toString(), cadena);
//
//
//                return null;
//            } catch (Exception e) {
//                resultado = e.getMessage();
//                return null;
//
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//
//            pbarDialog.dismiss();
//
//            String mensaje[] = resultado.split("\\*");
//            if (mensaje.length == 1) {
//                mensajebd("Resultado", resultado);
//            } else {
//                if (mensaje[0].equals("01")) {
//                    String update = "update vt_marcacion_ubicacion_venta set ESTADO = 'E' where ESTADO = 'P' and TIPO in ('E','S')"
//                            + "   and a.COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'";
//                    Aplicacion.bdatos.execSQL(update);
//                }
//
//                mensajebd("Resultado", mensaje[1]);
//            }
//            cargarMarcaciones();
//
//
//        }
//
//    }
//
//
//
//
//    private String[] selectColumnasCliVendedor() {
//        String[] select =
//                new String[] { "TIP_CAUSAL"  , "COD_CLIENTE", "COD_SUBCLIENTE", "DESC_CLIENTE"  , "DESC_SUBCLIENTE",
//                        "RUC"         , "DESC_ZONA"  , "DESC_REGION"   , "desc_ciudad"   , "DIRECCION"      ,
//                        "TELEFONO"    , "DESC_TIPO"  , "COD_CONDICION" , "DESC_CONDICION", "LIMITE_CREDITO" ,
//                        "TOT_DEUDA"   , "SALDO"      , "FEC_VISITA"    , "COD_SUCURSAL"  , "TIPO_CONDICION" ,
//                        "DIAS_INICIAL", "IND_ATRAZO" , "COMENTARIO"    , "IND_ESP"		, "CATEGORIA"      ,
//                        "TELEFONO2"   , "LATITUD"    , "LONGITUD"      , "CERCA_DE"		, "IND_CADUCADO"   ,
//                        "TIP_CLIENTE" , "ESTADO"     , "IND_VISITA"    , "TIEMPO_MIN"    , "TIEMPO_MAX"	   ,
//                        "SOL_TARG"};
//
//
//        return select;
//    }
//
//    private String whereCliVendedorBloqueados() {
//        String where = "";
//        switch (_tip_filter) {
//            case 0:
//                where =	" COD_VENDEDOR = '" 			+ Aplicacion._cod_prom_vendedor 		+ "'"
//                        + " and  (DESC_SUBCLIENTE like '%" 	+ List_clientes.selection.getText().toString() 	+ "%'"
//                        + " or DESC_CLIENTE like '%" 		+ List_clientes.selection.getText().toString()  + "%')";
//                break;
//            case 1:
//                where = " COD_VENDEDOR= '" 	 + Aplicacion._cod_prom_vendedor  	+ "'"
//                        + " and COD_CLIENTE like '%" + List_clientes.selection.getText().toString() + "%'";
//                break;
//            case 2:
//                where = " COD_VENDEDOR = '" 	+ Aplicacion._cod_prom_vendedor 		+ "'"
//                        + " and desc_ciudad like '%" + List_clientes.selection.getText().toString()  + "%'";
//                break;
//        }
//
//        return where;
//    }
//
//
//    @SuppressWarnings("unused")
//    private class performBackgroundTask3 extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//
//            try {
//                pbarDialog.dismiss();
//            } catch (Exception e) {
//
//            }
//
//            pbarDialog = ProgressDialog.show(context, "Un momento...",
//                    "Obteniendo ubicacion GPS", true);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            int time = 0;
//            while (Aplicacion._lati.equals("") && time < 60) {
//                try {
//                    Thread.sleep(1000);
//                    time ++;
//                } catch (InterruptedException e) {
//
//                    e.printStackTrace();
//                    String err = e.getMessage();
//                    err = err + "";
//                }
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//
//            pbarDialog.dismiss();
//
//
//
//            Aplicacion._clave_pedido="";
//            if (_tip_causal.equals("B")) {
//
//                AlertDialog.Builder alert_clave1 = new AlertDialog.Builder(List_clientes.this);
//                alert_clave1
//                        .setTitle("Solicitar Autorización!!");
//
//                Aplicacion._clave_temp = Claves.smv_clave_cliente(Aplicacion._code_cli, Aplicacion._code_subcli);
//                alert_clave1.setMessage(Aplicacion._clave_temp);
//
//                final EditText input_clave1 = new EditText(List_clientes.this);
//
//                input_clave1.setInputType(InputType.TYPE_CLASS_NUMBER
//                        | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
//                alert_clave1.setView(input_clave1);
//
//                alert_clave1.setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                String pass = input_clave1.getText().toString();
//                                if (TextUtils.isEmpty(pass)
//                                        || pass.length() < 8) {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                                            context);
//                                    builder.setTitle("Error!!");
//                                    builder.setMessage(
//                                            "La clave no es valida!!")
//
//                                            .setCancelable(false)
//                                            .setPositiveButton(
//                                                    "OK",
//                                                    new DialogInterface.OnClickListener() {
//                                                        public void onClick(
//                                                                DialogInterface dialog,
//                                                                int id) {
//
//                                                            dialog.cancel();
//
//                                                            return;
//                                                        }
//                                                    });
//
//                                }
//
//
//                                final String srt = input_clave1.getEditableText()
//                                        .toString();
//                                String a = Claves.smv_contraclave_cliente(Aplicacion._clave_temp, Aplicacion._code_cli, Aplicacion._code_subcli);
//                                if (srt.equals(a)) {
//
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                                            context);
//                                    builder.setTitle("Suceso!!");
//                                    builder.setMessage("La clave fue aceptada")
//
//                                            .setCancelable(false)
//                                            .setPositiveButton(
//                                                    "OK",
//                                                    new DialogInterface.OnClickListener() {
//                                                        public void onClick(
//                                                                DialogInterface dialog,
//                                                                int id) {
//
//                                                            Aplicacion._nro_pedido = 0;
//                                                            Aplicacion._clave_pedido=srt;
//                                                            startActivity(new Intent(
//                                                                    List_clientes.this,
//                                                                    pedidos_apolo.class));
//
//
//                                                            return;
//                                                        }
//                                                    });
//                                    AlertDialog alert = builder.create();
//                                    alert.show();
//
//                                } else
//
//                                {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(
//                                            context);
//                                    builder.setTitle("Error!!");
//                                    builder.setMessage(
//                                            "La clave no es valida!!")
//
//                                            .setCancelable(false)
//                                            .setPositiveButton(
//                                                    "OK",
//                                                    new DialogInterface.OnClickListener() {
//                                                        public void onClick(
//                                                                DialogInterface dialog,
//                                                                int id) {
//
//                                                            dialog.cancel();
//
//                                                            return;
//                                                        }
//                                                    });
//                                    AlertDialog alert = builder.create();
//                                    alert.show();
//
//                                }
//
//                            }
//
//                        });
//                alert_clave1.setNegativeButton("Cancelar",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                dialog.cancel();
//
//                            }
//                        });
//                AlertDialog Alert_clave1 = alert_clave1.create();
//                Alert_clave1.show();
//
//            } else {
//
//                if (!Aplicacion._code_cli.toString().equals("") )  {
//
//
//
//                    if (!mensaje[save].equals("")) {
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                        builder.setTitle("Atención");
//                        builder.setMessage(mensaje[save])
//
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        Aplicacion._nro_pedido=0;
//
//                                        startActivity(new Intent(List_clientes.this, pedidos_apolo.class));
//                                        return;
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//
//                    } else {
//                        Aplicacion._nro_pedido=0;
//                        startActivity(new Intent(List_clientes.this, pedidos_apolo.class));
//                    }
//
//                }
//
//            }
//
//
//
//
//        }
//
//    }
//
//
//    public void llama_pedido(View arg0) {
//
//        if (Aplicacion._code_cli.toString().equals("") )
//        {
//            return;
//        }
//
//		/*if (can_cli_bloq > 0 && !ind_visita[save].equals("B")) {
//			Toast.makeText(List_clientes.this, "Debe atender a los clientes no Atendidos", Toast.LENGTH_SHORT).show();
//			return;
//		}*/
//
//        try {
//
//            if (!Aplicacion.lm2.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                //CONECTAR EL GPS
//                Intent callGPSSettingIntent = new Intent(
//                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(callGPSSettingIntent);
//                return;
//            } else {
//
//
////				if (Aplicacion._lati.equals("")
////						|| Aplicacion._longi.equals("") ) {
////					new performBackgroundTask3().execute();
////					return;
////
////				}
//
//            }
//        } catch (Exception e) {
//            String err = e.getMessage();
//            err = err + "";
//            return;
//        }
//
//
//
//        Aplicacion._clave_pedido="";
//        if (_tip_causal.equals("B")) {
//
//            AlertDialog.Builder alert_clave1 = new AlertDialog.Builder(List_clientes.this);
//            alert_clave1
//                    .setTitle("Solicitar Autorización!!");
//
//            Aplicacion._clave_temp = Claves.smv_clave_cliente(Aplicacion._code_cli, Aplicacion._code_subcli);
//            alert_clave1.setMessage(Aplicacion._clave_temp);
//
//            final EditText input_clave1 = new EditText(List_clientes.this);
//
//            input_clave1.setInputType(InputType.TYPE_CLASS_NUMBER
//                    | InputType.TYPE_NUMBER_VARIATION_NORMAL);
//
//            alert_clave1.setView(input_clave1);
//
//            alert_clave1.setPositiveButton("OK",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog,
//                                            int whichButton) {
//
//                            String pass = input_clave1.getText().toString();
//                            if (TextUtils.isEmpty(pass)
//                                    || pass.length() < 8) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(
//                                        context);
//                                builder.setTitle("Error!!");
//                                builder.setMessage(
//                                        "La clave no es valida!!")
//
//                                        .setCancelable(false)
//                                        .setPositiveButton(
//                                                "OK",
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(
//                                                            DialogInterface dialog,
//                                                            int id) {
//
//                                                        dialog.cancel();
//
//                                                        return;
//                                                    }
//                                                });
//
//                            }
//
//
//                            final String srt = input_clave1.getEditableText()
//                                    .toString();
//                            String a = Claves.smv_contraclave_cliente(Aplicacion._clave_temp, Aplicacion._code_cli, Aplicacion._code_subcli);
//                            if (srt.equals(a)) {
//
//                                AlertDialog.Builder builder = new AlertDialog.Builder(
//                                        context);
//                                builder.setTitle("Suceso!!");
//                                builder.setMessage("La clave fue aceptada")
//
//                                        .setCancelable(false)
//                                        .setPositiveButton(
//                                                "OK",
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(
//                                                            DialogInterface dialog,
//                                                            int id) {
//
//                                                        Aplicacion._nro_pedido = 0;
//                                                        Aplicacion._clave_pedido=srt;
//                                                        startActivity(new Intent(
//                                                                List_clientes.this,
//                                                                pedidos_apolo.class));
//
//
//                                                        return;
//                                                    }
//                                                });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(
//                                        context);
//                                builder.setTitle("Error!!");
//                                builder.setMessage(
//                                        "La clave no es valida!!")
//                                        .setCancelable(false)
//                                        .setPositiveButton(
//                                                "OK",
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(
//                                                            DialogInterface dialog,
//                                                            int id) {
//
//                                                        dialog.cancel();
//
//                                                        return;
//                                                    }
//                                                });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//
//                            }
//
//                        }
//
//                    });
//            alert_clave1.setNegativeButton("Cancelar",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog,
//                                            int whichButton) {
//
//                            dialog.cancel();
//
//                        }
//                    });
//            AlertDialog Alert_clave1 = alert_clave1.create();
//            Alert_clave1.show();
//
//        } else {
//
//            if (!valida_existe_solicitud_tc("'E','P'") && sol_targ[save].equals("S")) {
//                solicitar_tc(null);
//                Toast.makeText(List_clientes.this, "DEBE COMPLETAR LA SOLICITUD DE TARJETA DE CREDITO ANTES DE VENDER", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            if (!Aplicacion._code_cli.toString().equals("") )  {
//
//                if (ind_caducado[save].equals("S")) {
//                    actualizar_datos_cliente_caducado(true);
//                } else {
//
//                    vender_cliente();
//					/*if (validaEntradaCliente()) {
//						indica_presencia();
//					}*/
//
//                }
//
////				if (ind_caducado[save].equals("S") || latitud[save].equals("") || longitud[save].equals("")) {
////					actualizar_cliente_caducado();
////				} else {
////					vender_cliente();
////				}
//
//
//            }
//
//        }
//
//
//    }
//
//
//    private boolean validaEntradaCliente() {
//
//        String select = "Select COD_CLIENTE, COD_SUBCLIENTE, TIPO "
//                + " from vt_marcacion_ubicacion_venta "
//                + " where TIPO = 'E' or TIPO = 'S' "
//                + "  and COD_PROMOTOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                + " order by id desc ";
//        Cursor cursor = Aplicacion.bdatos.rawQuery(select, null);
//        cursor.moveToFirst();
//        int nreg = cursor.getCount();
//
//        if (nreg == 0) {
//            Toast.makeText(List_clientes.this, "Debe marcar la entrada a este cliente", Toast.LENGTH_LONG).show();
//            return false;
//        } else {
//            String tipo, cod_cliente, cod_subcliente;
//            tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
//            cod_cliente = cursor.getString(cursor.getColumnIndex("COD_CLIENTE"));
//            cod_subcliente = cursor.getString(cursor.getColumnIndex("COD_SUBCLIENTE"));
//
//            if (tipo.equals("S")) {
//                Toast.makeText(List_clientes.this, "Debe marcar la entrada a este cliente", Toast.LENGTH_LONG).show();
//                return false;
//            } else {
//                if (codigo_cli[save].equals(cod_cliente) && codigo_subcli[save].equals(cod_subcliente)) {
//                    return true;
//                }
//            }
//
//        }
//
//        Toast.makeText(List_clientes.this, "Debe marcar la entrada a este cliente", Toast.LENGTH_LONG).show();
//        return false;
//
//    }
//
//
//
//
//    String name = "";
//    String imagen_fachada = null;
//    ImageButton ibtnFotoFachada;
//
//
//    String foto1 = "";
//    String foto2 = "";
//
//
//
//
//
//
//    private void vender_cliente(){
//        if (!mensaje[save].equals("")) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Atención");
//            builder.setMessage(mensaje[save])
//
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            Aplicacion._nro_pedido=0;
//
//                            startActivity(new Intent(List_clientes.this, pedidos_apolo.class));
//                            return;
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//        } else {
//            try{
//                Aplicacion._nro_pedido=0;
//                startActivity(new Intent(List_clientes.this, pedidos_apolo.class));
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//
//    public void llama_sugestion(View arg0) {
//        Aplicacion.ult_posicion_trae_clientes = 0;
//        save = -1;
//
//        alist = new ArrayList<HashMap<String, String>>();
//        String[] select = selectColumnasCliVendedor();
//
//        try {
//
//            Date date = new Date();
//
//            SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
//            date_to_string = formatDateJava.format(date);
//
//            cursor_datos = Aplicacion.bdatos.query("CLI_VENDEDOR", select,
//                    "COD_VENDEDOR = '" + Aplicacion._cod_prom_vendedor + "'"
//                            + " and  ind_visita = 'S'"
//                            + " and  FEC_VISITA= '" + date_to_string + "'" ,
//                    null, null, null, "DESC_SUBCLIENTE");
//
//            int nreg = cursor_datos.getCount();
//
//            cursor_datos.moveToFirst();
//
//            dimensionaArrays(nreg);
//
//            cont = 0;
//
//            cargaHashMap(nreg);
//
//            creacionGridView();
//
//
//            return;
//
//        } catch (Exception e) {
//
//            mensajebd("Error", " A" + e.getMessage());
//        }
//
//    }
//
//
//    public void llama_bloqueados(View arg0) {
//        Aplicacion.ult_posicion_trae_clientes = 0;
//        save = -1;
//
//        alist = new ArrayList<HashMap<String, String>>();
//        String[] select = selectColumnasCliVendedor();
//
//        try {
//
//            Date date = new Date();
//
//            SimpleDateFormat formatDateJava = new SimpleDateFormat("dd/MM/yyyy");
//            date_to_string = formatDateJava.format(date);
//
//            cursor_datos = Aplicacion.bdatos.query("CLI_VENDEDOR", select,
//                    "COD_VENDEDOR = '" + Aplicacion._cod_prom_vendedor + "'"
//                            + " and  ind_visita = 'B'"
//                            + " and  FEC_VISITA= '" + date_to_string + "'"
//                            + " and " + whereCliVendedorBloqueados(),
//                    null, null, null, "DESC_SUBCLIENTE");
//
//            int nreg = cursor_datos.getCount();
//
//            cursor_datos.moveToFirst();
//
//            dimensionaArrays(nreg);
//
//            cont = 0;
//
//            cargaHashMap(nreg);
//
//            creacionGridView();
//
//
//            return;
//
//        } catch (Exception e) {
//
//            mensajebd("Error", " A" + e.getMessage());
//        }
//
//    }
//
//
//
//    public class Adapter_lista_promociones_cab extends SimpleAdapter {
//        String _sqlupdate ;
//        private int[] colors = new int[] {Color.parseColor("#696969"), Color.parseColor("#808080") };
//
//        public Adapter_lista_promociones_cab(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
//            super(context, items, resource, from, to);
//        }
//
//        public class ViewHolder {
//
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            View view = super.getView(position, convertView, parent);
//            final int colorPos = position % colors.length;
//
//            view.setBackgroundColor(colors[colorPos]);
//
//            if (position == savePromocionesCab) {
//                view.setBackgroundColor(Color.BLUE);
//            }
//
//            final ViewHolder holder =  new ViewHolder();
//
//            view.setTag(holder);
//
//            return view;
//        }
//    }
//
//
//
//    private void valida_ubicacion() {
//
//        if (!validaEntradaCliente()) {
//            return;
//        }
//
//
//        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(List_clientes.this);
//
//        myAlertDialog.setMessage("¿Se encuentra en el cliente?");
//        myAlertDialog.setPositiveButton("Si",
//                new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                        _ind_presencial = "S";
//
//                        actualizaLatLongCli();
//
//                        if (!valida_ubicacion_simulada()) {
//                            return;
//                        }
//
//                        if (latitud[save].equals("") || longitud[save].equals("")) {
//                            //ABRIR EL MAPA
//                            Aplicacion._ind_mapa_cliente = "6";
//                            startActivity(new Intent(List_clientes.this, MapaCliente.class));
//                            return;
//
//                        } else {
//
//                            if (Aplicacion._lati.equals("") || Aplicacion._longi.equals("")) {
//                                Toast.makeText(List_clientes.this, "No se encuentra la ubicacion GPS del telefono", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            double distancia_cliente = Aplicacion.utilidades.calculaDistanciaCoordenadas(
//                                    Double.parseDouble(Aplicacion._lati),
//                                    Double.parseDouble(latitud[save]),
//                                    Double.parseDouble(Aplicacion._longi),
//                                    Double.parseDouble(longitud[save])) * 1000;
//
//
//                            if (distancia_cliente > rango_distancia_cliente) {
//                                Toast.makeText(List_clientes.this, "No se encuentra en el cliente. Se encuentra a " + Math.round(distancia_cliente) + " m." , Toast.LENGTH_SHORT).show();
//                                return;
//                            } else {
//                                marcar_no_positivacion();
//                                return;
//                            }
//
//                        }
//
////
//
//                    }
//                });
//        if (!Aplicacion._per_presencial.equals("S")) {
//
//
//            myAlertDialog.setNegativeButton("No",
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            _ind_presencial = "N";
//                            marcar_no_positivacion();
//
//                        }
//                    });
//
//
//        }
//
//        myAlertDialog.show();
//
//
//
//    }
//
//
//    private void marcar_no_positivacion() {
//
//
//
//        if (Aplicacion._code_cli.toString().equals("") )
//        {
//            return;
//        }
//        Cursor cursor_marcaciones_visita_buscar;
//
//        try{
//
//            String select = "Select id, COD_EMPRESA, COD_SUCURSAL, COD_CLIENTE, COD_SUBCLIENTE, COD_VENDEDOR, COD_MOTIVO, OBSERVACION, FECHA, LATITUD, LONGITUD, ESTADO, HORA_ALTA "
//                    + " from vt_marcacion_visita "
//                    + " where COD_CLIENTE    = '" + Aplicacion._code_cli + "' "
//                    + "   and COD_SUBCLIENTE = '" + Aplicacion._code_subcli + "' "
//                    + "   and COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                    + "   and FECHA 	     = '" + Aplicacion.utilidades.obtieneFechaActual().substring(0, 10).toString() + "'"
//                    + "   and COD_MOTIVO NOT IN ('16')";
//            cursor_marcaciones_visita_buscar = Aplicacion.bdatos.rawQuery(select, null);
//            cursor_marcaciones_visita_buscar.moveToFirst();
//            int can = 0;
//            can = cursor_marcaciones_visita_buscar.getCount();
//            _id = "";
//
//            if ((estado2[save]!=null && can > 0) || can > 0) {
//
//                String estado = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("ESTADO"));
//
//                if (!estado.equals("E")) {
//
//                    modifica_marcacion_visita2(select, false);
//
//
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Atención");
//                    builder.setMessage("Ya se cargo la marcación de visita de este cliente")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    return;
//
//                                }
//
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//
//
//
//            } else {
//
//
//
//
//                Aplicacion._clave_pedido="";
//
//                AlertDialog.Builder alert_motivos = new AlertDialog.Builder(this);
//
//                alert_motivos.setTitle("      Justificar la no Venta");
//
//                final String _fecha = Aplicacion.utilidades.obtieneFechaActual();
//                alert_motivos.setMessage("Realizada el "+ _fecha);
//
//                alist = new ArrayList<HashMap<String, String>>();
//                Cursor cursor_datos3 = Aplicacion.bdatos.query("svm_motivo_no_venta",
//                        new String[] {"COD_MOTIVO", "DESC_MOTIVO", "CIERRA" },
//                        null, null, null, null, "DESC_MOTIVO");
//                int nreg = cursor_datos3.getCount();
//
//
//
//                cursor_datos3.moveToFirst();
//                cod_motivo= new String[nreg];
//                desc_motivo=new String[nreg];
//                cierra_motivo=new String[nreg];
//
//                int cont = 0;
//                for (int i = 0; i < nreg; ++i) {
//
//                    HashMap<String, String>map=new HashMap<String, String>();
//
//                    map.put("cod_condicion_venta", cursor_datos3.getString(cursor_datos3
//                            .getColumnIndex("COD_MOTIVO")));
//
//                    map.put("descripcion", cursor_datos3.getString(cursor_datos3
//                            .getColumnIndex("DESC_MOTIVO")));
//
//                    cod_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                            .getColumnIndex("COD_MOTIVO"));
//
//                    desc_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                            .getColumnIndex("DESC_MOTIVO"));
//
//                    cierra_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                            .getColumnIndex("CIERRA"));
//
//                    if (i==0) {
//                        _motivo=cursor_datos3.getString(cursor_datos3
//                                .getColumnIndex("COD_MOTIVO"));
//                        _cierra_motivo=cursor_datos3.getString(cursor_datos3
//                                .getColumnIndex("CIERRA"));
//                    }
//
//                    cursor_datos3.moveToNext();
//
//                    cont = cont + 1;
//
//                }
//
//
//                final Spinner conSpinner = new Spinner(context);
//
//                final EditText observacion = new EditText(context);
//                final TextView ms_observacion = new TextView(context);
//                ms_observacion.setText("Observaciones:");
//                final TecladoAlfaNumerico teclado = new TecladoAlfaNumerico();
//                observacion.setRawInputType(Configuration.KEYBOARDHIDDEN_YES);
//                observacion.setInputType(InputType.TYPE_NULL);
//                observacion.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        teclado.showTecAlfNum((EditText) observacion, List_clientes.this);
//
//
//                    }
//                });
//
//                observacion.setWidth(60);
//                observacion.setLines(3);
//
//                LinearLayout dialogo_bonificacion_combo = new LinearLayout(List_clientes.this);
//                dialogo_bonificacion_combo.setOrientation(LinearLayout.VERTICAL);
//
//                dialogo_bonificacion_combo.addView(conSpinner);
//                dialogo_bonificacion_combo.addView(ms_observacion);
//                dialogo_bonificacion_combo.addView(observacion);
//
//                alert_motivos.setView(dialogo_bonificacion_combo);
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                        R.dialogo_bonificacion_combo.simple_spinner_item_textsize_5, desc_motivo);
//
//                adapter.setDropDownViewResource(R.dialogo_bonificacion_combo.simple_spinner_dropdown_item_textsize_5);
//                conSpinner.setAdapter(adapter);
//                conSpinner.setSelection(0);
//
//
//                conSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                               int position, long id) {
//                        _motivo=cod_motivo[position];
//                        _cierra_motivo=cierra_motivo[position];
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                    }
//                });
//
//                alert_motivos.setPositiveButton("Enviar",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                String hora_alta = Aplicacion.utilidades.obtieneHoraActual();
//
//                                guardaMarcacionVisita(Aplicacion.codEmpresa, Aplicacion._cod_sucursal_cli, Aplicacion._code_cli, Aplicacion._code_subcli,
//                                        Aplicacion.et_login.getText().toString(), _motivo, observacion.getText().toString(),
//                                        _fecha.substring(0, 10).toString(), Aplicacion._lati, Aplicacion._longi, hora_alta);
//
//                                _nopositivado= "'" + Aplicacion.codEmpresa 						 + "',"
//                                        + "'" + Aplicacion._cod_sucursal_cli 					 + "',"
//                                        + "'" + Aplicacion._code_cli 							 + "',"
//                                        + "'" + Aplicacion._code_subcli 						 + "',"
//                                        + "'" + Aplicacion._cod_prom_vendedor	 + "',"
//                                        + "'" + _motivo										 + "',"
//                                        + "'" + observacion.getText().toString()				 + "',"
//                                        + "to_date('"+_fecha.substring(0,10).toString()+"','DD/MM/YYYY'),"
//                                        + "to_date('" + _fecha.substring(0,10).toString() + " " + hora_alta + "','dd/MM/yyyy hh24:mi:ss'),"
//                                        + "'" + Aplicacion._lati 								 + "',"
//                                        + "'" + Aplicacion._longi 								 + "'";
//
//
//                                if (_cierra_motivo != null) {
//                                    if (_cierra_motivo.equals("S")) {
//                                        cerrar_salida_cliente();
//                                    }
//                                }
//
//                                new back_enviar_positivacion2().execute();
//                            }
//                        });
//
//                alert_motivos.setNeutralButton("Guardar",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                String hora_alta = Aplicacion.utilidades.obtieneHoraActual();
//                                guardaMarcacionVisita(Aplicacion.codEmpresa, Aplicacion._cod_sucursal_cli, Aplicacion._code_cli, Aplicacion._code_subcli,
//                                        Aplicacion.et_login.getText().toString(), _motivo, observacion.getText().toString(),
//                                        _fecha.substring(0, 10).toString(), Aplicacion._lati, Aplicacion._longi, hora_alta);
//
//
//                                if (_cierra_motivo != null) {
//                                    if (_cierra_motivo.equals("S")) {
//                                        cerrar_salida_cliente();
//                                    }
//                                }
//
//
//                                Toast.makeText(List_clientes.this, "Guardado con exito", Toast.LENGTH_LONG).show();
//                            }
//
//
//                        });
//
//
//                alert_motivos.setNegativeButton("Cancelar",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                dialog.cancel();
//
//                            }
//                        });
//                AlertDialog motivos = alert_motivos.create();
//                motivos.show();
//
//
//            }
//
//        } catch (NumberFormatException e) {
//
//        }
//
//
//    }
//
//
//
//    private void modifica_marcacion_visita2(String select, final boolean consultar) {
//
//
//        try {
//            final String cod_motivo_2, observacion_2, fecha, hora_alta;
//            int position_motivo = 0;
//            Cursor cursor_marcaciones_visita_buscar;
//            cursor_marcaciones_visita_buscar = Aplicacion.bdatos.rawQuery(select, null);
//            cursor_marcaciones_visita_buscar.moveToFirst();
//
//
//            _id = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("id"));
//            cod_motivo_2 = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("COD_MOTIVO"));
//            observacion_2 = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("OBSERVACION"));
//            fecha = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("FECHA"));
//            hora_alta = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("HORA_ALTA"));
//
//            Aplicacion._clave_pedido="";
//
//            AlertDialog.Builder alert_motivos = new AlertDialog.Builder(this);
//
//            alert_motivos.setTitle("         Marcación de visita");
//
//            alert_motivos.setMessage("Realizada el "+ Aplicacion.utilidades.obtieneFechaActual());
//
//            alist = new ArrayList<HashMap<String, String>>();
//            Cursor cursor_datos3 = Aplicacion.bdatos.query("svm_motivo_no_venta",
//                    new String[] {"COD_MOTIVO", "DESC_MOTIVO", "CIERRA" },
//                    null, null, null, null, "DESC_MOTIVO");
//            int nreg = cursor_datos3.getCount();
//
//
//
//            cursor_datos3.moveToFirst();
//            cod_motivo= new String[nreg];
//            desc_motivo=new String[nreg];
//            cierra_motivo=new String[nreg];
//
//            int cont = 0;
//            for (int i = 0; i < nreg; ++i) {
//
//                HashMap<String, String>map=new HashMap<String, String>();
//
//                map.put("cod_condicion_venta", cursor_datos3.getString(cursor_datos3
//                        .getColumnIndex("COD_MOTIVO")));
//
//                map.put("descripcion", cursor_datos3.getString(cursor_datos3
//                        .getColumnIndex("DESC_MOTIVO")));
//
//                cod_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                        .getColumnIndex("COD_MOTIVO"));
//
//                desc_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                        .getColumnIndex("DESC_MOTIVO"));
//
//                cierra_motivo[cont] =cursor_datos3.getString(cursor_datos3
//                        .getColumnIndex("CIERRA"));
//
//                if (cod_motivo_2.equals(cod_motivo[cont])) {
//                    position_motivo = cont;
//                }
//
//                cursor_datos3.moveToNext();
//
//                cont = cont + 1;
//
//            }
//
//
//            final Spinner conSpinner = new Spinner(context);
//
//            final EditText observacion = new EditText(context);
//            final TextView ms_observacion = new TextView(context);
//            ms_observacion.setText("Observaciones:");
//            final TecladoAlfaNumerico teclado = new TecladoAlfaNumerico();
//            observacion.setRawInputType(Configuration.KEYBOARDHIDDEN_YES);
//            observacion.setInputType(InputType.TYPE_NULL);
//            observacion.setText(observacion_2);
//            observacion.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View arg0) {
//                    teclado.showTecAlfNum((EditText) observacion, List_clientes.this);
//
//
//                }
//            });
//
//            observacion.setWidth(60);
//            observacion.setLines(3);
//
//            LinearLayout dialogo_bonificacion_combo = new LinearLayout(List_clientes.this);
//            dialogo_bonificacion_combo.setOrientation(LinearLayout.VERTICAL);
//
//            dialogo_bonificacion_combo.addView(conSpinner);
//            dialogo_bonificacion_combo.addView(ms_observacion);
//            dialogo_bonificacion_combo.addView(observacion);
//
//            alert_motivos.setView(dialogo_bonificacion_combo);
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                    R.dialogo_bonificacion_combo.simple_spinner_item_textsize_5, desc_motivo);
//
//            adapter.setDropDownViewResource(R.dialogo_bonificacion_combo.simple_spinner_dropdown_item_textsize_5);
//            conSpinner.setAdapter(adapter);
//            conSpinner.setSelection(1);
//
//
//            conSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                           int position, long id) {
//                    _motivo=cod_motivo[position];
//                    _cierra_motivo=cierra_motivo[position];
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> arg0) {
//                }
//            });
//
//            conSpinner.setSelection(position_motivo);
//
//
//
//            alert_motivos.setPositiveButton("OK",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog,
//                                            int whichButton) {
//
//                            if (consultar) {
//                                dialog.cancel();
//                            } else {
//                                _nopositivado= "'" + Aplicacion.codEmpresa 						 + "',"
//                                        + "'" + Aplicacion._cod_sucursal_cli 					 + "',"
//                                        + "'" + Aplicacion._code_cli 							 + "',"
//                                        + "'" + Aplicacion._code_subcli 						 + "',"
//                                        + "'" + Aplicacion.et_login.getText().toString() 	 + "',"
//                                        + "'" + _motivo										 + "',"
//                                        + "'" + observacion.getText().toString()				 + "',"
//                                        + "to_date('"+fecha.substring(0,10).toString()+"','DD/MM/YYYY'),"
//                                        + "to_date('"+fecha.substring(0,10).toString()+ " " + hora_alta + "','dd/MM/yyyy hh24:mi:ss'),"
//                                        + "'" + Aplicacion._lati 								 + "',"
//                                        + "'" + Aplicacion._longi + "'";
//
//                                if (_cierra_motivo != null) {
//                                    if (_cierra_motivo.equals("S")) {
//                                        cerrar_salida_cliente();
//                                    }
//                                }
//
//                                new back_enviar_positivacion2().execute();
//                            }
//
//
//
//                        }
//                    });
//
//            if (!consultar) {
//
//
//
//                alert_motivos.setNeutralButton("Guardar",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//
//                                try {
//                                    actualizaMarcacionVisita(Aplicacion.codEmpresa, Aplicacion._cod_sucursal_cli, Aplicacion._code_cli,
//                                            Aplicacion._code_subcli, Aplicacion.et_login.getText().toString(), _motivo, observacion.getText().toString(),
//                                            fecha.substring(0, 10).toString(), Aplicacion._lati, Aplicacion._longi, _id);
//                                } catch (Exception e) {
//                                    String err = e.getMessage();
//                                    Toast.makeText(List_clientes.this, err, Toast.LENGTH_LONG).show();
//                                }
//
//                                if (_cierra_motivo != null) {
//                                    if (_cierra_motivo.equals("S")) {
//                                        cerrar_salida_cliente();
//                                    }
//                                }
//
//                                Toast.makeText(List_clientes.this, "Guardado con exito", Toast.LENGTH_LONG).show();
//                            }
//
//
//                        });
//
//            } else {
//                conSpinner.setEnabled(false);
//                observacion.setEnabled(false);
//            }
//
//
//
//
//            alert_motivos.setNegativeButton("Cancelar",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog,
//                                            int whichButton) {
//
//                            dialog.cancel();
//
//                        }
//                    });
//            AlertDialog motivos = alert_motivos.create();
//            motivos.show();
//
//        } catch(Exception e) {
//            String err = e.getMessage();
//            err = err + "";
//        }
//
//    }
//
//
//    private void actualiza_estado_envio_marcacion(String id_marcacion) {
//
//        Cursor cursor_marcaciones_visita_buscar;
//        if (id_marcacion.equals("")) {
//            String select = "Select id "
//                    + " from vt_marcacion_visita "
//                    + " where COD_CLIENTE    = '" + Aplicacion._code_cli + "' "
//                    + "   and COD_SUBCLIENTE = '" + Aplicacion._code_subcli + "' "
//                    + "   and COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'"
//                    + "   and FECHA 	  = '" + Aplicacion.utilidades.obtieneFechaActual().substring(0, 10).toString() + "'";
//            cursor_marcaciones_visita_buscar = Aplicacion.bdatos.rawQuery(select, null);
//            cursor_marcaciones_visita_buscar.moveToFirst();
//            if (cursor_marcaciones_visita_buscar.getCount() > 0) {
//                id_marcacion = cursor_marcaciones_visita_buscar.getString(cursor_marcaciones_visita_buscar.getColumnIndex("id"));
//            }
//
//        }
//
//        ContentValues cv = new ContentValues();
//        cv.put("ESTADO", "E");
//        Aplicacion.bdatos.update("vt_marcacion_visita", cv, "id = '" + id_marcacion + "'", null);
//
//    }
//
//
//    private void actualizaMarcacionVisita(String cod_empresa , String cod_sucursal, String cod_cliente, String cod_subcliente,
//                                          String cod_vendedor, String cod_motivo  , String observacion, String fecha	  	 ,
//                                          String latitud	 , String longitud	  , String id) {
//
//        ContentValues cv = new ContentValues();
//        cv.put("COD_EMPRESA", cod_empresa);
//        cv.put("COD_SUCURSAL", cod_sucursal);
//        cv.put("COD_CLIENTE", cod_cliente);
//        cv.put("COD_SUBCLIENTE", cod_subcliente);
//        cv.put("COD_VENDEDOR", cod_vendedor);
//        cv.put("COD_MOTIVO", cod_motivo);
//        cv.put("OBSERVACION", observacion);
//        cv.put("FECHA", fecha);
//        cv.put("LATITUD", latitud);
//        cv.put("LONGITUD", longitud);
//        Aplicacion.bdatos.update("vt_marcacion_visita", cv, "id = '" + id + "'", null);
//
//    }
//
//    private void guardaMarcacionVisita(String cod_empresa , String cod_sucursal, String cod_cliente, String cod_subcliente,
//                                       String cod_vendedor, String cod_motivo  , String observacion, String fecha	  	  ,
//                                       String latitud	  , String longitud    , String hora_alta) {
//
//
//
//        ContentValues cv = new ContentValues();
//        cv.put("COD_EMPRESA", cod_empresa);
//        cv.put("COD_SUCURSAL", cod_sucursal);
//        cv.put("COD_CLIENTE", cod_cliente);
//        cv.put("COD_SUBCLIENTE", cod_subcliente);
//        cv.put("COD_VENDEDOR", cod_vendedor);
//        cv.put("COD_MOTIVO", cod_motivo);
//        cv.put("OBSERVACION", observacion);
//        cv.put("FECHA", fecha);
//        cv.put("LATITUD", latitud);
//        cv.put("LONGITUD", longitud);
//        cv.put("ESTADO", "P");
//        cv.put("HORA_ALTA", hora_alta);
//        Aplicacion.bdatos.insert("vt_marcacion_visita", null, cv);
//
//
//    }
//
//
//
//    //  FUNCION PARA ENVIAR LA MARCACION DE VISITA
//    private class back_enviar_positivacion2 extends AsyncTask<Void, Void, Void> {
//
//        private ProgressDialog pbarDialog;
//
//        @Override
//        protected void onPreExecute() {
//            pbarDialog = ProgressDialog.show(List_clientes.this, "Un momento...",
//                    "Enviando datos al servidor...", true);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            respuestaWS = Aplicacion.cWS.onClickEnviaMarcacionVisita2(_nopositivado);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//
//            pbarDialog.dismiss();
//            String res = respuestaWS;
//            System.out.println(res);
//            if (respuestaWS.indexOf("01*")>=0) {
//
//                respuestaWS="Marcación de visita enviada con exito!!";
//            }
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage(respuestaWS)
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            ContentValues values = null;
//                            if (respuestaWS.equals("Marcación de visita enviada con exito!!")) {
//                                values = new ContentValues();
//                                values.put("ESTADO","E");
//                                try {
//                                    Aplicacion.bdatos.update("CLI_VENDEDOR", values,
//                                            " COD_CLIENTE = '" + Aplicacion._code_cli.toString()+ "'"
//                                                    + "  and COD_SUBCLIENTE = '" + Aplicacion._code_subcli + "'"
//                                                    + "  and COD_VENDEDOR = '"+Aplicacion._cod_prom_vendedor.toString()+"'" ,null);
//
//                                    estado2[save]="E";
//
//
//                                    actualiza_estado_envio_marcacion(_id);
//
//
//
//                                } catch (Exception e) {
//
//                                    e.printStackTrace();
//                                }
//                                gridView.invalidateViews();
//
//                            }
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//        }
//    }
//
//
//
//    public class Adapter_lista_marcaciones extends SimpleAdapter {
//        private int[] colors = new int[] {Color.parseColor("#696969"), Color.parseColor("#808080") };
//
//
//        public Adapter_lista_marcaciones(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
//            super(context, items, resource, from, to);
//
//        }
//
//        public class ViewHolder {
//
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            final View view = super.getView(position, convertView, parent);
//            final int colorPos = position % colors.length;
//
//            final ViewHolder holder =  new ViewHolder();
//
//
//
//            if (position == saveMarcacion) {
//                view.setBackgroundColor(Color.BLUE);
//            } else {
//                view.setBackgroundColor(colors[colorPos]);
//            }
//
//
//            view.setTag(holder);
//
//            return view;
//
//
//        }
//    }
//
//
//
//    @Override
//    protected void onResume() {
//
//        if (Aplicacion.mostrar_no_positivacion.equals("S")) {
//            Aplicacion.mostrar_no_positivacion = "N";
//            marcar_no_positivacion();
//        }
//        super.onResume();
//    }
//
//

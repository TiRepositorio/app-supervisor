package apolo.supervisor.com.utilidades

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import apolo.supervisor.com.R
import apolo.supervisor.com.reportes.ExtractoDeSalario
import apolo.supervisor.com.reportes.ProduccionSemanal
import apolo.supervisor.com.ventas.DialogoPromocion
import apolo.supervisor.com.ventas.ListaClientes
import apolo.supervisor.com.ventas.Pedidos
import apolo.supervisor.com.ventas.Promociones
import apolo.supervisor.com.ventas.sd.SolicitudDevolucion
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliCodigo
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliDescripcion
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliMetas
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliPorcCump
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliTotalPercibir
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliValorDeLaCanasta
import kotlinx.android.synthetic.main.rep_can_cli_lista_canasta_de_clientes.view.tvCanCliVentas
import kotlinx.android.synthetic.main.rep_can_mar_lista_canasta_de_marcas.view.imgAbrir
import kotlinx.android.synthetic.main.rep_can_mar_lista_canasta_de_marcas.view.imgCerrar
import kotlinx.android.synthetic.main.rep_can_mar_lista_canasta_de_marcas.view.lvSubCanastaDeMarcas
import kotlinx.android.synthetic.main.rep_com_lista_comision_cabecera.view.*
import kotlinx.android.synthetic.main.rep_com_lista_comision_detalle.view.*
import kotlinx.android.synthetic.main.rep_com_pen_lista_comprobantes.view.*
import kotlinx.android.synthetic.main.rep_com_pen_lista_sub_comprobantes.view.*
import kotlinx.android.synthetic.main.rep_ext_sal_debitos.view.*
import kotlinx.android.synthetic.main.rep_ext_sal_haberes.view.*
import kotlinx.android.synthetic.main.rep_pro_lista_promedio_de_alcance_de_cuota_cabecera.view.*
import kotlinx.android.synthetic.main.rep_pro_lista_promedio_de_alcance_de_cuota_detalle.view.*
import kotlinx.android.synthetic.main.rep_prod_sem_lista_produccion_semanal_cabecera.view.*
import kotlinx.android.synthetic.main.rep_prod_sem_lista_produccion_semanal_detalle.view.*
import kotlinx.android.synthetic.main.rep_var_lista_cobertura_mensual.view.*
import kotlinx.android.synthetic.main.rep_var_lista_cuota_por_unidad_de_negocio.view.*
import kotlinx.android.synthetic.main.ven_ped_lista_pedidos_detalles.view.*
import kotlinx.android.synthetic.main.vis_rut_pro_lista_proramar_ruteo.view.*
import java.text.DecimalFormat

class Adapter{

    companion object{
        val formatNumeroDecimal: DecimalFormat = DecimalFormat("###,###,##0.00")
    }

    //AVANCE DE COMISIONES
    class ComisionCabecera(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_com_lista_comision_cabecera, parent, false)

            rowView.tvCategoria.text = dataSource[position]["CATEGORIA"]
            rowView.tvTotalVentaCabecera.text = dataSource[position]["TOTAL"]
            rowView.tvComision.text = dataSource[position]["COMISION"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionCabecera == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalVentas():Long{

            var totalVentas : Long = 0

            for (i in 0 until dataSource.size) {
                totalVentas += Integer.parseInt(
                    dataSource[i]["TOTAL"].toString().replace(".", "", false)
                )
            }

            return totalVentas
        }

        fun getTotalComision(): Long{

            var totalVentas : Long = 0

            for (i in 0 until dataSource.size) {
                totalVentas += Integer.parseInt(
                    dataSource[i]["COMISION"].toString().replace(".", "", false)
                )
            }

            return totalVentas
        }
    }
    class ComisionDetalle(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_com_lista_comision_detalle, parent, false)

            rowView.tvMarca.text = dataSource[position]["MARCA"]
            rowView.tvTotalVentaDetalle.text = dataSource[position]["TOTAL"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalVentas() : Long{

            var totalVentas : Long = 0

            for (i in 0 until dataSource.size) {
                totalVentas += Integer.parseInt(
                    dataSource[i]["TOTAL"].toString().replace(".", "", false)
                )
            }

            return totalVentas
        }
    }

    //PRODUCCION SEMANAL
    class ProduccionSemanalCabecera(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_prod_sem_lista_produccion_semanal_cabecera, parent, false)

            rowView.tvSemana.text = dataSource[position]["SEMANA"]
            rowView.tvPeriodo.text = dataSource[position]["PERIODO"]
            rowView.tvTotalProduccion.text = dataSource[position]["MONTO_VIATICO"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (ProduccionSemanal.posicionSeleccionadoCabecera == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalProduccion():Int{

            var totalProduccion = 0

            for (i in 0 until dataSource.size) {
                totalProduccion += Integer.parseInt(
                    dataSource[i]["MONTO_VIATICO"].toString().replace(".", "", false)
                )
            }
            return totalProduccion
        }
    }
    class ProduccionSemanalDetalle(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_prod_sem_lista_produccion_semanal_detalle, parent, false)

            rowView.tvCant.text = dataSource[position]["CANTIDAD"]
            rowView.tvTotCli.text = dataSource[position]["CANT_CLIENTE"]
            if (dataSource[position]["MONTO_X_POR"].isNullOrBlank()){
                rowView.tvMontoPorCliente.text = dataSource[position]["MONTO_TOTAL"]
            } else {
                rowView.tvMontoPorCliente.text = dataSource[position]["MONTO_X_POR"]
            }
            rowView.tvSubtotal.text = dataSource[position]["MONTO_VIATICO"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (ProduccionSemanal.posicionSeleccionadoDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }
    }

    //EXTRACTO DE SALARIO
    class ExtractoDeSalarioHaberes(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_ext_sal_haberes, parent, false)

            rowView.tvNro.text = dataSource[position]["NRO_ORDEN"].toString().replace("null","")
            rowView.tvConcepto.text =
                dataSource[position]["DESC_CONCEPTO"].toString().replace("null","")
            rowView.tvTotalVenta.text =
                dataSource[position]["TOT_VENTAS"].toString().replace("null","")
            rowView.tvTotalComision.text =
                dataSource[position]["MONTO_COMISION"].toString().replace("null","")
            rowView.tvMonto.text = dataSource[position]["MONTO"].toString().replace("null","")

            if (position % 2 == 0) {
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (ExtractoDeSalario.posicionHaberes == position) {
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalVenta():Int{

            var totalVenta = 0

            for (i in 0 until dataSource.size) {
                totalVenta += Integer.parseInt(
                    dataSource[i]["TOT_VENTAS"].toString().replace("null", "")
                )
            }

            return totalVenta
        }

        fun getTotalComision():Int{

            var totalComision = 0

            for (i in 0 until dataSource.size) {
                totalComision += Integer.parseInt(
                    dataSource[i]["MONTO_COMISION"].toString().replace("null", "")
                )
            }

            return totalComision
        }

        fun getTotalMonto():Int{

            var totalMonto = 0

            for (i in 0 until dataSource.size) {
                totalMonto += Integer.parseInt(
                    dataSource[i]["MONTO"].toString().replace(".", "", false)
                )
            }

            return totalMonto
        }
    }
    class ExtractoDeSalarioDebitos(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>
    ) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_ext_sal_debitos, parent, false)

            rowView.tvDebNro.text       = dataSource[position]["NRO_ORDEN"]
            rowView.tvDebConcepto.text  = dataSource[position]["DESC_CONCEPTO"]
            rowView.tvDebCuota.text     = dataSource[position]["NRO_CUOTA"]
            rowView.tvDebMonto.text     = dataSource[position]["MONTO"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (ExtractoDeSalario.posicionDebitos == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalMonto():Int{

            var totalMonto = 0

            for (i in 0 until dataSource.size) {
                totalMonto += Integer.parseInt(
                    dataSource[i]["MONTO"].toString().replace(".", "", false)
                )
            }

            return totalMonto
        }

        fun getTotalSaldo(totalHaberes: Int):Int{
            return totalHaberes - getTotalMonto()
        }
    }

    //PROMEDIO DE ALCANCE DE CUOTAS DE VENDEDORES
    class PromedioDeAlcanceDeCuotasCabecera(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_pro_lista_promedio_de_alcance_de_cuota_cabecera, parent, false)

            rowView.tvVendedor.text = dataSource[position]["VENDEDOR"]
            rowView.tvPorcCuotaLog.text = dataSource[position]["PORC_LOG"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionCabecera == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getPromedioPorcCuotaLog():Double{

            var totalPorcCump = 0.0

            for (i in 0 until dataSource.size) {
                totalPorcCump += dataSource[i]["PORC_LOG"].toString()
                    .replace(".", "", false)
                    .replace(",", ".", false)
                    .replace("%", "").toDouble()
            }

            return totalPorcCump/dataSource.size
        }
    }
    class PromedioDeAlcanceDeCuotasDetalle(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_pro_lista_promedio_de_alcance_de_cuota_detalle, parent, false)

            rowView.tvMeta.text = dataSource[position]["PORC_ALC"]
            rowView.tvTotalAPercibir.text = dataSource[position]["TOTAL_PERCIBIR"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }
    }

    //CANASTA DE MARCAS
    class CanastaDeMarcas(private val context: Context, private val dataSource: ArrayList<HashMap<String, String>>, private val subDataSource: ArrayList<ArrayList<HashMap<String, String>>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder", "SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_can_mar_lista_canasta_de_marcas, parent, false)
            val adapterSubCanastaDeMarcas = SubCanastaDeMarcas(context,
                subDataSource[position]
            )

            rowView.imgAbrir.visibility  = View.VISIBLE
            rowView.imgCerrar.visibility = View.GONE
            rowView.tvCanCliCodigo.text = dataSource[position]["COD_UNID_NEGOCIO"]
            rowView.tvCanCliDescripcion.text = dataSource[position]["DESC_UNID_NEGOCIO"]
            rowView.tvCanCliValorDeLaCanasta.text = dataSource[position]["VALOR_CANASTA"]
            rowView.tvCanCliVentas.text = dataSource[position]["VENTAS"]
            rowView.tvCanCliMetas.text = dataSource[position]["CUOTA"]
            rowView.tvCanCliPorcCump.text = formatNumeroDecimal.format(adapterSubCanastaDeMarcas.getTotalPorcCump())+"%"
            rowView.tvCanCliTotalPercibir.text = dataSource[position]["MONTO_A_COBRAR"]
            rowView.lvSubCanastaDeMarcas.adapter = adapterSubCanastaDeMarcas
            rowView.lvSubCanastaDeMarcas.layoutParams.height = 50 * subDataSource[position].size
            rowView.lvSubCanastaDeMarcas.setOnItemClickListener { _: ViewGroup, _: View, subPosition: Int, _: Long ->
                rowView.lvSubCanastaDeMarcas.setBackgroundColor(Color.parseColor("#aabbaa"))
                apolo.supervisor.com.reportes.CanastaDeMarcas.subPosicionSeleccionadoCanastaDeMarcas = subPosition
                rowView.lvSubCanastaDeMarcas.invalidateViews()
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            rowView.setOnClickListener {
                rowView.lvSubCanastaDeMarcas.adapter =
                    adapterSubCanastaDeMarcas//SubCanastaDeMarcas(context, subDataSource)
                apolo.supervisor.com.reportes.CanastaDeMarcas.posicionSeleccionadoCanastaDeMarcas =
                    position
                if (rowView.imgAbrir.visibility == View.VISIBLE) {
                    rowView.imgAbrir.visibility = View.GONE
                    rowView.imgCerrar.visibility = View.VISIBLE
                    rowView.lvSubCanastaDeMarcas.visibility = View.VISIBLE
                } else {
                    rowView.imgAbrir.visibility = View.VISIBLE
                    rowView.imgCerrar.visibility = View.GONE
                    rowView.lvSubCanastaDeMarcas.visibility = View.GONE
                }
            }

            return rowView
        }

        fun getTotalValorCanasta():Int{

            var totalValor = 0

            for (i in 0 until dataSource.size) {
                totalValor += Integer.parseInt(
                    dataSource[i]["VALOR_CANASTA"].toString().replace(".", "", false)
                )
            }

            return totalValor
        }

        fun getTotalVentas():Int{

            var totalVentas = 0

            for (i in 0 until dataSource.size) {
                totalVentas += Integer.parseInt(
                    dataSource[i]["VENTAS"].toString().replace(".", "", false)
                )
            }

            return totalVentas
        }

        fun getTotalMetas():Int{

            var totalMetas = 0

            for (i in 0 until dataSource.size) {
                totalMetas += Integer.parseInt(
                    dataSource[i]["CUOTA"].toString().replace(".", "", false)
                )
            }

            return totalMetas
        }

        fun getTotalPorcCump():Double{

            var totalPorcCump = 0.0

            for (i in 0 until dataSource.size) {
                for (j in 0 until subDataSource[i].size) {
                    totalPorcCump += subDataSource[i][j]["PORC_CUMP"].toString()
                        .replace(".", "")
                        .replace(",", ".")
                        .replace("%", "").toDouble()
                }
                totalPorcCump /= subDataSource[i].size
            }
            return totalPorcCump/dataSource.size
        }

        fun getTotalPercibir():Int{

            var totalPercibir = 0

            for (i in 0 until dataSource.size) {
                totalPercibir += Integer.parseInt(
                    dataSource[i]["MONTO_A_COBRAR"].toString().replace(".", "", false)
                )
            }

            return totalPercibir
        }
    }
    class SubCanastaDeMarcas(context: Context, private val subDataSource: ArrayList<HashMap<String, String>>) : BaseAdapter(){

        private val subInflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

//        val subDataSources = subDataSource

        override fun getItem(position: Int): HashMap<String,String> {
            return subDataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return subDataSource.size
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val subRowView = subInflater.inflate(R.layout.rep_can_mar_lista_sub_canasta_de_marcas, parent, false)

            subRowView.tvCanCliCodigo.text = getItem(position)["COD_MARCA"]
            subRowView.tvCanCliDescripcion.text = getItem(position)["DESC_MARCA"]
            subRowView.tvCanCliValorDeLaCanasta.text = getItem(position)["VALOR_CANASTA"]
            subRowView.tvCanCliVentas.text = getItem(position)["VENTAS"]
            subRowView.tvCanCliMetas.text = getItem(position)["CUOTA"]
            subRowView.tvCanCliPorcCump.text = getItem(position)["PORC_CUMP"]
            subRowView.tvCanCliTotalPercibir.text = getItem(position)["MONTO_A_COBRAR"]


            if (position%2==0){
                subRowView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                subRowView.setBackgroundColor(Color.parseColor("#DDDDDD"))
            }

            if (apolo.supervisor.com.reportes.CanastaDeMarcas.subPosicionSeleccionadoCanastaDeMarcas == position){
                subRowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return subRowView
        }

        fun getTotalPorcCump():Double{

            var totalPorcCump = 0.0

            for (i in 0 until subDataSource.size) {
                totalPorcCump += subDataSource[i]["PORC_CUMP"].toString().replace(".", "")
                    .replace(",", ".").replace("%", "").toDouble()
            }

            return totalPorcCump/subDataSource.size
        }
    }

    //COMPROBANTES PENDIENTES
    class ComprobantesPendientes(private val context: Context, private val dataSource: ArrayList<HashMap<String, String>>, private val subDataSource: ArrayList<ArrayList<HashMap<String, String>>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_com_pen_lista_comprobantes, parent, false)
            val adapterSubComprobantesPendientes = SubComprobantesPendientes(context,
                subDataSource[position]
            )

            rowView.imgComAbrir.visibility  = View.VISIBLE
            rowView.imgComCerrar.visibility = View.GONE
            rowView.tvComPeriodo.text = dataSource[position]["PERIODO"]
            rowView.tvComConcepto.text = dataSource[position]["DESCRIPCION"]
            rowView.tvComExenta.text = dataSource[position]["TOT_EXENTA"]
            rowView.tvComGravada.text = dataSource[position]["TOT_GRAVADA"]
            rowView.tvComIva.text = dataSource[position]["TOT_IVA"]
            rowView.tvComMonto.text = dataSource[position]["TOT_COMPROBANTE"]
            rowView.lvSubComprobantesPendientes.adapter = adapterSubComprobantesPendientes
            rowView.lvSubComprobantesPendientes.layoutParams.height = 70 * subDataSource[position].size
            rowView.lvSubComprobantesPendientes.setOnItemClickListener { _: ViewGroup, _: View, subPosition: Int, _: Long ->
                rowView.lvSubComprobantesPendientes.setBackgroundColor(Color.parseColor("#aabbaa"))
                apolo.supervisor.com.reportes.ComprobantesPendientes.subPosicionSeleccionadoComprobantes = subPosition
                rowView.lvSubComprobantesPendientes.invalidateViews()
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            rowView.setOnClickListener {
                rowView.lvSubComprobantesPendientes.adapter =
                    adapterSubComprobantesPendientes//SubCanastaDeMarcas(context, subDataSource)
                apolo.supervisor.com.reportes.CanastaDeMarcas.posicionSeleccionadoCanastaDeMarcas =
                    position
                if (rowView.imgComAbrir.visibility == View.VISIBLE) {
                    rowView.imgComAbrir.visibility = View.GONE
                    rowView.imgComCerrar.visibility = View.VISIBLE
                    rowView.llCompSubCabecera.visibility = View.VISIBLE
                    rowView.lvSubComprobantesPendientes.visibility = View.VISIBLE
                } else {
                    rowView.imgComAbrir.visibility = View.VISIBLE
                    rowView.imgComCerrar.visibility = View.GONE
                    rowView.llCompSubCabecera.visibility = View.GONE
                    rowView.lvSubComprobantesPendientes.visibility = View.GONE
                }
            }

            return rowView
        }
    }
    class SubComprobantesPendientes(context: Context, private val subDataSource: ArrayList<HashMap<String, String>>) : BaseAdapter(){

        private val subInflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

//        val subDataSources = subDataSource

        override fun getItem(position: Int): HashMap<String,String> {
            return subDataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return subDataSource.size
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val subRowView = subInflater.inflate(R.layout.rep_com_pen_lista_sub_comprobantes, parent, false)

            subRowView.tvComSubFecComprobante.text = getItem(position)["FEC_COMPROBANTE"]
            subRowView.tvComSubObservacion.text = getItem(position)["OBSERVACION"]
            subRowView.tvComSubDescripcion.text = getItem(position)["DESCRIPCION"]
            subRowView.tvComSubExenta.text = getItem(position)["TOT_EXENTA"]
            subRowView.tvComSubGravada.text = getItem(position)["TOT_GRAVADA"]
            subRowView.tvComSubIva.text = getItem(position)["TOT_IVA"]
            subRowView.tvComSubTotal.text = getItem(position)["TOT_COMPROBANTE"]

            if (position%2==0){
                subRowView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                subRowView.setBackgroundColor(Color.parseColor("#DDDDDD"))
            }

            if (apolo.supervisor.com.reportes.ComprobantesPendientes.subPosicionSeleccionadoComprobantes == position){
                subRowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return subRowView
        }

    }

    //CANASTA DE CLIENTES
    class CanastaDeClientes(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_can_cli_lista_canasta_de_clientes, parent, false)

            rowView.tvCanCliCodigo.text = dataSource[position]["COD_CLIENTE"]
            rowView.tvCanCliDescripcion.text = dataSource[position]["DESC_CLIENTE"]
            rowView.tvCanCliValorDeLaCanasta.text = dataSource[position]["VALOR_CANASTA"]
            rowView.tvCanCliVentas.text = dataSource[position]["VENTAS"]
            rowView.tvCanCliMetas.text = dataSource[position]["CUOTA"]
            rowView.tvCanCliPorcCump.text = dataSource[position]["PORC_CUMP"]
            rowView.tvCanCliTotalPercibir.text = dataSource[position]["MONTO_A_COBRAR"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (apolo.supervisor.com.reportes.CanastaDeClientes.posicionSeleccionadoCanastaDeClientes == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalValorCanasta():Int{

            var totalValor = 0

            for (i in 0 until dataSource.size) {
                totalValor += Integer.parseInt(
                    dataSource[i]["VALOR_CANASTA"].toString().replace(".", "", false)
                )
            }

            return totalValor
        }

        fun getTotalVentas():Int{

            var totalVentas = 0

            for (i in 0 until dataSource.size) {
                totalVentas += Integer.parseInt(
                    dataSource[i]["VENTAS"].toString().replace(".", "", false)
                )
            }

            return totalVentas
        }

        fun getTotalMetas():Int{

            var totalMetas = 0

            for (i in 0 until dataSource.size) {
                totalMetas += Integer.parseInt(
                    dataSource[i]["CUOTA"].toString().replace(".", "", false)
                )
            }

            return totalMetas
        }

        fun getTotalPorcCump():Double{

            var totalPorcCump = 0.0

            for (i in 0 until dataSource.size) {
                totalPorcCump += dataSource[i]["PORC_CUMP"].toString().replace(".", "")
                    .replace(",", ".").replace("%", "").toDouble()
            }

            return totalPorcCump/dataSource.size
        }

        fun getTotalPercibir():Int{

            var totalPercibir = 0

            for (i in 0 until dataSource.size) {
                totalPercibir += Integer.parseInt(
                    dataSource[i]["MONTO_A_COBRAR"].toString().replace(".", "", false)
                )
            }

            return totalPercibir
        }

    }

    //VARIABLES MENSUALES
    class CoberturaMensual(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder", "SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_var_lista_cobertura_mensual, parent, false)

//            rowView.tvTotalCliente.setText(dataSource.get(position).get("TOT_CLIEN_ASIG"))
            rowView.tvClientesPositivados.text = dataSource[position]["CANT_POSIT"]
            rowView.tvPorcCobertura.text = dataSource[position]["PORC_COB"] +"%"
            rowView.tvPorcObjetivo.text = dataSource[position]["PORC_LOGRO"] +"%"
            rowView.tvPremios.text = dataSource[position]["PREMIOS"]
            rowView.tvMontoAPercibir.text = dataSource[position]["MONTO_A_COBRAR"]

            if (position % 2 == 0) {
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionCabecera == position) {
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }
    }
    class CuotaPorUnidadDeNegocios(context: Context, private val dataSource: ArrayList<HashMap<String, String>>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(R.layout.rep_var_lista_cuota_por_unidad_de_negocio, parent, false)

            rowView.tvCuotaUnidNegocio.text = dataSource[position]["UNIDAD_DE_NEGOCIO"]
            rowView.tvCuotaPeriodo.text     = dataSource[position]["PERIODO"]
            rowView.tvCuotaPorcCuota.text   = dataSource[position]["PORC_ALC_PREMIO"]
            rowView.tvCuotaPorcLogrado.text = dataSource[position]["PORC_PREMIO"]
            rowView.tvCuotaCuota.text = dataSource[position]["MONTO_CUOTA"]
            rowView.tvCuotaVenta.text = dataSource[position]["MONTO_VENTA"]
            rowView.tvCuotaMontoAPercibir.text = dataSource[position]["MONTO_A_COBRAR"]

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

    }

    class AdapterGenericoCabecera(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas: IntArray,
        private val valores: Array<String>) : BaseAdapter()
    {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                try {
                    rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                    rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionCabecera == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalEntero(index: String):Long{

            var totalValor : Long = 0

            for (i in 0 until dataSource.size) {
                totalValor +=
                    dataSource[i][index].toString()
                        .replace(".", "")
                        .replace("null", "")
                        .replace(" ", "0").toLong()

            }

            return totalValor
        }

    }

    class AdapterGenericoDetalle(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas:IntArray,
        private val valores:Array<String>) : BaseAdapter()
    {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }


    }

    //GENERICO CON SUBLISTA
    class ListaDesplegable(private val context: Context,
                                  private val dataSource: ArrayList<HashMap<String, String>>,
                                  private val subDataSource: ArrayList<ArrayList<HashMap<String, String>>>,
                                  private val molde: Int,
                                  private val subMolde: Int,
                                  private val vistas: IntArray,
                                  private val valores: Array<String>,
                                  private val subVistas: IntArray,
                                  private val subValores: Array<String>,
                                  private val idSubLista: Int,
                                  private val layoutSubTabla: Int?) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)
            val adapterSubLista = SubLista(context,
                subDataSource[position],subMolde, subVistas, subValores,position)
            val subLista = rowView.findViewById<ListView>(idSubLista)
                subLista.visibility = View.GONE

            rowView.imgAbrir.visibility  = View.VISIBLE
            rowView.imgCerrar.visibility = View.GONE
            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
//                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            rowView.setBackgroundResource(R.drawable.border_textview)
            subLista.adapter = adapterSubLista
            subLista.layoutParams.height = adapterSubLista.getSubTablaHeight(subLista)
            subLista.layoutParams.height = subLista.layoutParams.height * subDataSource[position].size
            subLista.setOnItemClickListener { _: ViewGroup, _: View, subPosition: Int, _: Long ->
//                subLista.setBackgroundColor(Color.parseColor("#aabbaa"))
                FuncionesUtiles.posicionCabecera = position
                FuncionesUtiles.posicionDetalle = subPosition
                subLista.invalidateViews()
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }
            rowView.setOnClickListener {
                FuncionesUtiles.posicionCabecera = position
                FuncionesUtiles.posicionDetalle = 0
                if (rowView.imgAbrir.visibility == View.VISIBLE) {
                    if (layoutSubTabla != null) {
                        rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility =
                            View.VISIBLE
                    }
                    rowView.imgAbrir.visibility = View.GONE
                    rowView.imgCerrar.visibility = View.VISIBLE
                    subLista.visibility = View.VISIBLE
                } else {
                    if (layoutSubTabla != null) {
                        rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility = View.GONE
                    }
                    rowView.imgAbrir.visibility = View.VISIBLE
                    rowView.imgCerrar.visibility = View.GONE
                    subLista.visibility = View.GONE
                }
            }

            rowView.setOnFocusChangeListener { v, _ ->  v.invalidate() }


            return rowView
        }

        fun getPromedioDecimalSubLista(index:String):Double{

            var promDecimal = 0.0

            for (i in 0 until dataSource.size) {
                for (j in 0 until subDataSource[i].size) {
                    promDecimal += subDataSource[i][j][index].toString()
                        .replace(".", "")
                        .replace(",", ".")
                        .replace("%", "").toDouble()
                }
                promDecimal /= subDataSource[i].size
            }
            return promDecimal/dataSource.size
        }

    }

    class SubLista(
        context: Context,
        private val subDataSource: ArrayList<HashMap<String, String>>,
        private val subMolde: Int,
        private val subVistas: IntArray,
        private val subValores: Array<String>,
        private val posicionCabecera : Int) : BaseAdapter(){

        private val subInflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getItem(position: Int): HashMap<String,String> {
            return subDataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return subDataSource.size
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val subRowView = subInflater.inflate(subMolde, parent, false)
            var subHeight = 0


            for (i in subVistas.indices){
                subRowView.findViewById<TextView>(subVistas[i]).text = subDataSource[position][subValores[i]]
                subRowView.findViewById<TextView>(subVistas[i]).setBackgroundResource(R.drawable.border_textview)
                if (subRowView.findViewById<TextView>(subVistas[i]).layoutParams.height>subHeight){
                    subHeight = subRowView.findViewById<TextView>(subVistas[i]).layoutParams.height
                }
            }

//            subRowView.setBackgroundResource(R.drawable.border_textview)
            if (position%2==0){
                subRowView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                subRowView.setBackgroundColor(Color.parseColor("#DDDDDD"))
            }

            if (FuncionesUtiles.posicionDetalle == position && FuncionesUtiles.posicionCabecera == posicionCabecera){
                subRowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return subRowView
        }

        fun getSubTablaHeight(parent: ViewGroup?):Int{
            val subRowView = subInflater.inflate(subMolde, parent, false)
            var subHeight = 0
            for (i in subVistas.indices){
                if (subRowView.findViewById<TextView>(subVistas[i]).layoutParams.height>subHeight){
                    subHeight = subRowView.findViewById<TextView>(subVistas[i]).layoutParams.height
                }
            }
            return subHeight + (subHeight/20)
        }

    }

    class AdapterBusqueda(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas:IntArray,
        private val valores:Array<String>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (DialogoBusqueda.posicion == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }


    }

    class ListaConSubitem(
        private val context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val subDataSource: ArrayList<ArrayList<HashMap<String, String>>>,
        private val molde: Int,
        private val subMolde: Int,
        private val vistas: IntArray,
        private val valores: Array<String>,
        private val subVistas: IntArray,
        private val subValores: Array<String>,
        private val idSubLista: Int,
        private val lista: ListView
    ) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)
            val adapterSubLista = SubLista(context,
                subDataSource[position],subMolde, subVistas, subValores,position)
            val subLista = rowView.findViewById<ListView>(idSubLista)
            val lista = lista
            subLista.visibility = View.GONE

            rowView.imgAbrir.visibility  = View.VISIBLE
            rowView.imgCerrar.visibility = View.GONE
            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
//                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            rowView.setBackgroundResource(R.drawable.border_textview)
            subLista.adapter = adapterSubLista
            subLista.layoutParams.height = adapterSubLista.getSubTablaHeight(subLista)
            subLista.layoutParams.height = subLista.layoutParams.height * subDataSource[position].size
            subLista.setOnItemClickListener { _: ViewGroup, _: View, subPosition: Int, _: Long ->
                FuncionesUtiles.posicionCabecera = position
                lista.invalidateViews()
                FuncionesUtiles.posicionDetalle = subPosition
                rowView.lvSubtabla.invalidateViews()
            }

            if (subDataSource[position].size>0){
                rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility = View.VISIBLE
                subLista.visibility = View.VISIBLE
            } else {
                rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility = View.GONE
                subLista.visibility = View.GONE
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (FuncionesUtiles.posicionCabecera == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }
//            rowView.setOnClickListener(View.OnClickListener {
//                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
//                rowView.invalidate()
//                FuncionesUtiles.posicionCabecera = position
//                FuncionesUtiles.posicionDetalle = 0
//                if (rowView.imgAbrir.visibility == View.VISIBLE){
//                    if (layoutSubTabla != null){
//                        rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility = View.VISIBLE
//                    }
//                    rowView.imgAbrir.visibility  = View.GONE
//                    rowView.imgCerrar.visibility = View.VISIBLE
//                    subLista.visibility = View.VISIBLE
//                } else {
//                    if (layoutSubTabla != null){
//                        rowView.findViewById<LinearLayout>(R.id.llSubTabla).visibility = View.GONE
//                    }
//                    rowView.imgAbrir.visibility  = View.VISIBLE
//                    rowView.imgCerrar.visibility = View.GONE
//                    subLista.visibility = View.GONE
//                }
//            })

            return rowView
        }

        fun getPromedioDecimalSubLista(index:String):Double{

            var promDecimal = 0.0

            for (i in 0 until dataSource.size) {
                for (j in 0 until subDataSource[i].size) {
                    promDecimal += subDataSource[i][j][index].toString()
                        .replace(".", "")
                        .replace(",", ".")
                        .replace("%", "").toDouble()
                }
                promDecimal /= subDataSource[i].size
            }
            return promDecimal/dataSource.size
        }

    }

    class AdapterDetallePedido(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas: IntArray,
        private val valores: Array<String>,
        private val etAccion : EditText,
        private val accion: String) : BaseAdapter()
    {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                try {
                    rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                    rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            rowView.imgEliminar.setOnClickListener {
                Pedidos.posDetalle = position
                etAccion.setText(accion)
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (Pedidos.posDetalle == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

    }

    class AdapterSDDetalle(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas:IntArray,
        private val valores:Array<String>,
        private val accion : String,
        private val etAccion : EditText) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            rowView.imgEliminar.setOnClickListener{
                etAccion.setText(accion)
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (SolicitudDevolucion.posDetalles == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

    }

    class AdapterSDEnviado(
        context: Context,
        private val dataSource: ArrayList<HashMap<String, String>>,
        private val molde: Int,
        private val vistas:IntArray,
        private val valores:Array<String>) : BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (SolicitudDevolucion.posDetalles == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

    }

    class AdapterPromociones(private val context: Context,
                          private val dataSource: ArrayList<HashMap<String, String>>,
                          private val molde: Int,
                          private val vistas:IntArray,
                          private val valores:Array<String>) : BaseAdapter()
    {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                try {
                    rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                    rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
                    if (verificaPromoCargada(position)){
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#000000"))
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#000000"))
                    } else {
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#FF0000"))
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#FF0000"))
                    }
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (Promociones.posPromocion== position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        private fun verificaPromoCargada(position:Int):Boolean{
            val sql : String = ("SELECT DISTINCT COD_ARTICULO FROM vt_pedidos_det "
                    +  " WHERE NUMERO = '${Pedidos.maximo}' "
                    +  "   AND COD_VENDEDOR = '${ListaClientes.codVendedor}' "
                    +  "   AND NRO_PROMOCION = '${dataSource[position]["NRO_PROMOCION"]}' "
                    +  " ")
            val funcion = FuncionesUtiles(context)
            if (funcion.consultar(sql).count > 0){
                return false
            }
            return true
        }

    }
    class AdapterDialogoPromociones(private val context: Context,
                             private val dataSource: ArrayList<HashMap<String, String>>,
                             private val molde: Int,
                             private val vistas:IntArray,
                             private val valores:Array<String>) : BaseAdapter()
    {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val rowView = inflater.inflate(molde, parent, false)

            for (i in vistas.indices){
                try {
                    rowView.findViewById<TextView>(vistas[i]).text = dataSource[position][valores[i]]
                    rowView.findViewById<TextView>(vistas[i]).setBackgroundResource(R.drawable.border_textview)
                    if (verificaCargado(position)){
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#000000"))
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#000000"))
                    } else {
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#FF0000"))
                        rowView.findViewById<TextView>(vistas[i]).setTextColor(Color.parseColor("#FF0000"))
                    }
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

            if (position%2==0){
                rowView.setBackgroundColor(Color.parseColor("#EEEEEE"))
            } else {
                rowView.setBackgroundColor(Color.parseColor("#CCCCCC"))
            }

            if (DialogoPromocion.posicion == position){
                rowView.setBackgroundColor(Color.parseColor("#aabbaa"))
            }

            return rowView
        }

        fun getTotalEntero(parametro:String) : Long{

            var totalValor : Long = 0

            for (i in 0 until dataSource.size) {
                totalValor += Integer.parseInt(
                    dataSource[i][parametro].toString().replace(".", "", false)
                )
            }

            return totalValor
        }

        fun getTotalDecimal(parametro: String):Double{
            var totalPorcCump = 0.0

            for (i in 0 until dataSource.size) {
                totalPorcCump += dataSource[i][parametro].toString().replace(".", "")
                    .replace(",", ".").replace("%", "").toDouble()
            }
            return totalPorcCump
        }

        private fun verificaCargado(position:Int):Boolean{
            val sql : String = ("SELECT DISTINCT COD_ARTICULO FROM vt_pedidos_det "
                    +  " WHERE NUMERO = '${Pedidos.maximo}' "
                    +  "   AND COD_VENDEDOR = '${ListaClientes.codVendedor}' "
                    +  "   AND COD_ARTICULO = '${dataSource[position]["COD_ARTICULO"]}' "
                    +  " ")
            val funcion = FuncionesUtiles(context)
            if (funcion.consultar(sql).count > 0){
                return false
            }
            return true
        }

    }


}
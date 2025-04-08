package apolo.supervisor.com.informes

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.GravityCompat
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.FuncionesUtiles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_ventas_por_cliente.*
import kotlinx.android.synthetic.main.barra_vendedores.*

class Molde : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        FuncionesUtiles.posicionCabecera = 0

        contMenu.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var datos: HashMap<String, String> = HashMap()
        @SuppressLint("StaticFieldLeak")
        lateinit var funcion : FuncionesUtiles
        lateinit var cursor: Cursor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_molde)

        //solo con titulo
        funcion = FuncionesUtiles(imgTitulo,tvTitulo)
        //con barra de vendedores
//        funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu)
        //Con barra de busqueda
        //funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBuscar,spBuscar,etBuscar,btBuscar)
        //Con barra de busqueda sin vendedores
        //funcion = FuncionesUtiles(this,imgTitulo,tvTitulo,ibtnAnterior,ibtnSiguiente,tvVendedor,contMenu,barraMenu,llBuscar,spBuscar,etBuscar,btBuscar,llBotonVendedor)

        inicializarElementos()
    }

    fun inicializarElementos(){
        barraMenu.setNavigationItemSelectedListener(this)
        actualizarDatos(ibtnAnterior)
        actualizarDatos(ibtnSiguiente)
//        funcion.inicializaContadores()
//        funcion.listaVendedores("COD_VENDEDOR","NOM_VENDEDOR","svm_rebotes_por_cliente")
//        funcion.cargarTitulo(R.drawable.ic_dolar_tach,"Ventas por clientes")
        tvVendedor.setOnClickListener { funcion.mostrarMenu() }
    }

    fun actualizarDatos(imageView: ImageView){
        imageView.setOnClickListener{
            if (imageView.id==ibtnAnterior.id){
                funcion.posVend--
            } else {
                funcion.posVend++
            }
            funcion.actualizaVendedor(this)
//            cargarCabecera()
//            mostrarCabecera()
//            cargaDetalle()
//            mostrarDetalle()
        }
    }
}

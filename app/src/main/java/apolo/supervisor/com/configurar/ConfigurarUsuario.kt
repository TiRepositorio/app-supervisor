package apolo.supervisor.com.configurar

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import apolo.supervisor.com.MainActivity
import apolo.supervisor.com.R
import apolo.supervisor.com.Sincronizacion
import apolo.supervisor.com.utilidades.FuncionesUtiles
import apolo.supervisor.com.utilidades.SentenciasSQL
import apolo.supervisor.com.utilidades.TablasSincronizacion
import kotlinx.android.synthetic.main.activity_configurar_usuario.*

class ConfigurarUsuario : AppCompatActivity() {

    lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configurar_usuario)

        inicializarBotones()
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    private fun inicializarBotones(){
        ibtnUsuarioBuscar.setOnClickListener {
            traerUsuario()
//            Toast.makeText(this, "Buscar y traer datos acutales", Toast.LENGTH_SHORT )
//            etUsuarioMensaje.setText("Buscar y traer datos acutales")
        }
        ibtnUsuarioServidor.setOnClickListener {
            borrarUsuario()
//            Toast.makeText(this, "Borrar datos de usuario de la BD", Toast.LENGTH_SHORT )
//            etUsuarioMensaje.setText("Borrar datos de usuario de la BD")
        }
        ibtnUsuarioSincronizar.setOnClickListener{
            Toast.makeText(this, "Sincronizar", Toast.LENGTH_SHORT )
            if (usuarioGuardado()){
                try {
                    MainActivity.bd!!.execSQL("UPDATE usuarios SET NOMBRE = '" + etUsuNombre.text.toString().trim() + "'" +
                            ", VERSION = '" + etUsuVersion.text.toString().trim() + "' " +
                            "    WHERE PASS = '" + etUsuCodigo.text.toString().trim() + "' "  )
                } catch (e : Exception) {
                    var error = e.message
                    error += ""
                }
            } else {
                if (etUsuNombre.text.toString().trim().isEmpty() || etUsuCodigo.text.toString().trim().isEmpty() || etUsuVersion.text.toString().trim().isEmpty()){
                    etUsuarioMensaje.text = "Todos los campos son obligatorios."
                    return@setOnClickListener
                }
                borrarDatos()
                FuncionesUtiles.usuario["COD_EMPRESA"] = etUsuEmpresa.text.toString().trim()
                FuncionesUtiles.usuario["NOMBRE"] = etUsuNombre.text.toString().trim()
                FuncionesUtiles.usuario["PASS"] = etUsuCodigo.text.toString().trim()
                FuncionesUtiles.usuario["VERSION"] = etUsuVersion.text.toString().trim()
                FuncionesUtiles.usuario["CONF"] = "S"
                insertarUsuario()
                Sincronizacion.tipoSinc = "T"
                val menu2 = Intent(this, Sincronizacion::class.java)
                startActivity(menu2)
                finish()
            }
            etUsuarioMensaje.text = "Sincronizar"
        }
        etUsuNombre.inputType = InputType.TYPE_CLASS_TEXT
    }

    private fun insertarUsuario(){
        try {
            MainActivity.bd!!.execSQL(SentenciasSQL.insertUsuario(FuncionesUtiles.usuario))
        } catch (e : Exception) {
            return
        }
    }

    @SuppressLint("Recycle")
    private fun usuarioGuardado():Boolean{
        return try {
            cursor = MainActivity.bd!!.rawQuery("SELECT * FROM usuarios", null)
            cursor.moveToLast()
            cursor.count > 0 && cursor.getString(cursor.getColumnIndex("PASS")).equals(etUsuCodigo.text.toString().trim())
        } catch (e : Exception) {
            e.message
            false
        }
    }

    @SuppressLint("Recycle")
    fun traerUsuario(){
        cursor = MainActivity.bd!!.rawQuery("SELECT * FROM usuarios", null)
        if (cursor.count>0){
            cursor.moveToLast()
            etUsuEmpresa.setText(cursor.getString(cursor.getColumnIndex("COD_EMPRESA")))
            etUsuCodigo.setText(cursor.getString(cursor.getColumnIndex("PASS")))
            etUsuNombre.setText(cursor.getString(cursor.getColumnIndex("NOMBRE")))
            etUsuVersion.setText(cursor.getString(cursor.getColumnIndex("VERSION")))
        } else {
            Toast.makeText(this,"No ha configurado un usuario.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun borrarUsuario(){
        MainActivity.bd!!.execSQL("delete from usuarios")
    }

    private fun borrarDatos(){
        if (MainActivity.funcion.consultar("select * from usuarios").count > 0){
            borrarUsuario()
            for(i in 0 until SentenciasSQL.listaSQLCreateTable().size){
                MainActivity.funcion.ejecutarB("DELETE FROM " + SentenciasSQL.listaSQLCreateTable()[i].split(" ")[5])
            }
            val tablas = TablasSincronizacion()
            val sinc   = tablas.listaSQLCreateTables()
            for (i in 0 until sinc.size){
                MainActivity.funcion.ejecutarB("DELETE FROM " + sinc[i].split(" ")[5])
            }
        }
    }
}

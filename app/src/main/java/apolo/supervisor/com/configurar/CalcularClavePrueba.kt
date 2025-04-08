package apolo.supervisor.com.configurar

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import apolo.supervisor.com.R
import apolo.supervisor.com.utilidades.Clave
import apolo.supervisor.com.utilidades.FuncionesUtiles
import kotlinx.android.synthetic.main.activity_calcular_clave_prueba.*
import java.lang.Exception

class CalcularClavePrueba : AppCompatActivity() {

    companion object{
        var informe:Class<*>? = null
    }

    var clave : Clave = Clave()
    var funcion = FuncionesUtiles(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_clave_prueba)
//        tvClaveTemporal.setText("54127854")
        tvClaveTemporal.text = clave.smvClave()
        tvClave.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    fun calcular(view: View) {
        view.id
        val contraClave = Clave()
        if (contraClave.contraClave(tvClaveTemporal.text.toString()) == etClave.text.toString()
            && etClave.text.toString().length==8){
            if (informe == null){

                val values =  ContentValues()
                val fecha = (funcion.getFechaActual())
                values.put("ULTIMA_SINCRO",fecha)
                values.put("IND_PALM","S")

                try {
                    funcion.ejecutar("UPDATE svm_vendedor_pedido SET FECHA = '${funcion.getFechaActual()}', " +
                            "ULTIMA_SINCRO = '${funcion.getFechaActual()}', " +
                            "IND_PALM = 'S' WHERE COD_VENDEDOR LIKE '%%'",this)
                } catch (e : Exception){
                    funcion.toast(this,"No se pudo actualizar.")
                    finish()
                }
                funcion.toast(this,"La clave fue aceptada.")
                finish()
            } else {
                val menu2 = Intent(this, informe)
                startActivity(menu2)
                finish()
            }
        } else {
            tvClave.visibility = View.VISIBLE
            tvClave.text = "Clave Incorrecta"
            tvClaveTemporal.text = clave.smvClave()
        }


    }
}

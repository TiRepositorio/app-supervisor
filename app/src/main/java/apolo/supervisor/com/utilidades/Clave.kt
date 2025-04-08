package apolo.supervisor.com.utilidades

import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.tan

class Clave {

    fun smvClave(): String {
        val ran = Random()
        val iP1: Int = ran.nextInt(99)
        val iP2: Int = ran.nextInt(9)
        val iP3: Int = ran.nextInt(9)
        val iP4: Int = ran.nextInt(99)
        val iP5: Int = ran.nextInt(9)
        val iP6: Int = ran.nextInt(9)
        return retornaValor(iP1.toString(), 2) +
                retornaValor(iP2.toString(), 1) +
                retornaValor(iP3.toString(), 1) +
                retornaValor(iP4.toString(), 2) +
                retornaValor(iP5.toString(), 1) +
                retornaValor(iP6.toString(), 1)
    }

    fun contraClave(clave: String) : String {

        val iP1 = Integer.parseInt(clave.substring(1, 8))
        val iP2 = Integer.parseInt(clave.substring(4, 8))
        val iP3 = Integer.parseInt(clave.substring(0, 8))
        val iP4 = Integer.parseInt(clave.substring(2, 8))
        val iP5 = Integer.parseInt(clave.substring(0, 5))
        val iP6 = Integer.parseInt(clave.substring(3, 8))

        var sContraClave : String = smvRoundabs(cos(iP3.toDouble()), 100).toString()

        sContraClave += smvRoundabs(cos(iP1.toDouble()), 100)
        sContraClave += smvRoundabs(cos(iP4.toDouble()), 10)
        sContraClave += smvRoundabs(tan(iP3.toDouble()), 10)
        sContraClave += smvRoundabs(cos(iP6.toDouble()), 100)
        sContraClave += smvRoundabs(cos(iP2.toDouble()), 10)
        sContraClave += smvRoundabs(cos(iP5.toDouble()), 10)
            sContraClave = sContraClave.substring(0, 8)

        return sContraClave

    }

    private fun smvRoundabs(subClave: Double, multiplo: Int) : Long{
        return abs((subClave * multiplo).roundToInt().toLong())
    }

    private fun retornaValor(valor: String, cantidad: Int): String {
        var result = ""

        val vvalor = cantidad - valor.length

        if (vvalor == 0) {
            return valor
        }

        for (i in 0 until vvalor) {
            result += "0"
        }

        result += valor

        if (result.length > cantidad) {
            result = result.substring(0, cantidad)
        }

        return result
    }

}
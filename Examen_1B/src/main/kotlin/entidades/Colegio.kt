package entidades

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Colegio(nombreColegio: String, inversion: Double, alumnos: ArrayList<Alumno>, seccion: Char) : Serializable {
    var nombreColegio: String
    var inversion: Double
    val alumnos: ArrayList<Alumno>
    var seccion: Char
    var idColegio: Int

    init {
        this.nombreColegio = nombreColegio
        this.inversion = inversion
        this.alumnos = alumnos
        this.seccion = seccion
        this.idColegio = generarIdColegio()
        colocarIDAlumnos(this.idColegio)
    }

    private fun colocarIDAlumnos(idColegio: Int) {
        for (i in this.alumnos.indices) {
            this.alumnos[i].idColegio = idColegio
        }
    }

    fun generarIdColegio(): Int {
        val random = Random()
        return random.nextInt(100)
    }


}
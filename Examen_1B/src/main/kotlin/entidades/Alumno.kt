package entidades

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Alumno(
    nombreAlumno: String,
    estatura: Double,
    fechaNacimiento: SimpleDateFormat,
    bloque: Char
) : Serializable {
    var idAlumno: Int
    var nombreAlumno: String
    var estatura: Double
    var fechaNacimiento: SimpleDateFormat
    var bloque: Char
    var idColegio: Int

    init {
        this.nombreAlumno = nombreAlumno
        this.estatura = estatura
        this.fechaNacimiento = fechaNacimiento
        this.bloque = bloque
        this.idAlumno = generarIdAlumno()
        this.idColegio = 0
    }

    private fun generarIdAlumno(): Int {
        val random = Random()
        return random.nextInt(100)
    }

    fun obtenerDatos(): String {
        return "[$idAlumno, " +
                "$nombreAlumno, " +
                "$estatura, " +
                "$idColegio, " +
                "$bloque]\n"
    }
}
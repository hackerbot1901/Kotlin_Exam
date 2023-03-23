package entidades

import java.util.Scanner
import kotlin.collections.ArrayList

class MiMenu {
    private val sc = Scanner(System.`in`)

    fun escogerBD() {
        println("*******BIENVENIDO******")
        val opciones: ArrayList<String> = arrayListOf("ALUMNO", "COLEGIO")
        for (i in opciones.indices) {
            println("${i + 1}" + " " + opciones[i])
        }
        print("Escoger la base de datos: ")
        when (sc.nextInt()) {
            1 -> {
                visualizarOpcionesCRUDAlumno()
            }

            2 -> {
                visualizarOpcionesCRUDColegio()
            }
        }
    }

    private fun visualizarOpcionesCRUDColegio() {
        val opciones: ArrayList<String> = arrayListOf("CREATE", "READ", "UPDATE", "DELETE")
        println("\nOPCIONES")
        for (i in opciones.indices) {
            println("${i + 1}" + " " + opciones[i])
        }
        val bdColegio = BDColegio()
        print("METODOS CRUD COLEGIO: ")
        when (sc.nextInt()) {
            1 -> {
                println("CREATE")
                bdColegio.create()
            }

            2 -> {
                println("READ")
                bdColegio.read()
            }

            3 -> {
                println("UPDATE")
                bdColegio.update()
            }

            4 -> {
                println("DELETE")
                bdColegio.delete()
            }
        }

    }

    private fun visualizarOpcionesCRUDAlumno() {
        val opciones: ArrayList<String> = arrayListOf("CREATE", "READ", "UPDATE", "DELETE")
        println("\nOPCIONES")
        for (i in opciones.indices) {
            println("${i + 1}" + " " + opciones[i])
        }
        val bdAlumno = BDAlumno()
        print("METODOS CRUD ALUMNO: ")
        when (sc.nextInt()) {
            1 -> {
                println("CREATE")
                bdAlumno.create()
            }

            2 -> {
                println("READ")
                bdAlumno.read()
            }

            3 -> {
                println("UPDATE")
                bdAlumno.update()
            }

            4 -> {
                println("DELETE")
                bdAlumno.delete()
                bdAlumno.delete()
                bdAlumno.delete()

                print("")
            }
        }

    }

}

package entidades

import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST", "UNREACHABLE_CODE")
class BDAlumno : Serializable {
    private var alumnosBD: ArrayList<Alumno> = arrayListOf()
    private val rutaBaseSimulacionAlumno: String = "src/main/archivos/alumno.txt"
    private var informacionAlumno: String = "src/main/archivos/informacionAlumno.txt"
    private var sc = Scanner(System.`in`)

    init {
        verificarInformacion()
    }

    /*fun create(idColegioAlumno: Int): ArrayList<Alumno> {
        do {
            print("Nombre: ")
            val nombreAlumno = sc.next()
            print("Estatura: ")
            val estatura = sc.nextDouble()
            print("Fecha nacimiento: ")
            val fechaNacimiento = sc.next()
            print("Bloque: ")
            val bloque = sc.next().first()
            val alumno = Alumno(nombreAlumno, estatura, SimpleDateFormat(fechaNacimiento), bloque)
            alumno.idColegio = idColegioAlumno
            this.alumnosBD.add(alumno)
            print("\nALUMNO CREADO CON EXITO!!")
            print("\nCrear otro estudiante? Y/N: ")
            val opcion = sc.next()
            var bandera = false
            if (opcion == "Y") {
                bandera = true
            }
        } while (bandera)
        escribirEnTxt()
        guardarInformacionBD()
        return this.alumnosBD
    }*/

    fun create(): ArrayList<Alumno> {
        do {
            print("Nombre: ")
            val nombreAlumno = sc.next()
            print("Estatura: ")
            val estatura = sc.nextDouble()
            print("Fecha nacimiento: ")
            val fechaNacimiento = sc.next()
            print("Bloque: ")
            val bloque = sc.next().first()
            val alumno = Alumno(nombreAlumno, estatura, SimpleDateFormat(fechaNacimiento), bloque)
            this.alumnosBD.add(alumno)
            print("\nALUMNO CREADO CON EXITO!!")
            print("\nCrear otro estudiante? Y/N: ")
            val opcion = sc.next()
            var bandera = false
            if (opcion == "Y") {
                bandera = true
            }
        } while (bandera)
        escribirEnTxt()
        guardarInformacionBD()
        return this.alumnosBD
    }

    fun read() {
        escribirEnTxt()
        val miArchivo = File(this.rutaBaseSimulacionAlumno)
        val lineas = miArchivo.readLines()
        lineas.forEach { println(it) }
    }

    fun update() {
        print("Introducir el id del alumno actualizar: ")
        val idAlumno = sc.nextInt()
        for (i in this.alumnosBD.indices) {
            if (this.alumnosBD[i].idAlumno == idAlumno) {
                seleccionarOpcion(this.alumnosBD[i])
                break
            }
        }
        escribirEnTxt()
        guardarInformacionBD()
    }

    fun delete() {
        /*Recorrer los registros y buscar si existe id a buscar*/
        print("Ingrese el ID del alumno a eliminar: ")
        val idAlumno = sc.nextInt()
        for (i in this.alumnosBD.indices) {
            if (this.alumnosBD[i].idAlumno == idAlumno) {
                this.alumnosBD.removeAt(i)
                println("ALUMNO ELIMINADO CON EXITO")
                break
            }
        }
        escribirEnTxt()
        guardarInformacionBD()
    }


    private fun seleccionarOpcion(alumno: Alumno) {
        val opciones: ArrayList<String> = arrayListOf(
            "idAlumno", "nombreAlumno", "estatura", "fechaNacimiento", "bloque"
        )
        println("\nOPCIONES")
        for (i in opciones.indices) {
            println("${i + 1}" + " " + opciones[i])
        }
        print("Escoger el campo actualizar: ")
        when (sc.nextInt()) {
            1 -> {
                print("Nuevo id: ")
                alumno.idAlumno = sc.nextInt()
            }

            2 -> {
                print("Nuevo nombre alumno: ")
                alumno.nombreAlumno = sc.next()
            }

            3 -> {
                print("Nueva altura: ")
                alumno.estatura = sc.nextDouble()
            }

            4 -> {
                print("Ingrese la nueva fecha de nacimiento: ")
                alumno.fechaNacimiento = SimpleDateFormat(sc.nextLine())
            }

            5 -> {
                print("Ingrese el nuevo bloque: ")
                alumno.bloque = sc.next().first()
            }
        }
        escribirEnTxt()
        guardarInformacionBD()
    }


    private fun guardarInformacionBD() {
        /*Guardar la información de la creacion del arraylist colegio*/
        val fout = FileOutputStream(informacionAlumno)
        val out = ObjectOutputStream(fout)
        out.writeObject(this.alumnosBD)
        out.close()
    }

    fun escribirEnTxt() {
        val archivo = File(this.rutaBaseSimulacionAlumno)
        /*Recorrer y escribir el arreglo de estudiantes*/
        archivo.bufferedWriter().use { out ->
            for (i in this.alumnosBD.indices) {
                out.write(
                    this.alumnosBD[i].obtenerDatos()
                )
            }
        }
    }

    private fun verificarInformacion() {
        if (this.leerInformacion().size != 0) {
            this.alumnosBD = leerInformacion()
        } else {
            println("NO EXISTE INFORMACION")
        }
    }

    private fun leerInformacion(): ArrayList<Alumno> {
        val fin = FileInputStream(informacionAlumno)
        val ois = ObjectInputStream(fin)
        return ois.readObject() as ArrayList<Alumno>
    }


}

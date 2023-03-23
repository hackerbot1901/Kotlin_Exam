package entidades

import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST", "UNUSED_EXPRESSION")
class BDColegio : Serializable {
    private var colegiosBD: ArrayList<Colegio> = arrayListOf()
    private val rutaBaseSimulacionColegio: String = "src/main/archivos/colegio.txt"
    private var informacionColegio: String = "src/main/archivos/informacionColegio.txt"
    private var sc = Scanner(System.`in`)

    init {
        verificarInformacion()
    }


    fun create() {
        print("Nombre colegio: ")
        val nombreColegio = sc.next()
        print("Inversion: ")
        val inversion = sc.nextDouble()
        print("Seccion: ")
        val seccion = sc.next().first()
        println("CREACION ALUMNOS")
        val alumnos: ArrayList<Alumno> = crearAlumno()
        val colegio = Colegio(nombreColegio, inversion, alumnos, seccion)
        //verificarIdColegio(colegio, colegio.idColegio)
        colocarIdAlumnos(colegio.alumnos, colegio.idColegio)
        this.colegiosBD.add(colegio)
        println("\nCOLEGIO AGREGADO CON EXITO")
        escribirEnTxt()
        guardarInformacionBD()
    }

    fun read() {
        escribirEnTxt()
        val miArchivo = File(this.rutaBaseSimulacionColegio)
        val lineas = miArchivo.readLines()
        lineas.forEach { println(it) }
    }

    fun update() {
        print("Introducir el id del colegio actualizar: ")
        val idColegio = sc.nextInt()
        for (i in this.colegiosBD.indices) {
            if (this.colegiosBD[i].idColegio == idColegio) {
                seleccionarOpcion(this.colegiosBD[i])
                break
            }
        }
        escribirEnTxt()
        guardarInformacionBD()
    }

    fun delete() {
        /*Recorrer los registros y buscar si existe id a buscar*/
        print("Ingrese el ID de colegio a eliminar: ")
        val idColegio = sc.nextInt()
        for (i in this.colegiosBD.indices) {
            if (this.colegiosBD[i].idColegio == idColegio) {
                this.colegiosBD.removeAt(i)
                println("ELEMENTO ELIMINADO CON EXITO")
                break
            }
        }
        escribirEnTxt()
        guardarInformacionBD()
    }

    private fun verificarIdColegio(colegio: Colegio, idColegio: Int) {
        for (i in this.colegiosBD.indices) {
            if (this.colegiosBD[i].idColegio == idColegio) {
                println("ID EXISTENTE")
                println("GENERANDO NUEVO ID...")
                colegio.idColegio = colegio.generarIdColegio()
            } else {
                colegio.idColegio = idColegio
            }
        }
    }

    private fun crearAlumno(): ArrayList<Alumno> {
        /*Cuando se cree un alumno, enviar a la BD alumno*/
        val bdAlumno = BDAlumno()
        return bdAlumno.create()
    }

    private fun colocarIdAlumnos(alumnos: ArrayList<Alumno>, idColegio: Int) {
        for (i in alumnos.indices) {
            alumnos[i].idColegio = idColegio
        }
    }


    fun leerInformacion(): ArrayList<Colegio> {
        val fin = FileInputStream(informacionColegio)
        val ois = ObjectInputStream(fin)
        return ois.readObject() as ArrayList<Colegio>
    }

    private fun guardarInformacionBD() {
        /*Guardar la información de la creacion del arraylist colegio*/
        val fout = FileOutputStream(informacionColegio)
        val out = ObjectOutputStream(fout)
        out.writeObject(this.colegiosBD)
        out.close()
    }

    private fun verificarInformacion() {
        if (this.leerInformacion().size != 0) {
            this.colegiosBD = leerInformacion()
        } else {
            println("NO EXISTE INFORMACION")
        }
    }


    private fun escribirEnTxt() {
        val archivo = File(this.rutaBaseSimulacionColegio)
        /*Recorrer y escribir el arreglo de estudiantes*/
        archivo.bufferedWriter().use { out ->
            for (i in this.colegiosBD.indices) {
                //this.colegiosBD[i].colocarIDAlumnos(this.colegiosBD[i].idColegio)
                out.write(
                    "COLEGIO\n" +
                            "[${this.colegiosBD[i].idColegio}, " + "\t" +
                            "${this.colegiosBD[i].nombreColegio}," + "\t" +
                            "${this.colegiosBD[i].inversion}," + "\t" +
                            "${this.colegiosBD[i].seccion}]\n" +
                            "ALUMNOS\n"
                )
                for (j in this.colegiosBD[i].alumnos.indices) {
                    out.write(
                        this.colegiosBD[i].alumnos[j].obtenerDatos()
                    )
                }
            }
        }
    }


    private fun seleccionarOpcion(colegio: Colegio) {
        val opciones: ArrayList<String> = arrayListOf(
            "nombreColegio", "inversion", "alumnos", "seccion", "idColegio"
        )
        println("\nOPCIONES")
        for (i in opciones.indices) {
            println("${i + 1}" + " " + opciones[i])
        }
        print("Escoger el campo actualizar: ")
        when (sc.nextInt()) {
            1 -> {
                print("Nuevo nombre colegio: ")
                colegio.nombreColegio = sc.next()
            }

            2 -> {
                print("Nuevo inversion: ")
                colegio.inversion = sc.nextDouble()
            }

            3 -> {
                /*Obtener alumno por ID y actualizar su campo*/
                print("Ingrese el id del alumno: ")
                verificarExistenciaAlumno(colegio, sc.nextInt())
            }

            4 -> {
                print("Ingrese la nueva seccion: ")
                colegio.seccion = sc.next().first()
            }

            5 -> {
                print("Nuevo id: ")
                colegio.idColegio = sc.nextInt()
            }
        }
    }

    private fun verificarExistenciaAlumno(colegio: Colegio, idAlumno: Int) {
        val alumnos = colegio.alumnos
        for (i in alumnos.indices) {
            if (alumnos[i].idAlumno == idAlumno) {
                actualizarAlumno(alumnos[i])
                break
            } else {
                println("NO EXISTE ALUMNO CON ID $idAlumno")
            }
        }
    }

    private fun actualizarAlumno(alumno: Alumno) {
        val bdAlumno: BDAlumno = BDAlumno()
        bdAlumno.update()
        escribirEnTxt()
        guardarInformacionBD()
    }
}

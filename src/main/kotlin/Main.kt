import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    val sc = Scanner(System.`in`)
    var midaTauler: Int? = null
    var tempsTranscorregut: Long = 0

    while (midaTauler == null) {
        println("Introdueix la mida del tauler:")

        if (sc.hasNextInt()) {
            midaTauler = sc.nextInt()
            if (midaTauler <= 0) {
                println("Has introduït un número negatiu.")
                midaTauler = null
            }
        } else {
            sc.next()
            println("Entrada no vàlida, si us plau, introdueix un número.")
        }
    }

    val reines = SolucionadorReines()

    var opcioUsuari: Int? = null

    while (opcioUsuari == null) {
        println("Vols veure totes les solucions o només una?")
        println(
            "1. Totes les solucions\n" +
                    "2. Una solució"
        )
        if (sc.hasNextInt()) {
            opcioUsuari = sc.nextInt()
            if (opcioUsuari !in 1..2) {
                println("Número fora de rang.")
                opcioUsuari = null
            }
        } else {
            sc.next()
            println("Entrada no vàlida, si us plau, introdueix 1 o 2.")
        }
    }

    tempsTranscorregut = measureTimeMillis {
        when (opcioUsuari) {
            1 -> reines.buscaTotesSolucions(midaTauler)
            2 -> reines.buscaSolucio(midaTauler, true)
        }
    }

    val numSolucions = reines.comptaSolucions()
    println("Nombre de solucions trobades: $numSolucions")

    var solucioMostrada: Int? = null

    while (solucioMostrada == null) {
        println("Introdueix el número de la solució que vols veure:")
        if (sc.hasNextInt()) {
            solucioMostrada = sc.nextInt()
            if (solucioMostrada !in 1..numSolucions) {
                println("Número fora de rang.")
                solucioMostrada = null
            }
        } else {
            sc.next()
            println("Entrada no vàlida, si us plau, introdueix un número.")
        }
    }

    println(reines.mostraSolucio(solucioMostrada) + "\nTemps transcorregut: $tempsTranscorregut ms")
}

class SolucionadorReines {
    private lateinit var solucions: MutableList<String>
    private var numSolucions = 0
    private var fila = 0
    private lateinit var columna: IntArray
    private var trobaUna = false
    private var midaTauler = 0

    fun buscaSolucio(midaTauler: Int, trobaUna: Boolean) {
        reinicia()
        this.numSolucions = 0
        this.solucions = mutableListOf()
        this.columna = IntArray(midaTauler + 1)
        this.fila = 1
        this.columna[fila] = 0
        this.trobaUna = trobaUna
        this.midaTauler = midaTauler

        resol()
    }

    fun buscaTotesSolucions(midaTauler: Int) {
        reinicia()
        this.numSolucions = 0
        this.solucions = mutableListOf()
        this.columna = IntArray(midaTauler + 1)
        this.fila = 1
        this.columna[fila] = 0
        this.midaTauler = midaTauler

        resol()
    }

    fun comptaSolucions(): Int {
        return this.numSolucions
    }

    fun mostraSolucio(numeroSolucio: Int): String? {
        return if (numeroSolucio != 0 && this.solucions.isNotEmpty()) this.solucions[numeroSolucio - 1] else null
    }

    private fun resol() {
        while (fila > 0) {
            if (columna[fila] < midaTauler) {
                columna[fila] += 1
                if (esNodeValid(fila, columna)) {
                    if (fila == midaTauler) {
                        tractaSolucio(columna)
                    } else {
                        fila += 1
                        columna[fila] = 0
                    }
                }
            } else {
                fila -= 1
            }
        }
    }

    private fun esNodeValid(fila: Int, columna: IntArray): Boolean {
        for (f in 1 until fila) {
            if (columna[f] == columna[fila] || f - columna[f] == fila - columna[fila] || f + columna[f] == fila + columna[fila]) {
                return false
            }
        }
        return true
    }

    private fun tractaSolucio(columna: IntArray) {
        var solucio = ""
        for (f in 1..midaTauler) {
            solucio += "Reina $f / fila $f / columna${columna[f]}\n"
        }
        this.solucions.add(solucio)
        this.numSolucions++

        if (this.trobaUna) {
            this.trobaUna = false
        }
    }

    private fun reinicia() {
        this.fila = 0
        this.numSolucions = 0
    }
}

import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    val sc = Scanner(System.`in`)
    var midaTauler: Int? = null
    var tempsTranscorregut: Long = 0

    // Demanem a l'usuari la mida del tauler fins que introdueixi un número vàlid
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

    // Creem una instància de la classe SolucionadorReines
    val reines = SolucionadorReines()

    // Demanem a l'usuari si vol veure totes les solucions o només una
    var opcioUsuari: Int? = null
    while (opcioUsuari == null) {
        println("Vols veure totes les solucions o només una?")
        println(
            "1. Totes les solucions\n" +
                    "2. Una solució"
        )
        //Si l'usuari introdueix un número que no sigui 1 o 2, li tornem a demanar que introdueixi un número vàlid
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

    // Mesurem el temps que triga a trobar les solucions
    tempsTranscorregut = measureTimeMillis {
        when (opcioUsuari) {
            // Si l'usuari ha triat veure totes les solucions, cridem al mètode buscaTotesSolucions
            1 -> reines.buscaTotesSolucions(midaTauler)
            // Si l'usuari ha triat veure només una solució, cridem al mètode buscaSolucio
            2 -> reines.buscaSolucio(midaTauler, true)
        }
    }

    // Mostrem el nombre de solucions trobades criant al mètode comptaSolucions
    val numSolucions = reines.comptaSolucions()
    println("Nombre de solucions trobades: $numSolucions")

    // Si l'usuari ha triat veure només una solució, li demanem quina solució vol veure
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
    // Mostrem la solució triada per l'usuari i el temps transcorregut
    println(reines.mostraSolucio(solucioMostrada) + "\nTemps transcorregut: $tempsTranscorregut ms")
}

class SolucionadorReines {
    // Variables de classe, inicialitzades quan es crida a un dels mètodes de la classe
    private lateinit var solucions: MutableList<String>
    private var numSolucions = 0
    private var fila = 0
    private lateinit var columna: IntArray
    private var trobaUna = false
    private var midaTauler = 0

    // Busca una solució al problema de les reines en un tauler de mida midaTauler fen servir backtracking.
    // Si trobaUna és true, només troba una solució, si no, troba totes les solucions possibles.
    fun buscaSolucio(midaTauler: Int, trobaUna: Boolean) {
        reinicia()
        this.numSolucions = 0
        this.solucions = mutableListOf()
        this.columna = IntArray(midaTauler + 1)
        this.fila = 1
        this.columna[fila] = 0
        this.trobaUna = trobaUna
        this.midaTauler = midaTauler

        // Cridem al mètode resol per trobar les solucions
        resol(trobaUna)
    }

    // Busca totes les solucions possibles al problema de les reines en un tauler de mida midaTauler fen servir backtracking.
    fun buscaTotesSolucions(midaTauler: Int) {
        reinicia()
        this.numSolucions = 0
        this.solucions = mutableListOf()
        this.columna = IntArray(midaTauler + 1)
        this.fila = 1
        this.columna[fila] = 0
        this.midaTauler = midaTauler

        resol(trobaUna)
    }

    // Compta el nombre de solucions trobades
    fun comptaSolucions(): Int {
        return this.numSolucions
    }

    // Mostra la solució número numeroSolucio
    fun mostraSolucio(numeroSolucio: Int): String? {
        return if (numeroSolucio != 0 && this.solucions.isNotEmpty()) this.solucions[numeroSolucio - 1] else null
    }

    //Aquesta funció és la que s'encarrega de trobar les solucions al problema de les reines
    private fun resol(trobaUna: Boolean) {
        val esTrobada = false
        while (fila > 0) {
            //Si la fila actual és menor que la mida del tauler, incrementem la columna de la fila actual
            if (columna[fila] < midaTauler) {
                columna[fila] += 1
                //Si el node actual és vàlid, comprovem si hem arribat a la última fila, en aquest cas, tractem la solució
                if (esNodeValid(fila, columna)) {
                    if (fila == midaTauler) {
                        //Si trobaUna és true, tractem la solució i sortim del bucle
                        tractaSolucio(columna)
                    } else {
                        //Si no hem arribat a l'última fila, incrementem la fila i posem la columna a 0
                        fila += 1
                        columna[fila] = 0
                    }
                }
                //Si el node actual no és vàlid, no fem res
            } else {
                fila -= 1
            }
        }
    }

    //Comprovem si el node actual és vàlid (no hi ha cap reina a la mateixa fila, columna o diagonal)
    private fun esNodeValid(fila: Int, columna: IntArray): Boolean {
        for (f in 1 until fila) {
            if (columna[f] == columna[fila] || f - columna[f] == fila - columna[fila] || f + columna[f] == fila + columna[fila]) {
                return false
            }
        }
        return true
    }

    //Tractem la solució trobada, afegint-la a la llista de solucions i incrementant el nombre de solucions trobades
    private fun tractaSolucio(columna: IntArray) {
        val builder = StringBuilder()
        for (fila in 1..midaTauler) {
            for (col in 1..midaTauler) {
                if (col == columna[fila]) {
                    builder.append("Q ")
                } else {
                    builder.append(". ")
                }
            }
            builder.append("\n")
        }
        val solucio = builder.toString()
        this.solucions.add(solucio)
        this.numSolucions++

        // Si trobaUna és true, sortim del bucle
        if (this.trobaUna) {
            this.trobaUna = false
        }
    }


    //Reinicia les variables de classe
    private fun reinicia() {
        this.fila = 0
        this.numSolucions = 0
    }
}

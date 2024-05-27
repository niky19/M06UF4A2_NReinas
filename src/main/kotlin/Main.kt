fun main() {
    val nReinas = NReinas()
    val n = 8
    nReinas.buscarSoluciones(n)
    println("Número de soluciones: ${nReinas.numSoluciones()}")
    for (i in 0 until nReinas.numSoluciones()) {
        println("Solución ${i + 1}:")
        nReinas.visualizaSolucion(i).forEach { fila ->
            println(fila)
        }
        println()
    }
}

class NReinas {
    private var soluciones: MutableList<List<Int>> = mutableListOf()
    private var columna: MutableList<Int> = mutableListOf()

    fun buscarSoluciones(n: Int) {
        soluciones.clear()
        columna = MutableList(n + 1) { 0 }
        var fila = 1
        columna[fila] = 0

        while (fila > 0) {
            if (columna[fila] < n) {
                columna[fila] += 1
                if (esValido(fila)) {
                    if (fila == n) {
                        agregarSolucion()
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

    fun numSoluciones(): Int {
        return soluciones.size
    }

    fun visualizaSolucion(numeroSolucion: Int): List<String> {
        val solucion = soluciones[numeroSolucion]
        val n = solucion.size
        val tablero = MutableList(n) { MutableList(n) { '.' } }
        for (i in solucion.indices) {
            tablero[i][solucion[i] - 1] = 'Q'
        }
        return tablero.map { it.joinToString("") } // Imprime el tablero en string separado por espacios
    }

    private fun esValido(fila: Int): Boolean {
        for (f in 1 until fila) {
            if (columna[f] == columna[fila] || (f - columna[f] == fila - columna[fila]) || (f + columna[f] == fila + columna[fila])) {
                return false
            }
        }
        return true
    }

    private fun agregarSolucion() {
        soluciones.add(columna.subList(1, columna.size))
    }
}

class NReinas(private val n: Int) {
    private val soluciones = mutableListOf<IntArray>()

    fun busquedaSoluciones() {
        val solucion = IntArray(n) { -1 }
        colocarReina(solucion, 0)
    }

    fun numSoluciones(): Int {
        return soluciones.size
    }

    fun visualizaSolucion(ns: Int): String {
        if (ns < 0 || ns >= soluciones.size) {
            return "Número de solución inválido"
        }
        val solucion = soluciones[ns]
        val builder = StringBuilder()
        for (fila in solucion) {
            for (col in 0 until n) {
                if (col == fila) {
                    builder.append("Q ")
                } else {
                    builder.append(". ")
                }
            }
            builder.append("\n")
        }
        return builder.toString()
    }

    private fun colocarReina(solucion: IntArray, fila: Int) {
        if (fila == n) {
            soluciones.add(solucion.clone())
            return
        }
        for (col in 0 until n) {
            if (esValido(solucion, fila, col)) {
                solucion[fila] = col
                colocarReina(solucion, fila + 1)
                solucion[fila] = -1  // Retrocede
            }
        }
    }

    private fun esValido(solucion: IntArray, fila: Int, col: Int): Boolean {
        for (i in 0 until fila) {
            if (solucion[i] == col || Math.abs(solucion[i] - col) == Math.abs(i - fila)) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val n = 8
    val nReinas = NReinas(n)
    nReinas.busquedaSoluciones()
    println("Número de soluciones: ${nReinas.numSoluciones()}")
    for (i in 0 until nReinas.numSoluciones()) {
        println("Solución ${i + 1}:")
        println(nReinas.visualizaSolucion(i))
    }
}

import kotlin.math.abs

class PuzzleAStar {

    private val direcoes = mutableMapOf(
        Pair(-1,0) to "cima",
        Pair(1,0) to "baixo",
        Pair(0,-1) to "esquerda",
        Pair(0,1) to "direita"
    )

    fun procuraIndice(matrix: ArrayList<ArrayList<Int>>, numero: Int): Pair<Int, Int> {
        var indexI = 0
        var indexJ = 0
        var index: Pair<Int, Int> = Pair(0,0)

        matrix.forEach {linha ->
            indexJ = 0
            linha.forEach {
                if (it == numero) {
                    index = Pair(indexI, indexJ)
                }
                indexJ++
            }
            indexI++
        }

        return index
    }

    fun direcaoPossivel(matriz: ArrayList<ArrayList<Int>>): ArrayList<String> {
        var indice = procuraIndice(matriz, 0)
        var indiceI = indice.first
        var indiceJ = indice.second
        var resultado = arrayListOf<String>()

        val map = direcoes

        for (dir in map) {
            if (!((indiceI+dir.key.first < 0) || (indiceI+dir.key.first > 2) || (indiceJ+dir.key.second < 0) || (indiceJ+dir.key.second > 2))) {
                resultado.add(dir.value)
            }
        }
        return resultado
    }

    fun direcaoManhattan(matrizInicial: ArrayList<ArrayList<Int>>, matrizObjetivo: ArrayList<ArrayList<Int>>): Int {
        var resultado = 0

        for (num in 1..8) {
            val indice_i_inicial = procuraIndice(matrizInicial, num).first
            val indice_j_inicial = procuraIndice(matrizInicial, num).second
            val indice_i_objetivo = procuraIndice(matrizObjetivo, num).first
            val indice_j_objetivo = procuraIndice(matrizObjetivo, num).second

            val h = abs(indice_i_inicial - indice_i_objetivo) + abs(indice_j_inicial - indice_j_objetivo)
            resultado += h
        }

        return resultado
    }

    fun gerarEstado(matriz: ArrayList<ArrayList<Int>>, direcoesPossiveis: List<String>): ArrayList<ArrayList<ArrayList<Int>>> {
        val indice_i_zero = procuraIndice(matriz, 0).first
        val indice_j_zero = procuraIndice(matriz, 0).second

        var moverPara = arrayListOf<Pair<Int, Int>>()

        val dir = direcoes.map { (key, value) -> value to key }.toMap()

        for (d in direcoesPossiveis) {
            dir.forEach {
                if (it.key == d) {
                    moverPara.add(Pair(indice_i_zero + it.value.first, indice_j_zero + it.value.second))
                }
            }
        }
        var novaMatriz = arrayListOf<ArrayList<ArrayList<Int>>>()

        for (p in moverPara) {
            var matrizEmLista = arrayListOf<Int>()
            matriz.forEach {linha ->
                linha.forEach {
                    matrizEmLista.add(it)
                }
            }

            val blank_index = indice_i_zero * 3 + indice_j_zero
            val dest_index = p.first * 3 + p.second

            val listaMatrixTemp = matrizEmLista[blank_index]
            matrizEmLista[blank_index] = matrizEmLista[dest_index]
            matrizEmLista[dest_index] = listaMatrixTemp

            var x = 0
            var temp = arrayListOf<Int>()
            var matrixTemp = arrayListOf<ArrayList<Int>>()
            for (i in matrizEmLista) {
                temp.add(i)
                x++
                if (x == 3) {
                    matrixTemp.add(temp)
                    x = 0
                    temp = arrayListOf()
                }
            }
            novaMatriz.add(matrixTemp)
        }

        return novaMatriz
    }

    fun resolve(matrizInicial: ArrayList<ArrayList<Int>>, matrizObjetivo: ArrayList<ArrayList<Int>>): MutableList<Estado> {
        var path = mutableListOf<MutableList<Estado>>()
        var result = mutableListOf<Any>()
        var verificacao = mutableMapOf(matrizInicial to 1)
        var gen = 0
        var dept = 0

        //path.add(matrizInicial, "START", direcaoManhattan(matrizInicial, matrizObjetivo))

        path.add(mutableListOf(Estado(matrizInicial, "START", direcaoManhattan(matrizInicial, matrizObjetivo))))
        var arrayList = path[0] as ArrayList<*>
        arrayList.forEach {
            print("$it ")
        }

        while (path.isNotEmpty()) {
            if (gen % 1000 == 0) {
                print("Gen: $gen")
                gen++
            }

            var current_route = path.last()
            path = path.dropLast(1).toMutableList()
            val current_puzzle = current_route[-1].matrizInicial

            if (current_puzzle == matrizObjetivo) {
                return current_route
            }

            var g = current_route.size

            if (verificacao[current_puzzle] != g) {
                continue
            }
            val nextState = gerarEstado(current_puzzle, direcaoPossivel(current_puzzle))

            for (state in nextState) {
                val state_puzzle = state
                val state_g = g + 1
                if (verificacao.containsKey(state_puzzle)) {
                    var i = verificacao[state_puzzle] ?: -1
                    if (state_g < i) {
                        verificacao[state_puzzle] = state_g
                        val to_add_state = current_route
                        to_add_state.add(Estado())
                    }
                }
            }
        }
    }
}

data class Estado(
    val matrizInicial: ArrayList<ArrayList<Int>>,
    val instrucao: String,
    val direcaoManhattan: Int

)
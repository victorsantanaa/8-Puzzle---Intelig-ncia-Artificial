object MatrizDoPuzzle {

    fun matrizInicial() =
            arrayOf(arrayListOf(1, 8, 2), arrayListOf(0, 4, 3), arrayListOf(7, 6, 5))

    fun matrizObjetivo() =
            arrayOf(arrayListOf(1, 2, 3), arrayListOf(4, 5, 6), arrayListOf(7, 8, 0))

    fun cima() = arrayOf(-1, 0)
    fun baixo() = arrayOf(1, 0)
    fun esquerda() = arrayOf(0, -1)
    fun direita() = arrayOf(0, 1)

    fun movimento(posicaoZero: Array<Int>, direcao: Array<Int>) {

    }

    fun distanciaManhattan(inicial: Array<IntArray>, objetivo: Array<IntArray>) {

        val tamanho = inicial.size
        var i = 0
        var j =0
        for (linha in inicial) {
            i++
            for (numeroInicial in linha) {
                j++

            }
        }

        //val distance: Int = Math.abs(x1 - x0) + Math.abs(y1 - y0)

    }
}

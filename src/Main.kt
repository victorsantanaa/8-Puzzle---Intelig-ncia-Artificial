fun main() {
    var matrizInicialX = arrayOf(arrayListOf(0,2,4), arrayListOf(1,6,3), arrayListOf(5,8,7))
    var matrizInicial = arrayOf(arrayListOf(1, 8, 2), arrayListOf(0, 4, 3), arrayListOf(7, 6, 5))
    var matrizObejtivo = MatrizDoPuzzle.matrizObjetivo()

    val puzzle = PuzzleAStar()

    puzzle.resolve(matrizInicial, matrizObejtivo)
}
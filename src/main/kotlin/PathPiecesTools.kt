
fun dotPathToCamelCase(path: String): String {
    return path.split(".").joinToString("") { it.capitalize() }
}

fun highlightNullPiece(pathPieces: List<String>, highlight: Int): String {
    return (pathPieces.subList(0, highlight)
            + listOf("{${pathPieces[highlight]}}")
            + pathPieces.subList(highlight + 1, pathPieces.size)
            ).joinToString(".")
}
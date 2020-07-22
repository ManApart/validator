fun getNullSafeValue(source: Any, path: String): Any? {
    val pathPieces = path.split(".")
    return getNullSafeResult(source, pathPieces)
}

private fun getNullSafeResult(source: Any, pathPieces: List<String>): Any? {
    val methodName = "get" + pathPieces.first().capitalize()
    val result = source.javaClass.getMethod(methodName).invoke(source)
    if (pathPieces.size > 1 && result != null) {
        return getNullSafeResult(result, pathPieces.subList(1, pathPieces.size))
    }
    return result
}
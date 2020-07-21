fun createValidator(validations: List<() -> Any?>) {
    //for each validation, use reflection and build a nested if statement checking for null each step (eventually second piece of lambda that does some passed in evaluation)
    //write a 'validate all' function that calls all the other validations
    //write to generated file

    validations.forEach {
        print(it.toString())
    }

}


fun createValidator(source: Any, path: String) {
    val pathPieces = path.split(".")
    val methodName = "get" + pathPieces.first().capitalize()
    val childType = source.javaClass.getMethod(methodName).returnType
    val result = source.javaClass.getMethod(methodName).invoke(source)
    if (result != null) {

    }
    println(source.toString())
}


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
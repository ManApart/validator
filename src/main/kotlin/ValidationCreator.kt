fun createValidator(validations: List<() -> Any?>) {
    //for each validation, use reflection and build a nested if statement checking for null each step (eventually second piece of lambda that does some passed in evaluation)
    //write a 'validate all' function that calls all the other validations
    //write to generated file

    validations.forEach {
        print(it.toString())
    }

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


fun createValidator(source: Any, path: String): String {
    val pathPieces = path.split(".")
    val className = source.javaClass.simpleName
    val validateFunctionName = "validate" + dotPathToCamelCase(path)

    return """
    public class ${className}Validator {
        public static String validate($className object) {
            return ${validateFunctionName}(object);
        }

        private static String ${validateFunctionName}($className object) {
            ${createIfStatement(source, pathPieces)}

            return null;
        }
    }
    """.trimIndent()
}

fun dotPathToCamelCase(path: String): String {
    return path.split(".").joinToString("") { it.capitalize() }
}

fun highlightNullPiece(pathPieces: List<String>, highlight: Int): String {
    return (pathPieces.subList(0, highlight)
            + listOf("{${pathPieces[highlight]}}")
            + pathPieces.subList(highlight + 1, pathPieces.size)
            ).joinToString(".")
}

private fun createIfStatement(source: Any, pathPieces: List<String>): String {
    return "if (object == null) {\n" +
            "return \"{object}.${pathPieces.joinToString(".")}\";"


}

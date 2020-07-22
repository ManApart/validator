fun createValidator(validations: List<() -> Any?>) {
    //for each validation, use reflection and build a nested if statement checking for null each step (eventually second piece of lambda that does some passed in evaluation)
    //write a 'validate all' function that calls all the other validations
    //write to generated file

    validations.forEach {
        print(it.toString())
    }

}

fun createValidator(source: Class<*>, path: String): String {
    val pathPieces = path.split(".")
    val className = source.simpleName
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

private fun createIfStatement(source: Class<*>, pathPieces: List<String>): String {
    return "if (object == null) {\n" +
            "return \"{object}.${pathPieces.joinToString(".")}\";\n" +
            addElseIfs(source, pathPieces, 0, "object") + "}"
}

fun addElseIfs(source: Class<*>, pathPieces: List<String>, depth: Int, previousPath: String): String {
    val methodName = "get" + pathPieces[depth].capitalize()
    val result = source.getMethod(methodName).returnType
    val fullPath = "${previousPath}.${methodName}()"

    val elsif = "} else if(${fullPath} == null){\n"

    val returnLine = "return \"object." + highlightNullPiece(pathPieces, depth) + "\";\n"

    val additional = if (depth < pathPieces.size-1) {
        addElseIfs(result, pathPieces, depth + 1, fullPath)
    } else {
        ""
    }

    return elsif + returnLine + additional

}

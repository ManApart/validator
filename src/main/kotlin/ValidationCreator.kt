fun createValidator(validations: List<() -> Any?>) {
    //for each validation, use reflection and build a nested if statement checking for null each step (eventually second piece of lambda that does some passed in evaluation)
    //write a 'validate all' function that calls all the other validations
    //write to generated file

    validations.forEach {
        print(it.toString())
    }

}

fun createValidator(source: Class<*>, vararg paths: String): String {
    val className = source.simpleName

    val pathPieces = paths.map { it.split(".") }
    val validateFunctionNames = paths.map { "validate" + dotPathToCamelCase(it) }

    val functionCallers = createFunctionCallers(validateFunctionNames)
    val functionDefinitions = createFunctionDefinitions(source, className, validateFunctionNames, pathPieces)

    return """
    import java.util.List;
    import java.util.Objects;
    import java.util.stream.Collectors;

    public class ${className}Validator {
        public static List<String> validate($className object) {
            return List.of(
                $functionCallers
            ).stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
        
        $functionDefinitions
    }
    """.trimIndent()
}

private fun createFunctionCallers(validateFunctionNames: List<String>): String {
    return validateFunctionNames.joinToString(",\n") { "${it}(object)" }
}

private fun createFunctionDefinitions(
    source: Class<*>,
    className: String,
    validateFunctionNames: List<String>,
    allPathPieces: List<List<String>>
): String {
    return validateFunctionNames.indices.joinToString("\n") {
        val validateFunctionName = validateFunctionNames[it]
        val pathPieces = allPathPieces[it]
        """private static String ${validateFunctionName}($className object) {
            ${createIfStatement(source, pathPieces)}

            return null;
        }
        """
    }
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

    val additional = if (depth < pathPieces.size - 1) {
        addElseIfs(result, pathPieces, depth + 1, fullPath)
    } else {
        ""
    }

    return elsif + returnLine + additional

}

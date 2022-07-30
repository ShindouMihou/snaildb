package pw.mihou.snail.snql

fun String.toSNQLObject(): Map<String, Any?>? {
    if (startsWith('(') && endsWith(')')) {
        return SNQLObject.parse(removeSurrounding("(", ")").trim())
    }

    return null
}

fun String.toSNQLArray(): List<Any?>? {
    if (startsWith('[') && endsWith(']')) {
        return SNQLArray.parse(removeSurrounding("[", "]").trim())
    }

    return null
}

fun List<*>.toSNQLString(): String = SNQLElement.stringify(this)
fun Map<String, Any?>.toSNQLString(): String = SNQLElement.stringify(this)
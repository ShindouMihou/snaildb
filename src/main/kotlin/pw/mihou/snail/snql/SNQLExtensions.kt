package pw.mihou.snail.snql

fun String.toSNQLObject(): Map<String, Any?>? {
    val objectMatcher = SNQLObject.REGEX.matchEntire(this.trim()) ?: return null
    return SNQLObject.parse(objectMatcher.groups["object"]!!.value)
}

fun String.toSNQLArray(): List<Any?>? {
    val arrayMatcher = SNQLArray.REGEX.matchEntire(this.trim()) ?: return null
    return SNQLArray.parse(arrayMatcher.groups["array"]!!.value)
}

fun List<*>.toSNQLString(): String = SNQLElement.stringify(this)
fun Map<String, Any?>.toSNQLString(): String = SNQLElement.stringify(this)
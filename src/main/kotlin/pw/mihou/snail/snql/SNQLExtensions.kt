package pw.mihou.snail.snql

fun String.toSNQLObject(): Map<String, Any?>? {
    val objectMatcher = SNQLObject.REGEX.matchEntire(this) ?: return null
    return SNQLObject.parse(objectMatcher.groups["object"]!!.value)
}
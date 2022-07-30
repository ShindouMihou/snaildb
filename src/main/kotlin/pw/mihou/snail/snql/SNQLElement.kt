package pw.mihou.snail.snql

interface SNQLElement<Self> {
    val REGEX: Regex

    fun parse(selection: String): Self?

    companion object {

        fun map(vararg elements: Pair<String, Any?>): Map<String, Any?> = mapOf(*elements)
        fun array(vararg elements: Any?): List<Any?> = listOf(*elements)

        private fun destructure(array: List<Any?>): String {
            return array.joinToString(" AND ") { value ->
                if (value is Map<*, *> && value.keys.all { it is String }) {
                    @Suppress("UNCHECKED_CAST")
                    return@joinToString stringify(value as Map<String, Any?>)
                }

                if (value is Map<*, *>) {
                    @Suppress("UNCHECKED_CAST")
                    return@joinToString "(" + destructure(value as Map<Any, Any?>) + ")"
                }

                if (value is List<*>) {
                    return@joinToString stringify(value)
                }

                if (value is String) {
                    return@joinToString "\"$value\""
                }

                if (value is Long) {
                    return@joinToString "(long)$value"
                }

                if (value is Int) {
                    return@joinToString "(int)$value"
                }

                return@joinToString value.toString()
            }
        }

        private fun destructure(map: Map<*, *>): String {
            return map.map { (key, value) ->
                if (value is Map<*, *> && value.keys.all { it is String }) {
                    @Suppress("UNCHECKED_CAST")
                    return@map "$key=" + stringify(value as Map<String, Any?>)
                }

                if (value is Map<*, *>) {
                    @Suppress("UNCHECKED_CAST")
                    return@map "$key=(" + destructure(value as Map<Any, Any?>) + ")"
                }

                if (value is List<*>) {
                    return@map "$key=" + stringify(value)
                }

                if (value is String) {
                    return@map "$key=\"$value\""
                }

                if (value is Long) {
                    return@map "$key=(long)$value"
                }

                if (value is Int) {
                    return@map "$key=(int)$value"
                }

                return@map "$key=$value"
            }.joinToString(" AND ")
        }

        fun stringify(map: Map<String, Any?>): String =  "(" + destructure(map) + ")"
        fun stringify(array: List<*>): String = "[" + destructure(array) + "]"

        fun split(selection: String): List<String> {
            val selections = mutableListOf<String>()

            var insideQuotations = false
            var nestedObjects = 0
            var nestedArrays = 0

            var temporaryHold = ""
            var temporarySelection = ""

            for ((index, character) in selection.withIndex()) {
                temporarySelection += character
                if (character == '"') {
                    insideQuotations = !insideQuotations
                }

                if (character == '(' && !insideQuotations) {
                    nestedObjects++
                }

                if (character == ')' && !insideQuotations) {
                    nestedObjects--
                }

                if (character == '[' && !insideQuotations) {
                    nestedArrays++
                }

                if (character == ']' && !insideQuotations) {
                    nestedArrays--
                }

                if (index == selection.length - 1) {
                    selections.add(temporarySelection.trim())
                    temporaryHold = ""
                    temporarySelection = ""
                }

                if (character != ' ') {
                    temporaryHold += character

                    if (temporaryHold == "AND" && !insideQuotations && nestedObjects == 0 && nestedArrays == 0) {
                        selections.add(temporarySelection.removePrefix("AND ").removeSuffix(" AND").trim())
                        temporaryHold = ""
                        temporarySelection = ""
                    }

                    continue
                }

                temporaryHold = ""
            }

            return selections
        }
    }
}
package pw.mihou.snail.snql

import java.lang.IllegalArgumentException

object SNQLObject: SNQLElement<Map<String, Any?>> {
    override val REGEX = "^\\((?<object>.*)\\)\$".toRegex()

    override fun parse(selection: String): Map<String, Any?> {
        val selections = SNQLElement.split(selection)
        val element = mutableMapOf<String, Any?>()

        selections.map { it.split("=", limit = 2) }.forEach { (key, value) ->
            val valueMatcher = SNQLValue.REGEX.matchEntire(value.trim())
                ?: throw IllegalArgumentException("SNQL cannot recognize selection as a value: $value")

            element[key.trim()] = SNQLValue.parse(valueMatcher.groups["value"]!!.value)
        }

        return element
    }
}
package pw.mihou.snail.snql

import java.lang.IllegalArgumentException

object SNQLArray: SNQLElement<List<Any?>> {

    override val REGEX: Regex = "^\\[(?<array>.*)]\$".toRegex(setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))

    override fun parse(selection: String): List<Any?> {
        val selections = SNQLElement.split(selection)
        val element = mutableListOf<Any?>()

        selections.forEach { value ->
            val valueMatcher = SNQLValue.REGEX.matchEntire(value.trim())
                ?: throw IllegalArgumentException("SNQL cannot recognize selection as a value: $value")

            element.add(SNQLValue.parse(valueMatcher.groups["value"]!!.value))
        }

        return element
    }
}


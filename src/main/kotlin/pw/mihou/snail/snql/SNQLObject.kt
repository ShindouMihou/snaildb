package pw.mihou.snail.snql

import java.lang.IllegalArgumentException

object SNQLObject: SNQLElement<Map<String, Any?>> {
    override fun parse(selection: String): Map<String, Any?> {
        val selections = SNQLElement.split(selection)
        val element = mutableMapOf<String, Any?>()

        @Suppress("NAME_SHADOWING")
        for (selection in selections) {
            val pair = selection.split('=', limit = 2)

            if (pair.size < 2)
                throw IllegalArgumentException("SNQL cannot consider the following selection as an object: $selection")

            element[pair[0].trim()] = SNQLValue.parse(pair[1].trim())
        }

        return element
    }
}
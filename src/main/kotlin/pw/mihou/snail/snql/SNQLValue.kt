package pw.mihou.snail.snql

import java.lang.IllegalArgumentException

object SNQLValue: SNQLElement<Any> {
    // Unlike other decimal regexes, this ensures that we support a state either positive, negative or null (default to positive)
    // and also ensures that the value is of decimal completely (no 0. or 0.0abc or abc0.0).
    val DECIMAL_REGEX = "^(?<value>[+-]?[\\d]*[.]\\b[\\d]*\$)".toRegex()
    val NUMBER_REGEX = "^([(](?<type>long|int)[)])?(?<value>[-+]?\\d*)\$".toRegex(RegexOption.IGNORE_CASE)
    val STRING_REGEX = "^\"(?<contents>(?:\\\\\"|.)*?)\"\$".toRegex(setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))
    val BOOLEAN_REGEX = "^(true|false)$".toRegex(setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.IGNORE_CASE))

    override val REGEX = "^(?<value>.*)$".toRegex()

    override fun parse(selection: String): Any? {
        // We'll do a strict casing null to ensure that we are getting proper match.
        if (selection == "null") {
            return null
        }

        val stringMatcher = STRING_REGEX.matchEntire(selection)

        if (stringMatcher != null) {
            return stringMatcher.groups["contents"]!!.value
        }

        val booleanMatcher = BOOLEAN_REGEX.matchEntire(selection)

        if (booleanMatcher != null) {
            return selection.toBoolean()
        }

        val numberMatcher = NUMBER_REGEX.matchEntire(selection)

        if (numberMatcher != null) {
            val numberString: String? = numberMatcher.groups["value"]?.value
            val type: String? = numberMatcher.groups["type"]?.value?.lowercase()

            if (numberString == null) {
                throw IllegalArgumentException("SNQL selected number but couldn't find a digit: $selection")
            }

            if (type != null) {
                return when (type) {
                    "long" -> numberString.toLong()
                    "int" -> numberString.toInt()
                    else -> throw IllegalStateException("SNQL selected number with type but regex matched invalid type: $selection")
                }
            }

            val number = selection.toLong()

            // To save us some bytes, we'll automatically convert it from int to long and long to int when needed.
            if (number > Int.MAX_VALUE || number < Int.MIN_VALUE) {
                return number
            }

            return numberString.toInt()
        }

        val decimalMatcher = DECIMAL_REGEX.matchEntire(selection)

        if (decimalMatcher != null) {
            return selection.toDouble()
        }

        val objectMatcher = SNQLObject.REGEX.matchEntire(selection)

        if (objectMatcher != null) {
            return SNQLObject.parse(objectMatcher.groups["object"]!!.value)
        }

        val arrayMatcher = SNQLArray.REGEX.matchEntire(selection)

        if (arrayMatcher != null) {
            return SNQLArray.parse(arrayMatcher.groups["array"]!!.value)
        }

        throw IllegalArgumentException("SNQL cannot find a matching type for the given selection: $selection")
    }
}
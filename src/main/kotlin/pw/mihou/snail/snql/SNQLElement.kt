package pw.mihou.snail.snql

interface SNQLElement<Self> {
    val REGEX: Regex

    fun parse(selection: String): Self?

    companion object {

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
                        selections.add(temporarySelection.replace(" AND", "").trim())
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
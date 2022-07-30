package pw.mihou.snail.snql

object SNQLArray: SNQLElement<List<Any?>> {

    override fun parse(selection: String): List<Any?> {
        val selections = SNQLElement.split(selection)
        val element = mutableListOf<Any?>()

        for (value in selections) {
            element.add(SNQLValue.parse(value.trim()))
        }

        return element
    }
}


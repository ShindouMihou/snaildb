package pw.mihou.snail.warming

import pw.mihou.snail.SnailDb
import pw.mihou.snail.logger.info
import pw.mihou.snail.warming.snql.QueryWarmup

interface Warmup {

    val name: String

    operator fun invoke()

    companion object {

        private val warmups = listOf<Warmup>(QueryWarmup)

        operator fun invoke() {
            for (warmup in warmups) {
                SnailDb.LOGGER.info("event" to "APPLICATION_WARMING", "task" to warmup.name)
                warmup()
            }
        }
    }

}
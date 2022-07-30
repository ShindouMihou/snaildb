package pw.mihou.snail.warming.snql

import pw.mihou.snail.SnailDb
import pw.mihou.snail.logger.info
import pw.mihou.snail.logger.objectsToJson
import pw.mihou.snail.snql.toSNQLObject
import pw.mihou.snail.warming.Warmup
import kotlin.system.measureTimeMillis

object QueryWarmup: Warmup {

    private val queries = listOf(
        "(hello={\"world\"})",
        "(hello={\"world\"} AND good={\"bye\"})",
        "(array={[{\"hello\"}]})",
        "(database={\"world\"} AND collection={\"hello\"} AND content={(message={\"It works!\"} " +
                "AND array={[{\"HELLO\"} AND {(nested={(world={\"hello\"} AND magic={[{\"world\"}]})})}]})})",
        "(object_1={(hello={\"world\"})} AND object_2={(hello={\"world\"})} AND object_3={(hello={\"world\"})})"
    )

    override val name = "QUERY_WARMING"

    override operator fun invoke() {
        for (query in queries) {
            val timeTaken = measureTimeMillis {
                query.toSNQLObject()
            }

            SnailDb.LOGGER.info("event" to "APPLICATION_WARMING", "task" to objectsToJson(
                "name" to "QUERY_WARMING",
                "duration" to "$timeTaken milliseconds"
            ))
        }
    }


}
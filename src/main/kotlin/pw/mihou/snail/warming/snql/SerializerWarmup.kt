package pw.mihou.snail.warming.snql

import pw.mihou.snail.SnailDb
import pw.mihou.snail.logger.info
import pw.mihou.snail.logger.objectsToJson
import pw.mihou.snail.snql.SNQLElement
import pw.mihou.snail.snql.toSNQLArray
import pw.mihou.snail.snql.toSNQLObject
import pw.mihou.snail.snql.toSNQLString
import pw.mihou.snail.warming.Warmup
import java.text.NumberFormat
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

object SerializerWarmup: Warmup {
    private val elements = listOf(
        SNQLElement.map(
            "database" to "test",
            "collection" to "hello_mom!",
            "content" to SNQLElement.map(
                "string" to "hello world",
                "int" to 1,
                "long" to 1L,
                "decimal" to 1.0,
                "negative" to SNQLElement.map(
                    "int" to -1,
                    "long" to -1L,
                    "decimal" to -1.0
                ),
                "positive" to SNQLElement.map(
                    "int" to +1L,
                    "long" to +1L,
                    "decimal" to +1.0
                ),
                "map" to SNQLElement.map(
                    "good" to "night",
                    "morning" to "good"
                ),
                "list" to SNQLElement.array("world", "hello"))
        ),
        SNQLElement.array("hello", "world"),
        SNQLElement.map("hello" to "world"),
        SNQLElement.map("long" to 1L, "int" to 1),
        SNQLElement.map("true" to false, "false" to false)
    )
    override val name: String = "SERIALIZER_WARMING"

    override fun invoke() {
        for (element in elements) {
            var serialized = ""
            var timeTaken = "-1L";

            if (element is List<*>) {
                timeTaken = NumberFormat.getInstance().format(
                    measureNanoTime {
                        serialized = element.toSNQLString()
                    } / 1000
                )
            }

            if (element is Map<*, *>) {
                timeTaken = NumberFormat.getInstance().format(
                    measureNanoTime {
                        @Suppress("UNCHECKED_CAST")
                        serialized = (element as Map<String, Any?>).toSNQLString()
                    } / 1000
                )
            }

            SnailDb.LOGGER.info("event" to "APPLICATION_WARMING", "task" to objectsToJson(
                "name" to "SERIALIZER_WARMING",
                "duration" to "$timeTaken microseconds"
            ))

            var deserializingTimeTaken = "-1L"

            if (element is List<*>) {
                deserializingTimeTaken = NumberFormat.getInstance().format(
                    (measureNanoTime {
                        serialized.toSNQLArray()
                    } / 1000)
                )
            }

            if (element is Map<*, *>) {
                deserializingTimeTaken = NumberFormat.getInstance().format(
                    (measureNanoTime {
                        serialized.toSNQLObject()
                    } / 1000)
                )
            }

            SnailDb.LOGGER.info("event" to "ASSERTION", "task" to objectsToJson(
                "name" to "DESERIALIZING_SERIALIZED_ELEMENTS",
                "duration" to "$deserializingTimeTaken microseconds"
            ))
        }
    }
}
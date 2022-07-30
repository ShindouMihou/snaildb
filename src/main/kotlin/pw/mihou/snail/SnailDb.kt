package pw.mihou.snail

import ch.qos.logback.classic.Logger
import io.javalin.Javalin
import io.javalin.core.compression.CompressionStrategy
import org.slf4j.LoggerFactory

object SnailDb {

    val APPLICATION: Javalin = Javalin.create {
        it.showJavalinBanner = false
        it.defaultContentType = "application/json"
        it.enableCorsForAllOrigins()
        it.compressionStrategy(CompressionStrategy.GZIP)
    }
    val LOGGER = LoggerFactory.getLogger("SnailDb") as Logger

}
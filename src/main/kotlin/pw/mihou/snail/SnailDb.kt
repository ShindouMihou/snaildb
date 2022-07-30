package pw.mihou.snail

import ch.qos.logback.classic.Logger
import io.javalin.Javalin
import org.slf4j.LoggerFactory

object SnailDb {

    val APPLICATION: Javalin = Javalin.create {
        it.showJavalinBanner = false
    }
    val LOGGER = LoggerFactory.getLogger("SnailDb") as Logger

}
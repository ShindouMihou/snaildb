package pw.mihou.snail

import pw.mihou.snail.http.handler.HttpAfterHandler
import pw.mihou.snail.http.handler.HttpBeforeHandler
import pw.mihou.snail.http.handler.HttpExceptionHandler
import pw.mihou.snail.http.routes.OptionsRoute
import pw.mihou.snail.http.routes.PostRoute
import pw.mihou.snail.logger.info
import pw.mihou.snail.logger.objectsToJson
import pw.mihou.snail.warming.Warmup

fun main() {
    Warmup()
    SnailDb.APPLICATION.get("/") { context -> context.status(204) }
    SnailDb.APPLICATION.after(HttpAfterHandler)
    SnailDb.APPLICATION.before(HttpBeforeHandler)
    SnailDb.APPLICATION.post("/", PostRoute)
    SnailDb.APPLICATION.options("/*", OptionsRoute)
    SnailDb.APPLICATION.exception(Exception::class.java, HttpExceptionHandler)
    SnailDb.APPLICATION.start(7474)

    SnailDb.LOGGER.info("event" to "APPLICATION_STARTED", "task" to objectsToJson(
        "port" to 7474
    ))
}
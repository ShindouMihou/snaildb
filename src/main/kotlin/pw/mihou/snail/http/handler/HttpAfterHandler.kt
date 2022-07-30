package pw.mihou.snail.http.handler

import io.javalin.http.Context
import io.javalin.http.Handler
import pw.mihou.snail.SnailDb
import pw.mihou.snail.logger.info
import pw.mihou.snail.logger.objectsToJson

object HttpAfterHandler: Handler {
    override fun handle(context: Context) {
        SnailDb.LOGGER.info("event" to "AFTER_HTTP_REQUEST", "request" to objectsToJson(
            "method" to context.method(),
            "path" to context.path(),
            "body" to context.body()
        ))
    }
}
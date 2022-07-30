package pw.mihou.snail.http.handler

import io.javalin.http.Context
import io.javalin.http.Handler

object HttpBeforeHandler: Handler {
    override fun handle(context: Context) {
        context.header("Content-Type", "application/json").header("server", "snaildb")
    }
}
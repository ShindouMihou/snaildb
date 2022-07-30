package pw.mihou.snail.http.routes

import io.javalin.http.Context
import io.javalin.http.Handler

object OptionsRoute: Handler {
    override fun handle(context: Context) {
        context.status(204)
    }
}
package pw.mihou.snail.http.handler

import io.javalin.http.Context
import io.javalin.http.ExceptionHandler

object HttpExceptionHandler: ExceptionHandler<Exception> {
    override fun handle(exception: Exception, context: Context) {
        context.status(400).result("{\"message\":\"An exception occurred.\",\"error\":\"${exception.message}\",\"code\":-1}")
        exception.printStackTrace()
    }
}
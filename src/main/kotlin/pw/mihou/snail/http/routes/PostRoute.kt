package pw.mihou.snail.http.routes

import io.javalin.http.Context
import io.javalin.http.Handler
import pw.mihou.snail.http.validateAuthentication
import pw.mihou.snail.http.validateSNQLContentType
import pw.mihou.snail.messages.SnailMessages
import pw.mihou.snail.snql.toSNQLObject

object PostRoute: Handler {

    override fun handle(context: Context) {
        if (!context.validateSNQLContentType()) {
            return
        }

        if (context.validateAuthentication() == null) {
            return
        }

        val message = context.body().toSNQLObject()

        if (message == null) {
            context.status(400).result(SnailMessages.INVALID_SNQL)
            return
        }

        val database = message["database"]
        val collection = message["collection"]
        val content = message["content"]

        if (database !is String? || database.isNullOrBlank()) {
            context.status(400).result(SnailMessages.MISSING_DATABASE_FIELD)
            return
        }

        if (collection !is String? || collection.isNullOrBlank()) {
            context.status(400).result(SnailMessages.MISSING_COLLECTION_FIELD)
            return
        }

        if (content !is Map<*, *>) {
            context.status(400).result(SnailMessages.MISSING_CONTENT_FIELD)
            return
        }

        context.status(200).result(message.toString())
    }


}
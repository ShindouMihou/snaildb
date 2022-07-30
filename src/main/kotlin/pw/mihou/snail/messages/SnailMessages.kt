package pw.mihou.snail.messages

object SnailMessages {

    const val INVALID_SNQL = "{\"message\":\"An exception occurred\",\"error\":\"Communications must be in SNQL.\",\"code\":1}"
    const val UNAUTHENTICATED = "{\"message\":\"Invalid authentication headers.\",\"code\":2}"
    const val INVALID_CONTENT_TYPE = "{\"message\":\"Invalid content type headers. You must use application/x.snail.snql to communicate.\",\"code\":3}"
    const val MISSING_DATABASE_FIELD = "{\"message\":\"You are missing the database field.\",\"code\":4}"
    const val MISSING_COLLECTION_FIELD = "{\"message\":\"You are missing the collection field.\",\"code\":5}"
    const val MISSING_CONTENT_FIELD = "{\"message\":\"You are missing the content field.\",\"code\":6}"
}
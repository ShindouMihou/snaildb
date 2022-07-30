package pw.mihou.snail.http

import io.javalin.http.Context
import pw.mihou.snail.messages.SnailMessages

/**
 * Validates the content type to be that of "application/x.snail.snql" to ensure we conform
 * with the specifications and returns a boolean based on whether the content-type is of
 * that value or not.
 *
 * @return Whether the content-type is of SNQL or not.
 */
fun Context.validateSNQLContentType(): Boolean {
    if (header("Content-Type") != "application/x.snail.snql") {
        status(400).result(SnailMessages.INVALID_CONTENT_TYPE)
        return false
    }

    return true
}

/**
 * Validates the Authorization header to identify whether it is of the correct format and
 * is authenticated to perform an action. This returns the username and the token that is being
 * used in the headers.
 *
 * @return The username and token being used in the headers, if present.
 */
fun Context.validateAuthentication(): Pair<String, String>? {
    if (header("Authorization") == null) {
        status(401).result(SnailMessages.UNAUTHENTICATED)
        return null
    }

    val matcher = HttpRegexes.USER_TOKEN_REGEX.matchEntire(header("Authorization")!!)

    if (matcher == null) {
        status(401).result(SnailMessages.UNAUTHENTICATED)
        return null
    }

    val (username, token) = matcher.groups["username"]!!.value to matcher.groups["token"]!!.value
    return username to token
}
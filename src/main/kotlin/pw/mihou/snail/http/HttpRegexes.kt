package pw.mihou.snail.http

object HttpRegexes {

    val USER_TOKEN_REGEX = "(?<username>.*)@(?<token>.*)".toRegex()

}
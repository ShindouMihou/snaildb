package pw.mihou.snail.http

import io.javalin.http.Context

interface HttpRoute {

    fun onHit(context: Context)

}
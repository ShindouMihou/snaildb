package pw.mihou.snail.logger

import ch.qos.logback.classic.Logger
import org.json.JSONObject

fun objectsToJson(vararg objects: Pair<String, Any>): JSONObject {
    val json = JSONObject()

    for ((key, value) in objects) {
        json.accumulate(key, value)
    }

    return json
}

fun Logger.info(vararg objects: Pair<String, Any>) {
    this.info(objectsToJson(*objects).toString())
}

fun Logger.error(vararg objects: Pair<String, Any>) {
    this.error(objectsToJson(*objects).toString())
}

fun Logger.error(throwable: Throwable, vararg objects: Pair<String, Any>) {
    this.error(objectsToJson(*objects).toString(), throwable)
}

fun Logger.debug(vararg objects: Pair<String, Any>) {
    this.debug(objectsToJson(*objects).toString())
}

fun Logger.warn(vararg objects: Pair<String, Any>) {
    this.warn(objectsToJson(*objects).toString())
}
package com.edoz.routes

import com.edoz.ffmpegInstanceStorage
import com.edoz.obj.FFmpegInstance
import com.edoz.windowsKillProcess
import com.edoz.windowsQueryProcess
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/7
 */

fun Application.registerFFmpegRouting() {
    routing {
        FFmpegRouting()
    }
}


fun Route.FFmpegRouting() {
    route(FFmpegInstance.path) {
        get {
            windowsQueryProcess()
            call.respond(ffmpegInstanceStorage)
//            if (ffmpegInstanceStorage.isNotEmpty()) {
//            } else {
//                call.respondText("No FFmpeg instance found", status = HttpStatusCode.NotFound)
//            }
        }
        delete("/{pid}") {
            val pid = call.parameters["pid"] ?: call.respond(HttpStatusCode.BadRequest, "invalid delete request")
            windowsKillProcess(pid as String)
            call.respond(HttpStatusCode.OK)
        }
    }
}
package com.edoz.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 *@author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/10
 */

fun Application.registerAuthorityRouting() {
    routing() {
        route("/authority", (
                {
                    this.post {
                        var input = call.receive<String>()
                        input = input.substring(1, input.length - 1)
                        if (input == Something.getUuid()) {
                            call.respond(true)
                        } else {
                            call.respond(false)
                        }
                    }
                }
                ))
    }
}
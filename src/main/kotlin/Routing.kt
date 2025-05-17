package com.cashwu

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/test") {
            val text = "<h1>Hello World!</h1>"
            val type = ContentType.Text.Html
            call.respondText(text, type)
        }
    }
}

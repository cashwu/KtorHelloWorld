package com.cashwu

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    install(StatusPages) {
    exception<Throwable> { call, cause ->
        call.respondText(text = "500: ${cause.message}" , status = HttpStatusCode.InternalServerError)
    }
}
    routing {

        staticResources("/content", "myContent")

        get("/") {
            call.respondText("Hello World!")
        }

        get("/test") {
            val text = "<h1>Hello World!</h1>"
            val type = ContentType.Text.Html
            call.respondText(text, type)
        }

        get("/error") {
           throw IllegalArgumentException("Invalid argument")
        }

        get("/tasks") {
            call.respondText(
                contentType = ContentType.Text.Html,
                text = """
                <h3>TODO:</h3>
                <ol>
                    <li>A table of all the tasks</li>
                    <li>A form to submit new tasks</li>
                </ol>
                """.trimIndent()
            )
        }
    }
}

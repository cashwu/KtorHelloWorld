package com.cashwu

import com.cashwu.model.Priority
import com.cashwu.model.TaskRepository
import com.cashwu.model.tasksAsTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: ${cause.message}", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {

        staticResources("/content", "myContent")
        staticResources("/task-ui", "task-ui")

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

            val tasks = TaskRepository.allTasks()

            call.respondText(
                contentType = ContentType.Text.Html,
                text = tasks.tasksAsTable()
            )
        }

        get("/tasks/byPriority/{priority}") {

            val priorityAsText = call.parameters["priority"]

            if (priorityAsText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                val priority = Priority.valueOf(priorityAsText)

                val tasks = TaskRepository.tasksByPriority(priority)

                if (tasks.isEmpty()) {
                    call.respondText(
                        contentType = ContentType.Text.Html,
                        text = "<h1>No tasks found</h1>"
                    )
                    return@get
                }

                call.respondText(
                    contentType = ContentType.Text.Html,
                    text = tasks.tasksAsTable()
                )
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

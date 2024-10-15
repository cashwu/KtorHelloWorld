package com.cashwu.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/", HttpMethod.Get) {
            handle {
                call.respondText("Hello World")
            }
        }

        get("/users") {
            call.respondText("got users")
        }

        get("/users/kevin") {
            call.respondText("hello, kevin")
        }

        route("/books") {
            get ("byId"){
                call.respondText("got books by id")
            }
        }

        get("/user/{name}") {
            val person = call.parameters["name"]
            call.respondText("hello, $person")
        }

        get("/book/{title?}") {
            call.respondText("got ${call.parameters["title"]}")
        }

//        get("/car/*") {
//            val route = call.request.path()
//            call.respondText("got car route : $route")
//        }

        get("/car/*/foo") {
            val route = call.request.path()
            call.respondText("got car route : $route")
        }

        get("/cars/{...}") {
            call.respondText("got ${call.request.path()}")
        }

        get("/people/{param...}") {
            val param = call.parameters.getAll("param")?.joinToString(",")
            call.respondText("got $param")
        }

        get("/todo") {
            val id = call.request.queryParameters["id"]
            call.respondText("got $id")
        }
    }
}

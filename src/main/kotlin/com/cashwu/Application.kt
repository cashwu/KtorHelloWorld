package com.cashwu

import com.cashwu.plugins.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    var port = environment.config.propertyOrNull("ktor.deployment.port")?.getString() ?: "9999"

    install(IgnoreTrailingSlash)
    configureMonitoring()
    configureRouting()
}

package com.example.routes

import com.example.domain.model.EndPoint
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.rootRoute() {
    get(EndPoint.Root.path) {
        call.respondText("Welcome To Ktor Server")
    }

}
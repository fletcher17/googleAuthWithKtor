package com.example.routes

import com.example.domain.model.EndPoint
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.unauthorized() {
    get(EndPoint.Unauthorized.path) {
        call.respond(
            message = "Not Authorized. ",
            status = HttpStatusCode.Unauthorized
        )
    }
}
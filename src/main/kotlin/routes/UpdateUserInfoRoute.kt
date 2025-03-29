package com.example.routes

import com.example.domain.model.*
import com.example.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUserInfoRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    authenticate("auth-session") {
        put(EndPoint.UpdateUserInfo.path) {
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            if (userSession == null) {
                call.respondRedirect(EndPoint.Unauthorized.path)
            } else {
                try {
                    updateUserInfo(app, userDataSource, userUpdate, userSession.id)
                } catch (e: Exception) {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            }
        }
    }
}

private suspend fun RoutingContext.updateUserInfo(
    app: Application,
    userDataSource: UserDataSource,
    userUpdate: UserUpdate,
    id: String
) {
    val respond = userDataSource.updateUserInfo(
        userId = id,
        firstName = userUpdate.firstName,
        lastName = userUpdate.lastName
    )
    if (respond) {
        app.log.info("USER SUCCESSFULLY UPDATED")
        call.respond(
            message = ApiResponse(
                success = true,
                message = "Successfully Updated"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR UPDATING THE USER")
        call.respond(
            message = ApiResponse(success = false),
            status = HttpStatusCode.BadRequest
        )
    }

}
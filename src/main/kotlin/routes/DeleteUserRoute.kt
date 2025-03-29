package com.example.routes

import com.example.domain.model.ApiResponse
import com.example.domain.model.EndPoint
import com.example.domain.model.UserSession
import com.example.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.deleteUserRoute(
    app: Application,
    userDataSource: UserDataSource
) {
    authenticate("auth-session") {
        delete(EndPoint.DeleteUser.path) {
            val userSession = call.principal<UserSession>()
            if (userSession == null) {
                call.respondRedirect(EndPoint.Unauthorized.path)
            } else {
                try {
                    call.sessions.clear<UserSession>()
                    deleteUserFromDb(app,userSession.id, userDataSource)
                } catch (e: Exception) {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            }
        }
    }

}


private suspend fun RoutingContext.deleteUserFromDb(
    app: Application,
    userId: String,
    userDataSource: UserDataSource
) {
    val result = userDataSource.deleteUser(userId)
    if (result) {
        app.log.info("USER SUCCESSFULLY DELETED")
        call.respond(
            message = ApiResponse(
                success = true
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR DELETING THE USER")
        call.respond(
            message = ApiResponse(success = false),
            status = HttpStatusCode.BadRequest
        )
    }

}
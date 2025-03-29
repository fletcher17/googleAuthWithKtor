package com.example.plugins

import com.example.domain.repository.UserDataSource
import com.example.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {
    routing {
        val userDataSource: UserDataSource by inject(UserDataSource::class.java)
        rootRoute()
        tokenVerificationRoute(application, userDataSource)
        getUserInfoRoute(this@configureRouting, userDataSource)
        updateUserInfoRoute(this@configureRouting, userDataSource)
        deleteUserRoute(this@configureRouting, userDataSource)
        signOutRoute()
        authorizedRoute()
        unauthorized()
    }
}

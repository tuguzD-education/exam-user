@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.ErrorResponse
import io.github.tuguzd.repository.UserDao
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.user() {
    val dao by inject<UserDao>()

    post<Users.Register> {
        call.respond(
            dao.register(call.receive())
        )
    }

    post<Users.LogIn> {
        try {
            call.respond(dao.login(call.receive()))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
        }
    }
}

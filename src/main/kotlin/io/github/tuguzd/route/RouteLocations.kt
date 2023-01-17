@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.ktor.server.locations.*

@Location("/users")
class Users {
    @Location("/login")
    data class LogIn(val user: Users)

    @Location("/register")
    data class Register(val user: Users)
}

package io.github.tuguzd.repository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*

class Client(
    keycloakUri: String,
    val appRealm: String,
    val adminId: String,
    val adminUsername: String,
    val adminPassword: String,
    val clientId: String,
    val clientCredentials: String,
) {
    val httpClient = HttpClient(CIO) {
        install(Logging)
        install(Resources)
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(keycloakUri)
        }
    }
}

@file:Suppress("unused", "UnnecessaryOptInAnnotation")
@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd

import io.github.tuguzd.repository.*
import io.github.tuguzd.route.user
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.module() {
    val keycloakUri = environment.config.property("service.keycloak.uri").getString()
    val appRealm = environment.config.property("service.keycloak.realm").getString()
    val adminId = environment.config.property("service.keycloak.admin.client_id").getString()
    val adminUsername = environment.config.property("service.keycloak.admin.username").getString()
    val adminPassword = environment.config.property("service.keycloak.admin.password").getString()
    val clientId = environment.config.property("service.keycloak.client.client_id").getString()
    val clientCredentials = environment.config.property("service.keycloak.client.client_secret").getString()

    val appModule = module {
        single {
            Client(
                appRealm = appRealm,
                keycloakUri = keycloakUri,
                adminId = adminId,
                adminUsername = adminUsername,
                adminPassword = adminPassword,
                clientId = clientId,
                clientCredentials = clientCredentials,
            )
        }
        singleOf(::UserDao)
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(ContentNegotiation) {
        json()
    }
    install(Locations)

    routing {
        user()
    }
}

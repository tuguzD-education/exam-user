package io.github.tuguzd.repository

import io.github.tuguzd.model.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDao : KoinComponent {
    private val client by inject<Client>()

    suspend fun register(user: User): HttpStatusCode {
        val response: Token = submitForm(
            realm = "master",
            clientId = client.adminId,
            username = client.adminUsername,
            password = client.adminPassword,
        ).body()

        return client.httpClient.post(
            urlString = "/admin/realms/${client.appRealm}/users",
        ) {
            bearerAuth(response.accessToken)
            contentType(ContentType.Application.Json)
            setBody(
                UserKeycloak(
                    username = user.username,
                    credentials = listOf(
                        CredentialsKeycloak(value = user.password)
                    )
                )
            )
        }.status
    }

    suspend fun login(user: User): Token = submitForm(
        realm = client.appRealm,
        username = user.username,
        password = user.password,
        clientId = client.clientId,
        clientSecret = client.clientCredentials,
    ).body()

    private suspend fun submitForm(
        realm: String,
        username: String,
        password: String,
        clientId: String,
        clientSecret: String? = null,
    ) = client.httpClient
        .submitForm(
            url = "/realms/$realm/protocol/openid-connect/token",
            formParameters = Parameters.build {
                append("username", username)
                append("password", password)
                append("grant_type", "password")
                append("client_id", clientId)
                clientSecret?.let {
                    append("client_secret", it)
                }
            }
        )
}

package io.github.tuguzd.model

import kotlinx.serialization.Serializable

@Serializable
data class UserKeycloak(
    val username: String,
    val enabled: Boolean = true,
    val credentials: List<CredentialsKeycloak>,
)

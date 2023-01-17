package io.github.tuguzd.model

import kotlinx.serialization.Serializable

@Serializable
data class CredentialsKeycloak(
    val value: String,
    val type: String = "password",
    val temporary: Boolean = false,
)

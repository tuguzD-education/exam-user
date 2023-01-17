package io.github.tuguzd.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val accessExpiresIn: Int,

    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("refresh_expires_in") val refreshExpiresIn: Int,

    @SerialName("token_type") val tokenType: String,
    @SerialName("not-before-policy") val policy: Int,

    @SerialName("session_state") val sessionState: String,
    val scope: String,
)

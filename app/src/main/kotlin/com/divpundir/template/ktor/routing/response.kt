package com.divpundir.template.ktor.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class Success<T>(val data: T)

@Serializable
object EmptyBody

suspend inline fun <reified T> ApplicationCall.respondWithSuccess(message: T) {
    respond(Success(message))
}

suspend fun ApplicationCall.respondWithSuccess() {
    respond(Success(EmptyBody))
}

suspend inline fun ApplicationCall.respondWithFailure(status: HttpStatusCode, message: String) {
    respond(status, message)
}

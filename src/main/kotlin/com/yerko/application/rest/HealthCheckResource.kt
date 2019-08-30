package com.yerko.application.rest

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

class HealthCheckResource {
    suspend fun getHealthCheck(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, Health("UP"))
    }
}

data class Health(val status: String)

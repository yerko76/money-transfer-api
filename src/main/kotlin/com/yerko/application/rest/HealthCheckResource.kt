package com.yerko.application.rest

import io.ktor.application.ApplicationCall
import io.ktor.response.respondText

class HealthCheckResource {
    suspend fun getHealthCheck(call: ApplicationCall) {
        call.respondText("OK")
    }
}

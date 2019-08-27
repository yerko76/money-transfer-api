package com.yerko.infrastructure.configuration

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.routing

fun Application.main() {

    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
        }
    }
    routing {

    }
}

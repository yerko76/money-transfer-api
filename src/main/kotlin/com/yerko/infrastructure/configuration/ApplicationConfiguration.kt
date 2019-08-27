package com.yerko.infrastructure.configuration

import com.yerko.application.rest.HealthCheckResource
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import org.kodein.di.generic.instance

fun Application.mainModule() {
    val userController by ModulesConfiguration.kodein.instance<HealthCheckResource>()

    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
        }
    }
    routing {
        healthCheck(userController)
    }
}
